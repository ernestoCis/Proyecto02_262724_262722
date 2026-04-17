package componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Componente de botón personalizado con estilo de alto contraste y énfasis.
 * <p>
 * Esta clase extiende {@link JButton} para proporcionar un diseño visualmente
 * atractivo mediante el uso de tonos rojizos y bordes redondeados. Es ideal
 * para botones de acción principal dentro de la aplicación, integrando efectos
 * de iluminación (hover) y renderizado suavizado (antialiasing).</p>
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class BotonEstilizado extends JButton {

    /**
     * Color oscuro utilizado para el contorno del botón (#993F3D).
     */
    private final Color colorBorde = Color.decode("#993F3D");

    /**
     * Color de fondo suavizado que se aplica cuando el mouse está sobre el
     * botón (#875D5B).
     */
    private final Color colorHover = Color.decode("#875D5B");

    /**
     * Color de fondo vibrante para el estado normal del botón (#CC514E).
     */
    private final Color colorNormal = Color.decode("#CC514E");

    /**
     * Estado lógico para controlar la alternancia de colores en el renderizado.
     */
    private boolean hover = false;

    /**
     * Crea un botón estilizado con el texto proporcionado.
     *
     * @param texto La etiqueta de texto que se mostrará en el botón.
     */
    public BotonEstilizado(String texto) {
        super(texto);
        configurar();
        eventosHover();
    }

    /**
     * Establece las propiedades de la interfaz de usuario.
     * <p>
     * Configura una fuente más grande (20pt), cambia el cursor a tipo mano y
     * elimina los componentes de pintura por defecto de Swing para permitir el
     * dibujo personalizado de la forma redondeada.</p>
     */
    private void configurar() {
        setFont(new Font("SansSerif", Font.PLAIN, 20));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setPreferredSize(new Dimension(150, 45));
    }

    /**
     * Gestiona los eventos del ratón para actualizar el aspecto visual.
     * <p>
     * Utiliza un {@link MouseAdapter} para detectar la entrada y salida del
     * puntero, disparando el método {@code repaint()} para reflejar los cambios
     * de color instantáneamente.</p>
     */
    private void eventosHover() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }
        });
    }

    /**
     * Redefine el pintado del componente utilizando la API de Java 2D.
     * <p>
     * El proceso de renderizado sigue estos pasos:</p>
     * <ul>
     * <li><b>Antialiasing:</b> Suaviza los bordes de la figura geométrica.</li>
     * <li><b>Fondo:</b> Dibuja un rectángulo redondeado con relleno sólido
     * según el estado.</li>
     * <li><b>Texto:</b> Establece el color de la fuente en blanco
     * ({@code Color.WHITE}) para legibilidad.</li>
     * <li><b>Borde:</b> Aplica un trazo de 2 píxeles de grosor para definir el
     * contorno.</li>
     * </ul>
     *
     * @param g El contexto gráfico de pintura.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (hover) {
            g2.setColor(colorHover);
            setForeground(Color.WHITE);
        } else {
            g2.setColor(colorNormal);
            setForeground(Color.WHITE);
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        g2.setColor(colorBorde);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

        g2.dispose();

        super.paintComponent(g);
    }
}
