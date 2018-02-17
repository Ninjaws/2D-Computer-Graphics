import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Main extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("13 Squares");
        frame.setContentPane(new Main());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
          g2d.translate(10, getHeight() / 2);
          g2d.scale(1, -1);

        Rectangle2D[] squares = new Rectangle2D[13];
        int size = 30;
        int margin = 5;

        float space = 1f / squares.length;

        for (int i = 0; i < 13; i++) {

            squares[i] = new Rectangle2D.Double((size + margin) * i, 0, size, size);

            g2d.setColor(Color.getHSBColor(space * i, 1, 1));
            g2d.fill(squares[i]);
            g2d.setColor(Color.black);
            g2d.draw(squares[i]);

        }

    }
}
