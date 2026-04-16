/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.Mesa;
import excepciones.PersistenciaException;
import interfaces.IMesaDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

/**
 * Clase de Objeto de Acceso a Datos (DAO) para la entidad Mesa. Permite
 * gestionar el registro, consulta y actualización de las mesas del
 * establecimiento, controlando su disponibilidad y numeración mediante JPA.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class MesaDAO implements IMesaDAO {

    private static MesaDAO instance;

    /**
     * Constructor público de la clase.
     */
    public MesaDAO() {
    }

    /**
     * Obtiene la instancia única de MesaDAO siguiendo el patrón Singleton.
     *
     * * @return La instancia global de esta clase.
     */
    public static MesaDAO getInstance() {
        if (instance == null) {
            instance = new MesaDAO();
        }
        return instance;
    }

    /**
     * Registra una nueva mesa en la base de datos.
     *
     * * @param mesa El objeto {@link Mesa} a persistir.
     * @return La mesa registrada con su identificador generado.
     * @throws PersistenciaException Si ocurre un error durante la transacción.
     */
    @Override
    public Mesa registrarMesa(Mesa mesa) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();

            em.persist(mesa);

            em.getTransaction().commit();

            return mesa;

        } catch (Exception e) {
            throw new PersistenciaException("Error al registrar la mesa", e);
        } finally {
            em.close();
        }
    }

    /**
     * Recupera una lista completa de todas las mesas registradas en el sistema.
     *
     * * @return Lista de objetos {@link Mesa}.
     * @throws PersistenciaException Si la consulta JPQL falla.
     */
    @Override
    public List<Mesa> consultarTodas() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String consultaJPQL = "SELECT m FROM Mesa m";
            TypedQuery<Mesa> query = em.createQuery(consultaJPQL, Mesa.class);

            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar las mesas", e);
        } finally {
            em.close();
        }
    }

    /**
     * Busca una mesa específica utilizando su número identificador físico.
     *
     * * @param numeroMesa Número de la mesa a buscar.
     * @return El objeto {@link Mesa} correspondiente o null si no se encuentra.
     * @throws PersistenciaException Si ocurre un error durante la búsqueda.
     */
    @Override
    public Mesa consultarMesaPorNumero(Integer numeroMesa) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String consultaJPQL = "SELECT m FROM Mesa m WHERE numero = :num";
            TypedQuery<Mesa> query = em.createQuery(consultaJPQL, Mesa.class);
            query.setParameter("num", numeroMesa);

            List<Mesa> mesasEncontradas = query.getResultList();

            if (mesasEncontradas.isEmpty()) {
                return null;
            }

            return mesasEncontradas.get(0);

        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar el numero de mesa", e);
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza la información de una mesa, principalmente utilizada para
     * cambiar su estado de disponibilidad (Libre/Ocupada).
     *
     * * @param mesa Objeto mesa con los datos actualizados.
     * @return El objeto {@link Mesa} tras ser sincronizado con la base de
     * datos.
     * @throws PersistenciaException Si falla la actualización o se requiere
     * rollback.
     */
    @Override
    public Mesa actualizarMesa(Mesa mesa) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            Mesa actualizada = em.merge(mesa);
            em.getTransaction().commit();
            return actualizada;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al cambiar el estado de la mesa");
        } finally {
            em.close();
        }
    }

}
