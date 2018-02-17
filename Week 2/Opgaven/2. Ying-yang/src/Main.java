import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.sql.Time;

public class Main extends JPanel {


    public static double currentDegrees = 0;
    public static final double changInAngle = -0.1;
    public static double timePassed = 0;
    public static long startTime = 0;
    public static long endTime = 0;
    public static double frameSpeed = 1.0 / 60.0;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Yin Yang");
        frame.setContentPane(new Main());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);


        startTime = System.currentTimeMillis();
        while (true) {

            endTime = System.currentTimeMillis();
            timePassed = endTime - startTime;

            if (timePassed >= frameSpeed) {
                currentDegrees += changInAngle;
                frame.getContentPane().repaint();
                startTime = System.currentTimeMillis();
            }

        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.translate(getWidth() / 2, getHeight() / 2);
        g2d.scale(1, -1);
        g2d.rotate(Math.toRadians(currentDegrees));

        double yinyangSize = 300;
        double smallCircleSize = yinyangSize / 2;
        double tinyCircleSize = smallCircleSize / 3;

        Area perimeterCircle = new Area(new Ellipse2D.Double(-yinyangSize / 2, -yinyangSize / 2,
                yinyangSize, yinyangSize));


        Area halfCircle = new Area(new Arc2D.Double(-yinyangSize / 2, -yinyangSize / 2,
                yinyangSize, yinyangSize, 90, 180, Arc2D.PIE));


        Area yinCircle = new Area(new Ellipse2D.Double(-smallCircleSize / 2, 0, smallCircleSize, smallCircleSize));
        Area yangCircle = new Area(new Ellipse2D.Double(-smallCircleSize / 2, -smallCircleSize, smallCircleSize, smallCircleSize));

        Area yinInnerCircle = new Area(new Ellipse2D.Double(-tinyCircleSize / 2, (smallCircleSize / 2) - (tinyCircleSize / 2), tinyCircleSize, tinyCircleSize));
        Area yangInnerCircle = new Area(new Ellipse2D.Double(-tinyCircleSize / 2, -(smallCircleSize / 2) - (tinyCircleSize / 2), tinyCircleSize, tinyCircleSize));

        g2d.setColor(Color.WHITE);
        g2d.fill(perimeterCircle);
        g2d.setColor(Color.BLACK);
        g2d.draw(perimeterCircle);
        g2d.fill(halfCircle);
        g2d.fill(yinCircle);
        g2d.setColor(Color.WHITE);
        g2d.fill(yangCircle);
        g2d.fill(yinInnerCircle);
        g2d.setColor(Color.BLACK);
        g2d.fill(yangInnerCircle);





    }
}
