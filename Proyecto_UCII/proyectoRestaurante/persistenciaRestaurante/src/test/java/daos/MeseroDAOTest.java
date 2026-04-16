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
 * Clase de pruebas unitarias para MeseroDAO. Valida el registro de personal, la
 * recuperación de perfiles para autenticación y las restricciones de integridad
 * como la duplicidad de nombres de usuario.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class MeseroDAOTest {

    private MeseroDAO dao;
    private List<Long> idsParaLimpiar;

    /**
     * Configuración previa a cada test. Inicializa el acceso a datos y la lista
     * de rastreo para limpieza selectiva.
     */
    @BeforeEach
    public void setUp() {
        dao = MeseroDAO.getInstance();
        idsParaLimpiar = new ArrayList<>();
    }

    /**
     * Limpieza de la base de datos tras cada prueba. Elimina los registros de
     * meseros creados durante la ejecución para evitar colisiones de RFC o
     * nombres de usuario en pruebas posteriores.
     */
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

    /**
     * Método auxiliar para instanciar objetos Mesero con datos válidos.
     * Facilita la creación de escenarios de prueba consistentes.
     *
     * * @param usuario Nombre de usuario para login.
     * @param rfc Identificación fiscal del empleado.
     * @return Un objeto {@link Mesero} preparado para persistencia.
     */
    private Mesero crearMeseroValido(String usuario, String rfc) {
        Mesero m = new Mesero();
        m.setUsuario(usuario);
        m.setRfc(rfc);
        m.setNombres("Mesero Test");
        m.setApellidoPaterno("Apellido");
        return m;
    }

    /**
     * Prueba el flujo básico de registro de un mesero.
     *
     * @throws PersistenciaException Si ocurre un error en la transacción.
     */
    @Test
    public void testRegistrarMesero_FlujoBase() throws PersistenciaException {
        Mesero mesero = crearMeseroValido("mesero1", "RFC123456789");
        Mesero guardado = dao.registrarMesero(mesero);

        if (guardado != null) {
            idsParaLimpiar.add(guardado.getIdEmpleado());
        }

        assertNotNull(guardado.getIdEmpleado(), "El empleado debe tener un ID tras el registro");
        assertEquals("mesero1", guardado.getUsuario());
    }

    /**
     * Valida la recuperación de un perfil de mesero mediante su identificador
     * de usuario. Esencial para el módulo de Login.
     *
     * @throws PersistenciaException Si falla la consulta.
     */
    @Test
    public void testConsultarMeseroPorUsuario_Existente() throws PersistenciaException {
        String user = "usuario_test";
        Mesero m = crearMeseroValido(user, "RFC999");
        Mesero guardado = dao.registrarMesero(m);
        idsParaLimpiar.add(guardado.getIdEmpleado());

        Mesero encontrado = dao.consultarMeseroPorUsuario(user);

        assertNotNull(encontrado, "Debe encontrar al mesero por su nombre de usuario");
        assertEquals(user, encontrado.getUsuario());
        assertEquals(guardado.getIdEmpleado(), encontrado.getIdEmpleado());
    }

    /**
     * Verifica que la búsqueda de un usuario inexistente no provoque errores y
     * devuelva null.
     *
     * @throws PersistenciaException Si ocurre un error inesperado.
     */
    @Test
    public void testConsultarMeseroPorUsuario_Inexistente() throws PersistenciaException {
        Mesero encontrado = dao.consultarMeseroPorUsuario("usuario_falso_123");
        assertNull(encontrado, "No debe devolver resultados para un usuario que no existe");
    }

    /**
     * Prueba la obtención de la lista completa de colaboradores.
     *
     * @throws PersistenciaException Si falla la consulta masiva.
     */
    @Test
    public void testConsultarTodos() throws PersistenciaException {
        Mesero m1 = crearMeseroValido("u1", "RFC1");
        Mesero m2 = crearMeseroValido("u2", "RFC2");

        idsParaLimpiar.add(dao.registrarMesero(m1).getIdEmpleado());
        idsParaLimpiar.add(dao.registrarMesero(m2).getIdEmpleado());

        List<Mesero> lista = dao.consultarTodos();
        assertTrue(lista.size() >= 2, "La lista debe contener al menos los meseros registrados en el test");
    }

    /**
     * Verifica la restricción de unicidad del nombre de usuario. El sistema
     * debe impedir que dos empleados compartan la misma credencial de acceso.
     */
    @Test
    public void testRegistrarMesero_UsuarioDuplicado() throws PersistenciaException {
        String user = "repetido";
        Mesero m1 = crearMeseroValido(user, "RFC_A");
        Mesero guardado = dao.registrarMesero(m1);
        idsParaLimpiar.add(guardado.getIdEmpleado());

        Mesero m2 = crearMeseroValido(user, "RFC_B");

        assertThrows(PersistenciaException.class, () -> {
            dao.registrarMesero(m2);
        }, "Debe lanzar PersistenciaException por violación de restricción de unicidad");
    }
}
