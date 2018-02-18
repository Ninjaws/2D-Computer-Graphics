import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Main extends JPanel {

    private final double coefficient = 2.5;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mirror");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(500, 500));
        frame.setContentPane(new Main());
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(getWidth() / 2, getHeight() / 2);
        g2d.scale(1, -1);

        double x = 0;
        double y = 0;

        GeneralPath path = new GeneralPath();
        path.moveTo(x, y);

        for (int i = 0; i < getWidth(); i++) {
            x = i;
            y = 2.5 * x;
            path.lineTo((int) x, (int) y);
        //    System.out.println(x + ", " + y);
        }
        path.closePath();
        g2d.draw(path);


        g2d.setColor(Color.RED);
        AffineTransform t = new AffineTransform(1, 0, 0, 1, -50, 100);
        g2d.draw(t.createTransformedShape(new Rectangle2D.Double(0, 0, 100, 100)));

        g2d.setColor(Color.BLUE);
        t.setTransform(new AffineTransform(2 / (1 + Math.pow(this.coefficient, 2)) - 1,
                (2 * this.coefficient) / (1 + Math.pow(this.coefficient, 2)),
                (2 * this.coefficient) / (1 + Math.pow(this.coefficient, 2)),
                (2 * Math.pow(this.coefficient, 2)) / (1 + Math.pow(this.coefficient, 2)) - 1,
                0, 0));


        t.concatenate(new AffineTransform(1, 0, 0, 1, -50, 100));
        g2d.draw(t.createTransformedShape(new Rectangle2D.Double(0, 0, 100, 100)));


    }
}

