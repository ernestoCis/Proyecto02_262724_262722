package pantallas;

import componentes.BotonEstilizado;
import componentes.BotonRegresar;
import componentes.BotonX;
import componentes.PanelRedondeado;
import controlador.Coordinador;
import dtos.IngredienteDTO;
import enums.UnidadMedidaDTO;
import java.awt.*;
import javax.swing.*;
import utilerias.Validacion;

/**
 * Pantalla para el registro de nuevos ingredientes en el inventario del
 * restaurante.
 * <p>
 * Permite capturar el nombre, la unidad de medida, la cantidad inicial y
 * asociar una imagen representativa al ingrediente.</p>
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmRegistrarIngrediente extends JFrame {

    /**
     * Controlador principal para la gestion de flujos y persistencia.
     */
    private final Coordinador coordinador;

    /**
     * Campo para el nombre comercial del ingrediente.
     */
    private JTextField txtNombre;
    /**
     * Campo para la existencia fisica inicial.
     */
    private JTextField txtCantidad;
    /**
     * Selector de unidades (Piezas, Gramos, Mililitros).
     */
    private JComboBox<String> comboUnidad;

    /**
     * Boton de navegacion.
     */
    private BotonRegresar btnRegresar;
    /**
     * Boton de accion.
     */
    private BotonEstilizado btnRegistrar;

    /**
     * Components para la previsualizacion de archivos de imagen.
     */
    private JLabel lblImagen;
    /**
     * Componentes para la previsualizacion y seleccion de archivos de imagen.
     */
    private BotonEstilizado btnSeleccionarImagen;
    /**
     * Componentes para la seleccion de archivos de imagen.
     */
    private BotonX btnQuitarImagen;

    /**
     * Almacena la direccion absoluta de la imagen seleccionada en disco.
     */
    private String rutaImagen;

    /**
     * Constructor que inicializa la vista de registro de ingredientes.
     *
     * @param coordinador El coordinador general del sistema.
     */
    public FrmRegistrarIngrediente(Coordinador coordinador) {
        this.coordinador = coordinador;
        configurarVentana();
        inicializarComponentes();
    }

    /**
     * Configura las propiedades basicas de la ventana como tamaño y titulo.
     */
    private void configurarVentana() {
        setTitle("Ingredientes");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    /**
     * Inicializa los componentes graficos, colores y distribucion del
     * formulario.
     * <p>
     * Organiza los campos de texto a la izquierda y el area de imagen a la
     * derecha.</p>
     */
    private void inicializarComponentes() {

        Color colorMostaza = new Color(229, 171, 75);
        Color colorFondo = new Color(239, 239, 239);
        Color colorPanel = new Color(235, 235, 235);
        Color colorTexto = new Color(55, 55, 55);

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
        panelCampos.setPreferredSize(new Dimension(300, 250)); // 🔥 ancho fijo

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
        panelImagen.setPreferredSize(new Dimension(220, 250));

        lblImagen = new JLabel("Sin imagen", SwingConstants.CENTER);
        lblImagen.setPreferredSize(new Dimension(180, 150));
        lblImagen.setMaximumSize(new Dimension(180, 150));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnSeleccionarImagen = new BotonEstilizado("Imagen");
        btnQuitarImagen = new BotonX("X");
        btnQuitarImagen.setVisible(false);

        JPanel panelBotonesImagen = new JPanel();
        panelBotonesImagen.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelBotonesImagen.setOpaque(false);

        btnSeleccionarImagen.setPreferredSize(new Dimension(120, 35));
        btnQuitarImagen.setPreferredSize(new Dimension(60, 35));

        panelBotonesImagen.add(btnSeleccionarImagen);
        panelBotonesImagen.add(btnQuitarImagen);

        lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBotonesImagen.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelImagen.add(lblImagen);
        panelImagen.add(Box.createVerticalStrut(10));
        panelImagen.add(panelBotonesImagen);

        panelContenido.add(panelCampos);
        panelContenido.add(Box.createHorizontalStrut(60));
        panelContenido.add(panelImagen);

        btnQuitarImagen.addActionListener(e -> quitarImagen());

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

        ponerImagenDefault();

        eventos();
    }

    /**
     * Elimina la imagen seleccionada y restablece el icono por defecto.
     */
    private void quitarImagen() {
        rutaImagen = null;
        ponerImagenDefault();
        btnQuitarImagen.setVisible(false);
    }

    /**
     * Carga el icono representativo de "sin imagen" en el contenedor visual.
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
     * Crea un panel contenedor con una etiqueta y un campo de texto alineados.
     *
     * @param texto Descripcion del campo.
     * @param campo Componente de entrada de texto asociado.
     * @return <code>JPanel</code> configurado con BoxLayout.
     */
    private JPanel crearCampo(String texto, JTextField campo) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));

        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

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

    /**
     * Crea un panel contenedor con una etiqueta y un selector alineados.
     *
     * @param texto Descripcion del combo.
     * @param combo Componente JComboBox asociado.
     * @return <code>JPanel</code> configurado con BoxLayout.
     */
    private JPanel crearCombo(String texto, JComboBox combo) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));

        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));

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

    /**
     * Registra los escuchas de accion para botones y controles.
     */
    private void eventos() {
        btnRegresar.addActionListener(e -> {
            dispose();
            coordinador.mostrarIngredientes();
        });

        btnRegistrar.addActionListener(e -> registrarIngrediente());

        btnSeleccionarImagen.addActionListener(e -> seleccionarImagen());
    }

    /**
     * Abre un selector de archivos (JFileChooser) para que el usuario elija una
     * imagen.
     * <p>
     * Escala la imagen seleccionada para ajustarla al tamaño del JLabel.</p>
     */
    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen");

        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            java.io.File archivo = fileChooser.getSelectedFile();
            rutaImagen = archivo.getAbsolutePath();

            ImageIcon icono = new ImageIcon(rutaImagen);

            Dimension size = lblImagen.getPreferredSize();

            Image imagenEscalada = icono.getImage().getScaledInstance(
                    size.width,
                    size.height,
                    Image.SCALE_SMOOTH
            );

            lblImagen.setText("");
            lblImagen.setIcon(new ImageIcon(imagenEscalada));
            btnQuitarImagen.setVisible(true);
        }
    }

    /**
     * Valida los datos del formulario y solicita al coordinador el registro del
     * ingrediente.
     * <p>
     * Verifica que el nombre no este vacio y que la cantidad sea congruente con
     * la unidad de medida mediante la clase <code>Validacion</code>.</p>
     */
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

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Deseas agregar el ingrediente " + nombre + "?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
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
}
