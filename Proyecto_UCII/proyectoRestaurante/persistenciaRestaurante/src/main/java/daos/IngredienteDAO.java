/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daos;

import conexion.ConexionBD;
import entidades.Ingrediente;
import enums.UnidadMedida;
import excepciones.PersistenciaException;
import interfaces.IIngredienteDAO;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class IngredienteDAO implements IIngredienteDAO {

    private static IngredienteDAO instance;

    private IngredienteDAO() {
    }

    public static IngredienteDAO getInstance() {
        if (instance == null) {
            instance = new IngredienteDAO();
        }
        return instance;
    }

    @Override
    public void guardar(Ingrediente ingrediente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            em.persist(ingrediente);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizar(Ingrediente ingrediente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            em.merge(ingrediente);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenciaException("Error al actualizar ingrediente", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Ingrediente buscarPorId(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            return em.find(Ingrediente.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Ingrediente> obtenerIngredientes() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            return em.createQuery(
                    "SELECT i FROM Ingrediente i", Ingrediente.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Ingrediente> buscarPorFiltro(String filtro) throws PersistenciaException {
        return null;
    }

    @Override
    public Ingrediente buscarPorNombreYUnidad(String nombre, UnidadMedida unidad) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            String jpql = "SELECT i FROM Ingrediente i "
                    + "WHERE i.nombre = :nombre "
                    + "AND i.unidadMedida = :unidad";

            List<Ingrediente> lista = em.createQuery(jpql, Ingrediente.class)
                    .setParameter("nombre", nombre)
                    .setParameter("unidad", unidad)
                    .getResultList();

            if (lista.isEmpty()) {
                return null;
            }

            return lista.get(0);

        } finally {
            em.close();
        }
    }
}