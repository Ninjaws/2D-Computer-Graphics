package entity;

import org.dyn4j.dynamics.Body;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Rectangle;
import org.dyn4j.geometry.Vector2;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class GameObject {
    private BufferedImage image;
    private Body body;
    private double scaleX;
    private double scaleY;
    private double worldScale;
    private Vector2 offset;


    public GameObject(BufferedImage image, Body body, Vector2 offset, Circle circle, double worldScale) {
        this.body = body;
        this.offset = offset;
        this.image = image;
        this.scaleX = (circle.getRadius() * 2 * worldScale) / image.getWidth();
        this.scaleY = (circle.getRadius() * 2 * worldScale) / image.getHeight();
        this.worldScale = worldScale;
    }

    public GameObject(BufferedImage image, Body body, Vector2 offset, Rectangle rect, double worldScale) {
        this.body = body;
        this.offset = offset;
        this.image = image;
        this.scaleX = (rect.getWidth() * worldScale) / image.getWidth();
        this.scaleY = (rect.getHeight() * worldScale) / image.getHeight();
        this.worldScale = worldScale;
    }

    public void draw(Graphics2D g2d) {
        AffineTransform transform = new AffineTransform();
        transform.translate(body.getTransform().getTranslationX() * worldScale, body.getTransform().getTranslationY() * worldScale);
        transform.rotate(body.getTransform().getRotation());
        transform.scale(scaleX, -scaleY);
        transform.translate(offset.x, offset.y);
        transform.translate(-image.getWidth() / 2, -image.getHeight() / 2);

        g2d.drawImage(image, transform, null);

    }
}
