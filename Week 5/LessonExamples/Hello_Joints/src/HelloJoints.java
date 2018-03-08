
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.joint.Joint;
import org.dyn4j.dynamics.joint.RevoluteJoint;
import org.dyn4j.geometry.*;
import org.dyn4j.geometry.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HelloJoints extends JPanel implements ActionListener {

    Camera camera;
    World world;
    MousePicker mousePicker;
    long lastTime;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello Joints");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        frame.setContentPane(new HelloJoints());

        frame.setSize(1000, 800);
        frame.setVisible(true);
    }

    public HelloJoints() {
        world = new World();
        world.setGravity(new Vector2(0, -9.81));

        Body floor = new Body();
        floor.addFixture(new Rectangle(20, 1));
        floor.translate(0, -3.5);
        floor.setMass(MassType.INFINITE);

        world.addBody(floor);

        Body floor2 = new Body();
        floor2.addFixture(new Rectangle(3, 0.4));
        floor2.translate(0, -0.5);
        floor2.setMass(MassType.NORMAL);
        world.addBody(floor2);

        Body floor3 = new Body();
        floor3.addFixture(new Rectangle(2,0.1));
        floor3.translate(0, -0.5);
        floor3.setMass(MassType.NORMAL);
        world.addBody(floor3);

        RevoluteJoint joint = new RevoluteJoint(floor3, floor2, new Vector2(2.0, -0.0));
        world.addJoint(joint);


        lastTime = System.nanoTime();
        new Timer(15, this).start();
        camera = new Camera(this);
        mousePicker = new MousePicker(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        long time = System.nanoTime();
        double elapsedTime = (time - lastTime) / 1000000000.0;
        lastTime = time;

        mousePicker.update(world, camera.getTransform(getWidth(), getHeight()), 100);

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
