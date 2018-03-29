package presentation.frames.menus;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;

public class ControlsFrame extends JFrame {

    public ControlsFrame() {
        super("Controls");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        buildPanel();

        setSize(600, 200);
        setResizable(false);
    }


    public void buildPanel() {
        JPanel content = new JPanel(new BorderLayout());


        String[] columnNames = {"Key(s)", "Action(s)"};
        String[][] data =
                {{" LMB (Click/Drag)", "Mark location as target on the map for the particles"},
                        {" RMB (Click/Drag)", "Build a wall on the position of the mouse cursor"},
                        {" MMB (Click/Drag)", "Remove a wall on the position of the mouse cursor"},
                        {" LMB+CTRL (Click)", "Spawn 1000 particles at the tiles surrounding the mouse cursor"},
                        {" LMB+CTRL (Drag)", "Spawn a particle on the position of the mouse cursor"}};


        JTable table = new JTable(data, columnNames);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(150);
        table.getColumnModel().getColumn(1).setPreferredWidth(430);
        table.setRowHeight(20);

        JScrollPane pane = new JScrollPane(table);

        content.add(pane);

        super.setContentPane(content);
    }

}
