package pantallas;

import componentes.BotonRegresar;
import componentes.BotonReportes;
import controlador.Coordinador;
import java.awt.*;
import javax.swing.*;

/**
 * Pantalla de menú para la selección del tipo de reporte a generar.
 * <p>
 * Esta ventana centraliza el acceso a los reportes de actividad del
 * restaurante, permitiendo al usuario administrativo elegir entre informes de
 * comandas o estadísticas de clientes frecuentes.</p>
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmSeleccionReporte extends JFrame {

    /**
     * Controlador encargado de la navegación y la lógica de negocio de
     * reportes.
     */
    private final Coordinador coordinador;

    /**
     * Botón para acceder al módulo de reportes históricos de comandas.
     */
    private JButton btnReporteComandas;

    /**
     * Botón para acceder al módulo de reportes de clientes frecuentes.
     */
    private JButton btnReporteClientesF;

    /**
     * Constructor que inicializa la interfaz de selección de reportes.
     *
     * @param coordinador El coordinador general del sistema que gestiona el
     * flujo de navegación.
     */
    public FrmSeleccionReporte(Coordinador coordinador) {
        this.coordinador = coordinador;
        initComponents();
    }

    /**
     * Inicializa y configura todos los componentes visuales de la ventana.
     * <p>
     * Utiliza una combinación de {@link BorderLayout} para la estructura
     * general y {@link GridBagLayout} para centrar los botones de selección en
     * el área de contenido.</p>
     * <p>
     * Incluye la personalización estética con colores mostaza y fondos grises
     * consistentes con el resto de la aplicación.</p>
     */
    private void initComponents() {

        setTitle("Restaurante");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Color colorMostaza = new Color(229, 171, 75);
        Color colorFondo = new Color(238, 238, 238);

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(colorFondo);

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

        JLabel lblTitulo = new JLabel("Reportes");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(52, 58, 70));

        panelTitulo.add(lblTitulo);

        panelSuperior.add(lblLogo, BorderLayout.WEST);
        panelSuperior.add(panelTitulo, BorderLayout.CENTER);
        panelSuperiorContenedor.add(panelSuperior, BorderLayout.CENTER);

        BotonRegresar btnRegresar = new BotonRegresar();

        JPanel panelRegresar = new JPanel(null);
        panelRegresar.setOpaque(false);
        panelRegresar.setPreferredSize(new Dimension(1000, 60));

        btnRegresar.setBounds(20, 20, 40, 40);
        panelRegresar.add(btnRegresar);

        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);

        JPanel contenedorBotones = new JPanel();
        contenedorBotones.setLayout(new BoxLayout(contenedorBotones, BoxLayout.Y_AXIS));
        contenedorBotones.setOpaque(false);

        btnReporteComandas = new BotonReportes(
                "REPORTE DE COMANDAS",
                "src\\main\\resources\\imagenes\\icono_reportes.png"
        );

        btnReporteClientesF = new BotonReportes(
                "REPORTE DE CLIENTES FRECUENTES",
                "src\\main\\resources\\imagenes\\icono_reportes.png"
        );

        btnReporteComandas.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnReporteClientesF.setAlignmentX(Component.CENTER_ALIGNMENT);

        contenedorBotones.add(btnReporteComandas);
        contenedorBotones.add(Box.createRigidArea(new Dimension(0, 20)));
        contenedorBotones.add(btnReporteClientesF);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(70, 0, 0, 0);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        centro.add(contenedorBotones, gbc);

        JPanel contenedorCentro = new JPanel(new BorderLayout());
        contenedorCentro.setOpaque(false);

        contenedorCentro.add(panelRegresar, BorderLayout.NORTH);
        contenedorCentro.add(centro, BorderLayout.CENTER);

        fondo.add(panelSuperior, BorderLayout.NORTH);
        fondo.add(contenedorCentro, BorderLayout.CENTER);

        add(fondo);

        btnRegresar.addActionListener(e -> {
            coordinador.mostrarAcciones();
            dispose();
        });

        btnReporteComandas.addActionListener(e -> {
            coordinador.mostrarReportesComandas();
            dispose();
        });

        btnReporteClientesF.addActionListener(e -> {
            coordinador.mostrarReporteClientesFrecuentes();
        });
    }
}
