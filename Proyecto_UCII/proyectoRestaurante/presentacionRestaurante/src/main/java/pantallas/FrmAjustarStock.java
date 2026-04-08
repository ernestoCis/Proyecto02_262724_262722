package pantallas;

import componentes.BotonEstilizado;
import componentes.BotonRegresar;
import componentes.PanelRedondeado;
import controlador.Coordinador;
import dtos.IngredienteDTO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

/**
 * 
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmAjustarStock extends JFrame {

    private final Coordinador coordinador;
    private final IngredienteDTO ingrediente;

    private JLabel lblNombre;
    private JLabel lblStock;
    private JLabel lblImagen;
    private JTextField txtCantidad;
    private JRadioButton rbAgregar;
    private JRadioButton rbDescontar;
    private BotonEstilizado btnGuardar;
    private BotonRegresar btnRegresar;

    public FrmAjustarStock(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.ingrediente = coordinador.getIngredienteSeleccionado();

        configurarVentana();
        inicializarComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setTitle("Ajustar Stock");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {

        Color colorMostaza = new Color(229, 171, 75);
        Color colorFondo = new Color(239, 239, 239);

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

        JLabel lblTitulo = new JLabel("Ingredientes");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(52, 58, 70));
        panelTitulo.add(lblTitulo);

        panelSuperior.add(lblLogo, BorderLayout.WEST);
        panelSuperior.add(panelTitulo, BorderLayout.CENTER);
        panelSuperiorContenedor.add(panelSuperior, BorderLayout.CENTER);

        btnRegresar = new BotonRegresar();

        JPanel panelRegresar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelRegresar.setBackground(colorFondo);
        panelRegresar.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));
        panelRegresar.add(btnRegresar);

        JPanel contenedorSuperior = new JPanel(new BorderLayout());
        contenedorSuperior.add(panelSuperior, BorderLayout.NORTH);
        contenedorSuperior.add(panelRegresar, BorderLayout.CENTER);

        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(colorFondo);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(30, 80, 40, 80));

        PanelRedondeado panelFormulario = new PanelRedondeado(30);
        panelFormulario.setLayout(new BorderLayout());
        panelFormulario.setBackground(new Color(235, 235, 235));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JLabel lblSubtitulo = new JLabel("Editar Ingrediente");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblSubtitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 25, 0));

        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.X_AXIS));
        panelContenido.setOpaque(false);

        JPanel panelImagen = new JPanel();
        panelImagen.setLayout(new BoxLayout(panelImagen, BoxLayout.Y_AXIS));
        panelImagen.setOpaque(false);

        lblImagen = new JLabel("Click para agregar imagen");
        lblImagen.setPreferredSize(new Dimension(200, 180));
        lblImagen.setMaximumSize(new Dimension(200, 180));
        lblImagen.setMinimumSize(new Dimension(200, 180));

        lblImagen.setHorizontalAlignment(SwingConstants.CENTER);
        lblImagen.setVerticalAlignment(SwingConstants.CENTER);

        lblImagen.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180), 2, true));
        lblImagen.setCursor(new Cursor(Cursor.HAND_CURSOR));

        panelImagen.add(lblImagen);

        JPanel panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setOpaque(false);

        lblNombre = new JLabel("Ingrediente");
        lblNombre.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblNombre.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblStock = new JLabel("Stock");
        lblStock.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        //lblStock.setForeground(new Color(90, 90, 90));
        lblStock.setAlignmentX(Component.LEFT_ALIGNMENT);

        rbAgregar = new JRadioButton("Agregar");
        rbDescontar = new JRadioButton("Descontar");

        rbAgregar.setOpaque(false);
        rbDescontar.setOpaque(false);

        ButtonGroup grupo = new ButtonGroup();
        grupo.add(rbAgregar);
        grupo.add(rbDescontar);
        rbAgregar.setSelected(true);

        rbAgregar.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        rbDescontar.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JPanel panelRadios = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelRadios.setOpaque(false);
        panelRadios.setAlignmentX(Component.LEFT_ALIGNMENT);
        panelRadios.add(rbAgregar);
        panelRadios.add(rbDescontar);

        JLabel lblCantidad = new JLabel("Cantidad");
        lblCantidad.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblCantidad.setAlignmentX(Component.LEFT_ALIGNMENT);

        txtCantidad = new JTextField();
        txtCantidad.setMaximumSize(new Dimension(200, 40));
        txtCantidad.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        txtCantidad.setAlignmentX(Component.LEFT_ALIGNMENT);

        panelInfo.add(lblNombre);
        panelInfo.add(Box.createVerticalStrut(10));
        panelInfo.add(lblStock);
        panelInfo.add(Box.createVerticalStrut(20));
        panelInfo.add(panelRadios);
        panelInfo.add(Box.createVerticalStrut(20));
        panelInfo.add(lblCantidad);
        panelInfo.add(Box.createVerticalStrut(5));
        panelInfo.add(txtCantidad);

        panelContenido.add(panelImagen);
        panelContenido.add(Box.createHorizontalStrut(40));
        panelContenido.add(panelInfo);

        btnGuardar = new BotonEstilizado("Guardar");

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBoton.setOpaque(false);
        panelBoton.add(btnGuardar);

        panelFormulario.add(lblSubtitulo, BorderLayout.NORTH);
        panelFormulario.add(panelContenido, BorderLayout.CENTER);
        panelFormulario.add(panelBoton, BorderLayout.SOUTH);

        panelCentro.add(panelFormulario, BorderLayout.CENTER);

        panelPrincipal.add(contenedorSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);

        eventos();
    }

    private void cargarDatos() {
        if (ingrediente == null) {
            return;
        }

        lblNombre.setText("Ingrediente: " + ingrediente.getNombre());
        lblStock.setText("Stock: "
                + String.format("%.2f", ingrediente.getCantidadActual()) + " "
                + ingrediente.getUnidadMedida());

        cargarImagen();
    }

    private void cargarImagen() {
        try {
            if (ingrediente.getRutaImagen() != null) {
                ImageIcon icono = new ImageIcon(ingrediente.getRutaImagen());

                Image imgEscalada = icono.getImage().getScaledInstance(
                        lblImagen.getWidth(),
                        lblImagen.getHeight(),
                        Image.SCALE_SMOOTH
                );

                lblImagen.setText("");
                lblImagen.setIcon(new ImageIcon(imgEscalada));
            }
        } catch (Exception e) {
        }
    }

    private void eventos() {
        btnGuardar.addActionListener(e -> guardar());

        btnRegresar.addActionListener(e -> {
            dispose();
            coordinador.mostrarIngredientes();
        });

        lblImagen.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                seleccionarImagen();
            }
        });
    }

    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Seleccionar imagen");

        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            try {
                String ruta = fileChooser.getSelectedFile().getAbsolutePath();
                ImageIcon icono = new ImageIcon(ruta);

                Image imgEscalada = icono.getImage().getScaledInstance(
                        lblImagen.getWidth(),
                        lblImagen.getHeight(),
                        Image.SCALE_SMOOTH
                );

                lblImagen.setText("");
                lblImagen.setIcon(new ImageIcon(imgEscalada));

                ingrediente.setRutaImagen(ruta);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar imagen");
            }
        }
    }

    private void guardar() {
        try {
            double cantidad = Double.parseDouble(txtCantidad.getText());
            boolean agregar = rbAgregar.isSelected();

            String accion = agregar ? "agregar" : "descontar";

            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Seguro que deseas " + accion + " " + cantidad + " "
                    + ingrediente.getUnidadMedida() + " de "
                    + ingrediente.getNombre() + "?",
                    "Confirmar acción",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion != JOptionPane.YES_OPTION) {
                return;
            }

            coordinador.ajustarStock(
                    ingrediente.getIdIngrediente(),
                    cantidad,
                    agregar
            );

            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Cantidad inválida");
        }
    }
}
