package componentes;

import javax.swing.*;
import java.awt.*;

/**
 * 
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class BotonRegresar extends JButton {

    public BotonRegresar() {
        super("←");
        configurar();
    }

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