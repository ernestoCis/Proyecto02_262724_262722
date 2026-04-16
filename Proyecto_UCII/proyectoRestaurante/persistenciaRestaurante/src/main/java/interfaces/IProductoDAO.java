/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package interfaces;

import entidades.Producto;
import enums.DisponibilidadProducto;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Interfaz que define las operaciones de persistencia para la entidad Producto.
 * Establece el contrato para la gestión del menú, incluyendo el control de
 * disponibilidad automática basada en inventario y la validación de integridad
 * en procesos de venta activos.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IProductoDAO {

    /**
     * Almacena un nuevo producto en el catálogo de la base de datos.
     *
     * @param producto El objeto {@link Producto} a registrar.
     * @throws PersistenciaException Si ocurre un error durante el proceso de
     * guardado.
     */
    public void guardar(Producto producto) throws PersistenciaException;

    /**
     * Actualiza la información técnica, de costos o descriptiva de un producto.
     *
     * @param producto Objeto con los datos actualizados para persistir.
     * @throws PersistenciaException Si la actualización falla en la capa de
     * datos.
     */
    public void actualizar(Producto producto) throws PersistenciaException;

    /**
     * Localiza un producto mediante su identificador único.
     *
     * @param id Identificador del producto.
     * @return El {@link Producto} encontrado o null si no existe.
     * @throws PersistenciaException Si ocurre un error en la consulta.
     */
    public Producto buscarPorId(Long id) throws PersistenciaException;

    /**
     * Recupera la lista completa de todos los productos registrados en el
     * sistema.
     *
     * @return Lista de todos los objetos {@link Producto}.
     * @throws PersistenciaException Si falla la consulta al catálogo.
     */
    public List<Producto> obtenerProductos() throws PersistenciaException;

    /**
     * Busca productos cuyo nombre coincida parcialmente con el criterio
     * proporcionado.
     *
     * @param nombre Cadena de texto para la búsqueda por coincidencia.
     * @return Lista de productos que contienen el nombre buscado.
     * @throws PersistenciaException Si ocurre un error en la búsqueda.
     */
    public List<Producto> buscarPorNombre(String nombre) throws PersistenciaException;

    /**
     * Localiza un producto por su nombre exacto. Útil para validaciones de
     * duplicados.
     *
     * @param nombre Nombre exacto del producto.
     * @return El {@link Producto} coincidente o null si no se encuentra.
     * @throws PersistenciaException Si ocurre un error en la capa de
     * persistencia.
     */
    public Producto buscarPorNombreExacto(String nombre) throws PersistenciaException;

    /**
     * Gatilla una actualización masiva en la disponibilidad de productos tras
     * un cambio en el stock de un ingrediente específico.
     *
     * @param idIngrediente Identificador del ingrediente que cambió su
     * cantidad.
     * @throws PersistenciaException Si la actualización en cascada falla.
     */
    public void actualizarProductosPorIngrediente(Long idIngrediente) throws PersistenciaException;

    /**
     * Modifica el estado de disponibilidad de un producto de forma manual.
     *
     * @param id ID del producto.
     * @param estado Nuevo estado de {@link DisponibilidadProducto}.
     * @throws PersistenciaException Si no se puede cambiar el estado del
     * registro.
     */
    public void cambiarDisponibilidad(Long id, DisponibilidadProducto estado) throws PersistenciaException;

    /**
     * Verifica si el producto ha sido solicitado en pedidos históricos.
     * Fundamental para validar si el producto puede ser eliminado físicamente.
     *
     * @param idProducto ID del producto a verificar.
     * @return true si tiene registros asociados en detalles de pedido, false de
     * lo contrario.
     * @throws PersistenciaException Si ocurre un error en la verificación.
     */
    public boolean estaEnUso(Long idProducto) throws PersistenciaException;

    /**
     * Filtra y devuelve únicamente los productos marcados como DISPONIBLES.
     *
     * @return Lista de productos aptos para la venta actual.
     * @throws PersistenciaException Si falla la consulta.
     */
    public List<Producto> obtenerProductosDisponibles() throws PersistenciaException;

    /**
     * Elimina permanentemente el producto del sistema.
     *
     * @param id Identificador del producto a remover.
     * @throws PersistenciaException Si el producto no puede ser borrado.
     */
    public void eliminar(Long id) throws PersistenciaException;

    /**
     * Comprueba si el producto forma parte de una orden de servicio (comanda)
     * que aún no ha sido liquidada o cerrada.
     *
     * @param idProducto ID del producto.
     * @return true si se encuentra en una comanda con estado ABIERTA.
     * @throws PersistenciaException Si ocurre un error en la consulta de
     * estados.
     */
    public boolean estaEnComandaAbierta(Long idProducto) throws PersistenciaException;

    /**
     * Realiza una consulta avanzada sobre el catálogo completo aplicando
     * filtros dinámicos.
     *
     * @param filtro Texto para filtrar por nombre o categoría.
     * @return Lista de productos que cumplen el criterio.
     * @throws PersistenciaException Si ocurre un error al procesar el filtro.
     */
    public List<Producto> consultarProductosConFiltro(String filtro) throws PersistenciaException;

    /**
     * Realiza una consulta avanzada únicamente sobre productos que pueden
     * venderse en el momento.
     *
     * @param filtro Texto para filtrar productos disponibles.
     * @return Lista de productos disponibles filtrados.
     * @throws PersistenciaException Si falla la ejecución del filtro dinámico.
     */
    public List<Producto> consultarProductosDisponiblesConFiltro(String filtro) throws PersistenciaException;
}
