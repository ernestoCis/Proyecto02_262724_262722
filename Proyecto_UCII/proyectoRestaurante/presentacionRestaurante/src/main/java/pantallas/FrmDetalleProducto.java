package pantallas;

import controlador.Coordinador;
import dtos.ProductoDTO;
import dtos.RecetaDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Ventana de visualización detallada para un producto específico del menú.
 * <p>
 * Muestra la información general del producto (imagen, precio, descripción) y
 * el desglose de su receta, listando los ingredientes necesarios junto con sus
 * cantidades y unidades de medida.</p>
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmDetalleProducto extends JFrame {

    /**
     * Enlace con el coordinador para la gestión de navegación y flujo de datos.
     */
    private final Coordinador coordinador;
    /**
     * Objeto de transferencia que contiene la información del producto a
     * visualizar.
     */
    private ProductoDTO producto;
    /**
     * Tabla que despliega la lista de ingredientes asociados al producto.
     */
    private JTable tablaIngredientes;
    /**
     * Modelo de datos para gestionar el contenido de la tabla de ingredientes.
     */
    private DefaultTableModel modeloTabla;
    /**
     * Etiqueta que actúa como contenedor para mostrar la imagen del producto.
     */
    private JLabel lblImagen;

    /**
     * Construye la ventana de detalle del producto.
     * <p>
     * Recupera el producto seleccionado actualmente desde el coordinador e
     * inicializa la carga de datos visuales y la tabla de recetas.</p>
     *
     * * @param coordinador El coordinador del sistema que provee el contexto.
     */
    public FrmDetalleProducto(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.producto = coordinador.getProductoSeleccionado();
        configurarVentana();
        inicializarComponentes();
        cargarDatos();
    }

    /**
     * Define las dimensiones, posición y estilo decorativo del marco principal.
     */
    private void configurarVentana() {
        setTitle("Detalle Producto");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());
    }

    /**
     * * Construye la estructura visual de la ventana, incluyendo el encabezado,
     * el área de descripción y la configuración de la tabla de ingredientes.
     */
    private void inicializarComponentes() {

        Color fondo = Color.decode("#FFFFFF");
        Color primario = Color.decode("#CC514E");

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(fondo);
        panelPrincipal.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        // HEADER
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(primario);
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel lblTitulo = new JLabel(producto.getNombre());
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JButton btnCerrar = new JButton("X");
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setBackground(primario);
        btnCerrar.setBorder(null);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btnCerrar.addActionListener(e -> dispose());

        JButton btnEditar = new JButton();
        ImageIcon iconoEditar = new ImageIcon("src/main/resources/imagenes/icono_editar.png");
        Image img = iconoEditar.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        btnEditar.setIcon(new ImageIcon(img));
        btnEditar.setBackground(primario);
        btnEditar.setBorder(null);
        btnEditar.setFocusPainted(false);
        btnEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnEditar.addActionListener(e -> {
            dispose();
            coordinador.mostrarEditarProducto();
        });

        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        panelDerecho.setOpaque(false);
        panelDerecho.add(btnEditar);
        panelDerecho.add(Box.createHorizontalStrut(8));
        panelDerecho.add(btnCerrar);

        header.add(lblTitulo, BorderLayout.WEST);
        header.add(panelDerecho, BorderLayout.EAST);

        // CONTENIDO
        JPanel contenido = new JPanel();
        contenido.setBackground(fondo);
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setOpaque(false);
        panelCentro.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblImagen.setPreferredSize(new Dimension(200, 150));
        lblImagen.setMaximumSize(new Dimension(200, 150));
        lblImagen.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        lblImagen.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel lblPrecio = new JLabel("$" + producto.getPrecio(), SwingConstants.CENTER);
        lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblPrecio.setForeground(new Color(76, 175, 80));
        lblPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea txtDescripcion = new JTextArea(producto.getDescripcion());
        txtDescripcion.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtDescripcion.setForeground(new Color(90, 90, 90));
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setEditable(false);
        txtDescripcion.setOpaque(false);
        txtDescripcion.setBorder(null);

        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        scrollDescripcion.setPreferredSize(new Dimension(340, 80));
        scrollDescripcion.setMaximumSize(new Dimension(340, 80));
        scrollDescripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        scrollDescripcion.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollDescripcion.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollDescripcion.setBorder(BorderFactory.createEmptyBorder());
        scrollDescripcion.getViewport().setOpaque(false);

        panelCentro.add(lblImagen);
        panelCentro.add(Box.createVerticalStrut(15));
        panelCentro.add(lblPrecio);
        panelCentro.add(Box.createVerticalStrut(8));
        panelCentro.add(scrollDescripcion);

        JLabel lblIngredientes = new JLabel("Ingredientes");
        lblIngredientes.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblIngredientes.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblIngredientes.setHorizontalAlignment(SwingConstants.CENTER);

        String[] columnas = {"Nombre", "Unidad", "Cantidad"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            /**
             * Desactiva la edición directa de las celdas en la tabla de
             * detalle.
             *
             * @param row Índice de la fila.
             * @param col Índice de la columna.
             * @return false para mantener la tabla en modo solo lectura.
             */
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tablaIngredientes = new JTable(modeloTabla);
        tablaIngredientes.setRowHeight(28);
        tablaIngredientes.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        ((DefaultTableCellRenderer) tablaIngredientes.getTableHeader()
                .getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        tablaIngredientes.setSelectionBackground(Color.decode("#2A4E52"));

        JScrollPane scroll = new JScrollPane(tablaIngredientes);
        scroll.setPreferredSize(new Dimension(400, 200));

        contenido.add(panelCentro);
        contenido.add(Box.createVerticalStrut(30));
        contenido.add(lblIngredientes);
        contenido.add(Box.createVerticalStrut(10));
        contenido.add(scroll);

        panelPrincipal.add(header, BorderLayout.NORTH);
        panelPrincipal.add(contenido, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    /**
     * Carga el icono de sistema predeterminado para productos que carecen de
     * imagen.
     */
    private void ponerImagenDefault() {
        ImageIcon icono = new ImageIcon("src\\main\\resources\\imagenes\\icono_sin_imagen.png");

        Dimension size = lblImagen.getPreferredSize();

        Image img = icono.getImage().getScaledInstance(
                size.width,
                size.height,
                Image.SCALE_SMOOTH
        );

        lblImagen.setIcon(new ImageIcon(img));
        lblImagen.setText("");
    }

    /**
     * * Puebla la interfaz con la información del producto.
     * <p>
     * Carga la imagen desde la ruta especificada (si existe) y llena el modelo
     * de la tabla con los ingredientes definidos en la receta.</p>
     */
    private void cargarDatos() {

        if (producto == null) {
            return;
        }

        try {
            if (producto.getRutaImagen() != null && !producto.getRutaImagen().isEmpty()) {

                ImageIcon icono = new ImageIcon(producto.getRutaImagen());

                Image img = icono.getImage().getScaledInstance(
                        lblImagen.getPreferredSize().width,
                        lblImagen.getPreferredSize().height,
                        Image.SCALE_SMOOTH
                );

                lblImagen.setText("");
                lblImagen.setIcon(new ImageIcon(img));

            } else {
                ponerImagenDefault();
            }

        } catch (Exception e) {
            ponerImagenDefault();
        }

        List<RecetaDTO> recetas = producto.getRecetas();

        for (RecetaDTO r : recetas) {
            modeloTabla.addRow(new Object[]{
                r.getIngrediente().getNombre(),
                r.getIngrediente().getUnidadMedida(),
                r.getCantidad()
            });
        }
    }
}
