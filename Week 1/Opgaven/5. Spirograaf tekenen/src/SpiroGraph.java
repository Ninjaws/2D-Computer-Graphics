import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;

/**
 * @author Ian Vink
 * @version 1.2
 * @date 27-01-2018
 */

public class SpiroGraph extends JPanel {

    private Arm arm1;
    private Arm arm2;
    private Arm arm3;

    private ArrayList<Arm> arms;

    private boolean antiAliasingEnabled;


    public SpiroGraph() {

        arm1 = new Arm(1, 1, true);
        arm2 = new Arm(1, 1, true, arm1);
        arm3 = new Arm(1, 1, true, arm2);

        arms = new ArrayList<>();
        arms.add(arm1);
        arms.add(arm2);
        arms.add(arm3);
        antiAliasingEnabled = false;
    }

    public void setAntiAliasingEnabled(boolean antiAliasingEnabled) {
        this.antiAliasingEnabled = antiAliasingEnabled;
    }

    public boolean isAntiAliasingEnabled() {
        return antiAliasingEnabled;
    }

    public void setArmLength(double length1, double length2, double length3) {
        arm1.setLength(length1);
        arm2.setLength(length2);
        arm3.setLength(length3);
    }

    public void setArmDeltaAngle(double angle1, double angle2, double angle3) {
        arm1.setDeltaAngle(angle1);
        arm2.setDeltaAngle(angle2);
        arm3.setDeltaAngle(angle3);
    }


    public Arm getArm1() {
        return arm1;
    }

    public Arm getArm2() {
        return arm2;
    }

    public Arm getArm3() {
        return arm3;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (antiAliasingEnabled)
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        else
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        g2d.translate(getWidth() / 2, getHeight() / 2);
        g2d.scale(1, -1);

        double scale = 100;

        for (Arm arm : arms) {
            arm.calculatePosition(0);
            arm.setStartPosition();
        }

        // In the unlikely event that the break below doesn't trigger (the shape isn't completed),
        // there is a maximum amount of iterations to prevent it from getting stuck in an infinite loop
        for (int i = 1; i < 5000000; i++) {

            int counter = 0;
            for (Arm arm : arms) {
                if (counter == 0)
                    g2d.setColor(new Color(0, 0, 0));
                else if (counter == 1)
                    g2d.setColor(new Color(255, 113, 10));
                else if (counter == 2)
                    g2d.setColor(new Color(0, 4, 211));


                arm.setPreviousPosition();
                arm.calculatePosition(i);

                if (arm.isVisible()) {
                    g2d.draw(new Line2D.Double(arm.getOldX() * scale, arm.getOldY() * scale,
                            arm.getX() * scale, arm.getY() * scale));
                }

                counter++;

            }
            if (arm3.hasCompletedShape())
                break;

        }

    }
}
