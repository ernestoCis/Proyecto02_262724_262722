/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.EstadoMesa;
import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Representa la entidad <b>Mesa</b> en el sistema.
 * <p>Esta clase mapea a la tabla "Mesas" y gestiona la información física y 
 * el estado operativo de las mesas del establecimiento.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
@Entity
@Table(name = "Mesas")
public class Mesa implements Serializable{
    
    /** Identificador único de la mesa (Clave Primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMesa;
    
    /** * Número identificador de la mesa.
     * <p>Este valor es obligatorio y no puede repetirse en la base de datos.</p>
     */
    @Column(nullable = false, unique = true)
    private Integer numero;
    
    /** * Estado de disponibilidad actual de la mesa.
     * <p>Se almacena como una cadena de texto basada en el enumerado {@link EstadoMesa}.</p>
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMesa disponibilidad;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Mesa() {
    }

    /**
     * Constructor completo para inicializar todos los atributos de la mesa.
     * * @param idMesa Identificador único.
     * @param numero Número de la mesa.
     * @param disponibilidad Estado inicial de la mesa.
     */
    public Mesa(Long idMesa, Integer numero, EstadoMesa disponibilidad) {
        this.idMesa = idMesa;
        this.numero = numero;
        this.disponibilidad = disponibilidad;
    }

    /* @return El ID de la mesa. */
    public Long getIdMesa() {
        return idMesa;
    }

    /* @param idMesa El ID a asignar. */
    public void setIdMesa(Long idMesa) {
        this.idMesa = idMesa;
    }

    /* @return El número identificador de la mesa. */
    public Integer getNumero() {
        return numero;
    }

    /* @param numero El número a asignar. */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    /* @return El estado de disponibilidad de la mesa. */
    public EstadoMesa getDisponibilidad() {
        return disponibilidad;
    }

    /* @param disponibilidad El estado de disponibilidad a asignar. */
    public void setDisponibilidad(EstadoMesa disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}