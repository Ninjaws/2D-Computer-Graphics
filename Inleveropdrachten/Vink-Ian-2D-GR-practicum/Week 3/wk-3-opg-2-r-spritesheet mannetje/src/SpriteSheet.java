import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class SpriteSheet {
    private BufferedImage[] tiles;

    public SpriteSheet(String imageLocation, int width, int height, int horSprites, int verSprites) {
        try {
            BufferedImage image = ImageIO.read(getClass().getResource(imageLocation));

            tiles = new BufferedImage[horSprites * verSprites];

            for (int i = 0; i < tiles.length; i++)
                tiles[i] = image.getSubimage(width * (i % horSprites), height * (i / horSprites), width, height);

        } catch (Exception e) {
            System.out.println(imageLocation);
            e.printStackTrace();
        }
    }



    public BufferedImage getTile(int index) {
        return tiles[index];
    }

    public BufferedImage[] getTiles() {
        return tiles;
    }

}
