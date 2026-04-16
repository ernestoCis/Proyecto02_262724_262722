/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Clase Data Transfer Object (DTO) para representar la información de un Cliente Frecuente.
 * <p>Esta clase se utiliza para transportar datos de clientes entre las capas del sistema,
 * incluyendo información personal, métricas de consumo y puntos de lealtad.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class ClienteFrecuenteDTO implements Serializable {

    /** Identificador único del cliente en la base de datos. */
    private Long idCliente;
    /** Nombre o nombres de pila del cliente. */
    private String nombres;
    /** Primer apellido del cliente. */
    private String apellidoPaterno;
    /** Segundo apellido del cliente. */
    private String apellidoMaterno;
    /** Número telefónico de contacto del cliente. */
    private String telefono;
    /** Dirección de correo electrónico del cliente. */
    private String email;

    /** Cantidad acumulada de visitas realizadas por el cliente. */
    private Integer numeroVisitas;
    /** Monto total monetario que el cliente ha consumido en el establecimiento. */
    private Double totalGastado;
    /** Saldo actual de puntos acumulados en el programa de lealtad. */
    private Integer puntos;

    /** Fecha y hora en la que se registró la última actividad o comanda del cliente. */
    private LocalDateTime ultimaComanda;

    /**
     * Constructor por defecto para la creación de instancias vacías.
     */
    public ClienteFrecuenteDTO() {
    }

    /**
     * Constructor con parámetros esenciales para identificar y calificar al cliente.
     * * @param idCliente Identificador único del cliente.
     * @param nombres Nombre o nombres del cliente.
     * @param apellidoPaterno Apellido paterno.
     * @param apellidoMaterno Apellido materno.
     * @param telefono Número de contacto.
     * @param email Correo electrónico.
     * @param numeroVisitas Cantidad total de visitas registradas.
     * @param totalGastado Sumatoria de montos pagados.
     * @param puntos Saldo de puntos de lealtad.
     */
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

    /**
     * Constructor completo que incluye el registro de la última actividad del cliente.
     * * @param idCliente Identificador único del cliente.
     * @param nombres Nombre o nombres del cliente.
     * @param apellidoPaterno Apellido paterno.
     * @param apellidoMaterno Apellido materno.
     * @param telefono Número de contacto.
     * @param email Correo electrónico.
     * @param numeroVisitas Cantidad total de visitas.
     * @param totalGastado Monto acumulado de gastos.
     * @param puntos Puntos acumulados.
     * @param ultimaComanda Fecha y hora de la última compra realizada.
     */
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

    /**
     * Constructor sin identificador, útil para el registro de nuevos clientes.
     * * @param nombres Nombre o nombres del cliente.
     * @param apellidoPaterno Apellido paterno.
     * @param apellidoMaterno Apellido materno.
     * @param telefono Número de contacto.
     * @param email Correo electrónico.
     * @param numeroVisitas Cantidad de visitas iniciales.
     * @param totalGastado Gasto inicial acumulado.
     * @param puntos Puntos de lealtad iniciales.
     * @param ultimaComanda Registro de actividad reciente.
     */
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

    /**
     * Obtiene el ID del cliente.
     * @return El identificador único.
     */
    public Long getIdCliente() {
        return idCliente;
    }

    /**
     * Asigna el ID del cliente.
     * @param idCliente El identificador a asignar.
     */
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Obtiene los nombres del cliente.
     * @return Los nombres registrados.
     */
    public String getNombres() {
        return nombres;
    }

    /**
     * Asigna los nombres del cliente.
     * @param nombres Los nombres a asignar.
     */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * Obtiene el apellido paterno del cliente.
     * @return El apellido paterno.
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Asigna el apellido paterno del cliente.
     * @param apellidoPaterno El apellido a asignar.
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Obtiene el apellido materno del cliente.
     * @return El apellido materno.
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Asigna el apellido materno del cliente.
     * @param apellidoMaterno El apellido a asignar.
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * Obtiene el teléfono del cliente.
     * @return El número telefónico.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Asigna el teléfono del cliente.
     * @param telefono El número telefónico a asignar.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Obtiene el email del cliente.
     * @return El correo electrónico.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Asigna el email del cliente.
     * @param email El correo electrónico a asignar.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el número de visitas totales.
     * @return Cantidad de visitas.
     */
    public Integer getNumeroVisitas() {
        return numeroVisitas;
    }

    /**
     * Asigna el número de visitas totales.
     * @param numeroVisitas Cantidad de visitas a registrar.
     */
    public void setNumeroVisitas(Integer numeroVisitas) {
        this.numeroVisitas = numeroVisitas;
    }

    /**
     * Obtiene el total gastado por el cliente.
     * @return El monto acumulado.
     */
    public Double getTotalGastado() {
        return totalGastado;
    }

    /**
     * Asigna el total gastado por el cliente.
     * @param totalGastado El monto acumulado a asignar.
     */
    public void setTotalGastado(Double totalGastado) {
        this.totalGastado = totalGastado;
    }

    /**
     * Obtiene los puntos de lealtad actuales.
     * @return El saldo de puntos.
     */
    public Integer getPuntos() {
        return puntos;
    }

    /**
     * Asigna los puntos de lealtad.
     * @param puntos Los puntos a asignar.
     */
    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    /**
     * Genera y obtiene la concatenación del nombre completo del cliente.
     * @return Nombre completo (nombres + apellidos).
     */
    public String getNombreCompleto() {
        return nombres + " " + apellidoPaterno + " " + apellidoMaterno;
    }

    /**
     * Obtiene la fecha y hora de la última comanda.
     * @return Instancia de LocalDateTime con la última actividad.
     */
    public LocalDateTime  getUltimaComanda() {
        return ultimaComanda;
    }

    /**
     * Asigna la fecha y hora de la última comanda.
     * @param ultimaComanda La fecha y hora a registrar.
     */
    public void setUltimaComanda(LocalDateTime ultimaComanda) {
        this.ultimaComanda = ultimaComanda;
    }
}