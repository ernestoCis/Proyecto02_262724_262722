package interfaces;

import dtos.ClienteFrecuenteDTO;
import dtos.IngredienteDTO;
import dtos.MesaDTO;
import dtos.MeseroDTO;
import dtos.ProductoDTO;
import java.util.List;

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

    // INGREDIENTES
    void mostrarIngredientes();

    void setIngredienteSeleccionado(IngredienteDTO cliente);

    IngredienteDTO getIngredienteSeleccionado();

    List<IngredienteDTO> getListaIngredientesActual();

    void mostrarRegistrarIngrediente();

    void registrarIngrediente(IngredienteDTO ingredienteDTO);

    void actualizarTablaIngredientes();

    void mostrarProductos();

//    void mostrarRegistrarProducto();
//
//    void registrarProducto(ProductoDTO productoDTO);

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
    
}