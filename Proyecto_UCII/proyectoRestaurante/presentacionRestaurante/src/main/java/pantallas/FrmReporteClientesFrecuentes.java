package pantallas;

import componentes.BotonRegresar;
import controlador.Coordinador;
import java.awt.*;
import javax.swing.*;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

public class FrmReporteClientesFrecuentes extends JFrame {

    private final Coordinador coordinador;

    private JTextField txtNombre;
    private JTextField txtVisitas;
    private JCheckBox chkNombre;
    private JCheckBox chkVisitas;

    // Panel donde se mostrará el PDF
    private JPanel panelVista;

    public FrmReporteClientesFrecuentes(Coordinador coordinador) {
        this.coordinador = coordinador;
        initComponents();
    }

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

        btnRegresar.addActionListener(e -> {
            coordinador.mostrarOpcionesReporte();
            dispose();
        });

        panelRegresar.setBackground(colorFondo);
        panelRegresar.add(btnRegresar);

        JPanel contenedorSuperior = new JPanel(new BorderLayout());
        contenedorSuperior.add(panelSuperior, BorderLayout.NORTH);
        contenedorSuperior.add(panelRegresar, BorderLayout.CENTER);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(colorFondo);
        panelCentro.setBorder(
                BorderFactory.createEmptyBorder(10, 30, 20, 30));

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(245, 245, 245));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel contenido = new JPanel(new BorderLayout());
        contenido.setOpaque(false);

        JPanel panelFiltros = new JPanel();
        panelFiltros.setPreferredSize(new Dimension(250, 400));
        panelFiltros.setOpaque(false);
        panelFiltros.setLayout(
                new BoxLayout(panelFiltros, BoxLayout.Y_AXIS));

        JLabel lblTipo = new JLabel("Tipo de reporte:");
        JLabel lblTipoValor = new JLabel("Reporte de clientes frecuentes");
        lblTipoValor.setForeground(Color.GRAY);

        chkNombre = new JCheckBox("Nombre:");
        chkNombre.setOpaque(false);

        txtNombre = new JTextField();
        txtNombre.setMaximumSize(new Dimension(200, 25));

        chkVisitas = new JCheckBox("Mínimo de visitas:");
        chkVisitas.setOpaque(false);

        txtVisitas = new JTextField();
        txtVisitas.setMaximumSize(new Dimension(200, 25));

        panelFiltros.add(lblTipo);
        panelFiltros.add(Box.createVerticalStrut(5));
        panelFiltros.add(lblTipoValor);
        panelFiltros.add(Box.createVerticalStrut(20));
        panelFiltros.add(new JLabel("Filtrar por:"));
        panelFiltros.add(Box.createVerticalStrut(10));
        panelFiltros.add(chkNombre);
        panelFiltros.add(txtNombre);
        panelFiltros.add(Box.createVerticalStrut(15));
        panelFiltros.add(chkVisitas);
        panelFiltros.add(txtVisitas);

        panelVista = new JPanel(new BorderLayout());

        contenido.add(panelFiltros, BorderLayout.WEST);
        contenido.add(panelVista, BorderLayout.CENTER);

        JButton btnVistaPrevia = new JButton("Vista previa");
        JButton btnDescargar = new JButton("Descargar PDF");

        btnVistaPrevia.setPreferredSize(new Dimension(200, 35));
        btnDescargar.setPreferredSize(new Dimension(200, 35));

        btnVistaPrevia.addActionListener(e -> vistaPreviaPDF());
        btnDescargar.addActionListener(e -> descargarPDF());

        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.add(btnVistaPrevia);
        panelBoton.add(btnDescargar);

        card.add(contenido, BorderLayout.CENTER);

        panelCentro.add(card, BorderLayout.CENTER);
        panelCentro.add(panelBoton, BorderLayout.SOUTH);

        fondo.add(contenedorSuperior, BorderLayout.NORTH);
        fondo.add(panelCentro, BorderLayout.CENTER);

        add(fondo);
    }

    private void vistaPreviaPDF() {
        try {
            String nombre = null;
            Integer visitas = null;

            if (chkNombre.isSelected()) {
                nombre = txtNombre.getText();
            }

            if (chkVisitas.isSelected()
                    && !txtVisitas.getText().isBlank()) {
                visitas = Integer.valueOf(txtVisitas.getText());
            }

            JasperPrint print =
                    coordinador.generarReporteClientes(nombre, visitas);

            JRViewer viewer = new JRViewer(print);

            panelVista.removeAll();
            panelVista.add(viewer, BorderLayout.CENTER);
            panelVista.revalidate();
            panelVista.repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void descargarPDF() {

        String nombre = null;
        Integer visitas = null;

        if (chkNombre.isSelected()) {
            nombre = txtNombre.getText();
        }

        if (chkVisitas.isSelected()
                && !txtVisitas.getText().isBlank()) {
            visitas = Integer.valueOf(txtVisitas.getText());
        }

        coordinador.descargarPDFClientesFrecuentes(nombre, visitas);
    }
}