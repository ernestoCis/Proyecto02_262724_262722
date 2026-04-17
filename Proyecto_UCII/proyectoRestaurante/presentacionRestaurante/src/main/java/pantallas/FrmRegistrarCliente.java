package pantallas;

import componentes.BotonEstilizado;
import componentes.BotonRegresar;
import componentes.PanelRedondeado;
import controlador.Coordinador;
import dtos.ClienteFrecuenteDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import utilerias.Validacion;

/**
 * Pantalla para el registro de nuevos clientes frecuentes en el sistema.
 * <p>
 * Proporciona un formulario validado para capturar datos personales y de
 * contacto.</p>
 *
 * @author Paulina Guevera, Ernesto Cisneros
 */
public class FrmRegistrarCliente extends JFrame {

    /**
     * Referencia al controlador principal para la comunicacion con la logica.
     */
    private final Coordinador coordinador;

    /**
     * Referencia a la pantalla de clientes para actualizaciones.
     */
    private FrmClientes frmClientes;
    /**
     * Lista local de clientes para validaciones de ID o duplicados.
     */
    private List<ClienteFrecuenteDTO> listaClientes;

    /**
     * Campo de texto para la captura de informacion del cliente.
     */
    private JTextField txtNombre;
    /**
     * Campo de texto para la captura de informacion del cliente.
     */
    private JTextField txtApellidoPaterno;
    /**
     * Campo de texto para la captura de informacion del cliente.
     */
    private JTextField txtApellidoMaterno;
    /**
     * Campo de texto para la captura de informacion del cliente.
     */
    private JTextField txtTelefono;
    /**
     * Campo de texto para la captura de informacion del cliente.
     */
    private JTextField txtCorreo;

    /**
     * Boton para el control de navegacion.
     */
    private JButton btnRegresar;
    /**
     * Boton para el control de confirmacion.
     */
    private JButton btnRegistrar;

    /**
     * Constructor que inicializa el formulario de registro.
     *
     * @param coordinador El coordinador general del sistema.
     */
    public FrmRegistrarCliente(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.listaClientes = coordinador.getListaClientesActual();

        configurarVentana();
        inicializarComponentes();
    }

    /**
     * Define las propiedades visuales y de cierre del frame.
     */
    private void configurarVentana() {
        setTitle("Restaurante");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    /**
     * Crea y organiza los componentes del formulario.
     * <p>
     * Utiliza un <code>PanelRedondeado</code> para agrupar los campos de
     * entrada.</p>
     */
    private void inicializarComponentes() {
        Color colorMostaza = new Color(229, 171, 75);
        Color colorFondo = new Color(239, 239, 239);
        Color colorPanel = new Color(235, 235, 235);
        Color colorTexto = new Color(55, 55, 55);
        Color colorBoton = new Color(70, 70, 70);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);

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

        JLabel lblTitulo = new JLabel("Clientes");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(52, 58, 70));

        panelTitulo.add(lblTitulo);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setOpaque(false);
        panelDerecho.setPreferredSize(new Dimension(100, 90));

        panelSuperior.add(lblLogo, BorderLayout.WEST);
        panelSuperior.add(panelTitulo, BorderLayout.CENTER);
        panelSuperior.add(panelDerecho, BorderLayout.EAST);

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

        PanelRedondeado panelFormulario = new PanelRedondeado(30);
        panelFormulario.setLayout(new BorderLayout());
        panelFormulario.setBackground(colorPanel);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        JLabel lblSubtitulo = new JLabel("Registrar Cliente");
        lblSubtitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblSubtitulo.setForeground(colorTexto);
        lblSubtitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

        JPanel panelCampos = new JPanel(new GridBagLayout());
        panelCampos.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        txtNombre = new JTextField();
        txtApellidoPaterno = new JTextField();
        txtApellidoMaterno = new JTextField();
        txtTelefono = new JTextField();
        txtCorreo = new JTextField();

