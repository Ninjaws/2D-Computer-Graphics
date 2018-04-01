/**
 * @author Ian Vink
 * @version 1.1
 * @date 27-01-2018
 * <p>
 * The arm of the spirograph, which is connected to another arm to draw shapes
 * The first arm is connected to the origin, with the second one being connected to it, etc
 */

public class Arm {
    private Arm parentArm;

    private double length;
    // The angle at which the arm turns every iteration (negative is clockwise, positive is counterclockwise)
    private double deltaAngle;
    private boolean visible;

    private double x, y, oldX, oldY;

    private double startX, startY;

    /**
     * Constructor without parent position, for the arm connected to the origin point (the first arm)
     *
     * @param length     length of the arm
     * @param deltaAngle angle of the arm
     * @param visible    whether the arm is visible
     */
    public Arm(double length, double deltaAngle, boolean visible) {
        this.length = length;
        this.deltaAngle = deltaAngle;
        this.visible = visible;
        this.parentArm = null;

        calculatePosition(0);
        setStartPosition();
    }

    public Arm(double length, double deltaAngle, boolean visible, Arm parentArm) {
        this.length = length;
        this.deltaAngle = deltaAngle;
        this.visible = visible;
        this.parentArm = parentArm;

        calculatePosition(0);
        setStartPosition();
    }

    public void setStartPosition() {
        startX = x;
        startY = y;
    }

    public void setPreviousPosition() {
        oldX = x;
        oldY = y;
    }


    public void calculatePosition(int currentIteration) {
        double currentAngle = Math.toRadians(deltaAngle * currentIteration);

        if (parentArm == null) {
            x = Math.cos(currentAngle) * length;
            y = Math.sin(currentAngle) * length;
        } else {
            x = parentArm.getX() + Math.cos(currentAngle) * length;
            y = parentArm.getY() + Math.sin(currentAngle) * length;
        }
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getDeltaAngle() {
        return deltaAngle;
    }

    public void setDeltaAngle(double deltaAngle) {
        this.deltaAngle = deltaAngle;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean hasCompletedShape() {
        double margin = 0.000000001;
        return (startX >= x - margin && startX <= x + margin
                && startY >= y - margin && startY <= y + margin);
    }
}