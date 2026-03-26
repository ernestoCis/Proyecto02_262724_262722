/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.ClienteFrecuente;
import excepciones.PersistenciaException;
import interfaces.IClienteFrecuenteDAO;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ClienteFrecuenteDAO implements IClienteFrecuenteDAO {

    @Override
    public List<ClienteFrecuente> obtenerFrecuentes() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        return em.createQuery(
                "SELECT c FROM ClienteFrecuente c", ClienteFrecuente.class
        ).getResultList();
    }

    @Override
    public List<ClienteFrecuente> buscarPorFiltro(String filtro) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        return em.createQuery(
                "SELECT c FROM ClienteFrecuente c " + "WHERE c.nombres LIKE :filtro "
                + "OR c.telefono LIKE :filtro "
                + "OR c.email LIKE :filtro", ClienteFrecuente.class)
                .setParameter("filtro", "%" + filtro + "%")
                .getResultList();
    }

    @Override
    public void guardar(ClienteFrecuente cliente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        em.getTransaction().begin();
        em.persist(cliente);
        em.getTransaction().commit();

        em.close();
    }

    @Override
    public ClienteFrecuente buscarPorId(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        return em.find(ClienteFrecuente.class, id);
    }

    public void actualizar(ClienteFrecuente cliente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenciaException("Error al actualizar cliente", e);
        } finally {
            em.close();
        }
    }
}