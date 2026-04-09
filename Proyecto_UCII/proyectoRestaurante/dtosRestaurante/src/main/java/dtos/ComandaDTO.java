/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import enums.EstadoComandaDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ComandaDTO {
    private Long idComanda;
    private String folio; //se le dara un valor en negocio
    private LocalDateTime fecha;
    private Double total;
    private EstadoComandaDTO estado;
    private Integer numeroMesa;
    
    private MesaDTO mesa;
    private MeseroDTO mesero;
    private ClienteFrecuenteDTO cliente;
    
    private List<DetallePedidoDTO> detalles = new ArrayList<>();

    public ComandaDTO() {
    }

    public ComandaDTO(Long idComanda, String folio, LocalDateTime fecha, Double total, Integer numeroMesa, EstadoComandaDTO estado, MesaDTO mesa, MeseroDTO mesero, ClienteFrecuenteDTO cliente) {
        this.idComanda = idComanda;
        this.folio = folio;
        this.fecha = fecha;
        this.total = total;
        this.numeroMesa = numeroMesa;
        this.estado = estado;
        this.mesa = mesa;
        this.mesero = mesero;
        this.cliente = cliente;
    }

    public Long getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(Long idComanda) {
        this.idComanda = idComanda;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public EstadoComandaDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoComandaDTO estado) {
        this.estado = estado;
    }

    public MesaDTO getMesa() {
        return mesa;
    }

    public void setMesa(MesaDTO mesa) {
        this.mesa = mesa;
    }

    public MeseroDTO getMesero() {
        return mesero;
    }

    public void setMesero(MeseroDTO mesero) {
        this.mesero = mesero;
    }

    public ClienteFrecuenteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteFrecuenteDTO cliente) {
        this.cliente = cliente;
    }

    public List<DetallePedidoDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedidoDTO> detalles) {
        this.detalles = detalles;
    }
}
