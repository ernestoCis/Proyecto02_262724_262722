package pantallas;

import controlador.Coordinador;
import java.awt.BorderLayout;
import java.awt.Color;
import static java.awt.Component.CENTER_ALIGNMENT;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
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
 * Clase principal que representa la interfaz de inicio de la aplicación del restaurante.
 * <p>
 * Esta ventana permite al usuario seleccionar su rol dentro del sistema, 
 * ya sea como Mesero o como Administrador. Utiliza un objeto {@link Coordinador} 
 * para gestionar la navegación entre pantallas.
 * </p>
 * * @author Paulina Guevara, Ernesto Cisneros
 * @version 1.0
 * @see javax.swing.JFrame
 */
public class FrmInicio extends JFrame {
    
    /** * Referencia al coordinador del sistema para la gestión de eventos y navegación. 
     */
    private final Coordinador coordinador;

    /** Etiqueta que contiene el logo del restaurante. */
    private JLabel lblLogo;
    
    /** Etiqueta que muestra el icono de usuario en el panel central. */
    private JLabel lblUsuario;
    
    /** Etiqueta que muestra el título principal "Restaurante". */
    private JLabel lblTitulo;

    /** Botón para acceder a las funciones de Mesero. */
    private JButton btnMesero;
    
    /** Botón para acceder a las funciones de Administrador. */
    private JButton btnAdministrador;

    /**
     * Constructor de la clase. Inicializa la ventana, asigna el coordinador y construye los componentes.
     * * @param coordinador Instancia de {@link Coordinador} que controlará la lógica de navegación.
     */
    public FrmInicio(Coordinador coordinador) {
        this.coordinador = coordinador;
        initComponents();
    }

    /**
     * Configura e inicializa todos los componentes de la interfaz gráfica (GUI).
     * <p>
     * Define el diseño (layout), colores, tamaños y los eventos de escucha para los botones.
     * </p>
     */
    private void initComponents() {
        setTitle("Sistema restaurante");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Panel principal
        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(new Color(239, 239, 239));

        // panel superior
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setPreferredSize(new Dimension(1000, 165));
        panelSuperior.setOpaque(false);

        // encabezado color mostaza
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(new Color(226, 170, 75));
        encabezado.setPreferredSize(new Dimension(1000, 120));

        // panel del logo izquierdo
        JPanel panelLogo = new JPanel(new BorderLayout());
        panelLogo.setOpaque(false);
        panelLogo.setBorder(new EmptyBorder(18, 18, 18, 10));

        lblLogo = new JLabel();
        lblLogo.setPreferredSize(new Dimension(70, 70));
        ImageIcon imagenRestaurante = new ImageIcon("src\\main\\resources\\imagenes\\icono_restaurante.png");
        Image imagenEscalada = imagenRestaurante.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(imagenEscalada));

        panelLogo.add(lblLogo, BorderLayout.CENTER);

        // panel del titulo
        JPanel panelTitulo = new JPanel(new GridBagLayout());
        panelTitulo.setOpaque(false);

        lblTitulo = new JLabel("Restaurante");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(36, 41, 51));

        panelTitulo.setBorder(new EmptyBorder(0, -50, 20, 0));
        panelTitulo.add(lblTitulo);

        encabezado.add(panelLogo, BorderLayout.WEST);
        encabezado.add(panelTitulo, BorderLayout.CENTER);

        // franja roja
        JPanel franjaRoja = new JPanel();
        franjaRoja.setBackground(new Color(216, 84, 78));
        franjaRoja.setPreferredSize(new Dimension(1000, 45));

        panelSuperior.add(encabezado, BorderLayout.CENTER);
        panelSuperior.add(franjaRoja, BorderLayout.SOUTH);

        // panel central
        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBorder(new EmptyBorder(70, 0, 0, 0));

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

        fondo.add(panelSuperior, BorderLayout.NORTH);
        fondo.add(centro, BorderLayout.CENTER);

        add(fondo);

        //evento para el botón Mesero
        btnMesero.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Pantalla de mesero");
        });
        
        //evento para el botón Administrador
        btnAdministrador.addActionListener(e -> {
            coordinador.mostrarAcciones();
            setVisible(false);
        });
    }
    
    /**
     * Crea un botón personalizado con el estilo visual específico de la aplicación.
     * <p>
     * Configura propiedades como la fuente, color de fondo, cursor y dimensiones fijas.
     * </p>
     * * @param texto El texto descriptivo que aparecerá en el botón.
     * @return Un objeto {@link JButton} listo para ser añadido al contenedor.
     */
    private JButton crearBoton(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("SansSerif", Font.BOLD, 28));
        boton.setForeground(new Color(52, 58, 70));
        boton.setBackground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setAlignmentX(CENTER_ALIGNMENT);

        boton.setPreferredSize(new Dimension(300, 90));
        boton.setMaximumSize(new Dimension(300, 90));
        boton.setMinimumSize(new Dimension(300, 90));

        return boton;
    }
}