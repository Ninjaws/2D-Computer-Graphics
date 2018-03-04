import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class VerletPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    ArrayList<Particle> particles = new ArrayList<>();
    ArrayList<Constraint> constraints = new ArrayList<>();
    PositionConstraint mouseConstraint = new PositionConstraint(null);
    final static int POINT_SIZE = 30;


    public VerletPanel() {

        Particle p = new Particle(new Point2D.Double(300, 300), POINT_SIZE);
        addParticle(p, new PositionConstraint(p));

        addMouseListener(this);
        addMouseMotionListener(this);
        Timer timer = new Timer(1000 / 60, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
    }

    private void update() {
        for (Particle particle : particles) {
            particle.update(getWidth(), getHeight());
        }

        for (Constraint c : constraints) {
            c.satisfy();
        }

        mouseConstraint.satisfy();

        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Constraint c : constraints)
            c.draw(g2d);

        for (Particle p : particles)
            p.draw(g2d);


    }


    @Override
    public void mouseClicked(MouseEvent e) {

        //Left mouse button
        if (e.getButton() == MouseEvent.BUTTON1) {

            if (e.isControlDown()) {
                Particle newParticle = new Particle(e.getPoint(), POINT_SIZE);
                addParticle(newParticle, new PositionConstraint(newParticle));
            } else {
                if (particles.size() >= 1) {
                    Particle newParticle = new Particle(e.getPoint(), POINT_SIZE);
                    addParticle(newParticle, new RopeConstraint(newParticle, getNearest(e.getPoint())));
                }
            }


        }

        //Right mouse button
        else if (e.getButton() == MouseEvent.BUTTON3) {


            if (e.isShiftDown()) {
                if (particles.size() >= 2) {
                    ArrayList<Particle> closestParticles = getNearestPoints(e.getPoint());

                    constraints.add(new RopeConstraint(closestParticles.get(0), closestParticles.get(1)));
                }
            } else if (e.isControlDown()) {
                if (particles.size() >= 2) {
                    Particle newParticle = new Particle(e.getPoint(), POINT_SIZE);
                    addParticle(newParticle, new DistanceConstraint(newParticle, getNearest(e.getPoint()), 100));

                    ArrayList<Particle> closestParticles = getNearestPoints(e.getPoint());
                    constraints.add(new DistanceConstraint(newParticle, closestParticles.get(2), 100));
                }
            } else {
                if (particles.size() >= 2) {
                    Particle newParticle = new Particle(e.getPoint(), POINT_SIZE);
                    addParticle(newParticle, new RopeConstraint(newParticle, getNearest(e.getPoint())));

                    ArrayList<Particle> closestParticles = getNearestPoints(e.getPoint());
                    constraints.add(new RopeConstraint(newParticle, closestParticles.get(2)));
                }
            }
        }

    }

    public void addParticle(Particle particle, Constraint constraint) {
        if (constraint != null)
            constraints.add(constraint);
        particles.add(particle);

    }

    public void createCloth(int rows, int columns) {
        clearScreen();

        for (int i = 0; i < columns; i++) {
            Particle p = new Particle(new Point2D.Double(100 + (i % columns) * (POINT_SIZE * 1.5), 100 + (i / columns) * (POINT_SIZE * 1.5)), POINT_SIZE);


            if (i != 0) {
                constraints.add(new RopeConstraint(p, getNearest(p.getPosition())));
            }

            addParticle(p, new PositionConstraint(p));//RopeConstraint(p,getNearest(p.getPosition())));


        }

        for (int i = 0; i < (rows - 1) * columns; i++) {
            Particle p = new Particle(new Point2D.Double(100 + (i % columns) * (POINT_SIZE * 1.5), 100 + (POINT_SIZE * 1.5) + (i / columns) * (POINT_SIZE * 1.5)), POINT_SIZE);

            ArrayList<Particle> nearestParticles = getNearestPoints(p.getPosition());

            addParticle(p, new RopeConstraint(p, nearestParticles.get(0)));
            if (i % columns != 0)
                constraints.add(new RopeConstraint(p, nearestParticles.get(1)));
        }

        Collections.reverse(constraints);

    }

    public void clearScreen() {
        constraints.clear();
        particles.clear();

    }


    public Particle getNearest(Point2D point) {
        Particle nearest = particles.get(0);
        for (Particle p : particles) {
            if (p.getPosition().distance(point) < nearest.getPosition().distance(point))
                nearest = p;
        }
        return nearest;
    }

    public ArrayList<Particle> getNearestPoints(Point2D point) {
        ArrayList<Particle> sorted = new ArrayList<>();

        sorted.addAll(particles);

        Collections.sort(sorted, new Comparator<Particle>() {
            @Override
            public int compare(Particle o1, Particle o2) {
                return (int) (o1.getPosition().distance(point) - o2.getPosition().distance(point));
            }
        });

        return sorted;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (particles.size() >= 1) {
            Particle nearest = getNearest(e.getPoint());
            if (nearest.getPosition().distance(e.getPoint()) < nearest.getSize())
                mouseConstraint.setParticle(nearest);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseConstraint.setParticle(null);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseConstraint.setFixedPosition(e.getPoint());
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void saveState() throws Exception {
        File file = null;
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("resources/"));
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();

            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file));

            //  ArrayList<Particle> p = particles;
            //  ArrayList<Constraint> c = constraints;

            oos.writeObject(particles);
            oos.writeObject(constraints);
            oos.close();

        }


    }

    public void loadState() throws Exception {
        File file = null;
        JFileChooser chooser = new JFileChooser();
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
/*
            ArrayList<Object> generic = new ArrayList<>();
            generic.addAll((Collection<?>) ois.readObject());
            particles.clear();
            particles.addAll(generic);

            generic.clear();
            generic.addAll((ArrayList<Constraint>) ois.readObject());
            constraints.clear();
            constraints.addAll((ArrayList<Constraint>) generic);
            */
            //    ArrayList<Particle> p = (ArrayList<Particle>) ois.readObject();


            particles.clear();
            particles.addAll((ArrayList<Particle>) ois.readObject());
            constraints.clear();
            constraints.addAll((ArrayList<Constraint>) ois.readObject());

            ois.close();

        }
    }
}
