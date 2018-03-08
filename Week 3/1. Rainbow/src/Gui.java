import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    public Gui() {
        super("1. Rainbow");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        buildPanel();

        setSize(600, 600);
        setVisible(true);
    }

    public void buildPanel() {
        JPanel content = new JPanel(new BorderLayout());

        content.add(new RainbowPanel(), BorderLayout.CENTER);

        super.setContentPane(content);
    }

}
