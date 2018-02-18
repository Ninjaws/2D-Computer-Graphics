import javax.swing.*;

public class Frame {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Dragging blocks");
        frame.setContentPane(new DraggingBlocks());
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);

    }
}
