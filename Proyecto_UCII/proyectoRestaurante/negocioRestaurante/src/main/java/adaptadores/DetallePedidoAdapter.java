/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import dtos.DetallePedidoDTO;
import entidades.Comanda;
import entidades.DetallePedido;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class DetallePedidoAdapter {
    public static DetallePedidoDTO entidadADto(DetallePedido entidad){
        DetallePedidoDTO dto = new DetallePedidoDTO();
        dto.setIdDetallePedido(entidad.getIdDetallePedido());
        dto.setCantidad(entidad.getCantidad());
        dto.setPrecioUnitario(entidad.getPrecioUnitario());
        dto.setSubtotal(entidad.getSubtotal());
        dto.setNota(entidad.getNota());
        
        dto.setProductoDTO(ProductoAdapter.entidadADTO(entidad.getProducto()));
        
        dto.setIdComanda(entidad.getComanda().getIdComanda());
        
        return dto;
    }
    
    public static DetallePedido dtoAEntidadExistente(DetallePedidoDTO dto){
        DetallePedido entidad = new DetallePedido();
        entidad.setIdDetallePedido(dto.getIdDetallePedido());
        entidad.setCantidad(dto.getCantidad());
        entidad.setPrecioUnitario(dto.getPrecioUnitario());
        entidad.setSubtotal(dto.getSubtotal());
        entidad.setNota(dto.getNota());
        
        entidad.setProducto(ProductoAdapter.dtoAEntidadExistente(dto.getProductoDTO()));
        
        Comanda comanda = new Comanda();
        comanda.setIdComanda(dto.getIdComanda());
        entidad.setComanda(comanda);
        
        return entidad;
    }
    
    public static DetallePedido dtoAEntidadNueva(DetallePedidoDTO dto){
        DetallePedido entidad = new DetallePedido();
        entidad.setIdDetallePedido(null);
        entidad.setCantidad(dto.getCantidad());
        entidad.setPrecioUnitario(dto.getPrecioUnitario());
        entidad.setSubtotal(dto.getSubtotal());
        entidad.setNota(dto.getNota());
        
        entidad.setProducto(ProductoAdapter.dtoAEntidadNuevo(dto.getProductoDTO()));
        
        Comanda comanda = new Comanda();
        comanda.setIdComanda(dto.getIdComanda());
        entidad.setComanda(comanda);
        
        return entidad;
    }
}
