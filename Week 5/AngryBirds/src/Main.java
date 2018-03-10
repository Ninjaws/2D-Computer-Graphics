
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;
import util.Camera;
import util.DebugDraw;
import util.MousePicker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.ArrayList;

public class Main extends JPanel implements ActionListener {

    private World world;
    private Camera camera;
    private MousePicker mousePicker;
    private double worldScale;
    private long lastTime;
    JCheckBox showDebug;

    private ArrayList<GameObject> gameObjects = new ArrayList<>();


    public static void main(String[] args) {
        JFrame frame = new JFrame("Angry Birds");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(new Main());
        frame.setSize(1000, 800);
        frame.setVisible(true);
    }

    public Main() {

        world = new World();
        world.setGravity(new Vector2(0.0, -9.81));
        worldScale = 100;
        add(showDebug = new JCheckBox("Debug"));

        BufferedImage tileset = null;
        try {
            tileset = ImageIO.read(getClass().getResource("\\images\\spritesheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(tileset);

        Body floor = new Body();
        Rectangle rect = new Rectangle(20, 1);
        floor.addFixture(rect);
        floor.translate(0,-3.5);
        floor.setMass(MassType.INFINITE);

        BufferedImage floorImage = null;
        floorImage = tileset.getSubimage(167, 160, 83, 44);

        world.addBody(floor);
        gameObjects.add(new GameObject(floorImage, floor, new Vector2(-0.4, 0), rect, worldScale));

        camera = new Camera(this);
        mousePicker = new MousePicker(this);
        new Timer(15, this).start();
        lastTime = System.nanoTime();


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        long time = System.nanoTime();
        double elapsedTime = (time - lastTime) / 1000000000.0;
        lastTime = time;

        mousePicker.update(world, camera.getTransform(getWidth(), getHeight()), worldScale);
        world.update(elapsedTime);

        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        AffineTransform tranform = g2d.getTransform();
        g2d.setTransform(camera.getTransform(getWidth(), getHeight()));
        g2d.scale(1, -1);

        for (GameObject gameObject : gameObjects) {
            gameObject.draw(g2d);
        }

        if (showDebug.isSelected())
            DebugDraw.draw(g2d, world, worldScale);

        g2d.setTransform(tranform);
    }
}
