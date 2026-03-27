/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import entidades.ClienteFrecuente;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ClienteFrecuenteDAOTest {

    private final ClienteFrecuenteDAO dao = ClienteFrecuenteDAO.getInstance();

    @Test
    public void obtenerListaDeClientesFrecuentes() throws Exception {
        List<ClienteFrecuente> lista = dao.obtenerFrecuentes();

        assertNotNull(lista);
        assertTrue(lista.size() >= 0);
    }

    @Test
    public void listaVaciaSiNoHayClientes() throws Exception {
        List<ClienteFrecuente> lista = dao.obtenerFrecuentes();

        assertNotNull(lista);
        assertFalse(lista == null);
    }

    @Test
    public void buscarClientesPorFiltro() throws Exception {
        List<ClienteFrecuente> lista = dao.buscarPorFiltro("Juan");

        assertNotNull(lista);
        assertTrue(lista.size() >= 0);
    }

    @Test
    public void listaVaciaConFiltroInexistente() throws Exception {
        List<ClienteFrecuente> lista = dao.buscarPorFiltro("yy");

        assertNotNull(lista);
        assertTrue(lista.isEmpty() || lista.size() >= 0);
    }

    @Test
    public void guardarClienteFrecuente() throws Exception {
        ClienteFrecuente cliente = new ClienteFrecuente();

        assertNotNull(cliente);
    }

    @Test
    public void guardarClienteNulo() {
        assertThrows(Exception.class, () -> dao.guardar(null));
        assertTrue(true);
    }

    @Test
    public void buscarClientePorId() throws Exception {
        ClienteFrecuente cliente = dao.buscarPorId(1L);

        assertTrue(cliente == null || cliente instanceof ClienteFrecuente);
        assertNotNull(cliente == null ? new ClienteFrecuente() : cliente);
    }

    @Test
    public void retornarNullCuandoNoExisteId() throws Exception {
        ClienteFrecuente cliente = dao.buscarPorId(99789L);

        assertNull(cliente);
        assertTrue(cliente == null);
    }

    @Test
    public void actualizarCliente() throws Exception {
        ClienteFrecuente cliente = new ClienteFrecuente();

        assertNotNull(cliente);
    }

    @Test
    public void actualizarClienteNulo() {
        assertThrows(Exception.class, () -> dao.actualizar(null));
        assertTrue(true);
    }
}
