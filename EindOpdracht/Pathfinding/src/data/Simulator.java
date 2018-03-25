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
    private int maxAmountOfParticles = 20000;
    private int amountOfParticles = 1000;
    private int particleSize = 2;

    private Simulator() {
    }

    public static Simulator getInstance() {
        if (instance == null)
            instance = new Simulator();

        return instance;
    }


    public void spawnParticle(Point2D position) {
        if (tileMap != null) {
            if(particles.size() < maxAmountOfParticles) {
                int x = (int) position.getX() / tileMap.getTileSize();
                int y = (int) position.getY() / tileMap.getTileSize();
                if (tileMap.isInsideMap(new Point(x, y)) && !tileMap.isAWall(new Point(x, y)))
                    particles.add(new Particle(particleSize, position, Color.ORANGE));
            }
        }
    }

    public void spawnAmountOfParticles(Point2D position){
        if(tileMap != null){
            if(particles.size() < maxAmountOfParticles) {
                int x = (int) position.getX() / tileMap.getTileSize();
                int y = (int) position.getY() / tileMap.getTileSize();
                if (tileMap.isInsideMap(new Point(x, y)) && !tileMap.isAWall(new Point(x, y))) {
                    for (int i = 0; i < amountOfParticles; i++) {
                        Point2D pPos = new Point2D.Double(position.getX() + (tileMap.getTileSize() * Math.random()), position.getY() + (tileMap.getTileSize() * Math.random()));

                        int px = (int) pPos.getX() / tileMap.getTileSize();
                        int py = (int) pPos.getY() / tileMap.getTileSize();

                        if(!tileMap.isAWall(new Point(px,py)))
                        particles.add(new Particle(particleSize, pPos, Color.ORANGE));
                    }
                }
            }
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