        agregarCampo(panelCampos, gbc, 0, 0, "Nombre", txtNombre);
        agregarCampo(panelCampos, gbc, 1, 0, "Teléfono", txtTelefono);
        agregarCampo(panelCampos, gbc, 0, 1, "Apellido Paterno", txtApellidoPaterno);
        agregarCampo(panelCampos, gbc, 1, 1, "Correo Electrónico", txtCorreo);
        agregarCampo(panelCampos, gbc, 0, 2, "Apellido Materno", txtApellidoMaterno);

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

    /**
     * Metodo auxiliar para agregar campos de texto etiquetados al panel.
     *
     * @param panel El panel destino (GridBagLayout).
     * @param gbc Las restricciones de diseño.
     * @param x Posicion en columna.
     * @param y Posicion en fila.
     * @param textoLabel Texto descriptivo del campo.
     * @param textField El componente de entrada de texto.
     */
    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int x, int y, String textoLabel, JTextField textField) {
        JLabel label = new JLabel(textoLabel);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));
        label.setForeground(new Color(55, 55, 55));

        textField.setPreferredSize(new Dimension(320, 36));
        textField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        JPanel contenedor = new JPanel(new BorderLayout(0, 8));
        contenedor.setOpaque(false);
        contenedor.add(label, BorderLayout.NORTH);
        contenedor.add(textField, BorderLayout.CENTER);

        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(contenedor, gbc);
    }

    /**
     * Asigna los escuchas de eventos a los botones.
     */
    private void eventos() {
        btnRegresar.addActionListener(e -> {
            dispose();
            coordinador.mostrarClientes();
        });

        btnRegistrar.addActionListener(e -> registrarCliente());
    }

    /**
     * Procesa el registro del cliente previa validacion de los campos.
     * <p>
     * Utiliza la clase <code>Validacion</code> para asegurar la integridad de
     * los datos.</p>
     */
    private void registrarCliente() {
        String nombre = Validacion.limpiarCadena(txtNombre.getText());
        String apellidoPaterno = Validacion.limpiarCadena(txtApellidoPaterno.getText());
        String apellidoMaterno = Validacion.limpiarCadena(txtApellidoMaterno.getText());
        String telefono = Validacion.limpiarCadena(txtTelefono.getText());
        String correo = Validacion.limpiarCadena(txtCorreo.getText());

        if (!Validacion.esNombreValido(nombre)) {
            JOptionPane.showMessageDialog(null, "Nombre inválido");
            return;
        }

        if (!Validacion.esApellidoPaternoValido(apellidoPaterno)) {
            JOptionPane.showMessageDialog(null, "Apellido paterno inválido");
            return;
        }

        if (!Validacion.esApellidoMaternoValido(apellidoMaterno)) {
            JOptionPane.showMessageDialog(null, "Apellido materno inválido");
            return;
        }

        if (!Validacion.esTelefonoValido(telefono)) {
            JOptionPane.showMessageDialog(null, "Telefono inválido");
            return;
        }

        if (!Validacion.esEmailValido(correo)) {
            JOptionPane.showMessageDialog(null, "Email inválido");
            return;
        }

        ClienteFrecuenteDTO nuevoCliente = new ClienteFrecuenteDTO(
                null,
                nombre,
                apellidoPaterno,
                apellidoMaterno,
                telefono,
                correo,
                0,
                0.0,
                0
        );

        listaClientes.add(nuevoCliente);

        coordinador.registrarCliente(nuevoCliente);

        dispose();
    }

    /**
     * Calcula el siguiente ID disponible basado en la lista actual de clientes.
     *
     * @return Un nuevo ID de tipo <code>Long</code> incrementado.
     */
    private Long generarNuevoId() {
        Long mayor = 0L;

        for (ClienteFrecuenteDTO cliente : listaClientes) {
            if (cliente.getIdCliente() != null && cliente.getIdCliente() > mayor) {
                mayor = cliente.getIdCliente();
            }
        }

        return mayor + 1;
    }
}
