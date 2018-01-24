import javax.swing.*;
import java.awt.*;

public class Java2DDemo extends JPanel
{

    public static void main(String[] args) {
        JFrame frame = new JFrame("Java2D");
        frame.setSize(200,200);
        frame.setContentPane(new Java2DDemo());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public Java2DDemo(){}



    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
