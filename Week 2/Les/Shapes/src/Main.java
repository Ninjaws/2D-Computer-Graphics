import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;

public class Main extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Shapes");
        frame.setContentPane(new Main());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(getWidth() / 2, getHeight() / 2);
        g2d.scale(1, -1);
        int scale = 100;

        GeneralPath myShape = new GeneralPath();
        myShape.moveTo(-2f * scale, 0f * scale);
        myShape.quadTo(0 * scale, 2 * scale, 2 * scale, 0 * scale);
        myShape.quadTo(0f * scale, -2f * scale, -2f * scale, 0f * scale);
        myShape.moveTo(-1f * scale, 0.5f * scale);
        myShape.lineTo(-1f * scale, -0.5f * scale);
        myShape.lineTo(1f * scale, 0.5f * scale);
        myShape.lineTo(1f * scale, -0.5f * scale);
        myShape.closePath();

        g2d.draw(myShape);
        g2d.fill(myShape);
        //Nonzero rule is standard, FillRule for switching??
    }
}
