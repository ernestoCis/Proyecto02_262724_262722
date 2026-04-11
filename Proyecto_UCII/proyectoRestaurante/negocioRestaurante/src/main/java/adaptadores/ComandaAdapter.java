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
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class ComandaAdapter {

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

    public static ReporteComandaDTO entidadAReporteDto(Comanda entidad) {
        ReporteComandaDTO dto = new ReporteComandaDTO();
        dto.setFolio(entidad.getFolio());
        dto.setFecha(entidad.getFecha());
        dto.setTotal(entidad.getTotal());
        dto.setNumeroMesa(entidad.getNumeroMesa());
        if(entidad.getEstado() == EstadoComanda.ABIERTA){
            dto.setEstado(EstadoComandaDTO.ABIERTA);
        }else if(entidad.getEstado() == EstadoComanda.CANCELADA){
            dto.setEstado(EstadoComandaDTO.CANCELADA);
        }else if(entidad.getEstado() == EstadoComanda.ENTREGADA){
            dto.setEstado(EstadoComandaDTO.ENTREGADA);
        }

        if (entidad.getCliente() != null) {
            String nombre = "";
            nombre += entidad.getCliente().getNombres() + " ";
            nombre += entidad.getCliente().getApellidoPaterno();
            if(entidad.getCliente().getApellidoMaterno() != null){
                nombre += " " + entidad.getCliente().getApellidoMaterno();
            }
            
            dto.setNombreCliente(nombre);
        } else {
            dto.setNombreCliente("Cliente General");
        }

        return dto;
    }
}
