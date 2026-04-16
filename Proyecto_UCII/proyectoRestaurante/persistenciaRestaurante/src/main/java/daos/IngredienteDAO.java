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
 * Clase de Objeto de Acceso a Datos (DAO) para la entidad Ingrediente.
 * Proporciona métodos para gestionar el catálogo de insumos del sistema,
 * incluyendo búsquedas avanzadas mediante Criteria API y validaciones de
 * integridad.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class IngredienteDAO implements IIngredienteDAO {

    private static IngredienteDAO instance;

    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private IngredienteDAO() {
    }

    /**
     * Obtiene la instancia única de IngredienteDAO.
     *
     * * @return La instancia global de esta clase.
     */
    public static IngredienteDAO getInstance() {
        if (instance == null) {
            instance = new IngredienteDAO();
        }
        return instance;
    }

    /**
     * Persiste un nuevo ingrediente en la base de datos.
     *
     * * @param ingrediente El objeto {@link Ingrediente} a registrar.
     * @throws PersistenciaException Si ocurre un error durante la escritura.
     */
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

    /**
     * Actualiza la información de un ingrediente y dispara la actualización de
     * los productos relacionados para mantener la consistencia del sistema.
     *
     * * @param ingrediente Objeto con los nuevos datos.
     * @throws PersistenciaException Si la transacción falla o no se pueden
     * actualizar los productos.
     */
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

    /**
     * Busca un ingrediente por su identificador único.
     *
     * * @param id Identificador del ingrediente.
     * @return El objeto {@link Ingrediente} encontrado o null si no existe.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
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

    /**
     * Recupera la lista completa de ingredientes registrados en el sistema.
     *
     * * @return Lista de todos los objetos {@link Ingrediente}.
     * @throws PersistenciaException Si la consulta falla.
     */
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

    /**
     * Realiza una búsqueda filtrada utilizando Criteria API para nombres o
     * unidades de medida. Permite búsquedas dinámicas y seguras contra
     * inyección de código.
     *
     * * @param filtro Cadena de texto para filtrar los resultados.
     * @return Lista de ingredientes que coinciden con el filtro.
     * @throws PersistenciaException Si ocurre un error al construir o ejecutar
     * la consulta.
     */
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

    /**
     * Busca un ingrediente específico que coincida exactamente con el nombre y
     * la unidad de medida.
     *
     * * @param nombre Nombre del ingrediente.
     * @param unidad Objeto {@link UnidadMedida} asociado.
     * @return El {@link Ingrediente} encontrado o null si no hay coincidencias.
     * @throws PersistenciaException Si la consulta falla.
     */
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

    /**
     * Verifica si un ingrediente está siendo utilizado en alguna receta
     * registrada.
     *
     * * @param idIngrediente Identificador del ingrediente a verificar.
     * @return true si el ingrediente tiene dependencias en la tabla de recetas,
     * false de lo contrario.
     * @throws PersistenciaException Si ocurre un error al contar las
     * referencias.
     */
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

    /**
     * Elimina permanentemente un ingrediente del sistema mediante su ID.
     *
     * * @param id Identificador del ingrediente a eliminar.
     * @throws PersistenciaException Si ocurre un error o el objeto no existe.
     */
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
