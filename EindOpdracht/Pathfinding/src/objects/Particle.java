package objects;

import data.Simulator;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class Particle {

    private double radius;
    private Point2D position;

    private Point2D vector;
    private Point2D targetVector;

    private double velocity = 0.5;
    private Color color;

    private boolean bounceCollision;


    public Particle(double radius, Point2D position, Color color) {
        this.radius = radius;
        this.position = position;
        this.color = color;
        this.vector = new Point2D.Double(0, 0);
        this.bounceCollision = false;
    }

    public Particle(double radius, Point2D position) {
        this.radius = radius;
        this.position = position;
        this.vector = new Point2D.Double(0, 0);
        this.bounceCollision = false;
    }

    /**
     * Moves the particle to a position depending on the vector of the tile it is standing on
     *
     * @param deltaTime The amount of time that has passed, to make sure the particles always move at the same speed
     */
    public void move(long deltaTime) {

        Point currentTile = getOccupyingTile(position);

        /*
        if (!Simulator.getInstance().getTileMap().isInsideMap(currentTile))
            return;

        if (Simulator.getInstance().getTileMap().isAWall(currentTile) || Simulator.getInstance().getDestination().getDistanceMap().isNotInitialized(currentTile))
            return;
*/

        if (Simulator.getInstance().getDestination().getDistanceMap().isNotInitialized(currentTile))
            return;

        setTargetVector(Simulator.getInstance().getDestination().getDistanceMap().getTiles()[currentTile.y][currentTile.x].getVector());


        if (!Simulator.getInstance().getDestination().getDistanceMap().isNotInitialized(currentTile) && Simulator.getInstance().getDestination().getDistanceMap().getTiles()[currentTile.y][currentTile.x].getVector().equals(new Point2D.Double(0.0, 0.0))) {
            setTargetVector(Simulator.getInstance().getDestination().getDistanceMap().getTiles()[currentTile.y][currentTile.x].getVector());
            double angle = 0;
            while ((angle >= 60 && angle <= 120) || (angle >= 150 && angle <= 210) || (angle >= 240 && angle <= 300) || (angle >= 330) || (angle <= 30)) {
          //      System.out.println(angle);
                angle = Math.random() * 360;// Math.PI * 2;
            }
            setVector(new Point2D.Double(Math.cos(Math.toRadians(angle)) / 1.5, Math.sin(Math.toRadians(angle)) / 1.5));
        } else {
            setTargetVector(Simulator.getInstance().getDestination().getDistanceMap().getTiles()[currentTile.y][currentTile.x].getVector());
        }


        Point2D vectorDiff = new Point2D.Double(vector.getX() - targetVector.getX(), vector.getY() - targetVector.getY());

        if (vectorDiff.getX() < 0)
            vector.setLocation(vector.getX() + 0.001 * velocity * deltaTime, vector.getY());
        else if (vectorDiff.getX() > 0)
            vector.setLocation(vector.getX() - 0.001 * velocity * deltaTime, vector.getY());

        if (vectorDiff.getY() < 0)
            vector.setLocation(vector.getX(), vector.getY() + 0.001 * velocity * deltaTime);
        else if (vectorDiff.getY() > 0)
            vector.setLocation(vector.getX(), vector.getY() - 0.001 * velocity * deltaTime);


        Point2D newPosition = new Point2D.Double(
                position.getX() + (velocity * deltaTime) * vector.getX(), position.getY() + (velocity * deltaTime) * vector.getY());


        Point targetTile = getOccupyingTile(newPosition);


        Point direction = getDirection(currentTile, targetTile);


        if (!Simulator.getInstance().getTileMap().isInsideMap(targetTile) || Simulator.getInstance().getTileMap().isAWall(targetTile)
                || Simulator.getInstance().getDestination().getDistanceMap().isNotInitialized(targetTile)){// || Simulator.getInstance().getDestination().getDistanceMap().getTiles()[targetTile.y][targetTile.x].getVector() == new Point2D.Double(0, 0)) {

            if (bounceCollision) {
                if (direction.x > 0 || direction.x < 0)
                    vector.setLocation(vector.getX() * -0.5, vector.getY());
                if (direction.y > 0 || direction.y < 0)
                    vector.setLocation(vector.getX(), vector.getY() * -0.5);

                newPosition = new Point2D.Double(
                        position.getX() + (velocity * deltaTime) * vector.getX(), position.getY() + (velocity * deltaTime) * vector.getY());


                targetTile = getOccupyingTile(newPosition);

                //Secondary check to make sure the bounce doesn't make it go out of bounds
                if (!Simulator.getInstance().getTileMap().isInsideMap(targetTile) || Simulator.getInstance().getTileMap().isAWall(targetTile)
                        || Simulator.getInstance().getDestination().getDistanceMap().isNotInitialized(targetTile)){ //|| Simulator.getInstance().getDestination().getDistanceMap().getTiles()[targetTile.y][targetTile.x].getVector() == new Point2D.Double(0, 0)) {
                    vector.setLocation(0, 0);
                } else {
                    setPosition(newPosition);
                }


            } else {
                vector.setLocation(0, 0);
            }

        } else {
            setPosition(newPosition);
        }

    }


    public boolean hasCollision(ArrayList<Particle> particles) {
        boolean hasCollision = false;
     //   Particle collidingParticle = null;
        for (Particle p : particles) {
            if (p.equals(this))
                continue;

            double distance = position.distance(p.position);
            if (distance < (radius + p.radius)) {
                hasCollision = true;
         //       collidingParticle = p;
            }
        }

        return hasCollision;
    }


    public void draw(Graphics2D g2d) {

        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));

        if (Simulator.getInstance().getParticleTexture() != null) {

            Rectangle2D rect = new Rectangle2D.Double(position.getX() - radius, position.getY() - radius, radius * 2, radius * 2);

            g2d.setPaint(new TexturePaint(Simulator.getInstance().getParticleTexture(), rect));
            g2d.fill(rect);


        } else {
            g2d.setColor(Simulator.getInstance().getParticleColor());

            g2d.fill(new Ellipse2D.Double(position.getX() - radius, position.getY() - radius, radius * 2, radius * 2));

        }

    }

    private Point getOccupyingTile(Point2D point) {
        int x = (int) point.getX() / Simulator.getInstance().getTileMap().getTileSize();
        int y = (int) point.getY() / Simulator.getInstance().getTileMap().getTileSize();
        return new Point(x, y);

    }

    private Point getDirection(Point currentPoint, Point targetPoint) {

        return new Point(targetPoint.x - currentPoint.x, targetPoint.y - currentPoint.y);
    }


    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public Point2D getVector() {
        return vector;
    }

    public void setVector(Point2D vector) {
        this.vector = vector;
        //  setAngle(Math.atan2(this.vector.getY(),this.vector.getX()));
    }

    public Point2D getTargetVector() {
        return targetVector;
    }

    public void setTargetVector(Point2D targetVector) {
        this.targetVector = targetVector;
    }


    public boolean isBounceCollision() {
        return bounceCollision;
    }

    public void setBounceCollision(boolean bounceCollision) {
        this.bounceCollision = bounceCollision;
    }
}
