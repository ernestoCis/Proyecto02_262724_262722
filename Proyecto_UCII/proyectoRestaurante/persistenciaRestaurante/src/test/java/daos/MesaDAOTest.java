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
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class MesaDAOTest {

    private MesaDAO dao;
    private List<Long> idsParaLimpiar;

    @BeforeEach
    public void setUp() {
        dao = MesaDAO.getInstance();
        idsParaLimpiar = new ArrayList<>();
    }

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

    @Test
    public void testRegistrarMesa_FlujoBase() throws PersistenciaException {
        Mesa mesa = new Mesa();
        mesa.setNumero(10000);
        mesa.setDisponibilidad(EstadoMesa.DISPONIBLE);

        Mesa guardada = dao.registrarMesa(mesa);

        if (guardada != null && guardada.getIdMesa() != null) {
            idsParaLimpiar.add(guardada.getIdMesa());
        }

        assertNotNull(guardada);
        assertNotNull(guardada.getIdMesa());
        assertEquals(10000, (int) guardada.getNumero());
    }

    @Test
    public void testConsultarMesaPorNumero_Existente() throws PersistenciaException {
        Mesa mesa = new Mesa();
        mesa.setNumero(50);
        mesa.setDisponibilidad(EstadoMesa.DISPONIBLE);
        Mesa guardada = dao.registrarMesa(mesa);
        idsParaLimpiar.add(guardada.getIdMesa());

        Mesa encontrada = dao.consultarMesaPorNumero(50);

        assertNotNull(encontrada);
        assertEquals(guardada.getIdMesa(), encontrada.getIdMesa());
    }

    @Test
    public void testConsultarMesaPorNumero_Inexistente() throws PersistenciaException {
        Mesa encontrada = dao.consultarMesaPorNumero(9999);
        assertNull(encontrada);
    }

    @Test
    public void testActualizarMesa_FlujoBase() throws PersistenciaException {
        Mesa mesa = new Mesa();
        mesa.setNumero(5000);
        mesa.setDisponibilidad(EstadoMesa.DISPONIBLE);
        Mesa guardada = dao.registrarMesa(mesa);
        idsParaLimpiar.add(guardada.getIdMesa());

        guardada.setNumero(5005); // Cambiamos el número
        Mesa actualizada = dao.actualizarMesa(guardada);

        assertEquals(5005L, (long) actualizada.getNumero());
    }

    @Test
    public void testConsultarTodas() throws PersistenciaException {
        Mesa m1 = new Mesa();
        m1.setNumero(101);
        m1.setDisponibilidad(EstadoMesa.DISPONIBLE);
        Mesa m2 = new Mesa();
        m2.setDisponibilidad(EstadoMesa.DISPONIBLE);
        m2.setNumero(102);

        idsParaLimpiar.add(dao.registrarMesa(m1).getIdMesa());
        idsParaLimpiar.add(dao.registrarMesa(m2).getIdMesa());

        List<Mesa> lista = dao.consultarTodas();

        assertTrue(lista.size() >= 2);
    }

}
