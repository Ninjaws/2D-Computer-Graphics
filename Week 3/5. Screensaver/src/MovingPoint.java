import java.awt.geom.Point2D;

public class MovingPoint {

    private Point2D movement;

    private double x;
    private double y;

    /**
     * Constructor for the point, takes a change in position
     *
     * @param x        The horizontal position of the point
     * @param y        The vertical position of the point
     * @param movement The change in position
     */
    public MovingPoint(double x, double y, Point2D movement) {
        this.x = x;
        this.y = y;

        this.movement = movement;

    }


    public void updatePosition() {
        this.x += movement.getX();
        this.y += movement.getY();
    }

    public Point2D getMovement() {
        return movement;
    }

    public void setMovement(Point2D movement) {
        this.movement = movement;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

}
