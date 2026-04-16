/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import java.io.Serializable;

/**
 * Clase Data Transfer Object (DTO) que representa la información de un Mesero.
 * <p>Esta clase se utiliza para transportar los datos del personal de servicio entre las capas
 * del sistema, incluyendo sus credenciales de acceso (usuario) e información de identidad personal.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class MeseroDTO implements Serializable{
    
    /** Nombre de usuario asignado para el ingreso al sistema. */
    private String usuario;
    
    /** Identificador único del mesero en la base de datos. */
    private Long idMesero;
    /** Registro Federal de Contribuyentes (RFC) del mesero. */
    private String rfc;
    /** Nombre o nombres de pila del mesero. */
    private String nombre;
    /** Primer apellido del mesero. */
    private String apellidoPaterno;
    /** Segundo apellido del mesero. */
    private String apellidoMaterno;

    /**
     * Constructor por defecto para crear una instancia vacía de MeseroDTO.
     */
    public MeseroDTO() {
    }

    /**
     * Obtiene el nombre de usuario para el acceso al sistema.
     * @return El nombre de usuario.
     */
    public String getUsuario() {
        return usuario;
    }

    /**
     * Asigna el nombre de usuario para el acceso al sistema.
     * @param usuario El nombre de usuario a asignar.
     */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene el identificador único del mesero.
     * @return El ID del mesero.
     */
    public Long getIdMesero() {
        return idMesero;
    }

    /**
     * Asigna el identificador único del mesero.
     * @param idMesero El ID a asignar.
     */
    public void setIdMesero(Long idMesero) {
        this.idMesero = idMesero;
    }

    /**
     * Obtiene el RFC del mesero.
     * @return El Registro Federal de Contribuyentes.
     */
    public String getRfc() {
        return rfc;
    }

    /**
     * Asigna el RFC del mesero.
     * @param rfc El RFC a asignar.
     */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /**
     * Obtiene el nombre o nombres del mesero.
     * @return El nombre del mesero.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Asigna el nombre o nombres del mesero.
     * @param nombre El nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el apellido paterno del mesero.
     * @return El apellido paterno.
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Asigna el apellido paterno del mesero.
     * @param apellidoPaterno El apellido paterno a asignar.
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Obtiene el apellido materno del mesero.
     * @return El apellido materno.
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Asigna el apellido materno del mesero.
     * @param apellidoMaterno El apellido materno a asignar.
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
}