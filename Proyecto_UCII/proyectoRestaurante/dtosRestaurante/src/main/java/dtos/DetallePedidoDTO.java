/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.io.Serializable;

/**
 * Clase Data Transfer Object (DTO) que representa el desglose de un producto dentro de una comanda.
 * <p>Esta clase almacena la información específica de cada partida en el pedido, incluyendo
 * la cantidad solicitada, el precio al momento de la venta, el subtotal calculado y 
 * anotaciones especiales para la preparación.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class DetallePedidoDTO implements Serializable{
    
    /** Identificador único del detalle del pedido. */
    private Long idDetallePedido;
    /** Cantidad de unidades solicitadas de un mismo producto. */
    private Integer cantidad;
    /** Observaciones o especificaciones especiales para la preparación del producto. */
    private String nota;
    /** Monto resultante de multiplicar la cantidad por el precio unitario. */
    private Double subtotal;
    /** Precio del producto vigente al momento de realizar el pedido. */
    private Double precioUnitario;
    
    /** Información detallada del producto asociado a este detalle. */
    private ProductoDTO productoDTO;
    
    /** Identificador de la comanda a la que pertenece este detalle. */
    private Long idComanda;

    /**
     * Constructor por defecto para crear una instancia vacía de DetallePedidoDTO.
     */
    public DetallePedidoDTO() {
    }

    /**
     * Obtiene el identificador único del detalle del pedido.
     * @return El ID del detalle de pedido.
     */
    public Long getIdDetallePedido() {
        return idDetallePedido;
    }

    /**
     * Asigna el identificador único del detalle del pedido.
     * @param idDetallePedido El ID a asignar.
     */
    public void setIdDetallePedido(Long idDetallePedido) {
        this.idDetallePedido = idDetallePedido;
    }

    /**
     * Obtiene la cantidad de productos solicitados en este detalle.
     * @return La cantidad de artículos.
     */
    public Integer getCantidad() {
        return cantidad;
    }

    /**
     * Asigna la cantidad de productos solicitados en este detalle.
     * @param cantidad La cantidad a asignar.
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene las notas o especificaciones de preparación del producto.
     * @return El texto de la nota (ej. "Sin cebolla").
     */
    public String getNota() {
        return nota;
    }

    /**
     * Asigna notas o especificaciones de preparación al producto.
     * @param nota La descripción de la nota a asignar.
     */
    public void setNota(String nota) {
        this.nota = nota;
    }

    /**
     * Obtiene el subtotal calculado para este detalle (cantidad por precio unitario).
     * @return El monto del subtotal.
     */
    public Double getSubtotal() {
        return subtotal;
    }

    /**
     * Asigna el subtotal calculado para este detalle.
     * @param subtotal El monto del subtotal a asignar.
     */
    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    /**
     * Obtiene el precio unitario del producto registrado en el momento del pedido.
     * @return El precio unitario.
     */
    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    /**
     * Asigna el precio unitario del producto para este detalle.
     * @param precioUnitario El precio unitario a asignar.
     */
    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    /**
     * Obtiene la información del producto asociado a este detalle.
     * @return Objeto ProductoDTO con los datos del producto.
     */
    public ProductoDTO getProductoDTO() {
        return productoDTO;
    }

    /**
     * Asigna la información del producto asociado a este detalle.
     * @param productoDTO El DTO del producto a asignar.
     */
    public void setProductoDTO(ProductoDTO productoDTO) {
        this.productoDTO = productoDTO;
    }

    /**
     * Obtiene el identificador de la comanda a la que pertenece este detalle.
     * @return El ID de la comanda padre.
     */
    public Long getIdComanda() {
        return idComanda;
    }

    /**
     * Asigna el identificador de la comanda a la que pertenece este detalle.
     * @param idComanda El ID de la comanda a asignar.
     */
    public void setIdComanda(Long idComanda) {
        this.idComanda = idComanda;
    }
}