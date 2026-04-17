/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import dtos.ComandaDTO;
import dtos.DetallePedidoDTO;
import dtos.ReporteComandaDTO;
import entidades.Comanda;
import entidades.DetallePedido;
import enums.EstadoComanda;
import enums.EstadoComandaDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase adaptadora para la entidad Comanda. Centraliza la lógica de conversión
 * entre el modelo de persistencia y los objetos de transferencia de datos,
 * gestionando la integridad de las relaciones con Mesas, Meseros, Clientes y la
 * lista detallada de productos solicitados.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class ComandaAdapter {

    /**
     * Transforma un objeto {@link ComandaDTO} a una entidad {@link Comanda}.
     * Este método asegura la integridad referencial al establecer la relación
     * bidireccional entre la comanda y cada uno de sus detalles de pedido,
     * evitando valores nulos en la base de datos.
     *
     * * @param dto El DTO proveniente de la capa de vista o servicio.
     * @return Una entidad de persistencia lista para JPA, o null si el DTO es
     * nulo.
     */
    public static Comanda dtoAEntidad(ComandaDTO dto) {
        if (dto == null) {
            return null;
        }

        Comanda entidad = new Comanda();

        // Mapeo de campos básicos
        entidad.setIdComanda(dto.getIdComanda());
        entidad.setFolio(dto.getFolio());
        entidad.setFecha(dto.getFecha());
        entidad.setTotal(dto.getTotal());
        entidad.setNumeroMesa(dto.getNumeroMesa());

        if (dto.getEstado() == EstadoComandaDTO.ABIERTA) {
            entidad.setEstado(EstadoComanda.ABIERTA);
        } else if (dto.getEstado() == EstadoComandaDTO.CANCELADA) {
            entidad.setEstado(EstadoComanda.CANCELADA);
        } else if (dto.getEstado() == EstadoComandaDTO.ENTREGADA) {
            entidad.setEstado(EstadoComanda.ENTREGADA);
        }

        entidad.setMesa(MesaAdapter.dtoAEntidad(dto.getMesa()));
        entidad.setMesero(MeseroAdapter.dtoAEntidad(dto.getMesero()));
        entidad.setCliente(ClienteFrecuenteAdapter.dtoAEntidadExistente(dto.getCliente()));

        // Mapeo de Detalles
        if (dto.getDetalles() != null) {
            List<DetallePedido> detallesEntidad = new ArrayList<>();
            for (DetallePedidoDTO detalleDTO : dto.getDetalles()) {

                DetallePedido detalleEntidad = DetallePedidoAdapter.dtoAEntidadExistente(detalleDTO);

                //sin esto la columna idComanda en la tabla de detalles sera NULL
                detalleEntidad.setComanda(entidad);

                detallesEntidad.add(detalleEntidad);
            }
            entidad.setDetalles(detallesEntidad);
        }

        return entidad;
    }

    /**
     * Convierte una entidad {@link Comanda} de la base de datos a un
     * {@link ComandaDTO}. Útil para mostrar la información completa de un
     * pedido en la interfaz de usuario.
     *
     * * @param entidad La entidad recuperada por el EntityManager.
     * @return Un objeto DTO con la información jerárquica del pedido.
     */
    public static ComandaDTO entidadADto(Comanda entidad) {
        if (entidad == null) {
            return null;
        }

        ComandaDTO dto = new ComandaDTO();

        dto.setIdComanda(entidad.getIdComanda());
        dto.setFolio(entidad.getFolio());
        dto.setFecha(entidad.getFecha());
        dto.setTotal(entidad.getTotal());
        dto.setNumeroMesa(entidad.getNumeroMesa());

        if (entidad.getEstado() == EstadoComanda.ABIERTA) {
            dto.setEstado(EstadoComandaDTO.ABIERTA);
        } else if (entidad.getEstado() == EstadoComanda.CANCELADA) {
            dto.setEstado(EstadoComandaDTO.CANCELADA);
        } else if (entidad.getEstado() == EstadoComanda.ENTREGADA) {
            dto.setEstado(EstadoComandaDTO.ENTREGADA);
        }

        dto.setMesa(MesaAdapter.entidadADto(entidad.getMesa()));
        dto.setMesero(MeseroAdapter.entidadADto(entidad.getMesero()));
        dto.setCliente(ClienteFrecuenteAdapter.entidadADTO(entidad.getCliente()));

        //mapeo de detalles hacia DTO
        if (entidad.getDetalles() != null) {
            List<DetallePedidoDTO> detallesDTO = new ArrayList<>();
            for (DetallePedido detalleEntidad : entidad.getDetalles()) {
                detallesDTO.add(DetallePedidoAdapter.entidadADto(detalleEntidad));
            }
            dto.setDetalles(detallesDTO);
        }

        return dto;
    }

    /**
     * Genera un objeto {@link ReporteComandaDTO} optimizado para vistas de
     * resumen o reportes. A diferencia del DTO estándar, este simplifica el
     * nombre del cliente en una sola cadena y maneja la distinción entre
     * clientes registrados y público general.
     *
     * * @param entidad La comanda de la cual se extraerán los datos para el
     * reporte.
     * @return Un DTO simplificado para representación tabular.
     */
    public static ReporteComandaDTO entidadAReporteDto(Comanda entidad) {
        ReporteComandaDTO dto = new ReporteComandaDTO();
        dto.setFolio(entidad.getFolio());
        dto.setFecha(entidad.getFecha());
        dto.setTotal(entidad.getTotal());
        dto.setNumeroMesa(entidad.getNumeroMesa());
        if (entidad.getEstado() == EstadoComanda.ABIERTA) {
            dto.setEstado(EstadoComandaDTO.ABIERTA);
        } else if (entidad.getEstado() == EstadoComanda.CANCELADA) {
            dto.setEstado(EstadoComandaDTO.CANCELADA);
        } else if (entidad.getEstado() == EstadoComanda.ENTREGADA) {
            dto.setEstado(EstadoComandaDTO.ENTREGADA);
        }

        if (entidad.getCliente() != null) {
            String nombre = "";
            nombre += entidad.getCliente().getNombres() + " ";
            nombre += entidad.getCliente().getApellidoPaterno();
            if (entidad.getCliente().getApellidoMaterno() != null) {
                nombre += " " + entidad.getCliente().getApellidoMaterno();
            }

            dto.setNombreCliente(nombre);
        } else {
            dto.setNombreCliente("Cliente General");
        }

        return dto;
    }
}
