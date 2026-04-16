/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Representa la entidad <b>Mesero</b> en el sistema.
 * <p>Esta clase extiende de {@link Empleado} y añade credenciales de acceso 
 * específicas para que el personal pueda interactuar con el sistema de comandas.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
@Entity
@Table(name = "Meseros")
public class Mesero extends Empleado {
    
    /** * Nombre de usuario único para el acceso al sistema.
     * <p>Este campo es obligatorio y no permite duplicados en la base de datos.</p>
     */
    @Column(name = "usuario", unique = true, nullable = false)
    private String usuario;

    /**
     * Constructor por defecto.
     * <p>Invoca el constructor de la clase superior {@link Empleado}.</p>
     */
    public Mesero() {
        super();
    }

    /**
     * Constructor que inicializa únicamente el nombre de usuario.
     * @param usuario Nombre de usuario para el inicio de sesión.
     */
    public Mesero(String usuario) {
        this.usuario = usuario;
    }
    
    /**
     * Constructor completo que incluye la información personal heredada de Empleado.
     * @param idEmpleado Identificador único del empleado.
     * @param rfc Registro Federal de Contribuyentes.
     * @param nombres Nombre o nombres del mesero.
     * @param apellidoPaterno Apellido paterno.
     * @param apellidoMaterno Apellido materno.
     * @param usuario Nombre de usuario para el sistema.
     */
    public Mesero(Long idEmpleado, String rfc, String nombres, String apellidoPaterno, String apellidoMaterno, String usuario){
        super(idEmpleado, rfc, nombres, apellidoPaterno, apellidoMaterno);
        this.usuario = usuario;
    }

    /* @return El nombre de usuario del mesero. */
    public String getUsuario() {
        return usuario;
    }

    /* @param usuario El nombre de usuario a asignar. */
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}