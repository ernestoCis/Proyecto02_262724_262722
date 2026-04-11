package pantallas;

import controlador.Coordinador;
import dtos.ComandaDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import com.github.lgooddatepicker.components.DatePicker;
import dtos.ReporteComandaDTO;
import java.awt.Insets;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;

public class FrmReporteComandas extends JFrame {

    private final Coordinador coordinador;

    private JButton btnRegresar;
    private JButton btnAceptarRango;
    private JButton btnGenerarPdf;

    private DatePicker dpFechaInicio;
    private DatePicker dpFechaFin;

    private JPanel panelResultados;
    private JLabel lblTotalAcumulado;

    private List<ReporteComandaDTO> comandasFiltradas;

    private JasperPrint jasperPrintActual;

    public FrmReporteComandas(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.comandasFiltradas = new ArrayList<>();

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
        Color colorFondo = new Color(239, 239, 239);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(colorMostaza);
        panelSuperior.setPreferredSize(new Dimension(1000, 120));

        JLabel lblLogo = new JLabel();
        lblLogo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        lblLogo.setPreferredSize(new Dimension(100, 90));

        ImageIcon iconoLogo = new ImageIcon("src\\main\\resources\\imagenes\\icono_restaurante.png");
        Image logoEscalado = iconoLogo.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(logoEscalado));

        JPanel panelTitulo = new JPanel(new GridBagLayout());
        panelTitulo.setOpaque(false);

        JLabel lblTitulo = new JLabel("Generar reporte");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        lblTitulo.setForeground(Color.BLACK);
        panelTitulo.add(lblTitulo);

        panelSuperior.add(lblLogo, BorderLayout.WEST);
        panelSuperior.add(panelTitulo, BorderLayout.CENTER);

        JPanel panelCentro = new JPanel();
        panelCentro.setBackground(colorFondo);
        panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        JPanel panelContenido = new JPanel(new BorderLayout(20, 0));
        panelContenido.setBackground(new Color(235, 235, 235));
        panelContenido.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
        panelContenido.setPreferredSize(new Dimension(900, 430));
        panelContenido.setMaximumSize(new Dimension(900, 430));

        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setOpaque(false);
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setPreferredSize(new Dimension(260, 360));

        btnRegresar = new JButton("←");
        btnRegresar.setFont(new Font("Dialog", Font.BOLD, 24));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setBackground(new Color(229, 171, 75));
        btnRegresar.setFocusPainted(false);
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegresar.setBounds(20, 20, 70, 50);
        btnRegresar.setMargin(new Insets(0, 0, 0, 0));
        btnRegresar.setOpaque(true);
        btnRegresar.setContentAreaFilled(true);
        btnRegresar.setBorderPainted(false);
        btnRegresar.setBorder(BorderFactory.createEmptyBorder());

        JLabel lblTipo = new JLabel("Tipo de reporte:");
        lblTipo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTipo.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblReporte = new JLabel("Reporte de comandas");
        lblReporte.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblReporte.setForeground(new Color(90, 90, 90));
        lblReporte.setAlignmentX(LEFT_ALIGNMENT);

        JLabel lblRango = new JLabel("Rango de fechas:");
        lblRango.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblRango.setAlignmentX(LEFT_ALIGNMENT);

        dpFechaInicio = new DatePicker();
        dpFechaInicio.setMaximumSize(new Dimension(180, 35));
        dpFechaInicio.setDateToToday();
        dpFechaInicio.setAlignmentX(LEFT_ALIGNMENT);

        dpFechaFin = new DatePicker();
        dpFechaFin.setMaximumSize(new Dimension(180, 35));
        dpFechaFin.setDateToToday();
        dpFechaFin.setAlignmentX(LEFT_ALIGNMENT);

        dpFechaInicio.getComponentToggleCalendarButton().setText("▼");//▼
        dpFechaFin.getComponentToggleCalendarButton().setText("▼");

        dpFechaInicio.getComponentToggleCalendarButton().setPreferredSize(new Dimension(35, 30));
        dpFechaFin.getComponentToggleCalendarButton().setPreferredSize(new Dimension(35, 30));

        btnAceptarRango = new JButton("Aceptar");
        btnAceptarRango.setFont(new Font("SansSerif", Font.PLAIN, 16));
        btnAceptarRango.setFocusPainted(false);
        btnAceptarRango.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAceptarRango.setBackground(Color.WHITE);
        btnAceptarRango.setPreferredSize(new Dimension(120, 35));
        btnAceptarRango.setMaximumSize(new Dimension(120, 35));
        btnAceptarRango.setAlignmentX(LEFT_ALIGNMENT);
        btnAceptarRango.setBorder(BorderFactory.createLineBorder(colorMostaza, 1));

        panelIzquierdo.add(btnRegresar);
        panelIzquierdo.add(Box.createVerticalStrut(18));
        panelIzquierdo.add(lblTipo);
        panelIzquierdo.add(Box.createVerticalStrut(6));
        panelIzquierdo.add(lblReporte);
        panelIzquierdo.add(Box.createVerticalStrut(28));
        panelIzquierdo.add(lblRango);
        panelIzquierdo.add(Box.createVerticalStrut(12));
        panelIzquierdo.add(new JLabel("Fecha inicio"));
        panelIzquierdo.add(Box.createVerticalStrut(4));
        panelIzquierdo.add(dpFechaInicio);
        panelIzquierdo.add(Box.createVerticalStrut(12));
        panelIzquierdo.add(new JLabel("Fecha fin"));
        panelIzquierdo.add(Box.createVerticalStrut(4));
        panelIzquierdo.add(dpFechaFin);
        panelIzquierdo.add(Box.createVerticalStrut(16));
        panelIzquierdo.add(btnAceptarRango);

