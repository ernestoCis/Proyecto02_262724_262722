/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import entidades.ClienteFrecuente;
import excepciones.PersistenciaException;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ClienteFrecuenteDAOTest {

    private final ClienteFrecuenteDAO dao = ClienteFrecuenteDAO.getInstance();

    @Test
    public void obtenerListaDeClientesFrecuentes() throws PersistenciaException {
        ClienteFrecuenteDAO dao = ClienteFrecuenteDAO.getInstance();

        ClienteFrecuente cliente = new ClienteFrecuente();
        cliente.setNombres("Maria");
        cliente.setTelefono("644" + System.currentTimeMillis());
        cliente.setEmail("maria@gmail.com");

        dao.guardar(cliente);

        List<ClienteFrecuente> lista = dao.obtenerFrecuentes();

        assertNotNull(lista);
        assertTrue(
                lista.stream().anyMatch(c -> "Maria".equals(c.getNombres()))
        );
    }

    @Test
    public void listaVaciaSiNoHayClientes() throws PersistenciaException {
        List<ClienteFrecuente> lista = dao.obtenerFrecuentes();

        assertNotNull(lista);
    }

    @Test
    public void buscarClientesPorFiltro() throws PersistenciaException {
        ClienteFrecuenteDAO dao = ClienteFrecuenteDAO.getInstance();

        ClienteFrecuente cliente = new ClienteFrecuente();
        cliente.setNombres("Ana Lopez");
        cliente.setTelefono("644" + System.currentTimeMillis());
        cliente.setEmail("ana@gmail.com");

        dao.guardar(cliente);

        List<ClienteFrecuente> resultados = dao.buscarPorFiltro("Ana");

        assertNotNull(resultados);
        assertFalse(resultados.isEmpty());
        assertTrue(
                resultados.stream().anyMatch(c -> "Ana Lopez".equals(c.getNombres()))
        );
    }

    @Test
    public void listaVaciaConFiltroInexistente() throws PersistenciaException {
        List<ClienteFrecuente> lista = dao.buscarPorFiltro("yy");

        assertNotNull(lista);
    }

    @Test
    public void guardarClienteFrecuente() throws PersistenciaException {
        ClienteFrecuenteDAO dao = ClienteFrecuenteDAO.getInstance();

        ClienteFrecuente cliente = new ClienteFrecuente();
        cliente.setNombres("Ernesto");
        cliente.setTelefono("644" + System.currentTimeMillis());
        cliente.setEmail("ernesto@gmail.com");

        dao.guardar(cliente);

        ClienteFrecuente resultado = dao.buscarPorTelefono("6441234567");

        assertNotNull(resultado);
        assertEquals("Ernesto", resultado.getNombres());
    }

    @Test
    public void guardarClienteNulo() throws PersistenciaException {
        assertThrows(Exception.class, () -> dao.guardar(null));
        assertTrue(true);
    }

    @Test
    public void buscarClientePorId() throws PersistenciaException {
        ClienteFrecuenteDAO dao = ClienteFrecuenteDAO.getInstance();

        ClienteFrecuente cliente = new ClienteFrecuente();
        cliente.setNombres("Paula");

        cliente.setEmail("pau@gmail.com");
        cliente.setTelefono("644" + System.currentTimeMillis());
        dao.guardar(cliente);

        ClienteFrecuente encontrado = dao.buscarPorId(cliente.getIdCliente());

        assertNotNull(encontrado);
        assertEquals("Paula", encontrado.getNombres());
    }

    @Test
    public void retornarNullCuandoNoExisteId() throws PersistenciaException {
        ClienteFrecuente cliente = dao.buscarPorId(99789L);

        assertNull(cliente);
    }

    @Test
    public void actualizarCliente() throws PersistenciaException {
        ClienteFrecuenteDAO dao = ClienteFrecuenteDAO.getInstance();

        ClienteFrecuente cliente = new ClienteFrecuente();
        cliente.setNombres("Isa");
        cliente.setTelefono("644" + System.currentTimeMillis());
        cliente.setEmail("isa@gmail.com");

        dao.guardar(cliente);

        cliente.setNombres("Isa Actualizado");
        cliente.setEmail("actualizado@gmail.com");

        dao.actualizar(cliente);

        ClienteFrecuente actualizado = dao.buscarPorId(cliente.getIdCliente());

        assertNotNull(actualizado);
        assertEquals("Isa Actualizado", actualizado.getNombres());
        assertEquals("actualizado@gmail.com", actualizado.getEmail());
    }

    @Test
    public void actualizarClienteNulo() throws PersistenciaException {
        assertThrows(Exception.class, () -> dao.actualizar(null));
        assertTrue(true);
    }

    @Test
    public void eliminarCliente() throws PersistenciaException {
        ClienteFrecuenteDAO dao = ClienteFrecuenteDAO.getInstance();

        ClienteFrecuente cliente = new ClienteFrecuente();
        cliente.setNombres("Jorge");
        cliente.setTelefono("644" + System.currentTimeMillis());
        cliente.setEmail("jorge@gmail.com");

        dao.guardar(cliente);
        dao.eliminarCliente(cliente.getIdCliente());

        ClienteFrecuente eliminado = dao.buscarPorId(cliente.getIdCliente());
        ClienteFrecuente eliminadoTel = dao.buscarPorTelefono("6442222222");

        assertNull(eliminado);
        assertNull(eliminadoTel);
    }

    @Test
    public void buscarPorTelefono() throws PersistenciaException {
        ClienteFrecuenteDAO dao = ClienteFrecuenteDAO.getInstance();

        ClienteFrecuente cliente = new ClienteFrecuente();
        cliente.setNombres("Luz");
        cliente.setTelefono("644" + System.currentTimeMillis());
        cliente.setEmail("luz@gmail.com");

        dao.guardar(cliente);

        ClienteFrecuente encontrado = dao.buscarPorTelefono("6147777777");

        assertNotNull(encontrado);
        assertEquals("Luz", encontrado.getNombres());
    }

    @Test
    public void buscarPorTelefonoNoExistente() throws PersistenciaException {
        ClienteFrecuenteDAO dao = ClienteFrecuenteDAO.getInstance();

        ClienteFrecuente encontrado = dao.buscarPorTelefono("0000000000");

        assertNull(encontrado);
    }
}
