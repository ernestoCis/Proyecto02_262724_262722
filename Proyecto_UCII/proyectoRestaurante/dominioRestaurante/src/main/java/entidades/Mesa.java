/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.EstadoMesa;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
@Entity
@Table(name = "Mesas")
public class Mesa implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMesa;
    
    @Column(nullable = false, unique = true)
    private Integer numero;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMesa disponibilidad;

    public Mesa() {
    }

    public Mesa(Long idMesa, Integer numero, EstadoMesa disponibilidad) {
        this.idMesa = idMesa;
        this.numero = numero;
        this.disponibilidad = disponibilidad;
    }

    public Long getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Long idMesa) {
        this.idMesa = idMesa;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public EstadoMesa getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(EstadoMesa disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}
