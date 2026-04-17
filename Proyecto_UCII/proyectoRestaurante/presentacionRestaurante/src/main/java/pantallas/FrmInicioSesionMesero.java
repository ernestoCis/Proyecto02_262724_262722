package pantallas;

import componentes.BotonRegresar;
import controlador.Coordinador;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
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
 * Pantalla de inicio de sesion para el personal de servicio (meseros).
 * <p>
 * Esta ventana permite validar el acceso del mesero mediante su nombre de
 * usuario.</p>
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmInicioSesionMesero extends JFrame {

    /**
     * Boton para volver a la pantalla de inicio del sistema.
     */
    private JButton btnRegresar;
    /**
     * Boton para confirmar el intento de inicio de sesion.
     */
    private JButton btnAceptar;
    /**
     * Campo de entrada de texto para el nombre del mesero.
     */
    private JTextField txtUsuario;
    /**
     * Controlador principal para la gestion de flujos y datos.
     */
    private Coordinador coordinador;

    /**
     * Constructor que inicializa la vista de inicio de sesion.
     *
     * @param coordinador El coordinador general del sistema.
     */
    public FrmInicioSesionMesero(Coordinador coordinador) {
        this.coordinador = coordinador;
        configurarVentana();
        inicializarComponentes();
    }

    /**
     * Establece las propiedades basicas del marco de la ventana.
     */
    private void configurarVentana() {
        setTitle("Restaurante");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    /**
     * Inicializa los componentes visuales y organiza el diseño de la pantalla.
     * <p>
     * Configura el panel superior con el logo y el formulario de acceso
     * central.</p>
     */
    private void inicializarComponentes() {
        Color colorMostaza = new Color(229, 171, 75);
        Color colorRojo = new Color(216, 84, 78);
        Color colorFondo = new Color(239, 239, 239);
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

        JLabel lblTitulo = new JLabel("Mesero");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(52, 58, 70));

        panelTitulo.add(lblTitulo);

        panelSuperior.add(lblLogo, BorderLayout.WEST);
        panelSuperior.add(panelTitulo, BorderLayout.CENTER);
        panelSuperiorContenedor.add(panelSuperior, BorderLayout.CENTER);

        btnRegresar = new BotonRegresar();

        JPanel panelRegresar = new JPanel(null);
        panelRegresar.setOpaque(false);
        panelRegresar.setPreferredSize(new Dimension(1000, 60));

        btnRegresar.setBounds(20, 20, 40, 40);
        panelRegresar.add(btnRegresar);

        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(colorFondo);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.add(panelRegresar, BorderLayout.NORTH);

//        JLabel lblMesero = new JLabel("Mesero");
//        lblMesero.setFont(new Font("SansSerif", Font.BOLD, 30));
//        lblMesero.setForeground(colorTexto);
//        lblMesero.setAlignmentX(CENTER_ALIGNMENT);
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

        btnAceptar = crearBoton("Aceptar");

        panelCentro.add(Box.createVerticalStrut(10));
//        panelCentro.add(lblMesero);
//        panelCentro.add(Box.createVerticalStrut(10));
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

    /**
     * Asigna las acciones a los botones de la interfaz.
     * <p>
     * El boton aceptar valida que el campo de usuario no este vacio y consulta
     * la existencia del mesero en el sistema.</p>
     */
    private void eventos() {
        btnRegresar.addActionListener(e -> {
            dispose();
            coordinador.iniciarSistema();
        });

        btnAceptar.addActionListener(e -> {

            if (txtUsuario.getText().trim() == null || txtUsuario.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Ingrese un usuario");
                return;
            }

            if (coordinador.buscarMeseroPorUsuario(txtUsuario.getText().trim()) != null) {
                coordinador.mostrarMesas();
                dispose();
            }
        });
    }

    /**
     * Metodo de fabrica para crear botones con un estilo redondeado y efectos
     * de hover.
     *
     * @param texto El texto que se mostrara dentro del boton.
     * @return Un objeto <code>JButton</code> con diseño personalizado.
     */
    private JButton crearBoton(String texto) {

        JButton boton = new JButton(texto) {

            private boolean hover = false;
            private final Color colorNormal = Color.WHITE;
            private final Color colorHover = new Color(255, 245, 235);
            private final Color colorBorde = new Color(235, 155, 94);

            {
                setContentAreaFilled(false);
                setBorderPainted(false);
                setFocusPainted(false);
                setOpaque(false);

                addMouseListener(new java.awt.event.MouseAdapter() {
                    @Override
                    public void mouseEntered(java.awt.event.MouseEvent e) {
                        hover = true;
                        repaint();
                    }

                    @Override
                    public void mouseExited(java.awt.event.MouseEvent e) {
                        hover = false;
                        repaint();
                    }
                });
            }

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(hover ? colorHover : colorNormal);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                g2.setColor(colorBorde);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

                g2.dispose();
                super.paintComponent(g);
            }
        };

        boton.setFont(new Font("SansSerif", Font.PLAIN, 16));
        boton.setForeground(new Color(52, 58, 70));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(CENTER_ALIGNMENT);

        boton.setPreferredSize(new Dimension(150, 60));
        boton.setMaximumSize(new Dimension(150, 60));
        boton.setMinimumSize(new Dimension(150, 60));

        return boton;
    }
}
