/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import enums.UnidadMedidaDTO;
import java.util.List;

/**
 * Clase Data Transfer Object (DTO) que representa un ingrediente en el sistema.
 * <p>Se utiliza para transferir información sobre los insumos disponibles en el inventario,
 * incluyendo su nombre, unidad de medida, existencias actuales y referencia visual.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class IngredienteDTO {

    /** Identificador único del ingrediente en la base de datos. */
    private Long idIngrediente;
    /** Nombre descriptivo del insumo o ingrediente. */
    private String nombre;
    /** Unidad de medida utilizada para cuantificar el ingrediente (GRAMOS, MILILITROS, etc.). */
    private UnidadMedidaDTO unidadMedida;
    /** Cantidad física disponible actualmente en el almacén o inventario. */
    private Double cantidadActual;
    /** Ruta del sistema de archivos o URL donde se almacena la imagen del ingrediente. */
    private String rutaImagen;

    /**
     * Constructor por defecto para crear una instancia vacía de IngredienteDTO.
     */
    public IngredienteDTO() {
    }

    /**
     * Constructor completo para inicializar un ingrediente con todos sus atributos, incluyendo ID.
     * * @param idIngrediente Identificador único del ingrediente.
     * @param nombre Nombre descriptivo del insumo.
     * @param unidadMedida Unidad en la que se cuantifica el ingrediente (ej. GRAMOS, PIEZAS).
     * @param cantidadActual Cantidad disponible en el inventario.
     * @param rutaImagen Ruta de la imagen del ingrediente para la interfaz.
     */
    public IngredienteDTO(Long idIngrediente, String nombre, UnidadMedidaDTO unidadMedida, Double cantidadActual, String rutaImagen) {
        this.idIngrediente = idIngrediente;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.cantidadActual = cantidadActual;
        this.rutaImagen = rutaImagen;
    }

    /**
     * Constructor para inicializar un ingrediente sin identificador, útil para registros nuevos.
     * * @param nombre Nombre descriptivo del insumo.
     * @param unidadMedida Unidad en la que se cuantifica el ingrediente.
     * @param cantidadActual Stock inicial del ingrediente.
     * @param rutaImagen Ubicación del archivo de imagen.
     */
    public IngredienteDTO(String nombre, UnidadMedidaDTO unidadMedida, Double cantidadActual, String rutaImagen) {
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.cantidadActual = cantidadActual;
        this.rutaImagen = rutaImagen;
    }

    /**
     * Obtiene el identificador único del ingrediente.
     * @return El ID del ingrediente.
     */
    public Long getIdIngrediente() {
        return idIngrediente;
    }

    /**
     * Asigna el identificador único al ingrediente.
     * @param idIngrediente El ID a asignar.
     */
    public void setIdIngrediente(Long idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    /**
     * Obtiene el nombre del ingrediente.
     * @return El nombre del insumo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre al ingrediente.
     * @param nombre El nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la unidad de medida del ingrediente.
     * @return La unidad de medida (DTO).
     */
    public UnidadMedidaDTO getUnidadMedida() {
        return unidadMedida;
    }

    /**
     * Asigna la unidad de medida al ingrediente.
     * @param unidadMedida La unidad de medida a asignar.
     */
    public void setUnidadMedida(UnidadMedidaDTO unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    /**
     * Obtiene la cantidad actual disponible en inventario.
     * @return El stock actual.
     */
    public Double getCantidadActual() {
        return cantidadActual;
    }

    /**
     * Asigna la cantidad disponible en inventario.
     * @param cantidadActual La cantidad a registrar.
     */
    public void setCantidadActual(Double cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    /**
     * Obtiene la ruta de la imagen asociada al ingrediente.
     * @return La cadena con la ruta de la imagen.
     */
    public String getRutaImagen() {
        return rutaImagen;
    }

    /**
     * Asigna la ruta de la imagen asociada al ingrediente.
     * @param rutaImagen La ruta a asignar.
     */
    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}