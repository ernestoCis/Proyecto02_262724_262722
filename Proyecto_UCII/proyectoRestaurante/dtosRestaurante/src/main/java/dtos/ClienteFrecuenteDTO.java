/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ClienteFrecuenteDTO implements Serializable {

    private Long idCliente;
    private String nombres;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String telefono;
    private String email;

    private Integer numeroVisitas;
    private Double totalGastado;
    private Integer puntos;

    private LocalDateTime ultimaComanda;

    public ClienteFrecuenteDTO() {
    }

    public ClienteFrecuenteDTO(Long idCliente, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String email, Integer numeroVisitas, Double totalGastado, Integer puntos) {
        this.idCliente = idCliente;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.email = email;
        this.numeroVisitas = numeroVisitas;
        this.totalGastado = totalGastado;
        this.puntos = puntos;
    }

    public ClienteFrecuenteDTO(Long idCliente, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String email, Integer numeroVisitas, Double totalGastado, Integer puntos, LocalDateTime ultimaComanda) {
        this.idCliente = idCliente;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.email = email;
        this.numeroVisitas = numeroVisitas;
        this.totalGastado = totalGastado;
        this.puntos = puntos;
        this.ultimaComanda = ultimaComanda;
    }

    public ClienteFrecuenteDTO(String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String email, Integer numeroVisitas, Double totalGastado, Integer puntos, LocalDateTime ultimaComanda) {
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.email = email;
        this.numeroVisitas = numeroVisitas;
        this.totalGastado = totalGastado;
        this.puntos = puntos;
        this.ultimaComanda = ultimaComanda;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getNumeroVisitas() {
        return numeroVisitas;
    }

    public void setNumeroVisitas(Integer numeroVisitas) {
        this.numeroVisitas = numeroVisitas;
    }

    public Double getTotalGastado() {
        return totalGastado;
    }

    public void setTotalGastado(Double totalGastado) {
        this.totalGastado = totalGastado;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidoPaterno + " " + apellidoMaterno;
    }

    public LocalDateTime  getUltimaComanda() {
        return ultimaComanda;
    }

    public void setUltimaComanda(LocalDateTime ultimaComanda) {
        this.ultimaComanda = ultimaComanda;
    }
}