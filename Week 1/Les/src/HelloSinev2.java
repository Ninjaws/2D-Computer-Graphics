import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class HelloSinev2 extends JPanel {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Wave v2");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setContentPane(new HelloSinev2());
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(getWidth() / 2, getHeight() / 2);
        g2d.scale(1, -1);

        g2d.setColor(Color.RED);
        g2d.drawLine(0, 0, 1000, 0);
        g2d.setColor(Color.GREEN);
        g2d.drawLine(0, 0, 0, 1000);
    g2d.setColor(Color.BLACK);

    double resolution = 0.1;
    double scale = 50;
    double lastY = Math.sin(-10);

    for (double x = -10; x < 10; x+= resolution)
    {
        float y = (float)Math.sin(x);
        g2d.draw(new Line2D.Double(x*scale, y*scale, (x-resolution)*scale, lastY*scale));
        lastY = y;
    }
    }
}
