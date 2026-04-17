package componentes;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

/**
 * Contenedor personalizado con bordes redondeados.
 * <p>
 * Esta clase extiende {@link JPanel} para proporcionar un panel cuyo fondo se
 * dibuja con esquinas curvas. Es ideal para crear interfaces modernas basadas
 * en tarjetas (cards) o secciones visualmente diferenciadas.</p>
 *
 * * @author Paulina Guevara, Ernesto Cisneros
 */
public class PanelRedondeado extends JPanel {

    /**
     * El radio de curvatura para las esquinas del panel, medido en píxeles.
     */
    private int radio;

    /**
     * Crea una instancia de PanelRedondeado con un radio específico.
     * <p>
     * Establece la opacidad en {@code false} para asegurar que las esquinas
     * transparentes del panel permitan ver el fondo del contenedor padre.</p>
     *
     * * @param radio Grado de redondeo de las esquinas.
     */
    public PanelRedondeado(int radio) {
        this.radio = radio;
        setOpaque(false);
    }

    /**
     * Sobrescribe el proceso de pintado para dibujar la forma redondeada del
     * panel.
     * <p>
     * El método realiza las siguientes tareas:</p>
     * <ul>
     * <li>Castea el contexto {@link Graphics} a {@link Graphics2D} para acceder
     * a funciones avanzadas.</li>
     * <li>Activa el <b>Antialiasing</b> para evitar bordes dentados en las
     * curvas.</li>
     * <li>Utiliza el color de fondo actual del componente
     * ({@code getBackground()}) para rellenar un rectángulo redondeado
     * ({@code fillRoundRect}).</li>
     * </ul>
     *
     * * @param g El contexto gráfico de pintura.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);

        super.paintComponent(g);
    }
}
