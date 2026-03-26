package entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * 
 * @author Paulina Guevara, Ernesto Cisneros
 */
@Entity
@Table(name = "clientes_frecuentes")
public class ClienteFrecuente extends Cliente implements Serializable {

    @Transient
    private Integer numeroVisitas;
    
    @Transient
    private Double totalGastado;
    
    @Transient
    private Integer puntos;

    public ClienteFrecuente() {
        super();
    }

    public ClienteFrecuente(Integer numeroVisitas, Double totalGastado, Integer puntos) {
        this.numeroVisitas = numeroVisitas;
        this.totalGastado = totalGastado;
        this.puntos = puntos;
    }

    public ClienteFrecuente(Integer numeroVisitas, Double totalGastado, Integer puntos, Long idCliente, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, String email, LocalDate fechaRegistro, List<Comanda> comandas) {
        super(idCliente, nombres, apellidoPaterno, apellidoMaterno, telefono, email, fechaRegistro, comandas);
        this.numeroVisitas = numeroVisitas;
        this.totalGastado = totalGastado;
        this.puntos = puntos;
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
}
