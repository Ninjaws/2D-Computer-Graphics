import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class HelloLine extends JPanel
{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Java2d");
        frame.setSize(600,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(new HelloLine());
        frame.setVisible(true);

    }

    public HelloLine(){}

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.draw(new Line2D.Double(200,100, 400,300));
    }
}
