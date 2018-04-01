import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {
    public Gui() {
        super("Animations");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        buildPanel();

        setSize(600, 600);
        setVisible(true);

    }

    public void buildPanel() {
        JPanel content = new JPanel(new BorderLayout());

        content.add(new AnimationPanel(), BorderLayout.CENTER);

        super.setContentPane(content);
    }
}
