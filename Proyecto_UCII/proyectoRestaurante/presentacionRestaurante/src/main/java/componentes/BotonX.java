package componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Componente de botón personalizado diseñado para acciones críticas o de
 * cancelación.
 * <p>
 * Extiende {@link JButton} y utiliza un renderizado manual con
 * {@link Graphics2D} para presentar un diseño redondeado con colores rojos
 * vibrantes. Incluye efectos de iluminación (hover) y suavizado de bordes para
 * una apariencia moderna y profesional.</p>
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class BotonX extends JButton {

    /**
     * Color rojo oscuro para el borde y fondo base (#B51502).
     */
    private final Color colorBorde = Color.decode("#B51502");

    /**
     * Color coral claro para resaltar el botón cuando el cursor está encima
     * (#F58571).
     */
    private final Color colorHover = Color.decode("#F58571");

    /**
     * Color de fondo principal en estado de reposo.
     */
    private final Color colorNormal = Color.decode("#B51502");

    /**
     * Estado lógico que determina el color de renderizado actual.
     */
    private boolean hover = false;

    /**
     * Construye un botón con el texto especificado e inicializa su estilo.
     *
     * @param texto El mensaje que se mostrará en el botón.
     */
    public BotonX(String texto) {
        super(texto);
        configurar();
        eventosHover();
    }

    /**
     * Define la configuración estética y funcional del componente.
     * <p>
     * Establece una fuente SansSerif de 20pt, habilita el cursor de mano y
     * desactiva el dibujado estándar de Swing para permitir el diseño
     * personalizado de bordes redondeados.</p>
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
     * Registra los detectores de movimiento del ratón.
     * <p>
     * Actualiza el estado {@code hover} y solicita el redibujado del componente
     * para activar la transición de colores.</p>
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
     * Sobrescribe el proceso de pintado para crear la geometría del botón.
     * <p>
     * Tareas realizadas por el motor de gráficos:</p>
     * <ul>
     * <li><b>Antialiasing:</b> Habilita el suavizado para curvas
     * perfectas.</li>
     * <li><b>Fondo:</b> Pinta un rectángulo redondeado con relleno sólido.</li>
     * <li><b>Contraste:</b> Cambia el color del texto a {@code Color.WHITE}
     * para legibilidad.</li>
     * <li><b>Borde:</b> Dibuja un contorno con un grosor de 2 píxeles
     * (BasicStroke).</li>
     * </ul>
     *
     * @param g El contexto gráfico de Java.
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
