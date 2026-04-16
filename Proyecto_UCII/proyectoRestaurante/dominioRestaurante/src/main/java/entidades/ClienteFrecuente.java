package entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.annotations.Formula;

/**
 * Representa la entidad <b>ClienteFrecuente</b> en el sistema.
 * <p>Esta clase extiende de {@link Cliente} y añade campos calculados mediante
 * fórmulas de Hibernate para la gestión de fidelización.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
@Entity
@Table(name = "clientes_frecuentes")
public class ClienteFrecuente extends Cliente implements Serializable {

    /** * Cantidad total de comandas realizadas por el cliente.
     * <p>Calculado mediante una subconsulta que cuenta los registros en la tabla <b>comandas</b>.</p>
     */
    @Transient
    @Formula("(SELECT COUNT(c) FROM comandas c WHERE c.idCliente = idCliente)")
    private Integer numeroVisitas;
    
    /** * Monto total acumulado de todas las compras del cliente.
     * <p>Calculado sumando el campo <b>total</b> de sus comandas asociadas.</p>
     */
    @Transient
    @Formula("(SELECT SUM(c.total) FROM comandas c WHERE c.idCliente = idCliente)")
    private Double totalGastado;
    
    /** * Puntos de fidelidad acumulados.
     * <p>Calculado dividiendo el <b>totalGastado</b> entre 20 y aplicando un redondeo hacia abajo.</p>
     */
    @Transient
    @Formula("(SELECT FLOOR(SUM(c.total) / 20) FROM comandas c WHERE c.idCliente = idCliente)")
    private Integer puntos;

    /**
     * Constructor por defecto.
     * <p>Invoca el constructor de la clase superior {@link Cliente}.</p>
     */
    public ClienteFrecuente() {
        super();
    }

    /**
     * Constructor que inicializa los campos calculados.
     * @param numeroVisitas Cantidad de visitas inicial.
     * @param totalGastado Suma total inicial.
     * @param puntos Puntos acumulados iniciales.
     */
    public ClienteFrecuente(Integer numeroVisitas, Double totalGastado, Integer puntos) {
        this.numeroVisitas = numeroVisitas;
        this.totalGastado = totalGastado;
        this.puntos = puntos;
    }

    /**
     * Constructor completo con atributos de la clase padre y atributos propios.
     * @param numeroVisitas Cantidad de visitas.
     * @param totalGastado Monto total gastado.
     * @param puntos Puntos de fidelidad.
     * @param idCliente Identificador único.
     * @param nombres Nombres del cliente.
     * @param apellidoPaterno Apellido paterno.
     * @param apellidoMaterno Apellido materno.
     * @param telefono Teléfono de contacto.
     * @param email Correo electrónico.
     * @param fechaRegistro Fecha de alta.
     * @param comandas Lista de historial de pedidos.
     */
    public ClienteFrecuente(Integer numeroVisitas, Double totalGastado, Integer puntos, Long idCliente, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String email, LocalDate fechaRegistro, List<Comanda> comandas) {
        super(idCliente, nombres, apellidoPaterno, apellidoMaterno, telefono, email, fechaRegistro, comandas);
        this.numeroVisitas = numeroVisitas;
        this.totalGastado = totalGastado;
        this.puntos = puntos;
    }

    /* @return El número total de visitas/comandas. */
    public Integer getNumeroVisitas() {
        return numeroVisitas;
    }

    /* @param numeroVisitas El número de visitas a asignar. */
    public void setNumeroVisitas(Integer numeroVisitas) {
        this.numeroVisitas = numeroVisitas;
    }

    /* @return El monto total gastado en el establecimiento. */
    public Double getTotalGastado() {
        return totalGastado;
    }

    /* @param totalGastado El monto total a asignar. */
    public void setTotalGastado(Double totalGastado) {
        this.totalGastado = totalGastado;
    }

    /* @return La cantidad de puntos acumulados para canje. */
    public Integer getPuntos() {
        return puntos;
    }

    /* @param puntos Los puntos a asignar. */
    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }
}