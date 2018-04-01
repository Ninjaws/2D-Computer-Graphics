package presentation.frames.simulator;

import data.Simulator;
import map.MapLoader;
import presentation.frames.menus.AboutFrame;
import presentation.frames.menus.ControlsFrame;
import presentation.frames.options.OptionsPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class SimulatorFrame extends JFrame {

    private boolean paused;

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
        content.add(new SimulatorPanel(width, height, optionsPanel, statisticsPanel,this), BorderLayout.CENTER);


        super.setContentPane(content);
    }

    public void buildMenu() {
        JMenuBar bar = new JMenuBar();


        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Save Map");
        saveItem.addActionListener(e -> {
paused = true;

            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "MAPOBJ Files", "mapobj");
            fileChooser.setFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setApproveButtonText("Save");

            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {

                MapLoader.writeMapToObjFile(Simulator.getInstance().getTileMap(), fileChooser.getSelectedFile());
            }
            paused = false;
        });
        fileMenu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load Map");
        loadItem.addActionListener(e -> {
paused = true;
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "MAPOBJ Files", "mapobj");
            fileChooser.setFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setApproveButtonText("Load");

            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {

                Simulator.getInstance().setTileMap(MapLoader.loadMapFromObjFile(fileChooser.getSelectedFile()));
                Simulator.getInstance().deleteParticles();
                Simulator.getInstance().getDestination().getDistanceMap().updateDistance();
            }

paused = false;
        });
        fileMenu.add(loadItem);

        bar.add(fileMenu);

        ControlsFrame controlsFrame = new ControlsFrame();
        JMenu helpMenu = new JMenu("Info");
        JMenuItem controlsItem = new JMenuItem("Controls");
        controlsItem.addActionListener(e ->
        {
            controlsFrame.setVisible(true);
        });
        helpMenu.add(controlsItem);

        AboutFrame aboutFrame = new AboutFrame();
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> {
            aboutFrame.setVisible(true);
        });
        helpMenu.add(aboutItem);

        bar.add(helpMenu);


        super.setJMenuBar(bar);

    }

    public boolean isPaused() {
        return paused;
    }
}
