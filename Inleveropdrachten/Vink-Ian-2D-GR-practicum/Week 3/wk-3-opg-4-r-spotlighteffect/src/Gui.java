import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    public Gui() {
        super("Spotlight");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        buildPanel();

        setSize(800, 800);
        setVisible(true);
    }

    public void buildPanel() {
        JPanel content = new JPanel(new BorderLayout());

        content.add(new SpotlightPanel(), BorderLayout.CENTER);

        super.setContentPane(content);
    }
}
