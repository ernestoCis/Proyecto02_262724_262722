package componentes;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import static javax.swing.SwingConstants.CENTER;

public class BotonReportes extends JButton {

    private Color colorNormal = Color.decode("#54737A");
    private Color colorHover = Color.decode("#84A9B3");

    public BotonReportes(String texto, String rutaIcono) {
        super(texto);

        setFont(new Font("SansSerif", Font.PLAIN, 18));
        setForeground(Color.WHITE);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false); // importante para dibujar nosotros
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
