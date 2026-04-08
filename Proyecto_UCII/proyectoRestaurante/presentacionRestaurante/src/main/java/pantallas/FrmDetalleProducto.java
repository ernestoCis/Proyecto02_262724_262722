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
 * 
 * @author Paulina Guevara, Ernesto Cisneros 
 */
public class FrmDetalleProducto extends JFrame {

    private final Coordinador coordinador;
    private ProductoDTO producto;
    private JTable tablaIngredientes;
    private DefaultTableModel modeloTabla;
    private JLabel lblImagen;

    public FrmDetalleProducto(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.producto = coordinador.getProductoSeleccionado();

        configurarVentana();
        inicializarComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setTitle("Detalle Producto");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {

        Color fondo = Color.decode("#FFFFFF");
        Color primario = Color.decode("#CC514E");

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(fondo);
        panelPrincipal.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(primario);
        header.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel lblTitulo = new JLabel(producto.getNombre());
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));

        JButton btnCerrar = new JButton("✕");
        btnCerrar.setForeground(Color.WHITE);
        btnCerrar.setBackground(primario);
        btnCerrar.setBorder(null);
        btnCerrar.setFocusPainted(false);
        btnCerrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCerrar.addActionListener(e -> dispose());

        header.add(lblTitulo, BorderLayout.WEST);
        header.add(btnCerrar, BorderLayout.EAST);

        JPanel contenido = new JPanel();
        contenido.setBackground(fondo);
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelCentro = new JPanel();
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setOpaque(false);
        panelCentro.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblImagen = new JLabel("Sin imagen", SwingConstants.CENTER);
        lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblImagen.setPreferredSize(new Dimension(200, 150));
        lblImagen.setMaximumSize(new Dimension(200, 150));
        lblImagen.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));
        lblImagen.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        JLabel lblPrecio = new JLabel("$" + producto.getPrecio(), SwingConstants.CENTER);
        lblPrecio.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblPrecio.setForeground(new Color(76, 175, 80));
        lblPrecio.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCentro.add(lblImagen);
        panelCentro.add(Box.createVerticalStrut(15));
        panelCentro.add(lblPrecio);

        JLabel lblIngredientes = new JLabel("Ingredientes");
        lblIngredientes.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblIngredientes.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblIngredientes.setHorizontalAlignment(SwingConstants.CENTER);
        
        String[] columnas = {"Nombre", "Unidad", "Cantidad"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tablaIngredientes = new JTable(modeloTabla);
        tablaIngredientes.setRowHeight(28);
        tablaIngredientes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        // centrar encabezados
        ((DefaultTableCellRenderer) tablaIngredientes.getTableHeader()
                .getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);
        
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

    private void cargarDatos() {
        if (producto == null) {
            return;
        }

        try {
            if (producto.getRutaImagen() != null && !producto.getRutaImagen().isEmpty()) {
                ImageIcon icono = new ImageIcon(producto.getRutaImagen());
                Image img = icono.getImage().getScaledInstance(200, 150, Image.SCALE_SMOOTH);
                lblImagen.setText("");
                lblImagen.setIcon(new ImageIcon(img));
            } else {
                lblImagen.setIcon(null);
                lblImagen.setText("Sin imagen");
            }
        } catch (Exception e) {
            lblImagen.setIcon(null);
            lblImagen.setText("Sin imagen");
        }

        /*
        List<RecetaDTO> recetas = producto.getRecetas();

        for (RecetaDTO r : recetas) {
            modeloTabla.addRow(new Object[]{
                    r.getIngrediente().getNombre(),
                    r.getIngrediente().getUnidadMedida(),
                    r.getCantidad()
            });
        }
         */
    }
}
