import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

public class Particle implements Serializable {

    private Point2D position;
    private Point2D previousPosition;
    private int size;

    public Particle(Point2D position, int size) {
        this.position = position;
        this.previousPosition = new Point2D.Double(position.getX(), position.getY());
        this.size = size;
    }

    public void update(int width, int height) {

        Point2D newPrevPos = position;

        Point2D deltaPosition = new Point2D.Double(position.getX() - previousPosition.getX(), position.getY() - previousPosition.getY());

        position = new Point2D.Double(position.getX() + deltaPosition.getX(), position.getY() + deltaPosition.getY() + 0.2);
        previousPosition = newPrevPos;


        if (position.getX() < 0)
            position = new Point2D.Double(0, position.getY());
        else if (position.getX() > width)
            position = new Point2D.Double(width, position.getY());
        if (position.getY() < 0)
            position = new Point2D.Double(position.getX(), 0);
        else if (position.getY() > height)
            position = new Point2D.Double(position.getX(), height);

    }


    public void draw(Graphics2D g2d) {
        Shape circle = new Ellipse2D.Double(position.getX() - size / 2, position.getY() - size / 2, size, size);
        g2d.setColor(Color.ORANGE);
        g2d.fill(circle);
        g2d.setColor(Color.BLACK);
        g2d.draw(circle);
    }









    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Point2D getPreviousPosition() {
        return previousPosition;
    }

    public void setPreviousPosition(Point2D previousPosition) {
        this.previousPosition = previousPosition;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
