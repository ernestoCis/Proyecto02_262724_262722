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
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ComandaDAOTest {

    private ComandaDAO dao;
    private List<Long> idsComandas;
    private List<Long> idsClientes;
    private List<Long> idsMesas;
    private List<Long> idsMeseros;

    @BeforeEach
    public void setUp() {
        dao = ComandaDAO.getInstance();
        idsComandas = new ArrayList<>();
        idsClientes = new ArrayList<>();
        idsMesas = new ArrayList<>();
        idsMeseros = new ArrayList<>();
    }

    @AfterEach
    public void tearDown() {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();

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

    @Test
    public void testRegistrarComanda_FlujoBase() throws PersistenciaException {
        Comanda nueva = crearComandaEscenario();
        Comanda guardada = dao.registrarComanda(nueva);

        assertNotNull(guardada.getIdComanda());
        idsComandas.add(guardada.getIdComanda());
    }

    @Test
    public void testContarVisitas() throws PersistenciaException {
        Comanda c1 = crearComandaEscenario();
        Comanda g1 = dao.registrarComanda(c1);
        idsComandas.add(g1.getIdComanda());

        int visitas = dao.contarVisitas(c1.getCliente().getIdCliente());

        assertEquals(1, visitas);
    }

    @Test
    public void testTotalGastado() throws PersistenciaException {
        Comanda c1 = crearComandaEscenario();
        c1.setTotal(200.0);
        Comanda g1 = dao.registrarComanda(c1);
        idsComandas.add(g1.getIdComanda());

        double total = dao.totalGastado(c1.getCliente().getIdCliente());

        assertEquals(200.0, total, 0.1);
    }

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

    @Test
    public void testConsultarPorRangoFechas() throws PersistenciaException {
        Comanda c1 = crearComandaEscenario();
        Comanda g1 = dao.registrarComanda(c1);
        idsComandas.add(g1.getIdComanda());

        LocalDate hoy = LocalDate.now();
        List<Comanda> lista = dao.consultarPorRangoFechas(hoy, hoy);

        assertFalse(lista.isEmpty());
    }

    @Test
    public void testRegistrarComanda_Nula() {
        assertThrows(PersistenciaException.class, () -> {
            dao.registrarComanda(null);
        });
    }

    @Test
    public void testBuscarComandaAbiertaPorMesa_NoExiste() {
        int numeroMesaInexistente = 999;

        assertThrows(PersistenciaException.class, () -> {
            dao.buscarComandaAbiertaPorMesa(numeroMesaInexistente);
        });
    }

    @Test
    public void testContarVisitas_ClienteInexistente() throws PersistenciaException {
        int visitas = dao.contarVisitas(-1L); //id que no existe
        assertEquals(0, visitas);
    }

    @Test
    public void testTotalGastado_ClienteInexistente() throws PersistenciaException {
        double total = dao.totalGastado(-1L);
        assertEquals(0.0, total);
    }

    @Test
    public void testConsultarPorRangoFechas_SinResultados() throws PersistenciaException {
        LocalDate fechaAntigua = LocalDate.of(1990, 1, 1);

        List<Comanda> lista = dao.consultarPorRangoFechas(fechaAntigua, fechaAntigua);

        assertNotNull(lista);
        assertTrue(lista.isEmpty());
    }
}
