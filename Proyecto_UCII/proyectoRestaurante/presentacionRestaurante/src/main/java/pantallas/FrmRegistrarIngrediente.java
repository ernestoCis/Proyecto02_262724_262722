package pantallas;

import componentes.BotonEstilizado;
import componentes.BotonRegresar;
import componentes.PanelRedondeado;
import controlador.Coordinador;
import dtos.IngredienteDTO;
import enums.UnidadMedidaDTO;
import java.awt.*;
import javax.swing.*;
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
        setSize(1000, 650);
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

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(colorMostaza);
        panelSuperior.setPreferredSize(new Dimension(1000, 110));

        JLabel lblLogo = new JLabel();
        lblLogo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));

        ImageIcon iconoLogo = new ImageIcon("src\\main\\resources\\imagenes\\icono_restaurante.png");
        Image logoEscalado = iconoLogo.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(logoEscalado));

        JLabel lblTitulo = new JLabel("Ingredientes");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(52, 58, 70));

        JPanel panelTitulo = new JPanel(new GridBagLayout());
        panelTitulo.setOpaque(false);
        panelTitulo.add(lblTitulo);

        panelSuperior.add(lblLogo, BorderLayout.WEST);
        panelSuperior.add(panelTitulo, BorderLayout.CENTER);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(colorFondo);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 35, 30, 35));

        btnRegresar = new BotonRegresar();

        JPanel panelArriba = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelArriba.setOpaque(false);
        panelArriba.add(btnRegresar);

        PanelRedondeado panelFormulario = new PanelRedondeado(30);
        panelFormulario.setLayout(new BorderLayout());
        panelFormulario.setBackground(colorPanel);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel lblSubtitulo = new JLabel("Registrar Ingrediente");
        lblSubtitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblSubtitulo.setForeground(colorTexto);
        lblSubtitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.X_AXIS));
        panelContenido.setOpaque(false);

        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));
        panelCampos.setOpaque(false);

        txtNombre = new JTextField();
        txtCantidad = new JTextField();

        comboUnidad = new JComboBox<>(new String[]{
            "PIEZAS", "GRAMOS", "MILILITROS"
        });

        panelCampos.add(crearCampo("Nombre", txtNombre));
        panelCampos.add(Box.createVerticalStrut(15));
        panelCampos.add(crearCombo("Unidad de medida", comboUnidad));
        panelCampos.add(Box.createVerticalStrut(15));
        panelCampos.add(crearCampo("Cantidad actual en inventario", txtCantidad));

        JPanel panelImagen = new JPanel();
        panelImagen.setLayout(new BoxLayout(panelImagen, BoxLayout.Y_AXIS));
        panelImagen.setOpaque(false);

        lblImagen = new JLabel("Sin imagen", SwingConstants.CENTER);
        lblImagen.setPreferredSize(new Dimension(210, 220));
        lblImagen.setMaximumSize(new Dimension(210, 220));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));

        btnSeleccionarImagen = new BotonEstilizado("Seleccionar imagen");

        panelImagen.add(lblImagen);
        panelImagen.add(Box.createVerticalStrut(10));
        panelImagen.add(btnSeleccionarImagen);

        panelContenido.add(panelCampos);
        panelContenido.add(Box.createHorizontalStrut(60));
        panelContenido.add(panelImagen);

        btnRegistrar = new BotonEstilizado("Registrar");

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setOpaque(false);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        panelBoton.add(btnRegistrar);

        panelFormulario.add(lblSubtitulo, BorderLayout.NORTH);
        panelFormulario.add(panelContenido, BorderLayout.CENTER);
        panelFormulario.add(panelBoton, BorderLayout.SOUTH);

        panelCentro.add(panelArriba, BorderLayout.NORTH);
        panelCentro.add(panelFormulario, BorderLayout.CENTER);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);

        eventos();
    }

    private JPanel crearCampo(String texto, JTextField campo) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));

        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setOpaque(false);

        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);

        contenedor.add(label);
        contenedor.add(Box.createVerticalStrut(5));
        contenedor.add(campo);

        return contenedor;
    }

    private JPanel crearCombo(String texto, JComboBox combo) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));

        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setOpaque(false);

        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);

        contenedor.add(label);
        contenedor.add(Box.createVerticalStrut(5));
        contenedor.add(combo);

        return contenedor;
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
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen");

        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            java.io.File archivo = fileChooser.getSelectedFile();
            rutaImagen = archivo.getAbsolutePath();

            ImageIcon icono = new ImageIcon(rutaImagen);

            Image imagenEscalada = icono.getImage().getScaledInstance(
                    lblImagen.getWidth(),
                    lblImagen.getHeight(),
                    Image.SCALE_SMOOTH
            );

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
                nombre,
                unidad,
                cantidad,
                rutaImagen
        );

        coordinador.registrarIngrediente(ingrediente);
    }
}