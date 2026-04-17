package pantallas;

import componentes.BotonEstilizado;
import componentes.BotonRegresar;
import componentes.BotonX;
import componentes.PanelRedondeado;
import controlador.Coordinador;
import dtos.IngredienteDTO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import utilerias.Validacion;

/**
 * Ventana para el ajuste de stock e información visual de un ingrediente.
 * <p>
 * Esta interfaz permite al administrador incrementar o disminuir la existencia
 * física de un ingrediente en el inventario, así como gestionar la imagen
 * representativa del insumo.</p>
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmAjustarStock extends JFrame {

    /**
     * Enlace con el coordinador para la ejecución de la lógica de negocio.
     */
    private final Coordinador coordinador;
    /**
     * Objeto de transferencia con los datos del ingrediente a modificar.
     */
    private final IngredienteDTO ingrediente;

    /**
     * Etiqueta que muestra el nombre del ingrediente seleccionado.
     */
    private JLabel lblNombre;
    /**
     * Etiqueta que muestra la cantidad de stock actual y su unidad de medida.
     */
    private JLabel lblStock;
    /**
     * Contenedor visual para la imagen del ingrediente.
     */
    private JLabel lblImagen;
    /**
     * Campo de texto para ingresar el valor numérico del ajuste.
     */
    private JTextField txtCantidad;
    /**
     * Opción para indicar que la cantidad ingresada se sumará al stock.
     */
    private JRadioButton rbAgregar;
    /**
     * Opción para indicar que la cantidad ingresada se restará del stock.
     */
    private JRadioButton rbDescontar;
    /**
     * Botón para confirmar y procesar el ajuste de inventario.
     */
    private BotonEstilizado btnGuardar;
    /**
     * Botón para cerrar la ventana y volver al listado de ingredientes.
     */
    private BotonRegresar btnRegresar;
    /**
     * Botón para eliminar la imagen personalizada y volver a la predeterminada.
     */
    private BotonX btnQuitarImagen;

    /**
     * Construye la ventana de ajuste de stock.
     * <p>
     * Inicializa el componente recuperando el ingrediente seleccionado
     * previamente en el coordinador y prepara la interfaz gráfica.</p>
     *
     * * @param coordinador El coordinador del sistema que provee el contexto
     * de datos.
     */
    public FrmAjustarStock(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.ingrediente = coordinador.getIngredienteSeleccionado();

        configurarVentana();
        inicializarComponentes();
        cargarDatos();
    }

    /**
     * Define las propiedades de visualización y comportamiento de la ventana
     * principal.
     */
    private void configurarVentana() {
        setTitle("Restaurante");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    /**
     * Crea, organiza y estiliza los elementos visuales que componen el
     * formulario.
     */
    private void inicializarComponentes() {

        Color colorMostaza = new Color(229, 171, 75);
        Color colorFondo = new Color(239, 239, 239);

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

        btnRegresar = new BotonRegresar();

        JPanel panelRegresar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRegresar.setBackground(colorFondo);
        panelRegresar.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));
        panelRegresar.add(btnRegresar);

        JPanel contenedorSuperior = new JPanel(new BorderLayout());
        contenedorSuperior.add(panelSuperior, BorderLayout.NORTH);
        contenedorSuperior.add(panelRegresar, BorderLayout.CENTER);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(colorFondo);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(30, 80, 40, 80));

        PanelRedondeado panelFormulario = new PanelRedondeado(30);
        panelFormulario.setLayout(new BorderLayout());
        panelFormulario.setBackground(new Color(235, 235, 235));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JLabel lblSubtitulo = new JLabel("Editar Ingrediente");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblSubtitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.X_AXIS));
        panelContenido.setOpaque(false);

        JPanel panelImagen = new JPanel();
        panelImagen.setLayout(new BoxLayout(panelImagen, BoxLayout.Y_AXIS));
        panelImagen.setOpaque(false);

        lblImagen = new JLabel();
        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setPreferredSize(new Dimension(200, 180));
        lblImagen.setMaximumSize(new Dimension(200, 180));
        lblImagen.setMinimumSize(new Dimension(200, 180));

        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setVerticalAlignment(SwingConstants.CENTER);

        lblImagen.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2, true));
        lblImagen.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelImagen.add(lblImagen);

        btnQuitarImagen = new BotonX("X");
        Dimension size = new Dimension(120, 35);
        btnQuitarImagen.setPreferredSize(size);
        btnQuitarImagen.setMaximumSize(size);
        btnQuitarImagen.setMinimumSize(size);

        btnQuitarImagen.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnQuitarImagen.setVisible(false);
        lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnQuitarImagen.addActionListener(e -> {
            ingrediente.setRutaImagen(null);
            ponerImagenDefault();
        });

        panelImagen.add(Box.createVerticalStrut(10));
        panelImagen.add(btnQuitarImagen);

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setOpaque(false);

        lblNombre = new JLabel("Ingrediente");
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblStock = new JLabel("Stock");
        lblStock.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        //lblStock.setForeground(new Color(90, 90, 90));
        lblStock.setAlignmentX(Component.LEFT_ALIGNMENT);

        rbAgregar = new JRadioButton("Agregar");
        rbDescontar = new JRadioButton("Descontar");

        rbAgregar.setOpaque(false);
        rbDescontar.setOpaque(false);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbAgregar);
        grupo.add(rbDescontar);
        rbAgregar.setSelected(true);

        rbAgregar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        rbDescontar.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JPanel panelRadios = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelRadios.setOpaque(false);
        panelRadios.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelRadios.add(rbAgregar);
        panelRadios.add(rbDescontar);

        JLabel lblCantidad = new JLabel("Cantidad");
        lblCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblCantidad.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtCantidad = new JTextField();
        txtCantidad.setMaximumSize(new Dimension(200, 40));
        txtCantidad.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        txtCantidad.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelInfo.add(lblNombre);
        panelInfo.add(Box.createVerticalStrut(10));
        panelInfo.add(lblStock);
        panelInfo.add(Box.createVerticalStrut(20));
        panelInfo.add(panelRadios);
        panelInfo.add(Box.createVerticalStrut(20));
        panelInfo.add(lblCantidad);
        panelInfo.add(Box.createVerticalStrut(5));
        panelInfo.add(txtCantidad);

        panelContenido.add(panelImagen);
        panelContenido.add(Box.createHorizontalStrut(40));
        panelContenido.add(panelInfo);

        btnGuardar = new BotonEstilizado("Guardar");

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setOpaque(false);
        panelBoton.add(btnGuardar);

        panelFormulario.add(lblSubtitulo, BorderLayout.NORTH);
        panelFormulario.add(panelContenido, BorderLayout.CENTER);
        panelFormulario.add(panelBoton, BorderLayout.SOUTH);

        panelCentro.add(panelFormulario, BorderLayout.CENTER);

        panelPrincipal.add(contenedorSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);

        ponerImagenDefault();

        eventos();
    }

    /**
     * Puebla las etiquetas de la interfaz con la información actual del
     * ingrediente.
     */
    private void cargarDatos() {
        if (ingrediente == null) {
            return;
        }

        lblNombre.setText("Ingrediente: " + ingrediente.getNombre());
        lblStock.setText("Stock: "
                + String.format("%.2f", ingrediente.getCantidadActual()) + " "
                + ingrediente.getUnidadMedida());

        cargarImagen();
    }

    /**
     * Intenta cargar la imagen desde la ruta almacenada o coloca la imagen por
     * defecto.
     */
    private void cargarImagen() {
        try {
            if (ingrediente.getRutaImagen() != null) {
                ImageIcon icono = new ImageIcon(ingrediente.getRutaImagen());
                Image imgEscalada = icono.getImage().getScaledInstance(
                        200,
                        180,
                        Image.SCALE_SMOOTH
                );
                lblImagen.setText("");
                lblImagen.setIcon(new ImageIcon(imgEscalada));

                btnQuitarImagen.setVisible(true);
            } else {
                ponerImagenDefault();
                btnQuitarImagen.setVisible(false);
            }
        } catch (Exception e) {
            ponerImagenDefault();
            btnQuitarImagen.setVisible(false);
        }
    }

    /**
     * Configura los escuchadores de acciones para botones y la interacción con
     * la imagen.
     */
    private void eventos() {
        btnGuardar.addActionListener(e -> guardar());

        btnRegresar.addActionListener(e -> {
            dispose();
            coordinador.mostrarIngredientes();
        });

        lblImagen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarImagen();
            }
        });
    }

    /**
     * Restablece el contenedor de imagen al icono de sistema "sin imagen".
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
        btnQuitarImagen.setVisible(false);
    }

    /**
     * Abre un selector de archivos para permitir al usuario elegir una nueva
     * imagen de disco.
     */
    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen");
        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                String ruta = fileChooser.getSelectedFile().getAbsolutePath();
                ImageIcon icono = new ImageIcon(ruta);
                Image imgEscalada = icono.getImage().getScaledInstance(
                        200,
                        180,
                        Image.SCALE_SMOOTH
                );

                lblImagen.setText("");
                lblImagen.setIcon(new ImageIcon(imgEscalada));

                ingrediente.setRutaImagen(ruta);

                btnQuitarImagen.setVisible(true);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar imagen");
            }
        }
    }

    /**
     * Valida la entrada del usuario y solicita al coordinador el ajuste del
     * inventario.
     * <p>
     * Verifica que la cantidad sea coherente con la unidad de medida y solicita
     * confirmación antes de realizar cambios en la persistencia.</p>
     */
    private void guardar() {
        String cantidadTxt = txtCantidad.getText();
        try {
            if (!Validacion.esCantidadValida(cantidadTxt, ingrediente.getUnidadMedida().toString())) {
                JOptionPane.showMessageDialog(this,
                        "Cantidad inválida para la unidad");
                return;
            }
            Double cantidad = Double.valueOf(cantidadTxt);
            boolean agregar = rbAgregar.isSelected();

            String accion = agregar ? "agregar" : "descontar";

            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que deseas " + accion + " " + cantidad + " "
                    + ingrediente.getUnidadMedida() + " de "
                    + ingrediente.getNombre() + "?",
                    "Confirmar acción",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            coordinador.ajustarStock(
                    ingrediente.getIdIngrediente(),
                    cantidad,
                    agregar
            );

            // dispose();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida");
        }
    }
}
