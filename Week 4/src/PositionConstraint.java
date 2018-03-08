import java.awt.*;
import java.awt.geom.Point2D;

public class PositionConstraint extends Constraint {

    private Particle particle;
    private Point2D fixedPosition;

    public PositionConstraint(Particle particle) {
        this.particle = particle;
        if (particle != null)
            fixedPosition = particle.getPosition();

    }

    @Override
    void satisfy() {
        if (particle != null)
            particle.setPosition(fixedPosition);
    }

    @Override
    void draw(Graphics2D g2d) {

    }


    public Particle getParticle() {
        return particle;
    }

    public void setParticle(Particle particle) {
        this.particle = particle;
        if(particle != null)
            fixedPosition = particle.getPosition();
    }

    public Point2D getFixedPosition() {
        return fixedPosition;
    }

    public void setFixedPosition(Point2D fixedPosition) {
        this.fixedPosition = fixedPosition;
    }
}
