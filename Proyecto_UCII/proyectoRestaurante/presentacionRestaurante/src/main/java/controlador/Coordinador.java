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
 * Clase principal que implementa el patrón Mediador para la aplicación del
 * Restaurante.
 * <p>
 * El Coordinador centraliza la comunicación entre las pantallas (Capa de
 * Presentación) y los Objetos de Negocio (Capa Lógica). Mantiene el estado
 * global de la sesión, gestionando el flujo de datos sin que las pantallas se
 * acoplen entre sí.</p>
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class Coordinador implements ICoordinador {

    // FRAMES PRINCIPALES
    /**
     * Formulario principal de inicio de la aplicación.
     */
    private FrmInicio frmInicio;
    /**
     * Formulario de menú principal con las acciones del sistema.
     */
    private FrmAcciones frmAcciones;

    // CLIENTES
    /**
     * Objeto de negocio para gestionar las operaciones de Clientes Frecuentes
     * en la base de datos.
     */
    private final ClienteFrecuenteBO clienteFrecuenteBO;

    /**
     * Formulario para el catálogo general de clientes frecuentes.
     */
    private FrmClientes frmClientes;
    /**
     * Formulario utilizado para registrar un nuevo cliente en el sistema.
     */
    private FrmRegistrarCliente frmRegistrarCliente;
    /**
     * Formulario utilizado para editar los datos de un cliente existente.
     */
    private FrmEditarCliente frmEditarCliente;

    /**
     * Lista temporal que almacena los clientes mostrados en la sesión o tabla
     * actual.
     */
    private List<ClienteFrecuenteDTO> listaClientesActual;
    /**
     * Cliente que se encuentra actualmente seleccionado por el usuario en el
     * sistema.
     */
    private ClienteFrecuenteDTO clienteSeleccionado;

    // MESEROS
    /**
     * Objeto de negocio para gestionar las operaciones de Meseros en la base de
     * datos.
     */
    private final MeseroBO meseroBO;
    /**
     * Formulario para el inicio de sesión exclusivo de los meseros.
     */
    private FrmInicioSesionMesero frmInicioSesionMesero;

    /**
     * Objeto de negocio para gestionar las operaciones de Mesas en la base de
     * datos.
     */
    private final MesaBO mesaBO;
    /**
     * Mesero que ha iniciado sesión y se encuentra activo en el sistema.
     */
    private MeseroDTO meseroActual;

    // MESAS
    /**
     * Formulario para la gestión y visualización del mapa de mesas.
     */
    private FrmMesas frmMesas;
    /**
     * Mesa que se encuentra actualmente seleccionada para abrir o editar una
     * comanda.
     */
    private MesaDTO mesaSeleccionada;

    // SELECCION DE PRODUCTOS
    /**
     * Formulario para que el mesero seleccione productos al armar una comanda.
     */
    private FrmSeleccionProductos frmSeleccionProductos;
    /**
     * Formulario para mostrar el resumen de un pedido nuevo antes de enviarlo a
     * cocina.
     */
    private FrmResumenPedido frmResumenPedido;
    /**
     * Formulario para mostrar el resumen de un pedido que está siendo
     * modificado.
     */
    private FrmResumenPedidoEditado frmResumenPedidoEditado;
    /**
     * Lista de detalles de pedido que conforman el carrito de compras de la
     * comanda actual.
     */
    private List<DetallePedidoDTO> carrito;

    // INGREDIENTES
    /**
     * Objeto de negocio para gestionar las operaciones de Ingredientes en la
     * base de datos.
     */
    private final IngredienteBO ingredienteBO;

    /**
     * Formulario para el catálogo y control de inventario de ingredientes.
     */
    private FrmIngredientes frmIngredientes;
    /**
     * Formulario para dar de alta un nuevo ingrediente en el inventario.
     */
    private FrmRegistrarIngrediente frmRegistrarIngrediente;
    /**
     * Formulario para realizar ajustes manuales (mermas o ingresos) al stock de
     * ingredientes.
     */
    private FrmAjustarStock frmAjustarStock;

    /**
     * Lista temporal que almacena los ingredientes mostrados en la tabla
     * activa.
     */
    private List<IngredienteDTO> listaIngredientesActual;
    /**
     * Ingrediente que se encuentra actualmente seleccionado para su edición o
     * ajuste.
     */
    private IngredienteDTO ingredienteSeleccionado;

    // PRODUCTOS
    /**
     * Objeto de negocio para gestionar las operaciones de Productos en la base
     * de datos.
     */
    private final ProductoBO productoBO;

    /**
     * Formulario para la administración del catálogo completo de
     * productos/platillos.
     */
    private FrmProductos frmProductos;
    /**
     * Formulario para registrar un nuevo producto y su receta en el sistema.
     */
    private FrmRegistrarProducto frmRegistrarProducto;
    /**
     * Formulario que muestra los detalles, precio y receta de un producto
     * específico.
     */
    private FrmDetalleProducto frmDetalleProducto;
    /**
     * Formulario utilizado para editar la información de un producto existente.
     */
    private FrmEditarProducto frmEditarProducto;

    /**
     * Lista temporal que almacena los productos mostrados en la sesión actual.
     */
    private List<ProductoDTO> listaProductosActual;
    /**
     * Producto que se encuentra actualmente seleccionado por el usuario
     * administrador.
     */
    private ProductoDTO productoSeleccionado;
    /**
     * Lista que almacena el estado en tiempo real de todas las mesas del
     * restaurante.
     */
    private List<MesaDTO> mesas;

    // REPORTES 
    /**
     * Formulario de menú que presenta las diferentes opciones de reportes
     * gerenciales.
     */
    private FrmSeleccionReporte frmSeleccionReporte;
    /**
     * Formulario para configurar y generar el reporte analítico de clientes
     * frecuentes.
     */
    private FrmReporteClientesFrecuentes frmReporteClientesFrecuentes;
    /**
     * Formulario para configurar y generar el reporte histórico de ventas y
     * comandas.
     */
    private FrmReporteComandas frmReporteComandas;

    //Limpiar todo
    /**
     * Limpia completamente el estado de la sesión actual de ventas.
     * <p>
     * Restablece a nulo las mesas, clientes, carrito y comanda en curso para
     * preparar el sistema para un nuevo pedido y evitar fugas de memoria.</p>
     */
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
    /**
     * Objeto de negocio para gestionar las operaciones de Comandas en la base
     * de datos.
     */
    private final ComandaBO comandaBO;

    /**
     * Formulario emergente para confirmar el guardado final de una comanda.
     */
    FrmConfirmacionComanda frmConfirmacionComanda;

    /**
     * Formulario monitor para visualizar los estados actuales (ej. Preparando,
     * Entregado) de las comandas.
     */
    FrmEstadosComanda frmEstadosComanda;

    /**
     * Formulario para la interfaz de edición de productos en una comanda que ya
     * estaba abierta.
     */
    FrmEditarProductosComanda frmEditarProductosComanda;

    /**
     * Referencia temporal a la pantalla de selección de productos de la comanda
     * activa.
     */
    private FrmSeleccionProductos frmComandaActual;

    /**
     * Enumeración que define los posibles orígenes al abrir el buscador de
     * productos, permitiendo al sistema saber a qué pantalla debe retornar los
     * datos.
     */
    public enum OrigenBusquedaProductos {
        /**
         * Indica que el buscador se abrió durante la creación de una comanda
         * nueva.
         */
        NUEVA_COMANDA,
        /**
         * Indica que el buscador se abrió durante la edición de una comanda
         * existente.
         */
        EDICION_COMANDA
    }

    /**
     * Almacena el origen actual desde donde se invocó el buscador de productos.
     */
    private OrigenBusquedaProductos origenActualBusquedaProductos;

    /**
     * Objeto que representa la comanda que se encuentra actualmente en curso o
     * en edición.
     */
    private ComandaDTO comanda;

    /**
     * Constructor principal del Coordinador.
     * <p>
     * Inicializa todas las instancias de los Objetos de Negocio (BO) necesarios
     * para interactuar con la base de datos a lo largo de la ejecución del
     * sistema.</p>
     */
    public Coordinador() {
        this.clienteFrecuenteBO = ClienteFrecuenteBO.getInstance();
        this.meseroBO = MeseroBO.getInstance();
        this.ingredienteBO = IngredienteBO.getInstance();
        this.productoBO = ProductoBO.getInstance();
        this.mesaBO = MesaBO.getInstance();
        this.comandaBO = ComandaBO.getInstance();
    }

    //----- MOSTRAR FRAMES -----
    /**
     * Inicializa el flujo visual del sistema mostrando la pantalla de
     * bienvenida.
     */
    @Override
    public void iniciarSistema() {

        if (frmInicio == null) {
            frmInicio = new FrmInicio(this);
        }
        frmInicio.setVisible(true);
    }

    /**
     * Despliega el menú principal de acciones (módulos administrativos y
     * operativos).
     */
    @Override
    public void mostrarAcciones() {
        if (frmAcciones == null) {
            frmAcciones = new FrmAcciones(this);
        }
        frmAcciones.setVisible(true);
        frmAcciones.toFront();
    }

    //----- CLIENTES -----
    /**
     * Consulta clientes en la base de datos aplicando un filtro de texto.
     *
     * @param filtro Cadena a buscar (nombre, teléfono, etc.).
     * @return Lista de clientes encontrados.
     */
    @Override
    public List<ClienteFrecuenteDTO> buscarClientes(String filtro) {
        try {
            return clienteFrecuenteBO.consultarClientesPorFiltro(filtro);
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Abre la pantalla del catálogo de clientes y carga la información inicial
     * en la tabla.
     */
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

    /**
     * Muestra el formulario vacío para registrar un nuevo cliente frecuente.
     */
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

    /**
     * Procesa la inserción de un nuevo cliente, actualiza la vista y notifica
     * al usuario.
     *
     * @param clienteDTO El objeto con los datos del nuevo cliente a persistir.
     */
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

    /**
     * Refresca el modelo de la tabla de clientes en la pantalla activa.
     */
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

    /**
     * Valida que exista un cliente seleccionado y abre su pantalla de edición
     * con los datos cargados.
     */
    @Override
    public void mostrarEditarCliente() {
        if (clienteSeleccionado == null) {
            JOptionPane.showMessageDialog(null, "Debe seleccionar un cliente primero");
            return;
        }

        if (frmEditarCliente != null) {
            frmEditarCliente.dispose();
        }

        if (frmClientes != null) {
            frmClientes.dispose();
        }
        frmEditarCliente = new FrmEditarCliente(this);
        frmEditarCliente.setVisible(true);
        frmEditarCliente.toFront();
    }

    /**
     * Actualiza la información del cliente seleccionado en la base de datos.
     *
     * @param clienteDTO DTO con los datos modificados.
     */
    @Override
    public void editarCliente(ClienteFrecuenteDTO clienteDTO) {
        try {
            clienteFrecuenteBO.actualizarCliente(clienteDTO);
            JOptionPane.showMessageDialog(null, "Cliente editado correctamente");
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

        if (frmEditarCliente != null) {
            frmEditarCliente.dispose();
            frmEditarCliente = null;
        }

        mostrarClientes();
    }

    /**
     * Establece el cliente que el usuario ha seleccionado en la tabla o
     * buscador.
     * <p>
     * Si se está en medio de la creación/edición de un pedido, envía el cliente
     * seleccionado directamente al formulario del resumen del pedido.</p>
     *
     * @param cliente El cliente seleccionado.
     */
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

    /**
     * @return El cliente actualmente seleccionado en memoria.
     */
    @Override
    public ClienteFrecuenteDTO getClienteSeleccionado() {
        return this.clienteSeleccionado;
    }

    /**
     * @return La lista de clientes que se está mostrando actualmente en la
     * vista.
     */
    @Override
    public List<ClienteFrecuenteDTO> getListaClientesActual() {
        return this.listaClientesActual;
    }

    /**
     * Elimina el cliente especificado si no tiene historial de comandas
     * asociadas.
     *
     * @param cliente El cliente a eliminar.
     */
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

    /**
     * @return La lista total de clientes registrados para consultas generales.
     */
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

    /**
     * Busca o registra automáticamente el perfil genérico "Cliente general".
     *
     * @return El DTO correspondiente al mostrador/público general.
     */
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
    /**
     * Cierra cualquier sesión previa, precarga los meseros si es necesario, y
     * muestra la pantalla de inicio de sesión.
     */
    @Override
    public void mostrarInicioSesionMesero() {
        limpiarSesionComanda();
        precargarMeseros();

        if (frmInicioSesionMesero == null) {
            frmInicioSesionMesero = new FrmInicioSesionMesero(this);
        }
        frmInicioSesionMesero.setVisible(true);
    }

    /**
     * @return El mesero que actualmente ha iniciado sesión en el sistema.
     */
    @Override
    public MeseroDTO getMeseroActual() {
        return meseroActual;
    }

    /**
     * @param mesero Establece el mesero activo en la sesión del sistema.
     */
    @Override
    public void setMeseroActual(MeseroDTO mesero) {
        meseroActual = mesero;
    }

    /**
     * Verifica el usuario contra la base de datos para el inicio de sesión.
     *
     * @param usuario Nombre de usuario del mesero.
     * @return DTO del mesero si existe, null en caso contrario.
     */
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

    /**
     * Inserta meseros por defecto en la base de datos si esta se encuentra
     * vacía.
     */
    @Override
    //DATOS PRECARGADOS
    public void precargarMeseros() {
        try {
            if(!meseroBO.consultarTodos().isEmpty()){
                String usuarios = "";
                for(MeseroDTO mesero : meseroBO.consultarTodos()){
                    usuarios += mesero.getUsuario() + ", ";
                }
                JOptionPane.showMessageDialog(null, "Usuarios pre-cargados: " + usuarios);
            }
            
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
                
                String usuarios = "";
                for(MeseroDTO mesero : meseroBO.consultarTodos()){
                    usuarios += mesero.getUsuario() + ", ";
                }
                JOptionPane.showMessageDialog(null, "Usuarios pre-cargados: " + usuarios);
            }
            
        } catch (NegocioException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "error al precargar los datos");
        }
    }

    // INGREDIENTES 
    /**
     * Muestra la interfaz de gestión de ingredientes, cargando el catálogo
     * completo.
     */
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

    /**
     * @return Lista de todos los ingredientes registrados en la base de datos.
     */
    @Override
    public List<IngredienteDTO> obtenerIngredientes() {
        try {
            return ingredienteBO.consultarTodos();
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar ingredientes");
            return null;
        }
    }

    /**
     * @param ingrediente Guarda en memoria el ingrediente seleccionado por el
     * usuario.
     */
    public void setIngredienteSeleccionado(IngredienteDTO ingrediente) {
        this.ingredienteSeleccionado = ingrediente;
    }

    /**
     * @return El ingrediente que se encuentra actualmente seleccionado.
     */
    @Override
    public IngredienteDTO getIngredienteSeleccionado() {
        return this.ingredienteSeleccionado;
    }

    /**
     * @return La lista temporal de ingredientes que se muestra en la pantalla
     * activa.
     */
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

    /**
     * Muestra el formulario vacío para dar de alta un nuevo insumo/ingrediente.
     */
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

    /**
     * Procesa y guarda un nuevo ingrediente en el inventario.
     *
     * @param ingredienteDTO Objeto con los datos del ingrediente.
     */
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

    /**
     * Actualiza la tabla visual de ingredientes consultando los datos más
     * recientes.
     */
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

    /**
     * Valida que exista un ingrediente seleccionado y abre la pantalla para
     * modificar su stock.
     */
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

    /**
     * Realiza un ajuste manual al inventario sumando o restando existencias.
     *
     * @param idIngrediente Identificador único del insumo.
     * @param cantidad Valor numérico a modificar.
     * @param agregar true para sumar stock, false para restar.
     */
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

    /**
     * Elimina de manera lógica/física el ingrediente seleccionado actualmente.
     */
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

    /**
     * Busca ingredientes cuyo nombre coincida con la cadena ingresada.
     *
     * @param filtro Texto a buscar.
     * @return Lista de ingredientes filtrados.
     */
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
    /**
     * Abre el catálogo de productos con privilegios completos de administrador.
     */
    @Override
    public void mostrarProductosAdmin() {
        frmProductos = new FrmProductos(this, FrmProductos.Modo.ADMINISTRAR);
        frmProductos.setVisible(true);
        frmProductos.toFront();

    }

    /**
     * Filtra la base de datos de productos según un criterio de búsqueda.
     *
     * @param filtro Texto a buscar en el nombre del producto.
     * @return Lista de coincidencias.
     */
    @Override
    public List<ProductoDTO> consultarProductosFiltro(String filtro) {
        try {
            return productoBO.consultarProductosConFiltro(filtro);
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    /**
     * Despliega la pantalla vacía para la creación de un nuevo platillo/bebida.
     */
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

    /**
     * Guarda un nuevo producto, notifica el éxito y recarga el panel de
     * administración.
     *
     * @param productoDTO Datos consolidados del nuevo platillo o bebida.
     */
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

    /**
     * Valida selección y muestra la vista de solo-lectura con la receta y datos
     * del producto.
     */
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

    /**
     * Valida selección y abre el formulario para modificar la información de un
     * producto existente.
     */
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

    /**
     * Actualiza los datos de un producto en la base de datos.
     *
     * @param productoDTO DTO con la información modificada.
     */
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

    /**
     * Elimina el producto seleccionado o lo desactiva lógicamente si tiene
     * comandas históricas ligadas.
     */
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

    /**
     * Guarda el producto seleccionado en memoria. Si se está en medio de la
     * selección de una comanda, retorna el producto automáticamente a dicha
     * pantalla.
     *
     * @param producto El producto a almacenar en sesión.
     */
    @Override
    public void setProductoSeleccionado(ProductoDTO producto) {
        this.productoSeleccionado = producto;

        //si estabsmos armando una comanda le pasamos el producto y la volvemos a mostrar
        if (this.frmComandaActual != null) {
            frmComandaActual.setVisible(true);
            frmComandaActual.recibirProductoSeleccionado(producto);
        }
    }

    /**
     * @return El producto actualmente seleccionado en el sistema.
     */
    @Override
    public ProductoDTO getProductoSeleccionado() {
        return this.productoSeleccionado;
    }

    /**
     * @return La lista de productos que está cargada actualmente en la vista.
     */
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
    /**
     * Carga el estado actual de las mesas y despliega el mapa visual para el
     * mesero.
     */
    @Override
    public void mostrarMesas() {
//        if (frmMesas == null) {
        frmMesas = new FrmMesas(this);
        frmMesas = new FrmMesas(this);
        frmSeleccionProductos = null;
//        }
        frmMesas.setVisible(true);
    }

    /**
     * @return Consulta y retorna la lista completa de mesas del restaurante.
     */
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

    /**
     * Genera automáticamente un lote inicial de mesas si la base de datos está
     * vacía.
     *
     * @return Lista de las mesas inicializadas.
     */
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

    /**
     * @param mesa Establece la mesa en la que se va a operar (abrir/editar
     * comanda).
     */
    @Override
    public void setMesaSeleccionada(MesaDTO mesa) {
        mesaSeleccionada = mesa;
    }

    /**
     * @return La mesa activa en el contexto de la sesión.
     */
    @Override
    public MesaDTO getMesaSeleccionada() {
        return mesaSeleccionada;
    }

    /**
     * Actualiza el estado (Ej. Ocupada a Libre) o características de una mesa.
     *
     * @param mesa DTO con la nueva información.
     * @return La mesa actualizada.
     */
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
    /**
     * Muestra la interfaz de carrito donde el mesero añade productos a la mesa
     * seleccionada.
     */
    @Override
    public void mostrarSeleccionProductos() {
        if (frmSeleccionProductos == null) {
            frmSeleccionProductos = new FrmSeleccionProductos(this);
        }
        frmSeleccionProductos.setVisible(true);
    }

    /**
     * @return Consulta todos los productos registrados en la base de datos sin
     * restricciones.
     */
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

    /**
     * @return Consulta únicamente los productos que se encuentran marcados como
     * 'DISPONIBLE'.
     */
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

    /**
     * Cambia el estado (Disponible/No Disponible) de un platillo en el
     * catálogo.
     *
     * @param idProducto Identificador del producto.
     * @param estado Nuevo estado de disponibilidad.
     */
    public void cambiarDisponibilidadProducto(Long idProducto, DisponibilidadProductoDTO estado) {
        try {
            productoBO.cambiarDisponibilidad(idProducto, estado);

            JOptionPane.showMessageDialog(null, "Disponibilidad actualizada correctamente");

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    /**
     * @param productos Sobrescribe la lista temporal de productos mostrada al
     * usuario.
     */
    @Override
    public void setListaProductosAtual(List<ProductoDTO> productos) {
        listaProductosActual = productos;
    }

    /**
     * @return Los detalles (productos y cantidades) que conforman el pedido en
     * curso.
     */
    @Override
    public List<DetallePedidoDTO> getCarrito() {
        return carrito;
    }

    /**
     * @param carrito Actualiza los elementos seleccionados para la comanda
     * actual.
     */
    @Override
    public void setCarrito(List<DetallePedidoDTO> carrito) {
        this.carrito = carrito;
    }

    /**
     * Abre la pantalla con el resumen del pedido para ser validado antes de
     * enviar a cocina.
     */
    @Override
    public void mostrarResumenPedido() {
        if (frmResumenPedido != null) {
            frmResumenPedido.dispose();
        }
        frmResumenPedido = new FrmResumenPedido(this);
        frmResumenPedido.setVisible(true);
    }

    //----- COMANDAS -----
    /**
     * @return Retorna la entidad ComandaDTO que se está procesando en memoria.
     */
    @Override
    public ComandaDTO getComanda() {
        if (comanda == null) {
            comanda = new ComandaDTO();
        }
        return this.comanda;
    }

    /**
     * @param comanda Establece o reemplaza la comanda activa en la sesión.
     */
    @Override
    public void setComanda(ComandaDTO comanda) {
        this.comanda = comanda;
    }

    /**
     * Finaliza la creación de la comanda: asigna fecha, estado, bloquea la
     * mesa, persiste la transacción y muestra la confirmación.
     */
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

    /**
     * Recupera una orden en progreso vinculada a una mesa específica.
     *
     * @param numeroMesa Identificador de la mesa.
     * @return DTO de la comanda si está abierta, o null si está libre.
     */
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

    /**
     * Muestra el panel monitor de estados para que cocina/meseros actualicen el
     * flujo del pedido (Preparando, Listo, Entregado).
     */
    @Override
    public void mostrarEstadosComanda() {
        frmEstadosComanda = new FrmEstadosComanda(this);
        frmEstadosComanda.setVisible(true);
    }

    /**
     * Guarda los cambios aplicados (productos añadidos o estado modificado) a
     * una comanda existente.
     *
     * @param comanda El DTO de la comanda con sus alteraciones.
     * @return La comanda resultante de la persistencia.
     */
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
    /**
     * Despliega la pantalla de opciones/menú de reportes gerenciales.
     */
    @Override
    public void mostrarOpcionesReporte() {
        if (frmSeleccionReporte == null) {
            frmSeleccionReporte = new FrmSeleccionReporte(this);
        }
        frmSeleccionReporte.setVisible(true);
        frmSeleccionReporte.toFront();
    }

    /**
     * Abre la interfaz para configurar los filtros del reporte de clientes
     * frecuentes.
     */
    @Override
    public void mostrarReporteClientesFrecuentes() {

        if (frmSeleccionReporte != null) {
            frmSeleccionReporte.dispose();
        }

        frmReporteClientesFrecuentes = new FrmReporteClientesFrecuentes(this);
        frmReporteClientesFrecuentes.setVisible(true);
        frmReporteClientesFrecuentes.toFront();
    }

    /**
     * Consulta el historial de comandas para encontrar los clientes más leales
     * según criterios.
     *
     * @param nombre Filtro por aproximación de nombre.
     * @param minimoVisitas Número mínimo de comandas pagadas por el cliente.
     * @return Lista de clientes que cumplen la condición.
     */
    public List<ClienteFrecuenteDTO> consultarReporteClientesFrecuentes(String nombre, Integer minimoVisitas) {

        try {
            return clienteFrecuenteBO.consultarReporte(nombre, minimoVisitas);
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Compila y rellena el archivo Jasper para generar estadísticas de
     * clientes.
     *
     * @param nombre Filtro opcional por nombre de cliente.
     * @param visitas Filtro opcional por cantidad mínima de consumos.
     * @return Documento JasperPrint procesado.
     * @throws Exception Si ocurre un error de lectura o llenado.
     */
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

    /**
     * Abre un cuadro de diálogo para que el usuario guarde el reporte generado
     * como un archivo PDF en su PC.
     *
     * @param jasperPrint El reporte procesado en memoria.
     * @param nombre Filtro utilizado.
     * @param visitas Filtro utilizado.
     */
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

    /**
     * Abre la interfaz para añadir o remover productos de una comanda que ya
     * había sido enviada a cocina.
     */
    @Override
    public void mostrarEditarProductosComanda() {
        frmEditarProductosComanda = new FrmEditarProductosComanda(this);

        frmEditarProductosComanda.setVisible(true);
    }

    /**
     * Muestra el resumen del pedido reflejando los cambios hechos durante una
     * edición posterior.
     */
    @Override
    public void mostrarResumenPedidoEditado() {
        frmResumenPedidoEditado = new FrmResumenPedidoEditado(this);
        frmResumenPedidoEditado.setVisible(true);
    }

    /**
     * Muestra la ventana de configuración de filtros por fecha para el reporte
     * histórico de ventas.
     */
    @Override
    public void mostrarReportesComandas() {
        frmReporteComandas = new FrmReporteComandas(this);
        frmReporteComandas.setVisible(true);
    }

    /**
     * Obtiene una lista plana y formateada de comandas en un rango de fechas
     * para el reporte de Jasper.
     *
     * @param inicio Fecha inicial del filtro.
     * @param fin Fecha final del corte.
     * @return Datos de las comandas formateados para el reporte.
     */
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

    /**
     * Genera el reporte histórico de ventas basado en un rango de fechas.
     *
     * @param lista Resultados de la consulta a la base de datos.
     * @param inicio Fecha inicial del periodo analizado.
     * @param fin Fecha de corte del periodo analizado.
     * @return JasperPrint listo para su visualización.
     * @throws Exception Si falla la compilación del reporte.
     */
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

    /**
     * Evalúa si los ingredientes actuales cubren la cantidad solicitada de un
     * producto.
     *
     * @param producto Producto a evaluar.
     * @param proximaCantidad Unidades que se desean preparar.
     * @return true si el inventario es suficiente.
     */
    @Override
    public boolean verificarStock(ProductoDTO producto, int proximaCantidad) {
        return productoBO.hayStockSuficiente(producto, proximaCantidad);
    }

    /**
     * Crea un perfil en la base de datos para ventas rápidas que no requieran
     * registro (Cliente General).
     *
     * @return El perfil general persistido.
     */
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

    /**
     * Filtra únicamente los productos marcados como 'DISPONIBLES' en base a un
     * texto.
     *
     * @param filtro Nombre del producto.
     * @return Coincidencias en inventario activo.
     */
    @Override
    public List<ProductoDTO> consultarProductosDisponiblesFiltro(String filtro) {
        try {
            return productoBO.consultarProductosDisponiblesConFiltro(filtro);
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return null;
        }
    }

    /**
     * Abre el catálogo de productos en Modo SELECCIONAR para agregar a un
     * pedido en edición.
     *
     * @param frmOrigen Formulario que solicita la apertura del catálogo.
     */
    @Override
    public void mostrarProductosSelec(FrmEditarProductosComanda frmOrigen) {
        frmProductos = new FrmProductos(this, FrmProductos.Modo.SELECCIONAR);
        frmProductos.setVisible(true);
        frmProductos.toFront();
        frmOrigen.setVisible(false);
    }

    /**
     * Inicializa el flujo de búsqueda de productos pasando el contexto de la
     * comanda actual.
     *
     * @param frmActual Pantalla de selección base.
     */
    public void abrirBuscadorParaComanda(FrmSeleccionProductos frmActual) {
        this.frmComandaActual = frmActual;
        frmActual.setVisible(false);

        FrmProductos buscador = new FrmProductos(this, FrmProductos.Modo.SELECCIONAR);
        buscador.setVisible(true);
    }

    /**
     * Oculta temporalmente la interfaz de la comanda y despliega el buscador de
     * productos. Mantiene un estado (`OrigenBusquedaProductos`) para saber a
     * qué pantalla devolver el dato elegido.
     *
     * @param origen Contexto desde el que se llama (NUEVA_COMANDA o
     * EDICION_COMANDA).
     */
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

    /**
     * Cierra el buscador de productos y restaura la visibilidad del formulario
     * de comanda original.
     */
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

    /**
     * Metodo que muestra productos en seleccion
     */
    @Override
    public void mostrarProductosSelec() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * Abre un buscador emergente de clientes para asociarlo a una nueva comanda
     * antes de enviarla.
     *
     * @param frmActual Referencia a la pantalla del resumen del pedido que
     * quedará a la espera.
     */
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

    /**
     * Abre el buscador de clientes para cambiar o asociar un cliente a una
     * comanda que se está editando.
     *
     * @param frmActual Referencia al resumen del pedido en modo edición.
     */
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

    /**
     * Oculta temporalmente la pantalla actual y abre el catálogo de
     * ingredientes para seleccionar los elementos de la receta. Recibe un
     * callback para retornar los datos.
     *
     * @param frmActual Referencia a la pantalla de registro de producto.
     */
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

    /**
     * Abre el catálogo de ingredientes para agregar/editar insumos nuevos a la
     * receta de un producto en edición.
     *
     * @param frmActual Pantalla de edición a la espera de los ingredientes.
     */
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

    /**
     * Restaura la visibilidad del formulario de producto (Registro o Edición)
     * después de haber cerrado el buscador de ingredientes.
     */
    public void volverDeBusquedaIngredientes() {
        if (frmRegistrarProducto != null) {
            frmRegistrarProducto.setVisible(true);
        } else if (frmEditarProducto != null) {
            frmEditarProducto.setVisible(true);
        }
    }

    /**
     * Restaura la visibilidad de la pantalla de resumen de pedido tras haber
     * utilizado el buscador de clientes.
     */
    public void volverDeBusquedaClientes() {
        if (frmResumenPedido != null) {
            frmResumenPedido.setVisible(true);
        } else if (frmResumenPedidoEditado != null) {
            frmResumenPedidoEditado.setVisible(true);
        }
    }

    /**
     * Método sobrecargado para eliminar un cliente pasándolo por parámetro
     * explícitamente, validando que no tenga comandas en su historial.
     *
     * @param cliente El cliente exacto a eliminar.
     */
    @Override
    public void eliminarCliente(ClienteFrecuenteDTO cliente) {
        try {
            if (clienteFrecuenteBO.clienteConComandas(cliente.getIdCliente())) {
                JOptionPane.showMessageDialog(null, "No se pueden eliminar clientes con comandas");
            } else {
                clienteFrecuenteBO.eliminarCliente(cliente);
                JOptionPane.showMessageDialog(null, "Cliente eliminado con exito");
                listaClientesActual = clienteFrecuenteBO.consultarTodos();
                actualizarTablaClientes();
            }
        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }
}
