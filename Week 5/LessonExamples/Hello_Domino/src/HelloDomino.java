

import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelloDomino extends JPanel implements ActionListener {

    Camera camera;
    World world;
    long lastTime;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello Dominoes");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setContentPane(new HelloDomino());
        frame.setMinimumSize(new Dimension(1000, 800));
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

        frame.setVisible(true);

    }

    public HelloDomino() {
        world = new World();
        world.setGravity(new Vector2(0,-9.8));

        Body floor = new Body();
        floor.addFixture(Geometry.createRectangle(20,1));
        floor.translate(0,-0.5);
        floor.setMass(MassType.INFINITE);
        world.addBody(floor);

    }

    @Override
    public void actionPerformed(ActionEvent e) {


        repaint();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        g2d.setTransform(camera.getTransform(getWidth(),getHeight()));
        g2d.scale(1,-1);

        DebugDraw.draw(g2d, world,100);
    }
}
