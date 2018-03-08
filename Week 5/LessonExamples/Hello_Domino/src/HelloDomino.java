

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
        world.setGravity(new Vector2(0, -9.8));

        Body floor = new Body();
        floor.addFixture(Geometry.createRectangle(20, 1));
        floor.translate(0, -0.5);
        floor.setMass(MassType.INFINITE);
        world.addBody(floor);

        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10 - y; x++) {
                Body box = new Body();
                box.addFixture(Geometry.createRectangle(0.25, 0.25));
                box.setMass(MassType.NORMAL);
                box.translate(5 + x * 0.25 + 0.125 * y, y * 0.25);
                world.addBody(box);
            }
        }

        Body ball = new Body();
        ball.addFixture(Geometry.createCircle(1));
        ball.translate(new Vector2(0, 2));
        ball.setMass(MassType.NORMAL);
        ball.getFixture(0).setRestitution(0.25);
        ball.applyImpulse(-20);
        world.addBody(ball);

        lastTime = System.nanoTime();
        new Timer(15, this).start();
        camera = new Camera(this);
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

        g2d.setTransform(camera.getTransform(getWidth(), getHeight()));
        g2d.scale(1, -1);

        DebugDraw.draw(g2d, world, 100);
    }
}
