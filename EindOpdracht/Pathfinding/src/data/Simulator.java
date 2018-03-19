package data;

import map.TileMap;

public class Simulator {

    private static Simulator instance;
    private TileMap tileMap;
    private Destination destination;

    private Simulator() {
    }

    public static Simulator getInstance() {
        if (instance == null)
            instance = new Simulator();

        return instance;
    }


    public TileMap getTileMap() {
        return tileMap;
    }

    public void setTileMap(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }
}
