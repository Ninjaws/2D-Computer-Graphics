package map;

import data.Simulator;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class TileMap {


    private int tiles[][];
    private int tileSize;


    public TileMap(int rows, int columns, int tileSize) {

        this.tileSize = tileSize;
        setMap(rows, columns);
    }

    public void setMap(int rows, int columns) {
        tiles = new int[rows][columns];


        //Making outer tiles walls
        for (int x = 0; x < tiles[0].length; x++) {
            tiles[0][x] = 1;
            tiles[tiles.length - 1][x] = 1;
        }
        for (int y = 0; y < tiles.length; y++) {
            tiles[y][0] = 1;
            tiles[y][tiles[0].length - 1] = 1;
        }


        tiles[10][20] = 1;
        tiles[11][20] = 1;
        tiles[12][20] = 1;
        tiles[13][20] = 1;
        tiles[14][20] = 1;
        tiles[15][20] = 1;
        tiles[15][19] = 1;
        tiles[15][18] = 1;
        tiles[15][16] = 1;
        tiles[14][16] = 1;
        tiles[13][16] = 1;
        tiles[12][17] = 1;
        tiles[12][16] = 1;
        tiles[12][15] = 1;
        tiles[12][14] = 1;
        tiles[12][13] = 1;
        tiles[12][12] = 1;
        tiles[12][11] = 1;
        tiles[12][10] = 1;

      //  tiles[12][18] = 1;
       // tiles[12][19] = 1;
        //tiles[12][20] = 1;


    }

    public void drawMap(Graphics2D g2d) {

        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[row].length; col++) {
                Rectangle2D rect = new Rectangle2D.Double(col * tileSize, row * tileSize, tileSize, tileSize);

                if (tiles[row][col] == 1) {
                    g2d.setColor(new Color(205,205,180,100));
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
        return tiles[p.y][p.x] == 1;
    }
}
