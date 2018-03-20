package map;

import data.Simulator;
import data.Tile;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Queue;
import java.util.LinkedList;
import java.util.Random;

public class DistanceMap {

    private Tile tiles[][];

    public DistanceMap() {
        this.tiles = new Tile[Simulator.getInstance().getTileMap().getTiles().length][Simulator.getInstance().getTileMap().getTiles()[0].length];

        addTiles();
    }

    private void addTiles() {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {

                tiles[row][col] = new Tile();
                tiles[row][col].setDistance(-1);
            }
        }
    }

    private void resetTiles() {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {

                tiles[row][col].setDistance(-1);
            }
        }
    }


    public void calculateDistance(Point2D position) {
        //Return if the point is out of the map
        if (position.getX() >= tiles[0].length * Simulator.getInstance().getTileMap().getTileSize() || position.getY() >= tiles.length * Simulator.getInstance().getTileMap().getTileSize())
            return;

        int x = (int) position.getX() / Simulator.getInstance().getTileMap().getTileSize();
        int y = (int) position.getY() / Simulator.getInstance().getTileMap().getTileSize();


        //Return if the point is on a wall
        if (Simulator.getInstance().getTileMap().getTiles()[y][x] == 1)
            return;

        resetTiles();

        int maxDistance = 0;

        Queue<Point> unvisited = new LinkedList<>();

        unvisited.offer(new Point(x, y));
        tiles[y][x].setDistance(0);

        //TODO: 4 centerpoints (Prevent there not being a superior path) -> Make correct
/*
        Point secondpointX = new Point(x + 1, y);
        if (!isTileAvailable(secondpointX))
            secondpointX = new Point(x - 1, y);


        Point firstpointY = new Point(x, y + 1);
        if (!isTileAvailable(firstpointY))
            firstpointY = new Point(x, y - 1);

        Point secondpointY = new Point(secondpointX.x, firstpointY.y);

        unvisited.offer(secondpointX);
        unvisited.offer(firstpointY);
        unvisited.offer(secondpointY);


        tiles[secondpointX.y][secondpointX.x].setDistance(0);
        tiles[firstpointY.y][firstpointY.x].setDistance(0);
        tiles[secondpointY.y][secondpointY.x].setDistance(0);
*/

        while (!unvisited.isEmpty()) {
            Point p = unvisited.poll();

            for (int col = (p.x - 1); col <= (p.x + 1); col++) {
                for (int row = (p.y - 1); row <= (p.y + 1); row++) {

                    //Exclude points outside of the map
                    if (col < 0 || col >= tiles[0].length || row < 0 || row >= tiles.length)
                        continue;

                    //Exclude diagonal tiles
                    if (col < p.x && row < p.y || col > p.x && row < p.y ||
                            col < p.x && row > p.y || col > p.x && row > p.y)
                        continue;


                    //Has to be walkable and unedited
                    if (Simulator.getInstance().getTileMap().getTiles()[row][col] == 0 && tiles[row][col].getDistance() == -1) {


                        tiles[row][col].setDistance(tiles[p.y][p.x].getDistance() + 1);

                        if (tiles[row][col].getDistance() > maxDistance)
                            maxDistance = tiles[row][col].getDistance();

                        unvisited.offer(new Point(col, row));
                    }
                }
            }
        }

        calculateHeatMap(maxDistance);
        calculateVectorField();
    }


    private void calculateHeatMap(int maxDistance) {

        double relativeDistance = 1.0 / maxDistance;

        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {

                if (tiles[row][col].getDistance() == -1)
                    continue;

                int colorValue = (int) ((tiles[row][col].getDistance() * relativeDistance) * 255);
                tiles[row][col].setColor(new Color(0, 0, colorValue));

            }
        }

    }

    private void calculateVectorField() {
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[y].length; x++) {

                Tile currentTile = tiles[y][x];

                Point right = new Point(x + 1, y);
                if (!isTileAvailable(right))
                    right = new Point(x, y);

                Point left = new Point(x - 1, y);
                if (!isTileAvailable(left))
                    left = new Point(x, y);

                Point up = new Point(x, y - 1);
                if (!isTileAvailable(up))
                    up = new Point(x, y);

                Point down = new Point(x, y + 1);
                if (!isTileAvailable(down))
                    down = new Point(x, y);


                Point2D vector = new Point2D.Double(tiles[left.y][left.x].getDistance() - tiles[right.y][right.x].getDistance(),
                        tiles[up.y][up.x].getDistance() - tiles[down.y][down.x].getDistance());

                //Local Optima problem
                if (vector.getX() == 0 && vector.getY() == 0) {
                    int value = (int) Math.round(Math.random()) * 2 - 1;

                    // Walls on both horizontal sides
                    if (tiles[left.y][left.x].getDistance() == tiles[y][x].getDistance() && tiles[right.y][right.x].getDistance() == tiles[y][x].getDistance()) {

                        vector = new Point2D.Double(0, value);
                    } else {

                        vector = new Point2D.Double(value, 0);
                    }
                }

                double magnitude = Math.sqrt(vector.getX() * vector.getX() + vector.getY() * vector.getY());


                currentTile.setVector(new Point2D.Double(vector.getX() / magnitude, vector.getY() / magnitude));

            }
        }
    }


    public void drawHeatMap(Graphics2D g2d) {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                if (Simulator.getInstance().getTileMap().getTiles()[row][col] == 0 && tiles[row][col].getDistance() != -1) {

                    g2d.setColor(tiles[row][col].getColor());
                    g2d.fill(new Rectangle2D.Double(col * Simulator.getInstance().getTileMap().getTileSize(), row * Simulator.getInstance().getTileMap().getTileSize(),
                            Simulator.getInstance().getTileMap().getTileSize(), Simulator.getInstance().getTileMap().getTileSize()));

                    g2d.setColor(Color.WHITE);
                    g2d.draw(new Rectangle2D.Double(col * Simulator.getInstance().getTileMap().getTileSize(), row * Simulator.getInstance().getTileMap().getTileSize(),
                            Simulator.getInstance().getTileMap().getTileSize(), Simulator.getInstance().getTileMap().getTileSize()));
                }
            }
        }
    }


    public void drawDistanceMap(Graphics2D g2d) {
        g2d.setColor(Color.WHITE);
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                if (Simulator.getInstance().getTileMap().getTiles()[row][col] == 0 && tiles[row][col].getDistance() != -1)
                    g2d.drawString("" + tiles[row][col].getDistance(), col * Simulator.getInstance().getTileMap().getTileSize() + Simulator.getInstance().getTileMap().getTileSize() / 6,
                            row * Simulator.getInstance().getTileMap().getTileSize() + Simulator.getInstance().getTileMap().getTileSize() / 3);
            }
        }
    }


    public void drawVectorField(Graphics2D g2d) {

        int tileSize = Simulator.getInstance().getTileMap().getTileSize();

        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                if (Simulator.getInstance().getTileMap().getTiles()[row][col] == 0 && tiles[row][col].getDistance() != -1 && tiles[row][col].getDistance() != 0) {
                    int centerX = col * tileSize + tileSize / 2;
                    int centerY = row * tileSize + tileSize / 2;

                    g2d.setColor(Color.WHITE);
                    g2d.draw(new Line2D.Double(centerX, centerY, centerX + (tiles[row][col].getVector().getX() * tileSize / 2), centerY + (tiles[row][col].getVector().getY() * tileSize / 2)));

                    g2d.setColor(Color.RED);
                    double radius = tileSize / 10;
                    g2d.fill(new Ellipse2D.Double(centerX - radius / 2, centerY - radius / 2, radius, radius));

                }
            }
        }
    }


    /**
     * Checks if the tile is inside the map and not a wall
     *
     * @param p The point (tile) you're checking
     * @return Whether the tile is walkable and not outside the map
     */
    private boolean isTileAvailable(Point p) {
        return (isInsideMap(p) && isNotAWall(p));
    }

    private boolean isInsideMap(Point p) {
        return !(p.x < 0 || p.x >= tiles[0].length || p.y < 0 || p.y >= tiles.length);
    }

    private boolean isNotAWall(Point p) {
        return Simulator.getInstance().getTileMap().getTiles()[p.y][p.x] == 0;
    }

    private boolean isNotInitialized(Point p) {
        return tiles[p.y][p.x].getDistance() == -1;
    }


    public Tile[][] getTiles() {
        return tiles;
    }
}
