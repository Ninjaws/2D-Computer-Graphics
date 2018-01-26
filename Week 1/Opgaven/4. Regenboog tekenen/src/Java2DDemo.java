import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

/**
 * @author Ian Vin
 * @version 1.0
 * @date 25-01-2018
 */

public class Java2DDemo extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Rainbow");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new Java2DDemo());
        frame.setSize(600, 600);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.translate(getWidth()/2, getHeight()/2);
        g2d.scale(1,-1);

        double scale = 100;

        // The length at which the colors start
        double rayLengthStart = 1;
        // The length at which the colors end
        double rayLengthEnd = 1.5;

        float amountOfColors = 500;
        double anglePerColor = Math.PI/amountOfColors;

        for (int currentColor = 0; currentColor < amountOfColors; currentColor++) {

            g2d.setColor(Color.getHSBColor(currentColor / amountOfColors, 1, 1));

            double angle = anglePerColor * currentColor;

            double startX = Math.cos(angle) * rayLengthStart;
            double startY = Math.sin(angle) * rayLengthStart;

            double endX = Math.cos(angle) * rayLengthEnd;
            double endY = Math.sin(angle) * rayLengthEnd;

            g2d.draw(new Line2D.Double(startX * scale, startY*scale, endX * scale, endY * scale));
        }

    }

}
