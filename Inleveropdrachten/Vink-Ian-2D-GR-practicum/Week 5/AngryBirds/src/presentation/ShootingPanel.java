package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.Hashtable;

public class ShootingPanel extends JPanel {

    private GamePanel gamePanel;

    public ShootingPanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        JPanel gridPanel = new JPanel(new GridLayout(2, 5));

        JLabel mouseLabel = new JLabel("Fire with mouse or sliders");
        mouseLabel.setHorizontalAlignment(SwingConstants.CENTER);


        JButton respawnButton = new JButton("Respawn");
        respawnButton.addActionListener(e -> {
            gamePanel.resetBird();
        });
        respawnButton.setHorizontalAlignment(SwingConstants.CENTER);
        //JLabel spaceLabel = new JLabel("Try again with SPACE");
        //spaceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JCheckBox showDebug = new JCheckBox("Debug");
        showDebug.addActionListener(e ->
        {
            if (showDebug.isSelected()) {
                gamePanel.setShowDebug(true);
            } else
                gamePanel.setShowDebug(false);
        });
        showDebug.setHorizontalAlignment(SwingConstants.CENTER);

        // JSlider angleSlider = new JSlider();
        JSlider angleSlider = new JSlider(JSlider.HORIZONTAL, 90, 180, 90);
        angleSlider.setMinorTickSpacing(90);
        angleSlider.setMajorTickSpacing(15);
        angleSlider.setPaintLabels(true);
        angleSlider.setPaintTicks(true);
        Hashtable angleTable = new Hashtable();
        angleTable.put(90, new JLabel("Up"));
        angleTable.put(180, new JLabel("Right"));
        angleSlider.setLabelTable(angleTable);
        JLabel angleLabel = new JLabel("Adjust firing angle");
        angleSlider.setBounds(10, 460, 175, 50);
        angleLabel.setBounds(40, 440, 150, 20);
        angleLabel.setHorizontalAlignment(SwingConstants.CENTER);


        JSlider velocitySlider = new JSlider(JSlider.HORIZONTAL, 0, 20, 10);
        velocitySlider.setMinorTickSpacing(10);
        velocitySlider.setMajorTickSpacing(2);
        velocitySlider.setPaintLabels(true);
        velocitySlider.setPaintTicks(true);
        Hashtable labelTable = new Hashtable();
        labelTable.put(0, new JLabel("0"));
        labelTable.put(4, new JLabel("4"));
        labelTable.put(8, new JLabel("8"));
        labelTable.put(12, new JLabel("12"));
        labelTable.put(16, new JLabel("16"));
        labelTable.put(20, new JLabel("20"));
        velocitySlider.setLabelTable(labelTable);
        JLabel velocityLabel = new JLabel("Adjust firing velocity");
        velocitySlider.setBounds(10, 460, 175, 50);
        velocityLabel.setBounds(40, 440, 150, 20);
        velocityLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton fireButton = new JButton("Fire");
        fireButton.addActionListener(e -> {
            gamePanel.shootBird(angleSlider.getValue(), velocitySlider.getValue());
        });
        fireButton.setHorizontalAlignment(SwingConstants.CENTER);


        gridPanel.add(mouseLabel);
        gridPanel.add(angleLabel);
        gridPanel.add(new JPanel());
        gridPanel.add(velocityLabel);
        gridPanel.add(new JPanel());

        gridPanel.add(respawnButton);
        gridPanel.add(angleSlider);
        gridPanel.add(fireButton);
        gridPanel.add(velocitySlider);
        gridPanel.add(showDebug);


        add(gridPanel);
    }
}
