package interfaces;

import dtos.ClienteFrecuenteDTO;
import dtos.ComandaDTO;
import dtos.DetallePedidoDTO;
import dtos.IngredienteDTO;
import dtos.MesaDTO;
import dtos.MeseroDTO;
import dtos.ProductoDTO;
import java.io.File;
import java.util.List;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public interface ICoordinador {

    void iniciarSistema();

    void mostrarAcciones();

    void mostrarClientes();

    void mostrarRegistrarCliente();

    void registrarCliente(ClienteFrecuenteDTO clienteDTO);

    void actualizarTablaClientes();

    void mostrarEditarCliente();

    void mostrarInicioSesionMesero();

    void editarCliente(ClienteFrecuenteDTO clienteDTO);

    void setClienteSeleccionado(ClienteFrecuenteDTO cliente);

    ClienteFrecuenteDTO getClienteSeleccionado();

    List<ClienteFrecuenteDTO> getListaClientesActual();

    void eliminarCliente();

    MeseroDTO getMeseroActual();

    MeseroDTO buscarMeseroPorUsuario(String usuario);
    
    void setMeseroActual(MeseroDTO mesero);

    // INGREDIENTES
    void mostrarIngredientes();

    List<IngredienteDTO> obtenerIngredientes();

    void setIngredienteSeleccionado(IngredienteDTO cliente);

    IngredienteDTO getIngredienteSeleccionado();

    List<IngredienteDTO> getListaIngredientesActual();

    void mostrarRegistrarIngrediente();

    void registrarIngrediente(IngredienteDTO ingredienteDTO);

    void actualizarTablaIngredientes();
    
    void eliminarIngrediente();

    // PRODUCTOS
    void mostrarProductos();

    void mostrarRegistrarProducto();

    void registrarProducto(ProductoDTO productoDTO);

    void mostrarDetalleProducto();

    void mostrarEditarProducto();

    void actualizarProducto(ProductoDTO productoDTO);
    
    void actualizarTablaProductos();

    void setProductoSeleccionado(ProductoDTO producto);

    ProductoDTO getProductoSeleccionado();

    List<ProductoDTO> getListaProductosActual();

    void setListaProductosAtual(List<ProductoDTO> productos);

    void mostrarMesas();

    List<MesaDTO> obtenerMesas();

    List<MesaDTO> cargaMasivaMesas();

    void precargarMeseros();

    void mostrarSeleccionProductos();

    List<ProductoDTO> obtenerProductos();

    void setMesaSeleccionada(MesaDTO mesa);

    List<DetallePedidoDTO> getCarrito();

    void setCarrito(List<DetallePedidoDTO> carrito);

    void mostrarResumenPedido();

    List<ClienteFrecuenteDTO> consultarClientes();

    ComandaDTO getComanda();

    void setComanda(ComandaDTO comanda);

    void mostrarConfirmacionComanda();

    MesaDTO getMesaSeleccionada();

    void limpiarSesionComanda();

    void mostrarEstadosComanda();
    
    ComandaDTO buscarComandaAbiertaPorMesa(Integer numeroMesa);
    
    ComandaDTO actualizarComanda(ComandaDTO comanda);
    
    void mostrarEditarProductosComanda();
    
    MesaDTO actualizarMesa(MesaDTO mesa);
    
    // REPORTES
    void mostrarOpcionesReporte();
    
    void mostrarResumenPedidoEditado();
    
    void mostrarReporteClientesFrecuentes();
        
    JasperPrint generarReporteClientes(String nombre, Integer visitas) throws Exception;
    
}
