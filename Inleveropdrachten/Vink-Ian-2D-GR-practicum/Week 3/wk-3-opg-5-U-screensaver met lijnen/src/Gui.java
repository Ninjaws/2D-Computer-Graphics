import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

public class Gui extends JFrame {
    public Gui() {
        super("Screensaver");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        setSize(600,600);

        buildPanel();


        setVisible(true);
    }

    public void buildPanel() {
        JPanel content = new JPanel(new BorderLayout());

        content.add(new ScreensaverPanel(30,new Point2D.Double(getWidth(),getHeight())), BorderLayout.CENTER);

        super.setContentPane(content);
    }




}