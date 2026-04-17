package componentes;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import static javax.swing.SwingConstants.CENTER;

/**
 * Componente de botón personalizado optimizado para el acceso a módulos de
 * reportes.
 * <p>
 * Extiende de {@link JButton} y proporciona un diseño de gran formato (450x80)
 * con soporte para iconografía. Utiliza una paleta cromática en tonos azul
 * petróleo para diferenciar las acciones analíticas de las acciones operativas
 * del sistema.</p>
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class BotonReportes extends JButton {

    /**
     * Color de fondo base en estado normal, tono azul petróleo oscuro
     * (#54737A).
     */
    private Color colorNormal = Color.decode("#54737A");

    /**
     * Color de fondo aclarado para el efecto visual al pasar el mouse
     * (#84A9B3).
     */
    private Color colorHover = Color.decode("#84A9B3");

    /**
     * Construye un botón de reportes con texto e icono escalado.
     * <p>
     * Establece dimensiones fijas de gran tamaño para facilitar la interacción
     * en paneles de administración y escala automáticamente el icono
     * proporcionado a un tamaño de 26x26 píxeles.</p>
     *
     * * @param texto Etiqueta descriptiva del reporte.
     * @param rutaIcono Ruta del archivo de imagen para el icono decorativo.
     */
    public BotonReportes(String texto, String rutaIcono) {
        super(texto);

        setFont(new Font("SansSerif", Font.PLAIN, 18));
        setForeground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);

        setHorizontalAlignment(CENTER);
        setPreferredSize(new Dimension(450, 80));
        setMaximumSize(new Dimension(450, 80));
        setMinimumSize(new Dimension(450, 80));

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
     * Sobrescribe el dibujo del componente para aplicar una forma de cápsula.
     * <p>
     * Utiliza un radio de arco de 30 píxeles y activa el renderizado suavizado
     * para asegurar que los bordes del botón se vean nítidos y
     * profesionales.</p>
     *
     * * @param g El contexto gráfico de Java 2D.
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
