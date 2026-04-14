/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import enums.DisponibilidadProducto;
import enums.TipoProducto;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
@Entity
@Table(name = "productos")
public class Producto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idProducto;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private Double precio;

    @Enumerated(EnumType.STRING)
    private TipoProducto tipo;
    
    @Column(nullable = true)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private DisponibilidadProducto disponibilidad;
    
    private String rutaImg;

    @OneToMany(mappedBy = "producto",  cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Receta> recetas = new ArrayList<>();

    public Producto() {
    }

    public Producto(Long idProducto, String nombre, Double precio, TipoProducto tipo, String descripcion, DisponibilidadProducto disponibilidad, String rutaImg) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.disponibilidad = disponibilidad;
        this.rutaImg = rutaImg;
    }

    public Producto(String nombre, Double precio, TipoProducto tipo, String descripcion, DisponibilidadProducto disponibilidad, String rutaImg) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.disponibilidad = disponibilidad;
        this.rutaImg = rutaImg;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public TipoProducto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }

    public DisponibilidadProducto getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(DisponibilidadProducto disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public List<Receta> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<Receta> recetas) {
        this.recetas = recetas;
    }

    public String getRutaImg() {
        return rutaImg;
    }

    public void setRutaImg(String rutaImg) {
        this.rutaImg = rutaImg;
    }
    
    public void agregarReceta(Receta receta) {
        recetas.add(receta);
        receta.setProducto(this);
    }

    public void eliminarReceta(Receta receta) {
        recetas.remove(receta);
        receta.setProducto(null);
    }
}