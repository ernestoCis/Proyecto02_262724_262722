package interfaces;

import dtos.ProductoDTO;
import enums.DisponibilidadProductoDTO;
import excepciones.NegocioException;
import java.util.List;

/**
 * 
 * @author Paulina Guevara, Ernesto Cisneros
 */
public interface IProductoBO {

    void registrarProducto(ProductoDTO dto) throws NegocioException;

    void actualizarProducto(ProductoDTO dto) throws NegocioException;

    List<ProductoDTO> consultarTodos() throws NegocioException;

    List<ProductoDTO> buscarPorNombre(String nombre) throws NegocioException;

    void cambiarDisponibilidad(Long id, DisponibilidadProductoDTO estado) throws NegocioException;
    
    boolean hayStockSuficiente(ProductoDTO dto, int cantidadSolicitada);
}