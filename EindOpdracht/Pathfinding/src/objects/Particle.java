package objects;

import data.Simulator;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Particle {

    private double radius;
    private Point2D position;

    private Point2D vector;
    private Point2D targetVector;

    private double velocity = 0.1;
    private Color color;
    //  private double angle;


    public Particle(double radius, Point2D position, Color color) {
        this.radius = radius;
        this.position = position;
        this.color = color;
        //   this.angle = 0;
        this.vector = new Point2D.Double(0, 0);
    }

    public void move(long deltaTime) {


        Point currentTile = getOccupyingTile(position);
        setTargetVector(Simulator.getInstance().getDestination().getDistanceMap().getTiles()[currentTile.y][currentTile.x].getVector());

        if (Simulator.getInstance().getTileMap().isAWall(currentTile) || Simulator.getInstance().getDestination().getDistanceMap().isNotInitialized(currentTile))
            return;

        Point2D vectorDiff = new Point2D.Double(vector.getX() - targetVector.getX(), vector.getY() - targetVector.getY());

        if (vectorDiff.getX() < 0)
            vector.setLocation(vector.getX() + 0.005 * deltaTime, vector.getY());
        else if (vectorDiff.getX() > 0)
            vector.setLocation(vector.getX() - 0.005 * deltaTime, vector.getY());

        if (vectorDiff.getY() < 0)
            vector.setLocation(vector.getX(), vector.getY() + 0.005 * deltaTime);
        else if (vectorDiff.getY() > 0)
            vector.setLocation(vector.getX(), vector.getY() - 0.005 * deltaTime);


        Point2D newPosition = new Point2D.Double(
                position.getX() + (velocity * deltaTime) * vector.getX(), position.getY() + (velocity * deltaTime) * vector.getY());


        Point targetTile = getOccupyingTile(newPosition);


        Point direction = getDirection(currentTile, targetTile);

        if (Simulator.getInstance().getTileMap().isAWall(targetTile)) {

            //Right
            if (direction.x == 1) {
                System.out.println("Hitting right wall");

                //BottomRight
                if (direction.y == 1) {

                    //Colliding with the right wall
                    if (Simulator.getInstance().getTileMap().isAWall(new Point(currentTile.x + 1, currentTile.y)))
                        vector.setLocation(0, vector.getY());
                        //Colliding with bottom wall
                    else
                        vector.setLocation(vector.getX(), 0);

                }

                //TopRight
                else if (direction.y == -1) {

                    //Colliding with the right wall
                    if (Simulator.getInstance().getTileMap().isAWall(new Point(currentTile.x + 1, currentTile.y)))
                        vector.setLocation(0, vector.getY());
                        //Colliding with top wall
                    else
                        vector.setLocation(vector.getX(), 0);
                }

                //Right
                else {
                    vector.setLocation(0/*vector.getX() * -0.75*/, vector.getY());
                }

                //Left
            } else if (direction.x == -1) {
                System.out.println("Hitting left wall");

                //BottomLeft
                if (direction.y == 1) {
                    //Colliding with the left wall
                    if (Simulator.getInstance().getTileMap().isAWall(new Point(currentTile.x - 1, currentTile.y)))
                        vector.setLocation(0, vector.getY());
                        //Colliding with bottom wall
                    else
                        vector.setLocation(vector.getX(), 0);
                }

                //Topleft
                else if (direction.y == -1) {

                    //Colliding with the left wall
                    if (Simulator.getInstance().getTileMap().isAWall(new Point(currentTile.x - 1, currentTile.y)))
                        vector.setLocation(0, vector.getY());
                        //Colliding with top wall
                    else
                        vector.setLocation(vector.getX(), 0);
                }

                //Left
                else {
                    vector.setLocation(0/*vector.getX() * -0.75*/, vector.getY());
                }
            }

            //Bottom
            else if (direction.y == 1) {
                System.out.println("Hitting bottom wall");
                vector.setLocation(vector.getX(), 0);//vector.getY() * -0.75);
            }

            //Top
            else {
                System.out.println("Hitting top wall");
                vector.setLocation(vector.getX(), 0);//vector.getY() * -0.75);
            }

            newPosition = new Point2D.Double(
                    position.getX() + (velocity * deltaTime) * vector.getX(), position.getY() + (velocity * deltaTime) * vector.getY());

        } else {
            // setPosition(newPosition);
        }

        setPosition(newPosition);
    //    System.out.println(vector);
/*

        boolean collision = false;
        for (Particle p : Simulator.getInstance().getParticles()) {
            if (p.equals(this))
                continue;

            if (p.getPosition().distance(position) < p.getRadius() / 2 + radius / 2) {
                collision = true;
                break;
            }

        }

        if (collision)
            position = lastPos;
*/
/*
      //  System.out.println(angle);
        if (Simulator.getInstance().getTileMap().isAWall(targetTile)) {
            System.out.println("Hit");
            //position = lastPos;
            angle *= -1;

        }else{
            setPosition(newPosition);
        }
      //  System.out.println(angle);
*/
    }


    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fill(new Ellipse2D.Double(position.getX() - radius / 2, position.getY() - radius / 2, radius, radius));
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

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

}
