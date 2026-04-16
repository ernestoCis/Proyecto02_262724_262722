/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 * Representa la clase base abstracta <b>Empleado</b> en el sistema.
 * <p>Esta clase utiliza la estrategia de herencia <b>JOINED</b>, lo que significa que 
 * cada subclase tendrá su propia tabla vinculada por una clave primaria a la tabla "Empleados".</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
@Entity
@Table(name = "Empleados")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Empleado implements Serializable{
    
    /** Identificador único del empleado (Clave Primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmpleado;
    
    /** Registro Federal de Contribuyentes (RFC) del empleado. */
    private String rfc;
    
    /** Nombre o nombres del empleado. */
    private String nombres;
    
    /** Apellido paterno del empleado. */
    private String apellidoPaterno;
    
    /** Apellido materno del empleado. */
    private String apellidoMaterno;

    /**
     * Constructor por defecto.
     */
    public Empleado() {
    }

    /**
     * Constructor completo para inicializar todos los atributos del empleado.
     * * @param idEmpleado Identificador único.
     * @param rfc RFC del empleado.
     * @param nombres Nombres del empleado.
     * @param apellidoPaterno Apellido paterno.
     * @param apellidoMaterno Apellido materno.
     */
    public Empleado(Long idEmpleado, String rfc, String nombres, String apellidoPaterno, String apellidoMaterno) {
        this.idEmpleado = idEmpleado;
        this.rfc = rfc;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
    }

    /* @return El ID del empleado. */
    public Long getIdEmpleado() {
        return idEmpleado;
    }

    /* @param idEmpleado El ID a asignar. */
    public void setIdEmpleado(Long idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    /* @return El RFC del empleado. */
    public String getRfc() {
        return rfc;
    }

    /* @param rfc El RFC a asignar. */
    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    /* @return Los nombres del empleado. */
    public String getNombres() {
        return nombres;
    }

    /* @param nombres Los nombres a asignar. */
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /* @return El apellido paterno. */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /* @param apellidoPaterno El apellido paterno a asignar. */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /* @return El apellido materno. */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /* @param apellidoMaterno El apellido materno a asignar. */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }
}