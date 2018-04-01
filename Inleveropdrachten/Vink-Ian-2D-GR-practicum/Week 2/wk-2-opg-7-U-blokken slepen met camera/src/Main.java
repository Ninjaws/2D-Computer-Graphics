import javax.swing.*;
import java.awt.*;

public class Main {


    public static void main(String[] args) {
        JFrame frame = new JFrame("Dragging Squares");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(800, 600));
        frame.setContentPane(new Squares());
        frame.setVisible(true);
    }
}
