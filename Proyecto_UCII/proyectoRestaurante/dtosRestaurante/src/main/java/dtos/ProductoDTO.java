/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.List;
import enums.DisponibilidadProductoDTO;
import enums.TipoProductoDTO;

/**
 * Clase Data Transfer Object (DTO) que representa la información detallada de un Producto.
 * <p>Esta clase se utiliza para transportar los datos comerciales de los artículos del menú,
 * incluyendo su categorización, precio, estado de disponibilidad y la lista de elementos 
 * que componen su receta.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class ProductoDTO {

    /** Identificador único del producto en el sistema. */
    private Long idProducto;
    /** Nombre comercial o denominación del artículo en el menú. */
    private String nombre;
    /** Valor monetario de venta asignado al producto. */
    private Double precio;
    /** Categoría a la que pertenece el producto (ej. PLATILLO, BEBIDA). */
    private TipoProductoDTO tipo;
    /** Explicación detallada o ingredientes principales para el cliente. */
    private String descripcion;
    /** Estado que indica si el producto puede ser ordenado actualmente. */
    private DisponibilidadProductoDTO disponibilidad;
    /** Ubicación o nombre del archivo de imagen para la representación visual. */
    private String rutaImagen;
    /** Listado de insumos y cantidades que integran la preparación del producto. */
    private List<RecetaDTO> recetas;

    /**
     * Constructor por defecto para crear una instancia vacía de ProductoDTO.
     */
    public ProductoDTO() {
    }

    /**
     * Constructor completo para inicializar un producto con todos sus atributos, incluyendo el ID.
     * * @param idProducto Identificador único del producto.
     * @param nombre Nombre comercial del producto.
     * @param precio Precio de venta al público.
     * @param tipo Categoría del producto (PLATILLO, BEBIDA, etc.).
     * @param descripcion Detalle informativo sobre el producto.
     * @param disponibilidad Estado de disponibilidad actual.
     * @param rutaImagen Ruta del archivo de imagen del producto.
     * @param recetas Lista de DTOs que conforman la receta del producto.
     */
    public ProductoDTO(Long idProducto, String nombre, Double precio, TipoProductoDTO tipo, String descripcion, DisponibilidadProductoDTO disponibilidad, String rutaImagen, List<RecetaDTO> recetas) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.disponibilidad = disponibilidad;
        this.rutaImagen = rutaImagen;
        this.recetas = recetas;
    }

    /**
     * Constructor para inicializar un producto sin identificador, útil para el registro de nuevos productos.
     * * @param nombre Nombre comercial del producto.
     * @param precio Precio de venta al público.
     * @param tipo Categoría del producto.
     * @param descripcion Detalle informativo.
     * @param disponibilidad Estado de disponibilidad inicial.
     * @param rutaImagen Ubicación de la imagen.
     * @param recetas Lista de componentes de la receta.
     */
    public ProductoDTO(String nombre, Double precio, TipoProductoDTO tipo, String descripcion, DisponibilidadProductoDTO disponibilidad, String rutaImagen, List<RecetaDTO> recetas) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.disponibilidad = disponibilidad;
        this.rutaImagen = rutaImagen;
        this.recetas = recetas;
    }

    /**
     * Obtiene el identificador único del producto.
     * @return El ID del producto.
     */
    public Long getIdProducto() {
        return idProducto;
    }

    /**
     * Asigna el identificador único del producto.
     * @param idProducto El ID a asignar.
     */
    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene el nombre del producto.
     * @return El nombre comercial.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre del producto.
     * @param nombre El nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el precio del producto.
     * @return El precio de venta.
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * Asigna el precio del producto.
     * @param precio El precio a asignar.
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el tipo o categoría del producto.
     * @return El TipoProductoDTO asociado.
     */
    public TipoProductoDTO getTipo() {
        return tipo;
    }

    /**
     * Asigna el tipo o categoría del producto.
     * @param tipo El tipo a asignar.
     */
    public void setTipo(TipoProductoDTO tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene la descripción detallada del producto.
     * @return El texto de la descripción.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Asigna la descripción detallada del producto.
     * @param descripcion La descripción a asignar.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el estado de disponibilidad del producto.
     * @return La DisponibilidadProductoDTO actual.
     */
    public DisponibilidadProductoDTO getDisponibilidad() {
        return disponibilidad;
    }

    /**
     * Asigna el estado de disponibilidad del producto.
     * @param disponibilidad La disponibilidad a asignar.
     */
    public void setDisponibilidad(DisponibilidadProductoDTO disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    /**
     * Obtiene la ruta de la imagen representativa del producto.
     * @return La cadena con la ruta de la imagen.
     */
    public String getRutaImagen() {
        return rutaImagen;
    }

    /**
     * Asigna la ruta de la imagen representativa del producto.
     * @param rutaImagen La ruta a asignar.
     */
    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    /**
     * Obtiene la lista de detalles de la receta del producto.
     * @return Lista de objetos RecetaDTO.
     */
    public List<RecetaDTO> getRecetas() {
        return recetas;
    }

    /**
     * Asigna la lista de detalles de la receta del producto.
     * @param recetas La lista de recetas a asignar.
     */
    public void setRecetas(List<RecetaDTO> recetas) {
        this.recetas = recetas;
    }
}