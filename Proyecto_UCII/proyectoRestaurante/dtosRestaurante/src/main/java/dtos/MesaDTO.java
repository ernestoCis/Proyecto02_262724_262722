/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import enums.EstadoMesaDTO;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class MesaDTO {
    private Long idMesa;
    private Integer numero;
    private EstadoMesaDTO disponibilidad;
    
    public MesaDTO(){
    }
    
    public MesaDTO(Integer numero, EstadoMesaDTO disponibilidad){
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

    public EstadoMesaDTO getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(EstadoMesaDTO disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}
