import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

    VerletPanel verletPanel = new VerletPanel();

    public Gui() {
        super("Verlet Engine");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        buildMenu();
        buildPanel();

        // setExtendedState(getExtendedState() | Frame.MAXIMIZED_BOTH);
        setSize(1000, 800);
        setVisible(true);

    }

    public void buildPanel() {
        JPanel content = new JPanel(new BorderLayout());
        JPanel southPanel = new JPanel(new FlowLayout());

        content.add(verletPanel, BorderLayout.CENTER);

        JButton clothButton = new JButton("Create Cloth");
        clothButton.addActionListener(e -> {
            verletPanel.createCloth(10, 15);
        });
        southPanel.add(clothButton);

        JButton clearScreenButton = new JButton("Clear Screen");
        clearScreenButton.addActionListener(e -> {
            verletPanel.clearScreen();
        });
        southPanel.add(clearScreenButton);

        content.add(southPanel, BorderLayout.SOUTH);
        super.setContentPane(content);
    }

    public void buildMenu() {
        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("File");


        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> {
            System.out.println("Save");
            try {
                verletPanel.saveState();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        menu.add(saveItem);

        JMenuItem loadItem = new JMenuItem("Load");
        loadItem.addActionListener(e -> {
            System.out.println("Load");
            try {
                verletPanel.loadState();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
        menu.add(loadItem);


        bar.add(menu);
        super.setJMenuBar(bar);
    }

}