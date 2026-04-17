package componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Componente de botón personalizado diseñado específicamente para acciones de
 * edición.
 * <p>
 * Extiende de {@link JButton} y emplea técnicas de dibujo avanzado con
 * {@link Graphics2D} para presentar una estética de bordes redondeados y una
 * paleta de colores en tonos azules que facilitan la identificación visual de
 * tareas de modificación en el sistema.</p>
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class BotonEditar extends JButton {

    /**
     * Color utilizado para el contorno del botón y el fondo en estado hover
     * (#C3E0FA).
     */
    private final Color colorBorde = Color.decode("#C3E0FA");

    /**
     * Color de fondo que se activa cuando el usuario posiciona el mouse sobre
     * el botón.
     */
    private final Color colorHover = Color.decode("#C3E0FA");

    /**
     * Color de fondo base en estado de reposo (#D7E9F5).
     */
    private final Color colorNormal = Color.decode("#D7E9F5");

    /**
     * Bandera lógica para alternar el renderizado según la interacción del
     * mouse.
     */
    private boolean hover = false;

    /**
     * Crea una instancia de BotonEditar con el texto indicado.
     * <p>
     * Llama internamente a los métodos de configuración visual y registro de
     * eventos.</p>
     *
     * @param texto Etiqueta de texto que mostrará el botón.
     */
    public BotonEditar(String texto) {
        super(texto);
        configurar();
        eventosHover();
    }

    /**
     * Establece los parámetros de renderizado de Swing para permitir el diseño
     * personalizado.
     * <p>
     * Configura la fuente en negrita (BOLD) para resaltar la acción de edición
     * y desactiva los estilos nativos de los botones del Sistema Operativo.</p>
     */
    private void configurar() {
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setPreferredSize(new Dimension(150, 45));
    }

    /**
     * Implementa la lógica de respuesta visual mediante un MouseAdapter.
     * <p>
     * Cambia el estado de la variable {@code hover} y fuerza la ejecución de
     * {@code paintComponent} mediante la llamada a {@code repaint()}.</p>
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
     * Sobrescribe el proceso de dibujado del componente para aplicar formas
     * geométricas.
     * <p>
     * El método realiza las siguientes tareas técnicas:</p>
     * <ul>
     * <li>Activa el <b>Antialiasing</b> para evitar bordes pixelados en las
     * curvas.</li>
     * <li>Calcula el color de fondo dinámicamente según el estado de
     * interacción.</li>
     * <li>Dibuja un rectángulo con esquinas redondeadas (arco de 20px).</li>
     * <li>Aplica un borde (stroke) con un grosor de 2 píxeles para dar
     * profundidad.</li>
     * </ul>
     *
     * @param g El contexto gráfico proporcionado por Swing.
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
