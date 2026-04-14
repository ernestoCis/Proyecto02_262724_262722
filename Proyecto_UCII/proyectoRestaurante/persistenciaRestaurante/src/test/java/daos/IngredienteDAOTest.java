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
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class IngredienteDAOTest {

    private IngredienteDAO ingredienteDAO;
    private List<Long> idsLimpieza;

    public IngredienteDAOTest() {
        ingredienteDAO = IngredienteDAO.getInstance();
    }

    @BeforeEach
    public void setUp() {
        idsLimpieza = new ArrayList<>();
    }

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

    @Test
    public void testGuardarYBuscarPorId() throws PersistenciaException {
        Ingrediente ing = new Ingrediente();
        ing.setNombre("Ingrediente de Prueba");
        ing.setUnidadMedida(UnidadMedida.GRAMOS);
        ing.setCantidadActual(500.0);

        ingredienteDAO.guardar(ing);

        //registrar para limpieza
        assertNotNull(ing.getIdIngrediente());
        idsLimpieza.add(ing.getIdIngrediente());

        Ingrediente encontrado = ingredienteDAO.buscarPorId(ing.getIdIngrediente());
        assertNotNull(encontrado);
        assertEquals("Ingrediente de Prueba", encontrado.getNombre());
    }

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

    @Test
    public void testBuscarPorNombreYUnidad() throws PersistenciaException {
        String nombreUnico = "IngredienteUnico_" + System.currentTimeMillis();
        Ingrediente ing = new Ingrediente(nombreUnico, UnidadMedida.PIEZAS,  10.0, null);

        ingredienteDAO.guardar(ing);
        idsLimpieza.add(ing.getIdIngrediente());

        Ingrediente resultado = ingredienteDAO.buscarPorNombreYUnidad(nombreUnico, UnidadMedida.PIEZAS);

        assertNotNull(resultado);
        assertEquals(nombreUnico, resultado.getNombre());
    }

    @Test
    public void testEstaEnUso() throws PersistenciaException {
        Ingrediente ing = new Ingrediente("Sin Uso", UnidadMedida.PIEZAS, 10.0, null);
        ingredienteDAO.guardar(ing);
        idsLimpieza.add(ing.getIdIngrediente());

        //como es nuevo y no tiene recetas asociadas, debe ser false
        boolean enUso = ingredienteDAO.estaEnUso(ing.getIdIngrediente());
        assertFalse(enUso);
    }

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
