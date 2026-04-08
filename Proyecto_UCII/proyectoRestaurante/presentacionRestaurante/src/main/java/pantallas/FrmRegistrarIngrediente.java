package pantallas;

import componentes.BotonEstilizado;
import componentes.BotonRegresar;
import controlador.Coordinador;
import dtos.IngredienteDTO;
import enums.UnidadMedidaDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import utilerias.Validacion;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmRegistrarIngrediente extends JFrame {

    private final Coordinador coordinador;

    private JTextField txtNombre;
    private JTextField txtCantidad;
    private JComboBox<String> comboUnidad;

    private BotonRegresar btnRegresar;
    private BotonEstilizado btnRegistrar;

    private JLabel lblImagen;
    private BotonEstilizado btnSeleccionarImagen;
    private String rutaImagen;

    public FrmRegistrarIngrediente(Coordinador coordinador) {
        this.coordinador = coordinador;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Ingredientes");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {

        Color colorMostaza = new Color(229, 171, 75);
        Color colorFondo = new Color(239, 239, 239);
        Color colorPanel = new Color(235, 235, 235);
        Color colorTexto = new Color(55, 55, 55);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);

        // PANEL SUPERIOR
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(colorMostaza);
        panelSuperior.setPreferredSize(new Dimension(1000, 110));

        JLabel lblLogo = new JLabel();
        lblLogo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        lblLogo.setPreferredSize(new Dimension(100, 90));

        ImageIcon iconoLogo = new ImageIcon("src\\main\\resources\\imagenes\\icono_restaurante.png");
        Image logoEscalado = iconoLogo.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(logoEscalado));

        JPanel panelTitulo = new JPanel(new GridBagLayout());
        panelTitulo.setOpaque(false);

        JLabel lblTitulo = new JLabel("Ingredientes");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(52, 58, 70));
        panelTitulo.add(lblTitulo);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setOpaque(false);
        panelDerecho.setPreferredSize(new Dimension(100, 90));

        panelSuperior.add(lblLogo, BorderLayout.WEST);
        panelSuperior.add(panelTitulo, BorderLayout.CENTER);
        panelSuperior.add(panelDerecho, BorderLayout.EAST);

        // PANEL CENTRO
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(colorFondo);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 35, 30, 35));

        JPanel panelArriba = new JPanel(new BorderLayout());
        panelArriba.setOpaque(false);

        btnRegresar = new BotonRegresar();

        JPanel panelBotonRegresar = new JPanel();
        panelBotonRegresar.setOpaque(false);
        panelBotonRegresar.add(btnRegresar);

        panelArriba.add(panelBotonRegresar, BorderLayout.WEST);

        // PANEL FORMULARIO
        JPanel panelFormulario = new JPanel(new BorderLayout());
        panelFormulario.setBackground(colorPanel);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        JLabel lblSubtitulo = new JLabel("Registrar Ingrediente");
        lblSubtitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblSubtitulo.setForeground(colorTexto);
        lblSubtitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        txtNombre = new JTextField();
        txtCantidad = new JTextField();
        comboUnidad = new JComboBox<>(new String[]{
            "PIEZAS",
            "GRAMOS",
            "MILILITROS",});

        agregarCampo(panelCampos, gbc, 0, 0, "Nombre", txtNombre);
        agregarCombo(panelCampos, gbc, 0, 1, "Unidad de medida", comboUnidad);
        agregarCampo(panelCampos, gbc, 0, 2, "Cantidad actual en inventario", txtCantidad);

        lblImagen = new JLabel("Sin imagen", JLabel.CENTER);
        lblImagen.setPreferredSize(new Dimension(250, 250));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        btnSeleccionarImagen = new BotonEstilizado("Seleccionar imagen");

        JPanel panelImagen = new JPanel(new BorderLayout(10, 10));
        panelImagen.setOpaque(false);
        panelImagen.add(lblImagen, BorderLayout.CENTER);
        panelImagen.add(btnSeleccionarImagen, BorderLayout.SOUTH);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 3;
        gbc.weighty = 1.0;              // permite crecer verticalmente
        gbc.fill = GridBagConstraints.BOTH; // ocupa todo el espacio
        gbc.anchor = GridBagConstraints.NORTH;
        panelCampos.add(panelImagen, gbc);

        gbc.gridheight = 1;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // BOTON REGISTRAR
        JPanel panelBotonInferior = new JPanel(new BorderLayout());
        panelBotonInferior.setOpaque(false);
        panelBotonInferior.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        btnRegistrar = new BotonEstilizado("Registrar");

        JPanel panelBtnDerecha = new JPanel();
        panelBtnDerecha.setOpaque(false);
        panelBtnDerecha.add(btnRegistrar);

        panelBotonInferior.add(panelBtnDerecha, BorderLayout.EAST);

        panelFormulario.add(lblSubtitulo, BorderLayout.NORTH);
        panelFormulario.add(panelCampos, BorderLayout.CENTER);
        panelFormulario.add(panelBotonInferior, BorderLayout.SOUTH);

        panelCentro.add(panelArriba, BorderLayout.NORTH);
        panelCentro.add(panelFormulario, BorderLayout.CENTER);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);

        eventos();
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int x, int y, String textoLabel, JTextField textField) {
        JLabel label = new JLabel(textoLabel);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));

        textField.setPreferredSize(new Dimension(400, 42)); 

        JPanel contenedor = new JPanel(new BorderLayout(0, 8));
        contenedor.setOpaque(false);
        contenedor.add(label, BorderLayout.NORTH);
        contenedor.add(textField, BorderLayout.CENTER);

        gbc.gridx = x;
        gbc.gridy = y;
        gbc.weightx = 1.0;  // ocupa espacio horizontal
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel.add(contenedor, gbc);
    }

    private void agregarCombo(JPanel panel, GridBagConstraints gbc, int x, int y, String textoLabel, JComboBox combo) {

        JLabel label = new JLabel(textoLabel);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));

        combo.setPreferredSize(new Dimension(420, 42));

        JPanel contenedor = new JPanel(new BorderLayout(0, 8));
        contenedor.setOpaque(false);
        contenedor.add(label, BorderLayout.NORTH);
        contenedor.add(combo, BorderLayout.CENTER);

        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(contenedor, gbc);
    }

    private void eventos() {

        btnRegresar.addActionListener(e -> {
            dispose();
            coordinador.mostrarIngredientes();
        });

        btnRegistrar.addActionListener(e -> registrarIngrediente());

        btnSeleccionarImagen.addActionListener(e -> seleccionarImagen());

    }

    private void seleccionarImagen() {
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen");

        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File archivo = fileChooser.getSelectedFile();
            rutaImagen = archivo.getAbsolutePath();

            ImageIcon icono = new ImageIcon(rutaImagen);
            Image imagenEscalada = icono.getImage().getScaledInstance(
                    150, 150, Image.SCALE_SMOOTH);

            lblImagen.setText("");
            lblImagen.setIcon(new ImageIcon(imagenEscalada));
        }
    }

    private void registrarIngrediente() {

        String nombre = txtNombre.getText().trim();
        String cantidadTexto = txtCantidad.getText().trim();
        String unidadTexto = comboUnidad.getSelectedItem().toString();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre inválido");
            return;
        }

        if (!Validacion.esCantidadValida(cantidadTexto, unidadTexto)) {
            JOptionPane.showMessageDialog(this,
                    "Cantidad inválida para la unidad seleccionada");
            return;
        }

        double cantidad = Double.parseDouble(cantidadTexto);

        UnidadMedidaDTO unidad = UnidadMedidaDTO.valueOf(unidadTexto);

        IngredienteDTO ingrediente = new IngredienteDTO(
                null,
                nombre,
                unidad,
                cantidad,
                rutaImagen
        );

        coordinador.registrarIngrediente(ingrediente);
        dispose();
    }
}
