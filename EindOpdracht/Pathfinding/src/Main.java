import data.Simulator;
import map.TileMap;
import data.Destination;
import presentation.frames.SimulatorFrame;

public class Main {
    public static void main(String[] args) {

        Simulator.getInstance().setTileMap(new TileMap(40, 80, 16));
        Simulator.getInstance().setDestination(new Destination());
        Simulator.getInstance().addParticles();

        SimulatorFrame simulatorFrame = new SimulatorFrame();
    }
}
