package presentation.frames;

import data.Simulator;
import objects.Particle;
import presentation.components.DebugDraw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;

public class SimulatorPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    private long deltaTime;
    private long startTime;
    private long endTime;

    Simulator simulator;
    OptionsPanel optionsPanel;


    public SimulatorPanel(int width, int height, OptionsPanel optionsPanel) {

        startTime = 0;
        endTime = 0;
        deltaTime = 0;

        simulator = Simulator.getInstance();
        this.optionsPanel = optionsPanel;

        addMouseListener(this);
        addMouseMotionListener(this);
        new Timer(1000 / 60, this).start();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        simulator.getTileMap().drawMap(g2d);

        DebugDraw.getInstance().draw(g2d, optionsPanel.showHeatMap(), optionsPanel.showDistanceMap(), optionsPanel.showVectorField());

        for (Particle p : simulator.getParticles())
            p.draw(g2d);


    }


    @Override
    public void actionPerformed(ActionEvent e) {

        endTime = System.currentTimeMillis();
        deltaTime = endTime - startTime;
        startTime = System.currentTimeMillis();

        for (Particle p : simulator.getParticles())
            p.move(deltaTime);


        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        //Single press of a button
        if (SwingUtilities.isLeftMouseButton(e)) {
            simulator.getDestination().getDistanceMap().calculateDistance(e.getPoint());
        } else if (SwingUtilities.isRightMouseButton(e)) {
            simulator.getTileMap().buildWall(e.getPoint());
            simulator.getDestination().getDistanceMap().updateDistance();
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            simulator.getTileMap().removeWall(e.getPoint());
            simulator.getDestination().getDistanceMap().updateDistance();
        }
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

        //Holding of a button
        if (SwingUtilities.isLeftMouseButton(e)) {
            simulator.getDestination().getDistanceMap().calculateDistance(e.getPoint());
        } else if (SwingUtilities.isRightMouseButton(e)) {
            simulator.getTileMap().buildWall(e.getPoint());
            simulator.getDestination().getDistanceMap().updateDistance();
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            simulator.getTileMap().removeWall(e.getPoint());
            simulator.getDestination().getDistanceMap().updateDistance();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
