package presentation.frames;

import javax.swing.*;
import java.awt.*;

public class OptionsPanel extends JPanel {

    private JCheckBox showHeatMap;
    private JCheckBox showDistanceMap;
    private JCheckBox showVectorField;

    private JCheckBox setBounceCollision;

    public OptionsPanel() {

        JPanel gridPanel = new JPanel(new GridLayout(5, 1));

        gridPanel.add(showHeatMap = new JCheckBox("Show Heat Map",true));
        gridPanel.add(showDistanceMap = new JCheckBox("Show Distance Map",true));
        gridPanel.add(showVectorField = new JCheckBox("Show Vector Field",true));
        gridPanel.add(new JPanel());
        gridPanel.add(setBounceCollision = new JCheckBox("Set Bounce Collision",true));

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

    public boolean setBounceCollision(){
        return setBounceCollision.isSelected();
    }
}
