package daos;

import entidades.ClienteFrecuente;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
/**
 * 
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ClienteFrecuenteDAOTest {

    private ClienteFrecuenteDAO dao;
    
    private List<Long> idsParaLimpiar;

    @BeforeEach
    public void setUp() {
        dao = ClienteFrecuenteDAO.getInstance();
        idsParaLimpiar = new ArrayList<>();
    }
    
    @AfterEach
    public void tearDown() {
        for (Long id : idsParaLimpiar) {
            try {
                dao.eliminarCliente(id);
            } catch (PersistenciaException e) {
            }
        }
    }

    // --- pruebas del metodo guardar ---
    @Test
    public void testGuardar_FlujoBase() throws PersistenciaException {
        ClienteFrecuente cliente = new ClienteFrecuente();
        cliente.setNombres("Ernesto");
        cliente.setTelefono("6441234567");
        cliente.setEmail("ernesto@mail.com");

        ClienteFrecuente guardado = dao.guardar(cliente);
        
        idsParaLimpiar.add(guardado.getIdCliente());

        assertNotNull(guardado.getIdCliente());
        assertEquals("Ernesto", guardado.getNombres());
    }

    // --- pruebas del metodo buscar por telefono ---
    @Test
    public void testBuscarPorTelefono_FlujoBase() throws PersistenciaException {
        String tel = "1234567890";
        ClienteFrecuente c = new ClienteFrecuente();
        c.setNombres("Paulina");
        c.setTelefono(tel);
        dao.guardar(c);

        ClienteFrecuente encontrado = dao.buscarPorTelefono(tel);
        
        idsParaLimpiar.add(encontrado.getIdCliente());

        assertNotNull(encontrado);
        assertEquals("Paulina", encontrado.getNombres());
    }

    @Test
    public void testBuscarPorTelefono_NoExistente() throws PersistenciaException {
        ClienteFrecuente encontrado = dao.buscarPorTelefono("0000000000");
        assertNull(encontrado);
    }

    // --- pruebas del metodo eliminar ---
    @Test
    public void testEliminarCliente_FlujoBase() throws PersistenciaException {
        ClienteFrecuente c = new ClienteFrecuente();
        c.setNombres("AEliminar");
        c.setTelefono("9999");
        dao.guardar(c);
        Long id = c.getIdCliente();

        dao.eliminarCliente(id);

        assertNull(dao.buscarPorId(id));
    }

    @Test
    public void testEliminarCliente_IdInexistente() {
        assertThrows(PersistenciaException.class, () -> {
            dao.eliminarCliente(999999L);
        }, "debe lanzar PersistenciaException si el dD no existe");
    }

    // --- pruebas del metodo buscar por filtro ---
    @Test
    public void testBuscarPorFiltro_PorNombre() throws PersistenciaException {
        ClienteFrecuente c = new ClienteFrecuente();
        c.setNombres("Marcos");
        c.setTelefono("5555");
        ClienteFrecuente guardado = dao.guardar(c);

        List<ClienteFrecuente> resultados = dao.buscarPorFiltro("Marc");
        
        idsParaLimpiar.add(guardado.getIdCliente());
        
        assertFalse(resultados.isEmpty());
        assertTrue(resultados.stream().anyMatch(cl -> cl.getNombres().equals("Marcos")));
    }

    // --- pruebas del metodo tiene comandas ---
    @Test
    public void testTieneComandas_SinComandas() throws PersistenciaException {
        ClienteFrecuente c = new ClienteFrecuente();
        c.setNombres("Nuevo");
        c.setTelefono("8888");
        ClienteFrecuente guardado = dao.guardar(c);

        boolean tiene = dao.tieneComandas(c.getIdCliente());
        
        idsParaLimpiar.add(guardado.getIdCliente());

        assertFalse(tiene);
    }
}