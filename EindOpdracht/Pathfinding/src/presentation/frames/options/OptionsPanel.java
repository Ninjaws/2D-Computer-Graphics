package presentation.frames.options;

import data.Simulator;
import presentation.frames.simulator.StatisticsPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

/**
 * @author Ian Vink
 */

public class OptionsPanel extends JPanel {

    private JCheckBox showHeatMap;
    private JCheckBox showDistanceMap;
    private JCheckBox showVectorField;

    private JCheckBox setBounceCollision;
    private JCheckBox setAdditiveBlending;

    private boolean paused;

    public OptionsPanel(StatisticsPanel statisticsPanel) {

        paused = false;

        JPanel gridPanel = new JPanel(new GridLayout(12, 1));

        gridPanel.add(showHeatMap = new JCheckBox("Show Heat Map", true));
        gridPanel.add(showDistanceMap = new JCheckBox("Show Distance Map", true));
        gridPanel.add(showVectorField = new JCheckBox("Show Vector Field", true));
        gridPanel.add(new JPanel());

        setBounceCollision = new JCheckBox("Use Bounce Collision",Simulator.getInstance().isUsingBounceCollision());
        setBounceCollision.addActionListener(e->{
            Simulator.getInstance().setUsingBounceCollision(setBounceCollision.isSelected());
        });
        gridPanel.add(setBounceCollision);


        setAdditiveBlending = new JCheckBox("Use Additive Blending", Simulator.getInstance().isUsingAdditiveBlending());
        setAdditiveBlending.addActionListener(e->{
            Simulator.getInstance().setUsingAdditiveBlending(setAdditiveBlending.isSelected());
        });
        gridPanel.add(setAdditiveBlending);

        gridPanel.add(new JPanel());

        JButton resetButton = new JButton("Reset Field");
        resetButton.addActionListener(e -> {
            Simulator.getInstance().deleteParticles();
            Simulator.getInstance().getTileMap().resetMap();
            Simulator.getInstance().getDestination().getDistanceMap().updateDistance();
            statisticsPanel.updateParticleLabel();
        });
        gridPanel.add(resetButton);

        JButton pauseButton = new JButton("(Un)Pause Simulation");
        pauseButton.addActionListener(e -> {
            if (paused)
                paused = false;
            else
                paused = true;
        });
        gridPanel.add(pauseButton);
        gridPanel.add(new JPanel());

        JButton chooseColorButton = new JButton("Set Particle Color");
        chooseColorButton.addActionListener(e ->
        {
            paused = true;
            ColorChooserDialog dialog = new ColorChooserDialog("Choose a color", Simulator.getInstance().getParticleColor());
            ColorChooserDialog.DialogResult result = dialog.getResult();
            if (result != null) {
                Simulator.getInstance().setParticleTexture(null);
                Simulator.getInstance().setParticleColor(result.getColor());
            }
            paused = false;
        });
        gridPanel.add(chooseColorButton);

        JButton chooseImageButton = new JButton("Set Particle Image");
        chooseImageButton.addActionListener(e -> {
            paused = true;

            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "JPG & PNG Images", "jpg", "png");
            fileChooser.setFileFilter(filter);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {

                Simulator.getInstance().setParticleTexture(fileChooser.getSelectedFile());
            }

            paused = false;
        });
        gridPanel.add(chooseImageButton);

        add(gridPanel);
    }


    public boolean showHeatMap() {
        return showHeatMap.isSelected();
    }

    public boolean showDistanceMap() {
        return showDistanceMap.isSelected();
    }

    public boolean showVectorField() {
        return showVectorField.isSelected();
    }

    public boolean getBounceCollision() {
        return setBounceCollision.isSelected();
    }

    public boolean getAdditiveBlending() {
        return setAdditiveBlending.isSelected();
    }

    public boolean isPaused() {
        return paused;
    }

}
