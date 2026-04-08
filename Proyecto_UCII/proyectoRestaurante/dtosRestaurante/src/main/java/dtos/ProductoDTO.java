/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.util.List;
import enums.DisponibilidadProductoDTO;
import enums.TipoProductoDTO;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ProductoDTO {

    private Long idProducto;
    private String nombre;
    private Double precio;
    private TipoProductoDTO tipo;
    private DisponibilidadProductoDTO disponibilidad;
    private String rutaImagen;
    private List<RecetaDTO> recetas;

    public ProductoDTO() {
    }

    public ProductoDTO(Long idProducto, String nombre, Double precio, TipoProductoDTO tipo, DisponibilidadProductoDTO disponibilidad, String rutaImagen, List<RecetaDTO> recetas) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.disponibilidad = disponibilidad;
        this.rutaImagen = rutaImagen;
        this.recetas = recetas;
    }

    public ProductoDTO(String nombre, Double precio, TipoProductoDTO tipo, DisponibilidadProductoDTO disponibilidad, String rutaImagen, List<RecetaDTO> recetas) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.disponibilidad = disponibilidad;
        this.rutaImagen = rutaImagen;
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

    public TipoProductoDTO getTipo() {
        return tipo;
    }

    public void setTipo(TipoProductoDTO tipo) {
        this.tipo = tipo;
    }

    public DisponibilidadProductoDTO getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(DisponibilidadProductoDTO disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public List<RecetaDTO> getRecetas() {
        return recetas;
    }

    public void setRecetas(List<RecetaDTO> recetas) {
        this.recetas = recetas;
    }
}