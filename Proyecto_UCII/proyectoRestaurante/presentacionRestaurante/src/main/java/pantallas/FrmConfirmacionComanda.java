package pantallas;

import controlador.Coordinador;
import dtos.ComandaDTO;
import dtos.DetallePedidoDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Ventana de confirmación que muestra el resumen detallado de una comanda
 * finalizada.
 * <p>
 * Exhibe información general como el folio, mesa, cliente y el desglose de
 * productos solicitados con sus respectivos subtotales y notas especiales.</p>
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmConfirmacionComanda extends JFrame {

    /**
     * Enlace con el coordinador para la gestión de navegación y datos de
     * sesión.
     */
    private final Coordinador coordinador;

    /**
     * Botón para cerrar la sesión actual del mesero y salir del sistema.
     */
    private JButton btnSalir;
    /**
     * Botón para confirmar la lectura del resumen y regresar al mapa de mesas.
     */
    private JButton btnAceptar;

    /**
     * Tabla que despliega el desglose de productos, cantidades y notas del
     * pedido.
     */
    private JTable tblDetalles;
    /**
     * Modelo de datos para gestionar el contenido de la tabla de detalles.
     */
    private DefaultTableModel modeloTabla;

    /**
     * Etiqueta que muestra el número de folio único de la comanda.
     */
    private JLabel lblFolio;
    /**
     * Etiqueta que indica el número de mesa asignado.
     */
    private JLabel lblMesa;
    /**
     * Etiqueta que muestra la fecha y hora de creación de la comanda.
     */
    private JLabel lblFecha;
    /**
     * Etiqueta que indica el estado actual del pedido (ej. PAGADO).
     */
    private JLabel lblEstado;
    /**
     * Etiqueta que muestra el monto total acumulado de la cuenta.
     */
    private JLabel lblTotal;
    /**
     * Etiqueta que muestra el nombre del cliente vinculado a la comanda.
     */
    private JLabel lblClienteAsociado;

    /**
     * Construye la ventana de confirmación de comanda.
     * <p>
     * Inicializa los componentes visuales y carga automáticamente los datos de
     * la comanda almacenada en la sesión del coordinador.</p>
     *
     * * @param coordinador El coordinador del sistema que provee el contexto
     * de la aplicación.
     */
    public FrmConfirmacionComanda(Coordinador coordinador) {
        this.coordinador = coordinador;
        configurarVentana();
        inicializarComponentes();
        cargarDatosComanda();
    }

    /**
     * Establece las dimensiones, título y centrado de la ventana principal.
     */
    private void configurarVentana() {
        setTitle("Restaurante");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    /**
     * Crea y organiza la jerarquía de paneles, etiquetas, tablas y botones de
     * la interfaz.
     */
    private void inicializarComponentes() {
        Color colorMostaza = new Color(229, 171, 75);
        Color colorFondo = new Color(239, 239, 239);
        Color colorTablaHeader = new Color(242, 205, 205);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(colorMostaza);
        panelSuperior.setPreferredSize(new Dimension(980, 110));

        JLabel lblLogo = new JLabel();
        lblLogo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        lblLogo.setPreferredSize(new Dimension(100, 90));

        ImageIcon iconoLogo = new ImageIcon("src\\main\\resources\\imagenes\\icono_restaurante.png");
        Image logoEscalado = iconoLogo.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(logoEscalado));

        JPanel panelTitulo = new JPanel(new GridBagLayout());
        panelTitulo.setOpaque(false);

        JLabel lblTitulo = new JLabel("Comandas");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(52, 58, 70));

        panelTitulo.add(lblTitulo);

        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 25));
        panelDerecho.setOpaque(false);
        panelDerecho.setPreferredSize(new Dimension(100, 90));

        btnSalir = new JButton();
        btnSalir.setFocusPainted(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        ImageIcon salirOriginal = new ImageIcon("src\\main\\resources\\imagenes\\icono_salir.png");
        Image salirEscalado = salirOriginal.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        btnSalir.setIcon(new ImageIcon(salirEscalado));

        panelDerecho.add(btnSalir);

        panelSuperior.add(lblLogo, BorderLayout.WEST);
        panelSuperior.add(panelTitulo, BorderLayout.CENTER);
        panelSuperior.add(panelDerecho, BorderLayout.EAST);

        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(colorFondo);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(15, 30, 20, 30));

        JPanel panelContenido = new JPanel();
        panelContenido.setBackground(new Color(235, 235, 235));
        panelContenido.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setPreferredSize(new Dimension(900, 420));
        panelContenido.setMaximumSize(new Dimension(900, 420));

        JPanel panelInfoSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 0));
        panelInfoSuperior.setOpaque(false);

        lblFolio = crearLabelInfo("Folio: ");
        lblMesa = crearLabelInfo("Mesa: ");
        lblFecha = crearLabelInfo("Fecha: ");
        lblEstado = crearLabelInfo("Estado: ");
        lblTotal = crearLabelInfo("Total: ");

        panelInfoSuperior.add(lblFolio);
        panelInfoSuperior.add(lblMesa);
        panelInfoSuperior.add(lblFecha);
        panelInfoSuperior.add(lblEstado);
        panelInfoSuperior.add(lblTotal);

        JPanel panelCliente = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 8));
        panelCliente.setOpaque(false);

        lblClienteAsociado = crearLabelInfo("Cliente asociado: ");

        panelCliente.add(lblClienteAsociado);

        String[] columnas = {"Producto", "Cantidad", "Subtotal", "Nota"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDetalles = new JTable(modeloTabla);
        tblDetalles.setRowHeight(55);
        tblDetalles.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tblDetalles.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tblDetalles.getTableHeader().setBackground(colorTablaHeader);
        tblDetalles.getTableHeader().setForeground(Color.BLACK);
        tblDetalles.setSelectionBackground(new Color(245, 220, 220));
        tblDetalles.setSelectionForeground(Color.BLACK);

        JScrollPane scrollTabla = new JScrollPane(tblDetalles);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder());
        scrollTabla.setPreferredSize(new Dimension(820, 330));
        scrollTabla.setMaximumSize(new Dimension(820, 330));
        scrollTabla.getVerticalScrollBar().setUnitIncrement(16);

        JPanel panelTabla = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
        panelTabla.setOpaque(false);
        panelTabla.add(scrollTabla);

        panelContenido.add(panelInfoSuperior);
        panelContenido.add(panelCliente);
        panelContenido.add(Box.createVerticalStrut(10));
        panelContenido.add(panelTabla);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        panelInferior.setBackground(new Color(235, 235, 235));
        panelInferior.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        panelInferior.setPreferredSize(new Dimension(900, 65));
        panelInferior.setMaximumSize(new Dimension(900, 65));

        btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btnAceptar.setFocusPainted(false);
        btnAceptar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAceptar.setBackground(Color.WHITE);
        btnAceptar.setForeground(new Color(55, 55, 55));
        btnAceptar.setPreferredSize(new Dimension(170, 34));
        btnAceptar.setBorder(BorderFactory.createLineBorder(colorMostaza, 1));

        panelInferior.add(btnAceptar);

        panelCentro.add(panelContenido);
        panelCentro.add(Box.createVerticalStrut(30));
        panelCentro.add(panelInferior);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);

        eventos();
    }

    /**
     * Crea un componente JLabel con un estilo de fuente y color predeterminado.
     *
     * @param texto El texto inicial que mostrará el label.
     * @return Un nuevo JLabel configurado para mostrar información de la
     * comanda.
     */
    private JLabel crearLabelInfo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(new Color(70, 70, 70));
        return label;
    }

    /**
     * Configura los escuchadores de acciones para la navegación de salida y
     * aceptación.
     */
    private void eventos() {
        btnSalir.addActionListener(e -> {
            coordinador.limpiarSesionComanda();
            coordinador.mostrarInicioSesionMesero();
            dispose();
        });

        btnAceptar.addActionListener(e -> {
            coordinador.limpiarSesionComanda();
            coordinador.mostrarMesas();
            dispose();
        });
    }

    /**
     * * Recupera la comanda actual del coordinador y puebla todos los campos de
     * texto y la tabla.
     * <p>
     * Realiza formateo de fechas y validación de nulidad para los datos del
     * cliente y mesa.</p>
     */
    private void cargarDatosComanda() {
        ComandaDTO comanda = coordinador.getComanda();

        if (comanda == null) {
            lblFolio.setText("Folio: -");
            lblMesa.setText("Mesa: -");
            lblFecha.setText("Fecha: -");
            lblEstado.setText("Estado: -");
            lblTotal.setText("Total: -");
            lblClienteAsociado.setText("Cliente asociado: -");
            return;
        }

        lblFolio.setText("Folio: " + comanda.getFolio());

        if (comanda.getMesa() != null) {
            lblMesa.setText("Mesa: " + comanda.getMesa().getNumero());
        } else {
            lblMesa.setText("Mesa: -");
        }

        if (comanda.getFecha() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            lblFecha.setText("Fecha: " + comanda.getFecha().format(formatter));
        } else {
            lblFecha.setText("Fecha: -");
        }

        lblEstado.setText("Estado: " + comanda.getEstado());

        lblTotal.setText("Total: $" + comanda.getTotal());

        if (comanda.getCliente() != null) {
            lblClienteAsociado.setText("Cliente asociado: " + comanda.getCliente().getNombreCompleto());
        } else {
            lblClienteAsociado.setText("Cliente asociado: Cliente general");
        }

        modeloTabla.setRowCount(0);

        List<DetallePedidoDTO> detalles = comanda.getDetalles();

        if (detalles != null) {
            for (DetallePedidoDTO detalle : detalles) {
                Object[] fila = {
                    detalle.getProductoDTO() != null ? detalle.getProductoDTO().getNombre() : "",
                    detalle.getCantidad(),
                    "$" + detalle.getSubtotal(),
                    detalle.getNota() != null ? detalle.getNota() : ""
                };
                modeloTabla.addRow(fila);
            }
        }
    }
}
