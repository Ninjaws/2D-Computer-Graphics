import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Main extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Mirror");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setContentPane(new Main());
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        double x = 0;
        double y = 2.5 * x;

        double[][] mirrorMatrix = new double[3][3];
        mirrorMatrix[0][0] = 1; mirrorMatrix[0][1] = 0; mirrorMatrix[0][2] = 0;
        mirrorMatrix[1][0] = 0; mirrorMatrix[1][1] = 1; mirrorMatrix[1][2] = 0;
        mirrorMatrix[2][0] = 0; mirrorMatrix[2][1] = 0; mirrorMatrix[2][2] = 1;

        GeneralPath path = new GeneralPath();
        path.moveTo(x, y);

        for (int i = 0; i < getWidth(); i++) {
            x = i;
            y = 2.5 * x;
            path.lineTo(x, y);
        }

        g2d.draw(path);

        Renderable square = new Renderable(new Rectangle2D.Double(0, 0, 100, 100),
                new Point2D.Double(0, 150), (float) Math.toRadians(0), 1f);

   //     g2d.setTransform(square.getTransform());
        square.draw(g2d);

    }
}

