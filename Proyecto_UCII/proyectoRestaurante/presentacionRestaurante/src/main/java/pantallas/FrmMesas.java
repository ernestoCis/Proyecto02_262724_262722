package pantallas;

import controlador.Coordinador;
import dtos.ComandaDTO;
import dtos.MesaDTO;
import enums.EstadoMesaDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 * Pantalla principal de visualizacion y seleccion de mesas.
 * <p>Muestra el mapa de mesas del restaurante y su estado de disponibilidad actual.</p>
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmMesas extends JFrame {
    
    /** Referencia al controlador para gestionar flujos de datos. */
    private final Coordinador coordinador;

    /** Boton para cerrar sesion y volver al login. */
    private JButton btnSalir;
    /** Boton para generar mesas de forma masiva en el sistema. */
    private JButton btnInsercionMasiva;

    /** Panel contenedor que organiza las mesas en una cuadricula. */
    private JPanel panelMesas;

    /**
     * Constructor que inicializa la vista de mesas.
     * @param coordinador El coordinador general de la aplicacion.
     */
    public FrmMesas(Coordinador coordinador) {
        this.coordinador = coordinador;
        configurarVentana();
        inicializarComponentes();
    }

    /**
     * Establece las propiedades de tamaño, titulo y cierre del frame.
     */
    private void configurarVentana() {
        setTitle("Restaurante");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    /**
     * Inicializa la interfaz grafica.
     * <p>Crea:
     * <ul>
     * <li>Encabezado con logo y nombre del mesero.</li>
     * <li>Contenedor con scroll para las mesas.</li>
     * <li>Panel inferior de herramientas.</li>
     * </ul></p>
     */
    private void inicializarComponentes() {
        
        Color colorMostaza = new Color(229, 171, 75);
        Color colorRojo = new Color(216, 84, 78);
        Color colorFondo = new Color(239, 239, 239);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);

        JPanel panelSuperiorContenedor = new JPanel(new BorderLayout());
        panelSuperiorContenedor.setPreferredSize(new Dimension(1150, 215));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(colorMostaza);
        panelSuperior.setPreferredSize(new Dimension(1150, 135));

        JLabel lblLogo = new JLabel();
        lblLogo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        lblLogo.setPreferredSize(new Dimension(110, 100));

        ImageIcon iconoLogo = new ImageIcon("src\\main\\resources\\imagenes\\icono_restaurante.png");
        Image logoEscalado = iconoLogo.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(logoEscalado));

        JPanel panelTitulo = new JPanel(new GridBagLayout());
        panelTitulo.setOpaque(false);

        JLabel lblTitulo = new JLabel("Comandas");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 42));
        lblTitulo.setForeground(new Color(52, 58, 70));

        panelTitulo.add(lblTitulo);

        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 35));
        panelDerecho.setOpaque(false);
        panelDerecho.setPreferredSize(new Dimension(110, 100));
        
        btnSalir = new JButton();
        btnSalir.setFocusPainted(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        ImageIcon salirOriginal = new ImageIcon("src\\main\\resources\\imagenes\\icono_salir.png");
        Image salirEscalado = salirOriginal.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        btnSalir.setIcon(new ImageIcon(salirEscalado));

        panelDerecho.add(btnSalir);

        panelSuperior.add(lblLogo, BorderLayout.WEST);
        panelSuperior.add(panelTitulo, BorderLayout.CENTER);
        panelSuperior.add(panelDerecho, BorderLayout.EAST);

        JPanel panelFranjaRoja = new JPanel(new BorderLayout());
        panelFranjaRoja.setBackground(colorRojo);
        panelFranjaRoja.setPreferredSize(new Dimension(1150, 80));

        JPanel panelMesero = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 18));
        panelMesero.setOpaque(false);

        JLabel lblIconoMesero = new JLabel();
        ImageIcon iconoMesero = new ImageIcon("src\\main\\resources\\imagenes\\icono_usuario.png");
        Image meseroEscalado = iconoMesero.getImage().getScaledInstance(42, 42, Image.SCALE_SMOOTH);
        lblIconoMesero.setIcon(new ImageIcon(meseroEscalado));

        JLabel lblNombreMesero = new JLabel(coordinador.getMeseroActual().getNombre());
        lblNombreMesero.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblNombreMesero.setForeground(Color.BLACK);

        panelMesero.add(lblIconoMesero);
        panelMesero.add(lblNombreMesero);

        panelFranjaRoja.add(panelMesero, BorderLayout.WEST);

        panelSuperiorContenedor.add(panelSuperior, BorderLayout.CENTER);
        panelSuperiorContenedor.add(panelFranjaRoja, BorderLayout.SOUTH);

        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(colorFondo);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 40, 25, 40));

        panelMesas = new JPanel();
        panelMesas.setBackground(new Color(235, 235, 235));
        panelMesas.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panelMesas.setLayout(new GridLayout(0, 6, 35, 30));

        JScrollPane scrollMesas = new JScrollPane(panelMesas);
        scrollMesas.setPreferredSize(new Dimension(1050, 430));
        scrollMesas.setMaximumSize(new Dimension(1050, 430));
        scrollMesas.setBorder(BorderFactory.createEmptyBorder());
        scrollMesas.getVerticalScrollBar().setUnitIncrement(16);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        panelInferior.setBackground(new Color(235, 235, 235));
        panelInferior.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        panelInferior.setPreferredSize(new Dimension(1050, 70));
        panelInferior.setMaximumSize(new Dimension(1050, 70));

        btnInsercionMasiva = new JButton("+ Inserción masiva de mesas");
        btnInsercionMasiva.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btnInsercionMasiva.setFocusPainted(false);
        btnInsercionMasiva.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnInsercionMasiva.setBackground(Color.WHITE);
        btnInsercionMasiva.setForeground(new Color(80, 80, 80));
        btnInsercionMasiva.setPreferredSize(new Dimension(250, 36));
        btnInsercionMasiva.setBorder(BorderFactory.createLineBorder(colorMostaza, 1));

        panelInferior.add(btnInsercionMasiva);

        panelCentro.add(scrollMesas);
        panelCentro.add(Box.createVerticalStrut(20));
        panelCentro.add(panelInferior);

        panelPrincipal.add(panelSuperiorContenedor, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);

        eventos();
        
        //cargar mesas
        cargarMesas(coordinador.obtenerMesas());
    }

    /**
     * Define las acciones para los botones de la pantalla.
     */
    private void eventos() {
        btnSalir.addActionListener(e -> {
            coordinador.mostrarInicioSesionMesero();
            dispose();
        });

        btnInsercionMasiva.addActionListener(e -> {
            cargarMesas(coordinador.cargaMasivaMesas());
        });
    }

    /**
     * Renderiza la lista de mesas en el panel central.
     * <p>Limpia el contenedor actual y añade un panel por cada mesa obtenida.</p>
     * @param listaMesas Coleccion de objetos <code>MesaDTO</code> a mostrar.
     */
    public void cargarMesas(List<MesaDTO> listaMesas) {
        panelMesas.removeAll();

        if (listaMesas != null) {
            for (MesaDTO mesa : listaMesas) {
                panelMesas.add(crearPanelMesa(mesa));
            }
        }

        panelMesas.revalidate();
        panelMesas.repaint();
    }

    /**
     * Crea el componente visual individual para una mesa.
     * <p>Configura colores segun disponibilidad:
     * <ul>
     * <li>Verde: Disponible.</li>
     * <li>Rojo: Ocupada (No disponible).</li>
     * <li>Gris: Otros estados.</li>
     * </ul></p>
     * @param mesa Datos de la mesa a dibujar.
     * @return Un objeto <code>JPanel</code> con el boton de la mesa y su estado.
     */
    private JPanel crearPanelMesa(MesaDTO mesa) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JButton btnMesa = new JButton(String.valueOf(mesa.getNumero()));
        btnMesa.setFont(new Font("SansSerif", Font.PLAIN, 26));
        btnMesa.setFocusPainted(false);
        btnMesa.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnMesa.setAlignmentX(CENTER_ALIGNMENT);
        btnMesa.setPreferredSize(new Dimension(95, 95));
        btnMesa.setMaximumSize(new Dimension(95, 95));
        btnMesa.setMinimumSize(new Dimension(95, 95));
        btnMesa.setContentAreaFilled(false);
        btnMesa.setOpaque(true);

        JButton btnEstado = new JButton();
        btnEstado.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnEstado.setFocusPainted(false);
        btnEstado.setAlignmentX(CENTER_ALIGNMENT);
        btnEstado.setPreferredSize(new Dimension(95, 28));
        btnEstado.setMaximumSize(new Dimension(95, 28));
        btnEstado.setMinimumSize(new Dimension(95, 28));
        btnEstado.setEnabled(false);
        btnEstado.setOpaque(true);

        String disponibilidad = String.valueOf(mesa.getDisponibilidad());

        if (disponibilidad.equalsIgnoreCase("DISPONIBLE")) {
            btnMesa.setBorder(BorderFactory.createLineBorder(new Color(87, 214, 106), 1));
            btnEstado.setBorder(BorderFactory.createLineBorder(new Color(87, 214, 106), 1));
            btnEstado.setText("Disponible");
        } else if (disponibilidad.equalsIgnoreCase("NO_DISPONIBLE")) {
            btnMesa.setBorder(BorderFactory.createLineBorder(new Color(255, 92, 92), 1));
            btnEstado.setBorder(BorderFactory.createLineBorder(new Color(255, 92, 92), 1));
            btnEstado.setText("Ocupada");
        } else {
            btnMesa.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            btnEstado.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            btnEstado.setText(disponibilidad);
        }

        btnMesa.addActionListener(e -> {
            if(mesa.getDisponibilidad() == EstadoMesaDTO.DISPONIBLE){
                ComandaDTO comanda = coordinador.getComanda();
                comanda.setMesero(coordinador.getMeseroActual());
                coordinador.setMesaSeleccionada(mesa);
                comanda.setMesa(mesa);
                comanda.setNumeroMesa(mesa.getNumero());
                
                coordinador.setComanda(comanda);
                
                coordinador.setCarrito(null);
                
                coordinador.mostrarSeleccionProductos();
                dispose();
            }else if(mesa.getDisponibilidad() == EstadoMesaDTO.NO_DISPONIBLE){
                coordinador.setMesaSeleccionada(mesa);
                coordinador.mostrarEstadosComanda();
                dispose();
            }
        });

        panel.add(btnMesa);
        panel.add(Box.createVerticalStrut(6));
        panel.add(btnEstado);

        return panel;
    }
}