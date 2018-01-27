import javax.swing.*;
import java.awt.*;

/**
 * @author Ian Vink
 * @version 1.1
 * The GUI that supports the drawing of the spirograph
 * @date 26-01-2018
 */


public class Gui {
    private JFrame frame;
    private SpiroGraph spiroGraph;


    public Gui() {
        frame = new JFrame("Spirograph");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        buildPanel();

        frame.setSize(800, 800);
        frame.setVisible(true);
    }

    public void buildPanel() {
        JPanel content = new JPanel(new BorderLayout());
        JPanel northPanel = new JPanel(new FlowLayout());
        JPanel southPanel = new JPanel(new FlowLayout());

        JPanel armLengthPanel = new JPanel(new FlowLayout());
        JPanel anglePanel = new JPanel(new FlowLayout());
        JPanel gridPanel = new JPanel(new GridLayout(2, 0));

        spiroGraph = new SpiroGraph();

        JLabel arm1LengthLabel = new JLabel("Length of arm 1:");
        JTextField arm1LengthField = new JTextField("1.0", 2);
        JLabel arm2LengthLabel = new JLabel("Length of arm 2:");
        JTextField arm2LengthField = new JTextField("1.0", 2);
        JLabel arm3LengthLabel = new JLabel("Length of arm 3:");
        JTextField arm3LengthField = new JTextField("1.0", 2);

        JLabel angle1Label = new JLabel("Change in the angle of arm 1:");
        JTextField angle1Field = new JTextField("0.70", 3);
        JLabel angle2Label = new JLabel("Change in the angle of arm 2:");
        JTextField angle2Field = new JTextField("0.01", 3);
        JLabel angle3Label = new JLabel("Change in the angle of arm 3:");
        JTextField angle3Field = new JTextField("0.01", 3);

        spiroGraph.setArmLength(Double.parseDouble(arm1LengthField.getText()),
                Double.parseDouble(arm2LengthField.getText()),
                Double.parseDouble(arm3LengthField.getText()));
        spiroGraph.setArmDeltaAngle(Double.parseDouble(angle1Field.getText()),
                Double.parseDouble(angle2Field.getText()),
                Double.parseDouble(angle3Field.getText()));

        JButton drawButton = new JButton("Draw");
        drawButton.addActionListener(e ->
        {
            try {
                spiroGraph.setArmLength(Double.parseDouble(arm1LengthField.getText()),
                        Double.parseDouble(arm2LengthField.getText()),
                        Double.parseDouble(arm3LengthField.getText()));
                spiroGraph.setArmDeltaAngle(Double.parseDouble(angle1Field.getText()),
                        Double.parseDouble(angle2Field.getText()),
                        Double.parseDouble(angle3Field.getText()));
                spiroGraph.repaint();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input. Only enter numbers.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        JButton arm1Button = new JButton("Set arm 1 (in)visible");
        arm1Button.addActionListener(e ->
        {
            if (spiroGraph.getArm1().isVisible())
                spiroGraph.getArm1().setVisible(false);
            else
                spiroGraph.getArm1().setVisible(true);
            spiroGraph.repaint();
        });
        JButton arm2Button = new JButton("Set arm 2 (in)visible");
        arm2Button.addActionListener(e ->
        {
            if (spiroGraph.getArm2().isVisible())
                spiroGraph.getArm2().setVisible(false);
            else
                spiroGraph.getArm2().setVisible(true);
            spiroGraph.repaint();
        });
        JButton arm3Button = new JButton("Set arm 3 (in)visible");
        arm3Button.addActionListener(e ->
        {
            if (spiroGraph.getArm3().isVisible())
                spiroGraph.getArm3().setVisible(false);
            else
                spiroGraph.getArm3().setVisible(true);
            spiroGraph.repaint();
        });

        JButton antiAliasingButton = new JButton("Turn Anti-aliasing on/off");
        antiAliasingButton.addActionListener(e ->
        {
            if (spiroGraph.isAntiAliasingEnabled())
                spiroGraph.setAntiAliasingEnabled(false);
            else
                spiroGraph.setAntiAliasingEnabled(true);
            spiroGraph.repaint();
        });

        armLengthPanel.add(arm1LengthLabel);
        armLengthPanel.add(arm1LengthField);
        armLengthPanel.add(arm2LengthLabel);
        armLengthPanel.add(arm2LengthField);
        armLengthPanel.add(arm3LengthLabel);
        armLengthPanel.add(arm3LengthField);

        anglePanel.add(angle1Label);
        anglePanel.add(angle1Field);
        anglePanel.add(angle2Label);
        anglePanel.add(angle2Field);
        anglePanel.add(angle3Label);
        anglePanel.add(angle3Field);

        gridPanel.add(armLengthPanel, 0);
        gridPanel.add(anglePanel, 1);

        northPanel.add(gridPanel);

        southPanel.add(drawButton);
        southPanel.add(arm1Button);
        southPanel.add(arm2Button);
        southPanel.add(arm3Button);
        southPanel.add(antiAliasingButton);

        content.add(northPanel, BorderLayout.NORTH);
        content.add(southPanel, BorderLayout.SOUTH);
        content.add(spiroGraph, BorderLayout.CENTER);

        frame.setContentPane(content);
    }

}
