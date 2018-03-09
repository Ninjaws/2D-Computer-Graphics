import org.dyn4j.dynamics.Body;
import org.dyn4j.dynamics.World;
import org.dyn4j.geometry.Circle;
import org.dyn4j.geometry.Convex;
import org.dyn4j.geometry.Vector2;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class GameObject {

    Body body;
    BufferedImage image;
    Vector2 offset;
    double scaleX;
    double scaleY;

    public GameObject(String imageFile, Body body, Vector2 offset, double scale) {
        this.body = body;
        this.offset = offset;
        //this.scale = scale;

        try {
            image = ImageIO.read(getClass().getResource(imageFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.scaleX = scale;
        this.scaleY = scale;
    }

    public GameObject(String imageFile, Body body, Vector2 offset, org.dyn4j.geometry.Rectangle rect, double worldScale) {
        this.body = body;
        this.offset = offset;
        //this.scale = scale;

        try {
            image = ImageIO.read(getClass().getResource(imageFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.scaleX = (rect.getWidth()*worldScale)/image.getWidth();
        this.scaleY = (rect.getHeight()*worldScale)/image.getHeight();
    }

    public GameObject(String imageFile, Body body, Vector2 offset, Circle circle, double worldScale) {
        this.body = body;
        this.offset = offset;
        //this.scale = scale;

        try {
            image = ImageIO.read(getClass().getResource(imageFile));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.scaleX = (circle.getRadius()*2*worldScale)/image.getWidth();
        this.scaleY = (circle.getRadius()*2*worldScale)/image.getHeight();
    }




    public void draw(Graphics2D g2d) {
        if (image == null)
            return;

        AffineTransform transform = new AffineTransform();
        transform.translate(body.getTransform().getTranslationX() * 100, body.getTransform().getTranslationY() * 100);
        transform.rotate(body.getTransform().getRotation());
        transform.scale(scaleX, -scaleY);
        transform.translate(offset.x, offset.y);

        transform.translate(-image.getWidth() / 2, -image.getHeight() / 2);

        g2d.drawImage(image, transform, null);
    }

}
