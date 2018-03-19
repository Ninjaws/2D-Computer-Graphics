package data;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Tile {
    private int distance;
    private Point2D vector;
    private Color color;


    public Tile() {
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public Point2D getVector() {
        return vector;
    }

    public void setVector(Point2D vector) {
        this.vector = vector;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
