package data;

import map.TileMap;
import objects.Particle;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

/**
 * @author Ian Vink
 */

public class Simulator {

    private static Simulator instance;
    private TileMap tileMap;
    private Destination destination;
    private ArrayList<Particle> particles = new ArrayList<>();

    private int maxAmountOfParticles = 15000;
    private int amountOfParticles = 1000;
    private int particleSize = 2;
    private Color particleColor = new Color(255, 120, 0, 255);// new Color(255,0,0,155);//Color.ORANGE;

    private boolean usingTexture = false;
    private BufferedImage particleTexture = null;

    private boolean usingBounceCollision = true;
    private boolean usingAdditiveBlending = false;

    private Simulator() {
    }

    /**
     * Allows only 1 instance of the Simulator class
     *
     * @return The instance of the Simulator
     */
    public static Simulator getInstance() {
        if (instance == null)
            instance = new Simulator();

        return instance;
    }


    /**
     * Spawns a particle on the position of the mouse
     *
     * @param position The position of the mouse
     */
    public void spawnParticle(Point2D position) {
        if (tileMap != null) {
            if (particles.size() < maxAmountOfParticles) {
                int x = (int) position.getX() / tileMap.getTileSize();
                int y = (int) position.getY() / tileMap.getTileSize();
                if (tileMap.isInsideMap(new Point(x, y)) && !tileMap.isAWall(new Point(x, y)))
                    particles.add(new Particle(particleSize, position));
            }
        }
    }

    /**
     * Spawns a specifiek number of particles on the tile the mouse is occupying
     *
     * @param position The position of the mouse
     */
    public void spawnAmountOfParticles(Point2D position) {
        if (tileMap != null) {
            if (particles.size() < maxAmountOfParticles) {
                int x = (int) position.getX() / tileMap.getTileSize();
                int y = (int) position.getY() / tileMap.getTileSize();
                if (tileMap.isInsideMap(new Point(x, y)) && !tileMap.isAWall(new Point(x, y))) {
                    for (int i = 0; i < amountOfParticles; i++) {
                        Point2D pPos = new Point2D.Double(position.getX() - tileMap.getTileSize() + (tileMap.getTileSize() * 2 * Math.random()), position.getY() - tileMap.getTileSize() + (tileMap.getTileSize() * 2 * Math.random()));

                        int px = (int) pPos.getX() / tileMap.getTileSize();
                        int py = (int) pPos.getY() / tileMap.getTileSize();

                        if (!tileMap.isAWall(new Point(px, py)))
                            particles.add(new Particle(particleSize, pPos));
                    }
                }
            }
        }
    }

    public void deleteParticles() {
        this.particles.clear();
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

    public Color getParticleColor() {
        return particleColor;
    }

    public void setParticleColor(Color particleColor) {
        this.particleColor = particleColor;
    }

    public BufferedImage getParticleTexture() {
        return particleTexture;
    }

    public void setParticleTexture(File filePath) {

        BufferedImage image = null;
        try {
            image = ImageIO.read(filePath);
        } catch (Exception e) {
        }

        this.particleTexture = image;
    }

    public void setUsingAdditiveBlending(boolean usingAdditiveBlending) {
        this.usingAdditiveBlending = usingAdditiveBlending;
    }

    public void setUsingBounceCollision(boolean usingBounceCollision) {
        this.usingBounceCollision = usingBounceCollision;
    }

    public boolean isUsingBounceCollision() {
        return usingBounceCollision;
    }

    public boolean isUsingAdditiveBlending() {
        return usingAdditiveBlending;
    }
}
