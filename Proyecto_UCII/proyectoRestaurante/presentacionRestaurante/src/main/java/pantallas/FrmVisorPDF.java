package pantallas;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PagePanel;
import controlador.Coordinador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
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
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmVisorPDF extends JFrame {

    private final Coordinador coordinador;

    private JButton btnRegresar;
    private JPanel panelVistaPdf;

    public FrmVisorPDF(Coordinador coordinador, String ruta) {
        this.coordinador = coordinador;
        configurarVentana();
        inicializarComponentes();
        cargarPDF(ruta);
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
        Color colorFondo = new Color(239, 239, 239);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);

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

        JLabel lblTitulo = new JLabel("Generar reporte");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblTitulo.setForeground(Color.BLACK);

        panelTitulo.add(lblTitulo);

        JPanel panelDerecho = new JPanel();
        panelDerecho.setOpaque(false);
        panelDerecho.setPreferredSize(new Dimension(110, 100));

        panelSuperior.add(lblLogo, BorderLayout.WEST);
        panelSuperior.add(panelTitulo, BorderLayout.CENTER);
        panelSuperior.add(panelDerecho, BorderLayout.EAST);

        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(colorFondo);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(22, 35, 25, 35));

        JPanel panelBotonRegresar = new JPanel();
        panelBotonRegresar.setOpaque(false);

        btnRegresar = new JButton("←");
        btnRegresar.setFont(new Font("Dialog", Font.BOLD, 24));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setBackground(colorMostaza);
        btnRegresar.setFocusPainted(false);
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegresar.setPreferredSize(new Dimension(38, 38));
        btnRegresar.setMargin(new Insets(0, 0, 0, 0));
        btnRegresar.setOpaque(true);
        btnRegresar.setContentAreaFilled(true);
        btnRegresar.setBorderPainted(false);
        btnRegresar.setBorder(BorderFactory.createEmptyBorder());

        panelBotonRegresar.add(btnRegresar, BorderLayout.WEST);

        JPanel panelContenedorVista = new JPanel(new BorderLayout());
        panelContenedorVista.setBackground(new Color(235, 235, 235));
        panelContenedorVista.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panelContenedorVista.setPreferredSize(new Dimension(1060, 620));
        panelContenedorVista.setMaximumSize(new Dimension(1060, 620));
        panelContenedorVista.setAlignmentX(CENTER_ALIGNMENT);

        panelVistaPdf = new JPanel();
        panelVistaPdf.setBackground(new Color(248, 248, 248));
        panelVistaPdf.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        panelVistaPdf.setLayout(new BorderLayout());

        panelContenedorVista.add(panelVistaPdf, BorderLayout.CENTER);

        JPanel contenedorSuperior = new JPanel(new BorderLayout());
        contenedorSuperior.setOpaque(false);
        contenedorSuperior.setMaximumSize(new Dimension(1060, 40));
        contenedorSuperior.setPreferredSize(new Dimension(1060, 40));
        contenedorSuperior.add(panelBotonRegresar, BorderLayout.WEST);

        panelCentro.add(contenedorSuperior);
        panelCentro.add(Box.createVerticalStrut(28));
        panelCentro.add(panelContenedorVista);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);

        eventos();
    }

    private void eventos() {
        btnRegresar.addActionListener(e -> {
            coordinador.mostrarReportesComandas();
            dispose();
        });
    }

    public JPanel getPanelVistaPdf() {
        return panelVistaPdf;
    }

    private void cargarPDF(String ruta) {
        final PagePanel visorInterno = new PagePanel();

        panelVistaPdf.removeAll();
        panelVistaPdf.setLayout(new BorderLayout());

        JScrollPane scroll = new JScrollPane(visorInterno);
        panelVistaPdf.add(scroll, BorderLayout.CENTER);

        panelVistaPdf.revalidate();
        panelVistaPdf.repaint();

        new Thread(() -> {
            try {
                File file = new File(ruta);
                RandomAccessFile raf = new RandomAccessFile(file, "r");
                FileChannel channel = raf.getChannel();
                ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());

                PDFFile pdfFile = new PDFFile(buf);

                if (pdfFile.getNumPages() > 0) {
                    PDFPage pagina = pdfFile.getPage(1);

                    float escala = 1.0f;
                    int ancho = (int) (pagina.getBBox().getWidth() * escala);
                    int alto = (int) (pagina.getBBox().getHeight() * escala);

                    visorInterno.setPreferredSize(new Dimension(ancho, alto));

                    EventQueue.invokeLater(() -> {
                        visorInterno.showPage(pagina);
                        panelVistaPdf.revalidate();
                        panelVistaPdf.repaint();
                    });
                }

                raf.close(); //liberamos el archivo

            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al procesar la imagen del PDF: " + e.getMessage());
            }
        }).start();
    }
}
