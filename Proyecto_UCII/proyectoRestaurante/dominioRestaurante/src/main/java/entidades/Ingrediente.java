/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.UnidadMedida;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa la entidad <b>Ingrediente</b> en el sistema.
 * <p>Esta clase mapea a la tabla "ingredientes" y gestiona la información de los insumos
 * básicos del inventario, incluyendo sus niveles de stock y su participación en diversas recetas.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
@Entity
@Table(name = "ingredientes")
public class Ingrediente implements Serializable {
    
    /** Identificador único del ingrediente (Clave Primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idIngrediente;
    
    /** Nombre descriptivo del ingrediente (ej. "Harina", "Jitomate"). */
    @Column(nullable = false)
    private String nombre;
    
    /** * Unidad de medida utilizada para el inventario.
     * <p>Almacenado como una cadena de texto según el enumerado {@link UnidadMedida}.</p>
     */
    @Enumerated(EnumType.STRING)
    private UnidadMedida unidadMedida;
    
    /** Cantidad disponible actualmente en el inventario. */
    @Column(nullable = false)
    private Double cantidadActual;
    
    /** Ruta de acceso a la imagen representativa del ingrediente en el sistema de archivos. */
    private String rutaImg;

    /** * Listado de recetas donde este ingrediente es utilizado.
     * <p>Relación de uno a muchos con la entidad {@link Receta}.</p>
     */
    @OneToMany(mappedBy = "ingrediente",  cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Receta> recetas = new ArrayList<>();
    
    /**
     * Constructor por defecto requerido por JPA.
     */
    public Ingrediente() {
    }

    /**
     * Constructor completo incluyendo el identificador único.
     * * @param idIngrediente Identificador único.
     * @param nombre Nombre del insumo.
     * @param unidadMedida Unidad de medida (Kg, L, Pza, etc.).
     * @param cantidadActual Stock actual.
     * @param rutaImg Ubicación de la imagen.
     */
    public Ingrediente(Long idIngrediente, String nombre, UnidadMedida unidadMedida, Double cantidadActual, String rutaImg) {
        this.idIngrediente = idIngrediente;
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.cantidadActual = cantidadActual;
        this.rutaImg = rutaImg;
    }

    /**
     * Constructor para inicializar un nuevo ingrediente (sin ID).
     * * @param nombre Nombre del insumo.
     * @param unidadMedida Unidad de medida.
     * @param cantidadActual Stock inicial.
     * @param rutaImg Ubicación de la imagen.
     */
    public Ingrediente(String nombre, UnidadMedida unidadMedida, Double cantidadActual, String rutaImg) {
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
        this.cantidadActual = cantidadActual;
        this.rutaImg = rutaImg;
    }

    /* @return El ID del ingrediente. */
    public Long getIdIngrediente() {
        return idIngrediente;
    }

    /* @param idIngrediente El ID a asignar. */
    public void setIdIngrediente(Long idIngrediente) {
        this.idIngrediente = idIngrediente;
    }

    /* @return El nombre del ingrediente. */
    public String getNombre() {
        return nombre;
    }

    /* @param nombre El nombre a asignar. */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /* @return La unidad de medida del ingrediente. */
    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    /* @param unidadMedida La unidad de medida a asignar. */
    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    /* @return La cantidad disponible en stock. */
    public Double getCantidadActual() {
        return cantidadActual;
    }

    /* @param cantidadActual La cantidad a asignar al stock. */
    public void setCantidadActual(Double cantidadActual) {
        this.cantidadActual = cantidadActual;
    }

    /* @return La ruta de la imagen del ingrediente. */
    public String getRutaImg() {
        return rutaImg;
    }

    /* @param rutaImg La ruta de imagen a asignar. */
    public void setRutaImg(String rutaImg) {
        this.rutaImg = rutaImg;
    }
}