import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class VerletPanel extends JPanel implements ActionListener {

    ArrayList<Particle> particles = new ArrayList<>();

    public VerletPanel() {
        for (int i = 0; i < 20; i++) {
            particles.add(new Particle(new Point2D.Double(100 + i * 10, 200)));
        }

        Timer timer = new Timer(1000 / 60, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
    }

    private void update() {
        for (Particle particle : particles) {
            particle.update();
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Particle p : particles)
            p.draw(g2d);


    }


}
