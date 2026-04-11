package pantallas;

import controlador.Coordinador;
import dtos.ComandaDTO;
import dtos.DetallePedidoDTO;
import dtos.MesaDTO;
import enums.EstadoComandaDTO;
import enums.EstadoMesaDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.time.format.DateTimeFormatter;
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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FrmEstadosComanda extends JFrame {

    private final Coordinador coordinador;

    private JButton btnSalir;
    private JButton btnRegresar;
    private JButton btnEditar;
    private JButton btnEntregada;
    private JButton btnCancelar;

    private JTable tblDetalles;
    private DefaultTableModel modeloTabla;

    private JLabel lblFolio;
    private JLabel lblMesa;
    private JLabel lblFecha;
    private JLabel lblEstado;
    private JLabel lblTotal;
    private JLabel lblClienteAsociado;
    private JLabel lblNombreMesero;

    public FrmEstadosComanda(Coordinador coordinador) {
        this.coordinador = coordinador;
        configurarVentana();
        inicializarComponentes();
        cargarDatosComanda();
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
        Color colorTablaHeader = new Color(242, 205, 205);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);

        JPanel panelSuperiorContenedor = new JPanel(new BorderLayout());
        panelSuperiorContenedor.setPreferredSize(new Dimension(1080, 175));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(colorMostaza);
        panelSuperior.setPreferredSize(new Dimension(1080, 125));

        JLabel lblLogo = new JLabel();
        lblLogo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        lblLogo.setPreferredSize(new Dimension(110, 100));

        ImageIcon iconoLogo = new ImageIcon("src\\main\\resources\\imagenes\\icono_restaurante.png");
        Image logoEscalado = iconoLogo.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(logoEscalado));

        JPanel panelTitulo = new JPanel(new GridBagLayout());
        panelTitulo.setOpaque(false);

        JLabel lblTitulo = new JLabel("Comanda");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 40));
        lblTitulo.setForeground(new Color(52, 58, 70));

        panelTitulo.add(lblTitulo);

        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 28));
        panelDerecho.setOpaque(false);
        panelDerecho.setPreferredSize(new Dimension(110, 90));

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
        panelFranjaRoja.setPreferredSize(new Dimension(1080, 80));

        JPanel panelIzquierdoFranja = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 16));
        panelIzquierdoFranja.setOpaque(false);

        JLabel lblIconoMesero = new JLabel();
        ImageIcon iconoMesero = new ImageIcon("src\\main\\resources\\imagenes\\icono_usuario.png");
        Image meseroEscalado = iconoMesero.getImage().getScaledInstance(42, 42, Image.SCALE_SMOOTH);
        lblIconoMesero.setIcon(new ImageIcon(meseroEscalado));

        lblNombreMesero = new JLabel(coordinador.getMeseroActual().getNombre());
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
        panelCentro.setBorder(BorderFactory.createEmptyBorder(10, 35, 22, 35));

        JPanel panelContenido = new JPanel();
        panelContenido.setBackground(new Color(235, 235, 235));
        panelContenido.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setPreferredSize(new Dimension(980, 390));
        panelContenido.setMaximumSize(new Dimension(980, 390));

        JPanel panelInfoSuperior = new JPanel(new FlowLayout(FlowLayout.LEFT, 28, 0));
        panelInfoSuperior.setOpaque(false);

        lblFolio = crearLabelInfo("Folio:");
        lblMesa = crearLabelInfo("Mesa:");
        lblFecha = crearLabelInfo("Fecha:");
        lblEstado = crearLabelInfo("Estado:");
        lblTotal = crearLabelInfo("Total:");

        panelInfoSuperior.add(lblFolio);
        panelInfoSuperior.add(lblMesa);
        panelInfoSuperior.add(lblFecha);
        panelInfoSuperior.add(lblEstado);
        panelInfoSuperior.add(lblTotal);

        JPanel panelCliente = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 8));
        panelCliente.setOpaque(false);

        lblClienteAsociado = crearLabelInfo("Cliente asociado:");
        panelCliente.add(lblClienteAsociado);

        String[] columnas = {"Producto", "Cantidad", "Subtotal", "Nota"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDetalles = new JTable(modeloTabla);
        tblDetalles.setRowHeight(60);
        tblDetalles.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tblDetalles.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tblDetalles.getTableHeader().setBackground(colorTablaHeader);
        tblDetalles.getTableHeader().setForeground(Color.BLACK);
        tblDetalles.setSelectionBackground(new Color(245, 220, 220));
        tblDetalles.setSelectionForeground(Color.BLACK);

        JScrollPane scrollTabla = new JScrollPane(tblDetalles);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder());
        scrollTabla.setPreferredSize(new Dimension(860, 330));
        scrollTabla.setMaximumSize(new Dimension(860, 330));
        scrollTabla.getVerticalScrollBar().setUnitIncrement(16);

        JPanel panelTabla = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        panelTabla.setOpaque(false);
        panelTabla.add(scrollTabla);

        panelContenido.add(panelInfoSuperior);
        panelContenido.add(panelCliente);
        panelContenido.add(Box.createVerticalStrut(18));
        panelContenido.add(panelTabla);

        JScrollPane scrollContenido = new JScrollPane(panelContenido);
        scrollContenido.setBorder(BorderFactory.createEmptyBorder());
        scrollContenido.setPreferredSize(new Dimension(980, 390));
        scrollContenido.setMaximumSize(new Dimension(980, 390));
        scrollContenido.getVerticalScrollBar().setUnitIncrement(16);
        scrollContenido.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollContenido.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollContenido.setAlignmentX(CENTER_ALIGNMENT);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 45, 18));
        panelInferior.setBackground(new Color(235, 235, 235));
        panelInferior.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        panelInferior.setPreferredSize(new Dimension(980, 80));
        panelInferior.setMaximumSize(new Dimension(980, 80));

        btnEditar = new JButton("Editar");
        btnEditar.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btnEditar.setFocusPainted(false);
        btnEditar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEditar.setBackground(Color.WHITE);
        btnEditar.setForeground(new Color(55, 55, 55));
        btnEditar.setPreferredSize(new Dimension(210, 34));
        btnEditar.setBorder(BorderFactory.createLineBorder(colorMostaza, 1));

        btnEntregada = new JButton("Entregada");
        btnEntregada.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btnEntregada.setFocusPainted(false);
        btnEntregada.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEntregada.setBackground(Color.WHITE);
        btnEntregada.setForeground(new Color(55, 55, 55));
        btnEntregada.setPreferredSize(new Dimension(210, 34));
        btnEntregada.setBorder(BorderFactory.createLineBorder(colorMostaza, 1));

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btnCancelar.setFocusPainted(false);
        btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancelar.setBackground(Color.WHITE);
        btnCancelar.setForeground(new Color(55, 55, 55));
        btnCancelar.setPreferredSize(new Dimension(210, 34));
        btnCancelar.setBorder(BorderFactory.createLineBorder(new Color(255, 92, 92), 1));

        panelInferior.add(btnEditar);
        panelInferior.add(btnEntregada);
        panelInferior.add(btnCancelar);

        panelCentro.add(scrollContenido);
        panelCentro.add(Box.createVerticalStrut(32));
        panelCentro.add(panelInferior);

        panelPrincipal.add(panelSuperiorContenedor, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);

        eventos();
    }

    private JLabel crearLabelInfo(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(new Color(70, 70, 70));
        return label;
    }

    private void eventos() {
        btnSalir.addActionListener(e -> {
            coordinador.limpiarSesionComanda();
            coordinador.mostrarInicioSesionMesero();
            dispose();
        });

        btnRegresar.addActionListener(e -> {
            coordinador.mostrarMesas();
            dispose();
        });

        btnEditar.addActionListener(e -> {
            coordinador.mostrarEditarProductosComanda();
            dispose();
        });

        btnEntregada.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(
                    null,
                    "La comanda se marcará como entregada",
                    "Entregar comanda",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (respuesta == JOptionPane.OK_OPTION) {
                ComandaDTO comanda = coordinador.getComanda();
                comanda.setEstado(EstadoComandaDTO.ENTREGADA);
                coordinador.setComanda(comanda);
                coordinador.actualizarComanda(comanda);

                MesaDTO mesa = coordinador.getMesaSeleccionada();
                mesa.setDisponibilidad(EstadoMesaDTO.DISPONIBLE);
                coordinador.setMesaSeleccionada(mesa);
                coordinador.actualizarMesa(mesa);

                JOptionPane.showMessageDialog(null, "Comanda entregada");

                coordinador.mostrarMesas();

                dispose();

            }

        });

        btnCancelar.addActionListener(e -> {
            int respuesta = JOptionPane.showConfirmDialog(
                    null,
                    "La comanda se marcará como cancelada",
                    "Cancelar comanda",
                    JOptionPane.OK_CANCEL_OPTION, //bbotones Aceptar y Cancelar
                    JOptionPane.QUESTION_MESSAGE //icono de interrogación
            );

            if (respuesta == JOptionPane.OK_OPTION) {
                ComandaDTO comanda = coordinador.getComanda();
                comanda.setEstado(EstadoComandaDTO.CANCELADA);
                coordinador.setComanda(comanda);
                coordinador.actualizarComanda(comanda);

                MesaDTO mesa = coordinador.getMesaSeleccionada();
                mesa.setDisponibilidad(EstadoMesaDTO.DISPONIBLE);
                coordinador.setMesaSeleccionada(mesa);
                coordinador.actualizarMesa(mesa);

                JOptionPane.showMessageDialog(null, "Comanda cancelada");

                coordinador.mostrarMesas();

                dispose();

            }
        });
    }

    private void cargarDatosComanda() {
        ComandaDTO comanda = coordinador.buscarComandaAbiertaPorMesa(coordinador.getMesaSeleccionada().getNumero());

        if (coordinador.getMeseroActual() != null) {
            lblNombreMesero.setText(coordinador.getMeseroActual().getNombre());
        }

        if (comanda == null) {
            lblFolio.setText("Folio: -");
            lblMesa.setText("Mesa: -");
            lblFecha.setText("Fecha: -");
            lblEstado.setText("Estado: -");
            lblTotal.setText("Total: -");
            lblClienteAsociado.setText("Cliente asociado: -");
            return;
        }

        lblFolio.setText("Folio: " + comanda.getFolio());

        if (comanda.getMesa() != null) {
            lblMesa.setText("Mesa: " + comanda.getMesa().getNumero());
        } else {
            lblMesa.setText("Mesa: -");
        }

        if (comanda.getFecha() != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            lblFecha.setText("Fecha: " + comanda.getFecha().format(formatter));
        } else {
            lblFecha.setText("Fecha: -");
        }

        lblEstado.setText("Estado: " + comanda.getEstado());
        lblTotal.setText("Total: $" + comanda.getTotal());

        if (comanda.getCliente() != null) {
            lblClienteAsociado.setText("Cliente asociado: " + comanda.getCliente().getNombreCompleto());
        } else {
            lblClienteAsociado.setText("Cliente asociado: Cliente general");
        }

        modeloTabla.setRowCount(0);

        List<DetallePedidoDTO> detalles = comanda.getDetalles();

        if (detalles != null) {
            for (DetallePedidoDTO detalle : detalles) {
                Object[] fila = {
                    detalle.getProductoDTO() != null ? detalle.getProductoDTO().getNombre() : "",
                    detalle.getCantidad(),
                    "$" + detalle.getSubtotal(),
                    detalle.getNota() != null ? detalle.getNota() : ""
                };
                modeloTabla.addRow(fila);
            }
        }
    }
}
