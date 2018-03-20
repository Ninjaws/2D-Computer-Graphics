package presentation.frames;

import data.Simulator;
import presentation.components.DebugDraw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class SimulatorPanel extends JPanel implements ActionListener, MouseListener {

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
        new Timer(1000 / 60, this).start();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        simulator.getTileMap().drawMap(g2d);

        DebugDraw.getInstance().draw(g2d, optionsPanel.showHeatMap(), optionsPanel.showDistanceMap(), optionsPanel.showVectorField());

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        endTime = System.currentTimeMillis();
        deltaTime = endTime - startTime;
        startTime = System.currentTimeMillis();




        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        simulator.getDestination().getDistanceMap().calculateDistance(e.getPoint());


        //TODO: Build walls
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
}
