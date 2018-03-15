import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Main extends JPanel implements MouseListener, MouseMotionListener {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Radial Square");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));
      //  frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setContentPane(new Main());
        frame.setVisible(true);
    }

    Main() {
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    public Point2D position = new Point2D.Double(100, 100);

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


        float[] fractions = new float[3];
        fractions[0] = 0.1f;
        fractions[1] = 0.5f;
        fractions[2] = 0.8f;
        Color[] colours = new Color[3];
        colours[0] = Color.getHSBColor(0.1f,1,1);//new Color(255, 0, 0);
        colours[1] = Color.getHSBColor(0.5f,1,1);//new Color(0, 255, 0);
        colours[2] = Color.getHSBColor(0.9f,1,1);//new Color(255, 255, 255);
        float radius = 100;


        RadialGradientPaint rgp = new RadialGradientPaint(new Point2D.Double(getWidth()/2,getHeight()/2), radius,position,
                fractions, colours, MultipleGradientPaint.CycleMethod.NO_CYCLE);


        Rectangle2D rect = new Rectangle2D.Double(0, 0, getWidth(), getHeight());

        g2d.setPaint(rgp);
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

    @Override
    public void mouseMoved(MouseEvent e) {
        position = e.getPoint();
        repaint();
    }
}
