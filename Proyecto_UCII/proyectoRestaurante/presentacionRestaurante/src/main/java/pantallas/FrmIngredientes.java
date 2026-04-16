package pantallas;

import componentes.BotonEstilizado;
import componentes.BotonRegresar;
import controlador.Coordinador;
import dtos.IngredienteDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmIngredientes extends JFrame {

    private boolean modoSeleccion = false;
    private JLabel lblNota;

    private SeleccionIngredienteListener listener;

    public interface SeleccionIngredienteListener {

        boolean onIngredientesSeleccionados(List<IngredienteDTO> ingredientes);
    }

    private final Coordinador coordinador;
    private JTable tblIngredientes;
    private DefaultTableModel modeloTabla;
    private JTextField txtBuscar;
    private BotonRegresar btnRegresar;
    private BotonEstilizado btnRegistrar;
    private BotonEstilizado btnEliminar;
    private List<IngredienteDTO> listaOriginal;

    public FrmIngredientes(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.listaOriginal = this.coordinador.obtenerIngredientes();
        configurarVentana();
        inicializarComponentes();
        cargarDatosTabla(this.listaOriginal);
    }

    public FrmIngredientes(Coordinador coordinador, boolean modoSeleccion, SeleccionIngredienteListener listener) {
        this.coordinador = coordinador;
        this.modoSeleccion = modoSeleccion;
        this.listener = listener;
        this.listaOriginal = this.coordinador.obtenerIngredientes();
        configurarVentana();
        inicializarComponentes();
        cargarDatosTabla(this.listaOriginal);
    }

    private void configurarVentana() {
        setTitle("Restaurante");
        setSize(1000, 650);
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

        JLabel lblTitulo = new JLabel("Ingredientes");
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
            "Nombre", "Unidad de medida", "Cantidad actual"
        };

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblIngredientes = new JTable(modeloTabla);
        tblIngredientes.setRowHeight(40);
        tblIngredientes.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tblIngredientes.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tblIngredientes.getTableHeader().setBackground(colorTablaHeader);
        tblIngredientes.getTableHeader().setForeground(Color.BLACK);
        tblIngredientes.setSelectionBackground(new Color(220, 230, 220));
        tblIngredientes.setSelectionForeground(Color.BLACK);

        JScrollPane scrollPane = new JScrollPane(tblIngredientes);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        ((DefaultTableCellRenderer) tblIngredientes.getTableHeader()
                .getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(colorFondo);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);

        btnRegistrar = new BotonEstilizado("+ Registrar");
        btnEliminar = new BotonEstilizado("Eliminar");

        lblNota = new JLabel();
        lblNota.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lblNota.setForeground(new Color(90, 90, 90));
        lblNota.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));

        if (modoSeleccion) {
            lblNota.setText("Da doble click para ingresar cantidad de ingrediente y añadirlo al producto");
        } else {
            lblNota.setText("Da doble click para ajustar stock");
        }

        lblNota.setForeground(new Color(120, 120, 120));
        lblNota.setHorizontalAlignment(SwingConstants.CENTER);

        if (modoSeleccion) {
            btnRegistrar.setVisible(false);
            btnEliminar.setVisible(false);
            // btnRegresar.setVisible(false);
        }

        panelBotones.add(btnRegistrar);
        panelBotones.add(Box.createHorizontalStrut(15));
        panelBotones.add(btnEliminar);

        panelInferior.add(Box.createVerticalStrut(8));
        panelInferior.add(panelBotones);

        panelCentro.add(panelBusqueda, BorderLayout.NORTH);
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setOpaque(false);

        panelTabla.add(lblNota, BorderLayout.NORTH);
        panelTabla.add(scrollPane, BorderLayout.CENTER);

        panelCentro.add(panelTabla, BorderLayout.CENTER);
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

        tblIngredientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tblIngredientes.getSelectedRow();
                    if (fila != -1) {
                        IngredienteDTO ingredienteSeleccionado = listaOriginal.get(fila);
                        if (modoSeleccion) {
                            if (listener != null) {
                                List<IngredienteDTO> lista = new ArrayList<>();
                                lista.add(ingredienteSeleccionado);

                                boolean agregado = listener.onIngredientesSeleccionados(lista);

                                if (agregado) {
                                    dispose();
                                }
                            }
                        } else {
                            coordinador.setIngredienteSeleccionado(ingredienteSeleccionado);
                            coordinador.mostrarAjustarStock();
                        }
                    }
                }
            }
        }
        );

        btnRegistrar.addActionListener(e
                -> {
            dispose();
            coordinador.mostrarRegistrarIngrediente();
        }
        );

        btnRegresar.addActionListener(e -> {
            dispose();
            if (modoSeleccion) {
                coordinador.volverDeBusquedaIngredientes();
            } else {
                coordinador.mostrarAcciones();
            }
        });

        btnEliminar.addActionListener(e
                -> eliminarIngredienteSeleccionado());
    }

    private void cargarDatosTabla(List<IngredienteDTO> lista) {
        modeloTabla.setRowCount(0);

        if (lista != null) {
            for (IngredienteDTO ingrediente : lista) {
                Object[] fila = {
                    ingrediente.getNombre(),
                    ingrediente.getUnidadMedida(),
                    ingrediente.getCantidadActual()
                };
                modeloTabla.addRow(fila);
            }
        }
    }

    private void accionBuscar() {
        String texto = txtBuscar.getText().trim();

        if (texto.isEmpty()) {
            cargarDatosTabla(coordinador.obtenerIngredientes());
            return;
        }

        List<IngredienteDTO> filtrados = coordinador.buscarIngredientes(texto);
        cargarDatosTabla(filtrados);
    }

    private void ponerPlaceholder() {
        txtBuscar.setText("Buscar ingrediente");
        txtBuscar.setForeground(Color.GRAY);

        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (txtBuscar.getText().equals("Buscar ingrediente")) {
                    txtBuscar.setText("");
                    txtBuscar.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (txtBuscar.getText().trim().isEmpty()) {
                    txtBuscar.setText("Buscar ingrediente");
                    txtBuscar.setForeground(Color.GRAY);
                }
            }
        });
    }

    public void actualizarTablaIngredientes(List<IngredienteDTO> ingredientes) {
        this.listaOriginal = ingredientes;
        cargarDatosTabla(this.listaOriginal);
    }

    private void eliminarIngredienteSeleccionado() {
        int fila = tblIngredientes.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un ingrediente");
            return;
        }

        IngredienteDTO ingrediente = listaOriginal.get(fila);

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar el ingrediente " + ingrediente.getNombre() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            coordinador.setIngredienteSeleccionado(ingrediente);
            coordinador.eliminarIngrediente();
        }
    }
}
