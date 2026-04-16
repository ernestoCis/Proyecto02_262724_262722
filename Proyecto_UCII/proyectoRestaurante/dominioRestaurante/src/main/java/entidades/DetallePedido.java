/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Representa la entidad <b>DetallePedido</b> en el sistema.
 * <p>Esta clase mapea a la tabla "DetallesPedido" y funciona como una línea de detalle 
 * dentro de una {@link Comanda}, asociando un producto específico con su cantidad y precio.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
@Entity
@Table (name = "DetallesPedido")
public class DetallePedido implements Serializable{
    
    /** Identificador único del detalle del pedido (Clave Primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetallePedido;
    
    /** Cantidad de unidades solicitadas del producto. */
    @Column(nullable = false)
    private Integer cantidad;
    
    /** Nota o instrucción especial para la preparación del producto (ej. "sin cebolla"). */
    @Column(nullable = true)
    private String nota;
    
    /** Importe resultante de multiplicar la cantidad por el precio unitario. */
    @Column(nullable = false)
    private Double subtotal;
    
    /** Precio individual del producto al momento de realizar el pedido. */
    @Column(nullable = false)
    private Double precioUnitario;
    
    /** * Producto asociado a esta línea de detalle.
     * <p>Relación de muchos a uno con la entidad {@link Producto}.</p>
     */
    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;
    
    /** * Comanda a la que pertenece este detalle.
     * <p>Relación de muchos a uno con la entidad {@link Comanda}.</p>
     */
    @ManyToOne
    @JoinColumn(name = "idComanda", nullable = false)
    private Comanda comanda;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public DetallePedido() {
    }

    /**
     * Constructor para inicializar un detalle sin ID (para nuevas inserciones).
     * * @param cantidad Cantidad del producto.
     * @param nota Observaciones adicionales.
     * @param subtotal Subtotal calculado.
     * @param precioUnitario Precio por unidad.
     * @param producto Instancia del producto solicitado.
     * @param comanda Instancia de la comanda contenedora.
     */
    public DetallePedido(Integer cantidad, String nota, Double subtotal, Double precioUnitario, Producto producto, Comanda comanda) {
        this.cantidad = cantidad;
        this.nota = nota;
        this.subtotal = subtotal;
        this.precioUnitario = precioUnitario;
        this.producto = producto;
        this.comanda = comanda;
    }

    /**
     * Constructor completo incluyendo el identificador único.
     * * @param idDetallePedido Identificador único.
     * @param cantidad Cantidad del producto.
     * @param nota Observaciones adicionales.
     * @param subtotal Subtotal calculado.
     * @param precioUnitario Precio por unidad.
     * @param producto Instancia del producto solicitado.
     * @param comanda Instancia de la comanda contenedora.
     */
    public DetallePedido(Long idDetallePedido, Integer cantidad, String nota, Double subtotal, Double precioUnitario, Producto producto, Comanda comanda) {
        this.idDetallePedido = idDetallePedido;
        this.cantidad = cantidad;
        this.nota = nota;
        this.subtotal = subtotal;
        this.precioUnitario = precioUnitario;
        this.producto = producto;
        this.comanda = comanda;
    }
    
    /* @return El ID del detalle del pedido. */
    public Long getIdDetallePedido() {
        return idDetallePedido;
    }

    /* @param idDetallePedido El ID a asignar. */
    public void setIdDetallePedido(Long idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }

    /* @return La cantidad de productos. */
    public Integer getCantidad() {
        return cantidad;
    }

    /* @param cantidad La cantidad a asignar. */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /* @return La nota o instrucción especial. */
    public String getNota() {
        return nota;
    }

    /* @param nota La nota a asignar. */
    public void setNota(String nota) {
        this.nota = nota;
    }

    /* @return El subtotal de la línea. */
    public Double getSubtotal() {
        return subtotal;
    }

    /* @param subtotal El subtotal a asignar. */
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    /* @return El precio unitario registrado. */
    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    /* @param precioUnitario El precio unitario a asignar. */
    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /* @return El producto asociado. */
    public Producto getProducto() {
        return producto;
    }

    /* @param producto El producto a asignar. */
    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /* @return La comanda asociada. */
    public Comanda getComanda() {
        return comanda;
    }

    /* @param comanda La comanda a asignar. */
    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }
}