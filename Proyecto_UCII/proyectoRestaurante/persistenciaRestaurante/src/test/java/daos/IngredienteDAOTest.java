/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.Ingrediente;
import enums.UnidadMedida;
import excepciones.PersistenciaException;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para IngredienteDAO. Valida la persistencia de los
 * insumos, el manejo de diferentes unidades de medida y la integridad
 * referencial para evitar la eliminación de ingredientes que forman parte de
 * recetas activas.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class IngredienteDAOTest {

    private IngredienteDAO ingredienteDAO;
    private List<Long> idsLimpieza;

    /**
     * Constructor de la clase de prueba. Inicializa la instancia del DAO a
     * evaluar.
     */
    public IngredienteDAOTest() {
        ingredienteDAO = IngredienteDAO.getInstance();
    }

    /**
     * Configuración previa a cada prueba. Prepara la lista de rastreo de
     * identificadores para asegurar la limpieza posterior.
     */
    @BeforeEach
    public void setUp() {
        idsLimpieza = new ArrayList<>();
    }

    /**
     * Limpieza posterior a cada ejecución. Remueve manualmente los ingredientes
     * creados durante el test para mantener la independencia entre pruebas y
     * evitar la saturación de la base de datos.
     */
    @AfterEach
    public void tearDown() {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            for (Long id : idsLimpieza) {
                Ingrediente i = em.find(Ingrediente.class, id);
                if (i != null) {
                    em.remove(i);
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Error limpiando BD: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Prueba el flujo de guardado y recuperación por ID. Verifica que el objeto
     * se persista correctamente y mantenga sus atributos.
     *
     * @throws PersistenciaException Si ocurre un error en la transacción.
     */
    @Test
    public void testGuardarYBuscarPorId() throws PersistenciaException {
        Ingrediente ing = new Ingrediente();
        ing.setNombre("Ingrediente de Prueba");
        ing.setUnidadMedida(UnidadMedida.GRAMOS);
        ing.setCantidadActual(500.0);

        ingredienteDAO.guardar(ing);

        assertNotNull(ing.getIdIngrediente());
        idsLimpieza.add(ing.getIdIngrediente());

        Ingrediente encontrado = ingredienteDAO.buscarPorId(ing.getIdIngrediente());
        assertNotNull(encontrado);
        assertEquals("Ingrediente de Prueba", encontrado.getNombre());
    }

    /**
     * Valida la actualización de los datos de un ingrediente. Verifica que los
     * cambios en el nombre y stock se reflejen en la base de datos.
     *
     * @throws PersistenciaException Si falla el merge de la entidad.
     */
    @Test
    public void testActualizar() throws PersistenciaException {
        Ingrediente ing = new Ingrediente("Tomate", UnidadMedida.PIEZAS, 5.0, "ruta");
        ingredienteDAO.guardar(ing);
        idsLimpieza.add(ing.getIdIngrediente());

        ing.setNombre("Tomate Cherry");
        ing.setCantidadActual(200.0);
        ingredienteDAO.actualizar(ing);

        Ingrediente actualizado = ingredienteDAO.buscarPorId(ing.getIdIngrediente());
        assertEquals("Tomate Cherry", actualizado.getNombre());
        assertEquals(200.0, actualizado.getCantidadActual());
    }

    /**
     * Verifica la recuperación masiva de ingredientes. Comprueba que la lista
     * devuelva al menos los elementos creados en el test.
     *
     * @throws PersistenciaException Si la consulta JPQL falla.
     */
    @Test
    public void testObtenerIngredientes() throws PersistenciaException {
        Ingrediente ing1 = new Ingrediente("Prueba 1", UnidadMedida.MILILITROS, 10.0, null);
        Ingrediente ing2 = new Ingrediente("Prueba 2", UnidadMedida.GRAMOS, 20.0, "");
        ingredienteDAO.guardar(ing1);
        ingredienteDAO.guardar(ing2);
        idsLimpieza.add(ing1.getIdIngrediente());
        idsLimpieza.add(ing2.getIdIngrediente());

        List<Ingrediente> lista = ingredienteDAO.obtenerIngredientes();
        assertTrue(lista.size() >= 2);
    }

    /**
     * Prueba la búsqueda por coincidencia exacta de nombre y unidad. Esencial
     * para evitar colisiones entre insumos similares con distintas medidas.
     *
     * @throws PersistenciaException Si ocurre un error en la búsqueda.
     */
    @Test
    public void testBuscarPorNombreYUnidad() throws PersistenciaException {
        String nombreUnico = "IngredienteUnico_" + System.currentTimeMillis();
        Ingrediente ing = new Ingrediente(nombreUnico, UnidadMedida.PIEZAS, 10.0, null);
        ingredienteDAO.guardar(ing);
        idsLimpieza.add(ing.getIdIngrediente());

        Ingrediente resultado = ingredienteDAO.buscarPorNombreYUnidad(nombreUnico, UnidadMedida.PIEZAS);
        assertNotNull(resultado);
        assertEquals(nombreUnico, resultado.getNombre());
    }

    /**
     * Valida la detección de uso de un ingrediente. Verifica que un ingrediente
     * recién creado sin recetas asociadas devuelva false.
     *
     * @throws PersistenciaException Si falla la consulta de conteo.
     */
    @Test
    public void testEstaEnUso() throws PersistenciaException {
        Ingrediente ing = new Ingrediente("Sin Uso", UnidadMedida.PIEZAS, 10.0, null);
        ingredienteDAO.guardar(ing);
        idsLimpieza.add(ing.getIdIngrediente());

        boolean enUso = ingredienteDAO.estaEnUso(ing.getIdIngrediente());
        assertFalse(enUso, "Un ingrediente nuevo no debería marcarse como en uso");
    }

    /**
     * Verifica la eliminación física de un ingrediente del sistema.
     *
     * @throws PersistenciaException Si el ingrediente tiene dependencias o no
     * existe.
     */
    @Test
    public void testEliminar() throws PersistenciaException {
        Ingrediente ing = new Ingrediente("Para Eliminar", UnidadMedida.PIEZAS, 10.0, null);
        ingredienteDAO.guardar(ing);
        Long id = ing.getIdIngrediente();

        ingredienteDAO.eliminar(id);

        Ingrediente eliminado = ingredienteDAO.buscarPorId(id);
        assertNull(eliminado, "El ingrediente debería ser nulo tras eliminarlo");
    }
}
