/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
@Entity
@Table (name = "DetallesPedido")
public class DetallePedido implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDetallePedido;
    
    @Column(nullable = false)
    private Integer cantidad;
    
    @Column(nullable = true)
    private String nota;
    
    @Column(nullable = false)
    private Double subtotal;
    
    @Column(nullable = false)
    private Double precioUnitario;
    
    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private Producto producto;
    
    @ManyToOne
    @JoinColumn(name = "idComanda", nullable = false)
    private Comanda comanda;

    public DetallePedido() {
    }

    public DetallePedido(Integer cantidad, String nota, Double subtotal, Double precioUnitario, Producto producto, Comanda comanda) {
        this.cantidad = cantidad;
        this.nota = nota;
        this.subtotal = subtotal;
        this.precioUnitario = precioUnitario;
        this.producto = producto;
        this.comanda = comanda;
    }

    public DetallePedido(Long idDetallePedido, Integer cantidad, String nota, Double subtotal, Double precioUnitario, Producto producto, Comanda comanda) {
        this.idDetallePedido = idDetallePedido;
        this.cantidad = cantidad;
        this.nota = nota;
        this.subtotal = subtotal;
        this.precioUnitario = precioUnitario;
        this.producto = producto;
        this.comanda = comanda;
    }
    
    public Long getIdDetallePedido() {
        return idDetallePedido;
    }

    public void setIdDetallePedido(Long idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }
}
