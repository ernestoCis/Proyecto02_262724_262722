package pantallas;

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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class FrmEditarCliente extends JFrame {

    private final Coordinador coordinador;
    private final ClienteFrecuenteDTO clienteEditar;

    private JTextField txtNombre;
    private JTextField txtApellidoPaterno;
    private JTextField txtApellidoMaterno;
    private JTextField txtTelefono;
    private JTextField txtCorreo;

    private JButton btnRegresar;
    private JButton btnEditar;

    public FrmEditarCliente(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.clienteEditar = coordinador.getClienteSeleccionado();

        configurarVentana();
        inicializarComponentes();
        cargarDatosCliente();
    }

    private void configurarVentana() {
        setTitle("FrmEditarCliente");
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

        btnRegresar = new JButton("←");
        btnRegresar.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setBackground(colorMostaza);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegresar.setPreferredSize(new Dimension(40, 40));
        btnRegresar.setOpaque(true);
        btnRegresar.setContentAreaFilled(true);
        btnRegresar.setBorderPainted(false);
        btnRegresar.setBorder(BorderFactory.createEmptyBorder());
        btnRegresar.setMargin(new Insets(0, 0, 0, 0));
        btnRegresar.setHorizontalAlignment(SwingConstants.CENTER);
        btnRegresar.setVerticalAlignment(SwingConstants.CENTER);

        JPanel panelBotonRegresar = new JPanel();
        panelBotonRegresar.setOpaque(false);
        panelBotonRegresar.add(btnRegresar);

        panelArriba.add(panelBotonRegresar, BorderLayout.WEST);

        JPanel panelFormulario = new JPanel(new BorderLayout());
        panelFormulario.setBackground(colorPanel);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        JLabel lblSubtitulo = new JLabel("Editar Cliente");
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
        
        btnEditar = new JButton("Editar");
        btnEditar.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btnEditar.setFocusPainted(false);
        btnEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEditar.setBackground(new Color(229, 171, 75));
        btnEditar.setForeground(new Color(80, 80, 80));
        btnEditar.setPreferredSize(new Dimension(140, 38));

        JPanel panelBtnDerecha = new JPanel();
        panelBtnDerecha.setOpaque(false);
        panelBtnDerecha.add(btnEditar);

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

    private void eventos() {
        btnRegresar.addActionListener(e -> {
            dispose();
            coordinador.mostrarClientes();
        });
        btnEditar.addActionListener(e -> editarCliente());
    }

    private void cargarDatosCliente() {
        if (clienteEditar == null) {
            return;
        }
        
        txtNombre.setText(clienteEditar.getNombres() != null ? clienteEditar.getNombres() : "");
        txtApellidoPaterno.setText(clienteEditar.getApellidoPaterno() != null ? clienteEditar.getApellidoPaterno() : "");
        txtApellidoMaterno.setText(clienteEditar.getApellidoMaterno() != null ? clienteEditar.getApellidoMaterno() : "");
        txtTelefono.setText(clienteEditar.getTelefono() != null ? clienteEditar.getTelefono() : "");
        txtCorreo.setText(clienteEditar.getEmail() != null ? clienteEditar.getEmail() : "");
    }

    private void editarCliente() {
        String nombre = txtNombre.getText().trim();
        String apellidoPaterno = txtApellidoPaterno.getText().trim();
        String apellidoMaterno = txtApellidoMaterno.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String correo = txtCorreo.getText().trim();

        if (nombre.isEmpty() || apellidoPaterno.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completa al menos nombre, apellido paterno y teléfono.");
            return;
        }


        ClienteFrecuenteDTO clienteActualizado = new ClienteFrecuenteDTO(
                clienteEditar.getIdCliente(),
                nombre,
                apellidoPaterno,
                apellidoMaterno,
                telefono,
                correo,
                clienteEditar.getNumeroVisitas(),
                clienteEditar.getTotalGastado(),
                clienteEditar.getPuntos()
        );

        coordinador.editarCliente(clienteActualizado);

        dispose();
    }
}