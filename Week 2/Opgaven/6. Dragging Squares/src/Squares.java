import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;


public class Squares extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private ArrayList<Rectangle2D> squares = new ArrayList<>();

    private Camera camera = new Camera();

    private Point2D mousePosition = new Point2D.Double(0, 0);
    private Point2D squareCorrectionValue = new Point2D.Double(0, 0);
    private Point2D dragPoint = new Point2D.Double(0, 0);
    private boolean dragScreen = false;
    private boolean squarePressed = false;
    private int pressedSquareIndex = -1;
    private int size = 50;

    public Squares() {
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);

        resetSquares();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setTransform(camera.cameraTransform);

        Collections.reverse(squares);
        for (int i = 0; i < squares.size(); i++) {
            g2d.setColor(Color.getHSBColor((1.0f / squares.size()) * i, 1, 1));
            g2d.fill(squares.get(i));
        }
        Collections.reverse(squares);

    }

    public Point2D getGoodPoint(Point2D point2D) {
        try {
            return camera.cameraTransform.inverseTransform(point2D, null);
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void resetView() {
        camera.cameraPosition.setLocation(0, 0);
        camera.zoom = 1;
        camera.cameraTransform.setToIdentity();
        repaint();
    }

    public void resetSquares() {
        squares.clear();

        int margin = 5;
        for (int i = 0; i < 5; i++)
            squares.add(new Rectangle2D.Double((size + margin) * i, 0, size, size));

        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if (SwingUtilities.isLeftMouseButton(e)) {
            mousePosition = getGoodPoint(e.getPoint());
            squarePressed = false;
            pressedSquareIndex = -1;

            for (int i = 0; i < squares.size(); i++) {

                if (squares.get(i).contains(mousePosition)) {
                    squarePressed = true;
                    pressedSquareIndex = i;
                     squareCorrectionValue.setLocation(mousePosition.getX() - squares.get(i).getX(),
                              mousePosition.getY() - squares.get(i).getY());
                    return;
                }
                dragScreen = false;
            }
        } else if (SwingUtilities.isRightMouseButton(e)) {
            dragScreen = true;
            dragPoint = getGoodPoint(e.getPoint());
        }
        squarePressed = false;
        pressedSquareIndex = -1;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (SwingUtilities.isRightMouseButton(e))
            dragScreen = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mousePosition = getGoodPoint(e.getPoint());

        if (squarePressed) {
               squares.get(pressedSquareIndex).setRect(mousePosition.getX() - squareCorrectionValue.getX(),
                       mousePosition.getY() - squareCorrectionValue.getY(), squares.get(pressedSquareIndex).getWidth(),
                       squares.get(pressedSquareIndex).getHeight());

            repaint();
        } else if (dragScreen) {
            camera.cameraPosition.setLocation(mousePosition.getX() - dragPoint.getX(), mousePosition.getY() - dragPoint.getY());

            Point2D p1 = e.getPoint();
            Point2D p2 = getGoodPoint(p1);
            camera.cameraTransform.setToIdentity();
            camera.cameraTransform.translate(p1.getX(), p1.getY());
            camera.cameraTransform.scale(camera.zoom, camera.zoom);
            camera.cameraTransform.translate(-p2.getX() + camera.cameraPosition.getX(), -p2.getY() + camera.cameraPosition.getY());
            repaint();
        } else {
            pressedSquareIndex = -1;
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        camera.mousePoint = e.getPoint();

        if (e.getPreciseWheelRotation() > 0)
            camera.zoom *= 0.9;
        else
            camera.zoom *= 1.1;
        camera.zoom = Math.max(0.3, camera.zoom);
        camera.zoom = Math.min(1.0, camera.zoom);
        camera.calculate();
        repaint();
    }

    private class Camera {
        private Point2D cameraPosition = new Point2D.Double(0, 0);
        private double zoom = 1;
        private Point2D mousePoint;
        private AffineTransform cameraTransform = new AffineTransform();

        private void calculate() {
            Point2D p2 = Squares.this.getGoodPoint(this.mousePoint);

            camera.cameraTransform.setToIdentity();
            camera.cameraTransform.translate(this.mousePoint.getX(), this.mousePoint.getY());
            camera.cameraTransform.scale(camera.zoom, camera.zoom);
            camera.cameraTransform.translate(-p2.getX(), -p2.getY());
        }
    }

}


