/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.EstadoComanda;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    
    @Column(nullable = false)
    private String folio;
    
    @Column(nullable = false)
    private LocalDateTime fecha;
    
    @Column(nullable = false)
    private Double total;
    
    @Column(nullable = true)
    private Integer numeroMesa;
    
    @Enumerated(EnumType.STRING)
    private EstadoComanda estado;
    
    @ManyToOne
    @JoinColumn(name = "idMesa", nullable = true)
    private Mesa mesa;
    
    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private ClienteFrecuente cliente;
    
    @ManyToOne
    @JoinColumn(name = "idMesero", nullable = false)
    private Mesero mesero;
    
    @OneToMany(mappedBy = "comanda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;

    public Comanda() {
    }

    public Comanda(Long idComanda, String folio, LocalDateTime fecha, Double total, Integer numeroMesa, EstadoComanda estado, Mesa mesa, ClienteFrecuente cliente, Mesero mesero, List<DetallePedido> detalles) {
        this.idComanda = idComanda;
        this.folio = folio;
        this.fecha = fecha;
        this.total = total;
        this.numeroMesa = numeroMesa;
        this.estado = estado;
        this.mesa = mesa;
        this.cliente = cliente;
        this.mesero = mesero;
        this.detalles = detalles;
    }

    public Comanda(String folio, LocalDateTime fecha, Double total, Integer numeroMesa, EstadoComanda estado, Mesa mesa, ClienteFrecuente cliente, Mesero mesero, List<DetallePedido> detalles) {
        this.folio = folio;
        this.fecha = fecha;
        this.total = total;
        this.numeroMesa = numeroMesa;
        this.estado = estado;
        this.mesa = mesa;
        this.cliente = cliente;
        this.mesero = mesero;
        this.detalles = detalles;
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

    public EstadoComanda getEstado() {
        return estado;
    }

    public void setEstado(EstadoComanda estado) {
        this.estado = estado;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    public ClienteFrecuente getCliente() {
        return cliente;
    }

    public void setCliente(ClienteFrecuente cliente) {
        this.cliente = cliente;
    }

    public Mesero getMesero() {
        return mesero;
    }

    public void setMesero(Mesero mesero) {
        this.mesero = mesero;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }
}