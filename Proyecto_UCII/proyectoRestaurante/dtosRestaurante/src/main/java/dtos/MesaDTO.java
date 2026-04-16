/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import enums.EstadoMesaDTO;

/**
 * Clase Data Transfer Object (DTO) que representa una mesa del establecimiento.
 * <p>Esta clase se utiliza para gestionar la información de las mesas, permitiendo 
 * conocer su número identificador y su estado de disponibilidad actual para la 
 * asignación de nuevas comandas.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class MesaDTO {
    
    /** Identificador único de la mesa en la base de datos. */
    private Long idMesa;
    /** Número físico o comercial asignado a la mesa en el establecimiento. */
    private Integer numero;
    /** Estado actual de disponibilidad de la mesa (ej. DISPONIBLE, NO_DISPONIBLE). */
    private EstadoMesaDTO disponibilidad;
    
    /**
     * Constructor por defecto para crear una instancia vacía de MesaDTO.
     */
    public MesaDTO(){
    }
    
    /**
     * Constructor para inicializar una mesa con su número y estado de disponibilidad.
     * * @param numero El número físico asignado a la mesa.
     * @param disponibilidad El estado actual de la mesa (ej. DISPONIBLE, OCUPADA).
     */
    public MesaDTO(Integer numero, EstadoMesaDTO disponibilidad){
        this.numero = numero;
        this.disponibilidad = disponibilidad;
    }

    /**
     * Obtiene el identificador único de la mesa en la base de datos.
     * @return El ID de la mesa.
     */
    public Long getIdMesa() {
        return idMesa;
    }

    /**
     * Asigna el identificador único de la mesa.
     * @param idMesa El ID a asignar.
     */
    public void setIdMesa(Long idMesa) {
        this.idMesa = idMesa;
    }

    /**
     * Obtiene el número físico de la mesa.
     * @return El número de la mesa.
     */
    public Integer getNumero() {
        return numero;
    }

    /**
     * Asigna el número físico de la mesa.
     * @param numero El número a asignar.
     */
    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    /**
     * Obtiene el estado de disponibilidad de la mesa.
     * @return El estado actual de la mesa (DTO).
     */
    public EstadoMesaDTO getDisponibilidad() {
        return disponibilidad;
    }

    /**
     * Asigna el estado de disponibilidad de la mesa.
     * @param disponibilidad El estado a asignar.
     */
    public void setDisponibilidad(EstadoMesaDTO disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}