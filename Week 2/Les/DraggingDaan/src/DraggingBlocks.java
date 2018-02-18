import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Collections;

public class DraggingBlocks extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    // private Shadows shadows;
    // private boolean castShadows = true;
    private ArrayList<Rectangle2D> squares = new ArrayList<>();
    private int squareSize = 100;
    private Camera camera = new Camera();
    private Point2D position = new Point2D.Double(0, 0);
    private Point2D offset = new Point2D.Double(0, 0);
    private Point2D dragPoint = new Point2D.Double(0, 0);
    private boolean dragScreen = false;
    private boolean startedInSquare = false;
    private int selectedSquare = -1;

    DraggingBlocks() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addMouseWheelListener(this);
        //    this.shadows = new Shadows(this);
        resetSquares();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setTransform(camera.cameraTransform);
        g2d.setStroke(new BasicStroke((float) (2 + camera.zoom / 50), BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));

        // Cast shadows if the toggle is turned on
      /*
        if (this.castShadows) {
            // Paint shadows
            float[] fractions = {0.1f, 0.9f};
            Color[] colors = {Color.BLACK, new Color(238, 238, 238)};

            g2d.setPaint(new RadialGradientPaint(
                    (int) shadows.getLightSource().getX(), (int) shadows.getLightSource().getY(),    //center
                    5000, //radius
                    (float) shadows.getLightSource().getX(), (float) shadows.getLightSource().getY(), //focal point
                    fractions, colors, MultipleGradientPaint.CycleMethod.NO_CYCLE));

            ArrayList<GeneralPath> array = shadows.castShadow();
            for (GeneralPath shadow : array) {
                g2d.fill(shadow);
            }

            // Paint lightsource
            int lightRadius = 20;
            Ellipse2D light = new Ellipse2D.Double(shadows.getLightSource().getX() - lightRadius / 2,
                    shadows.getLightSource().getY() - lightRadius / 2, lightRadius, lightRadius);
            g2d.setColor(Color.YELLOW);
            g2d.fill(light);
            g2d.setColor(Color.DARK_GRAY);
            g2d.draw(light);

        }
        */

        // Draw squares
        g2d.setStroke(new BasicStroke((float) (2 + camera.zoom / 50)));
        float index = 0;
        Collections.reverse(this.squares);
        for (Rectangle2D square : squares) {
            Color color = Color.getHSBColor((index - squares.size()) / squares.size(), 1f, 1f);
            g2d.setColor(color);
            g2d.fill(square);
            g2d.setColor(Color.BLACK);
            g2d.draw(square);
            index++;
        }
        Collections.reverse(this.squares);
    }

    // Calculate the world point coordinates instead of the camera point coordinates
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
        this.squares.clear();
        int[] y = new int[]{200, 400, 550, 650, 700, 700, 650, 550, 400, 200};
        for (int i = 1; i <= 10; i++) {
            Rectangle2D square = new Rectangle2D.Double(i * 150, y[i - 1], this.squareSize, this.squareSize);
            this.squares.add(square);
        }
        repaint();
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
        // Check if the right mouse button stopped dragging
        if (SwingUtilities.isRightMouseButton(e))
            this.dragScreen = false;
    }

    public void mousePressed(MouseEvent e) {
        // Check if the left mouse button is pressed on a square
        if (SwingUtilities.isLeftMouseButton(e)) {
            this.position = getGoodPoint(e.getPoint());
            this.startedInSquare = false;
            this.selectedSquare = -1;
            int index = 0;
            for (Rectangle2D square : squares) {
                if (square.contains(this.position)) {
                    this.startedInSquare = true;
                    this.selectedSquare = index;
                    this.offset.setLocation(square.getCenterX() - this.position.getX(),
                            square.getCenterY() - this.position.getY());
                    return;
                }
                index++;
                this.dragScreen = false;
            }
            // Check if the right mouse button has started dragging the screen
        } else if (SwingUtilities.isRightMouseButton(e)) {
            this.dragScreen = true;
            this.dragPoint = getGoodPoint(e.getPoint());
        }
        this.startedInSquare = false;
        this.selectedSquare = -1;
    }

    public void mouseDragged(MouseEvent e) {
        this.position = getGoodPoint(e.getPoint());
        // Drag the selected square along with the mouse
        if (this.startedInSquare) {
            squares.get(this.selectedSquare).setRect(position.getX() + this.offset.getX() - (this.squareSize) / 2,
                    position.getY() + this.offset.getY() - (this.squareSize) / 2, this.squareSize, this.squareSize);
            repaint();
            // Drag the screen along with the mouse
        } else if (this.dragScreen) {
            camera.cameraPosition.setLocation(this.position.getX() - this.dragPoint.getX(), this.position.getY() - this.dragPoint.getY());

            Point2D p1 = e.getPoint();
            Point2D p2 = getGoodPoint(p1);
            camera.cameraTransform.setToIdentity();
            camera.cameraTransform.translate(p1.getX(), p1.getY());
            camera.cameraTransform.scale(camera.zoom, camera.zoom);
            camera.cameraTransform.translate(-p2.getX() + camera.cameraPosition.getX(), -p2.getY() + camera.cameraPosition.getY());
            DraggingBlocks.this.repaint();

            repaint();
        } else {
            this.selectedSquare = -1;
        }
    }

    public ArrayList<Rectangle2D> getSquares() {
        return squares;
    }
/*
    public void setCastShadows(boolean castShadows) {
        this.castShadows = castShadows;
        repaint();
    }
*/
    // Calculate zoom value when scrolled
    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        camera.mousePoint = e.getPoint();

        if (e.getPreciseWheelRotation() > 0)
            camera.zoom *= 0.9;
        else
            camera.zoom *= 1.1;
        camera.zoom = Math.max(0.1, camera.zoom);
        camera.zoom = Math.min(10, camera.zoom);
        camera.calculate();
        DraggingBlocks.this.repaint();
    }

    // Keeps track of the camera position (pan and zoom)
    private class Camera {
        private Point2D cameraPosition = new Point2D.Double(0, 0);
        private double zoom = 1;
        private Point2D mousePoint;
        private AffineTransform cameraTransform = new AffineTransform();

        // Recalculates the AffineTransform of the camera
        private void calculate() {
            Point2D p2 = DraggingBlocks.this.getGoodPoint(this.mousePoint);

            camera.cameraTransform.setToIdentity();
            camera.cameraTransform.translate(this.mousePoint.getX(), this.mousePoint.getY());
            camera.cameraTransform.scale(camera.zoom, camera.zoom);
            camera.cameraTransform.translate(-p2.getX(), -p2.getY());
        }
    }
}