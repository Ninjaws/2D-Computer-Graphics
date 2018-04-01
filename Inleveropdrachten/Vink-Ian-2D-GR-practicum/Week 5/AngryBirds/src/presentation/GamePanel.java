package presentation;

import entity.GameObject;
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.joint.Joint;
import org.dyn4j.dynamics.joint.WheelJoint;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.MassType;
import org.dyn4j.geometry.Vector2;
import util.Camera;
import util.DebugDraw;
import util.MousePicker;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Ian Vink
 */

public class GamePanel extends JPanel implements ActionListener, MouseListener, KeyListener {

    private World world;
    private Camera camera;
    private MousePicker mousePicker;
    private double worldScale;
    private long lastTime;
    //JCheckBox showDebug;
    private boolean showDebug;

    //Shooting the bird
    private boolean shotFired;
    private Vector2 startingPosition;
    private Convex birdFixture;
    private double birdDensity;
    private double birdFriction;
    private double birdRestitution;
    private Body currentBody;
    private Joint slingshotJoint;
    private BufferedImage birdImage;
    private Body floor;

    private ArrayList<TexturePaint> backgroundPaints = new ArrayList<>();
    private ArrayList<Shape> backgroundObjects = new ArrayList<>();
    private ArrayList<GameObject> gameObjects = new ArrayList<>();

    public GamePanel() {
        world = new World();
        world.setGravity(new Vector2(0.0, -9.81));
        worldScale = 100;


        // JLabel tryAgainLabel = new JLabel("Try again with SPACE");
        // add(tryAgainLabel);


        //Background
        BufferedImage backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/images/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Rectangle2D backgroundRect = new Rectangle2D.Double(0, 0, 1000, 800);
        TexturePaint backgroundPaint = new TexturePaint(backgroundImage, backgroundRect);
        backgroundPaints.add(backgroundPaint);
        backgroundObjects.add(backgroundRect);
        //---


        //Slingshot
        //---

        //Objects

        BufferedImage tileset = null;
        try {
            tileset = ImageIO.read(getClass().getResource("/images/spritesheet.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Floor
        floor = new Body();
        org.dyn4j.geometry.Rectangle rectFloor = new org.dyn4j.geometry.Rectangle(20, 1);
        floor.addFixture(rectFloor);
        floor.translate(0, -3.5);
        floor.setMass(MassType.INFINITE);
        BufferedImage floorImage = tileset.getSubimage(167, 162, 83, 42);

        world.addBody(floor);
        gameObjects.add(new GameObject(floorImage, floor, new Vector2(-0.4, 0), rectFloor, worldScale));
        //---


        //Crates
        BufferedImage crateImage = tileset.getSubimage(0, 0, 85, 85);
        org.dyn4j.geometry.Rectangle rectCrate = new org.dyn4j.geometry.Rectangle(0.25, 0.25);
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10 - y; x++) {
                Body box = new Body();
                box.addFixture(rectCrate);
                box.setMass(MassType.NORMAL);
                box.translate(2 + x * 0.25 + 0.125 * y, -3 + y * 0.25);
                world.addBody(box);
                gameObjects.add(new GameObject(crateImage, box, new Vector2(0, 0), rectCrate, worldScale));
            }
        }
        //---

        // Bird
        try {
            birdImage = ImageIO.read(getClass().getResource("/images/bird3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        startingPosition = new Vector2(-2, -2);
        birdFixture = new Circle(0.2);
        birdDensity = 5;
        birdFriction = 0.0;
        birdRestitution = 0.25;

        currentBody = new Body();
        currentBody.addFixture(birdFixture, birdDensity, birdFriction, birdRestitution);
        currentBody.setMass(MassType.NORMAL);
        currentBody.translate(startingPosition);
        world.addBody(currentBody);
        gameObjects.add(new GameObject(birdImage, currentBody, new Vector2(0, 0), (Circle) birdFixture, worldScale));

        slingshotJoint = new WheelJoint(floor, currentBody, currentBody.getWorldCenter(), new Vector2(-1, 0));
        slingshotJoint.setCollisionAllowed(false);
        world.addJoint(slingshotJoint);
        //---


        camera = new Camera(this);
        mousePicker = new MousePicker(this);
        addMouseListener(this);
        addKeyListener(this);
        setFocusable(true);
        new Timer(15, this).start();
        lastTime = System.nanoTime();

        //     currentBody.setLinearVelocity(100, 0);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        long time = System.nanoTime();
        double elapsedTime = (time - lastTime) / 1000000000.0;
        lastTime = time;

        mousePicker.update(world, camera.getTransform(getWidth(), getHeight()), worldScale);
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

        if (backgroundPaints.size() == backgroundObjects.size())
            for (int i = 0; i < backgroundPaints.size(); i++) {
                g2d.setPaint(backgroundPaints.get(i));
                g2d.fill(backgroundObjects.get(i));
            }

        AffineTransform transform = g2d.getTransform();
        g2d.setTransform(camera.getTransform(getWidth(), getHeight()));
        g2d.scale(1, -1);


        for (GameObject gameObject : gameObjects) {
            gameObject.draw(g2d);
        }

        if (showDebug)
            DebugDraw.draw(g2d, world, worldScale);

        g2d.setTransform(transform);
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
    public void keyTyped(KeyEvent e) {
        if (e.getKeyChar() == KeyEvent.VK_SPACE) {

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }


    public void setShowDebug(boolean showDebug) {
        this.showDebug = showDebug;
    }

    public void shootBird(int angle, int velocity) {
        if(shotFired)
            resetBird();

        shotFired = true;
        double angleInRad = Math.toRadians(angle);
        Vector2 vector = new Vector2(-Math.cos(angleInRad) * velocity, Math.sin(angleInRad) * velocity);
        world.removeAllJoints();
        currentBody.setLinearVelocity(vector);
    }

    public void resetBird() {
        if (shotFired) {
            Body bird = new Body();
            bird.addFixture(birdFixture, birdDensity, birdFriction, birdRestitution);
            bird.setMass(MassType.NORMAL);
            bird.translate(startingPosition);
            world.addBody(bird);
            gameObjects.add(new GameObject(birdImage, bird, new Vector2(0, 0), (Circle) birdFixture, worldScale));

            currentBody = bird;

            Joint joint = new WheelJoint(floor, currentBody, currentBody.getWorldCenter(), new Vector2(-1, 0));
            world.addJoint(joint);
            shotFired = false;
        }
    }
}
