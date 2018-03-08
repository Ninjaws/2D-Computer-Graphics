import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public class ScreensaverPanel extends JPanel implements ActionListener {

    ArrayList<MovingPoint> points = new ArrayList<>();
    ArrayList<Line2D> lines = new ArrayList<>();
    private static final int AMOUNT_OF_LINES = 3000;


    public ScreensaverPanel(int fps, Point2D screenDimensions) {

        double width = screenDimensions.getX();
        double height = screenDimensions.getY();

        points.add(new MovingPoint(width / 4, height / 4, new Point2D.Double(-3, 2)));
        points.add(new MovingPoint(width / 4, (height / 4) * 3, new Point2D.Double(4, -2.3)));
        points.add(new MovingPoint((width / 4) * 3, height / 4, new Point2D.Double(-1.2, 3.5)));
        points.add(new MovingPoint((width / 4) * 3, (height / 4) * 3, new Point2D.Double(2.4, -3.1)));
     //   points.add(new MovingPoint((width / 4) * 2, (height / 4) * 3, new Point2D.Double(1.6, 2.1)));
     //   points.add(new MovingPoint((width / 4) * 3, (height / 4) * 2, new Point2D.Double(-2.3, -1.9)));




        Timer timer = new Timer(1000 / fps, this);
        timer.start();
    }

    public void update() {
        for (MovingPoint point : points) {
            point.updatePosition();

            if (point.getX() <= 0 || point.getX() >= getWidth())
                point.setMovement(new Point2D.Double(-point.getMovement().getX(), point.getMovement().getY()));
            if (point.getY() <= 0 || point.getY() >= getHeight())
                point.setMovement(new Point2D.Double(point.getMovement().getX(), -point.getMovement().getY()));


            for (int i = 0; i < points.size(); i++) {
                Line2D line = new Line2D.Double(points.get(i).getX(), points.get(i).getY(),
                        points.get((i + 1) % points.size()).getX(), points.get((i + 1) % points.size()).getY());
                lines.add(line);

                if (lines.size() > AMOUNT_OF_LINES)
                    lines.remove(0);
            }
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Rectangle2D rect = new Rectangle2D.Double(0,0,getWidth(),getHeight());
        g2d.setColor(Color.BLACK);
        g2d.fill(rect);

        g2d.setColor(new Color( 255,0,255,30));
        for (Line2D line : lines)
            g2d.draw(line);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }
}
