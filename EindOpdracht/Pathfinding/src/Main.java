import data.Simulator;
import map.TileMap;
import data.Destination;
import presentation.frames.SimulatorFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        Simulator.getInstance().setTileMap(new TileMap(20, 40, 32));
        Simulator.getInstance().setDestination(new Destination());

        SimulatorFrame simulatorFrame = new SimulatorFrame();
    }
}
