import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class Spiral extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Spiral");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setContentPane(new Spiral());
        frame.setVisible(true);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


        g2d.translate(getWidth() / 2, getHeight() / 2);
        g2d.scale(1, -1);

        g2d.setColor(Color.BLACK);

        // The axis, for visualization
        // g2d.drawLine(-getWidth() / 2, 0, getWidth() / 2, 0);
        // g2d.drawLine(0, -getHeight() / 2, 0, getHeight() / 2);

        g2d.setColor(Color.BLUE);

        double scale = 200.0;

        double circle = Math.PI * 2;
        double start = circle * 0.1;
        double end = circle * 7;
        double range = Math.abs(start - end);

        double angle = start;
        double baseLength = 1;
        double r = baseLength;

        double x = Math.cos(angle) * r;
        double y = Math.sin(angle) * r;

        float resolution = 0.01f;

        boolean isPositive = true;
        //If the start is larger than the end, then the counter has to be negative
        if (start > end) {
            isPositive = false;
            resolution *= -1;
        }

        for (; ; angle += resolution) {
            //The break condition depends on whether the loop counts forwards of backwards
            if (isPositive) {
                if (angle >= end)
                    break;
            } else {
                if (angle <= end)
                    break;
            }

            // The angle increases until end (x times a full circle)
            // The progress is how close the angle is to the goal value (1 is at the start, 0 is at the goal)
            double progress = Math.abs(angle - end) / range;
            r = baseLength * (progress);

            double oldX = x;
            double oldY = y;

            x = Math.cos(angle) * r;
            y = Math.sin(angle) * r;

            g2d.draw(new Line2D.Double(oldX * scale, oldY * scale, x * scale, y * scale));

            //X and Y visualization, use only for single line (otherwise it will be a mess of lines)
            // g2d.setColor(Color.GREEN);
            // g2d.draw(new Line2D.Double(0 * scale, y * scale, x * scale, y * scale));

            // g2d.setColor(Color.RED);
            // g2d.draw(new Line2D.Double(x * scale, 0 * scale, x * scale, y * scale));

        }


    }
}
