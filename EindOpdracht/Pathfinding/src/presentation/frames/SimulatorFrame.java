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
        StatisticsPanel statisticsPanel = new StatisticsPanel();

        content.add(optionsPanel, BorderLayout.WEST);
        content.add(statisticsPanel,BorderLayout.NORTH);
        content.add(new SimulatorPanel(width, height, optionsPanel, statisticsPanel), BorderLayout.CENTER);


        super.setContentPane(content);
    }

}
