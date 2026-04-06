/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import jakarta.persistence.*;
import java.io.Serializable;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
@Entity
@Table(name = "Meseros")
public class Mesero extends Empleado{
    
    @Column(name = "usuario", unique = true, nullable = false)
    private String usuario;

    public Mesero() {
        super();
    }

    public Mesero(String usuario) {
        this.usuario = usuario;
    }
    
    public Mesero(Long idEmpleado, String rfc, String nombres, String apellidoPaterno, String apellidoMaterno, String usuario){
        super(idEmpleado, rfc, nombres, apellidoPaterno, apellidoMaterno);
        this.usuario = usuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
