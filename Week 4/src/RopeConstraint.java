import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class RopeConstraint extends Constraint {

    private double distance;
    private Particle a;
    private Particle b;
    private float stressFactor;
    private Color stressColor;

    public RopeConstraint(Particle a, Particle b) {
        this.a = a;
        this.b = b;
        distance = a.getPosition().distance(b.getPosition());

        setStressFactor(distance);
    }

    public RopeConstraint(Particle a, Particle b, double distance) {
        this.a = a;
        this.b = b;
        this.distance = distance;
        setStressFactor((float) (a.getPosition().distance(b.getPosition()) / distance));
    }

    @Override
    void satisfy() {
        double currentDistance = a.getPosition().distance(b.getPosition());

        if (currentDistance > distance) {

            double adjustmentDistance = (currentDistance - distance) / 2;

            Point2D ba = new Point2D.Double(b.getPosition().getX() - a.getPosition().getX(),
                    b.getPosition().getY() - a.getPosition().getY());
            double length = ba.distance(0, 0);

            if (length > 0.0001)
                ba = new Point2D.Double(ba.getX() / length, ba.getY() / length);
            else
                ba = new Point2D.Double(1, 0);

            a.setPosition(new Point2D.Double(a.getPosition().getX() + ba.getX() * adjustmentDistance,
                    a.getPosition().getY() + ba.getY() * adjustmentDistance));

            b.setPosition(new Point2D.Double(b.getPosition().getX() - ba.getX() * adjustmentDistance,
                    b.getPosition().getY() - ba.getY() * adjustmentDistance));
        }
        setStressFactor(currentDistance);
    }

    @Override
    void draw(Graphics2D g2d) {
        Shape line = new Line2D.Double(a.getPosition().getX(), a.getPosition().getY(), b.getPosition().getX(), b.getPosition().getY());
        g2d.setColor(stressColor);
        g2d.draw(line);
    }

    public void setStressFactor(double currentDistance) {
        stressFactor = (float) (currentDistance / distance);

        if (stressFactor <= 1)
            stressColor = new Color(0, 255, 0, 255);
        else {
            int colorValue = (int) ((stressFactor - 1) * 2000);
            if (colorValue > 255)
                colorValue = 255;
            stressColor = new Color(colorValue, 255 - colorValue, 0, 255);
        }

    }

}
