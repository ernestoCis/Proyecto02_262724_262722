package daos;

import conexion.ConexionBD;
import entidades.Ingrediente;
import entidades.Producto;
import enums.DisponibilidadProducto;
import enums.EstadoComanda;
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

    private ProductoDAO() {
    }

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
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
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
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al actualizar producto", e);
        } finally {
            em.close();
        }
    }

    @Override
    public Producto buscarPorId(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            return em.createQuery(
                    "SELECT p FROM Producto p "
                    + "LEFT JOIN FETCH p.recetas r "
                    + "LEFT JOIN FETCH r.ingrediente "
                    + "WHERE p.idProducto = :id",
                    Producto.class
            ).setParameter("id", id)
                    .getSingleResult();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al buscar por id", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Producto> obtenerProductos() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            return em.createQuery(
                    "SELECT DISTINCT p FROM Producto p "
                    + "LEFT JOIN FETCH p.recetas r "
                    + "LEFT JOIN FETCH r.ingrediente",
                    Producto.class
            ).getResultList();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al obtener los productos", e);
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
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al buscar pro nombre", e);
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

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al consultar por nombre exacto", e);
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
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al cambiar disponibilidad", e);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean estaEnUso(Long idProducto) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(d) FROM DetallePedido d "
                    + "WHERE d.producto.idProducto = :id",
                    Long.class
            ).setParameter("id", idProducto)
                    .getSingleResult();

            return count > 0;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al verificar uso del producto", e);
        } finally {
            em.close();
        }
    }

    @Override
    public List<Producto> obtenerProductosDisponibles() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            return em.createQuery(
                    "SELECT DISTINCT p FROM Producto p "
                    + "LEFT JOIN FETCH p.recetas r "
                    + "LEFT JOIN FETCH r.ingrediente "
                    + "WHERE p.disponibilidad = :disp",
                    Producto.class
            ).setParameter("disp", DisponibilidadProducto.DISPONIBLE).getResultList();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al obtener los productos", e);
        } finally {
            em.close();
        }
    }

    @Override
    public void eliminar(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();

            Producto producto = em.find(Producto.class, id);

            if (producto != null) {
                em.remove(producto);
            }

            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al eliminar producto", e);
        } finally {
            em.close();
        }
    }

    @Override
    public boolean estaEnComandaAbierta(Long idProducto) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            Long count = em.createQuery(
                    "SELECT COUNT(d) FROM DetallePedido d "
                    + "JOIN d.comanda c "
                    + "WHERE d.producto.idProducto = :id "
                    + "AND c.estado = :estado",
                    Long.class
            )
                    .setParameter("id", idProducto)
                    .setParameter("estado", EstadoComanda.ABIERTA)
                    .getSingleResult();

            return count > 0;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al verificar si el producto está en comanda abierta", e);
        } finally {
            em.close();
        }
    }

    public void actualizarProductosPorIngrediente(Long idIngrediente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {

            List<Long> idsProductos = em.createQuery(
                    "SELECT DISTINCT r.producto.idProducto "
                    + "FROM Receta r "
                    + "WHERE r.ingrediente.idIngrediente = :id",
                    Long.class
            )
                    .setParameter("id", idIngrediente)
                    .getResultList();

            for (Long idProducto : idsProductos) {
                actualizarDisponibilidadPorStock(idProducto);
            }

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al actualizar productos por ingrediente", e);
        } finally {
            em.close();
        }
    }

    public void actualizarDisponibilidadPorStock(Long idProducto) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();

            Producto producto = em.find(Producto.class, idProducto);

            Long faltantes = em.createQuery(
                    "SELECT COUNT(r) FROM Receta r "
                    + "WHERE r.producto.idProducto = :id "
                    + "AND r.ingrediente.cantidadActual < r.cantidad",
                    Long.class
            )
                    .setParameter("id", idProducto)
                    .getSingleResult();

            if (faltantes > 0) {
                producto.setDisponibilidad(DisponibilidadProducto.NO_DISPONIBLE);
            } else {
                producto.setDisponibilidad(DisponibilidadProducto.DISPONIBLE);
            }

            em.merge(producto);
            em.getTransaction().commit();

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al actualizar disponibilidad", e);
        } finally {
            em.close();
        }
    }
}
