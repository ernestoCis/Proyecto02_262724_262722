package componentes;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Componente de botón personalizado diseñado para la navegación entre módulos
 * principales.
 * <p>
 * Esta clase extiende {@link JButton} e incorpora la capacidad de mostrar un
 * icono escalado junto al texto, un diseño de bordes altamente redondeados
 * (tipo cápsula) y efectos visuales de cambio de color al interactuar con el
 * mouse.</p>
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class BotonModulos extends JButton {

    /**
     * Color de fondo principal en estado de reposo (#CC514E).
     */
    private Color colorNormal = Color.decode("#CC514E");

    /**
     * Color de fondo suavizado para el efecto hover (#BA6E6C).
     */
    private Color colorHover = Color.decode("#BA6E6C");

    /**
     * Construye un botón de módulo con texto e icono personalizado.
     * <p>
     * Configura automáticamente el tamaño preferido (280x60), escala el icono
     * proporcionado a 26x26 píxeles con suavizado y establece la separación
     * entre el gráfico y el texto.</p>
     *
     * * @param texto El nombre del módulo o acción.
     * @param rutaIcono Ruta relativa o absoluta hacia el archivo de imagen del
     * icono.
     */
    public BotonModulos(String texto, String rutaIcono) {
        super(texto);

        setFont(new Font("SansSerif", Font.PLAIN, 18));
        setForeground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

        setHorizontalAlignment(CENTER);
        setPreferredSize(new Dimension(280, 60));
        setMaximumSize(new Dimension(280, 60));
        setMinimumSize(new Dimension(280, 60));

        setIconTextGap(18);

        // ICONO
        if (rutaIcono != null) {
            ImageIcon iconoOriginal = new ImageIcon(rutaIcono);
            Image iconoEscalado = iconoOriginal.getImage().getScaledInstance(26, 26, Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(iconoEscalado));
        }

        // HOVER
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(colorHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(colorNormal);
            }
        });

        setBackground(colorNormal);
    }

    /**
     * Sobrescribe el proceso de dibujo para renderizar un fondo con esquinas
     * circulares.
     * <p>
     * Aplica un radio de arco de 30 píxeles, logrando una estética de cápsula.
     * El uso de {@link RenderingHints#VALUE_ANTIALIAS_ON} garantiza que los
     * bordes curvos no presenten artefactos visuales o pixelado.</p>
     *
     * * @param g El contexto gráfico de dibujo.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo redondeado
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        super.paintComponent(g);
        g2.dispose();
    }
}
