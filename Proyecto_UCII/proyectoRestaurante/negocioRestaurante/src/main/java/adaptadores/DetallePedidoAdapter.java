/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import dtos.DetallePedidoDTO;
import entidades.Comanda;
import entidades.DetallePedido;

/**
 * Clase adaptadora para la entidad DetallePedido. Facilita la conversión entre
 * el modelo de persistencia y el DTO, gestionando la información específica de
 * cada producto solicitado en una comanda, incluyendo cantidades, precios
 * pactados al momento de la venta y notas especiales.
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class DetallePedidoAdapter {

    /**
     * Convierte una entidad {@link DetallePedido} a un
     * {@link DetallePedidoDTO}. Extrae la información del producto asociado y
     * el identificador de la comanda para facilitar su manejo en la capa de
     * presentación.
     *
     * * @param entidad La entidad de persistencia recuperada.
     * @return Un objeto DTO con la información detallada del renglón de pedido.
     */
    public static DetallePedidoDTO entidadADto(DetallePedido entidad) {
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

    /**
     * Transforma un DTO en una entidad {@link DetallePedido} existente. Se
     * utiliza principalmente para actualizaciones de pedidos abiertos donde el
     * detalle ya tiene una identidad en la base de datos.
     *
     * * @param dto El objeto de transferencia con datos actualizados.
     * @return Una entidad con su ID y relaciones reconstruidas.
     */
    public static DetallePedido dtoAEntidadExistente(DetallePedidoDTO dto) {
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

    /**
     * Crea una nueva entidad {@link DetallePedido} a partir de un DTO. Asegura
     * que el ID sea nulo para que el motor de persistencia genere una nueva
     * entrada en la tabla de detalles.
     *
     * * @param dto El DTO con la información del nuevo producto solicitado.
     * @return Una entidad lista para ser persistida por primera vez.
     */
    public static DetallePedido dtoAEntidadNueva(DetallePedidoDTO dto) {
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
