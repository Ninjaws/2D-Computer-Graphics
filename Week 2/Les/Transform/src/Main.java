import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Main extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Transform");
        frame.setContentPane(new Main());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);



    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(getWidth()/2,getHeight()/2);
        g2d.scale(1,-1);


        Renderable square = new Renderable(new Rectangle2D.Double(-50, -50, 100, 100),
                new Point2D.Double(0, 0), (float) Math.toRadians(0), 1);

        square.draw(g2d);
        System.out.println(square.getTransform());
    }
}
