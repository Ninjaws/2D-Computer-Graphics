import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.ArrayList;

public class ImagePanel extends JPanel implements ActionListener {

    private ArrayList<BufferedImage> images = new ArrayList<>();
    private int imageIndex = 0;
    private float opacity = 1;
    private float deltaOpacity;
    private float fadeTime;
    private final int FPS;

    public ImagePanel(int fps) {

        this.FPS = fps;
        fadeTime = 15.0f; //Time in seconds to complete fade into next image
        deltaOpacity = 1 / (fps * fadeTime); // How much the opacity needs to change every frame


        ImageLoader loader = new ImageLoader();
        images.add(loader.loadImage("resources/gamble.jpg"));
        images.add(loader.loadImage("resources/OP_9.png"));
        images.add(loader.loadImage("resources/OP_10.png"));

        Timer timer = new Timer(1000 / FPS, this);
        timer.start();
    }

    private void update() {
        opacity -= deltaOpacity;

        if (opacity <= 0) {
            imageIndex = (imageIndex + 1) % images.size();

            opacity = 1;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        update();
        repaint();
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        Rectangle2D nextRect = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
        g2d.setPaint(new TexturePaint(images.get((imageIndex + 1) % images.size()), nextRect));
        g2d.fill(nextRect);

        Rectangle2D rect = new Rectangle2D.Double(0, 0, getWidth(), getHeight());
        g2d.setPaint(new TexturePaint(images.get(imageIndex), rect));
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.fill(rect);
    }
}
