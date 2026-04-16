/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import enums.EstadoComandaDTO;
import java.time.LocalDateTime;

/**
 * Clase Data Transfer Object (DTO) diseñada para la generación de reportes de comandas.
 * <p>Esta clase simplifica la estructura de una comanda para facilitar su visualización 
 * en tablas, reportes PDF o consultas históricas, consolidando información clave como 
 * la fecha, el folio, el cliente y el estado actual.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class ReporteComandaDTO {
    
    /** Fecha y hora exacta en la que se registró la apertura de la comanda. */
    private LocalDateTime fecha;
    /** Número de la mesa donde se realizó el servicio reportado. */
    private Integer numeroMesa;
    /** Monto total monetario de la comanda al finalizar el servicio. */
    private Double total;
    /** Estado administrativo final de la comanda (ej. ABIERTA, ENTREGADA, CANCELADA). */
    private EstadoComandaDTO estado;
    /** Nombre completo o razón social del cliente registrado en la cuenta. */
    private String nombreCliente;
    /** Código alfanumérico único utilizado para la identificación administrativa del reporte. */
    private String folio;

    /**
     * Constructor por defecto para crear una instancia vacía de ReporteComandaDTO.
     */
    public ReporteComandaDTO() {
    }

    /**
     * Constructor completo para inicializar todos los campos del reporte de comanda.
     * * @param fecha Fecha y hora en que se realizó la comanda.
     * @param numeroMesa Número físico de la mesa asociada.
     * @param total Monto total de la transacción.
     * @param estado Estado final o actual de la comanda (ej. PAGADA, CANCELADA).
     * @param nombreCliente Nombre del cliente que realizó el consumo.
     * @param folio Identificador alfanumérico único de la comanda.
     */
    public ReporteComandaDTO(LocalDateTime fecha, Integer numeroMesa, Double total, EstadoComandaDTO estado, String nombreCliente, String folio) {
        this.fecha = fecha;
        this.numeroMesa = numeroMesa;
        this.total = total;
        this.estado = estado;
        this.nombreCliente = nombreCliente;
        this.folio = folio;
    }

    /**
     * Obtiene la fecha y hora de la comanda en el reporte.
     * @return Objeto LocalDateTime con el registro temporal.
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * Asigna la fecha y hora de la comanda para el reporte.
     * @param fecha La fecha a asignar.
     */
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el número de mesa registrado en el reporte.
     * @return El número de la mesa.
     */
    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    /**
     * Asigna el número de mesa para el reporte.
     * @param numeroMesa El número a asignar.
     */
    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    /**
     * Obtiene el monto total reportado.
     * @return El total acumulado.
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Asigna el monto total para el reporte.
     * @param total El total a asignar.
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * Obtiene el estado de la comanda para fines informativos.
     * @return El estado de la comanda (DTO).
     */
    public EstadoComandaDTO getEstado() {
        return estado;
    }

    /**
     * Asigna el estado de la comanda en el reporte.
     * @param estado El estado a asignar.
     */
    public void setEstado(EstadoComandaDTO estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el nombre del cliente asociado a la comanda.
     * @return Cadena con el nombre del cliente.
     */
    public String getNombreCliente() {
        return nombreCliente;
    }

    /**
     * Asigna el nombre del cliente para el reporte.
     * @param nombreCliente El nombre a asignar.
     */
    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    /**
     * Obtiene el folio identificador de la comanda.
     * @return El folio administrativo.
     */
    public String getFolio() {
        return folio;
    }

    /**
     * Asigna el folio identificador de la comanda.
     * @param folio El folio a asignar.
     */
    public void setFolio(String folio) {
        this.folio = folio;
    }
}