/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dtos.ClienteFrecuenteDTO;
import dtos.ComandaDTO;
import dtos.DetallePedidoDTO;
import dtos.MeseroDTO;
import dtos.IngredienteDTO;
import dtos.MesaDTO;
import dtos.ProductoDTO;
import dtos.ReporteComandaDTO;
import enums.DisponibilidadProductoDTO;
import enums.EstadoComandaDTO;
import enums.EstadoMesaDTO;
import excepciones.NegocioException;
import interfaces.ICoordinador;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import objetosnegocio.ClienteFrecuenteBO;
import objetosnegocio.ComandaBO;
import objetosnegocio.MeseroBO;
import objetosnegocio.IngredienteBO;
import objetosnegocio.MesaBO;
import objetosnegocio.ProductoBO;
import pantallas.*;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class Coordinador implements ICoordinador {

    // FRAMES PRINCIPALES
    private FrmInicio frmInicio;
    private FrmAcciones frmAcciones;

    // CLIENTES
    private final ClienteFrecuenteBO clienteFrecuenteBO;

    private FrmClientes frmClientes;
    private FrmRegistrarCliente frmRegistrarCliente;
    private FrmEditarCliente frmEditarCliente;

    private List<ClienteFrecuenteDTO> listaClientesActual;
    private ClienteFrecuenteDTO clienteSeleccionado;

    // MESEROS
    private final MeseroBO meseroBO;

    private FrmInicioSesionMesero frmInicioSesionMesero;

    private final MesaBO mesaBO;

    private MeseroDTO meseroActual;

    // MESAS
    private FrmMesas frmMesas;

    private MesaDTO mesaSeleccionada;

    // SELECCION DE PRODUCTOS
    private FrmSeleccionProductos frmSeleccionProductos;
    private FrmResumenPedido frmResumenPedido;
    private FrmResumenPedidoEditado frmResumenPedidoEditado;

    private List<DetallePedidoDTO> carrito;

    // INGREDIENTES
    private final IngredienteBO ingredienteBO;

    private FrmIngredientes frmIngredientes;
    private FrmRegistrarIngrediente frmRegistrarIngrediente;
    private FrmAjustarStock frmAjustarStock;

    private List<IngredienteDTO> listaIngredientesActual;
    private IngredienteDTO ingredienteSeleccionado;

    // PRODUCTOS
    private final ProductoBO productoBO;

    private FrmProductos frmProductos;
    private FrmRegistrarProducto frmRegistrarProducto;
    private FrmDetalleProducto frmDetalleProducto;
    private FrmEditarProducto frmEditarProducto;

    private List<ProductoDTO> listaProductosActual;
    private ProductoDTO productoSeleccionado;
    private List<MesaDTO> mesas;

    // REPORTES 
    private FrmSeleccionReporte frmSeleccionReporte;
    private FrmReporteClientesFrecuentes frmReporteClientesFrecuentes;
    private FrmReporteComandas frmReporteComandas;

    //Limpiar todo
    public void limpiarSesionComanda() {
        this.mesaSeleccionada = null;
        this.mesas = null;
        this.clienteSeleccionado = null;
        this.carrito = null;
        this.comanda = null;
        this.productoSeleccionado = null;
        this.frmComandaActual = null;

    }

    //COMANDAS
    private final ComandaBO comandaBO;

    FrmConfirmacionComanda frmConfirmacionComanda;

    FrmEstadosComanda frmEstadosComanda;

    FrmEditarProductosComanda frmEditarProductosComanda;

    private FrmSeleccionProductos frmComandaActual;

    public enum OrigenBusquedaProductos {
        NUEVA_COMANDA,
        EDICION_COMANDA
    }

    private OrigenBusquedaProductos origenActualBusquedaProductos;

    private ComandaDTO comanda;

    public Coordinador() {
        this.clienteFrecuenteBO = ClienteFrecuenteBO.getInstance();
        this.meseroBO = MeseroBO.getInstance();
        this.ingredienteBO = IngredienteBO.getInstance();
        this.productoBO = ProductoBO.getInstance();
        this.mesaBO = MesaBO.getInstance();
        this.comandaBO = ComandaBO.getInstance();
    }

    //----- MOSTRAR FRAMES -----
    @Override
    public void iniciarSistema() {

        if (frmInicio == null) {
            frmInicio = new FrmInicio(this);
        }
        frmInicio.setVisible(true);
    }

    @Override
    public void mostrarAcciones() {
        if (frmAcciones == null) {
            frmAcciones = new FrmAcciones(this);
        }
        frmAcciones.setVisible(true);
        frmAcciones.toFront();
    }

    //----- CLIENTES -----
    @Override
    public List<ClienteFrecuenteDTO> buscarClientes(String filtro) {
        try {
            return clienteFrecuenteBO.consultarClientesPorFiltro(filtro);
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public void mostrarClientes() {
        try {
            this.listaClientesActual = clienteFrecuenteBO.consultarTodos();
            frmClientes = new FrmClientes(this);
            frmClientes.setVisible(true);
            frmClientes.toFront();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    @Override
    public void mostrarRegistrarCliente() {
        try {
            this.listaClientesActual = clienteFrecuenteBO.consultarTodos();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        if (frmRegistrarCliente == null) {
            frmRegistrarCliente = new FrmRegistrarCliente(this);
        }
        frmRegistrarCliente.setVisible(true);
        frmRegistrarCliente.toFront();
    }

    @Override
    public void registrarCliente(ClienteFrecuenteDTO clienteDTO) {
        try {
            clienteFrecuenteBO.registrarCliente(clienteDTO);
            JOptionPane.showMessageDialog(null, "Cliente registrado correctamente");

            //actualizar tabla
            actualizarTablaClientes();

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        if (frmRegistrarCliente != null) {
            frmRegistrarCliente.dispose();
            frmRegistrarCliente = null;
        }
        frmClientes.setVisible(true);
    }

    @Override
    public void actualizarTablaClientes() {
        try {
            List<ClienteFrecuenteDTO> clientes = clienteFrecuenteBO.consultarTodos();

            if (frmClientes != null) {
                frmClientes.actualizarTablaClientes(clientes);
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    @Override
    public void mostrarEditarCliente() {
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente primero");
            return;
        }

        if (frmEditarCliente != null) {
            frmEditarCliente.dispose();
        }
        frmClientes.dispose();
        frmEditarCliente = new FrmEditarCliente(this);
        frmEditarCliente.setVisible(true);
        frmEditarCliente.toFront();
    }

    @Override
    public void editarCliente(ClienteFrecuenteDTO clienteDTO) {
        try {
            clienteFrecuenteBO.actualizarCliente(clienteDTO);
            JOptionPane.showMessageDialog(null, "Cliente editado correctamente");

            actualizarTablaClientes();

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        if (frmEditarCliente != null) {
            frmEditarCliente.dispose();
            frmEditarCliente = null;
        }
        frmClientes.setVisible(true);
    }

    @Override
    public void setClienteSeleccionado(ClienteFrecuenteDTO cliente) {
        this.clienteSeleccionado = cliente;

        if (frmClientes != null) {
            frmClientes.dispose();
            frmClientes = null;
        }

        if (this.frmResumenPedido != null) {
            frmResumenPedido.setVisible(true);
            frmResumenPedido.recibirClienteSeleccionado(cliente);
            frmResumenPedido = null;

        } else if (this.frmResumenPedidoEditado != null) {
            frmResumenPedidoEditado.setVisible(true);
            frmResumenPedidoEditado.recibirClienteSeleccionado(cliente);
            frmResumenPedidoEditado = null;
        }
    }

    @Override
    public ClienteFrecuenteDTO getClienteSeleccionado() {
        return this.clienteSeleccionado;
    }

    @Override
    public List<ClienteFrecuenteDTO> getListaClientesActual() {
        return this.listaClientesActual;
    }

    @Override
    public void eliminarCliente() {
        try {

            if (clienteFrecuenteBO.clienteConComandas(clienteSeleccionado.getIdCliente())) {
                JOptionPane.showMessageDialog(null, "No se pueden eliminar clientes con comandas");
            } else {
                clienteFrecuenteBO.eliminarCliente(clienteSeleccionado);
                JOptionPane.showMessageDialog(null, "Cliente eliminado con exito");
                clienteSeleccionado = null;
                listaClientesActual = clienteFrecuenteBO.consultarTodos();
                actualizarTablaClientes();
            }

        } catch (NegocioException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    @Override
    public List<ClienteFrecuenteDTO> consultarClientes() {
        try {
            listaClientesActual = clienteFrecuenteBO.consultarTodos();
            return listaClientesActual;
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar el cliente");
            return null;
        }
    }

    @Override
    public ClienteFrecuenteDTO getClienteGeneral() {
        try {
            return clienteFrecuenteBO.buscarClienteFrecuenteGeneral();
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, "Error al asignar el cliente general");
            e.printStackTrace();
            return null;
        }
    }

    //----- MESEROS -----
    @Override
    public void mostrarInicioSesionMesero() {
        limpiarSesionComanda();
        precargarMeseros();

        if (frmInicioSesionMesero == null) {
            frmInicioSesionMesero = new FrmInicioSesionMesero(this);
        }
        frmInicioSesionMesero.setVisible(true);
    }

    @Override
    public MeseroDTO getMeseroActual() {
        return meseroActual;
    }

    @Override
    public void setMeseroActual(MeseroDTO mesero) {
        meseroActual = mesero;
    }

    @Override
    public MeseroDTO buscarMeseroPorUsuario(String usuario) {
        try {
            MeseroDTO mesero = meseroBO.buscarMeseroPorUsuario(usuario);

            meseroActual = mesero;

            return meseroActual;

        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, "No se encontro al mesero con usuario: " + usuario);
            return null;
        }

    }

    @Override
    //DATOS PRECARGADOS
    public void precargarMeseros() {
        try {
            if (meseroBO.consultarTodos().isEmpty()) {
                MeseroDTO m = new MeseroDTO();
                m.setRfc("CIVJ061128V25");
                m.setApellidoPaterno("Cisneros");
                m.setApellidoMaterno("Valenzuela");
                m.setNombre("Ernesto");
                m.setUsuario("m1");
                meseroBO.registrarMesero(m);

                MeseroDTO m2 = new MeseroDTO();
                m2.setRfc("GUCP060724H89");
                m2.setApellidoPaterno("Guevara");
                m2.setApellidoMaterno("Cervantes");
                m2.setNombre("Paulina");
                m2.setUsuario("m2");
                meseroBO.registrarMesero(m2);
            }
        } catch (NegocioException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "error al precargar los datos");
        }
    }

    // INGREDIENTES 
    @Override
    public void mostrarIngredientes() {
        try {
            this.listaIngredientesActual = ingredienteBO.consultarTodos();
//            if (frmIngredientes == null) {
            frmIngredientes = new FrmIngredientes(this);
//            }
            frmIngredientes.setVisible(true);
            frmIngredientes.toFront();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    @Override
    public List<IngredienteDTO> obtenerIngredientes() {
        try {
            return ingredienteBO.consultarTodos();
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar ingredientes");
            return null;
        }
    }

    public void setIngredienteSeleccionado(IngredienteDTO ingrediente) {
        this.ingredienteSeleccionado = ingrediente;
    }

    @Override
    public IngredienteDTO getIngredienteSeleccionado() {
        return this.ingredienteSeleccionado;
    }

    @Override
    public List<IngredienteDTO> getListaIngredientesActual() {
        try {
            this.listaIngredientesActual = ingredienteBO.consultarTodos();
            return this.listaIngredientesActual;
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar ingredientes");
            return new ArrayList<>();
        }
    }

    @Override
    public void mostrarRegistrarIngrediente() {
        try {
            this.listaIngredientesActual = ingredienteBO.consultarTodos();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
        if (frmRegistrarIngrediente == null) {
            frmRegistrarIngrediente = new FrmRegistrarIngrediente(this);
        }
        frmRegistrarIngrediente.setVisible(true);
        frmRegistrarIngrediente.toFront();
    }

    @Override
    public void registrarIngrediente(IngredienteDTO ingredienteDTO) {
        try {
            ingredienteBO.registrarIngrediente(ingredienteDTO);
            JOptionPane.showMessageDialog(null, "Ingrediente registrado correctamente");

            //actualizar tabla
            actualizarTablaIngredientes();

            if (frmRegistrarIngrediente != null) {
                frmRegistrarIngrediente.dispose();
                frmRegistrarIngrediente = null;
            }
            frmIngredientes.setVisible(true);
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    @Override
    public void actualizarTablaIngredientes() {
        try {
            List<IngredienteDTO> ingredientes = ingredienteBO.consultarTodos();

            if (frmIngredientes != null) {
                frmIngredientes.actualizarTablaIngredientes(ingredientes);
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    public void mostrarAjustarStock() {
        if (ingredienteSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un ingrediente");
            return;
        }

        if (frmAjustarStock != null) {
            frmAjustarStock.dispose();
        }

        frmIngredientes.dispose();
        frmAjustarStock = new FrmAjustarStock(this);
        frmAjustarStock.setVisible(true);
        frmAjustarStock.toFront();
    }

    public void ajustarStock(Long idIngrediente, double cantidad, boolean agregar) {
        try {
            IngredienteDTO ingrediente = this.ingredienteSeleccionado;

            double stockActual = ingrediente.getCantidadActual();

            if (agregar) {
                ingrediente.setCantidadActual(stockActual + cantidad);
            } else {
                if (stockActual < cantidad) {
                    JOptionPane.showMessageDialog(null, "No hay suficiente stock");
                    return;
                }
                ingrediente.setCantidadActual(stockActual - cantidad);
            }

            ingredienteBO.actualizarIngrediente(ingrediente);

            JOptionPane.showMessageDialog(null, "Stock actualizado correctamente");
            actualizarTablaIngredientes();

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        if (frmAjustarStock != null) {
            frmAjustarStock.dispose();
            frmAjustarStock = null;
        }

        if (frmIngredientes != null) {
            frmIngredientes.setVisible(true);
        }
    }

    @Override
    public void eliminarIngrediente() {
        try {
            ingredienteBO.eliminarIngrediente(ingredienteSeleccionado.getIdIngrediente());

            JOptionPane.showMessageDialog(null, "Ingrediente eliminado correctamente");

            ingredienteSeleccionado = null;
            actualizarTablaIngredientes();

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    @Override
    public List<IngredienteDTO> buscarIngredientes(String filtro) {
        try {
            return ingredienteBO.buscarPorFiltro(filtro);
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return new ArrayList<>();
        }
    }

    // PRODUCTOS 
    @Override
    public void mostrarProductosAdmin() {
        frmProductos = new FrmProductos(this, FrmProductos.Modo.ADMINISTRAR);
        frmProductos.setVisible(true);
        frmProductos.toFront();

    }

    @Override
    public List<ProductoDTO> consultarProductosFiltro(String filtro) {
        try {
            return productoBO.consultarProductosConFiltro(filtro);
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    @Override
    public void mostrarRegistrarProducto() {
        try {
            this.listaProductosActual = productoBO.consultarTodos();
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        if (frmRegistrarProducto == null) {
            frmRegistrarProducto = new FrmRegistrarProducto(this);
        }

        frmRegistrarProducto.setVisible(true);
        frmRegistrarProducto.toFront();
    }

    @Override
    public void registrarProducto(ProductoDTO productoDTO) {
        try {
            productoBO.registrarProducto(productoDTO);

            JOptionPane.showMessageDialog(null, "Producto registrado correctamente");

            if (frmRegistrarProducto != null) {
                frmRegistrarProducto.dispose();
                frmRegistrarProducto = null;
            }

            frmProductos = new FrmProductos(this, FrmProductos.Modo.ADMINISTRAR);

            frmProductos.setVisible(true);

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    @Override
    public void mostrarDetalleProducto() {
        if (productoSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto");
            return;
        }

        if (frmDetalleProducto != null) {
            frmDetalleProducto.dispose();
        }

        frmDetalleProducto = new FrmDetalleProducto(this);
        frmDetalleProducto.setVisible(true);
        frmDetalleProducto.toFront();
    }

    @Override
    public void mostrarEditarProducto() {
        if (productoSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto");
            return;
        }

        if (frmEditarProducto != null) {
            frmEditarProducto.dispose();
        }

        frmEditarProducto = new FrmEditarProducto(this);
        frmEditarProducto.setVisible(true);
        frmEditarProducto.toFront();
    }

    @Override
    public void actualizarProducto(ProductoDTO productoDTO) {
        try {
            productoBO.actualizarProducto(productoDTO);

            JOptionPane.showMessageDialog(null, "Producto actualizado correctamente");

            if (frmEditarProducto != null) {
                frmEditarProducto.dispose();
                frmEditarProducto = null;
            }

            frmProductos = new FrmProductos(this, FrmProductos.Modo.ADMINISTRAR);

            frmProductos.setVisible(true);

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    @Override
    public void eliminarProducto() {
        try {
            productoBO.eliminarProducto(productoSeleccionado.getIdProducto());

            JOptionPane.showMessageDialog(null, "Producto eliminado correctamente o desactivado si estaba en uso");

            productoSeleccionado = null;
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    @Override
    public void setProductoSeleccionado(ProductoDTO producto) {
        this.productoSeleccionado = producto;

        //si estabsmos armando una comanda le pasamos el producto y la volvemos a mostrar
        if (this.frmComandaActual != null) {
            frmComandaActual.setVisible(true);
            frmComandaActual.recibirProductoSeleccionado(producto);
        }
    }

    @Override
    public ProductoDTO getProductoSeleccionado() {
        return this.productoSeleccionado;
    }

    @Override
    public List<ProductoDTO> getListaProductosActual() {
        try {
            this.listaProductosActual = productoBO.consultarTodos();
            return this.listaProductosActual;
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar productos");
            return new ArrayList<>();
        }
    }

    //----- MESAS -----
    @Override
    public void mostrarMesas() {
//        if (frmMesas == null) {
        frmMesas = new FrmMesas(this);
        frmMesas = new FrmMesas(this);
        frmSeleccionProductos = null;
//        }
        frmMesas.setVisible(true);
    }

    @Override
    public List<MesaDTO> obtenerMesas() {
        try {
            if (mesas == null || mesas.isEmpty()) {
                mesas = mesaBO.consultarTodas();
            }

            mesas = mesaBO.consultarTodas();

            return mesas;

        } catch (NegocioException e) {
            return null;
        }
    }

    @Override
    public List<MesaDTO> cargaMasivaMesas() {
        if (mesas == null || mesas.isEmpty()) {

            try {
                List<MesaDTO> mesasGeneradas = new ArrayList<>();
                for (int i = 1; i <= 20; i++) {
                    mesasGeneradas.add(new MesaDTO(i, EstadoMesaDTO.DISPONIBLE));
                }

                //persistir las mesas generadas
                for (MesaDTO m : mesasGeneradas) {
                    mesaBO.registrarMesa(m);
                }

                mesas = mesasGeneradas;

            } catch (NegocioException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar las mesas generadas");
                e.printStackTrace();
                return null;
            }
        }

        return mesas;
    }

    @Override
    public void setMesaSeleccionada(MesaDTO mesa) {
        mesaSeleccionada = mesa;
    }

    @Override
    public MesaDTO getMesaSeleccionada() {
        return mesaSeleccionada;
    }

    @Override
    public MesaDTO actualizarMesa(MesaDTO mesa) {
        try {
            return mesaBO.actualizarMesa(mesa);
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, "No se pudo actualizar la disponibilidad de la mesa");
            return null;
        }
    }

    //----- PANTALLA DE PRODUCTOS -----
    @Override
    public void mostrarSeleccionProductos() {
        if (frmSeleccionProductos == null) {
            frmSeleccionProductos = new FrmSeleccionProductos(this);
        }
        frmSeleccionProductos.setVisible(true);
    }

    @Override
    public List<ProductoDTO> obtenerProductos() {
        try {
            listaProductosActual = productoBO.consultarTodos();
            return listaProductosActual;
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, "Error al consultar los productos");
            return null;
        }
    }

    @Override
    public List<ProductoDTO> obtenerProductosDisponibles() {
        try {
            listaProductosActual = productoBO.consultarProductosDisponibles();
            return listaProductosActual;
        } catch (NegocioException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al consultar los productos");
            return null;
        }
    }

    public void cambiarDisponibilidadProducto(Long idProducto, DisponibilidadProductoDTO estado) {
        try {
            productoBO.cambiarDisponibilidad(idProducto, estado);

            JOptionPane.showMessageDialog(null, "Disponibilidad actualizada correctamente");

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    @Override
    public void setListaProductosAtual(List<ProductoDTO> productos) {
        listaProductosActual = productos;
    }

    @Override
    public List<DetallePedidoDTO> getCarrito() {
        return carrito;
    }

    @Override
    public void setCarrito(List<DetallePedidoDTO> carrito) {
        this.carrito = carrito;
    }

    @Override
    public void mostrarResumenPedido() {
        if (frmResumenPedido != null) {
            frmResumenPedido.dispose();
        }
        frmResumenPedido = new FrmResumenPedido(this);
        frmResumenPedido.setVisible(true);
    }

    //----- COMANDAS -----
    @Override
    public ComandaDTO getComanda() {
        if (comanda == null) {
            comanda = new ComandaDTO();
        }
        return this.comanda;
    }

    @Override
    public void setComanda(ComandaDTO comanda) {
        this.comanda = comanda;
    }

    @Override
    public void mostrarConfirmacionComanda() {
        try {

            comanda.setFecha(LocalDateTime.now());
            comanda.setEstado(EstadoComandaDTO.ABIERTA);
            if (comanda.getCliente() == null) {
                if (clienteFrecuenteBO.buscarClienteFrecuenteGeneral() == null) {
                    JOptionPane.showMessageDialog(null, "No hay un cliente general para guardar la comanda");
                    mostrarMesas();
                    return;
                } else {
                    comanda.setCliente(clienteFrecuenteBO.buscarClienteFrecuenteGeneral());
                }

            }

            mesaSeleccionada.setDisponibilidad(EstadoMesaDTO.NO_DISPONIBLE);

            mesaSeleccionada = mesaBO.actualizarMesa(mesaSeleccionada);

            mesas = mesaBO.consultarTodas();

            //restar los ingredientes
            ComandaDTO registrado = comandaBO.registrarComanda(comanda);

            //settear el folo al DTO para la pantalla
            comanda.setFolio(registrado.getFolio());

            new FrmConfirmacionComanda(this).setVisible(true);

        } catch (NegocioException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al registrar la comanda");
        }

    }

    @Override
    public ComandaDTO buscarComandaAbiertaPorMesa(Integer numeroMesa) {
        try {

            this.comanda = comandaBO.obtenerComandaAbiertaPorMesa(numeroMesa);

            if (comanda == null) {
                JOptionPane.showMessageDialog(null, "No se encontró ninguna comanda para la mesa con numero: " + numeroMesa);
            }

        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, "Error al buscar la comanda de la mesa: " + numeroMesa);
            e.printStackTrace();
        }

        return comanda;

    }

    @Override
    public void mostrarEstadosComanda() {
        frmEstadosComanda = new FrmEstadosComanda(this);
        frmEstadosComanda.setVisible(true);
    }

    @Override
    public ComandaDTO actualizarComanda(ComandaDTO comanda) {
        try {
            return comandaBO.actualizarComanda(comanda);
        } catch (NegocioException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "No se pudo actualizar la comanda");
            return null;
        }
    }

    // REPORTES
    @Override
    public void mostrarOpcionesReporte() {
        if (frmSeleccionReporte == null) {
            frmSeleccionReporte = new FrmSeleccionReporte(this);
        }
        frmSeleccionReporte.setVisible(true);
        frmSeleccionReporte.toFront();
    }

    @Override
    public void mostrarReporteClientesFrecuentes() {

        if (frmSeleccionReporte != null) {
            frmSeleccionReporte.dispose();
        }

        frmReporteClientesFrecuentes = new FrmReporteClientesFrecuentes(this);
        frmReporteClientesFrecuentes.setVisible(true);
        frmReporteClientesFrecuentes.toFront();
    }

    public List<ClienteFrecuenteDTO> consultarReporteClientesFrecuentes(String nombre, Integer minimoVisitas) {

        try {
            return clienteFrecuenteBO.consultarReporte(nombre, minimoVisitas);
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return new ArrayList<>();
        }
    }

    @Override
    public JasperPrint generarReporteClientes(String nombre, Integer visitas) throws Exception {

        List<ClienteFrecuenteDTO> lista = consultarReporteClientesFrecuentes(nombre, visitas);

        List<Map<String, Object>> data = new ArrayList<>();

        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (ClienteFrecuenteDTO c : lista) {
            Map<String, Object> fila = new HashMap<>();

            fila.put("nombre",
                    c.getNombres() + " "
                    + c.getApellidoPaterno() + " "
                    + c.getApellidoMaterno());

            fila.put("visitas", c.getNumeroVisitas());
            fila.put("total", c.getTotalGastado());

            String fecha = "Sin comandas";
            if (c.getUltimaComanda() != null) {
                fecha = c.getUltimaComanda().format(formato);
            }

            fila.put("ultima", fecha);

            data.add(fila);
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);

        JasperReport report = JasperCompileManager.compileReport(
                getClass().getResourceAsStream(
                        "/reportes/ReporteClientesFrecuentes.jrxml"));

        return JasperFillManager.fillReport(
                report,
                new HashMap<>(),
                dataSource);
    }

    public void descargarPDFClientesFrecuentes(String nombre, Integer visitas) {
        try {
            JasperPrint jasperPrint = generarReporteClientes(nombre, visitas);

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File("ReporteClientesFrecuentes.pdf"));

            int opcion = fileChooser.showSaveDialog(null);

            if (opcion == JFileChooser.APPROVE_OPTION) {
                File archivo = fileChooser.getSelectedFile();

                JasperExportManager.exportReportToPdfFile(
                        jasperPrint,
                        archivo.getAbsolutePath()
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    @Override
    public void mostrarEditarProductosComanda() {
        frmEditarProductosComanda = new FrmEditarProductosComanda(this);

        frmEditarProductosComanda.setVisible(true);
    }

    @Override
    public void mostrarResumenPedidoEditado() {
        frmResumenPedidoEditado = new FrmResumenPedidoEditado(this);
        frmResumenPedidoEditado.setVisible(true);
    }

    @Override
    public void mostrarReportesComandas() {
        frmReporteComandas = new FrmReporteComandas(this);
        frmReporteComandas.setVisible(true);
    }

    @Override
    public List<ReporteComandaDTO> obetnerComandasPorRangoFechas(LocalDate inicio, LocalDate fin) {
        try {
            return comandaBO.obtenerComandasPorRango(inicio, fin);
        } catch (NegocioException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al consultar las comandas");
            return null;
        }
    }

    @Override
    public JasperPrint generarJasperComandas(List<ReporteComandaDTO> lista, LocalDate inicio, LocalDate fin) throws Exception {
        List<Map<String, Object>> data = new ArrayList<>();
        DateTimeFormatter formatoConHora = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        DateTimeFormatter formatoSimple = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (ReporteComandaDTO c : lista) {
            Map<String, Object> fila = new HashMap<>();
            fila.put("folio", c.getFolio() != null ? c.getFolio() : "-");
            fila.put("fecha", c.getFecha() != null ? c.getFecha().format(formatoConHora) : "-");
            fila.put("mesa", c.getNumeroMesa());
            fila.put("estado", String.valueOf(c.getEstado()));
            fila.put("total", c.getTotal());
            fila.put("cliente", (c.getNombreCliente() != null && !c.getNombreCliente().isEmpty()) ? c.getNombreCliente() : "Mostrador");
            data.add(fila);
        }

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("fechaInicio", inicio.format(formatoSimple));
        parametros.put("fechaFin", fin.format(formatoSimple));

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(data);
        JasperReport report = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reportes/ReporteComandas.jrxml"));

        return JasperFillManager.fillReport(report, parametros, dataSource);
    }

    @Override
    public boolean verificarStock(ProductoDTO producto, int proximaCantidad) {
        return productoBO.hayStockSuficiente(producto, proximaCantidad);
    }

    @Override
    public ClienteFrecuenteDTO registrarClienteGeneral() {
        try {
            if (clienteFrecuenteBO.buscarClienteFrecuenteGeneral() == null) {
                ClienteFrecuenteDTO clienteGeneral = new ClienteFrecuenteDTO(null, "Cliente general", "", "", "0", "", 0, 0.0, 0);
                return clienteFrecuenteBO.registrarCliente(clienteGeneral);
            } else {
                JOptionPane.showMessageDialog(null, "El cliente general ya esta registrado");
                return null;
            }
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, "Error al registrar al cliente general");
            return null;
        }
    }

    @Override
    public List<ProductoDTO> consultarProductosDisponiblesFiltro(String filtro) {
        try {
            return productoBO.consultarProductosDisponiblesConFiltro(filtro);
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    @Override
    public void mostrarProductosSelec(FrmEditarProductosComanda frmOrigen) {
        frmProductos = new FrmProductos(this, FrmProductos.Modo.SELECCIONAR);
        frmProductos.setVisible(true);
        frmProductos.toFront();
        frmOrigen.setVisible(false);
    }

    public void abrirBuscadorParaComanda(FrmSeleccionProductos frmActual) {
        this.frmComandaActual = frmActual;
        frmActual.setVisible(false);

        FrmProductos buscador = new FrmProductos(this, FrmProductos.Modo.SELECCIONAR);
        buscador.setVisible(true);
    }

    public void abrirBuscadorProductos(OrigenBusquedaProductos origen) {
        this.origenActualBusquedaProductos = origen;

        //ccultamos la ventana dependiendo de quien llamó
        if (origen == OrigenBusquedaProductos.EDICION_COMANDA && frmEditarProductosComanda != null) {
            frmEditarProductosComanda.setVisible(false);
        } else if (origen == OrigenBusquedaProductos.NUEVA_COMANDA && frmSeleccionProductos != null) {
            frmSeleccionProductos.setVisible(false);
        }

        //bbuscador
        FrmProductos frmBuscador = new FrmProductos(this, FrmProductos.Modo.SELECCIONAR);
        frmBuscador.setVisible(true);
    }

    public void volverDeBusquedaProductos() {
        if (origenActualBusquedaProductos == OrigenBusquedaProductos.EDICION_COMANDA) {
            if (frmEditarProductosComanda != null) {
                frmEditarProductosComanda.setVisible(true);
            }
        } else if (origenActualBusquedaProductos == OrigenBusquedaProductos.NUEVA_COMANDA) {
            if (frmSeleccionProductos != null) {
                frmSeleccionProductos.setVisible(true);
            }
        }
    }

    @Override
    public void mostrarProductosSelec() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void abrirBuscadorClientesParaComanda(FrmResumenPedido frmActual) {
        this.frmResumenPedido = frmActual;
        frmActual.setVisible(false);

        try {
            this.listaClientesActual = clienteFrecuenteBO.consultarTodos();
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        frmClientes = new FrmClientes(this, true); // modo selección
        frmClientes.setVisible(true);
    }

    @Override
    public void abrirBuscadorClientesParaComanda(FrmResumenPedidoEditado frmActual) {
        this.frmResumenPedidoEditado = frmActual;
        frmActual.setVisible(false);

        try {
            this.listaClientesActual = clienteFrecuenteBO.consultarTodos();
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        frmClientes = new FrmClientes(this, true); // modo selección
        frmClientes.setVisible(true);
    }

    public void abrirBuscadorIngredientesParaProducto(FrmRegistrarProducto frmActual) {
        this.frmRegistrarProducto = frmActual;
        frmActual.setVisible(false);

        try {
            this.listaIngredientesActual = ingredienteBO.consultarTodos();
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        // Abrir en modo selección
        frmIngredientes = new FrmIngredientes(this, true, ingredientesSeleccionados -> {
            frmActual.recibirIngredientesSeleccionados(ingredientesSeleccionados);
            frmIngredientes.dispose();
            frmActual.setVisible(true);
            return true;
        });

        frmIngredientes.setVisible(true);
    }

    public void abrirBuscadorIngredientesParaEditarProducto(FrmEditarProducto frmActual) {
        this.frmEditarProducto = frmActual;
        frmActual.setVisible(false);

        try {
            this.listaIngredientesActual = ingredienteBO.consultarTodos();
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        frmIngredientes = new FrmIngredientes(this, true, ingredientesSeleccionados -> {
            frmActual.recibirIngredientesSeleccionados(ingredientesSeleccionados);
            frmIngredientes.dispose();
            frmActual.setVisible(true);
            return true;
        });

        frmIngredientes.setVisible(true);
    }

    public void volverDeBusquedaIngredientes() {
        if (frmRegistrarProducto != null) {
            frmRegistrarProducto.setVisible(true);
        } else if (frmEditarProducto != null) {
            frmEditarProducto.setVisible(true);
        }
    }

    public void volverDeBusquedaClientes() {
        if (frmResumenPedido != null) {
            frmResumenPedido.setVisible(true);
        } else if (frmResumenPedidoEditado != null) {
            frmResumenPedidoEditado.setVisible(true);
        }
    }
}
