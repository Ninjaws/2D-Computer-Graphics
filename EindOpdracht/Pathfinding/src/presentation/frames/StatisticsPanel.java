package presentation.frames;

import data.Simulator;

import javax.swing.*;
import java.awt.*;

public class StatisticsPanel extends JPanel {

    public StatisticsPanel(){

        //TODO: Show particles
        add(new Label("Amount of particles: " + Simulator.getInstance().getParticles().size()));
    }
}
