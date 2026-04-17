/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package interfaces;

import dtos.ProductoDTO;
import enums.DisponibilidadProductoDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * Interfaz que define las operaciones de lógica de negocio para la gestión de
 * Productos. Centraliza la administración del catálogo de productos, el control
 * de recetas, y la validación de stock para determinar la disponibilidad en el
 * menú.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IProductoBO {

    /**
     * Registra un nuevo producto en el sistema. Incluye la validación de sus
     * ingredientes base (receta) y asegura que no existan nombres duplicados.
     *
     * * @param dto El objeto de transferencia con la información del nuevo
     * producto.
     * @throws NegocioException Si los datos son insuficientes, el producto ya
     * existe o no tiene ingredientes asignados.
     */
    void registrarProducto(ProductoDTO dto) throws NegocioException;

    /**
     * Actualiza la información de un producto existente. Permite modificar
     * precios, tipos y la estructura de su receta.
     *
     * * @param dto El DTO con los datos actualizados del producto.
     * @throws NegocioException Si el ID no es válido o la actualización viola
     * reglas de integridad.
     */
    void actualizarProducto(ProductoDTO dto) throws NegocioException;

    /**
     * Elimina un producto del catálogo o lo deshabilita. Dependiendo de la
     * implementación, puede realizar una eliminación lógica si el producto ya
     * tiene historial en comandas.
     *
     * * @param id Identificador único del producto a eliminar.
     * @throws NegocioException Si el producto no se encuentra o no puede ser
     * eliminado.
     */
    void eliminarProducto(Long id) throws NegocioException;

    /**
     * Recupera la lista completa de productos registrados, sin importar su
     * disponibilidad.
     *
     * * @return Lista de {@link ProductoDTO} con todos los productos del
     * catálogo.
     * @throws NegocioException Si ocurre un error al consultar la base de
     * datos.
     */
    List<ProductoDTO> consultarTodos() throws NegocioException;

    /**
     * Realiza una búsqueda de productos filtrando por coincidencias en el
     * nombre.
     *
     * * @param nombre Cadena de texto a buscar.
     * @return Lista de {@link ProductoDTO} que coinciden con el nombre
     * proporcionado.
     * @throws NegocioException Si ocurre un error durante el proceso de
     * búsqueda.
     */
    List<ProductoDTO> buscarPorNombre(String nombre) throws NegocioException;

    /**
     * Modifica manualmente el estado de disponibilidad de un producto.
     *
     * * @param id Identificador único del producto.
     * @param estado El nuevo estado (e.g., DISPONIBLE, NO_DISPONIBLE).
     * @throws NegocioException Si se intenta marcar como disponible y no hay
     * stock suficiente.
     */
    void cambiarDisponibilidad(Long id, DisponibilidadProductoDTO estado) throws NegocioException;

    /**
     * Verifica si existe inventario suficiente de cada ingrediente en la receta
     * para producir una cantidad específica del producto.
     *
     * * @param dto El DTO del producto que contiene la receta a evaluar.
     * @param cantidadSolicitada La cantidad de unidades del producto
     * requeridas.
     * @return {@code true} si hay stock suficiente, {@code false} en caso
     * contrario.
     */
    boolean hayStockSuficiente(ProductoDTO dto, int cantidadSolicitada);

    /**
     * Recupera únicamente los productos que se encuentran marcados como
     * disponibles. Ideal para mostrar el menú activo a los meseros.
     *
     * * @return Lista de {@link ProductoDTO} actualmente disponibles para
     * venta.
     * @throws NegocioException Si ocurre un error al cargar los productos
     * disponibles.
     */
    List<ProductoDTO> consultarProductosDisponibles() throws NegocioException;

    /**
     * Consulta productos generales aplicando un filtro de búsqueda textual.
     *
     * * @param filtro Texto para filtrar por nombre o categoría.
     * @return Lista de {@link ProductoDTO} filtrada.
     * @throws NegocioException Si hay errores en la capa de persistencia.
     */
    public List<ProductoDTO> consultarProductosConFiltro(String filtro) throws NegocioException;

    /**
     * Consulta únicamente productos disponibles aplicando un filtro de
     * búsqueda.
     *
     * * @param filtro Texto para filtrar productos aptos para la venta.
     * @return Lista de {@link ProductoDTO} disponibles y filtrados.
     * @throws NegocioException Si ocurre un error en la consulta.
     */
    public List<ProductoDTO> consultarProductosDisponiblesConFiltro(String filtro) throws NegocioException;
}
