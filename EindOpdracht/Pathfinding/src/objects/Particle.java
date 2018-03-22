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
    private double angle;


    public Particle(double radius, Point2D position, Color color) {
        this.radius = radius;
        this.position = position;
        this.color = color;
        this.angle = 0;
    }

    public void move(long deltaTime) {


        Point currentTile = getOccupyingTile(position);
        setTargetVector(Simulator.getInstance().getDestination().getDistanceMap().getTiles()[currentTile.y][currentTile.x].getVector());

        if(!Simulator.getInstance().getTileMap().isAWall(currentTile) && Simulator.getInstance().getDestination().getDistanceMap().isNotInitialized(currentTile))
            return;

       // Point2D lastPos = new Point2D.Double(position.getX(), position.getY());

        // System.out.println(vector);
        //   Point2D deltaPosition = new Point2D.Double(vector.getX() * velocity, vector.getY() * velocity);

        //   position.setLocation(new Point2D.Double(position.getX() + deltaPosition.getX(), position.getY() + deltaPosition.getY()));


        //    int x = (int) position.getX() / Simulator.getInstance().getTileMap().getTileSize();
        //   int y = (int) position.getY() / Simulator.getInstance().getTileMap().getTileSize();

        double targetAngle = Math.atan2(targetVector.getY(), targetVector.getX());


        Point2D vectorDiff = new Point2D.Double(vector.getX() - targetVector.getX(), vector.getY() - targetVector.getY());

        double angleDiff = angle - targetAngle;
/*
        while (angleDiff < -Math.PI)
            angleDiff += 2 * Math.PI;
        while (angleDiff > Math.PI)
            angleDiff -= 2 * Math.PI;*/

        if (angleDiff < 0)
            angle += 0.0002 * deltaTime; //0.002
        else if (angleDiff > 0)
            angle -= 0.0002 * deltaTime;


        Point2D newPosition = new Point2D.Double(
                position.getX() + (velocity * deltaTime) * Math.cos(angle), position.getY() + (velocity * deltaTime) * Math.sin(angle));


        Point targetTile = getOccupyingTile(newPosition);


        Point direction = getDirection(currentTile,targetTile);

        if(Simulator.getInstance().getTileMap().isAWall(targetTile)){
            //Rechts
           if(direction.x == 1){
               System.out.println("Hitting right wall");

           }
           else if (direction.x == -1){
               System.out.println("Hitting left wall");
           }
           else if (direction.y == 1){
               System.out.println("Hitting bottom wall");
           }
           else{
               System.out.println("Hitting top wall");
           }

        }



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

      //  System.out.println(angle);
        if (Simulator.getInstance().getTileMap().isAWall(targetTile)) {
            System.out.println("Hit");
            //position = lastPos;
            angle *= -1;

        }else{
            setPosition(newPosition);
        }
      //  System.out.println(angle);

    }


    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fill(new Ellipse2D.Double(position.getX() - radius / 2, position.getY() - radius / 2, radius, radius));
    }

    private Point getOccupyingTile(Point2D point){
        int x = (int) point.getX() / Simulator.getInstance().getTileMap().getTileSize();
        int y = (int) point.getY() / Simulator.getInstance().getTileMap().getTileSize();
    return new Point(x,y);

    }

    private Point getDirection(Point currentPoint, Point targetPoint){

        return new Point(targetPoint.x - currentPoint.x,targetPoint.y - currentPoint.y);
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

    public double getAngle() {
        return angle;
    }

    private void setAngle(double angle) {
        this.angle = angle;
    }
}
