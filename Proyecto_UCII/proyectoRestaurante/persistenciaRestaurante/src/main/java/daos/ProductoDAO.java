package daos;

import conexion.ConexionBD;
import entidades.Producto;
import enums.DisponibilidadProducto;
import excepciones.PersistenciaException;
import interfaces.IProductoDAO;
import jakarta.persistence.EntityManager;
import java.util.List;

/**
 * 
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ProductoDAO implements IProductoDAO {

    private static ProductoDAO instance;

    private ProductoDAO() {}

    public static ProductoDAO getInstance() {
        if (instance == null) {
            instance = new ProductoDAO();
        }
        return instance;
    }

    @Override
    public void guardar(Producto producto) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            em.persist(producto); 
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenciaException("Error al guardar producto", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void actualizar(Producto producto) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            em.merge(producto);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenciaException("Error al actualizar producto", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Producto buscarPorId(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            return em.find(Producto.class, id);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Producto> obtenerProductos() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            return em.createQuery(
                "SELECT p FROM Producto p", Producto.class
            ).getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public List<Producto> buscarPorNombre(String nombre) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String jpql = "SELECT p FROM Producto p "
                        + "WHERE LOWER(p.nombre) LIKE LOWER(:nombre)";
            
            return em.createQuery(jpql, Producto.class)
                    .setParameter("nombre", "%" + nombre + "%")
                    .getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public Producto buscarPorNombreExacto(String nombre) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String jpql = "SELECT p FROM Producto p "
                        + "WHERE p.nombre = :nombre "
                        + "AND p.disponibilidad = :estado";

            List<Producto> lista = em.createQuery(jpql, Producto.class)
                    .setParameter("nombre", nombre)
                    .setParameter("estado", DisponibilidadProducto.DISPONIBLE)
                    .getResultList();

            if (lista.isEmpty()) {
                return null;
            }
            return lista.get(0);

        } finally {
            em.close();
        }
    }

    @Override
    public void cambiarDisponibilidad(Long id, DisponibilidadProducto estado) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();

            Producto producto = em.find(Producto.class, id);
            if (producto != null) {
                producto.setDisponibilidad(estado);
                em.merge(producto);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenciaException("Error al cambiar disponibilidad", e);
        } finally {
            em.close();
        }
    }
}