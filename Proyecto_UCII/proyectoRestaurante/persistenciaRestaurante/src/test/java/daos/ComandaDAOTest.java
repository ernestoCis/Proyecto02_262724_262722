/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.ClienteFrecuente;
import entidades.Comanda;
import entidades.Mesa;
import entidades.Mesero;
import enums.EstadoComanda;
import enums.EstadoMesa;
import excepciones.PersistenciaException;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de pruebas unitarias para ComandaDAO. Valida el ciclo de vida de los
 * pedidos, la gestión de estados de mesas y la precisión de las estadísticas de
 * consumo por cliente y rangos de fechas.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class ComandaDAOTest {

    private ComandaDAO dao;
    private List<Long> idsComandas;
    private List<Long> idsClientes;
    private List<Long> idsMesas;
    private List<Long> idsMeseros;

    /**
     * Inicializa las instancias necesarias y las listas de seguimiento de IDs
     * antes de cada ejecución de prueba.
     */
    @BeforeEach
    public void setUp() {
        dao = ComandaDAO.getInstance();
        idsComandas = new ArrayList<>();
        idsClientes = new ArrayList<>();
        idsMesas = new ArrayList<>();
        idsMeseros = new ArrayList<>();
    }

    /**
     * Realiza una limpieza exhaustiva en la base de datos después de cada
     * prueba. Elimina los registros creados en orden inverso de jerarquía
     * (Comanda primero, luego sus dependencias) para evitar errores de
     * integridad referencial.
     */
    @AfterEach
    public void tearDown() {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            // Eliminación controlada de registros de prueba
            for (Long id : idsComandas) {
                Comanda c = em.find(Comanda.class, id);
                if (c != null) {
                    em.remove(c);
                }
            }
            for (Long id : idsClientes) {
                ClienteFrecuente c = em.find(ClienteFrecuente.class, id);
                if (c != null) {
                    em.remove(c);
                }
            }
            for (Long id : idsMesas) {
                Mesa m = em.find(Mesa.class, id);
                if (m != null) {
                    em.remove(m);
                }
            }
            for (Long id : idsMeseros) {
                Mesero m = em.find(Mesero.class, id);
                if (m != null) {
                    em.remove(m);
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
     * Método auxiliar para generar un escenario de prueba completo. Crea y
     * persiste las entidades necesarias (Cliente, Mesa, Mesero) para poder
     * instanciar una Comanda válida.
     *
     * * @return Una instancia de {@link Comanda} lista para ser procesada.
     * @throws PersistenciaException Si falla la creación de las dependencias.
     */
    private Comanda crearComandaEscenario() throws PersistenciaException {
        ClienteFrecuente c = new ClienteFrecuente();
        c.setNombres("Cliente Test");
        c.setTelefono("0000000000");
        ClienteFrecuente guardadoC = ClienteFrecuenteDAO.getInstance().guardar(c);
        idsClientes.add(guardadoC.getIdCliente());

        Mesa m = new Mesa();
        int numeroUnico = (int) (System.currentTimeMillis() % 100000);
        m.setNumero(numeroUnico);
        m.setDisponibilidad(EstadoMesa.NO_DISPONIBLE);
        Mesa guardadaM = MesaDAO.getInstance().registrarMesa(m);
        idsMesas.add(guardadaM.getIdMesa());

        Mesero ms = new Mesero();
        ms.setUsuario("test_user_" + System.currentTimeMillis());
        ms.setRfc("RFC" + System.currentTimeMillis());
        Mesero guardadoMs = MeseroDAO.getInstance().registrarMesero(ms);
        idsMeseros.add(guardadoMs.getIdEmpleado());

        Comanda comanda = new Comanda();
        comanda.setFolio("OB-" + System.currentTimeMillis());
        comanda.setCliente(guardadoC);
        comanda.setMesa(guardadaM);
        comanda.setMesero(guardadoMs);
        comanda.setFecha(LocalDateTime.now());
        comanda.setTotal(150.0);
        comanda.setEstado(EstadoComanda.ABIERTA);
        return comanda;
    }

    /**
     * Prueba el flujo básico de registro de una comanda.
     */
    @Test
    public void testRegistrarComanda_FlujoBase() throws PersistenciaException {
        Comanda nueva = crearComandaEscenario();
        Comanda guardada = dao.registrarComanda(nueva);
        assertNotNull(guardada.getIdComanda());
        idsComandas.add(guardada.getIdComanda());
    }

    /**
     * Valida que el conteo de visitas por cliente funcione correctamente.
     */
    @Test
    public void testContarVisitas() throws PersistenciaException {
        Comanda c1 = crearComandaEscenario();
        Comanda g1 = dao.registrarComanda(c1);
        idsComandas.add(g1.getIdComanda());
        int visitas = dao.contarVisitas(c1.getCliente().getIdCliente());
        assertEquals(1, visitas);
    }

    /**
     * Verifica que el cálculo del total gastado por un cliente sea exacto.
     */
    @Test
    public void testTotalGastado() throws PersistenciaException {
        Comanda c1 = crearComandaEscenario();
        c1.setTotal(200.0);
        Comanda g1 = dao.registrarComanda(c1);
        idsComandas.add(g1.getIdComanda());
        double total = dao.totalGastado(c1.getCliente().getIdCliente());
        assertEquals(200.0, total, 0.1);
    }

    /**
     * Prueba la búsqueda de comandas activas filtradas por el número de mesa.
     */
    @Test
    public void testBuscarComandaAbiertaPorMesa() throws PersistenciaException {
        Comanda c1 = crearComandaEscenario();
        c1.setEstado(EstadoComanda.ABIERTA);
        Comanda g1 = dao.registrarComanda(c1);
        idsComandas.add(g1.getIdComanda());
        Comanda encontrada = dao.buscarComandaAbiertaPorMesa(c1.getMesa().getNumero());
        assertNotNull(encontrada);
        assertEquals(EstadoComanda.ABIERTA, encontrada.getEstado());
    }

    /**
     * Valida la recuperación de comandas dentro de un rango de fechas
     * específico.
     */
    @Test
    public void testConsultarPorRangoFechas() throws PersistenciaException {
        Comanda c1 = crearComandaEscenario();
        Comanda g1 = dao.registrarComanda(c1);
        idsComandas.add(g1.getIdComanda());
        LocalDate hoy = LocalDate.now();
        List<Comanda> lista = dao.consultarPorRangoFechas(hoy, hoy);
        assertFalse(lista.isEmpty());
    }

    /**
     * Verifica que el sistema lance una excepción al intentar registrar una
     * comanda nula.
     */
    @Test
    public void testRegistrarComanda_Nula() {
        assertThrows(PersistenciaException.class, () -> {
            dao.registrarComanda(null);
        });
    }

    /**
     * Comprueba que se maneje correctamente la búsqueda en una mesa sin
     * comandas abiertas.
     */
    @Test
    public void testBuscarComandaAbiertaPorMesa_NoExiste() {
        int numeroMesaInexistente = 999;
        assertThrows(PersistenciaException.class, () -> {
            dao.buscarComandaAbiertaPorMesa(numeroMesaInexistente);
        });
    }

    /**
     * Valida que el sistema devuelva cero visitas para un cliente inexistente.
     */
    @Test
    public void testContarVisitas_ClienteInexistente() throws PersistenciaException {
        int visitas = dao.contarVisitas(-1L);
        assertEquals(0, visitas);
    }

    /**
     * Valida que el sistema devuelva un gasto nulo para un cliente inexistente.
     */
    @Test
    public void testTotalGastado_ClienteInexistente() throws PersistenciaException {
        double total = dao.totalGastado(-1L);
        assertEquals(0.0, total);
    }

    /**
     * Verifica que la consulta por fechas devuelva una lista vacía cuando no
     * hay registros.
     */
    @Test
    public void testConsultarPorRangoFechas_SinResultados() throws PersistenciaException {
        LocalDate fechaAntigua = LocalDate.of(1990, 1, 1);
        List<Comanda> lista = dao.consultarPorRangoFechas(fechaAntigua, fechaAntigua);
        assertNotNull(lista);
        assertTrue(lista.isEmpty());
    }
}
