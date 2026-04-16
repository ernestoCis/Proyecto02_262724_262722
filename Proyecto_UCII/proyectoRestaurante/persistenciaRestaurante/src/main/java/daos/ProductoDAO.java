package daos;

import conexion.ConexionBD;
import entidades.Ingrediente;
import entidades.Producto;
import enums.DisponibilidadProducto;
import enums.EstadoComanda;
import excepciones.PersistenciaException;
import interfaces.IProductoDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de Objeto de Acceso a Datos (DAO) para la entidad Producto. Gestiona el
 * catálogo de productos del restaurante, incluyendo la lógica compleja de
 * actualización de disponibilidad basada en las existencias de ingredientes
 * definidos en las recetas.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class ProductoDAO implements IProductoDAO {

    private static ProductoDAO instance;

    /**
     * Constructor privado para implementar el patrón Singleton.
     */
    private ProductoDAO() {
    }

    /**
     * Obtiene la instancia única de ProductoDAO.
     *
     * * @return La instancia global de esta clase.
     */
    public static ProductoDAO getInstance() {
        if (instance == null) {
            instance = new ProductoDAO();
        }
        return instance;
    }

    /**
     * Persiste un nuevo producto en la base de datos.
     *
     * * @param producto El objeto {@link Producto} a guardar.
     * @throws PersistenciaException Si ocurre un error en la transacción.
     */
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

    /**
     * Actualiza la información de un producto existente.
     *
     * * @param producto Objeto con los datos actualizados.
     * @throws PersistenciaException Si falla la actualización.
     */
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

    /**
     * Busca un producto por su ID, cargando de forma ansiosa (Eager) sus
     * recetas e ingredientes asociados para evitar errores de sesión.
     *
     * * @param id Identificador del producto.
     * @return El {@link Producto} con sus relaciones cargadas.
     * @throws PersistenciaException Si el producto no se encuentra o hay error
     * en la consulta.
     */
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
            throw new PersistenciaException("Error al buscar por id", e);
        } finally {
            em.close();
        }
    }

    /**
     * Recupera todos los productos registrados, incluyendo sus recetas e
     * ingredientes.
     *
     * * @return Lista de todos los productos.
     * @throws PersistenciaException Si ocurre un error en la base de datos.
     */
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
            throw new PersistenciaException("Error al obtener los productos", e);
        } finally {
            em.close();
        }
    }

    /**
     * Busca productos cuyo nombre coincida parcialmente con el criterio
     * proporcionado.
     *
     * * @param nombre Cadena de texto a buscar.
     * @return Lista de productos que coinciden con el nombre.
     * @throws PersistenciaException Si la consulta falla.
     */
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
            throw new PersistenciaException("Error al buscar pro nombre", e);
        } finally {
            em.close();
        }
    }

    /**
     * Busca un producto por nombre exacto que se encuentre actualmente
     * disponible.
     *
     * * @param nombre Nombre exacto del producto.
     * @return El {@link Producto} encontrado o null si no está disponible o no
     * existe.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
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
            throw new PersistenciaException("Error al consultar por nombre exacto", e);
        } finally {
            em.close();
        }
    }

    /**
     * Cambia manualmente el estado de disponibilidad de un producto.
     *
     * * @param id ID del producto.
     * @param estado Nuevo estado de {@link DisponibilidadProducto}.
     * @throws PersistenciaException Si el producto no existe o falla la
     * actualización.
     */
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

    /**
     * Verifica si un producto ha sido incluido en algún pedido (DetallePedido).
     *
     * * @param idProducto ID del producto a verificar.
     * @return true si existen registros asociados, false de lo contrario.
     * @throws PersistenciaException Si ocurre un error al contar los registros.
     */
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
            throw new PersistenciaException("Error al verificar uso del producto", e);
        } finally {
            em.close();
        }
    }

    /**
     * Recupera únicamente los productos marcados como disponibles.
     *
     * * @return Lista de productos disponibles con sus recetas cargadas.
     * @throws PersistenciaException Si la consulta falla.
     */
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
            throw new PersistenciaException("Error al obtener los productos", e);
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un producto de la base de datos.
     *
     * * @param id ID del producto a eliminar.
     * @throws PersistenciaException Si ocurre un error durante el borrado.
     */
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

    /**
     * Comprueba si un producto forma parte de alguna comanda que aún no ha sido
     * cerrada.
     *
     * * @param idProducto ID del producto.
     * @return true si está en una comanda abierta, false en caso contrario.
     * @throws PersistenciaException Si ocurre un error en la verificación.
     */
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
            throw new PersistenciaException("Error al verificar si el producto está en comanda abierta", e);
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza la disponibilidad de todos los productos que utilizan un
     * ingrediente específico. Útil cuando se modifica el stock de un
     * ingrediente.
     *
     * * @param idIngrediente ID del ingrediente modificado.
     * @throws PersistenciaException Si falla la actualización masiva.
     */
    @Override
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
            throw new PersistenciaException("Error al actualizar productos por ingrediente", e);
        } finally {
            em.close();
        }
    }

    /**
     * Evalúa si un producto tiene suficientes ingredientes en stock para ser
     * preparado. Si algún ingrediente de la receta es insuficiente, marca el
     * producto como NO_DISPONIBLE.
     *
     * * @param idProducto ID del producto a evaluar.
     * @throws PersistenciaException Si ocurre un error al consultar el stock o
     * actualizar el estado.
     */
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

            DisponibilidadProducto nuevoEstado = (faltantes > 0)
                    ? DisponibilidadProducto.NO_DISPONIBLE
                    : DisponibilidadProducto.DISPONIBLE;

            em.createQuery("UPDATE Producto p SET p.disponibilidad = :estado WHERE p.idProducto = :id")
                    .setParameter("estado", nuevoEstado)
                    .setParameter("id", idProducto)
                    .executeUpdate();

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

    /**
     * Consulta productos aplicando un filtro dinámico sobre nombre o tipo
     * utilizando Criteria API.
     *
     * * @param filtro Texto para filtrar productos.
     * @return Lista de productos filtrados.
     * @throws PersistenciaException Si ocurre un error en la construcción de la
     * consulta.
     */
    @Override
    public List<Producto> consultarProductosConFiltro(String filtro) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Producto> cq = cb.createQuery(Producto.class);
            Root<Producto> producto = cq.from(Producto.class);

            producto.fetch("recetas", jakarta.persistence.criteria.JoinType.LEFT)
                    .fetch("ingrediente", jakarta.persistence.criteria.JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<>();

            if (filtro != null && !filtro.trim().isEmpty()) {
                String pattern = "%" + filtro.toLowerCase() + "%";

                Predicate nombreLike = cb.like(cb.lower(producto.get("nombre")), pattern);

                Predicate tipoLike = cb.like(cb.lower(producto.get("tipo").as(String.class)), pattern);

                predicates.add(cb.or(nombreLike, tipoLike));
            }

            cq.select(producto).distinct(true).where(predicates.toArray(new Predicate[0]));

            return em.createQuery(cq).getResultList();

        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar productos con filtro", e);
        } finally {
            em.close();
        }
    }

    /**
     * Consulta únicamente productos disponibles aplicando un filtro dinámico.
     *
     * * @param filtro Texto para filtrar los productos disponibles.
     * @return Lista de productos disponibles que coinciden con el filtro.
     * @throws PersistenciaException Si ocurre un error en la base de datos.
     */
    @Override
    public List<Producto> consultarProductosDisponiblesConFiltro(String filtro) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Producto> cq = cb.createQuery(Producto.class);
            Root<Producto> producto = cq.from(Producto.class);

            producto.fetch("recetas", jakarta.persistence.criteria.JoinType.LEFT)
                    .fetch("ingrediente", jakarta.persistence.criteria.JoinType.LEFT);

            List<Predicate> predicates = new ArrayList<>();

            Predicate soloDisponibles = cb.equal(producto.get("disponibilidad"), DisponibilidadProducto.DISPONIBLE);
            predicates.add(soloDisponibles);

            if (filtro != null && !filtro.trim().isEmpty()) {
                String pattern = "%" + filtro.toLowerCase() + "%";

                Predicate nombreLike = cb.like(cb.lower(producto.get("nombre")), pattern);
                Predicate tipoLike = cb.like(cb.lower(producto.get("tipo").as(String.class)), pattern);

                predicates.add(cb.or(nombreLike, tipoLike));
            }

            cq.select(producto).distinct(true).where(predicates.toArray(new Predicate[0]));

            return em.createQuery(cq).getResultList();

        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar productos disponibles con filtro", e);
        } finally {
            em.close();
        }
    }
}
