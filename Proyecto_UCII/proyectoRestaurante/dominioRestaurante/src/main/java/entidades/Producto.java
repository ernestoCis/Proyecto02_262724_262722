/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.DisponibilidadProducto;
import enums.TipoProducto;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa la entidad <b>Producto</b> en el sistema.
 * <p>Esta clase mapea a la tabla "productos" y contiene la información comercial, 
 * clasificación y disponibilidad de los artículos ofrecidos en el menú, así como su composición en recetas.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
@Entity
@Table(name = "productos")
public class Producto implements Serializable {

    /** Identificador único del producto (Clave Primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    /** Nombre comercial del producto. */
    @Column(nullable = false)
    private String nombre;

    /** Precio de venta al público. */
    @Column(nullable = false)
    private Double precio;

    /** * Clasificación del producto.
     * <p>Basado en el enumerado {@link TipoProducto} (ej. PLATILLO, BEBIDA).</p>
     */
    @Enumerated(EnumType.STRING)
    private TipoProducto tipo;
    
    /** Descripción detallada de los ingredientes o preparación del producto. */
    @Column(nullable = true)
    private String descripcion;

    /** * Estado de disponibilidad actual para venta.
     * <p>Basado en el enumerado {@link DisponibilidadProducto}.</p>
     */
    @Enumerated(EnumType.STRING)
    private DisponibilidadProducto disponibilidad;
    
    /** Ruta de la imagen ilustrativa del producto en el servidor o sistema local. */
    private String rutaImg;

    /** * Lista de ingredientes y cantidades que conforman el producto.
     * <p>Relación de uno a muchos con {@link Receta} con persistencia en cascada.</p>
     */
    @OneToMany(mappedBy = "producto",  cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Receta> recetas = new ArrayList<>();

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Producto() {
    }

    /**
     * Constructor completo incluyendo el identificador único.
     * * @param idProducto Identificador único.
     * @param nombre Nombre del producto.
     * @param precio Precio de venta.
     * @param tipo Categoría del producto.
     * @param descripcion Descripción informativa.
     * @param disponibilidad Estado de stock/venta.
     * @param rutaImg Ubicación de la imagen.
     */
    public Producto(Long idProducto, String nombre, Double precio, TipoProducto tipo, String descripcion, DisponibilidadProducto disponibilidad, String rutaImg) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.disponibilidad = disponibilidad;
        this.rutaImg = rutaImg;
    }

    /**
     * Constructor para inicializar un nuevo producto (sin ID).
     * * @param nombre Nombre del producto.
     * @param precio Precio de venta.
     * @param tipo Categoría del producto.
     * @param descripcion Descripción informativa.
     * @param disponibilidad Estado de stock/venta.
     * @param rutaImg Ubicación de la imagen.
     */
    public Producto(String nombre, Double precio, TipoProducto tipo, String descripcion, DisponibilidadProducto disponibilidad, String rutaImg) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.disponibilidad = disponibilidad;
        this.rutaImg = rutaImg;
    }

    /* @return El ID del producto. */
    public Long getIdProducto() {
        return idProducto;
    }

    /* @param idProducto El ID a asignar. */
    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    /* @return El nombre del producto. */
    public String getNombre() {
        return nombre;
    }

    /* @param nombre El nombre a asignar. */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /* @return El precio de venta. */
    public Double getPrecio() {
        return precio;
    }

    /* @param precio El precio a asignar. */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /* @return La descripción del producto. */
    public String getDescripcion() {
        return descripcion;
    }

    /* @param descripcion La descripción a asignar. */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /* @return El tipo de producto. */
    public TipoProducto getTipo() {
        return tipo;
    }

    /* @param tipo El tipo a asignar. */
    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }

    /* @return El estado de disponibilidad. */
    public DisponibilidadProducto getDisponibilidad() {
        return disponibilidad;
    }

    /* @param disponibilidad La disponibilidad a asignar. */
    public void setDisponibilidad(DisponibilidadProducto disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    /* @return La lista de elementos de la receta. */
    public List<Receta> getRecetas() {
        return recetas;
    }

    /* @param recetas La lista de recetas a asignar. */
    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
    }

    /* @return La ruta de la imagen. */
    public String getRutaImg() {
        return rutaImg;
    }

    /* @param rutaImg La ruta de imagen a asignar. */
    public void setRutaImg(String rutaImg) {
        this.rutaImg = rutaImg;
    }
    
    /** * Añade una receta a la lista y establece la relación bidireccional.
     * @param receta La receta a agregar.
     */
    public void agregarReceta(Receta receta) {
        recetas.add(receta);
        receta.setProducto(this);
    }

    /** * Elimina una receta de la lista y rompe la relación bidireccional.
     * @param receta La receta a eliminar.
     */
    public void eliminarReceta(Receta receta) {
        recetas.remove(receta);
        receta.setProducto(null);
    }
}