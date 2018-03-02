import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

public class Gui extends JFrame {

    public Gui() {
        super("Verlet Engine");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        buildPanel();

        // setExtendedState(getExtendedState() | Frame.MAXIMIZED_BOTH);
        setSize(1000, 800);
        setVisible(true);

    }

    public void buildPanel() {
        JPanel content = new JPanel(new BorderLayout());

        content.add(new VerletPanel(), BorderLayout.CENTER);

        super.setContentPane(content);
    }

}
