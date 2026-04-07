/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class RecetaDTO {

    private Long idReceta;
    private Double cantidad;
    private IngredienteDTO ingrediente;

    public RecetaDTO() {
    }

    public RecetaDTO(Long idReceta, Double cantidad, IngredienteDTO ingrediente) {
        this.idReceta = idReceta;
        this.cantidad = cantidad;
        this.ingrediente = ingrediente;
    }

    public RecetaDTO(Double cantidad, IngredienteDTO ingrediente) {
        this.cantidad = cantidad;
        this.ingrediente = ingrediente;
    }

    public Long getIdReceta() {
        return idReceta;
    }

    public void setIdReceta(Long idReceta) {
        this.idReceta = idReceta;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public IngredienteDTO getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(IngredienteDTO ingrediente) {
        this.ingrediente = ingrediente;
    }
}