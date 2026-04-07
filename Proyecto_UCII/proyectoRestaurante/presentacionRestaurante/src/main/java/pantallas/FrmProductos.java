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
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmProductos extends JFrame {

    private final Coordinador coordinador;

    private JTable tblProductos;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;

    private BotonRegresar btnRegresar;
    private BotonEstilizado btnRegistrar;
    private BotonEstilizado btnEliminar;

    private List<ProductoDTO> listaOriginal;

    public FrmProductos(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.listaOriginal = this.coordinador.getListaProductosActual();

        configurarVentana();
        inicializarComponentes();
        cargarDatosTabla(this.listaOriginal);
    }

    private void configurarVentana() {
        setTitle("Restaurante");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

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

        JLabel lblTitulo = new JLabel("Productos");
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

        txtBuscar = new JTextField(30);
        txtBuscar.setPreferredSize(new Dimension(420, 35));
        txtBuscar.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        txtBuscar.setToolTipText("Buscar productos");

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

        JScrollPane scrollPane = new JScrollPane(tblProductos);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(colorFondo);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);

        btnRegistrar = new BotonEstilizado("+ Registrar");
        btnEliminar = new BotonEstilizado("Eliminar");
        btnEliminar.setEnabled(false);

        panelBotones.add(btnRegistrar);
        panelBotones.add(Box.createHorizontalStrut(15));
        panelBotones.add(btnEliminar);

        panelInferior.add(Box.createVerticalStrut(8));
        panelInferior.add(panelBotones);

        panelCentro.add(panelBusqueda, BorderLayout.NORTH);
        panelCentro.add(scrollPane, BorderLayout.CENTER);
        panelCentro.add(panelInferior, BorderLayout.SOUTH);

        panelPrincipal.add(panelSuperiorContenedor, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);

        registrarEventos();
    }

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
                        ProductoDTO producto = listaOriginal.get(fila);
                        coordinador.setProductoSeleccionado(producto);
                        // coordinador.mostrarEditarProducto();
                    }
                }
            }
        });

        btnRegistrar.addActionListener(e -> {
            dispose();
           // coordinador.mostrarRegistrarProducto();
        });

        btnRegresar.addActionListener(e -> {
            dispose();
            coordinador.mostrarAcciones();
        });

        btnEliminar.addActionListener(e -> eliminarProductoSeleccionado());
    }

    private void cargarDatosTabla(List<ProductoDTO> lista) {
        modeloTabla.setRowCount(0);

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

    private void accionBuscar() {
        String texto = txtBuscar.getText().trim().toLowerCase();

        if (texto.isEmpty()) {
            cargarDatosTabla(listaOriginal);
            return;
        }

        List<ProductoDTO> filtrados = new ArrayList<>();

        for (ProductoDTO p : listaOriginal) {
            String nombre = p.getNombre() != null ? p.getNombre().toLowerCase() : "";

            if (nombre.contains(texto)) {
                filtrados.add(p);
            }
        }

        cargarDatosTabla(filtrados);
    }

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

    public void actualizarTablaProductos(List<ProductoDTO> productos) {
        this.listaOriginal = productos;
        cargarDatosTabla(productos);
    }

    private void eliminarProductoSeleccionado() {
        int fila = tblProductos.getSelectedRow();

        if (fila == -1) return;

        ProductoDTO producto = listaOriginal.get(fila);

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar el producto " + producto.getNombre() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            coordinador.setProductoSeleccionado(producto);
            // coordinador.eliminarProducto();
        }
    }
}