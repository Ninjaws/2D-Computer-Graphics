
import org.dyn4j.dynamics.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelloJoints extends JPanel implements ActionListener {

    Camera camera;
    World world;
    long lastTime;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello Joints");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setContentPane(new HelloJoints());

        frame.setSize(1000, 800);
        frame.setVisible(true);
    }

    public HelloJoints() {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.scale(1, -1);

        DebugDraw.draw(g2d, world, 100);
    }
}
