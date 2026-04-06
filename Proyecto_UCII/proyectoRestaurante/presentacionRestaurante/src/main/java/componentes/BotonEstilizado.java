package componentes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 
 * @author Paulina Guevara, Ernesto Cisneros
 */
public class BotonEstilizado extends JButton {

    private final Color colorBorde = new Color(229, 171, 75);
    private final Color colorHover = Color.decode("#3B3B3B");
    private final Color colorNormal = new Color(240, 240, 240);

    private boolean hover = false;

    public BotonEstilizado(String texto) {
        super(texto);
        configurar();
        eventosHover();
    }

    private void configurar() {
        setFont(new Font("SansSerif", Font.PLAIN, 20));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setOpaque(false);
        setPreferredSize(new Dimension(170, 45));
    }

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
            setForeground(new Color(60, 60, 60));
        }

        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        g2.setColor(colorBorde);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);

        g2.dispose();

        super.paintComponent(g);
    }
}