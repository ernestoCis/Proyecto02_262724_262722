package pantallas;

import controlador.Coordinador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmInicioSesionMesero extends JFrame {

    private JButton btnRegresar;
    private JButton btnAceptar;
    private JTextField txtUsuario;
    private Coordinador coordinador;

    public FrmInicioSesionMesero(Coordinador coordinador) {
        this.coordinador = coordinador;
        configurarVentana();
        inicializarComponentes();
    }

    private void configurarVentana() {
        setTitle("Restaurante");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {
        Color colorMostaza = new Color(229, 171, 75);
        Color colorRojo = new Color(216, 84, 78);
        Color colorFondo = new Color(239, 239, 239);
        Color colorTexto = new Color(55, 55, 55);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);

        JPanel panelSuperiorContenedor = new JPanel(new BorderLayout());
        panelSuperiorContenedor.setPreferredSize(new Dimension(900, 170));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(colorMostaza);
        panelSuperior.setPreferredSize(new Dimension(900, 105));

        JLabel lblLogo = new JLabel();
        lblLogo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
        lblLogo.setPreferredSize(new Dimension(90, 90));

        ImageIcon iconoLogo = new ImageIcon("src\\main\\resources\\imagenes\\icono_restaurante.png");
        Image logoEscalado = iconoLogo.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(logoEscalado));

        JPanel panelTitulo = new JPanel(new GridBagLayout());
        panelTitulo.setOpaque(false);

        JLabel lblTitulo = new JLabel("Restaurante");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 34));
        lblTitulo.setForeground(new Color(52, 58, 70));

        panelTitulo.add(lblTitulo);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setOpaque(false);
        panelDerecho.setPreferredSize(new Dimension(90, 90));

        panelSuperior.add(lblLogo, BorderLayout.WEST);
        panelSuperior.add(panelTitulo, BorderLayout.CENTER);
        panelSuperior.add(panelDerecho, BorderLayout.EAST);

        JPanel panelFranjaRoja = new JPanel(new BorderLayout());
        panelFranjaRoja.setBackground(colorRojo);
        panelFranjaRoja.setPreferredSize(new Dimension(900, 65));

        btnRegresar = new JButton("←");
        btnRegresar.setFont(new Font("Dialog", Font.BOLD, 20));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setBackground(colorMostaza);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegresar.setPreferredSize(new Dimension(45, 40));
        btnRegresar.setOpaque(true);
        btnRegresar.setContentAreaFilled(true);
        btnRegresar.setBorderPainted(false);
        btnRegresar.setBorder(BorderFactory.createEmptyBorder());

        JPanel panelBotonRegresar = new JPanel();
        panelBotonRegresar.setOpaque(false);
        panelBotonRegresar.setBorder(BorderFactory.createEmptyBorder(12, 15, 10, 0));
        panelBotonRegresar.add(btnRegresar);

        panelFranjaRoja.add(panelBotonRegresar, BorderLayout.WEST);

        panelSuperiorContenedor.add(panelSuperior, BorderLayout.CENTER);
        panelSuperiorContenedor.add(panelFranjaRoja, BorderLayout.SOUTH);

        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(colorFondo);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

        JLabel lblMesero = new JLabel("Mesero");
        lblMesero.setFont(new Font("SansSerif", Font.BOLD, 30));
        lblMesero.setForeground(colorTexto);
        lblMesero.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblIconoUsuario = new JLabel();
        lblIconoUsuario.setAlignmentX(CENTER_ALIGNMENT);
        lblIconoUsuario.setPreferredSize(new Dimension(120, 120));
        lblIconoUsuario.setMaximumSize(new Dimension(120, 120));

        ImageIcon iconoUsuario = new ImageIcon("src\\main\\resources\\imagenes\\icono_usuario.png");
        Image usuarioEscalado = iconoUsuario.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
        lblIconoUsuario.setIcon(new ImageIcon(usuarioEscalado));

        JPanel panelUsuario = new JPanel();
        panelUsuario.setOpaque(false);

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblUsuario.setForeground(Color.BLACK);

        txtUsuario = new JTextField();
        txtUsuario.setPreferredSize(new Dimension(180, 32));
        txtUsuario.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtUsuario.setBorder(BorderFactory.createLineBorder(colorMostaza, 1));

        panelUsuario.add(lblUsuario);
        panelUsuario.add(Box.createHorizontalStrut(15));
        panelUsuario.add(txtUsuario);

        btnAceptar = new JButton("Aceptar");
        btnAceptar.setFont(new Font("SansSerif", Font.PLAIN, 20));
        btnAceptar.setFocusPainted(false);
        btnAceptar.setBackground(Color.WHITE);
        btnAceptar.setForeground(Color.BLACK);
        btnAceptar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAceptar.setPreferredSize(new Dimension(320, 70));
        btnAceptar.setMaximumSize(new Dimension(320, 70));
        btnAceptar.setAlignmentX(CENTER_ALIGNMENT);
        btnAceptar.setBorder(BorderFactory.createLineBorder(colorMostaza, 1));

        panelCentro.add(Box.createVerticalStrut(10));
        panelCentro.add(lblMesero);
        panelCentro.add(Box.createVerticalStrut(10));
        panelCentro.add(lblIconoUsuario);
        panelCentro.add(Box.createVerticalStrut(25));
        panelCentro.add(panelUsuario);
        panelCentro.add(Box.createVerticalStrut(80));
        panelCentro.add(btnAceptar);
        panelCentro.add(Box.createVerticalStrut(40));

        panelPrincipal.add(panelSuperiorContenedor, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);

        eventos();
    }

    private void eventos() {
        btnRegresar.addActionListener(e -> {
            dispose();
            coordinador.iniciarSistema();
        });

        btnAceptar.addActionListener(e -> {
            coordinador.mostrarMesas(txtUsuario.getText().trim());
        });
    }
}