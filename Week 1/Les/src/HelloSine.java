import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class HelloSine extends JPanel
{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Wave");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600,600);
        frame.setContentPane(new HelloSine());
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        g2d.translate(getWidth()/2,getHeight()/2);
        g2d.scale(50,-50);

        g2d.setColor(Color.RED);
        g2d.drawLine(0,0,1000,0);
        g2d.setColor(Color.GREEN);
        g2d.drawLine(0,0,0,1000);
        g2d.setColor(Color.BLACK);

        double resolution = 0.1;
        double lastY = Math.sin(-10);

        for (double x = -10; x < 10; x += resolution)
        {
            float y = (float)Math.sin(x);
            g2d.draw(new Line2D.Double(x, y, x-resolution, lastY));
            lastY = y;
        }
    }
}
