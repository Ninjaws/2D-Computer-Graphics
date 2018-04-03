package presentation.frames.simulator;

import calculations.AdditiveComposite;
import calculations.BasicComposite;
import data.Simulator;
import objects.Particle;
import presentation.components.DebugDraw;
import presentation.frames.options.OptionsPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @author Ian Vink
 */

public class SimulatorPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

    private long deltaTime;
    private long startTime;
    private long endTime;

    private boolean paused;

    private Simulator simulator;
    private OptionsPanel optionsPanel;
    private StatisticsPanel statisticsPanel;
    private SimulatorFrame frame;

    public SimulatorPanel(OptionsPanel optionsPanel, StatisticsPanel statisticsPanel, SimulatorFrame frame) {

        startTime = 0;
        endTime = 0;
        deltaTime = 0;

        paused = false;

        simulator = Simulator.getInstance();
        this.optionsPanel = optionsPanel;
        this.statisticsPanel = statisticsPanel;
        this.frame = frame;

        addMouseListener(this);
        addMouseMotionListener(this);
        new Timer(1000 / 60, this).start();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        simulator.getTileMap().drawMap(g2d);

        DebugDraw.getInstance().draw(g2d, optionsPanel.showHeatMap(), optionsPanel.showDistanceMap(), optionsPanel.showVectorField());


        //Composite oldComp = g2d.getComposite();

        //TODO: Figure out why AlphaComposite (and anything else that is not a self-made Composite) lags
        //TODO: when you switch away from a self-made Composite
        if (Simulator.getInstance().isUsingAdditiveBlending()) {
            g2d.setComposite(new AdditiveComposite());
        } else {
            g2d.setComposite(new BasicComposite());
          //  g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        }
        for (Particle p : simulator.getParticles()) {

            p.draw(g2d);
        }
        //g2d.setComposite(oldComp);


    }


    @Override
    public void actionPerformed(ActionEvent e) {

        endTime = System.currentTimeMillis();
        deltaTime = endTime - startTime;
        startTime = System.currentTimeMillis();

        paused = optionsPanel.isPaused();

        if (paused)
            return;
        if (frame.isPaused())
            return;

        for (Particle p : simulator.getParticles()) {
            p.move(deltaTime);
        }


        repaint();


    }

    @Override
    public void mouseClicked(MouseEvent e) {

        //Single press of a button
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (e.isControlDown()) {
                simulator.spawnAmountOfParticles(e.getPoint());
                statisticsPanel.updateParticleLabel();
            } else
                simulator.getDestination().getDistanceMap().calculateDistance(e.getPoint());
        } else if (SwingUtilities.isRightMouseButton(e)) {
            simulator.getTileMap().buildWall(e.getPoint());
            simulator.getDestination().getDistanceMap().updateDistance();
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            simulator.getTileMap().removeWall(e.getPoint());
            simulator.getDestination().getDistanceMap().updateDistance();
        }
        repaint();
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
            if (e.isControlDown()) {
                simulator.spawnParticle(e.getPoint());
                statisticsPanel.updateParticleLabel();
            } else
                simulator.getDestination().getDistanceMap().calculateDistance(e.getPoint());
        } else if (SwingUtilities.isRightMouseButton(e)) {
            simulator.getTileMap().buildWall(e.getPoint());
            simulator.getDestination().getDistanceMap().updateDistance();
        } else if (SwingUtilities.isMiddleMouseButton(e)) {
            simulator.getTileMap().removeWall(e.getPoint());
            simulator.getDestination().getDistanceMap().updateDistance();
        }
        repaint();
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public boolean isPaused() {
        return paused;
    }

    public void setPaused(boolean paused) {
        this.paused = paused;
    }
}
