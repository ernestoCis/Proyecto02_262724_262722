/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
@Entity
@Table(name = "comandas")
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComanda;

    private Integer numeroMesa;
    
    @Enumerated(EnumType.STRING)
    private EstadoComanda estado;
    
    private String folio;
    
    private LocalDate fecha;
    
    private Double total;

    @ManyToOne
    @JoinColumn(name = "idCliente")
    private Cliente cliente;

    public Comanda() {
        this.fecha = LocalDate.now();
        this.estado = EstadoComanda.ABIERTA;
    }

    public Comanda(Long idComanda, Integer numeroMesa, EstadoComanda estado, String folio, LocalDate fecha, Double total, Cliente cliente) {
        this.idComanda = idComanda;
        this.numeroMesa = numeroMesa;
        this.estado = estado;
        this.folio = folio;
        this.fecha = fecha;
        this.total = total;
        this.cliente = cliente;
    }

    public Long getIdComanda() {
        return idComanda;
    }

    public void setIdComanda(Long idComanda) {
        this.idComanda = idComanda;
    }

    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public EstadoComanda getEstado() {
        return estado;
    }

    public void setEstado(EstadoComanda estado) {
        this.estado = estado;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}