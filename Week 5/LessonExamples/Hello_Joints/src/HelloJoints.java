
import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.BodyFixture;
import org.dyn4j.dynamics.World;
import org.dyn4j.dynamics.joint.*;
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

        Body slingshot = new Body();
        slingshot.addFixture(new Circle(0.1));
        slingshot.translate(-0,-0.75);
        slingshot.setMass(MassType.NORMAL);
        world.addBody(slingshot);



        Body center = new Body();
        center.addFixture(new Circle(0.1));
        center.translate(0,-0.5);
        center.setMass(MassType.INFINITE);
        world.addBody(center);


        Body top = new Body();
        top.addFixture(new Circle(0.1));
        center.translate(0,-1.0);
        center.setMass(MassType.INFINITE);
        world.addBody(top);

/*
        Body arm = new Body();
        arm.addFixture(new Rectangle(0.2, 2));
        arm.translate(0, -0.3);
        arm.setMass(MassType.NORMAL);
        world.addBody(arm);
        */

      //  System.out.println(circle.getWorldCenter());

       // PulleyJoint joint3 = new PulleyJoint(circle2, floor, )

     //   RevoluteJoint joint = new RevoluteJoint(floor, circle2, circle2.getWorldCenter());


     //   WheelJoint joint = new WheelJoint(floor, circle2, circle2.getWorldCenter(), new Vector2(0, -1));//(floor, arm, new Vector2(0,-2.8));
        //    joint.setCollisionAllowed(false);
     //   joint.setCollisionAllowed(true);
     //  world.addJoint(joint);


   //     RevoluteJoint joint2 = new RevoluteJoint(circle2, circle, circle2.getWorldCenter());

     //   joint2.setCollisionAllowed(false);
  //      world.addJoint(joint2);
   //     RopeJoint joint2 = new RopeJoint(circle, circle2, circle.getWorldCenter(),circle2.getWorldCenter());

        WheelJoint joint2 = new WheelJoint(center, slingshot, center.getWorldCenter(),new Vector2(-1,0));
//joint2.setCollisionAllowed(true);
       // joint2.setCollisionAllowed(true);
        world.addJoint(joint2);

        WheelJoint joint3 = new WheelJoint(top, slingshot, slingshot.getWorldCenter(),new Vector2(-1,0));
    //    joint3.setCollisionAllowed(true);
        world.addJoint(joint3);
/*
        Body ball = new Body();
        ball.addFixture(new Circle(0.1));
        ball.setMass(MassType.NORMAL);
        world.addBody(ball);
*/

        /*



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
*/

        lastTime = System.nanoTime();
        new Timer(15, this).start();
        camera = new Camera(this);
        mousePicker = new MousePicker(this);
        System.out.println("Been here");
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
