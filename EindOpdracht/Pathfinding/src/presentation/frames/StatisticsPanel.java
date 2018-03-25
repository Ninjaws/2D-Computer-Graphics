package presentation.frames;

import data.Simulator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StatisticsPanel extends JPanel {

    private Label particleLabel;

    public StatisticsPanel() {


        particleLabel = new Label("Amount of particles: " + Simulator.getInstance().getParticles().size());
        particleLabel.setPreferredSize(new Dimension(256, 32));
        add(particleLabel);

    }

    public void updateParticleLabel() {
        particleLabel.setText("Amount of particles: " + Simulator.getInstance().getParticles().size());
    }

}
