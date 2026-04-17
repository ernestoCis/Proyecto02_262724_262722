package pantallas;

import componentes.BotonEstilizado;
import componentes.BotonRegresar;
import componentes.BotonX;
import componentes.PanelRedondeado;
import controlador.Coordinador;
import dtos.IngredienteDTO;
import dtos.ProductoDTO;
import dtos.RecetaDTO;
import enums.DisponibilidadProductoDTO;
import enums.TipoProductoDTO;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import utilerias.Validacion;

/**
 * Pantalla para el registro de nuevos productos en el sistema del restaurante.
 * <p>
 * Permite definir datos generales del producto, asociar una imagen y construir
 * la receta seleccionando ingredientes y cantidades específicas.</p>
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmRegistrarProducto extends JFrame {

    /**
     * Controlador principal para la gestión de flujos de navegación y
     * persistencia.
     */
    private final Coordinador coordinador;

    /**
     * Campos de texto para la información básica del producto.
     */
    private JTextField txtNombre, txtPrecio, txtDescripcion;
    /**
     * Selector para la categoría del producto (Platillo, Bebida, Postre).
     */
    private JComboBox<String> comboTipo;

    /**
     * Tabla para la gestión visual de los ingredientes de la receta.
     */
    private JTable tablaIngredientes;
    /**
     * Modelo para la gestión visual de los ingredientes de la receta.
     */
    private DefaultTableModel modeloTabla;

    /**
     * Etiqueta para previsualizar la imagen del producto.
     */
    private JLabel lblImagen;
    /**
     * Ruta absoluta del archivo de imagen seleccionado en el equipo.
     */
    private String rutaImagen;

    /**
     * Botones para acciones de registro, gestión de imagen y navegación.
     */
    private BotonEstilizado btnRegistrar, btnAgregarIngrediente, btnEliminarIngrediente, btnSeleccionarImagen;
    /**
     * Boton para navegación.
     */
    private BotonRegresar btnRegresar;
    /**
     * Boton para gestión de imagen.
     */
    private JButton btnQuitarImagen;

    /**
     * Lista de objetos DTO que conforman la receta del producto actual.
     */
    private List<RecetaDTO> recetas = new ArrayList<>();

    /**
     * Constructor que inicializa la interfaz de registro de productos.
     *
     * @param coordinador El coordinador del sistema para la comunicación entre
     * capas.
     */
    public FrmRegistrarProducto(Coordinador coordinador) {
        this.coordinador = coordinador;
        configurarVentana();
        inicializarComponentes();
    }

    /**
     * Establece las propiedades de la ventana (tamaño, título, posición).
     */
    private void configurarVentana() {
        setTitle("Restaurante");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }

    /**
     * Inicializa los componentes gráficos y define la estructura del
     * formulario.
     * <p>
     * Organiza los campos en tres secciones: datos generales, imagen e
     * ingredientes.</p>
     */
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

        JLabel lblSubtitulo = new JLabel("Registrar Producto");
        lblSubtitulo.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        lblSubtitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        lblSubtitulo.setForeground(colorTexto);

        // CONTENIDO
        JPanel panelContenido = new JPanel();
        panelContenido.setLayout(new BoxLayout(panelContenido, BoxLayout.X_AXIS));
        panelContenido.setOpaque(false);

        // FORMULARIO
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new BoxLayout(panelCampos, BoxLayout.Y_AXIS));
        panelCampos.setOpaque(false);

        txtNombre = new JTextField();
        txtPrecio = new JTextField();
        comboTipo = new JComboBox<>(new String[]{"PLATILLO", "BEBIDA", "POSTRE"});
        txtDescripcion = new JTextField();

        panelCampos.add(crearCampo("Nombre", txtNombre));
        panelCampos.add(Box.createVerticalStrut(8));
        panelCampos.add(crearCampo("Precio", txtPrecio));
        panelCampos.add(Box.createVerticalStrut(8));
        panelCampos.add(crearCombo("Tipo", comboTipo));
        panelCampos.add(Box.createVerticalStrut(8));
        panelCampos.add(crearCampo("Descripción", txtDescripcion));

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
        btnRegistrar = new BotonEstilizado("Registrar");

        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        panelBoton.setOpaque(false);
        panelBoton.add(btnRegistrar);

        panelFormulario.add(lblSubtitulo, BorderLayout.NORTH);
        panelFormulario.add(panelContenido, BorderLayout.CENTER);
        panelFormulario.add(panelBoton, BorderLayout.SOUTH);

        panelCentro.add(panelArriba, BorderLayout.NORTH);
        panelCentro.add(panelFormulario, BorderLayout.CENTER);

        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);

        eventos();
    }

    /**
     * Restablece el icono por defecto cuando no hay una imagen seleccionada.
     */
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

    /**
     * Crea un panel con una etiqueta y un campo de texto alineados
     * verticalmente.
     *
     * @param texto Etiqueta descriptiva.
     * @param campo Componente de entrada de texto.
     * @return <code>JPanel</code> configurado.
     */
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

    /**
     * Crea un panel con una etiqueta y un selector alineados verticalmente.
     *
     * @param texto Etiqueta descriptiva.
     * @param combo Componente de selección.
     * @return <code>JPanel</code> configurado.
     */
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

    /**
     * Configura los escuchas de eventos (ActionListeners y MouseListeners) de
     * la pantalla.
     * <p>
     * Incluye la lógica para editar cantidades de ingredientes mediante doble
     * clic en la tabla.</p>
     */
    private void eventos() {

        btnRegresar.addActionListener(e -> {
            dispose();
            coordinador.mostrarProductosAdmin();
        });

        btnSeleccionarImagen.addActionListener(e -> seleccionarImagen());

        btnAgregarIngrediente.addActionListener(e -> {
            coordinador.abrirBuscadorIngredientesParaProducto(this);
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

        btnRegistrar.addActionListener(e -> registrarProducto());

        tablaIngredientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int fila = tablaIngredientes.getSelectedRow();
                    if (fila != -1) {

                        int columnaCantidad = 2;

                        String valorActual = modeloTabla.getValueAt(fila, columnaCantidad).toString();

                        String nuevaCantidad = JOptionPane.showInputDialog(
                                FrmRegistrarProducto.this,
                                "Nueva cantidad:",
                                valorActual
                        );

                        if (nuevaCantidad != null) {

                            String unidad = modeloTabla.getValueAt(fila, 1).toString();

                            if (!Validacion.esCantidadValida(nuevaCantidad, unidad)) {
                                JOptionPane.showMessageDialog(
                                        FrmRegistrarProducto.this,
                                        unidad.equalsIgnoreCase("Piezas")
                                        ? "Solo se permiten números enteros para piezas"
                                        : "Cantidad inválida"
                                );
                                return;
                            }

                            try {
                                Double cantidad = Double.valueOf(nuevaCantidad);

                                modeloTabla.setValueAt(cantidad, fila, columnaCantidad);

                                recetas.get(fila).setCantidad(cantidad);

                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(
                                        FrmRegistrarProducto.this,
                                        "Cantidad inválida"
                                );
                            }
                        }
                    }
                }
            }
        });
    }

    /**
     * Valida los datos del formulario y solicita al coordinador el registro del
     * producto.
     * <p>
     * Asigna por defecto la disponibilidad como "DISPONIBLE" al crear un
     * producto nuevo.</p>
     */
    private void registrarProducto() {

        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String precioTxt = txtPrecio.getText().trim();
        String tipoTxt = comboTipo.getSelectedItem().toString();

        // VALIDACIONES
        if (!Validacion.esNombreValido(nombre)) {
            JOptionPane.showMessageDialog(this, "Nombre inválido");
            return;
        }

//        if (descripcion.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Descripción inválida");
//            return;
//        }
        if (!Validacion.esPrecioValido(precioTxt)) {
            JOptionPane.showMessageDialog(this, "Precio inválido (máx 3 decimales)");
            return;
        }

        Double precio;
        try {
            precio = Double.valueOf(precioTxt);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio no es un número válido");
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Deseas agregar el producto " + nombre + "?",
                "Confirmación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            TipoProductoDTO tipo = TipoProductoDTO.valueOf(tipoTxt);

            ProductoDTO producto = new ProductoDTO(
                    nombre,
                    precio,
                    tipo,
                    descripcion,
                    DisponibilidadProductoDTO.DISPONIBLE,
                    rutaImagen,
                    recetas
            );

            coordinador.registrarProducto(producto);
        }
    }

    /**
     * Abre un selector de archivos para cargar una imagen local al sistema.
     */
    private void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            java.io.File archivo = fileChooser.getSelectedFile();
            rutaImagen = archivo.getAbsolutePath();

            ImageIcon icono = new ImageIcon(rutaImagen);

            Image img = icono.getImage().getScaledInstance(
                    lblImagen.getWidth(),
                    lblImagen.getHeight(),
                    Image.SCALE_SMOOTH
            );

            lblImagen.setText("");
            lblImagen.setIcon(new ImageIcon(img));
            btnQuitarImagen.setVisible(true);
        }
    }

    /**
     * Agrega un ingrediente a la tabla visual y a la lista de recetas del
     * producto.
     *
     * @param ingrediente El objeto DTO del ingrediente seleccionado.
     * @param cantidad La cantidad necesaria para la receta.
     * @return <code>true</code> si se agregó con éxito, <code>false</code> si
     * el ingrediente ya existía en la receta.
     */
    public boolean agregarIngredienteATabla(IngredienteDTO ingrediente, Double cantidad) {

        // validar duplicados
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

    /**
     * Procesa una lista de ingredientes seleccionados desde un buscador
     * externo.
     * <p>
     * Solicita mediante diálogos la cantidad requerida para cada ingrediente
     * recibido.</p>
     *
     * @param ingredientesSeleccionados Lista de ingredientes provenientes del
     * buscador.
     */
    public void recibirIngredientesSeleccionados(List<IngredienteDTO> ingredientesSeleccionados) {
        for (IngredienteDTO ingrediente : ingredientesSeleccionados) {

            String cantidadStr = JOptionPane.showInputDialog(
                    "Cantidad para " + ingrediente.getNombre());

            if (cantidadStr == null || cantidadStr.trim().isEmpty()) {
                continue;
            }

            String unidad = ingrediente.getUnidadMedida().toString();

            if (!Validacion.esCantidadValida(cantidadStr, unidad)) {
                JOptionPane.showMessageDialog(null, "Cantidad inválida");
                continue;
            }

            double cantidad = Double.parseDouble(cantidadStr);

            agregarIngredienteATabla(ingrediente, cantidad);
        }
    }
}
