package presentation.frames;

import data.Simulator;

import javax.swing.*;
import java.awt.*;

public class SimulatorFrame extends JFrame {

    public SimulatorFrame() {
        super("Simulator");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        int xMargin = 200;
        int yMargin = 100;

        int width = Simulator.getInstance().getTileMap().getTiles()[0].length * Simulator.getInstance().getTileMap().getTileSize() + xMargin;
        int height = Simulator.getInstance().getTileMap().getTiles().length * Simulator.getInstance().getTileMap().getTileSize() + yMargin;


        buildPanel(width, height);

        setSize(width, height);
        setVisible(true);
    }

    public void buildPanel(int width, int height) {
        JPanel content = new JPanel(new BorderLayout());

        OptionsPanel optionsPanel = new OptionsPanel();

        content.add(optionsPanel, BorderLayout.WEST);
        content.add(new SimulatorPanel(width, height, optionsPanel), BorderLayout.CENTER);
        content.add(new StatisticsPanel(),BorderLayout.NORTH);

        super.setContentPane(content);
    }

}
