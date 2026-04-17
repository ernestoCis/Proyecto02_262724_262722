package interfaces;

import dtos.ClienteFrecuenteDTO;
import dtos.ComandaDTO;
import dtos.DetallePedidoDTO;
import dtos.IngredienteDTO;
import dtos.MesaDTO;
import dtos.MeseroDTO;
import dtos.ProductoDTO;
import java.io.File;
import dtos.ReporteComandaDTO;
import java.time.LocalDate;
import java.util.List;
import net.sf.jasperreports.engine.JasperPrint;
import pantallas.FrmEditarProductosComanda;
import pantallas.FrmResumenPedido;
import pantallas.FrmResumenPedidoEditado;

/**
 * Interfaz principal del Mediador (Coordinador) del sistema.
 * <p>Centraliza el flujo de navegación entre pantallas y el intercambio de datos (DTOs) 
 * entre la capa de presentación y la capa lógica/servicios. Asegura el desacoplamiento 
 * de la interfaz de usuario.</p>
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public interface ICoordinador {

    // ==========================================
    // NAVEGACIÓN GENERAL Y SESIÓN
    // ==========================================

    /** Inicia el sistema y muestra la pantalla principal o de inicio de sesión. */
    void iniciarSistema();

    /** Muestra el menú principal de acciones administrativas/operativas. */
    void mostrarAcciones();

    /** Muestra la pantalla de inicio de sesión específica para meseros. */
    void mostrarInicioSesionMesero();

    /**
     * Obtiene el mesero que ha iniciado sesión actualmente.
     * @return El DTO del mesero en sesión.
     */
    MeseroDTO getMeseroActual();

    /**
     * Busca un mesero en la base de datos a partir de su nombre de usuario.
     * @param usuario Nombre de usuario ingresado.
     * @return El DTO del mesero si existe, o null en caso contrario.
     */
    MeseroDTO buscarMeseroPorUsuario(String usuario);

    /**
     * Establece el mesero activo en la sesión actual.
     * @param mesero El mesero que acaba de iniciar sesión.
     */
    void setMeseroActual(MeseroDTO mesero);

    /** Realiza una carga inicial de los meseros registrados en el sistema. */
    void precargarMeseros();


    // ==========================================
    // MÓDULO DE CLIENTES FRECUENTES
    // ==========================================

    /**
     * Busca clientes frecuentes según un criterio de texto.
     * @param filtro Texto a buscar (nombre, teléfono, etc.).
     * @return Lista de clientes que coinciden con el filtro.
     */
    List<ClienteFrecuenteDTO> buscarClientes(String filtro);

    /** Muestra el catálogo o lista general de clientes frecuentes. */
    void mostrarClientes();

    /** Muestra el formulario para registrar un nuevo cliente frecuente. */
    void mostrarRegistrarCliente();

    /**
     * Procesa el registro de un nuevo cliente en el sistema.
     * @param clienteDTO Datos del cliente a registrar.
     */
    void registrarCliente(ClienteFrecuenteDTO clienteDTO);

    /** Refresca la vista de la tabla de clientes con los datos más recientes. */
    void actualizarTablaClientes();

    /** Muestra el formulario con los datos del cliente seleccionado para edición. */
    void mostrarEditarCliente();

    /**
     * Actualiza la información de un cliente existente.
     * @param clienteDTO Los datos actualizados del cliente.
     */
    void editarCliente(ClienteFrecuenteDTO clienteDTO);

    /**
     * Guarda en memoria temporal el cliente seleccionado por el usuario.
     * @param cliente El cliente seleccionado.
     */
    void setClienteSeleccionado(ClienteFrecuenteDTO cliente);

    /** @return El cliente actualmente seleccionado en memoria. */
    ClienteFrecuenteDTO getClienteSeleccionado();

    /** @return La lista completa de clientes cargados en la sesión actual. */
    List<ClienteFrecuenteDTO> getListaClientesActual();

    /** Elimina al cliente seleccionado actualmente de la base de datos. */
    void eliminarCliente();

    /** @return La lista total de clientes registrados para consultas generales. */
    List<ClienteFrecuenteDTO> consultarClientes();

    /** @return El perfil de "Cliente General" para ventas al público en general. */
    ClienteFrecuenteDTO getClienteGeneral();

    /**
     * Crea y registra el perfil por defecto de "Cliente General" si no existe.
     * @return El cliente general registrado o recuperado.
     */
    ClienteFrecuenteDTO registrarClienteGeneral();


    // ==========================================
    // MÓDULO DE INGREDIENTES
    // ==========================================

    /** Muestra el catálogo de inventario de ingredientes. */
    void mostrarIngredientes();

    /** @return Lista completa de todos los ingredientes registrados. */
    List<IngredienteDTO> obtenerIngredientes();

    /** Guarda temporalmente el ingrediente seleccionado por el usuario. */
    void setIngredienteSeleccionado(IngredienteDTO cliente);

    /** @return El ingrediente actualmente seleccionado. */
    IngredienteDTO getIngredienteSeleccionado();

    /** @return La lista de ingredientes cargada en la vista actual. */
    List<IngredienteDTO> getListaIngredientesActual();

    /** Muestra el formulario para dar de alta un nuevo ingrediente. */
    void mostrarRegistrarIngrediente();

    /** @param ingredienteDTO Datos del nuevo ingrediente a registrar. */
    void registrarIngrediente(IngredienteDTO ingredienteDTO);

    /** Refresca la tabla de ingredientes para mostrar los cambios recientes. */
    void actualizarTablaIngredientes();

    /** Elimina lógicamente o físicamente el ingrediente seleccionado. */
    void eliminarIngrediente();

    /**
     * Busca ingredientes por coincidencia de nombre o código.
     * @param filtro Texto de búsqueda.
     * @return Lista de ingredientes filtrados.
     */
    List<IngredienteDTO> buscarIngredientes(String filtro);


    // ==========================================
    // MÓDULO DE PRODUCTOS
    // ==========================================

    /** Muestra el catálogo de productos con permisos de administrador (CRUD). */
    void mostrarProductosAdmin();
    
    /**
     * Muestra la selección de productos para agregar a una comanda en edición.
     * @param frmOrigen El formulario de origen que solicita los productos.
     */
    void mostrarProductosSelec(FrmEditarProductosComanda frmOrigen);

    /** Muestra la pantalla para seleccionar productos en una nueva comanda. */
    void mostrarProductosSelec();

    /** Muestra el formulario para crear un nuevo platillo/bebida. */
    void mostrarRegistrarProducto();

    /** @param productoDTO Datos del nuevo producto a crear. */
    void registrarProducto(ProductoDTO productoDTO);

    /** Muestra la vista detallada (ingredientes, precios) de un producto. */
    void mostrarDetalleProducto();

    /** Muestra la pantalla de edición para el producto seleccionado. */
    void mostrarEditarProducto();

    /** @param productoDTO Datos modificados del producto a guardar. */
    void actualizarProducto(ProductoDTO productoDTO);

    /** Elimina el producto seleccionado del catálogo. */
    void eliminarProducto();

    /** Guarda el producto sobre el que se está operando actualmente. */
    void setProductoSeleccionado(ProductoDTO producto);

    /** @return El producto actualmente seleccionado en memoria. */
    ProductoDTO getProductoSeleccionado();

    /** @return La lista de productos mostrada en la pantalla actual. */
    List<ProductoDTO> getListaProductosActual();

    /** @param productos Actualiza la lista de productos en memoria. */
    void setListaProductosAtual(List<ProductoDTO> productos);

    /** @return Obtiene todo el catálogo de productos sin filtrar. */
    List<ProductoDTO> obtenerProductos();

    /**
     * Verifica si hay suficientes ingredientes para preparar una cantidad de producto.
     * @param producto Producto a evaluar.
     * @param proximaCantidad Cantidad que se desea agregar.
     * @return true si hay stock disponible, false de lo contrario.
     */
    boolean verificarStock(ProductoDTO producto, int proximaCantidad);

    /** @return Lista de productos que tienen stock suficiente para ser vendidos. */
    List<ProductoDTO> obtenerProductosDisponibles();

    /**
     * @param filtro Búsqueda por nombre de producto en todo el catálogo.
     * @return Lista de productos coincidentes.
     */
    List<ProductoDTO> consultarProductosFiltro(String filtro);

    /**
     * @param filtro Búsqueda por nombre de producto solo entre los disponibles.
     * @return Lista de productos disponibles coincidentes.
     */
    List<ProductoDTO> consultarProductosDisponiblesFiltro(String filtro);


    // ==========================================
    // MÓDULO OPERATIVO (MESAS Y COMANDAS)
    // ==========================================

    /** Muestra el mapa o listado visual de mesas del restaurante. */
    void mostrarMesas();

    /** @return Lista de las mesas configuradas en el sistema. */
    List<MesaDTO> obtenerMesas();

    /**
     * Genera o recupera la estructura inicial de las mesas (Ej. 1 al 20).
     * @return Lista de mesas inicializadas.
     */
    List<MesaDTO> cargaMasivaMesas();

    /** Muestra el panel donde el mesero elige productos para la comanda actual. */
    void mostrarSeleccionProductos();

    /** Guarda la mesa que el mesero acaba de seleccionar para abrir o modificar comanda. */
    void setMesaSeleccionada(MesaDTO mesa);

    /** @return La mesa activa en la sesión actual. */
    MesaDTO getMesaSeleccionada();

    /** @return Lista de productos (Detalles) que se han agregado al pedido. */
    List<DetallePedidoDTO> getCarrito();

    /** @param carrito Actualiza los elementos del carrito actual. */
    void setCarrito(List<DetallePedidoDTO> carrito);

    /** Muestra la pantalla con el resumen del pedido antes de enviarlo a cocina. */
    void mostrarResumenPedido();

    /** @return La comanda que se está procesando actualmente. */
    ComandaDTO getComanda();

    /** @param comanda Establece la comanda activa en el contexto. */
    void setComanda(ComandaDTO comanda);

    /** Muestra la ventana final de confirmación antes de guardar la comanda. */
    void mostrarConfirmacionComanda();

    /** Limpia los datos de mesa, carrito y comanda activa para iniciar una nueva. */
    void limpiarSesionComanda();

    /** Muestra el monitor de estados de comanda (Pendiente, Preparando, etc.). */
    void mostrarEstadosComanda();

    /**
     * Busca si existe un pedido en curso para una mesa específica.
     * @param numeroMesa Identificador de la mesa.
     * @return La comanda activa, o null si la mesa está libre.
     */
    ComandaDTO buscarComandaAbiertaPorMesa(Integer numeroMesa);

    /**
     * Guarda los cambios de estado o productos de una comanda.
     * @param comanda DTO con la comanda a actualizar.
     * @return La comanda persistida.
     */
    ComandaDTO actualizarComanda(ComandaDTO comanda);

    /** Muestra la pantalla para modificar los productos de un pedido ya enviado. */
    void mostrarEditarProductosComanda();

    /**
     * Actualiza el estado (Libre/Ocupada) de una mesa.
     * @param mesa DTO con la información de la mesa.
     * @return La mesa actualizada.
     */
    MesaDTO actualizarMesa(MesaDTO mesa);

    /** Muestra el resumen de un pedido que ha sufrido modificaciones. */
    void mostrarResumenPedidoEditado();

    /**
     * Abre un buscador emergente para asociar un cliente a una comanda nueva.
     * @param frmActual Referencia a la pantalla del resumen del pedido.
     */
    void abrirBuscadorClientesParaComanda(FrmResumenPedido frmActual);

    /**
     * Abre un buscador emergente para asociar un cliente a una comanda en edición.
     * @param frmActual Referencia a la pantalla del resumen del pedido editado.
     */
    void abrirBuscadorClientesParaComanda(FrmResumenPedidoEditado frmActual);


    // ==========================================
    // MÓDULO DE REPORTES (JASPER)
    // ==========================================

    /** Muestra el menú de selección de los diferentes reportes administrativos. */
    void mostrarOpcionesReporte();

    /** Muestra la interfaz para generar o ver el reporte de clientes más frecuentes. */
    void mostrarReporteClientesFrecuentes();

    /**
     * Genera el documento Jasper con la estadística de los clientes.
     * @param nombre Filtro opcional por nombre.
     * @param visitas Filtro mínimo de visitas.
     * @return Objeto JasperPrint listo para ser visualizado o exportado.
     * @throws Exception Si ocurre un error al compilar o llenar el reporte.
     */
    JasperPrint generarReporteClientes(String nombre, Integer visitas) throws Exception;

    /** Muestra la interfaz para definir filtros y generar el reporte histórico de comandas. */
    void mostrarReportesComandas();

    /**
     * Consulta la base de datos para obtener las comandas cerradas en un lapso.
     * @param inicio Fecha inicial del rango.
     * @param fin Fecha final del rango.
     * @return Lista de objetos adaptados para el reporte.
     */
    List<ReporteComandaDTO> obetnerComandasPorRangoFechas(LocalDate inicio, LocalDate fin);

    /**
     * Construye el documento Jasper para el historial de ventas y comandas.
     * @param lista Los datos extraídos de la base de datos.
     * @param inicio Fecha inicio mostrada en el encabezado.
     * @param fin Fecha fin mostrada en el encabezado.
     * @return Objeto JasperPrint listo para visualizar.
     * @throws Exception Si falla la generación del archivo.
     */
    JasperPrint generarJasperComandas(List<ReporteComandaDTO> lista, LocalDate inicio, LocalDate fin) throws Exception;

}