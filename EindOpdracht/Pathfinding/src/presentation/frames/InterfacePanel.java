package presentation.frames;

import javax.swing.*;
import java.awt.*;

public class InterfacePanel extends JPanel {

    private JCheckBox showHeatMap;
    private JCheckBox showDistanceMap;
    private JCheckBox showVectorField;

    public InterfacePanel(){

        JPanel gridPanel = new JPanel(new GridLayout(3,1));

        gridPanel.add(showHeatMap = new JCheckBox("Show Heat Map"));
        gridPanel.add(showDistanceMap = new JCheckBox("Show Distance Map"));
        gridPanel.add(showVectorField = new JCheckBox("Show Vector Field"));

        add(gridPanel);
    }



    public boolean showHeatMap(){
        return showHeatMap.isSelected();
    }
    public boolean showDistanceMap(){
        return showDistanceMap.isSelected();
    }
    public boolean showVectorField(){
        return showVectorField.isSelected();
    }
}
