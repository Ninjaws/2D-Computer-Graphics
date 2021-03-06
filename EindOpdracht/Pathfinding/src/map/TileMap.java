package map;

import data.Simulator;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

/**
 * @author Ian Vink
 */

public class TileMap implements Serializable {


    private int tiles[][];
    private int tileSize;


    public TileMap(int rows, int columns, int tileSize) {

        this.tileSize = tileSize;
        setMap(rows, columns);
    }

    /**
     * Builds a permanent surrounding wall
     * @param rows The amount of rows in the map
     * @param columns The amount of columns in the map
     */
    public void setMap(int rows, int columns) {
        tiles = new int[rows][columns];


        //Making outer tiles walls, 2 is for permanent walls
        for (int x = 0; x < tiles[0].length; x++) {
            tiles[0][x] = 2;
            tiles[tiles.length - 1][x] = 2;
        }
        for (int y = 0; y < tiles.length; y++) {
            tiles[y][0] = 2;
            tiles[y][tiles[0].length - 1] = 2;
        }

    }

    /**
     * Removes all built walls
     */
    public void resetMap() {
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                if (tiles[row][col] != 2)
                    tiles[row][col] = 0;
            }
        }
    }

    /**
     * Sets a wall tile on the mouse position
     *
     * @param position The mouse position
     */
    public void buildWall(Point2D position) {

        int x = (int) position.getX() / tileSize;
        int y = (int) position.getY() / tileSize;

        //Return if the point is out of the map
        if (!isInsideMap(new Point(x, y)))
            return;


        if (tiles[y][x] == 0) {
            tiles[y][x] = 1;
        }

    }

    /**
     * Removes a wall tile on the mouse position
     *
     * @param position The mouse position
     */
    public void removeWall(Point2D position) {

        // if (position.getX() >= tiles[0].length * tileSize || position.getY() >= tiles.length * tileSize)
        //     return;

        int x = (int) position.getX() / tileSize;
        int y = (int) position.getY() / tileSize;

        //Return if the point is out of the map
        if (!isInsideMap(new Point(x, y)))
            return;

        //Only allow the removal of non-permanent (1) walls, not permanent (2) walls
        if (tiles[y][x] == 1) {
            tiles[y][x] = 0;
        }
    }


    public void drawMap(Graphics2D g2d) {

        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                Rectangle2D rect = new Rectangle2D.Double(col * tileSize, row * tileSize, tileSize, tileSize);

                if (tiles[row][col] == 1 || tiles[row][col] == 2) {
                    g2d.setColor(new Color(205, 205, 180, 100));
                    g2d.fill(rect);
                } else {
                    g2d.setColor(Color.DARK_GRAY);
                    g2d.fill(rect);
                    g2d.setColor(Color.WHITE);
                    g2d.draw(rect);
                }
            }
        }
    }

    public int[][] getTiles() {
        return tiles;
    }

    public void setTiles(int[][] tiles) {
        this.tiles = tiles;
    }

    public int getTileSize() {
        return tileSize;
    }

    public void setTileSize(int tileSize) {
        this.tileSize = tileSize;
    }

    public boolean isAWall(Point p) {
        if (isInsideMap(p))
            return tiles[p.y][p.x] == 1 || tiles[p.y][p.x] == 2;
        else
            return isInsideMap(p);
    }

    public boolean isInsideMap(Point p) {
        return !(p.x < 0 || p.x >= tiles[0].length || p.y < 0 || p.y >= tiles.length);
    }
}
