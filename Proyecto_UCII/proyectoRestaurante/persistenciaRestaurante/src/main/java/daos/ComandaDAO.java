/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.Comanda;
import entidades.DetallePedido;
import entidades.Producto;
import enums.EstadoComanda;
import excepciones.PersistenciaException;
import interfaces.IComandaDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

/**
 * Clase de Objeto de Acceso a Datos (DAO) para la entidad Comanda. Centraliza
 * las operaciones de persistencia para las órdenes del restaurante, incluyendo
 * cálculos estadísticos de clientes, gestión de estados y filtros temporales.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class ComandaDAO implements IComandaDAO {

    private static ComandaDAO instance;

    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private ComandaDAO() {
    }

    /**
     * Obtiene la instancia única de ComandaDAO.
     *
     * * @return La instancia global de esta clase.
     */
    public static ComandaDAO getInstance() {
        if (instance == null) {
            instance = new ComandaDAO();
        }
        return instance;
    }

    /**
     * Cuenta el número total de comandas asociadas a un cliente específico.
     *
     * * @param idCliente Identificador único del cliente.
     * @return El número total de visitas (comandas) realizadas.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    @Override
    public int contarVisitas(Long idCliente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        Long count = em.createQuery(
                "SELECT COUNT(c) FROM Comanda c WHERE c.cliente.idCliente = :id",
                Long.class
        ).setParameter("id", idCliente)
                .getSingleResult();

        return count.intValue();
    }

    /**
     * Calcula la suma total de los montos de todas las comandas de un cliente.
     *
     * * @param idCliente Identificador único del cliente.
     * @return El monto total gastado por el cliente.
     * @throws PersistenciaException Si ocurre un error en la base de datos.
     */
    @Override
    public double totalGastado(Long idCliente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        Double total = em.createQuery(
                "SELECT COALESCE(SUM(c.total),0) FROM Comanda c WHERE c.cliente.idCliente = :id",
                Double.class
        ).setParameter("id", idCliente)
                .getSingleResult();

        return total;
    }

    /**
     * Registra una nueva comanda en el sistema.
     *
     * * @param comanda El objeto {@link Comanda} a persistir.
     * @return La comanda registrada con su ID generado.
     * @throws PersistenciaException Si la transacción falla.
     */
    @Override
    public Comanda registrarComanda(Comanda comanda) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            em.persist(comanda);
            em.getTransaction().commit();
            return comanda;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al registrar la comanda: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza una comanda existente, gestionando la actualización de sus
     * detalles de pedido y asegurando la integridad de los productos y recetas
     * asociados.
     *
     * * @param comandaParam Objeto con los datos actualizados.
     * @return La comanda actualizada y precargada.
     * @throws PersistenciaException Si la comanda no existe o hay error en la
     * actualización.
     */
    @Override
    public Comanda actualizarComanda(Comanda comandaParam) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();

            Comanda comandaExistente = em.find(Comanda.class, comandaParam.getIdComanda());

            if (comandaExistente == null) {
                throw new PersistenciaException("La comanda no existe.");
            }

            comandaExistente.getDetalles().clear();
            em.flush();

            if (comandaParam.getDetalles() != null) {
                for (DetallePedido nuevoDetalle : comandaParam.getDetalles()) {
                    nuevoDetalle.setComanda(comandaExistente);

                    Producto p = em.find(Producto.class, nuevoDetalle.getProducto().getIdProducto());
                    nuevoDetalle.setProducto(p);

                    comandaExistente.getDetalles().add(nuevoDetalle);
                }
            }

            comandaExistente.setCliente(comandaParam.getCliente());
            comandaExistente.setEstado(comandaParam.getEstado());
            comandaExistente.setTotal(comandaParam.getTotal());

            comandaExistente = em.merge(comandaExistente);
            em.getTransaction().commit();

            for (DetallePedido dp : comandaExistente.getDetalles()) {
                if (dp.getProducto() != null) {
                    dp.getProducto().getRecetas().size();
                }
            }

            return comandaExistente;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al actualizar y precargar comanda", e);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina físicamente una comanda de la base de datos.
     *
     * * @param comanda El objeto comanda a eliminar.
     * @return El objeto eliminado.
     * @throws PersistenciaException Si ocurre un error durante el borrado.
     */
    @Override
    public Comanda eliminarComanda(Comanda comanda) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            Comanda c = em.find(Comanda.class, comanda.getIdComanda());
            if (c != null) {
                em.remove(c);
            }
            em.getTransaction().commit();
            return c;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al eliminar la comanda");
        } finally {
            em.close();
        }
    }

    /**
     * Busca una comanda por su ID e incluye la carga de sus detalles de pedido
     * mediante un FETCH JOIN.
     *
     * * @param idParam Identificador de la comanda.
     * @return La {@link Comanda} encontrada o null si no existe.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    @Override
    public Comanda buscarComandaPorId(Long idParam) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String jpql = "SELECT c FROM Comanda c LEFT JOIN FETCH c.detalles WHERE c.idComanda = :id";

            return em.createQuery(jpql, Comanda.class)
                    .setParameter("id", idParam)
                    .getSingleResult();
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar comanda por ID", e);
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene el identificador más alto registrado en la tabla de comandas.
     *
     * * @return El último ID registrado o 0L si la tabla está vacía.
     */
    @Override
    public Long obtenerUltimoId() {
        EntityManager em = ConexionBD.crearConexion();
        try {
            Long maxId = em.createQuery("SELECT MAX(c.idComanda) FROM Comanda c", Long.class)
                    .getSingleResult();
            return (maxId == null) ? 0L : maxId;
        } catch (Exception e) {
            return 0L;
        } finally {
            em.close();
        }
    }

    /**
     * Localiza la comanda que se encuentra actualmente en estado ABIERTA para
     * una mesa específica.
     *
     * * @param numeroMesa Número de la mesa a consultar.
     * @return La {@link Comanda} abierta encontrada.
     * @throws PersistenciaException Si ocurre un error o no hay comandas
     * abiertas para esa mesa.
     */
    @Override
    public Comanda buscarComandaAbiertaPorMesa(Integer numeroMesa) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String jpql = """
                          SELECT c FROM Comanda c
                          LEFT JOIN FETCH c.detalles d
                          LEFT JOIN FETCH d.producto p
                          WHERE c.mesa.numero = :numMesa AND c.estado = :estadoEnum
                          """;

            TypedQuery<Comanda> query = em.createQuery(jpql, Comanda.class);
            query.setParameter("numMesa", numeroMesa);
            query.setParameter("estadoEnum", EstadoComanda.ABIERTA);

            Comanda comanda = query.getSingleResult();

            if (comanda != null && comanda.getDetalles() != null) {
                em.createQuery("SELECT p FROM Producto p LEFT JOIN FETCH p.recetas WHERE p IN (SELECT d.producto FROM Comanda c JOIN c.detalles d WHERE c = :comanda)", Producto.class)
                        .setParameter("comanda", comanda)
                        .getResultList();
            }

            return comanda;

        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar la comanda abierta para la mesa " + numeroMesa, e);
        } finally {
            em.close();
        }
    }

    /**
     * Recupera la fecha y hora de la última comanda registrada por un cliente.
     *
     * * @param idCliente ID del cliente.
     * @return Objeto {@link LocalDateTime} de la última visita.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    public LocalDateTime obtenerUltimaComanda(Long idCliente) throws PersistenciaException {

        EntityManager em = ConexionBD.crearConexion();

        try {

            return em.createQuery(
                    "SELECT MAX(c.fecha) FROM Comanda c WHERE c.cliente.idCliente = :id",
                    LocalDateTime.class
            )
                    .setParameter("id", idCliente)
                    .getSingleResult();

        } catch (Exception e) {
            throw new PersistenciaException("Error al obtener ultima comanda", e);
        }
    }

    /**
     * Consulta todas las comandas registradas dentro de un periodo de tiempo
     * específico.
     *
     * * @param inicio Fecha inicial del rango.
     * @param fin Fecha final del rango.
     * @return Lista de comandas encontradas en el rango, ordenadas por fecha
     * descendente.
     * @throws PersistenciaException Si ocurre un error en la base de datos.
     */
    @Override
    public List<Comanda> consultarPorRangoFechas(LocalDate inicio, LocalDate fin) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            LocalDateTime fechaInicio = inicio.atStartOfDay();
            LocalDateTime fechaFin = fin.atTime(LocalTime.MAX);

            String jpql = """
                        SELECT c FROM Comanda c
                        WHERE c.fecha >= :inicio AND c.fecha <= :fin
                        ORDER BY c.fecha DESC
                        """;

            TypedQuery<Comanda> query = em.createQuery(jpql, Comanda.class);
            query.setParameter("inicio", fechaInicio);
            query.setParameter("fin", fechaFin);

            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar comandas por rango", e);
        } finally {
            em.close();
        }
    }
}
