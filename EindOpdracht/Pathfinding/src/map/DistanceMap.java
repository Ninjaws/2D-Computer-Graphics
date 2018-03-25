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

public class DistanceMap {

    private Tile tiles[][];
    private Point2D lastCalculatedPoint;

    private boolean singleCenterPoint;

    public DistanceMap() {
        this.tiles = new Tile[Simulator.getInstance().getTileMap().getTiles().length][Simulator.getInstance().getTileMap().getTiles()[0].length];
        this.lastCalculatedPoint = new Point2D.Double(0, 0);
        this.singleCenterPoint = true;
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

    /**
     * Recalculates the distance map based on the previously chosen mouse point
     */
    public void updateDistance() {
        calculateDistance(lastCalculatedPoint);
    }

    /**
     * Calculates the distance of the tiles from the mouse position
     *
     * @param position The position of the mouse
     */
    public void calculateDistance(Point2D position) {

        int x = (int) position.getX() / Simulator.getInstance().getTileMap().getTileSize();
        int y = (int) position.getY() / Simulator.getInstance().getTileMap().getTileSize();

        //Return if the point is out of the map
        if (!Simulator.getInstance().getTileMap().isInsideMap(new Point(x, y)))
            return;

        //Return if the point is on a wall
        if (Simulator.getInstance().getTileMap().getTiles()[y][x] == 1 || Simulator.getInstance().getTileMap().getTiles()[y][x] == 2)
            return;

        resetTiles();
        lastCalculatedPoint = position;

        int maxDistance = 0;

        Queue<Point> unvisited = new LinkedList<>();


        singleCenterPoint = false;
        for (int col = (x - 1); col <= (x + 2); col++) {
            for (int row = (y - 1); row <= (y + 2); row++) {


                if (Simulator.getInstance().getTileMap().isAWall(new Point(col, row))) {
                    singleCenterPoint = true;
                    break;
                }

            }
        }

        //4 tiles selected
        if (!singleCenterPoint) {
            for (int col = (x); col <= (x + 1); col++) {
                for (int row = (y); row <= (y + 1); row++) {

                    tiles[row][col].setDistance(0);
                    unvisited.offer(new Point(col, row));
                }
            }

        } else {
            unvisited.offer(new Point(x, y));
            tiles[y][x].setDistance(0);
        }


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


                if (currentTile.getDistance() == 0) {
                    if (singleCenterPoint) {
                        tiles[y][x].setVector(new Point2D.Double(0.0, 0.0));
                        continue;
                    }
                }


                Point right = new Point(x + 1, y);
                if (!isTileAvailable(right))
                    right = new Point(x, y);

                Point left = new Point(x - 1, y);
                if (!isTileAvailable(left)) {
                    left = new Point(x, y);
                }

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

                    // Walls on both horizontal sides
                    if (tiles[left.y][left.x].getDistance() == tiles[y][x].getDistance() && tiles[right.y][right.x].getDistance() == tiles[y][x].getDistance()) {

                        vector = new Point2D.Double(0, -1);
                    } else {

                        vector = new Point2D.Double(-1, 0);
                    }
                }


                //Wallcheck relies on the earlier "isTileAvailable", which sets the position to equal the current position in case of a wall

                //Left tile is a wall
                if (left.getLocation().equals(new Point(x, y))) {
                    //Up and down are the same value
                    if (tiles[up.y][up.x].getDistance() == tiles[down.y][down.x].getDistance()) {
                        //The vector is pointing straight at the wall
                        if (vector.getX() == -1) {
                            vector = new Point2D.Double(0, -1);
                        }
                    }
                }

                //Right tile is a wall
                else if (right.getLocation().equals(new Point(x, y))) {
                    //Up and down are the same value
                    if (tiles[up.y][up.x].getDistance() == tiles[down.y][down.x].getDistance()) {
                        //The vector is pointing straight at the wall
                        if (vector.getX() == 1) {
                            vector = new Point2D.Double(0, -1);
                        }
                    }
                }

                //Up tile is a wall
                else if (up.getLocation().equals(new Point(x, y))) {
                    //Up and down are the same value
                    if (tiles[left.y][left.x].getDistance() == tiles[right.y][right.x].getDistance()) {
                        //The vector is pointing straight at the wall
                        if (vector.getY() == -1) {
                            vector = new Point2D.Double(-1, 0);
                        }
                    }
                }

                //Down tile is a wall
                else if (down.getLocation().equals(new Point(x, y))) {
                    //Up and down are the same value
                    if (tiles[left.y][left.x].getDistance() == tiles[right.y][right.x].getDistance()) {
                        //The vector is pointing straight at the wall
                        if (vector.getY() == 1) {
                            vector = new Point2D.Double(-1, 0);
                        }
                    }
                }




                /*

                //Left or right tile is a wall
                if (left.getLocation().equals(new Point(x, y)) || right.getLocation().equals(new Point(x, y))) {

                    int value = (int) Math.round(Math.random()) * 2 - 1;

                    // Check if both up and down have same value
                    // If yes, randomize value to choose either up or down
                    if (tiles[up.y][up.x].getDistance() == tiles[down.y][down.x].getDistance()) {
                        System.out.println("Tile: " + currentTile.getDistance());
                        vector = new Point2D.Double(0, value);
                    }
                }
                //Top or bottom tile is a wall
                else if (up.getLocation().equals(new Point(x, y)) || down.getLocation().equals(new Point(x, y))) {

                    int value = (int) Math.round(Math.random()) * 2 - 1;

                    // Check if both left and right have same value
                    // If yes, randomize value to choose either left or right
                    if (tiles[left.y][left.x].getDistance() == tiles[right.y][right.x].getDistance()) {
                        System.out.println("Tile: " + currentTile.getDistance());
                        vector = new Point2D.Double(value, 0);
                    }
                }
                */


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
                if (Simulator.getInstance().getTileMap().getTiles()[row][col] == 0 && tiles[row][col].getDistance() != -1) {// && tiles[row][col].getDistance() != 0) {
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
        return (Simulator.getInstance().getTileMap().isInsideMap(p) && !Simulator.getInstance().getTileMap().isAWall(p));
    }


    public boolean isNotInitialized(Point p) {
        return tiles[p.y][p.x].getDistance() == -1;
    }


    public Tile[][] getTiles() {
        return tiles;
    }


}
