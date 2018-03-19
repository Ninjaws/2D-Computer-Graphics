import data.Simulator;
import map.TileMap;
import data.Destination;
import presentation.frames.SimulatorFrame;

public class Main {
    public static void main(String[] args) {

        Simulator.getInstance().setTileMap(new TileMap(20,40,32));
        Simulator.getInstance().setDestination(new Destination());


        SimulatorFrame simulatorFrame = new SimulatorFrame();
    }
}
