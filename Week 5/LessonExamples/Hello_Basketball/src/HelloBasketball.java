import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.*;
import org.dyn4j.geometry.Rectangle;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class HelloBasketball extends JPanel implements ActionListener {

    Camera camera;
    World world;
    MousePicker mousePicker;
    long lastTime;
    private double scale;

    JCheckBox showDebug;

    ArrayList<GameObject> gameObjects = new ArrayList<>();


    public static void main(String[] args) {
        JFrame frame = new JFrame("Hello Basketball");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(new HelloBasketball());
        frame.setSize(1000, 800);
        frame.setVisible(true);
    }

    public HelloBasketball() {

        scale = 100;
        add(showDebug = new JCheckBox("Debug"));
        world = new World();
        world.setGravity(new Vector2(0, -9.81));


        Body floor = new Body();
        floor.addFixture(new Rectangle(20, 1));
        floor.getTransform().setTranslation(0, -0.5);
        floor.setMass(MassType.INFINITE);
        world.addBody(floor);
        gameObjects.add(new GameObject("/images/floor.png", floor, new Vector2(0, 0), 1));

        Body wall1 = new Body();
        wall1.addFixture(Geometry.createRectangle(0.15, 10));
        wall1.getTransform().setTranslation(10, 5);
        wall1.setMass(MassType.INFINITE);
        world.addBody(wall1);
        gameObjects.add(new GameObject("/images/wall.png", wall1, new Vector2(0, 0), 1));

        Body wall2 = new Body();
        wall2.addFixture(Geometry.createRectangle(0.15, 10));
        wall2.getTransform().setTranslation(-10, 5);
        wall2.setMass(MassType.INFINITE);
        world.addBody(wall2);
        gameObjects.add(new GameObject("/images/wall.png", wall2, new Vector2(0, 0), 1));


        Body pole = new Body();
        pole.addFixture(Geometry.createRectangle(0.1, 2.5));
        pole.getTransform().setTranslation(4, 1.25);
        pole.setMass(MassType.INFINITE);
        world.addBody(pole);
        gameObjects.add(new GameObject("/images/basket.png", pole, new Vector2(15, 0), 1));

        Body net = new Body();
        net.addFixture(Geometry.createRectangle(0.02, 0.2));
        net.getTransform().setTranslation(3.6, 2.0);
        net.setMass(MassType.INFINITE);
        world.addBody(net);


        Body ball = new Body();
        ball.addFixture(Geometry.createCircle(0.15));
        ball.getTransform().setTranslation(0, 2.4);
        ball.setMass(MassType.NORMAL);
        ball.getFixture(0).setRestitution(0.75);
        world.addBody(ball);
        gameObjects.add(new GameObject("/images/basketball.png", ball, new Vector2(0, 0), 0.05));


        Body crate = new Body();
        Circle rect = new Circle( 1);
        crate.addFixture(rect);
        crate.translate(-1, 0);
        crate.setMass(MassType.NORMAL);
        world.addBody(crate);
        gameObjects.add(new GameObject("/images/basketball.png", crate, new Vector2(0, 0), rect, scale));

        camera = new Camera(this);
        mousePicker = new MousePicker(this);
        lastTime = System.nanoTime();
        new Timer(15, this).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        long currentTime = System.nanoTime();
        double deltaTime = (currentTime - lastTime) / 1000000000.0;
        lastTime = currentTime;

        mousePicker.update(world, camera.getTransform(getWidth(), getHeight()), scale);
        world.update(deltaTime);

        repaint();

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        AffineTransform transform = g2d.getTransform();
        g2d.setTransform(camera.getTransform(getWidth(), getHeight()));
        g2d.scale(1, -1);

        for (GameObject gameObject : gameObjects) {
            gameObject.draw(g2d);
        }

        if (showDebug.isSelected()) {
            g2d.setColor(Color.BLUE);
            DebugDraw.draw(g2d, world, scale);
        }

        g2d.setTransform(transform);

    }

}
