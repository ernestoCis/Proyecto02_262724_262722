/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.Mesero;
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
public class MeseroDAOTest {

    private MeseroDAO dao;
    private List<Long> idsParaLimpiar;

    @BeforeEach
    public void setUp() {
        dao = MeseroDAO.getInstance();
        idsParaLimpiar = new ArrayList<>();
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            for (Long id : idsParaLimpiar) {
                Mesero mesero = em.find(Mesero.class, id);
                if (mesero != null) {
                    em.remove(mesero);
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

    private Mesero crearMeseroValido(String usuario, String rfc) {
        Mesero m = new Mesero();
        m.setUsuario(usuario);
        m.setRfc(rfc);
        m.setNombres("Mesero Test");
        m.setApellidoPaterno("Apellido");
        return m;
    }

    @Test
    public void testRegistrarMesero_FlujoBase() throws PersistenciaException {
        Mesero mesero = crearMeseroValido("mesero1", "RFC123456789");

        Mesero guardado = dao.registrarMesero(mesero);

        if (guardado != null) {
            idsParaLimpiar.add(guardado.getIdEmpleado());
        }

        assertNotNull(guardado.getIdEmpleado());
        assertEquals("mesero1", guardado.getUsuario());
    }

    @Test
    public void testConsultarMeseroPorUsuario_Existente() throws PersistenciaException {
        String user = "usuario_test";
        Mesero m = crearMeseroValido(user, "RFC999");
        Mesero guardado = dao.registrarMesero(m);
        idsParaLimpiar.add(guardado.getIdEmpleado());

        Mesero encontrado = dao.consultarMeseroPorUsuario(user);

        assertNotNull(encontrado);
        assertEquals(user, encontrado.getUsuario());
        assertEquals(guardado.getIdEmpleado(), encontrado.getIdEmpleado());
    }

    @Test
    public void testConsultarMeseroPorUsuario_Inexistente() throws PersistenciaException {
        Mesero encontrado = dao.consultarMeseroPorUsuario("usuario_falso_123");
        assertNull(encontrado);
    }

    @Test
    public void testConsultarTodos() throws PersistenciaException {
        Mesero m1 = crearMeseroValido("u1", "RFC1");
        Mesero m2 = crearMeseroValido("u2", "RFC2");

        idsParaLimpiar.add(dao.registrarMesero(m1).getIdEmpleado());
        idsParaLimpiar.add(dao.registrarMesero(m2).getIdEmpleado());

        List<Mesero> lista = dao.consultarTodos();

        assertTrue(lista.size() >= 2);
    }

    @Test
    public void testRegistrarMesero_UsuarioDuplicado() throws PersistenciaException {
        String user = "repetido";
        Mesero m1 = crearMeseroValido(user, "RFC_A");
        Mesero guardado = dao.registrarMesero(m1);
        idsParaLimpiar.add(guardado.getIdEmpleado());

        Mesero m2 = crearMeseroValido(user, "RFC_B");

        assertThrows(PersistenciaException.class, () -> {
            dao.registrarMesero(m2);
        });
    }

}
