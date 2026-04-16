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
 * Representa la entidad <b>Comanda</b> en el sistema.
 * <p>Esta clase mapea a la tabla "comandas" y contiene la información principal 
 * de una orden de servicio, incluyendo el cliente, el mesero y los detalles del pedido.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
@Entity
@Table(name = "comandas")
public class Comanda {

    /** Identificador único de la comanda (Clave Primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComanda;
    
    /** Folio único identificador de la comanda para fines administrativos. */
    @Column(nullable = false)
    private String folio;
    
    /** Fecha y hora exacta en la que se generó la comanda. */
    @Column(nullable = false)
    private LocalDateTime fecha;
    
    /** Monto total a pagar de la comanda. */
    @Column(nullable = false)
    private Double total;
    
    /** Número físico de la mesa asignada (opcional). */
    @Column(nullable = true)
    private Integer numeroMesa;
    
    /** Estado actual de la comanda (ej. ABIERTA, ENTREGADA, CANCELADA). */
    @Enumerated(EnumType.STRING)
    private EstadoComanda estado;
    
    /** * Mesa asociada a la comanda.
     * <p>Relación de muchos a uno con la entidad {@link Mesa}.</p>
     */
    @ManyToOne
    @JoinColumn(name = "idMesa", nullable = true)
    private Mesa mesa;
    
    /** * Cliente que realizó el pedido.
     * <p>Relación obligatoria de muchos a uno con {@link ClienteFrecuente}.</p>
     */
    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private ClienteFrecuente cliente;
    
    /** * Mesero que atendió la comanda.
     * <p>Relación obligatoria de muchos a uno con {@link Mesero}.</p>
     */
    @ManyToOne
    @JoinColumn(name = "idMesero", nullable = false)
    private Mesero mesero;
    
    /** * Lista de productos y cantidades que integran el pedido.
     * <p>Incluye persistencia en cascada y eliminación de huérfanos.</p>
     */
    @OneToMany(mappedBy = "comanda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetallePedido> detalles;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Comanda() {
    }

    /**
     * Constructor con todos los parámetros, incluyendo el ID.
     * * @param idComanda Identificador único.
     * @param folio Folio administrativo.
     * @param fecha Fecha de creación.
     * @param total Monto total.
     * @param numeroMesa Número de mesa.
     * @param estado Estado de la orden.
     * @param mesa Entidad Mesa asociada.
     * @param cliente Entidad Cliente asociado.
     * @param mesero Entidad Mesero asociado.
     * @param detalles Lista de detalles del pedido.
     */
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

    /**
     * Constructor para crear una comanda nueva (sin ID).
     * * @param folio Folio administrativo.
     * @param fecha Fecha de creación.
     * @param total Monto total.
     * @param numeroMesa Número de mesa.
     * @param estado Estado de la orden.
     * @param mesa Entidad Mesa asociada.
     * @param cliente Entidad Cliente asociado.
     * @param mesero Entidad Mesero asociado.
     * @param detalles Lista de detalles del pedido.
     */
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

    /* @return El ID de la comanda. */
    public Long getIdComanda() {
        return idComanda;
    }

    /* @param idComanda El ID a asignar. */
    public void setIdComanda(Long idComanda) {
        this.idComanda = idComanda;
    }

    /* @return El folio de la comanda. */
    public String getFolio() {
        return folio;
    }

    /* @param folio El folio a asignar. */
    public void setFolio(String folio) {
        this.folio = folio;
    }

    /* @return La fecha y hora de la comanda. */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /* @param fecha La fecha a asignar. */
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    /* @return El total de la comanda. */
    public Double getTotal() {
        return total;
    }

    /* @param total El total a asignar. */
    public void setTotal(Double total) {
        this.total = total;
    }

    /* @return El número de mesa. */
    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    /* @param numeroMesa El número de mesa a asignar. */
    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    /* @return El estado de la comanda. */
    public EstadoComanda getEstado() {
        return estado;
    }

    /* @param estado El estado a asignar. */
    public void setEstado(EstadoComanda estado) {
        this.estado = estado;
    }

    /* @return La mesa asociada. */
    public Mesa getMesa() {
        return mesa;
    }

    /* @param mesa La mesa a asignar. */
    public void setMesa(Mesa mesa) {
        this.mesa = mesa;
    }

    /* @return El cliente asociado. */
    public ClienteFrecuente getCliente() {
        return cliente;
    }

    /* @param cliente El cliente a asignar. */
    public void setCliente(ClienteFrecuente cliente) {
        this.cliente = cliente;
    }

    /* @return El mesero asociado. */
    public Mesero getMesero() {
        return mesero;
    }

    /* @param mesero El mesero a asignar. */
    public void setMesero(Mesero mesero) {
        this.mesero = mesero;
    }

    /* @return La lista de detalles del pedido. */
    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    /* @param detalles La lista de detalles a asignar. */
    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }
}