package componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Componente de botón personalizado con diseño redondeado y soporte para
 * efectos visuales.
 * <p>
 * Esta clase extiende {@link JButton} y redefine su renderizado mediante
 * {@link Graphics2D} para ofrecer un aspecto moderno con bordes suavizados,
 * colores personalizados y un cambio de apariencia dinámico al pasar el cursor
 * (hover).</p>
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class BotonColor extends JButton {

    /**
     * Color principal del borde y fondo en estado normal (#EB9B5E).
     */
    private final Color colorBorde = Color.decode("#EB9B5E");

    /**
     * Color de fondo cuando el mouse está sobre el componente (#FFDEC7).
     */
    private final Color colorHover = Color.decode("#FFDEC7");

    /**
     * Color de fondo predeterminado del botón.
     */
    private final Color colorNormal = Color.decode("#EB9B5E");

    /**
     * Estado lógico que indica si el puntero del mouse se encuentra sobre el
     * botón.
     */
    private boolean hover = false;

    /**
     * Construye un nuevo botón con el texto especificado e inicializa su
     * configuración estética y detectores de eventos.
     *
     * @param texto El texto que se mostrará en el botón.
     */
    public BotonColor(String texto) {
        super(texto);
        configurar();
        eventosHover();
    }

    /**
     * Establece las propiedades base del botón para permitir el dibujo
     * personalizado.
     * <p>
     * Desactiva el área de contenido predeterminada, el pintado de bordes
     * estándar y el foco visual para que no interfieran con el diseño
     * redondeado.</p>
     */
    private void configurar() {
        setFont(new Font("SansSerif", Font.PLAIN, 16));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setPreferredSize(new Dimension(150, 45));
    }

    /**
     * Registra un {@link MouseAdapter} para gestionar el estado de
     * {@code hover}.
     * <p>
     * Actualiza la variable de estado y solicita un redibujado (repaint) cuando
     * el mouse entra o sale del área del componente.</p>
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
     * Sobrescribe el método de pintado para dibujar manualmente la forma del
     * botón.
     * <p>
     * Utiliza {@link RenderingHints} para activar el antialiasing, logrando
     * bordes curvos suaves. El proceso incluye:</p>
     * <ul>
     * <li>Pintado del fondo redondeado (fillRoundRect) basado en el estado
     * hover.</li>
     * <li>Dibujo del contorno (drawRoundRect) con un grosor de 2 píxeles.</li>
     * <li>Llamada al método base para renderizar el texto sobre la forma
     * dibujada.</li>
     * </ul>
     *
     * @param g El contexto gráfico para pintar.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (hover) {
            g2.setColor(colorHover);
            setForeground(Color.BLACK);
        } else {
            g2.setColor(colorNormal);
            setForeground(Color.BLACK);
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        g2.setColor(colorBorde);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

        g2.dispose();

        super.paintComponent(g);
    }
}
