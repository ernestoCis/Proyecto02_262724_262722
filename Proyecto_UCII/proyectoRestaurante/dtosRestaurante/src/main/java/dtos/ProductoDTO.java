/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.List;
import enums.DisponibilidadProducto;
import enums.TipoProducto;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ProductoDTO {

    private Long idProducto;
    private String nombre;
    private Double precio;
    private TipoProducto tipo;
    private DisponibilidadProducto disponibilidad;

    private List<RecetaDTO> recetas;

    public ProductoDTO() {
    }

    public ProductoDTO(Long idProducto, String nombre, Double precio, TipoProducto tipo, DisponibilidadProducto disponibilidad, List<RecetaDTO> recetas) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.disponibilidad = disponibilidad;
        this.recetas = recetas;
    }

    public ProductoDTO(String nombre, Double precio, TipoProducto tipo, DisponibilidadProducto disponibilidad, List<RecetaDTO> recetas) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.disponibilidad = disponibilidad;
        this.recetas = recetas;
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

    public List<RecetaDTO> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<RecetaDTO> recetas) {
        this.recetas = recetas;
    }
    
    
}
