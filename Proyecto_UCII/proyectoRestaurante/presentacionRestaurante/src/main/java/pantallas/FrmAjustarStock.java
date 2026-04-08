package pantallas;

import componentes.BotonEstilizado;
import componentes.BotonRegresar;
import controlador.Coordinador;
import dtos.IngredienteDTO;
import java.awt.*;
import javax.swing.*;

/**
 * 
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmAjustarStock extends JFrame {

    private final Coordinador coordinador;
    private final IngredienteDTO ingrediente;

    private JLabel lblNombre;
    private JLabel lblStock;
    private JLabel lblImagen;

    private JTextField txtCantidad;
    private JRadioButton rbAgregar;
    private JRadioButton rbDescontar;

    private BotonEstilizado btnGuardar;
    private BotonRegresar btnRegresar;

    public FrmAjustarStock(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.ingrediente = coordinador.getIngredienteSeleccionado();

        configurarVentana();
        inicializarComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setTitle("Ajustar Stock");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {

        Color colorMostaza = new Color(229,171,75);
        Color colorFondo = new Color(239,239,239);
        Color colorPanel = new Color(235,235,235);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);

        // HEADER
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(colorMostaza);
        panelSuperior.setPreferredSize(new Dimension(900,80));

        JLabel lblTituloHeader = new JLabel("Ingredientes", SwingConstants.CENTER);
        lblTituloHeader.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTituloHeader.setForeground(new Color(52,58,70));

        panelSuperior.add(lblTituloHeader, BorderLayout.CENTER);

        btnRegresar = new BotonRegresar();

        JPanel panelRegresar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRegresar.setBackground(colorFondo);
        panelRegresar.setBorder(BorderFactory.createEmptyBorder(10,20,0,0));
        panelRegresar.add(btnRegresar);

        JPanel contenedorSuperior = new JPanel(new BorderLayout());
        contenedorSuperior.add(panelSuperior, BorderLayout.NORTH);
        contenedorSuperior.add(panelRegresar, BorderLayout.CENTER);

        // PANEL CENTRO
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(colorFondo);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10,30,20,30));

        JPanel panelFormulario = new JPanel(new BorderLayout());
        panelFormulario.setBackground(colorPanel);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(25,35,25,35));

        JLabel lblSubtitulo = new JLabel("Ajustar Stock");
        lblSubtitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblSubtitulo.setBorder(BorderFactory.createEmptyBorder(0,0,20,0));

        // CONTENIDO
        JPanel panelContenido = new JPanel(new GridBagLayout());
        panelContenido.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.WEST;

        // IMAGEN
        lblImagen = new JLabel("Sin imagen", SwingConstants.CENTER);
        lblImagen.setPreferredSize(new Dimension(160,160));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 4;
        panelContenido.add(lblImagen, gbc);

        // NOMBRE
        lblNombre = new JLabel("Ingrediente");
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 18));

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        panelContenido.add(lblNombre, gbc);

        // STOCK
        lblStock = new JLabel("Stock");
        lblStock.setFont(new Font("SansSerif", Font.BOLD, 16));
        //lblStock.setForeground(new Color(229,171,75));

        gbc.gridy = 1;
        panelContenido.add(lblStock, gbc);

        // RADIOS
        rbAgregar = new JRadioButton("Agregar");
        rbDescontar = new JRadioButton("Descontar");

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbAgregar);
        grupo.add(rbDescontar);

        rbAgregar.setSelected(true);

        JPanel panelRadios = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRadios.setOpaque(false);
        panelRadios.add(rbAgregar);
        panelRadios.add(rbDescontar);

        gbc.gridy = 2;
        panelContenido.add(panelRadios, gbc);

        // CANTIDAD
        JPanel panelCantidad = new JPanel(new BorderLayout(0,5));
        panelCantidad.setOpaque(false);

        JLabel lblCantidad = new JLabel("Cantidad");
        lblCantidad.setFont(new Font("SansSerif", Font.BOLD, 15));

        txtCantidad = new JTextField();
        txtCantidad.setPreferredSize(new Dimension(300,35));

        panelCantidad.add(lblCantidad, BorderLayout.NORTH);
        panelCantidad.add(txtCantidad, BorderLayout.CENTER);

        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panelContenido.add(panelCantidad, gbc);

        // BOTON
        btnGuardar = new BotonEstilizado("Guardar");
        btnGuardar.setPreferredSize(new Dimension(150,40));

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setOpaque(false);
        panelBoton.add(btnGuardar);

        panelFormulario.add(lblSubtitulo, BorderLayout.NORTH);
        panelFormulario.add(panelContenido, BorderLayout.CENTER);
        panelFormulario.add(panelBoton, BorderLayout.SOUTH);

        panelCentro.add(panelFormulario);

        panelPrincipal.add(contenedorSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);

        eventos();
    }

    private void cargarDatos() {
        if (ingrediente == null) return;

        lblNombre.setText("Ingrediente: " + ingrediente.getNombre());
        lblStock.setText("Stock actual: "
                + ingrediente.getCantidadActual()
                + " "
                + ingrediente.getUnidadMedida());

        cargarImagen();
    }

    private void cargarImagen() {
        try {
            if (ingrediente.getRutaImagen() != null) {
                ImageIcon icono = new ImageIcon(ingrediente.getRutaImagen());
                Image img = icono.getImage()
                        .getScaledInstance(150,150, Image.SCALE_SMOOTH);

                lblImagen.setText("");
                lblImagen.setIcon(new ImageIcon(img));
            }
        } catch (Exception e) {
        }
    }

    private void eventos() {

        btnGuardar.addActionListener(e -> guardar());

        btnRegresar.addActionListener(e -> {
            dispose();
            coordinador.mostrarIngredientes();
        });
    }

    private void guardar() {
        try {
            double cantidad = Double.parseDouble(txtCantidad.getText());
            boolean agregar = rbAgregar.isSelected();

            coordinador.ajustarStock(
                    ingrediente.getIdIngrediente(),
                    cantidad,
                    agregar
            );

            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida");
        }
    }
}