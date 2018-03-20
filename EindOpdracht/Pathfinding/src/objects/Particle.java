package objects;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Particle {

    private double radius;
    private Point2D position;

private Point2D vector;
    private double velocity = 2;
    private Color color;


    public Particle(double radius, Point2D position, Color color){
        this.radius = radius;
        this.position = position;
        this.color = color;
    }

    public void move(){

        System.out.println(vector);
        Point2D deltaPosition = new Point2D.Double(vector.getX()*velocity,vector.getY()*velocity);

        position.setLocation(new Point2D.Double(position.getX()+deltaPosition.getX(),position.getY()+deltaPosition.getY()));

    }

    public void draw(Graphics2D g2d){
        g2d.setColor(color);
        g2d.fill(new Ellipse2D.Double(position.getX(),position.getY(),radius,radius));
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
    }
}
