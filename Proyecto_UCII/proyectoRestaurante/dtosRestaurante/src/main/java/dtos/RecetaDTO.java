/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

/**
 * Clase Data Transfer Object (DTO) que representa un elemento individual de una receta.
 * <p>Esta clase vincula un ingrediente específico con la cantidad necesaria para la 
 * preparación de un producto, permitiendo gestionar la composición técnica de los artículos del menú.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class RecetaDTO {

    /** Identificador único del registro de la receta en la base de datos. */
    private Long idReceta;
    /** Proporción o medida exacta del ingrediente requerida para la elaboración del producto. */
    private Double cantidad;
    /** Información detallada del ingrediente vinculado a este elemento de la receta. */
    private IngredienteDTO ingrediente;

    /**
     * Constructor por defecto para crear una instancia vacía de RecetaDTO.
     */
    public RecetaDTO() {
    }

    /**
     * Constructor completo para inicializar un elemento de la receta con todos sus atributos.
     * * @param idReceta Identificador único del registro de la receta.
     * @param cantidad Cantidad específica del ingrediente requerida.
     * @param ingrediente Objeto DTO que representa el ingrediente a utilizar.
     */
    public RecetaDTO(Long idReceta, Double cantidad, IngredienteDTO ingrediente) {
        this.idReceta = idReceta;
        this.cantidad = cantidad;
        this.ingrediente = ingrediente;
    }

    /**
     * Constructor para inicializar un elemento de receta sin identificador, útil para nuevas asignaciones.
     * * @param cantidad Cantidad del ingrediente requerida para la preparación.
     * @param ingrediente Objeto DTO con la información del ingrediente.
     */
    public RecetaDTO(Double cantidad, IngredienteDTO ingrediente) {
        this.cantidad = cantidad;
        this.ingrediente = ingrediente;
    }

    /**
     * Obtiene el identificador único de la receta.
     * @return El ID de la receta.
     */
    public Long getIdReceta() {
        return idReceta;
    }

    /**
     * Asigna el identificador único de la receta.
     * @param idReceta El ID a asignar.
     */
    public void setIdReceta(Long idReceta) {
        this.idReceta = idReceta;
    }

    /**
     * Obtiene la cantidad del ingrediente establecida en la receta.
     * @return La cantidad requerida.
     */
    public Double getCantidad() {
        return cantidad;
    }

    /**
     * Asigna la cantidad del ingrediente necesaria para la receta.
     * @param cantidad La cantidad a asignar.
     */
    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el ingrediente asociado a este elemento de la receta.
     * @return Objeto IngredienteDTO asociado.
     */
    public IngredienteDTO getIngrediente() {
        return ingrediente;
    }

    /**
     * Asigna el ingrediente correspondiente a este elemento de la receta.
     * @param ingrediente El ingrediente a asignar.
     */
    public void setIngrediente(IngredienteDTO ingrediente) {
        this.ingrediente = ingrediente;
    }
}