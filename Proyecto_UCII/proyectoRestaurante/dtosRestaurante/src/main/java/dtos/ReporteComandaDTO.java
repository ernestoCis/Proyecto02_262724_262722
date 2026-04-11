/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import enums.EstadoComandaDTO;
import java.time.LocalDateTime;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ReporteComandaDTO {
    private LocalDateTime fecha;
    private Integer numeroMesa;
    private Double total;
    private EstadoComandaDTO estado;
    private String nombreCliente;
    private String folio;

    public ReporteComandaDTO() {
    }

    public ReporteComandaDTO(LocalDateTime fecha, Integer numeroMesa, Double total, EstadoComandaDTO estado, String nombreCliente, String folio) {
        this.fecha = fecha;
        this.numeroMesa = numeroMesa;
        this.total = total;
        this.estado = estado;
        this.nombreCliente = nombreCliente;
        this.folio = folio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public EstadoComandaDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoComandaDTO estado) {
        this.estado = estado;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }
}
