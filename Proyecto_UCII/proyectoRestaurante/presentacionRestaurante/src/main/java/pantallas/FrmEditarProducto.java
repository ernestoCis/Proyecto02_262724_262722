package pantallas;

import componentes.BotonEditar;
import componentes.BotonEstilizado;
import componentes.BotonRegresar;
import componentes.BotonX;
import componentes.PanelRedondeado;
import controlador.Coordinador;
import dtos.IngredienteDTO;
import dtos.ProductoDTO;
import dtos.RecetaDTO;
import enums.DisponibilidadProductoDTO;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utilerias.Validacion;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmEditarProducto extends JFrame {

    private final Coordinador coordinador;

    private JTextField txtNombre;
    private JTextField txtPrecio;
    private JTextField txtDescripcion;
    private JComboBox<String> comboTipo;

    private JTable tablaIngredientes;
    private DefaultTableModel modeloTabla;

    private JLabel lblImagen;
    private String rutaImagen;

    private BotonEstilizado btnGuardar;
    private BotonEstilizado btnAgregarIngrediente;
    private BotonEstilizado btnEliminarIngrediente;
    private BotonEstilizado btnSeleccionarImagen;
    private BotonRegresar btnRegresar;
    private BotonEditar btnDisponibilidad;
    private JButton btnQuitarImagen;

    private ProductoDTO producto;

    private List<RecetaDTO> recetas = new ArrayList<>();

    public FrmEditarProducto(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.producto = coordinador.getProductoSeleccionado();

        configurarVentana();
        inicializarComponentes();
        cargarDatos();
    }

    private void configurarVentana() {
        setTitle("Restaurante");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    private void inicializarComponentes() {

        Color colorMostaza = new Color(229, 171, 75);
        Color colorFondo = new Color(239, 239, 239);
        Color colorPanel = new Color(235, 235, 235);
        Color colorTexto = new Color(55, 55, 55);

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

        JLabel lblTitulo = new JLabel("Productos");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 36));
        lblTitulo.setForeground(new Color(52, 58, 70));

        panelTitulo.add(lblTitulo);

        panelSuperior.add(lblLogo, BorderLayout.WEST);
        panelSuperior.add(panelTitulo, BorderLayout.CENTER);
        panelSuperiorContenedor.add(panelSuperior, BorderLayout.CENTER);

        // CENTRO
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.setBackground(colorFondo);
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 35, 30, 35));

        btnRegresar = new BotonRegresar();

        JPanel panelArriba = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelArriba.setOpaque(false);
        panelArriba.add(btnRegresar);

        PanelRedondeado panelFormulario = new PanelRedondeado(30);
        panelFormulario.setLayout(new BorderLayout());
        panelFormulario.setBackground(colorPanel);
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel lblSubtitulo = new JLabel("Editar Producto");
        lblSubtitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        lblSubtitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblSubtitulo.setForeground(colorTexto);

        btnDisponibilidad = new BotonEditar("");
        btnDisponibilidad.setPreferredSize(new Dimension(160, 40));
        actualizarTextoDisponibilidad();

        JPanel panelHeaderFormulario = new JPanel(new BorderLayout());
        panelHeaderFormulario.setOpaque(false);

        panelHeaderFormulario.add(lblSubtitulo, BorderLayout.WEST);
        panelHeaderFormulario.add(btnDisponibilidad, BorderLayout.EAST);

        // CONTENIDO
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.X_AXIS));
        panelContenido.setOpaque(false);

        // CAMPOS
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));
        panelCampos.setOpaque(false);

        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        txtDescripcion = new JTextField();
        comboTipo = new JComboBox<>(new String[]{"PLATILLO", "BEBIDA", "POSTRE"});

        txtNombre.setEditable(false);
        comboTipo.setEnabled(false);

        panelCampos.add(crearCampo("Nombre", txtNombre));
        panelCampos.add(Box.createVerticalStrut(8));
        panelCampos.add(crearCampo("Precio", txtPrecio));
        panelCampos.add(Box.createVerticalStrut(8));
        panelCampos.add(crearCombo("Tipo", comboTipo));
        panelCampos.add(Box.createVerticalStrut(8));
        panelCampos.add(crearCampo("Descripcion", txtDescripcion));

        // IMAGEN
        JPanel panelImagen = new JPanel();
        panelImagen.setLayout(new BoxLayout(panelImagen, BoxLayout.Y_AXIS));
        panelImagen.setOpaque(false);

        lblImagen = new JLabel();
        lblImagen.setPreferredSize(new Dimension(170, 165));
        lblImagen.setMinimumSize(new Dimension(170, 165));
        lblImagen.setMaximumSize(new Dimension(170, 165));
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2, true));
        lblImagen.setAlignmentX(Component.CENTER_ALIGNMENT);

        ponerImagenDefault();

        // botones
        btnSeleccionarImagen = new BotonEstilizado("Imagen");
        btnSeleccionarImagen.setPreferredSize(new Dimension(120, 30));

        btnQuitarImagen = new BotonX("X");
        btnQuitarImagen.setPreferredSize(new Dimension(50, 30));
        btnQuitarImagen.setForeground(Color.WHITE);

        btnQuitarImagen.setVisible(false);

        // panel botones en horizontal
        JPanel panelBotonesImagen = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        panelBotonesImagen.setOpaque(false);
        panelBotonesImagen.add(btnSeleccionarImagen);
        panelBotonesImagen.add(btnQuitarImagen);

        panelImagen.add(lblImagen);
        panelImagen.add(Box.createVerticalStrut(10));
        panelImagen.add(panelBotonesImagen);

        btnQuitarImagen.addActionListener(e -> {
            rutaImagen = null;
            ponerImagenDefault();
            btnQuitarImagen.setVisible(false);
        });

        // INGREDIENTES 
        JPanel panelIngredientes = new JPanel(new BorderLayout());
        panelIngredientes.setOpaque(false);
        panelIngredientes.setPreferredSize(new Dimension(400, 350));

        JLabel lblIngredientes = new JLabel("Ingredientes");
        lblIngredientes.setFont(new Font("SansSerif", Font.BOLD, 18));

        modeloTabla = new DefaultTableModel(
                new String[]{"Nombre", "Unidad", "Cantidad"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tablaIngredientes = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaIngredientes);

        ((DefaultTableCellRenderer) tablaIngredientes.getTableHeader()
                .getDefaultRenderer())
                .setHorizontalAlignment(SwingConstants.CENTER);

        tablaIngredientes.setSelectionBackground(Color.decode("#2A4E52"));

        btnAgregarIngrediente = new BotonEstilizado("+");
        btnEliminarIngrediente = new BotonEstilizado("−");

        btnAgregarIngrediente.setPreferredSize(new Dimension(60, 45));
        btnEliminarIngrediente.setPreferredSize(new Dimension(60, 45));

        // BOTONES 
        JPanel panelBottomTabla = new JPanel(new BorderLayout());
        panelBottomTabla.setOpaque(false);

        panelBottomTabla.add(btnAgregarIngrediente, BorderLayout.WEST);
        panelBottomTabla.add(btnEliminarIngrediente, BorderLayout.EAST);

        panelIngredientes.add(lblIngredientes, BorderLayout.NORTH);
        panelIngredientes.add(scroll, BorderLayout.CENTER);
        panelIngredientes.add(panelBottomTabla, BorderLayout.SOUTH);

        // UNIÓN
        panelContenido.add(panelCampos);
        panelContenido.add(Box.createHorizontalStrut(30));
        panelContenido.add(panelImagen);
        panelContenido.add(Box.createHorizontalStrut(30));
        panelContenido.add(panelIngredientes);

        // BOTÓN
        btnGuardar = new BotonEstilizado("Guardar");

        JPanel panelBoton = new JPanel();
        panelBoton.setOpaque(false);
        panelBoton.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        panelBoton.add(btnGuardar);

        panelFormulario.add(panelHeaderFormulario, BorderLayout.NORTH);
        panelFormulario.add(panelContenido, BorderLayout.CENTER);
        panelFormulario.add(panelBoton, BorderLayout.SOUTH);

        panelCentro.add(panelArriba, BorderLayout.NORTH);
        panelCentro.add(panelFormulario, BorderLayout.CENTER);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);

        eventos();
    }

    private void actualizarTextoDisponibilidad() {
        if (producto != null) {
            btnDisponibilidad.setText(
                    producto.getDisponibilidad().toString().replace("_", " ")
            );
        }
    }

    // CARGAR DATOS
    private void cargarDatos() {
        if (producto == null) {
            return;
        }

        txtNombre.setText(producto.getNombre());
        txtPrecio.setText(String.valueOf(producto.getPrecio()));
        comboTipo.setSelectedItem(producto.getTipo().toString());
        txtDescripcion.setText(producto.getDescripcion());

        if (producto.getRutaImagen() != null) {
            ImageIcon icono = new ImageIcon(producto.getRutaImagen());

            Dimension size = lblImagen.getPreferredSize();

            Image img = icono.getImage().getScaledInstance(
                    size.width,
                    size.height,
                    Image.SCALE_SMOOTH
            );

            lblImagen.setText("");
            lblImagen.setIcon(new ImageIcon(img));
            rutaImagen = producto.getRutaImagen();
            btnQuitarImagen.setVisible(true);
        } else {
            ponerImagenDefault();
        }

        // ingre
        if (producto.getRecetas() != null) {
            recetas.clear();
            modeloTabla.setRowCount(0);

            for (RecetaDTO receta : producto.getRecetas()) {
                recetas.add(receta);

                modeloTabla.addRow(new Object[]{
                    receta.getIngrediente().getNombre(),
                    receta.getIngrediente().getUnidadMedida(),
                    receta.getCantidad()
                });
            }
        }
    }

    private void ponerImagenDefault() {
        ImageIcon icono = new ImageIcon("src\\main\\resources\\imagenes\\icono_sin_imagen.png");
        Dimension size = lblImagen.getSize();

        if (size.width == 0 || size.height == 0) {
            size = new Dimension(170, 165);
        }

        Image img = icono.getImage().getScaledInstance(
                size.width,
                size.height,
                Image.SCALE_SMOOTH
        );

        lblImagen.setIcon(new ImageIcon(img));
        lblImagen.setText("");
        rutaImagen = null;
    }

    private JPanel crearCampo(String texto, JTextField campo) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));

        campo.setMaximumSize(new Dimension(250, 20));
        campo.setPreferredSize(new Dimension(250, 20));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setOpaque(false);

        contenedor.add(label);
        contenedor.add(Box.createVerticalStrut(5));
        contenedor.add(campo);

        return contenedor;
    }

    private JPanel crearCombo(String texto, JComboBox<String> combo) {
        JLabel label = new JLabel(texto);
        label.setFont(new Font("SansSerif", Font.BOLD, 16));

        Dimension size = new Dimension(250, 20);

        combo.setPreferredSize(size);
        combo.setMaximumSize(size);
        combo.setMinimumSize(size);

        combo.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel contenedor = new JPanel();
        contenedor.setLayout(new BoxLayout(contenedor, BoxLayout.Y_AXIS));
        contenedor.setOpaque(false);

        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        contenedor.add(label);
        contenedor.add(Box.createVerticalStrut(5));
        contenedor.add(combo);

        return contenedor;
    }

    private void eventos() {

        btnRegresar.addActionListener(e -> {
            dispose();
            coordinador.mostrarProductos();
        });

        btnSeleccionarImagen.addActionListener(e -> seleccionarImagen());

        btnAgregarIngrediente.addActionListener(e -> {
            FrmIngredientes frm = new FrmIngredientes(
                    coordinador,
                    true,
                    ingredientesSeleccionados -> {

                        for (IngredienteDTO ingrediente : ingredientesSeleccionados) {

                            String cantidadStr = JOptionPane.showInputDialog(
                                    "Cantidad para " + ingrediente.getNombre()
                            );

                            if (cantidadStr == null || cantidadStr.trim().isEmpty()) {
                                return false;
                            }

                            String unidad = ingrediente.getUnidadMedida().toString();

                            if (!Validacion.esCantidadValida(cantidadStr, unidad)) {
                                JOptionPane.showMessageDialog(this, "Cantidad inválida");
                                return false;
                            }

                            double cantidad = Double.parseDouble(cantidadStr);

                            agregarIngredienteATabla(ingrediente, cantidad);
                        }

                        return true;
                    });
            frm.setVisible(true);
        });

        btnEliminarIngrediente.addActionListener(e -> {
            int fila = tablaIngredientes.getSelectedRow();

            if (fila == -1) {
                JOptionPane.showMessageDialog(this,
                        "Seleccione un ingrediente para eliminar");
                return;
            }

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea eliminar el ingrediente seleccionado?",
                    "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION
            );

            if (opcion == JOptionPane.YES_OPTION) {
                modeloTabla.removeRow(fila);
                recetas.remove(fila);
            }
        });

        btnGuardar.addActionListener(e -> {

            String precioTxt = txtPrecio.getText();

            if (!Validacion.esPrecioValido(precioTxt)) {
                JOptionPane.showMessageDialog(this, "Precio inválido (máx 3 decimales)");
                return;
            }

            Double precio;
            try {
                precio = Double.valueOf(precioTxt);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El precio no es un número válido");
                return;
            }
            int confirmacion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Deseas confirmar la actualización del producto?",
                    "Confirmación",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmacion == JOptionPane.YES_OPTION) {
                producto.setPrecio(precio);
                producto.setDescripcion(txtDescripcion.getText());
                producto.setRecetas(recetas);
                producto.setRutaImagen(rutaImagen);
                producto.setDisponibilidad(producto.getDisponibilidad());

                coordinador.actualizarProducto(producto);

                // coordinador.mostrarProductos();
            }
        });

        btnDisponibilidad.addActionListener(e -> {
            if (producto == null) {
                return;
            }

            DisponibilidadProductoDTO estadoActual = producto.getDisponibilidad();

            DisponibilidadProductoDTO nuevoEstado
                    = (estadoActual == DisponibilidadProductoDTO.DISPONIBLE)
                            ? DisponibilidadProductoDTO.NO_DISPONIBLE
                            : DisponibilidadProductoDTO.DISPONIBLE;

            if (nuevoEstado == DisponibilidadProductoDTO.DISPONIBLE) {
                if (!coordinador.verificarStock(producto, 1)) {
                    JOptionPane.showMessageDialog(this,
                            "No hay suficiente stock para habilitar el producto");
                    return;
                }
            }

            String textoMostrar = nuevoEstado.toString().replace("_", " ");

            int opcion = JOptionPane.showConfirmDialog(
                    this,
                    "¿Desea cambiar el estado del producto a " + textoMostrar + "?",
                    "Confirmar cambio",
                    JOptionPane.YES_NO_OPTION
            );
            if (opcion == JOptionPane.YES_OPTION) {
                try {

                    coordinador.cambiarDisponibilidadProducto(producto.getIdProducto(), nuevoEstado);

                    producto.setDisponibilidad(nuevoEstado);
                    actualizarTextoDisponibilidad();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }

        });

        tablaIngredientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int fila = tablaIngredientes.getSelectedRow();
                    int columna = tablaIngredientes.getSelectedColumn();

                    if (fila != -1 && columna == 2) {
                        String valorActual = modeloTabla.getValueAt(fila, columna).toString();

                        String nuevaCantidad = JOptionPane.showInputDialog(
                                FrmEditarProducto.this,
                                "Nueva cantidad:",
                                valorActual
                        );

                        if (nuevaCantidad != null) {

                            // Obtener unidad del ingrediente 
                            String unidad = modeloTabla.getValueAt(fila, 1).toString();

                            if (!Validacion.esCantidadValida(nuevaCantidad, unidad)) {
                                JOptionPane.showMessageDialog(
                                        FrmEditarProducto.this,
                                        "Cantidad inválida para la unidad " + unidad
                                );
                                return;
                            }

                            try {
                                Double cantidad = Double.valueOf(nuevaCantidad);
                                modeloTabla.setValueAt(cantidad, fila, columna);
                                recetas.get(fila).setCantidad(cantidad);

                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(
                                        FrmEditarProducto.this,
                                        "Cantidad inválida"
                                );
                            }
                        }
                    }
                }
            }
        });
    }

    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            java.io.File archivo = fileChooser.getSelectedFile();
            rutaImagen = archivo.getAbsolutePath();

            ImageIcon icono = new ImageIcon(rutaImagen);
            Dimension size = lblImagen.getPreferredSize();

            Image img = icono.getImage().getScaledInstance(
                    size.width,
                    size.height,
                    Image.SCALE_SMOOTH
            );

            lblImagen.setText("");
            lblImagen.setIcon(new ImageIcon(img));

            btnQuitarImagen.setVisible(true);
            btnQuitarImagen.repaint();
            btnQuitarImagen.revalidate();
        }
    }

    public boolean agregarIngredienteATabla(IngredienteDTO ingrediente, Double cantidad) {

        for (RecetaDTO r : recetas) {
            if (r.getIngrediente().getIdIngrediente()
                    .equals(ingrediente.getIdIngrediente())) {
                JOptionPane.showMessageDialog(this,
                        "Este ingrediente ya fue agregado");
                return false;
            }
        }

        modeloTabla.addRow(new Object[]{
            ingrediente.getNombre(),
            ingrediente.getUnidadMedida(),
            cantidad
        });

        RecetaDTO receta = new RecetaDTO();
        receta.setIngrediente(ingrediente);
        receta.setCantidad(cantidad);
        recetas.add(receta);

        return true;
    }
}
