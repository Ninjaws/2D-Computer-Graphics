package objects;

import data.Simulator;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

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
        //   this.angle = 0;
        this.vector = new Point2D.Double(0, 0);
        this.bounceCollision = false;
    }

    public void move(long deltaTime) {


        Point currentTile = getOccupyingTile(position);

        if (!Simulator.getInstance().getDestination().getDistanceMap().isNotInitialized(currentTile) && Simulator.getInstance().getDestination().getDistanceMap().getTiles()[currentTile.y][currentTile.x].getVector().equals(new Point2D.Double(0, 0))) {
            // if (Simulator.getInstance().getDestination().getDistanceMap().getTiles()[currentTile.y][currentTile.x].getVector().equals(new Point2D.Double(0,0))) {
            double angle = Math.random() * Math.PI * 2;
            setVector(new Point2D.Double(Math.cos(angle) / 2, Math.sin(angle) / 2));
            //   }

            //     else
            //        setTargetVector(Simulator.getInstance().getDestination().getDistanceMap().getTiles()[currentTile.y][currentTile.x].getVector());

        } else

            setTargetVector(Simulator.getInstance().getDestination().getDistanceMap().getTiles()[currentTile.y][currentTile.x].getVector());


        if (Simulator.getInstance().getTileMap().isAWall(currentTile) || Simulator.getInstance().getDestination().getDistanceMap().isNotInitialized(currentTile))
            return;

        Point2D vectorDiff = new Point2D.Double(vector.getX() - targetVector.getX(), vector.getY() - targetVector.getY());

        if (vectorDiff.getX() < 0)
            vector.setLocation(vector.getX() + 0.001 * velocity * deltaTime, vector.getY());
        else if (vectorDiff.getX() > 0)
            vector.setLocation(vector.getX() - 0.001 * velocity * deltaTime, vector.getY());

        if (vectorDiff.getY() < 0)
            vector.setLocation(vector.getX(), vector.getY() + 0.001 * velocity * deltaTime);
        else if (vectorDiff.getY() > 0)
            vector.setLocation(vector.getX(), vector.getY() - 0.001 * velocity * deltaTime);

        /*
        if (Simulator.getInstance().getDestination().getDistanceMap().getTiles()[currentTile.y][currentTile.x].getDistance() == 0)
        {
            int valuex = (int)Math.round(Math.random()*2-1);
            int valuey = (int)Math.round(Math.random()*2-1);
            vector.setLocation(0.02, -0.02);
    }
    */


        Point2D newPosition = new Point2D.Double(
                position.getX() + (velocity * deltaTime) * vector.getX(), position.getY() + (velocity * deltaTime) * vector.getY());


        Point targetTile = getOccupyingTile(newPosition);


        Point direction = getDirection(currentTile, targetTile);


        if (Simulator.getInstance().getTileMap().isAWall(targetTile) || !Simulator.getInstance().getTileMap().isInsideMap(targetTile) || Simulator.getInstance().getDestination().getDistanceMap().getTiles()[targetTile.y][targetTile.x].getVector() == new Point2D.Double(0, 0)) {


            if (bounceCollision) {
                if (direction.x > 0 || direction.x < 0)
                    vector.setLocation(vector.getX() * -0.5, vector.getY());
                if (direction.y > 0 || direction.y < 0)
                    vector.setLocation(vector.getX(), vector.getY() * -0.5);

            } else {

                //Right
                if (direction.x > 0) {
                    System.out.println("Hitting right wall");

                    //BottomRight
                    if (direction.y > 0) {

                        //Colliding with the right wall
                        if (Simulator.getInstance().getTileMap().isAWall(new Point(currentTile.x + direction.x, currentTile.y)))
                            vector.setLocation(0, vector.getY());
                            //Colliding with bottom wall
                        else
                            vector.setLocation(vector.getX(), 0);

                    }

                    //TopRight
                    else if (direction.y < 0) {

                        //Colliding with the right wall
                        if (Simulator.getInstance().getTileMap().isAWall(new Point(currentTile.x + direction.x, currentTile.y)))
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
                } else if (direction.x < 0) {
                    System.out.println("Hitting left wall");

                    //BottomLeft
                    if (direction.y > 0) {
                        //Colliding with the left wall
                        if (Simulator.getInstance().getTileMap().isAWall(new Point(currentTile.x - direction.x, currentTile.y)))
                            vector.setLocation(0, vector.getY());
                            //Colliding with bottom wall
                        else
                            vector.setLocation(vector.getX(), 0);
                    }

                    //Topleft
                    else if (direction.y < 0) {

                        //Colliding with the left wall
                        if (Simulator.getInstance().getTileMap().isAWall(new Point(currentTile.x - direction.x, currentTile.y)))
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
                else if (direction.y > 0) {
                    System.out.println("Hitting bottom wall");
                    vector.setLocation(vector.getX(), 0);//vector.getY() * -0.75);
                }

                //Top
                else {
                    System.out.println("Hitting top wall");
                    vector.setLocation(vector.getX(), 0);//vector.getY() * -0.75);
                }

            }

            newPosition = new Point2D.Double(
                    position.getX() + (velocity * deltaTime) * vector.getX(), position.getY() + (velocity * deltaTime) * vector.getY());

        }

        setPosition(newPosition);
/*

        Particle collidingParticle = hasCollision(Simulator.getInstance().getParticles());
        if (collidingParticle == null)
            setPosition(newPosition);
        else {
            vector.setLocation(collidingParticle.getVector().getX() * -1, collidingParticle.getVector().getY() * -1);
            newPosition = new Point2D.Double(
                    position.getX() + (velocity * deltaTime) * vector.getX(), position.getY() + (velocity * deltaTime) * vector.getY());

            setPosition(newPosition);

        }
        */

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


    public Particle hasCollision(ArrayList<Particle> particles) {
        boolean hasCollision = false;
        Particle collidingParticle = null;
        for (Particle p : particles) {
            if (p.equals(this))
                continue;

            double distance = position.distance(p.position);
            if (distance < (radius + p.radius)) {
                hasCollision = true;
                collidingParticle = p;
            }
        }

        return collidingParticle;
    }


    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.5f));
        g2d.fill(new Ellipse2D.Double(position.getX() - radius, position.getY() - radius, radius * 2, radius * 2));
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
