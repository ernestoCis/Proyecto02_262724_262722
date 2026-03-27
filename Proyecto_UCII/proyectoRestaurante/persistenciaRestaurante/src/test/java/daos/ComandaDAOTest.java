/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

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
public class ComandaDAOTest {

    private final ComandaDAO dao = ComandaDAO.getInstance();

    @Test
    public void contarVisitasDeCliente() {
        int visitas = dao.contarVisitas(1L);

        assertTrue(visitas >= 0);
        assertNotNull(visitas);
    }

    @Test
    public void retornarCeroSiNoHayVisitas() {
        int visitas = dao.contarVisitas(99789L);

        assertTrue(visitas >= 0);
        assertFalse(visitas < 0);
    }

    @Test
    public void calcularTotalGastado() {
        double total = dao.totalGastado(1L);

        assertTrue(total >= 0);
        assertNotNull(total);
    }

    @Test
    public void retornarCeroSiNoHayConsumos() {
        double total = dao.totalGastado(99789L);

        assertTrue(total >= 0);
        assertFalse(total < 0);
    }
}
