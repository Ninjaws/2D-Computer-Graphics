import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Main extends JPanel {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Gradient Paint");
        frame.setContentPane(new Main());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        float[] fractions = new float[3];
        fractions[0] = 0.1f;
        fractions[1] = 0.5f;
        fractions[2] = 0.8f;
        Color[] colours = new Color[3];
        colours[0] = new Color(255, 0, 0);
        colours[1] = new Color(0, 255, 0);
        colours[2] = new Color(0, 0, 255);


        LinearGradientPaint lgd = new LinearGradientPaint(0, 0,
                getWidth()/2, getHeight()/2, fractions, colours,MultipleGradientPaint.CycleMethod.REFLECT);



        Rectangle2D rect = new Rectangle2D.Double(0, 0, getWidth(), getHeight());

        g2d.setPaint(lgd);
        g2d.fill(rect);
    }
}
