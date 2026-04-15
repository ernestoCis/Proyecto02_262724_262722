/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import componentes.BotonModulos;
import controlador.Coordinador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmAcciones extends JFrame {

    private final Coordinador coordinador;

    private JLabel lblLogo;
    private JLabel lblTitulo;

    private JButton btnSalir;
    private JButton btnProductos;
    private JButton btnIngredientes;
    private JButton btnClientes;
    private JButton btnReportes;

    public FrmAcciones(Coordinador coordinador) {
        this.coordinador = coordinador;
        initComponents();
    }

    private void initComponents() {
        setTitle("Restaurante");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JPanel fondo = new JPanel(new BorderLayout());
        fondo.setBackground(new Color(238, 238, 238));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setPreferredSize(new Dimension(1000, 115));
        panelSuperior.setBackground(new Color(229, 171, 75));

        JPanel panelLogo = new JPanel();
        panelLogo.setOpaque(false);
        panelLogo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));

        lblLogo = new JLabel();
        lblLogo.setPreferredSize(new Dimension(70, 70));
        ImageIcon logoOriginal = new ImageIcon("src\\main\\resources\\imagenes\\icono_restaurante.png");
        Image logoEscalado = logoOriginal.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(logoEscalado));

        panelLogo.add(lblLogo);

        JPanel panelTitulo = new JPanel(new GridBagLayout());
        panelTitulo.setOpaque(false);

        lblTitulo = new JLabel("Restaurante");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(52, 58, 70));

        panelTitulo.add(lblTitulo);

        JPanel panelSalir = new JPanel();
        panelSalir.setOpaque(false);
        panelSalir.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 15));

        btnSalir = new JButton();
        btnSalir.setFocusPainted(false);
        btnSalir.setBorderPainted(false);
        btnSalir.setContentAreaFilled(false);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        ImageIcon salirOriginal = new ImageIcon("src\\main\\resources\\imagenes\\icono_salir.png");
        Image salirEscalado = salirOriginal.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        btnSalir.setIcon(new ImageIcon(salirEscalado));

        panelSalir.add(btnSalir);

        panelSuperior.add(panelLogo, BorderLayout.WEST);
        panelSuperior.add(panelTitulo, BorderLayout.CENTER);
        panelSuperior.add(panelSalir, BorderLayout.EAST);

        JPanel centro = new JPanel();
        centro.setOpaque(false);
        centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
        centro.setBorder(BorderFactory.createEmptyBorder(70, 0, 0, 0));

        btnProductos = new BotonModulos("PRODUCTOS", "src\\main\\resources\\imagenes\\icono_productos.png");
        btnIngredientes = new BotonModulos("INGREDIENTES", "src\\main\\resources\\imagenes\\icono_ingredientes.png");
        btnClientes = new BotonModulos("CLIENTES", "src\\main\\resources\\imagenes\\icono_clientes.png");
        btnReportes = new BotonModulos("REPORTES", "src\\main\\resources\\imagenes\\icono_reportes.png");

        btnProductos.setAlignmentX(CENTER_ALIGNMENT);
        btnIngredientes.setAlignmentX(CENTER_ALIGNMENT);
        btnClientes.setAlignmentX(CENTER_ALIGNMENT);
        btnReportes.setAlignmentX(CENTER_ALIGNMENT);

        centro.add(btnProductos);
        centro.add(Box.createRigidArea(new Dimension(0, 28)));
        centro.add(btnIngredientes);
        centro.add(Box.createRigidArea(new Dimension(0, 28)));
        centro.add(btnClientes);
        centro.add(Box.createRigidArea(new Dimension(0, 28)));
        centro.add(btnReportes);

        fondo.add(panelSuperior, BorderLayout.NORTH);
        fondo.add(centro, BorderLayout.CENTER);

        add(fondo);

        btnSalir.addActionListener(e -> {
            coordinador.iniciarSistema();
            dispose();
        });
        btnProductos.addActionListener(e -> {
            coordinador.mostrarProductosAdmin();
            dispose();
        });
        btnIngredientes.addActionListener(e -> {
            coordinador.mostrarIngredientes();
            dispose();
        });
        btnClientes.addActionListener(e -> {
            coordinador.mostrarClientes();
            dispose();
        });
        btnReportes.addActionListener(e -> {
            coordinador.mostrarOpcionesReporte();
            dispose();
        });
    }

}
