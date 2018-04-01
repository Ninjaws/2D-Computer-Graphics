import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class Graph extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Graph");
        frame.setSize(800, 600);
        frame.setContentPane(new Graph());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(getWidth() / 2, getHeight() / 2);
        g2d.scale( 1, -1);

        g2d.drawLine(-getWidth() / 2, 0, getWidth() / 2, 0);
        g2d.drawLine(0, -getHeight() / 2, 0, getHeight() / 2);

        float resolution = 0.1f;
        double scale = 100.0;
        double lastY = Math.pow(-10, 3);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (float x = -10; x < 10; x += resolution) {
            float y = (float)Math.pow(x, 3);
            g2d.draw(new Line2D.Double(x*scale, y*scale, (x-resolution)*scale, lastY*scale));
            lastY = y;
        }
    }
}
