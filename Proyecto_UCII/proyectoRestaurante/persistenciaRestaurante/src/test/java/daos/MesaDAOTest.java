/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.Mesa;
import enums.EstadoMesa;
import excepciones.PersistenciaException;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para MesaDAO. Verifica la correcta gestión del
 * mobiliario del restaurante, asegurando que el registro, actualización de
 * disponibilidad y consultas por número físico funcionen de manera consistente
 * con las reglas de negocio.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class MesaDAOTest {

    private MesaDAO dao;
    private List<Long> idsParaLimpiar;

    /**
     * Configuración inicial antes de cada caso de prueba. Obtiene la instancia
     * del DAO y prepara la lista de limpieza para garantizar la independencia
     * de los tests.
     */
    @BeforeEach
    public void setUp() {
        dao = MesaDAO.getInstance();
        idsParaLimpiar = new ArrayList<>();
    }

    /**
     * Limpieza de la base de datos tras la ejecución de cada prueba. Remueve
     * las mesas creadas durante el test para evitar conflictos de duplicidad de
     * números de mesa en ejecuciones posteriores.
     */
    @AfterEach
    public void tearDown() {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            for (Long id : idsParaLimpiar) {
                Mesa mesa = em.find(Mesa.class, id);
                if (mesa != null) {
                    em.remove(mesa);
                }
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
        } finally {
            em.close();
        }
    }

    /**
     * Prueba el registro exitoso de una nueva mesa. Verifica que el objeto
     * persista correctamente y se le asigne un ID único.
     *
     * @throws PersistenciaException Si ocurre un error en la inserción.
     */
    @Test
    @DisplayName("Prueba de registro básico de mesa")
    public void testRegistrarMesa_FlujoBase() throws PersistenciaException {
        Mesa mesa = new Mesa();
        mesa.setNumero(10000);
        mesa.setDisponibilidad(EstadoMesa.DISPONIBLE);

        Mesa guardada = dao.registrarMesa(mesa);

        if (guardada != null && guardada.getIdMesa() != null) {
            idsParaLimpiar.add(guardada.getIdMesa());
        }

        assertNotNull(guardada, "La mesa guardada no debe ser nula");
        assertNotNull(guardada.getIdMesa(), "La mesa debe tener un ID asignado");
        assertEquals(10000, (int) guardada.getNumero(), "El número de mesa debe coincidir");
    }

    /**
     * Valida la búsqueda de una mesa mediante su número de identificación
     * física.
     *
     * @throws PersistenciaException Si la consulta falla.
     */
    @Test
    @DisplayName("Búsqueda de mesa por número existente")
    public void testConsultarMesaPorNumero_Existente() throws PersistenciaException {
        Mesa mesa = new Mesa();
        mesa.setNumero(50);
        mesa.setDisponibilidad(EstadoMesa.DISPONIBLE);
        Mesa guardada = dao.registrarMesa(mesa);
        idsParaLimpiar.add(guardada.getIdMesa());

        Mesa encontrada = dao.consultarMesaPorNumero(50);

        assertNotNull(encontrada, "Debe encontrar la mesa registrada");
        assertEquals(guardada.getIdMesa(), encontrada.getIdMesa());
    }

    /**
     * Comprueba que la búsqueda de un número de mesa inexistente devuelva null.
     *
     * @throws PersistenciaException Si ocurre un error inesperado en la base de
     * datos.
     */
    @Test
    @DisplayName("Búsqueda de mesa inexistente")
    public void testConsultarMesaPorNumero_Inexistente() throws PersistenciaException {
        Mesa encontrada = dao.consultarMesaPorNumero(9999);
        assertNull(encontrada, "No debe encontrar una mesa con número inexistente");
    }

    /**
     * Prueba la actualización de los datos de una mesa. Verifica que los
     * cambios en el número o estado se reflejen correctamente.
     *
     * @throws PersistenciaException Si falla la actualización.
     */
    @Test
    @DisplayName("Actualización de datos de mesa")
    public void testActualizarMesa_FlujoBase() throws PersistenciaException {
        Mesa mesa = new Mesa();
        mesa.setNumero(5000);
        mesa.setDisponibilidad(EstadoMesa.DISPONIBLE);
        Mesa guardada = dao.registrarMesa(mesa);
        idsParaLimpiar.add(guardada.getIdMesa());

        guardada.setNumero(5005);
        Mesa actualizada = dao.actualizarMesa(guardada);

        assertEquals(5005, (int) actualizada.getNumero(), "El número de la mesa debe haberse actualizado");
    }

    /**
     * Verifica la recuperación de todas las mesas del catálogo. Comprueba que
     * la lista contenga al menos los elementos insertados durante el test.
     *
     * @throws PersistenciaException Si la consulta masiva falla.
     */
    @Test
    @DisplayName("Consulta de catálogo completo de mesas")
    public void testConsultarTodas() throws PersistenciaException {
        Mesa m1 = new Mesa();
        m1.setNumero(101);
        m1.setDisponibilidad(EstadoMesa.DISPONIBLE);
        Mesa m2 = new Mesa();
        m2.setNumero(102);
        m2.setDisponibilidad(EstadoMesa.DISPONIBLE);

        idsParaLimpiar.add(dao.registrarMesa(m1).getIdMesa());
        idsParaLimpiar.add(dao.registrarMesa(m2).getIdMesa());

        List<Mesa> lista = dao.consultarTodas();
        assertTrue(lista.size() >= 2, "La lista debe contener al menos las 2 mesas registradas");
    }
}
