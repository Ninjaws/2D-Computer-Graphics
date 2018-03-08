import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Geometry;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelloPhysics extends JPanel implements ActionListener {

    World world;
    long lastTime;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello Physics");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setContentPane(new HelloPhysics());
        frame.setVisible(true);
    }

    public HelloPhysics() {
        world = new World();
        world.setGravity(new Vector2(0, -9.8));

        Body platform = new Body();
        platform.addFixture(Geometry.createRectangle(8, 2));
        platform.rotate(Math.toRadians(1));
        platform.setMass(MassType.INFINITE);
        world.addBody(platform);


        Body weirdBall = new Body();
        weirdBall.addFixture(Geometry.createCircle(0.1),1,1,0.25);
        weirdBall.translate(0, 6);
        weirdBall.setMass(MassType.NORMAL);
        world.addBody(weirdBall);

        lastTime = System.nanoTime();
        new Timer(15, this).start();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        long time = System.nanoTime();
        double elapsedTime = (time - lastTime) / 1000000000.0;
        lastTime = time;
        world.update(elapsedTime);

        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.translate(getWidth() / 2, getHeight() / 2);
        g2d.scale(1, -1);
        DebugDraw.draw(g2d, world, 50);
    }

}
