
import org.dyn4j.collision.Fixture;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.joint.*;
import org.dyn4j.geometry.*;
import org.dyn4j.geometry.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.security.Key;

public class HelloJoints extends JPanel implements ActionListener, MouseListener, MouseMotionListener, KeyListener {

    Camera camera;
    World world;
    MousePicker mousePicker;
    long lastTime;
    private boolean shotFired;
    private double scale = 100;
   // private Body slingshot;
    private Body pivot;
    private Vector2 startingPosition;
    private Convex ballFixture;
    private double ballDensity;
    private double ballFriction;
    private double ballRestitution;
   // private Joint joint;
    private Body currentBody;

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


        startingPosition = new Vector2(0, -1);
        ballFixture = new Circle(0.2);
        ballDensity = 0.5;
        ballFriction = 0.0;
        ballRestitution = 0.25;


        Body slingshot = new Body();
        slingshot.addFixture(ballFixture, ballDensity, ballFriction, ballRestitution);
        slingshot.translate(startingPosition);
        slingshot.setMass(MassType.NORMAL);
        world.addBody(slingshot);

        currentBody = slingshot;

        pivot = new Body();
        pivot.addFixture(new Circle(0.01));
        pivot.translate(0, -0.0);
        pivot.setMass(MassType.INFINITE);

        Joint joint = new WheelJoint(pivot, currentBody, currentBody.getWorldCenter(), new Vector2(-1, 0));
        world.addJoint(joint);


        lastTime = System.nanoTime();
        new Timer(15, this).start();
        camera = new Camera(this);
        mousePicker = new MousePicker(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addKeyListener(this);
        setFocusable(true);
        shotFired = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        long time = System.nanoTime();
        double elapsedTime = (time - lastTime) / 1000000000.0;
        lastTime = time;

        mousePicker.update(world, camera.getTransform(getWidth(), getHeight()), scale);
        update();
        world.update(elapsedTime);

        repaint();
    }


    public void update() {
        if (shotFired) {
            if (currentBody.getWorldCenter().x > startingPosition.x) {
                world.removeAllJoints();
            }
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setTransform(camera.getTransform(getWidth(), getHeight()));
        g2d.scale(1, -1);

        DebugDraw.draw(g2d, world, scale);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        shotFired = true;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

        if (e.getKeyChar() == KeyEvent.VK_SPACE) {
            Body ball = new Body();
            ball.addFixture(ballFixture, ballDensity, ballFriction, ballRestitution);
            ball.setMass(MassType.NORMAL);
            ball.translate(startingPosition);
            world.addBody(ball);

            currentBody = ball;

            Joint joint =  new WheelJoint(pivot, currentBody, currentBody.getWorldCenter(), new Vector2(-1, 0));
            world.addJoint(joint);
            shotFired = false;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
