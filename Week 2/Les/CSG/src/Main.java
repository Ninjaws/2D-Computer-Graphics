import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Main extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("CSG");
        frame.setContentPane(new Main());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Area a = new Area(new Ellipse2D.Double(0, 0, 100, 100));
        Area b = new Area(new Ellipse2D.Double(50, 0, 100, 100));

        Area added = new Area(a);
        added.add(b);

        Area sub = new Area(a);
        sub.subtract(b);

        Area intersect = new Area(a);
        intersect.intersect(b);

        Area xor = new Area(a);
        xor.exclusiveOr(b);

        g2d.translate(25, 25);

        g2d.setColor(Color.lightGray);
        g2d.fill(added);
        g2d.setColor(Color.black);
        g2d.draw(a);
        g2d.draw(b);

        g2d.translate(0, 150);
        g2d.setColor(Color.lightGray);
        g2d.fill(xor);
        g2d.setColor(Color.black);
        g2d.draw(a);
        g2d.draw(b);

        g2d.translate(0, 150);
        g2d.setColor(Color.lightGray);
        g2d.fill(intersect);
        g2d.setColor(Color.black);
        g2d.draw(a);
        g2d.draw(b);

        g2d.translate(0, 150);
        g2d.setColor(Color.lightGray);
        g2d.fill(xor);
        g2d.setColor(Color.black);
        g2d.draw(a);
        g2d.draw(b);
    }


}
