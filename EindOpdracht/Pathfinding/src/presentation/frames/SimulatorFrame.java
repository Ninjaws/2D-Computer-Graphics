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


        buildMenu();
        buildPanel(width, height);

        setSize(width, height);
        setVisible(true);
    }

    public void buildPanel(int width, int height) {
        JPanel content = new JPanel(new BorderLayout());


        StatisticsPanel statisticsPanel = new StatisticsPanel();
        OptionsPanel optionsPanel = new OptionsPanel(statisticsPanel);

        content.add(statisticsPanel, BorderLayout.NORTH);
        content.add(optionsPanel, BorderLayout.WEST);
        content.add(new SimulatorPanel(width, height, optionsPanel, statisticsPanel), BorderLayout.CENTER);


        super.setContentPane(content);
    }

    public void buildMenu() {
        JMenuBar bar = new JMenuBar();


        JMenu fileMenu = new JMenu("File");
        fileMenu.addActionListener(e -> {

        });
        bar.add(fileMenu);


        JMenu helpMenu = new JMenu("Help");
        JMenuItem controlsItem = new JMenuItem("Controls");
        controlsItem.addActionListener(e ->
        {

        });
        helpMenu.add(controlsItem);

        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> {

        });
        helpMenu.add(aboutItem);

        bar.add(helpMenu);


        super.setJMenuBar(bar);

    }

}
