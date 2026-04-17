package pantallas;

import componentes.BotonEstilizado;
import componentes.BotonRegresar;
import controlador.Coordinador;
import dtos.ProductoDTO;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 * Pantalla para el catalogo de productos del restaurante.
 * <p>
 * Funciona en dos modalidades: administracion total o seleccion para
 * ventas.</p>
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmProductos extends JFrame {

    /**
     * Etiqueta de instrucciones dinamicas segun el modo.
     */
    private JLabel lblNota;

    /**
     * Enumeracion para definir el comportamiento de la pantalla.
     */
    public enum Modo {
        /**
         * Permite editar, registrar y eliminar productos.
         */
        ADMINISTRAR,
        /**
         * Permite elegir un producto para agregarlo a una comanda.
         */
        SELECCIONAR
    }

    /**
     * Almacena el modo en el que se abrio la ventana.
     */
    private final Modo modoActual;
    /**
     * Referencia al controlador de la aplicacion.
     */
    private final Coordinador coordinador;

    /**
     * Componente para la visualizacion de datos.
     */
    private JTable tblProductos;
    /**
     * Componente para la visualizacion de datos.
     */
    private DefaultTableModel modeloTabla;
    /**
     * Componente para la visualizacion de datos.
     */
    private JTextField txtBuscar;

    /**
     * Botones de navegacion y acciones CRUD.
     */
    private BotonRegresar btnRegresar;
    /**
     * Botones de navegacion y acciones CRUD.
     */
    private BotonEstilizado btnRegistrar, btnEditar, btnEliminar, btnSeleccionar;

    /**
     * Listas para el manejo y filtrado de la informacion.
     */
    private List<ProductoDTO> listaOriginal;
    /**
     * Listas para el manejo y filtrado de la informacion.
     */
    private List<ProductoDTO> listaMostrada;

    /**
     * Constructor principal de la pantalla de productos.
     *
     * @param coordinador El coordinador del sistema.
     * @param modo El modo de operacion (ADMINISTRAR o SELECCIONAR).
     */
    public FrmProductos(Coordinador coordinador, Modo modo) {
        this.coordinador = coordinador;
        this.modoActual = modo;
        this.listaOriginal = this.coordinador.getListaProductosActual();

        configurarVentana();
        inicializarComponentes();
        configurarVisibilidadSegunModo();

        accionBuscar();
    }

    /**
     * Ajusta el titulo y propiedades basicas del frame.
     */
    private void configurarVentana() {
        setTitle(modoActual == Modo.ADMINISTRAR ? "Gestión de Productos" : "Seleccionar Producto");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    /**
     * Inicializa y distribuye los componentes graficos.
     */
    private void inicializarComponentes() {

        Color colorMostaza = new Color(229, 171, 75);
        Color colorFondo = new Color(238, 238, 238);
        Color colorTablaHeader = new Color(177, 201, 182);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);

        JPanel panelSuperiorContenedor = new JPanel(new BorderLayout());
        panelSuperiorContenedor.setPreferredSize(new Dimension(1000, 115));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(colorMostaza);
        panelSuperior.setPreferredSize(new Dimension(1000, 110));

        JLabel lblLogo = new JLabel();
        lblLogo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
        lblLogo.setPreferredSize(new Dimension(90, 90));

        ImageIcon iconoLogo = new ImageIcon("src\\main\\resources\\imagenes\\icono_restaurante.png");
        Image logoEscalado = iconoLogo.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(logoEscalado));

        JPanel panelTitulo = new JPanel(new GridBagLayout());
        panelTitulo.setOpaque(false);
        panelTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 70));

        JLabel lblTitulo = new JLabel(modoActual == Modo.ADMINISTRAR ? "Productos" : "Buscador de Productos");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(52, 58, 70));

        panelTitulo.add(lblTitulo);

        panelSuperior.add(lblLogo, BorderLayout.WEST);
        panelSuperior.add(panelTitulo, BorderLayout.CENTER);
        panelSuperiorContenedor.add(panelSuperior, BorderLayout.CENTER);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(colorFondo);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));

        JPanel panelBusqueda = new JPanel(new BorderLayout());
        panelBusqueda.setOpaque(false);
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));

        btnRegresar = new BotonRegresar();

        JPanel panelIzquierdoBusqueda = new JPanel();
        panelIzquierdoBusqueda.setOpaque(false);
        panelIzquierdoBusqueda.add(btnRegresar);

        txtBuscar = new JTextField();
        txtBuscar.setPreferredSize(new Dimension(420, 40));
        txtBuscar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtBuscar.setForeground(Color.GRAY);
        txtBuscar.setBackground(Color.WHITE);

        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 15, 8, 15)
        ));

        ponerPlaceholder();

        JPanel panelCentroBusqueda = new JPanel();
        panelCentroBusqueda.setOpaque(false);
        panelCentroBusqueda.add(txtBuscar);

        panelBusqueda.add(panelIzquierdoBusqueda, BorderLayout.WEST);
        panelBusqueda.add(panelCentroBusqueda, BorderLayout.CENTER);

        String[] columnas = {
            "Nombre", "Precio", "Tipo", "Disponibilidad"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblProductos = new JTable(modeloTabla);
        tblProductos.setRowHeight(40);
        tblProductos.setFont(new Font("SansSerif", Font.PLAIN, 14));

        ((DefaultTableCellRenderer) tblProductos.getTableHeader()
                .getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        tblProductos.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tblProductos.getTableHeader().setBackground(colorTablaHeader);
        tblProductos.getTableHeader().setForeground(Color.BLACK);

        tblProductos.setSelectionBackground(new Color(220, 230, 220));
        tblProductos.setSelectionForeground(Color.BLACK);

        tblProductos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnEliminar.setEnabled(tblProductos.getSelectedRow() != -1);
            }
        });

        lblNota = new JLabel();
        lblNota.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lblNota.setForeground(new Color(120, 120, 120));
        lblNota.setHorizontalAlignment(SwingConstants.CENTER);
        lblNota.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

        if (modoActual == Modo.SELECCIONAR) {
            lblNota.setText("Da doble click para agregar producto a comanda");
        } else {
            lblNota.setText("Da doble click para ver el detalle del producto");
        }
        JScrollPane scrollPane = new JScrollPane(tblProductos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(colorFondo);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);

        btnRegistrar = new BotonEstilizado("+ Registrar");
        btnEditar = new BotonEstilizado("Editar");
        btnEliminar = new BotonEstilizado("Eliminar");
        btnSeleccionar = new BotonEstilizado("Seleccionar");

        panelBotones.add(btnRegistrar);
        panelBotones.add(Box.createHorizontalStrut(15));
        panelBotones.add(btnEditar);
        panelBotones.add(Box.createHorizontalStrut(15));
        panelBotones.add(btnEliminar);
        panelBotones.add(Box.createHorizontalStrut(15));
        panelBotones.add(btnSeleccionar);

        panelInferior.add(Box.createVerticalStrut(8));
        panelInferior.add(panelBotones);

        panelCentro.add(panelBusqueda, BorderLayout.NORTH);

        panelCentro.add(panelInferior, BorderLayout.SOUTH);
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setOpaque(false);

        panelTabla.add(lblNota, BorderLayout.NORTH);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        panelCentro.add(panelTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelSuperiorContenedor, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);

        registrarEventos();
    }

    /**
     * Muestra u oculta botones dependiendo del <code>modoActual</code>.
     */
    private void configurarVisibilidadSegunModo() {
        boolean esAdmin = (modoActual == Modo.ADMINISTRAR);

        btnRegistrar.setVisible(esAdmin);
        btnEditar.setVisible(esAdmin);
        btnEliminar.setVisible(esAdmin);

        btnSeleccionar.setVisible(!esAdmin);
    }

    /**
     * Registra los escuchas para teclado, mouse y botones.
     */
    private void registrarEventos() {

        txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                accionBuscar();
            }
        });

        tblProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tblProductos.getSelectedRow();
                    if (fila != -1) {
                        ProductoDTO producto = listaMostrada.get(fila);

                        // Le guardamos el producto al coordinador
                        coordinador.setProductoSeleccionado(producto);

                        if (modoActual == Modo.ADMINISTRAR) {
                            coordinador.mostrarDetalleProducto();
                        } else {
                            // Si es modo selección solo le pedimos regresar
                            dispose();
                            coordinador.volverDeBusquedaProductos();
                        }
                    }
                }
            }
        });

        btnSeleccionar.addActionListener(e -> {
            int fila = tblProductos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un producto");
                return;
            }
            ProductoDTO producto = listaMostrada.get(fila);

            coordinador.setProductoSeleccionado(producto);
            dispose();
            coordinador.volverDeBusquedaProductos();
        });

        btnRegistrar.addActionListener(e -> {
            dispose();
            coordinador.mostrarRegistrarProducto();
        });

        btnEditar.addActionListener(e -> {
            int fila = tblProductos.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un producto");
                return;
            }
            ProductoDTO producto = listaMostrada.get(fila);
            coordinador.setProductoSeleccionado(producto);

            coordinador.mostrarEditarProducto();
            dispose();
        });

        btnRegresar.addActionListener(e -> {
            dispose();
            if (modoActual == Modo.ADMINISTRAR) {
                coordinador.mostrarAcciones();
            } else {
                coordinador.volverDeBusquedaProductos();
            }
        });

        btnEliminar.addActionListener(e -> {
            eliminarProductoSeleccionado();
            accionBuscar();
        });
    }

    /**
     * Llena la tabla con los datos de los productos.
     *
     * @param lista Coleccion de productos a cargar.
     */
    private void cargarDatosTabla(List<ProductoDTO> lista) {
        modeloTabla.setRowCount(0);

        this.listaMostrada = lista;

        if (lista != null) {
            for (ProductoDTO p : lista) {
                Object[] fila = {
                    p.getNombre(),
                    p.getPrecio(),
                    p.getTipo(),
                    p.getDisponibilidad()
                };
                modeloTabla.addRow(fila);
            }
        }
    }

    /**
     * Realiza la busqueda de productos filtrando por el texto ingresado.
     * <p>
     * Considera si el producto debe estar disponible o no segun el modo.</p>
     */
    private void accionBuscar() {
        String texto = txtBuscar.getText().trim();

        if (texto.equals("Buscar producto")) {
            texto = "";
        }
        List<ProductoDTO> filtrados;

        if (modoActual == Modo.SELECCIONAR) {
            filtrados = coordinador.consultarProductosDisponiblesFiltro(texto);
        } else {
            filtrados = coordinador.consultarProductosFiltro(texto);
        }

        cargarDatosTabla(filtrados);
    }

    /**
     * Controla el comportamiento del texto de sugerencia en el buscador.
     */
    private void ponerPlaceholder() {
        txtBuscar.setText("Buscar producto");
        txtBuscar.setForeground(Color.GRAY);

        txtBuscar.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtBuscar.getText().equals("Buscar producto")) {
                    txtBuscar.setText("");
                    txtBuscar.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtBuscar.getText().trim().isEmpty()) {
                    txtBuscar.setText("Buscar producto");
                    txtBuscar.setForeground(Color.GRAY);
                }
            }
        });
    }

    /**
     * Refresca la informacion de la tabla externamente.
     *
     * @param productos Nueva lista de productos.
     */
    public void actualizarTablaProductos(List<ProductoDTO> productos) {
        this.listaOriginal = productos;
        cargarDatosTabla(productos);
        this.listaMostrada = productos;
    }

    /**
     * Proceso para dar de baja el producto seleccionado tras confirmacion.
     */
    private void eliminarProductoSeleccionado() {
        int fila = tblProductos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto");
            return;
        }

        ProductoDTO producto = listaMostrada.get(fila);

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar el producto " + producto.getNombre() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            coordinador.setProductoSeleccionado(producto);
            coordinador.eliminarProducto();
        }
    }
}
