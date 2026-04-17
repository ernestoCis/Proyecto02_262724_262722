package componentes;

import javax.swing.*;
import java.awt.*;

/**
 * Componente de botón especializado para la navegación de retorno en la
 * interfaz.
 * <p>
 * Esta clase extiende {@link JButton} para proporcionar un botón de control
 * pequeño y consistente, caracterizado por el símbolo "←". Está diseñado para
 * ubicarse en las esquinas superiores de las pantallas, permitiendo al usuario
 * retroceder al menú o pantalla anterior.</p>
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class BotonRegresar extends JButton {

    /**
     * Construye un botón de regreso con el símbolo de flecha predefinido.
     * <p>
     * Invoca automáticamente los parámetros de configuración visual para
     * mantener la consistencia con la paleta de colores del sistema.</p>
     */
    public BotonRegresar() {
        super("←");
        configurar();
    }

    /**
     * Establece la apariencia del botón utilizando colores y dimensiones fijas.
     * <p>
     * Las características principales incluyen:</p>
     * <ul>
     * <li><b>Color:</b> Utiliza un tono mostaza (RGB: 229, 171, 75) con texto
     * blanco.</li>
     * <li><b>Dimensiones:</b> Tamaño cuadrado compacto de 40x40 píxeles.</li>
     * <li><b>Interactividad:</b> Cambia el cursor a tipo mano (HAND_CURSOR)
     * para indicar que es un elemento accionable.</li>
     * <li><b>Simplicidad:</b> Elimina los bordes y el pintado del foco para un
     * acabado más limpio y moderno.</li>
     * </ul>
     */
    private void configurar() {
        Color colorMostaza = new Color(229, 171, 75);

        setFont(new Font("SansSerif", Font.BOLD, 18));
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(40, 40));

        setBackground(colorMostaza);
        setForeground(Color.WHITE);

        setOpaque(true);
        setContentAreaFilled(true);
        setBorderPainted(false);
        setBorder(BorderFactory.createEmptyBorder());
    }
}
