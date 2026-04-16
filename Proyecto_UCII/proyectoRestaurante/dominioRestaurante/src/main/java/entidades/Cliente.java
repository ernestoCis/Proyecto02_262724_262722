/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.time.LocalDate;
import java.util.List;
import jakarta.persistence.*;
import java.io.Serializable;
import utilerias.EncriptadorTelefono;

/**
 * Representa la entidad <b>Cliente</b> en el sistema.
 * <p>Esta clase mapea a la tabla "clientes" y sirve como base para la jerarquía de clientes.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
@Entity
@Table(name = "clientes")
@Inheritance(strategy = InheritanceType.JOINED)
public class Cliente implements Serializable {
    
    /** Identificador único del cliente (Clave Primaria). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;

    /** Nombre o nombres del cliente. */
    private String nombres;
    
    /** Apellido paterno del cliente. */
    private String apellidoPaterno;
    
    /** Apellido materno del cliente. */
    private String apellidoMaterno;
    
    /** * Teléfono de contacto. 
     * <p>Utiliza el convertidor {@link EncriptadorTelefono} para seguridad.</p> 
     */
    @Column(nullable = false)
    @Convert(converter = EncriptadorTelefono.class)
    private String telefono;
    
    /** Correo electrónico del cliente. */
    private String email;

    /** Fecha en la que el cliente fue dado de alta en el sistema. */
    private LocalDate fechaRegistro;

    /** Lista de comandas asociadas a este cliente. */
    @OneToMany(mappedBy = "cliente")
    private List<Comanda> comandas;

    /**
     * Constructor por defecto.
     * <p>Inicializa la <b>fechaRegistro</b> con la fecha actual del sistema.</p>
     */
    public Cliente() {
        this.fechaRegistro = LocalDate.now();
    }

    /**
     * Constructor con todos los campos.
     * * @param idCliente Identificador único.
     * @param nombres Nombres del cliente.
     * @param apellidoPaterno Apellido paterno.
     * @param apellidoMaterno Apellido materno.
     * @param telefono Número telefónico.
     * @param email Dirección de correo.
     * @param fechaRegistro Fecha de registro.
     * @param comandas Lista de pedidos asociados.
     */
    public Cliente(Long idCliente, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String email, LocalDate fechaRegistro, List<Comanda> comandas) {
        this.idCliente = idCliente;
        this.nombres = nombres;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.telefono = telefono;
        this.email = email;
        this.fechaRegistro = fechaRegistro;
        this.comandas = comandas;
    }

    /* @return El ID del cliente. */
    public Long getIdCliente() {
        return idCliente;
    }

    /* @param idCliente El ID a asignar. */
    public void setIdCliente(Long idCliente) {
        this.idCliente = idCliente;
    }

    /* @return Los nombres del cliente. */
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

    /* @return El teléfono de contacto. */
    public String getTelefono() {
        return telefono;
    }

    /* @param telefono El teléfono a asignar. */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /* @return El correo electrónico. */
    public String getEmail() {
        return email;
    }

    /* @param email El correo a asignar. */
    public void setEmail(String email) {
        this.email = email;
    }

    /* @return La fecha de registro. */
    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    /* @param fechaRegistro La fecha a asignar. */
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    /* @return La lista de comandas vinculadas. */
    public List<Comanda> getComandas() {
        return comandas;
    }

    /* @param comandas La lista de comandas a asignar. */
    public void setComandas(List<Comanda> comandas) {
        this.comandas = comandas;
    }
}