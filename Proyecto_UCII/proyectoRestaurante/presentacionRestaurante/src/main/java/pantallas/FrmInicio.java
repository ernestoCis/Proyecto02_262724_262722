package pantallas;

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
import javax.swing.border.EmptyBorder;

/**
 * Clase principal que representa la interfaz de inicio de la aplicación del
 * restaurante.
 * <p>
 * Esta ventana permite al usuario seleccionar su rol dentro del sistema, ya sea
 * como Mesero o como Administrador. Utiliza un objeto {@link Coordinador} para
 * gestionar la navegación entre pantallas.
 * </p>
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 * @version 1.0
 * @see javax.swing.JFrame
 */
public class FrmInicio extends JFrame {

    /**
     * * Referencia al coordinador del sistema para la gestión de eventos y
     * navegación.
     */
    private final Coordinador coordinador;

    /**
     * Etiqueta que contiene el logo del restaurante.
     */
    private JLabel lblLogo;

    /**
     * Etiqueta que muestra el icono de usuario en el panel central.
     */
    private JLabel lblUsuario;

    /**
     * Etiqueta que muestra el título principal "Restaurante".
     */
    private JLabel lblTitulo;

    /**
     * Botón para acceder a las funciones de Mesero.
     */
    private JButton btnMesero;

    /**
     * Botón para acceder a las funciones de Administrador.
     */
    private JButton btnAdministrador;

    /**
     * Constructor de la clase. Inicializa la ventana, asigna el coordinador y
     * construye los componentes.
     *
     * * @param coordinador Instancia de {@link Coordinador} que controlará la
     * lógica de navegación.
     */
    public FrmInicio(Coordinador coordinador) {
        this.coordinador = coordinador;
        initComponents();
    }

    /**
     * Configura e inicializa todos los componentes de la interfaz gráfica
     * (GUI).
     * <p>
     * Define el diseño (layout), colores, tamaños y los eventos de escucha para
     * los botones.
     * </p>
     */
    private void initComponents() {
        Color colorMostaza = new Color(229, 171, 75);
        Color colorRojo = new Color(216, 84, 78);
        
        setTitle("Restaurante");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(new Color(239, 239, 239));

        // panel superior
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
        panelFranjaRoja.setPreferredSize(new Dimension(900, 40));

        panelSuperiorContenedor.add(panelSuperior, BorderLayout.CENTER);
        panelSuperiorContenedor.add(panelFranjaRoja, BorderLayout.SOUTH);
        
        // panel central
        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBorder(new EmptyBorder(40, 0, 0, 0));

        // imagen de usuario
        lblUsuario = new JLabel();
        lblUsuario.setAlignmentX(CENTER_ALIGNMENT);
        lblUsuario.setPreferredSize(new Dimension(120, 120));
        lblUsuario.setMaximumSize(new Dimension(120, 120));

        ImageIcon iconoUsuarioOriginal = new ImageIcon("src\\main\\resources\\imagenes\\icono_usuario.png");
        Image imagenUsuarioEscalada = iconoUsuarioOriginal.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        lblUsuario.setIcon(new ImageIcon(imagenUsuarioEscalada));

        centro.add(lblUsuario);
        centro.add(Box.createRigidArea(new Dimension(0, 35)));

        // ----- botones -----
        btnMesero = crearBoton("Mesero");
        btnAdministrador = crearBoton("Administrador");

        btnMesero.setAlignmentX(CENTER_ALIGNMENT);
        btnAdministrador.setAlignmentX(CENTER_ALIGNMENT);

        centro.add(btnMesero);
        centro.add(Box.createRigidArea(new Dimension(0, 26)));
        centro.add(btnAdministrador);

        fondo.add(panelSuperiorContenedor, BorderLayout.NORTH);
        fondo.add(centro, BorderLayout.CENTER);

        add(fondo);

        //evento para el botón Administrador
        btnAdministrador.addActionListener(e -> {
            coordinador.mostrarAcciones();
            setVisible(false);
        });

        //evento para el botón Mesero
        btnMesero.addActionListener(e -> {
            coordinador.mostrarInicioSesionMesero();
            setVisible(false);
        });
    }

    /**
     * Crea un botón personalizado con el estilo visual específico de la
     * aplicación.
     * <p>
     * Configura propiedades como la fuente, color de fondo, cursor y
     * dimensiones fijas.
     * </p>
     *
     * * @param texto El texto descriptivo que aparecerá en el botón.
     * @return Un objeto {@link JButton} listo para ser añadido al contenedor.
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

        boton.setFont(new Font("SansSerif", Font.BOLD, 28));
        boton.setForeground(new Color(52, 58, 70));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(CENTER_ALIGNMENT);

        boton.setPreferredSize(new Dimension(300, 90));
        boton.setMaximumSize(new Dimension(300, 90));
        boton.setMinimumSize(new Dimension(300, 90));

        return boton;
    }
}
