package presentation.frames;

import javax.swing.*;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import java.awt.*;

public class ColorChooserDialog extends JDialog {

    private Color color;
    private DialogResult result;


    protected JPanel mainPanel = new JPanel(new BorderLayout());

    /**
     * ActionDialog Constructor
     *
     * @param title Title of Dialog
     */
    public ColorChooserDialog(String title, Color color) {
        setTitle(title);

        initialize();
        this.color = color;
        mainPanel.add(buildActionPanel(), BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);

    }

    private JPanel buildActionPanel() {
        JPanel panel = new JPanel(new BorderLayout());


        JLabel label = new JLabel("Choose a color");
        label.setForeground(color);
        JColorChooser colorChooser = new JColorChooser(label.getForeground());
        for(AbstractColorChooserPanel a : colorChooser.getChooserPanels()){
            if(a.getDisplayName().equals("HSV") ||a.getDisplayName().equals("HSL") ||a.getDisplayName().equals("RGB") ||a.getDisplayName().equals("CMYK"))
                colorChooser.removeChooserPanel(a);
        }

        panel.add(colorChooser,BorderLayout.NORTH);

        JButton save = new JButton("Done");
        save.addActionListener(e -> {
            color = colorChooser.getColor();
            result = new DialogResult(color);
            dispose();
        });
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(save);

        panel.add(buttonPanel,BorderLayout.CENTER);

        return panel;
    }


    /**
     * Initializes the Dialog
     */
    private void initialize() {
        setModalityType(ModalityType.APPLICATION_MODAL);
         setResizable(false);
        setContentPane(mainPanel);
    }



    public DialogResult getResult() {
        setVisible(true);
        return result;
    }


    public class DialogResult {
        private Color color;

        private DialogResult(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }


}
