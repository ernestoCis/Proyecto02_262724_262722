package pantallas;

import componentes.BotonColor;
import controlador.Coordinador;
import dtos.IngredienteDTO;
import java.util.List;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class DlgAgregarIngrediente extends JDialog {

    private Coordinador coordinador;

    private JTextField txtBuscar;
    private JTextField txtCantidad;
    private JTable tablaIngredientes;
    private DefaultTableModel modeloTabla;

    private BotonColor btnCancelar;
    private BotonColor btnAgregar;

    private JFrame parent;
    
    private List<IngredienteDTO> ingredientes;

    public DlgAgregarIngrediente(JFrame parent, Coordinador coordinador) {
        super(parent, true);
        this.parent = parent;
        this.coordinador = coordinador;
        configurarVentana();
        inicializarComponentes();
        cargarIngredientes();
    }

    private void configurarVentana() {
        setTitle("Ingredientes");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {

        Color fondo = new Color(245, 245, 245);
        Color headerTabla = new Color(180, 195, 210);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(fondo);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // HEADER
        JPanel panelTop = new JPanel(new BorderLayout());
        panelTop.setOpaque(false);

        JLabel lblTitulo = new JLabel("Ingredientes");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));

        JButton btnCerrar = new JButton("X");
        btnCerrar.setFocusPainted(false);
        btnCerrar.setBorderPainted(false);
        btnCerrar.setContentAreaFilled(false);
        btnCerrar.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelTop.add(lblTitulo, BorderLayout.WEST);
        panelTop.add(btnCerrar, BorderLayout.EAST);

        // BUSCADOR
        JPanel panelBusqueda = new JPanel(new BorderLayout());
        panelBusqueda.setOpaque(false);
        panelBusqueda.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        txtBuscar = new JTextField("   Buscar ingrediente");
        txtBuscar.setPreferredSize(new Dimension(200, 35));

        panelBusqueda.add(txtBuscar, BorderLayout.CENTER);

        // TABLA
        modeloTabla = new DefaultTableModel(
                new String[]{"Nombre", "Unidad", "Cantidad Actual"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaIngredientes = new JTable(modeloTabla);
        tablaIngredientes.setRowHeight(30);

        ((DefaultTableCellRenderer) tablaIngredientes.getTableHeader()
                .getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        JScrollPane scroll = new JScrollPane(tablaIngredientes);

        // DATOS DEMO
        modeloTabla.addRow(new Object[]{"Pan", "Piezas", 62});
        modeloTabla.addRow(new Object[]{"Queso", "Piezas", 16});
        modeloTabla.addRow(new Object[]{"Mayonesa", "Gramos", 10});
        modeloTabla.addRow(new Object[]{"Jamón", "Piezas", 23});

        JPanel panelBottom = new JPanel(new BorderLayout());
        panelBottom.setOpaque(false);
        panelBottom.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        // cantidad
        JPanel panelCantidad = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCantidad.setOpaque(false);

        JLabel lblCantidad = new JLabel("Cantidad:");
        txtCantidad = new JTextField("1");
        txtCantidad.setPreferredSize(new Dimension(120, 30));

        panelCantidad.add(lblCantidad);
        panelCantidad.add(txtCantidad);

        // botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        panelBotones.setOpaque(false);

        btnCancelar = new BotonColor("Cancelar");
        btnAgregar = new BotonColor("Agregar");

        panelBotones.add(btnCancelar);
        panelBotones.add(btnAgregar);

        panelBottom.add(panelCantidad, BorderLayout.WEST);
        panelBottom.add(panelBotones, BorderLayout.EAST);

        panelPrincipal.add(panelTop, BorderLayout.NORTH);
        panelPrincipal.add(panelBusqueda, BorderLayout.BEFORE_FIRST_LINE);
        panelPrincipal.add(scroll, BorderLayout.CENTER);
        panelPrincipal.add(panelBottom, BorderLayout.SOUTH);

        add(panelPrincipal);

        eventos(btnCerrar);
    }

    private void eventos(JButton btnCerrar) {

        btnCerrar.addActionListener(e -> dispose());

        btnCancelar.addActionListener(e -> dispose());

        btnAgregar.addActionListener(e -> {

            int fila = tablaIngredientes.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Selecciona un ingrediente");
                return;
            }

            String nombre = modeloTabla.getValueAt(fila, 0).toString();
            String unidad = modeloTabla.getValueAt(fila, 1).toString();
            String cantidad = txtCantidad.getText().trim();

            if (cantidad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa una cantidad");
                return;
            }

            if (!utilerias.Validacion.esCantidadValida(cantidad, unidad)) {

                if (unidad.equalsIgnoreCase("Piezas")) {
                    JOptionPane.showMessageDialog(this,
                            "Solo se permiten números enteros para piezas");
                } else {
                    JOptionPane.showMessageDialog(this,
                            "Cantidad inválida");
                }
                return;
            }

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Deseas agregar:\n\n"
                    + nombre + " - " + cantidad + " " + unidad + "?",
                    "Confirmar ingrediente",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {

                IngredienteDTO ingrediente = ingredientes.get(fila);
                
                if (parent instanceof FrmRegistrarProducto) {
                    ((FrmRegistrarProducto) parent).agregarIngredienteATabla(ingrediente, Double.valueOf(cantidad));
                } else if (parent instanceof FrmEditarProducto) {
                    ((FrmEditarProducto) parent).agregarIngredienteATabla(ingrediente, Double.valueOf(cantidad));
                }

                JOptionPane.showMessageDialog(this, "Ingrediente agregado correctamente");
                dispose();

            }
        });
    }

    private void cargarIngredientes() {
        modeloTabla.setRowCount(0);

        ingredientes = coordinador.obtenerIngredientes();

        if (ingredientes != null) {
            for (IngredienteDTO ing : ingredientes) {
                modeloTabla.addRow(new Object[]{
                    ing.getNombre(),
                    ing.getUnidadMedida(),
                    ing.getCantidadActual()
                });
            }
        }
    }
}
