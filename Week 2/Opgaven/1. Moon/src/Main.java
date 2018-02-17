import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Main extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Moon");
        frame.setContentPane(new Main());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Area a = new Area(new Ellipse2D.Double(0, 0, 100, 100));
        Area b = new Area(new Ellipse2D.Double(30, 0, 100, 100));

        Area subtract = new Area(b);
        subtract.subtract(a);

        g2d.translate(getWidth() / 2, getHeight() / 2);

        g2d.fill(subtract);

    }
}
