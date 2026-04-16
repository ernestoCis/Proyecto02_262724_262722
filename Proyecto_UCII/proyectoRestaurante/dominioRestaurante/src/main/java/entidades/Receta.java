/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Representa la entidad <b>Receta</b> en el sistema.
 * <p>Esta clase funciona como una tabla asociativa (break-table) que rompe la relación 
 * de muchos a muchos entre {@link Producto} e {@link Ingrediente}, permitiendo además 
 * almacenar un atributo adicional: la cantidad específica de cada insumo.</p>
 * <p>Cuenta con una restricción de unicidad para evitar que un mismo ingrediente se 
 * registre más de una vez en el mismo producto.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
@Entity
@Table(
        name = "recetas",
        uniqueConstraints = @UniqueConstraint(columnNames = {"idProducto", "idIngrediente"})
)
public class Receta implements Serializable {

    /** Identificador único del registro de la receta (Clave Primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Cantidad del ingrediente necesaria para la elaboración del producto. */
    private Double cantidad;

    /** * Producto al que pertenece esta parte de la receta.
     * <p>Relación de muchos a uno con {@link Producto}.</p>
     */
    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;

    /** * Ingrediente que compone esta parte de la receta.
     * <p>Relación de muchos a uno con {@link Ingrediente}.</p>
     */
    @ManyToOne
    @JoinColumn(name = "idIngrediente", nullable = false)
    private Ingrediente ingrediente;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Receta() {
    }

    /**
     * Constructor completo incluyendo el identificador único.
     * * @param id Identificador único de la receta.
     * @param cantidad Cantidad del insumo.
     * @param producto Producto asociado.
     * @param ingrediente Ingrediente asociado.
     */
    public Receta(Long id, Double cantidad, Producto producto, Ingrediente ingrediente) {
        this.id = id;
        this.cantidad = cantidad;
        this.producto = producto;
        this.ingrediente = ingrediente;
    }

    /**
     * Constructor para inicializar una nueva entrada de receta (sin ID).
     * * @param cantidad Cantidad necesaria.
     * @param producto Producto que la utiliza.
     * @param ingrediente Insumo utilizado.
     */
    public Receta(Double cantidad, Producto producto, Ingrediente ingrediente) {
        this.cantidad = cantidad;
        this.producto = producto;
        this.ingrediente = ingrediente;
    }

    /* @return El ID del registro de receta. */
    public Long getId() {
        return id;
    }

    /* @param id El ID a asignar. */
    public void setId(Long id) {
        this.id = id;
    }

    /* @return La cantidad utilizada en la receta. */
    public Double getCantidad() {
        return cantidad;
    }

    /* @param cantidad La cantidad a asignar. */
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    /* @return El producto asociado a esta receta. */
    public Producto getProducto() {
        return producto;
    }

    /* @param producto El producto a asignar. */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /* @return El ingrediente asociado a esta receta. */
    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    /* @param ingrediente El ingrediente a asignar. */
    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }
}