        panelResultados = new JPanel();
        panelResultados.setOpaque(false);
        panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.Y_AXIS));

        JScrollPane scrollResultados = new JScrollPane(panelResultados);
        scrollResultados.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210), 1));
        scrollResultados.getVerticalScrollBar().setUnitIncrement(16);

        JPanel panelDerecho = new JPanel(new BorderLayout());
        panelDerecho.setOpaque(false);

        lblTotalAcumulado = new JLabel("Total acumulado: $0.00");
        lblTotalAcumulado.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblTotalAcumulado.setHorizontalAlignment(JLabel.RIGHT);
        lblTotalAcumulado.setVisible(false);

        panelDerecho.add(scrollResultados, BorderLayout.CENTER);
        panelDerecho.add(lblTotalAcumulado, BorderLayout.SOUTH);

        panelContenido.add(panelIzquierdo, BorderLayout.WEST);
        panelContenido.add(panelDerecho, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        panelInferior.setBackground(new Color(235, 235, 235));
        panelInferior.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        panelInferior.setPreferredSize(new Dimension(900, 70));
        panelInferior.setMaximumSize(new Dimension(900, 70));

        btnGenerarPdf = new JButton("Descargar pdf");
        btnGenerarPdf.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btnGenerarPdf.setFocusPainted(false);
        btnGenerarPdf.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnGenerarPdf.setBackground(Color.WHITE);
        btnGenerarPdf.setForeground(new Color(55, 55, 55));
        btnGenerarPdf.setPreferredSize(new Dimension(190, 34));
        btnGenerarPdf.setBorder(BorderFactory.createLineBorder(colorMostaza, 1));

        panelInferior.add(btnGenerarPdf);

        panelCentro.add(panelContenido);
        panelCentro.add(Box.createVerticalStrut(18));
        panelCentro.add(panelInferior);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);

        eventos();
    }

    private void eventos() {
        btnRegresar.addActionListener(e -> {
            coordinador.mostrarOpcionesReporte();
            dispose();
        });

        btnAceptarRango.addActionListener(e -> {
            comandasFiltradas = new ArrayList<>();
            panelResultados.removeAll();
            panelResultados.repaint();
            lblTotalAcumulado.setVisible(false);
            cargarComandasPorRango();
        });

        btnGenerarPdf.addActionListener(e -> {
            if (jasperPrintActual == null) {
                JOptionPane.showMessageDialog(this, "Debes aceptar un rango de fechas para generar datos.");
                return;
            }

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar Reporte de Comandas");
            fileChooser.setFileFilter(new FileNameExtensionFilter("Archivos PDF (*.pdf)", "pdf"));

            String nombreSugerido = "Reporte_Comandas" + ".pdf";
            fileChooser.setSelectedFile(new File(nombreSugerido));

            int seleccion = fileChooser.showSaveDialog(this);

            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File archivoDestino = fileChooser.getSelectedFile();
                String ruta = archivoDestino.getAbsolutePath();

                if (!ruta.toLowerCase().endsWith(".pdf")) {
                    ruta += ".pdf";
                }

                try {
                    net.sf.jasperreports.engine.JasperExportManager.exportReportToPdfFile(jasperPrintActual, ruta);

                    JOptionPane.showMessageDialog(this, "¡Reporte descargado con éxito!");

                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error al guardar el PDF: " + ex.getMessage());
                }
            }
        });
    }

    private void cargarComandasPorRango() {
        panelResultados.removeAll();
        panelResultados.revalidate();
        panelResultados.repaint();

        LocalDate fechaInicio = dpFechaInicio.getDate();
        LocalDate fechaFin = dpFechaFin.getDate();

        if (fechaInicio == null || fechaFin == null) {
            JOptionPane.showMessageDialog(null, "Selecciona ambas fechas");
            return;
        }

        if (fechaInicio.isAfter(fechaFin)) {
            JOptionPane.showMessageDialog(this, "La fecha inicio no puede ser mayor que la fecha fin.");
            return;
        }

        comandasFiltradas = coordinador.obetnerComandasPorRangoFechas(fechaInicio, fechaFin);
        if (comandasFiltradas == null || comandasFiltradas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No existen comandas dentro de ese rango de fechas");
            return;
        }

        try {
            jasperPrintActual = coordinador.generarJasperComandas(comandasFiltradas, fechaInicio, fechaFin);

            JRViewer visor = new JRViewer(jasperPrintActual);

            panelResultados.removeAll();
            panelResultados.add(visor, BorderLayout.CENTER);

            panelResultados.revalidate();
            panelResultados.repaint();

            double total = comandasFiltradas.stream().mapToDouble(ReporteComandaDTO::getTotal).sum();
            lblTotalAcumulado.setText("Total acumulado: $" + String.format("%,.2f", total));
            lblTotalAcumulado.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al generar visor: " + e.getMessage());
        }

        panelResultados.revalidate();
        panelResultados.repaint();
    }
}
