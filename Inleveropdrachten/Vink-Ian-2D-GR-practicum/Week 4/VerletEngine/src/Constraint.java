import java.awt.*;
import java.io.Serializable;

public abstract class Constraint implements Serializable {

    abstract void satisfy();

    abstract void draw(Graphics2D g2d);
}