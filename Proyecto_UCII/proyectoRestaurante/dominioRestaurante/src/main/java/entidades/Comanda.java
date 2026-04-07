/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.EstadoComanda;
import jakarta.persistence.*;
import java.time.LocalDateTime;

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
    
    @Enumerated(EnumType.STRING)
    private EstadoComanda estado;
    
    @ManyToOne
    @JoinColumn(name = "idMesa", nullable = true)
    private Mesa mesa;
    
    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;
    
    @ManyToOne
    @JoinColumn(name = "idMesero", nullable = false)
    private Mesero mesero;
    
//    @OneToMany(mappedBy = "comanda", cascade = CascadeType.ALL)
//    private List<DetallePedido> detalles;

    public Comanda() {
        this.fecha = LocalDateTime.now();
        this.estado = EstadoComanda.ABIERTA;
    }
}