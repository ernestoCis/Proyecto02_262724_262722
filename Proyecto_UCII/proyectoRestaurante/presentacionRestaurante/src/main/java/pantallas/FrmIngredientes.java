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
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmIngredientes extends JFrame {

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
        this.listaOriginal = this.coordinador.getListaIngredientesActual();
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

        txtBuscar = new JTextField(30);
        txtBuscar.setPreferredSize(new Dimension(420, 35));
        txtBuscar.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtBuscar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));

        txtBuscar.setToolTipText("Buscar ingredientes");
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

        tblIngredientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                btnEliminar.setEnabled(tblIngredientes.getSelectedRow() != -1);
            }
        });

        JScrollPane scrollPane = new JScrollPane(tblIngredientes);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(colorFondo);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));
        panelInferior.setLayout(new BoxLayout(panelInferior, BoxLayout.Y_AXIS));

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);

        btnRegistrar = new BotonEstilizado("+ Registrar");
//        btnRegistrar.setFont(new Font("SansSerif", Font.PLAIN, 18));
//        btnRegistrar.setFocusPainted(false);
//        btnRegistrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        btnRegistrar.setBackground(Color.WHITE);
//        btnRegistrar.setForeground(new Color(80, 80, 80));
//        btnRegistrar.setPreferredSize(new Dimension(190, 38));

        btnEliminar = new BotonEstilizado("Eliminar");
//        btnEliminar.setFont(new Font("SansSerif", Font.PLAIN, 18));
//        btnEliminar.setFocusPainted(false);
//        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        btnEliminar.setBackground(Color.WHITE);
//        btnEliminar.setForeground(new Color(80, 80, 80));
//        btnEliminar.setPreferredSize(new Dimension(190, 38));
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

        tblIngredientes.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int fila = tblIngredientes.getSelectedRow();
                    if (fila != -1) {
                        IngredienteDTO ingrediente = listaOriginal.get(fila);
                        coordinador.setIngredienteSeleccionado(ingrediente);
                    //    coordinador.mostrarEditarIngrediente();
                    }
                }
            }
        });

        btnRegistrar.addActionListener(e -> {
            dispose();
          //  coordinador.mostrarRegistrarIngrediente();
        });

        btnRegresar.addActionListener(e -> {
            dispose();
            coordinador.mostrarAcciones();
        });

        btnEliminar.addActionListener(e -> eliminarIngredienteSeleccionado());
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
        String texto = txtBuscar.getText().trim().toLowerCase();

        if (texto.isEmpty()) {
            cargarDatosTabla(listaOriginal);
            return;
        }

        List<IngredienteDTO> filtrados = new ArrayList<>();

        for (IngredienteDTO ingrediente : listaOriginal) {
            String nombre = ingrediente.getNombre() != null ? ingrediente.getNombre().toLowerCase() : "";

            if (nombre.contains(texto)) {
                filtrados.add(ingrediente);
            }
        }

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
        if (fila == -1) return;

        IngredienteDTO ingrediente = listaOriginal.get(fila);

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Seguro que deseas eliminar el ingrediente " + ingrediente.getNombre() + "?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            coordinador.setIngredienteSeleccionado(ingrediente);
         //   coordinador.eliminarIngrediente();
        }
    }
}