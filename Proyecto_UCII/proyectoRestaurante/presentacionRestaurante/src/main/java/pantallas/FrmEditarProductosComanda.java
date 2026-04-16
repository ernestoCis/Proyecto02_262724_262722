package pantallas;

import controlador.Coordinador;
import dtos.ComandaDTO;
import dtos.DetallePedidoDTO;
import dtos.ProductoDTO;
import dtos.RecetaDTO;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;

/**
 *
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class FrmEditarProductosComanda extends JFrame {

    private final Coordinador coordinador;

    private JButton btnSalir;
    private JButton btnRegresar;
    private JButton btnTerminar;
    private JButton btnBuscarProducto;
    
    private JPanel panelCarrito;

    private final List<DetallePedidoDTO> listaDetalles = new ArrayList<>();

    public FrmEditarProductosComanda(Coordinador coordinador) {
        this.coordinador = coordinador;
        configurarVentana();
        inicializarComponentes();
        precargarDetallesDeComanda();
        
        this.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                ProductoDTO producto = coordinador.getProductoSeleccionado();
                
                if (producto != null) {
                    agregarNuevoProductoAlCarrito(producto);
                    
                    coordinador.setProductoSeleccionado(null); 
                }
            }
        });
        
    }

    private void configurarVentana() {
        setTitle("Restaurante - Editar Comanda");
        setSize(1000, 750); 
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

        JLabel lblTitulo = new JLabel("Editar Comanda");
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
        panelCentro.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        JPanel panelBusqueda = crearPanelBuscador();

        JLabel lblTituloCarrito = new JLabel("Contenido actual de la comanda:");
        lblTituloCarrito.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblTituloCarrito.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        lblTituloCarrito.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelCarrito = new JPanel();
        panelCarrito.setBackground(new Color(245, 245, 245));
        panelCarrito.setLayout(new BoxLayout(panelCarrito, BoxLayout.Y_AXIS));

        JScrollPane scrollCarrito = new JScrollPane(panelCarrito);
        scrollCarrito.setPreferredSize(new Dimension(1040, 400));
        scrollCarrito.setBorder(BorderFactory.createLineBorder(new Color(210, 210, 210), 1));
        scrollCarrito.getVerticalScrollBar().setUnitIncrement(16);

        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 8));
        panelInferior.setOpaque(false);
        panelInferior.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        btnTerminar = new JButton("Guardar cambios");
        btnTerminar.setFont(new Font("SansSerif", Font.BOLD, 18));
        btnTerminar.setFocusPainted(false);
        btnTerminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTerminar.setBackground(colorMostaza);
        btnTerminar.setForeground(Color.BLACK);
        btnTerminar.setPreferredSize(new Dimension(220, 45));

        panelInferior.add(btnTerminar);

        panelCentro.add(panelBusqueda);
        panelCentro.add(lblTituloCarrito);
        panelCentro.add(scrollCarrito);
        panelCentro.add(panelInferior);

        panelPrincipal.add(panelSuperiorContenedor, BorderLayout.NORTH);
        panelPrincipal.add(panelCentro, BorderLayout.CENTER);

        add(panelPrincipal);
        eventos();
    }

    private JPanel crearPanelBuscador() {
        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBusqueda.setOpaque(false);

        btnBuscarProducto = new JButton("🔍 Buscar y Agregar Producto");
        btnBuscarProducto.setFont(new Font("SansSerif", Font.BOLD, 16));
        btnBuscarProducto.setPreferredSize(new Dimension(300, 45));
        btnBuscarProducto.setBackground(new Color(177, 201, 182));
        btnBuscarProducto.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnBuscarProducto.setFocusPainted(false);

        btnBuscarProducto.addActionListener(e -> {
            coordinador.abrirBuscadorProductos(Coordinador.OrigenBusquedaProductos.EDICION_COMANDA);
        });

        panelBusqueda.add(btnBuscarProducto);
        return panelBusqueda;
    }

    private void eventos() {
        btnSalir.addActionListener(e -> {
            coordinador.limpiarSesionComanda();
            coordinador.setMeseroActual(null);
            coordinador.mostrarInicioSesionMesero();
            dispose();
        });

        btnRegresar.addActionListener(e -> {
            coordinador.mostrarEstadosComanda();
            dispose();
        });

        btnTerminar.addActionListener(e -> {
            if (listaDetalles.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar al menos un producto.");
            } else {
                ComandaDTO comanda = coordinador.getComanda();
                comanda.setDetalles(new ArrayList<>(listaDetalles));
                coordinador.setComanda(comanda);

                coordinador.mostrarResumenPedidoEditado();
                dispose();
            }
        });
    }

    private void precargarDetallesDeComanda() {
        ComandaDTO comanda = coordinador.getComanda();
        listaDetalles.clear();

        if (comanda != null && comanda.getDetalles() != null) {
            for (DetallePedidoDTO d : comanda.getDetalles()) {
                DetallePedidoDTO copia = new DetallePedidoDTO();
                copia.setProductoDTO(d.getProductoDTO());
                copia.setCantidad(d.getCantidad());
                copia.setPrecioUnitario(d.getPrecioUnitario());
                copia.setSubtotal(d.getSubtotal());
                copia.setNota(d.getNota() != null ? d.getNota() : "");
                listaDetalles.add(copia);
            }
        }
        actualizarVistaCarrito();
    }

    public void recibirProductoSeleccionado(ProductoDTO productoSeleccionado) {
        if (productoSeleccionado != null) {
            agregarNuevoProductoAlCarrito(productoSeleccionado);
        }
    }

    private void agregarNuevoProductoAlCarrito(ProductoDTO producto) {
        String inputCantidad = JOptionPane.showInputDialog(this, "¿Qué cantidad deseas agregar de " + producto.getNombre() + "?", "1");
        if (inputCantidad == null || inputCantidad.trim().isEmpty()) return;

        try {
            int cantSolicitada = Integer.parseInt(inputCantidad);
            if (cantSolicitada <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.");
                return;
            }

            int maxPosible = calcularMaximoPosibleEdicion(producto);

            if (cantSolicitada > maxPosible) {
                JOptionPane.showMessageDialog(
                        this,
                        "¡Sin stock suficiente!\nSolo puedes agregar " + maxPosible + " unidades adicionales de este producto.",
                        "Límite de inventario", JOptionPane.WARNING_MESSAGE
                );
                return;
            }

            String nota = JOptionPane.showInputDialog(this, "Escribe una nota para este platillo (Opcional):", "");
            if (nota == null) nota = "";
            nota = nota.trim();

            boolean agrupado = false;
            for (DetallePedidoDTO detalleExistente : listaDetalles) {
                if (detalleExistente.getProductoDTO().getIdProducto().equals(producto.getIdProducto())
                        && detalleExistente.getNota().equalsIgnoreCase(nota)) {
                    
                    detalleExistente.setCantidad(detalleExistente.getCantidad() + cantSolicitada);
                    detalleExistente.setSubtotal(detalleExistente.getCantidad() * detalleExistente.getPrecioUnitario());
                    agrupado = true;
                    break;
                }
            }

            if (!agrupado) {
                DetallePedidoDTO nuevoDetalle = new DetallePedidoDTO();
                nuevoDetalle.setProductoDTO(producto);
                nuevoDetalle.setCantidad(cantSolicitada);
                nuevoDetalle.setPrecioUnitario(producto.getPrecio());
                nuevoDetalle.setSubtotal(cantSolicitada * producto.getPrecio());
                nuevoDetalle.setNota(nota);
                listaDetalles.add(nuevoDetalle);
            }

            actualizarVistaCarrito();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Por favor ingresa un número válido.");
        }
    }

    private int calcularMaximoPosibleEdicion(ProductoDTO productoAñadir) {
        Map<Long, Double> consumoPantalla = new HashMap<>();
        for (DetallePedidoDTO detalle : listaDetalles) {
            if (detalle.getProductoDTO().getRecetas() != null) {
                for (RecetaDTO r : detalle.getProductoDTO().getRecetas()) {
                    long idIng = r.getIngrediente().getIdIngrediente();
                    double gasto = r.getCantidad() * detalle.getCantidad();
                    consumoPantalla.put(idIng, consumoPantalla.getOrDefault(idIng, 0.0) + gasto);
                }
            }
        }

        Map<Long, Double> consumoOriginalComanda = new HashMap<>();
        ComandaDTO comandaActual = coordinador.getComanda();
        if (comandaActual != null && comandaActual.getDetalles() != null) {
            for (DetallePedidoDTO detalle : comandaActual.getDetalles()) {
                if (detalle.getProductoDTO().getRecetas() != null) {
                    for (RecetaDTO r : detalle.getProductoDTO().getRecetas()) {
                        long idIng = r.getIngrediente().getIdIngrediente();
                        double gastoOriginal = r.getCantidad() * detalle.getCantidad();
                        consumoOriginalComanda.put(idIng, consumoOriginalComanda.getOrDefault(idIng, 0.0) + gastoOriginal);
                    }
                }
            }
        }

        int maxPermitido = Integer.MAX_VALUE;

        if (productoAñadir.getRecetas() != null && !productoAñadir.getRecetas().isEmpty()) {
            for (RecetaDTO r : productoAñadir.getRecetas()) {
                long idIng = r.getIngrediente().getIdIngrediente();
                double stockRealBD = r.getIngrediente().getCantidadActual();
                double yaApartadoEnBD = consumoOriginalComanda.getOrDefault(idIng, 0.0);
                
                double stockTotalParaEstaComanda = stockRealBD + yaApartadoEnBD;
                double consumidoEnPantalla = consumoPantalla.getOrDefault(idIng, 0.0);
                
                double stockSobrante = stockTotalParaEstaComanda - consumidoEnPantalla;

                if (stockSobrante <= 0) return 0;

                int maxPorEsteIngrediente = (int) (stockSobrante / r.getCantidad());
                if (maxPorEsteIngrediente < maxPermitido) {
                    maxPermitido = maxPorEsteIngrediente;
                }
            }
        } else {
            return 999; 
        }

        return maxPermitido;
    }

    private void actualizarVistaCarrito() {
        panelCarrito.removeAll();

        if (listaDetalles.isEmpty()) {
            JLabel lblVacio = new JLabel("La comanda está vacía.");
            lblVacio.setFont(new Font("SansSerif", Font.ITALIC, 16));
            lblVacio.setForeground(Color.GRAY);
            lblVacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelCarrito.add(Box.createVerticalStrut(30));
            panelCarrito.add(lblVacio);
        } else {
            for (DetallePedidoDTO detalle : listaDetalles) {
                panelCarrito.add(crearFilaDetalle(detalle));
                panelCarrito.add(Box.createVerticalStrut(5)); 
            }
        }

        panelCarrito.revalidate();
        panelCarrito.repaint();
    }

    private JPanel crearFilaDetalle(DetallePedidoDTO detalle) {
        JPanel panelFila = new JPanel(new BorderLayout());
        panelFila.setBackground(Color.WHITE);
        panelFila.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        panelFila.setMaximumSize(new Dimension(1000, 70));

        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        panelInfo.setOpaque(false);

        JLabel lblCant = new JLabel(detalle.getCantidad() + "x ");
        lblCant.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblCant.setForeground(new Color(216, 84, 78));

        JLabel lblNombre = new JLabel(detalle.getProductoDTO().getNombre());
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 16));

        String textoNota = detalle.getNota();
        JLabel lblNota = new JLabel(textoNota.isEmpty() ? "" : " (Nota: " + textoNota + ")");
        lblNota.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lblNota.setForeground(Color.GRAY);

        panelInfo.add(lblCant);
        panelInfo.add(lblNombre);
        panelInfo.add(lblNota);

        JPanel panelAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelAcciones.setOpaque(false);

        JButton btnEditarNota = new JButton("Editar Nota");
        btnEditarNota.setFocusPainted(false);
        btnEditarNota.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnEliminar = new JButton("❌ Quitar");
        btnEliminar.setFocusPainted(false);
        btnEliminar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnEliminar.setForeground(Color.RED);

        btnEditarNota.addActionListener(e -> {
            String nuevaNota = JOptionPane.showInputDialog(this, "Editar nota para " + detalle.getProductoDTO().getNombre() + ":", textoNota);
            if (nuevaNota != null) {
                detalle.setNota(nuevaNota.trim());
                actualizarVistaCarrito();
            }
        });

        btnEliminar.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "¿Quitar este renglón de la comanda?", "Confirmar", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                listaDetalles.remove(detalle);
                actualizarVistaCarrito();
            }
        });

        panelAcciones.add(btnEditarNota);
        panelAcciones.add(btnEliminar);

        panelFila.add(panelInfo, BorderLayout.CENTER);
        panelFila.add(panelAcciones, BorderLayout.EAST);

        return panelFila;
    }
}