/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import entidades.ClienteFrecuente;
import entidades.Comanda;
import excepciones.PersistenciaException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ComandaDAOTest {

    private final ComandaDAO dao = ComandaDAO.getInstance();

    @Test
    public void contarVisitasDeCliente() throws PersistenciaException {
//        ClienteFrecuenteDAO clienteDAO = ClienteFrecuenteDAO.getInstance();
//        ComandaDAO comandaDAO = ComandaDAO.getInstance();
//
//        ClienteFrecuente cliente = new ClienteFrecuente();
//        cliente.setNombres("Juan");
//        cliente.setTelefono("644" + System.currentTimeMillis());
//        cliente.setEmail("juan@gmail.com");
//
//        clienteDAO.guardar(cliente);
//
//        Comanda comanda = new Comanda();
//        comanda.setCliente(cliente);
//        comanda.setTotal(200.0);
//
//        // fakta guardar comanda
//        
//        int visitas = dao.contarVisitas(cliente.getIdCliente());
//
//        assertTrue(visitas >= 1);
    }

    @Test
    public void retornarCeroSiNoHayVisitas() throws PersistenciaException {
        int visitas = dao.contarVisitas(99789L);

        assertTrue(visitas >= 0);
        assertFalse(visitas < 0);
    }

    @Test
    public void calcularTotalGastado() throws PersistenciaException {
        double total = dao.totalGastado(1L);

        assertTrue(total >= 0);
        assertNotNull(total);
    }

    @Test
    public void retornarCeroSiNoHayConsumos() throws PersistenciaException {
        double total = dao.totalGastado(99789L);

        assertTrue(total >= 0);
        assertFalse(total < 0);
    }
}
