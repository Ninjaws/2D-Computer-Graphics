import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader {

    public ImageLoader() {


    }

    public BufferedImage loadImage(String imageLocation) {
        File file = new File(imageLocation);

        BufferedImage image = null;

        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }


}
