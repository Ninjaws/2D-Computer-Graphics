import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    public Gui() {
        super("Image Fading");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        buildPanel();

        setSize(1000, 800);
        setVisible(true);
    }

    public void buildPanel() {
        JPanel content = new JPanel(new BorderLayout());

        content.add(new ImagePanel(15), BorderLayout.CENTER);

        super.setContentPane(content);
    }
}
