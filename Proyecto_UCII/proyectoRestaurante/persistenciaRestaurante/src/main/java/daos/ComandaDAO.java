/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.Comanda;
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
 *
 * @author Paulina Guevara, Ernesto Cisneros 
 */
public class ComandaDAO implements IComandaDAO {

    private static ComandaDAO instance;

    private ComandaDAO() {
    }

    public static ComandaDAO getInstance() {
        if (instance == null) {
            instance = new ComandaDAO();
        }
        return instance;
    }
    
    @Override
    public int contarVisitas(Long idCliente) throws PersistenciaException  {
        EntityManager em = ConexionBD.crearConexion();

        Long count = em.createQuery(
                "SELECT COUNT(c) FROM Comanda c WHERE c.cliente.idCliente = :id",
                Long.class
        ).setParameter("id", idCliente)
                .getSingleResult();

        return count.intValue();
    }

    @Override
    public double totalGastado(Long idCliente)  throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        Double total = em.createQuery(
                "SELECT COALESCE(SUM(c.total),0) FROM Comanda c WHERE c.cliente.idCliente = :id",
                Double.class
        ).setParameter("id", idCliente)
                .getSingleResult();

        return total;
    }
    
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
    
    public Comanda actualizarComanda(Comanda comanda) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            // merge es el que se encarga de insertar detalles nuevos o actualizar existentes
            Comanda actualizada = em.merge(comanda);
            em.getTransaction().commit();
            return actualizada;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al actualizar la comanda");
        } finally {
            em.close();
        }
    }
    
    public Comanda eliminarComanda(Comanda comanda) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            // Buscamos la comanda antes de eliminar para que esté en contexto
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
    
    public Comanda buscarComandaPorId(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            return em.find(Comanda.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar comanda por ID");
        } finally {
            em.close();
        }
    }

    @Override
    public Long obtenerUltimoId(){
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

    @Override
    public Comanda buscarComandaAbiertaPorMesa(Integer numeroMesa) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String jpql = "SELECT c FROM Comanda c LEFT JOIN FETCH c.detalles WHERE c.mesa.numero = :numMesa AND c.estado = :estadoEnum";

            TypedQuery<Comanda> query = em.createQuery(jpql, Comanda.class);
            query.setParameter("numMesa", numeroMesa);
            query.setParameter("estadoEnum", EstadoComanda.ABIERTA);

            return query.getSingleResult();
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar la comanda abierta para la mesa " + numeroMesa, e);
        } finally {
            em.close();
        }
    }
    
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

    @Override
    public List<Comanda> consultarPorRangoFechas(LocalDate inicio, LocalDate fin) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            //convertimos LocalDate a LocalDateTime para cubrir el día completo
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