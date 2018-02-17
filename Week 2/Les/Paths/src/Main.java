import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;

public class Main extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Paths");
        frame.setContentPane(new Main());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        GeneralPath path = new GeneralPath();
        path.moveTo(100, 100);
        path.lineTo(150, 150);
        path.lineTo(50, 300);
        path.closePath();

        g2d.draw(path);
        g2d.setColor(Color.BLUE);
        g2d.fill(path);
    }
}
