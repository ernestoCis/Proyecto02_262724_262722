package pantallas;

import controlador.Coordinador;
import dtos.ClienteFrecuenteDTO;
import dtos.ComandaDTO;
import dtos.DetallePedidoDTO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;
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
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmResumenPedidoEditado extends JFrame {

    private final Coordinador coordinador;

    private JButton btnSalir;
    private JButton btnRegresar;
    private JButton btnBuscarCliente;
    private JButton btnQuitarCliente;
    private JButton btnTerminarComanda;

    private JTable tblDetalles;
    private DefaultTableModel modeloTabla;

    private JTextField txtBuscarCliente;

    private JLabel lblNombreMesero;
    private JLabel lblTotal;
    private JLabel lblClienteSeleccionado;

    private List<DetallePedidoDTO> listaDetalles;
    private List<ClienteFrecuenteDTO> listaClientes;
    private ClienteFrecuenteDTO clienteSeleccionado;

    public FrmResumenPedidoEditado(Coordinador coordinador) {
        this.coordinador = coordinador;

        ComandaDTO comanda = coordinador.getComanda();
        this.listaDetalles = (comanda != null && comanda.getDetalles() != null)
                ? comanda.getDetalles()
                : new ArrayList<>();

        this.listaClientes = coordinador.getListaClientesActual() != null
                ? coordinador.getListaClientesActual()
                : new ArrayList<>();

        if (comanda != null) {
            this.clienteSeleccionado = comanda.getCliente();
        }

        configurarVentana();
        inicializarComponentes();
        cargarTablaDetalles();
        actualizarClienteSeleccionado();
    }

    private void configurarVentana() {
        setTitle("Restaurante");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setResizable(false);
    }

    private void inicializarComponentes() {
        Color colorMostaza = new Color(229, 171, 75);
        Color colorRojo = new Color(216, 84, 78);
        Color colorFondo = new Color(239, 239, 239);
        Color colorTablaHeader = new Color(242, 205, 205);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(colorFondo);

        JPanel panelSuperiorContenedor = new JPanel(new BorderLayout());
        panelSuperiorContenedor.setPreferredSize(new Dimension(1000, 180));

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBackground(colorMostaza);
        panelSuperior.setPreferredSize(new Dimension(1000, 110));

        JLabel lblLogo = new JLabel();
        lblLogo.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 10));
        lblLogo.setPreferredSize(new Dimension(100, 90));

        ImageIcon iconoLogo = new ImageIcon("src\\main\\resources\\imagenes\\icono_restaurante.png");
        Image logoEscalado = iconoLogo.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        lblLogo.setIcon(new ImageIcon(logoEscalado));

        JPanel panelTitulo = new JPanel(new GridBagLayout());
        panelTitulo.setOpaque(false);

        JLabel lblTitulo = new JLabel("Comandas");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(52, 58, 70));

        panelTitulo.add(lblTitulo);

        JPanel panelDerecho = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 25));
        panelDerecho.setOpaque(false);
        panelDerecho.setPreferredSize(new Dimension(100, 90));

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
        panelFranjaRoja.setPreferredSize(new Dimension(1000, 68));

        JPanel panelIzquierdoFranja = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 10));
        panelIzquierdoFranja.setOpaque(false);

        JLabel lblIconoMesero = new JLabel();
        ImageIcon iconoMesero = new ImageIcon("src\\main\\resources\\imagenes\\icono_usuario.png");
        Image meseroEscalado = iconoMesero.getImage().getScaledInstance(38, 38, Image.SCALE_SMOOTH);
        lblIconoMesero.setIcon(new ImageIcon(meseroEscalado));

        lblNombreMesero = new JLabel(coordinador.getMeseroActual().getNombre());
        lblNombreMesero.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblNombreMesero.setForeground(Color.BLACK);

        panelIzquierdoFranja.add(lblIconoMesero);
        panelIzquierdoFranja.add(lblNombreMesero);

        JPanel panelDerechoFranja = new JPanel(new FlowLayout(FlowLayout.RIGHT, 18, 12));
        panelDerechoFranja.setOpaque(false);

        btnRegresar = new JButton("←");
        btnRegresar.setFont(new Font("SansSerif", Font.BOLD, 18));
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
        panelCentro.setBorder(BorderFactory.createEmptyBorder(15, 30, 20, 30));

        JPanel panelContenido = new JPanel();
        panelContenido.setBackground(new Color(235, 235, 235));
        panelContenido.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.Y_AXIS));
        panelContenido.setPreferredSize(new Dimension(900, 470));
        panelContenido.setMaximumSize(new Dimension(900, 470));

        String[] columnas = {"Producto", "Cantidad", "Subtotal", "Nota"};

        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblDetalles = new JTable(modeloTabla);
        tblDetalles.setRowHeight(55);
        tblDetalles.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tblDetalles.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tblDetalles.getTableHeader().setBackground(colorTablaHeader);
        tblDetalles.getTableHeader().setForeground(Color.BLACK);
        tblDetalles.setSelectionBackground(new Color(245, 220, 220));
        tblDetalles.setSelectionForeground(Color.BLACK);
        tblDetalles.setFillsViewportHeight(true);

        JScrollPane scrollTabla = new JScrollPane(tblDetalles);
        scrollTabla.setBorder(BorderFactory.createEmptyBorder());
        scrollTabla.setPreferredSize(new Dimension(820, 340));
        scrollTabla.setMaximumSize(new Dimension(820, 340));
        scrollTabla.getVerticalScrollBar().setUnitIncrement(16);
        scrollTabla.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollTabla.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        

        JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        panelTotal.setOpaque(false);

        lblTotal = new JLabel("Total: $0");
        lblTotal.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblTotal.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 190, 190), 1),
                BorderFactory.createEmptyBorder(4, 16, 4, 16)
        ));

        panelTotal.add(lblTotal);

        JPanel panelBuscarCliente = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelBuscarCliente.setOpaque(false);

        JLabel lblAsociar = new JLabel("Asociar cliente:");
        lblAsociar.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblAsociar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 190, 190), 1),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        txtBuscarCliente = new JTextField(28);
        txtBuscarCliente.setPreferredSize(new Dimension(360, 35));
        txtBuscarCliente.setFont(new Font("SansSerif", Font.PLAIN, 16));
        txtBuscarCliente.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));

        ponerPlaceholder();

        btnBuscarCliente = new JButton("Buscar");
        btnBuscarCliente.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnBuscarCliente.setFocusPainted(false);
        btnBuscarCliente.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscarCliente.setBackground(Color.WHITE);
        btnBuscarCliente.setPreferredSize(new Dimension(90, 35));

        panelBuscarCliente.add(lblAsociar);
        panelBuscarCliente.add(txtBuscarCliente);
        panelBuscarCliente.add(btnBuscarCliente);

        JPanel panelClienteSeleccionado = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panelClienteSeleccionado.setOpaque(false);

        lblClienteSeleccionado = new JLabel("Sin cliente asociado");
        lblClienteSeleccionado.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblClienteSeleccionado.setForeground(new Color(55, 55, 55));

        btnQuitarCliente = new JButton("Quitar");
        btnQuitarCliente.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnQuitarCliente.setFocusPainted(false);
        btnQuitarCliente.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnQuitarCliente.setBackground(Color.WHITE);
        btnQuitarCliente.setPreferredSize(new Dimension(90, 30));
        btnQuitarCliente.setEnabled(false);

        panelClienteSeleccionado.add(lblClienteSeleccionado);
        panelClienteSeleccionado.add(btnQuitarCliente);

        scrollTabla.setAlignmentX(CENTER_ALIGNMENT);
        panelContenido.add(scrollTabla);
        panelContenido.add(Box.createVerticalStrut(5));
        panelContenido.add(panelTotal);
        panelContenido.add(Box.createVerticalStrut(5));
        panelContenido.add(panelBuscarCliente);
        panelContenido.add(Box.createVerticalStrut(5));
        panelContenido.add(panelClienteSeleccionado);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        panelInferior.setBackground(new Color(235, 235, 235));
        panelInferior.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(210, 210, 210), 1),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        panelInferior.setPreferredSize(new Dimension(900, 65));
        panelInferior.setMaximumSize(new Dimension(900, 65));

        btnTerminarComanda = new JButton("Terminar comanda");
        btnTerminarComanda.setFont(new Font("SansSerif", Font.PLAIN, 18));
        btnTerminarComanda.setFocusPainted(false);
        btnTerminarComanda.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTerminarComanda.setBackground(Color.WHITE);
        btnTerminarComanda.setForeground(new Color(55, 55, 55));
        btnTerminarComanda.setPreferredSize(new Dimension(180, 34));
        btnTerminarComanda.setBorder(BorderFactory.createLineBorder(colorMostaza, 1));

        panelInferior.add(btnTerminarComanda);

        panelCentro.add(panelContenido);
        panelCentro.add(Box.createVerticalStrut(30));
        panelCentro.add(panelInferior);

        panelPrincipal.add(panelSuperiorContenedor, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);

        eventos();
    }

    private void eventos() {
        btnSalir.addActionListener(e -> {
            coordinador.mostrarInicioSesionMesero();
            dispose();
        });

        btnRegresar.addActionListener(e -> {
            coordinador.mostrarEditarProductosComanda();
            dispose();
        });

        btnBuscarCliente.addActionListener(e -> {
            accionBuscarCliente();
        });

        txtBuscarCliente.addActionListener(e -> {
            accionBuscarCliente();
        });

        btnQuitarCliente.addActionListener(e -> {
            clienteSeleccionado = null;
            actualizarClienteSeleccionado();
            txtBuscarCliente.setText("Buscar por nombre, teléfono o correo");
            txtBuscarCliente.setForeground(Color.GRAY);
        });

        btnTerminarComanda.addActionListener(e -> {
            ComandaDTO comanda = coordinador.getComanda();

            if (comanda != null) {
                double total = 0;
                for (DetallePedidoDTO detalle : listaDetalles) {
                    total += detalle.getSubtotal();
                }

                comanda.setCliente(clienteSeleccionado);
                comanda.setDetalles(listaDetalles);
                comanda.setTotal(total);

                coordinador.setComanda(comanda);
            }

            coordinador.actualizarComanda(coordinador.getComanda());

            coordinador.mostrarEstadosComanda();
            dispose();
        });
    }

    private void cargarTablaDetalles() {
        modeloTabla.setRowCount(0);
        double total = 0;

        for (DetallePedidoDTO detalle : listaDetalles) {
            Object[] fila = {
                detalle.getProductoDTO().getNombre(),
                detalle.getCantidad(),
                "$" + detalle.getSubtotal(),
                detalle.getNota() == null ? "" : detalle.getNota()
            };
            modeloTabla.addRow(fila);
            total += detalle.getSubtotal();
        }

        lblTotal.setText("Total: $" + total);
    }

    private void accionBuscarCliente() {
        String texto = txtBuscarCliente.getText().trim().toLowerCase();

        if (texto.isEmpty() || texto.equals("buscar por nombre, teléfono o correo")) {
            JOptionPane.showMessageDialog(this, "Escribe un nombre, teléfono o correo para buscar.");
            return;
        }

        if (listaClientes == null || listaClientes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay clientes cargados para buscar.");
            return;
        }

        for (ClienteFrecuenteDTO cliente : listaClientes) {
            String nombre = cliente.getNombreCompleto() != null ? cliente.getNombreCompleto().toLowerCase() : "";
            String telefono = cliente.getTelefono() != null ? cliente.getTelefono().toLowerCase() : "";
            String correo = cliente.getEmail() != null ? cliente.getEmail().toLowerCase() : "";

            if (nombre.contains(texto) || telefono.contains(texto) || correo.contains(texto)) {
                clienteSeleccionado = cliente;
                actualizarClienteSeleccionado();
                return;
            }
        }

        clienteSeleccionado = null;
        actualizarClienteSeleccionado();
        JOptionPane.showMessageDialog(this, "No se encontró ningún cliente con ese criterio.");
    }

    private void actualizarClienteSeleccionado() {
        if (clienteSeleccionado == null) {
            lblClienteSeleccionado.setText("Sin cliente asociado");
            btnQuitarCliente.setEnabled(false);
        } else {
            lblClienteSeleccionado.setText(
                    clienteSeleccionado.getNombreCompleto()
                    + " | Tel: " + clienteSeleccionado.getTelefono()
                    + " | Correo: " + clienteSeleccionado.getEmail()
            );
            btnQuitarCliente.setEnabled(true);
        }
    }

    private void ponerPlaceholder() {
        txtBuscarCliente.setText("Buscar por nombre, teléfono o correo");
        txtBuscarCliente.setForeground(Color.GRAY);

        txtBuscarCliente.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (txtBuscarCliente.getText().equals("Buscar por nombre, teléfono o correo")) {
                    txtBuscarCliente.setText("");
                    txtBuscarCliente.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (txtBuscarCliente.getText().trim().isEmpty()) {
                    txtBuscarCliente.setText("Buscar por nombre, teléfono o correo");
                    txtBuscarCliente.setForeground(Color.GRAY);
                }
            }
        });
    }
}
