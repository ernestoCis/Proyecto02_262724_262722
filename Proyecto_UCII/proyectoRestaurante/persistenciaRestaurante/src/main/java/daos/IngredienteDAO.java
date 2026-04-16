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
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
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
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al guardar ingrediente", e);
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

            // actualizar productos relacionados al ingrediente
            ProductoDAO.getInstance().actualizarProductosPorIngrediente(ingrediente.getIdIngrediente());

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
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
        } catch (Exception e) {
            throw new PersistenciaException("Erroral buscar por id", e);
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
        } catch (Exception e) {
            throw new PersistenciaException("Erroral obtener ingredientes", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Ingrediente> buscarPorFiltro(String filtro) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Ingrediente> cq = cb.createQuery(Ingrediente.class);
            Root<Ingrediente> root = cq.from(Ingrediente.class);

            List<Predicate> predicates = new ArrayList<>();

            if (filtro != null && !filtro.isBlank()) { 
                String filtroLike = "%" + filtro.toLowerCase() + "%";

                Predicate porNombre = cb.like(
                        cb.lower(root.get("nombre")),
                        filtroLike
                );

                Predicate porUnidad = cb.like(
                        cb.lower(root.get("unidadMedida").as(String.class)),
                        filtroLike
                );

                predicates.add(cb.or(porNombre, porUnidad));
            }

            cq.where(predicates.toArray(new Predicate[0]));

            return em.createQuery(cq).getResultList();

        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar ingredientes", e);
        } finally {
            em.close();
        }
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

        } catch (Exception e) {
            throw new PersistenciaException("Error", e);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean estaEnUso(Long idIngrediente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(r) FROM Receta r "
                    + "WHERE r.ingrediente.idIngrediente = :id",
                    Long.class
            ).setParameter("id", idIngrediente)
                    .getSingleResult();

            return count > 0;

        } catch (Exception e) {
            throw new PersistenciaException("Error al verificar uso del ingrediente", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminar(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();

            Ingrediente ingrediente = em.find(Ingrediente.class, id);

            if (ingrediente != null) {
                em.remove(ingrediente);
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al eliminar ingrediente", e);
        } finally {
            em.close();
        }
    }

}
