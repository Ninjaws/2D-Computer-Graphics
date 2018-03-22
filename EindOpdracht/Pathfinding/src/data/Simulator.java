package data;

import map.TileMap;
import objects.Particle;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Simulator {

    private static Simulator instance;
    private TileMap tileMap;
    private Destination destination;
    private ArrayList<Particle> particles = new ArrayList<>();
    private int amountOfParticles =1;

    private Simulator() {
    }

    public static Simulator getInstance() {
        if (instance == null)
            instance = new Simulator();

        return instance;
    }


    public void addParticles() {
        if (tileMap != null)
            while (particles.size() < amountOfParticles) {
                particles.add(new Particle(tileMap.getTileSize()/2, new Point2D.Double(Simulator.getInstance().tileMap.getTileSize() * 2 + tileMap.getTileSize() * particles.size(), Simulator.getInstance().tileMap.getTileSize()*2), new Color(255, 200, 0)));


            }
        for (Particle p : particles) {
            System.out.println(p.getPosition());
        }

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

    public ArrayList<Particle> getParticles() {
        return particles;
    }

    public void setParticles(ArrayList<Particle> particles) {
        this.particles = particles;
    }
}
