/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import entidades.ClienteFrecuente;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

/**
 * Clase de pruebas unitarias para ClienteFrecuenteDAO. Valida los procesos de
 * registro, búsqueda por filtros, integridad de datos y eliminación, asegurando
 * que el comportamiento de la persistencia sea el esperado.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClienteFrecuenteDAOTest {

    private ClienteFrecuenteDAO dao;
    private List<Long> idsParaLimpiar;

    /**
     * Configuración inicial antes de cada prueba. Inicializa la instancia del
     * DAO y la lista de IDs para asegurar un entorno limpio en cada ejecución.
     */
    @BeforeEach
    public void setUp() {
        dao = ClienteFrecuenteDAO.getInstance();
        idsParaLimpiar = new ArrayList<>();
    }

    /**
     * Limpieza posterior a cada prueba. Elimina de la base de datos todos los
     * registros creados durante el test para evitar efectos secundarios en
     * pruebas posteriores.
     */
    @AfterEach
    public void tearDown() {
        for (Long id : idsParaLimpiar) {
            try {
                dao.eliminarCliente(id);
            } catch (PersistenciaException e) {
                // Silenciamos la excepción en limpieza si el registro ya no existe
            }
        }
    }

    /**
     * Prueba el flujo básico de guardado de un cliente. Verifica que se genere
     * un ID y que los datos persistidos coincidan con los enviados.
     *
     * @throws PersistenciaException si ocurre un error inesperado.
     */
    @Test
    public void testGuardar_FlujoBase() throws PersistenciaException {
        ClienteFrecuente cliente = new ClienteFrecuente();
        cliente.setNombres("Ernesto");
        cliente.setTelefono("6441234567");
        cliente.setEmail("ernesto@mail.com");

        ClienteFrecuente guardado = dao.guardar(cliente);

        idsParaLimpiar.add(guardado.getIdCliente());
        assertNotNull(guardado.getIdCliente(), "El ID del cliente no debe ser nulo tras guardar");
        assertEquals("Ernesto", guardado.getNombres());
    }

    /**
     * Prueba la búsqueda de un cliente por su número telefónico.
     *
     * @throws PersistenciaException si ocurre un error en la consulta.
     */
    @Test
    public void testBuscarPorTelefono_FlujoBase() throws PersistenciaException {
        String tel = "1234567890";
        ClienteFrecuente c = new ClienteFrecuente();
        c.setNombres("Paulina");
        c.setTelefono(tel);
        dao.guardar(c);

        ClienteFrecuente encontrado = dao.buscarPorTelefono(tel);

        if (encontrado != null) {
            idsParaLimpiar.add(encontrado.getIdCliente());
        }

        assertNotNull(encontrado, "Debe encontrar al cliente por teléfono");
        assertEquals("Paulina", encontrado.getNombres());
    }

    /**
     * Valida que la búsqueda por un teléfono inexistente devuelva null.
     *
     * @throws PersistenciaException si falla la conexión.
     */
    @Test
    public void testBuscarPorTelefono_NoExistente() throws PersistenciaException {
        ClienteFrecuente encontrado = dao.buscarPorTelefono("0000000000");
        assertNull(encontrado, "No debe encontrar resultados para un teléfono inexistente");
    }

    /**
     * Verifica la eliminación física de un registro de cliente.
     *
     * @throws PersistenciaException si ocurre un error al eliminar.
     */
    @Test
    public void testEliminarCliente_FlujoBase() throws PersistenciaException {
        ClienteFrecuente c = new ClienteFrecuente();
        c.setNombres("AEliminar");
        c.setTelefono("9999");
        dao.guardar(c);

        Long id = c.getIdCliente();
        dao.eliminarCliente(id);

        assertNull(dao.buscarPorId(id), "El cliente no debe existir tras ser eliminado");
    }

    /**
     * Comprueba que intentar eliminar un ID inexistente dispare la excepción
     * adecuada.
     */
    @Test
    public void testEliminarCliente_IdInexistente() {
        assertThrows(PersistenciaException.class, () -> {
            dao.eliminarCliente(999999L);
        }, "Debe lanzar PersistenciaException si el ID no existe");
    }

    /**
     * Prueba la búsqueda dinámica por filtro (nombre parcial).
     *
     * @throws PersistenciaException si falla la consulta Criteria.
     */
    @Test
    public void testBuscarPorFiltro_PorNombre() throws PersistenciaException {
        ClienteFrecuente c = new ClienteFrecuente();
        c.setNombres("Marcos");
        c.setTelefono("5555");
        ClienteFrecuente guardado = dao.guardar(c);

        List<ClienteFrecuente> resultados = dao.buscarPorFiltro("Marc");
        idsParaLimpiar.add(guardado.getIdCliente());

        assertFalse(resultados.isEmpty(), "La lista de resultados no debe estar vacía");
        assertTrue(resultados.stream().anyMatch(cl -> cl.getNombres().equals("Marcos")),
                "Debe contener al cliente 'Marcos'");
    }

    /**
     * Valida la detección de actividad comercial para un cliente nuevo.
     *
     * @throws PersistenciaException si falla la consulta de agregación.
     */
    @Test
    public void testTieneComandas_SinComandas() throws PersistenciaException {
        ClienteFrecuente c = new ClienteFrecuente();
        c.setNombres("Nuevo");
        c.setTelefono("8888");
        ClienteFrecuente guardado = dao.guardar(c);

        boolean tiene = dao.tieneComandas(c.getIdCliente());
        idsParaLimpiar.add(guardado.getIdCliente());

        assertFalse(tiene, "Un cliente nuevo no debe tener comandas asociadas");
    }
}
