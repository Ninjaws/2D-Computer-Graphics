package presentation.frames;

import data.Simulator;
import javafx.scene.control.ColorPicker;

import javax.swing.*;
import java.awt.*;
import java.beans.EventHandler;

public class OptionsPanel extends JPanel {

    private JCheckBox showHeatMap;
    private JCheckBox showDistanceMap;
    private JCheckBox showVectorField;

    private JCheckBox setBounceCollision;

    private JButton resetButton;
    private JButton chooseColorButton;


    private boolean insideDialog;

    public OptionsPanel(StatisticsPanel statisticsPanel) {

        insideDialog = false;

        JPanel gridPanel = new JPanel(new GridLayout(7, 1));

        gridPanel.add(showHeatMap = new JCheckBox("Show Heat Map", true));
        gridPanel.add(showDistanceMap = new JCheckBox("Show Distance Map", true));
        gridPanel.add(showVectorField = new JCheckBox("Show Vector Field", true));
        gridPanel.add(new JPanel());
        gridPanel.add(setBounceCollision = new JCheckBox("Set Bounce Collision", true));

        resetButton = new JButton("Reset Field");
        resetButton.addActionListener(e -> {
            Simulator.getInstance().deleteParticles();
            Simulator.getInstance().getTileMap().resetMap();
            Simulator.getInstance().getDestination().getDistanceMap().updateDistance();
            statisticsPanel.updateParticleLabel();
        });
        gridPanel.add(resetButton);

        chooseColorButton = new JButton("Set Particle Color");
        chooseColorButton.addActionListener(e ->
        {
            insideDialog = true;
            ColorChooserDialog dialog = new ColorChooserDialog("Choose a color", Simulator.getInstance().getParticleColor());
            ColorChooserDialog.DialogResult result = dialog.getResult();
            if (result != null) {
                System.out.println("Color - " + result.getColor());
                Simulator.getInstance().setParticleColor(result.getColor());
            }
            insideDialog = false;
        });
        gridPanel.add(chooseColorButton);


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

    public boolean setBounceCollision() {
        return setBounceCollision.isSelected();
    }


    public boolean isInsideDialog() {
        return insideDialog;
    }

}
