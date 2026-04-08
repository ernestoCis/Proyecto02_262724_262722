package pantallas;

import controlador.Coordinador;
import dtos.ProductoDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

public class FrmSeleccionProductos extends JFrame {
    
    private final Coordinador coordinador;

    private JButton btnSalir;
    private JButton btnRegresar;
    private JButton btnTerminar;
    private JPanel panelProductos;

    private final Map<Long, Integer> cantidades = new HashMap<>();

    public FrmSeleccionProductos(Coordinador coordinador) {
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

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);

        JPanel panelSuperiorContenedor = new JPanel(new BorderLayout());
        panelSuperiorContenedor.setPreferredSize(new Dimension(1120, 205));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(colorMostaza);
        panelSuperior.setPreferredSize(new Dimension(1120, 125));

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

        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 28));
        panelDerecho.setOpaque(false);
        panelDerecho.setPreferredSize(new Dimension(120, 90));

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
        panelFranjaRoja.setPreferredSize(new Dimension(1120, 80));

        JPanel panelIzquierdoFranja = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 16));
        panelIzquierdoFranja.setOpaque(false);

        JLabel lblIconoMesero = new JLabel();
        ImageIcon iconoMesero = new ImageIcon("src\\main\\resources\\imagenes\\icono_usuario.png");
        Image meseroEscalado = iconoMesero.getImage().getScaledInstance(42, 42, Image.SCALE_SMOOTH);
        lblIconoMesero.setIcon(new ImageIcon(meseroEscalado));

        JLabel lblNombreMesero = new JLabel(coordinador.getMeseroActual().getNombre());
        lblNombreMesero.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblNombreMesero.setForeground(Color.BLACK);

        panelIzquierdoFranja.add(lblIconoMesero);
        panelIzquierdoFranja.add(lblNombreMesero);

        JPanel panelDerechoFranja = new JPanel(new FlowLayout(FlowLayout.RIGHT, 18, 18));
        panelDerechoFranja.setOpaque(false);

        btnRegresar = new JButton("←");
        btnRegresar.setFont(new Font("SansSerif", Font.BOLD, 24));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setBackground(colorMostaza);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegresar.setPreferredSize(new Dimension(38, 38));
        btnRegresar.setMargin(new Insets(0, 0, 0, 0));
        btnRegresar.setOpaque(true);
        btnRegresar.setContentAreaFilled(true);
        btnRegresar.setBorderPainted(false);

        panelDerechoFranja.add(btnRegresar);

        panelFranjaRoja.add(panelIzquierdoFranja, BorderLayout.WEST);
        panelFranjaRoja.add(panelDerechoFranja, BorderLayout.EAST);

        panelSuperiorContenedor.add(panelSuperior, BorderLayout.CENTER);
        panelSuperiorContenedor.add(panelFranjaRoja, BorderLayout.SOUTH);

        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(colorFondo);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 40, 25, 40));

        panelProductos = new JPanel();
        panelProductos.setBackground(new Color(235, 235, 235));
        panelProductos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(22, 22, 22, 22)
        ));
        panelProductos.setLayout(new GridLayout(0, 4, 35, 22));

        JScrollPane scrollProductos = new JScrollPane(panelProductos);
        scrollProductos.setPreferredSize(new Dimension(1040, 410));
        scrollProductos.setMaximumSize(new Dimension(1040, 410));
        scrollProductos.setBorder(BorderFactory.createEmptyBorder());
        scrollProductos.getVerticalScrollBar().setUnitIncrement(16);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 8));
        panelInferior.setBackground(new Color(235, 235, 235));
        panelInferior.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        panelInferior.setPreferredSize(new Dimension(1040, 70));
        panelInferior.setMaximumSize(new Dimension(1040, 70));

        btnTerminar = new JButton("Terminar de añadir");
        btnTerminar.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btnTerminar.setFocusPainted(false);
        btnTerminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTerminar.setBackground(Color.WHITE);
        btnTerminar.setForeground(new Color(55, 55, 55));
        btnTerminar.setPreferredSize(new Dimension(220, 34));
        btnTerminar.setBorder(BorderFactory.createLineBorder(colorMostaza, 1));

        panelInferior.add(btnTerminar);

        panelCentro.add(scrollProductos);
        panelCentro.add(Box.createVerticalStrut(35));
        panelCentro.add(panelInferior);

        panelPrincipal.add(panelSuperiorContenedor, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);

        eventos();
        
        cargarProductos(coordinador.obtenerProductos());
    }

    private void eventos() {
        btnSalir.addActionListener(e -> {
            coordinador.mostrarInicioSesionMesero();
            dispose();
        });
        
        btnRegresar.addActionListener(e -> {
            coordinador.mostrarMesas();
            dispose();
        });
        

        btnTerminar.addActionListener(e -> {
            System.out.println("Cantidades seleccionadas:");
            for (Map.Entry<Long, Integer> entry : cantidades.entrySet()) {
                if (entry.getValue() > 0) {
                    System.out.println("Producto ID " + entry.getKey() + " -> " + entry.getValue());
                }
            }
        });
    }

    private void cargarProductos(List<ProductoDTO> productos) {
        panelProductos.removeAll();
        cantidades.clear();

        if (productos != null) {
            for (ProductoDTO producto : productos) {
                cantidades.put(producto.getIdProducto(), 0);
                panelProductos.add(crearPanelProducto(producto));
            }
        }

        panelProductos.revalidate();
        panelProductos.repaint();
    }

    private JPanel crearPanelProducto(ProductoDTO producto) {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel panelImagen = new JPanel(new GridBagLayout());
        panelImagen.setBackground(Color.WHITE);
        panelImagen.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        panelImagen.setPreferredSize(new Dimension(90, 70));
        panelImagen.setMaximumSize(new Dimension(90, 70));
        panelImagen.setMinimumSize(new Dimension(90, 70));
        panelImagen.setAlignmentX(CENTER_ALIGNMENT);

        JLabel lblSinImagen = new JLabel("Sin imagen");
        lblSinImagen.setFont(new Font("SansSerif", Font.PLAIN, 11));
        panelImagen.add(lblSinImagen);

        JLabel lblNombre = new JLabel(producto.getNombre());
        lblNombre.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblNombre.setForeground(new Color(65, 65, 65));
        lblNombre.setAlignmentX(CENTER_ALIGNMENT);

        JPanel panelCantidad = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 0));
        panelCantidad.setOpaque(false);

        JButton btnMenos = new JButton("−");
        btnMenos.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btnMenos.setFocusPainted(false);
        btnMenos.setPreferredSize(new Dimension(45, 34));
        btnMenos.setMargin(new Insets(0, 0, 0, 0));

        JLabel lblCantidad = new JLabel("0", JLabel.CENTER);
        lblCantidad.setFont(new Font("SansSerif", Font.PLAIN, 16));
        lblCantidad.setBorder(BorderFactory.createLineBorder(new Color(190, 190, 190), 1));
        lblCantidad.setPreferredSize(new Dimension(36, 32));

        JButton btnMas = new JButton("+");
        btnMas.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btnMas.setFocusPainted(false);
        btnMenos.setPreferredSize(new Dimension(45, 34));
        btnMenos.setMargin(new Insets(0, 0, 0, 0));

        panelCantidad.add(btnMenos);
        panelCantidad.add(lblCantidad);
        panelCantidad.add(btnMas);

        JButton btnNota = new JButton("+Agregar nota");
        btnNota.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnNota.setAlignmentX(CENTER_ALIGNMENT);
        btnNota.setVisible(false);
        btnNota.setFocusPainted(false);
        btnNota.setBorderPainted(false);
        btnNota.setContentAreaFilled(false);
        btnNota.setOpaque(false);
        btnNota.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNota.setForeground(Color.BLACK);
        btnNota.setMargin(new Insets(0, 0, 0, 0));

        btnMas.addActionListener(e -> {
            int cantidadActual = cantidades.get(producto.getIdProducto());
            cantidadActual++;
            cantidades.put(producto.getIdProducto(), cantidadActual);
            lblCantidad.setText(String.valueOf(cantidadActual));
            btnNota.setVisible(cantidadActual >= 1);
        });

        btnMenos.addActionListener(e -> {
            int cantidadActual = cantidades.get(producto.getIdProducto());

            if (cantidadActual > 0) {
                cantidadActual--;
                cantidades.put(producto.getIdProducto(), cantidadActual);
                lblCantidad.setText(String.valueOf(cantidadActual));
                btnNota.setVisible(cantidadActual >= 1);
            }
        });
        
        btnNota.addActionListener(e -> {
//            String notaActual = notas.get(producto.getIdProducto());

            String nuevaNota = JOptionPane.showInputDialog(
                    this,
                    "Escribe una nota para " + producto.getNombre() + ":"
            );

//            if (nuevaNota != null) {
//                notas.put(producto.getIdProducto(), nuevaNota.trim());
//            }
        });

        panel.add(panelImagen);
        panel.add(Box.createVerticalStrut(8));
        panel.add(lblNombre);
        panel.add(Box.createVerticalStrut(8));
        panel.add(panelCantidad);
        panel.add(Box.createVerticalStrut(5));
        panel.add(btnNota);

        return panel;
    }
}