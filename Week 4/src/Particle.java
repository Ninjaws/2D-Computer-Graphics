import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Particle {

    private Point2D position;
    private Point2D previousPosition;

    public Particle(Point2D position) {
        this.position = position;
        this.previousPosition = new Point2D.Double(position.getX(), position.getY());
    }


    public void update() {

        Point2D newPrevPos = position;

        Point2D deltaPosition = new Point2D.Double(position.getX() - previousPosition.getX(), position.getY() - previousPosition.getY());

        position = new Point2D.Double(position.getX() + deltaPosition.getX(), position.getY() + deltaPosition.getY());

        previousPosition = newPrevPos;
    }


    public void draw(Graphics2D g2d) {
        Shape circle = new Ellipse2D.Double(position.getX(), position.getY(), 20, 20);
        g2d.setColor(Color.CYAN);
        g2d.fill(circle);
        g2d.setColor(Color.BLACK);
        g2d.draw(circle);
    }
}
