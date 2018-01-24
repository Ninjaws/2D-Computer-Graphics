import javax.swing.*;
import java.awt.*;

public class HelloColors extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello Java2D");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(600, 600));
        frame.setContentPane(new HelloColors());
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int i = 0; i < 500; i++) {
            g2d.setColor(Color.getHSBColor(i / 500.0f, 1, 1));
            g2d.drawLine(i, 10, i, 100);
        }
    }
}
