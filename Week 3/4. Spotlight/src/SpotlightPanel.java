import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class SpotlightPanel extends JPanel implements MouseListener, MouseMotionListener {

    private BufferedImage image;
    private Point mousePos;

    public SpotlightPanel() {
        ImageLoader loader = new ImageLoader();
        image = loader.loadImage("resources/gamble.jpg");

        mousePos = new Point();
        mousePos.setLocation(0, 0);

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos = new Point(e.getPoint());
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Ellipse2D.Double circle = new Ellipse2D.Double(mousePos.getX(), mousePos.getY(), 200, 200);
        circle.setFrame(circle.getX() -circle.getWidth()/2,circle.getY()-circle.getHeight()/2,
                circle.getWidth(),circle.getHeight());
        g2d.setClip(circle);
        System.out.println(getWidth());
        Rectangle2D rect = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
        g2d.setPaint(new TexturePaint(image, rect));
        g2d.fill(rect);
    }


    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }
}
