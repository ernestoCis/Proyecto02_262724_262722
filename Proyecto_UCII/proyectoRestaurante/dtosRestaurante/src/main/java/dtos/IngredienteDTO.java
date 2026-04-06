/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import enums.UnidadMedida;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class IngredienteDTO {

    private Long idIngrediente;
    private String nombre;
    private UnidadMedida unidadMedida;
    private Double cantidadActual;
    private String rutaImagen;

    public IngredienteDTO() {
    }

    
    public IngredienteDTO(Long idIngrediente, String nombre, UnidadMedida unidadMedida, Double cantidadActual, String rutaImagen) {
        this.idIngrediente = idIngrediente;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.cantidadActual = cantidadActual;
        this.rutaImagen = rutaImagen;
    }

    public IngredienteDTO(String nombre, UnidadMedida unidadMedida, Double cantidadActual, String rutaImagen) {
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.cantidadActual = cantidadActual;
        this.rutaImagen = rutaImagen;
    }

    public Long getIdIngrediente() {
        return idIngrediente;
    }

    public void setIdIngrediente(Long idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Double getCantidadActual() {
        return cantidadActual;
    }

    public void setCantidadActual(Double cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}