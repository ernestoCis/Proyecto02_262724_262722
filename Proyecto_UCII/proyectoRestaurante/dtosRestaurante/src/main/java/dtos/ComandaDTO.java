/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtos;

import enums.EstadoComandaDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase Data Transfer Object (DTO) que representa la información de una Comanda.
 * <p>Esta clase se utiliza para transportar los datos de una orden de servicio entre las capas
 * de negocio y presentación, incluyendo referencias a la mesa, el mesero, el cliente y la lista de pedidos.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class ComandaDTO {
    /** Identificador único de la comanda en la base de datos. */
    private Long idComanda;
    /** Folio alfanumérico administrativo asignado en la capa de negocio. */
    private String folio; 
    /** Fecha y hora en la que se registró la comanda. */
    private LocalDateTime fecha;
    /** Monto total acumulado de todos los productos en la comanda. */
    private Double total;
    /** Estado actual del flujo de la comanda (ej. ABIERTA, ENTREGADA). */
    private EstadoComandaDTO estado;
    /** Número identificador físico de la mesa asignada. */
    private Integer numeroMesa;
    
    /** Información detallada de la mesa asociada. */
    private MesaDTO mesa;
    /** Información del mesero responsable de atender la comanda. */
    private MeseroDTO mesero;
    /** Información del cliente frecuente asociado a la cuenta, si aplica. */
    private ClienteFrecuenteDTO cliente;
    
    /** Lista de los productos y cantidades que integran el pedido. */
    private List<DetallePedidoDTO> detalles = new ArrayList<>();

    /**
     * Constructor por defecto para crear una instancia vacía de ComandaDTO.
     */
    public ComandaDTO() {
    }

    /**
     * Constructor con parámetros para inicializar una comanda con sus atributos principales.
     * * @param idComanda Identificador único de la comanda.
     * @param folio Folio administrativo generado por la capa de negocio.
     * @param fecha Fecha y hora de creación de la comanda.
     * @param total Monto total acumulado de la orden.
     * @param numeroMesa Número físico de la mesa asignada.
     * @param estado Estado actual de la comanda (ej. ABIERTA, CERRADA).
     * @param mesa Objeto DTO con la información de la mesa.
     * @param mesero Objeto DTO con la información del mesero asignado.
     * @param cliente Objeto DTO con la información del cliente frecuente.
     */
    public ComandaDTO(Long idComanda, String folio, LocalDateTime fecha, Double total, Integer numeroMesa, EstadoComandaDTO estado, MesaDTO mesa, MeseroDTO mesero, ClienteFrecuenteDTO cliente) {
        this.idComanda = idComanda;
        this.folio = folio;
        this.fecha = fecha;
        this.total = total;
        this.numeroMesa = numeroMesa;
        this.estado = estado;
        this.mesa = mesa;
        this.mesero = mesero;
        this.cliente = cliente;
    }

    /**
     * Obtiene el identificador único de la comanda.
     * @return El ID de la comanda.
     */
    public Long getIdComanda() {
        return idComanda;
    }

    /**
     * Asigna el identificador único de la comanda.
     * @param idComanda El ID a asignar.
     */
    public void setIdComanda(Long idComanda) {
        this.idComanda = idComanda;
    }

    /**
     * Obtiene el folio administrativo de la comanda.
     * @return El folio de la comanda.
     */
    public String getFolio() {
        return folio;
    }

    /**
     * Asigna el folio administrativo de la comanda.
     * @param folio El folio a asignar.
     */
    public void setFolio(String folio) {
        this.folio = folio;
    }

    /**
     * Obtiene la fecha y hora de la comanda.
     * @return Objeto LocalDateTime con la fecha.
     */
    public LocalDateTime getFecha() {
        return fecha;
    }

    /**
     * Asigna la fecha y hora de la comanda.
     * @param fecha La fecha a asignar.
     */
    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el monto total de la comanda.
     * @return El total de la orden.
     */
    public Double getTotal() {
        return total;
    }

    /**
     * Asigna el monto total de la comanda.
     * @param total El total a asignar.
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * Obtiene el número físico de la mesa.
     * @return El número de mesa.
     */
    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    /**
     * Asigna el número físico de la mesa.
     * @param numeroMesa El número a asignar.
     */
    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    /**
     * Obtiene el estado actual de la comanda.
     * @return El estado de la comanda.
     */
    public EstadoComandaDTO getEstado() {
        return estado;
    }

    /**
     * Asigna el estado actual de la comanda.
     * @param estado El estado a asignar.
     */
    public void setEstado(EstadoComandaDTO estado) {
        this.estado = estado;
    }

    /**
     * Obtiene la información de la mesa asociada.
     * @return Objeto MesaDTO asociado.
     */
    public MesaDTO getMesa() {
        return mesa;
    }

    /**
     * Asigna la información de la mesa asociada.
     * @param mesa La mesa a asignar.
     */
    public void setMesa(MesaDTO mesa) {
        this.mesa = mesa;
    }

    /**
     * Obtiene la información del mesero que atiende la comanda.
     * @return Objeto MeseroDTO asociado.
     */
    public MeseroDTO getMesero() {
        return mesero;
    }

    /**
     * Asigna la información del mesero que atiende la comanda.
     * @param mesero El mesero a asignar.
     */
    public void setMesero(MeseroDTO mesero) {
        this.mesero = mesero;
    }

    /**
     * Obtiene la información del cliente frecuente.
     * @return Objeto ClienteFrecuenteDTO asociado.
     */
    public ClienteFrecuenteDTO getCliente() {
        return cliente;
    }

    /**
     * Asigna la información del cliente frecuente.
     * @param cliente El cliente a asignar.
     */
    public void setCliente(ClienteFrecuenteDTO cliente) {
        this.cliente = cliente;
    }

    /**
     * Obtiene la lista de detalles del pedido incluidos en la comanda.
     * @return Lista de objetos DetallePedidoDTO.
     */
    public List<DetallePedidoDTO> getDetalles() {
        return detalles;
    }

    /**
     * Asigna la lista de detalles del pedido a la comanda.
     * @param detalles La lista de detalles a asignar.
     */
    public void setDetalles(List<DetallePedidoDTO> detalles) {
        this.detalles = detalles;
    }
}