package presentation;

import javax.swing.*;
import java.awt.*;

public class Gui extends JFrame {

   // private GamePanel gamePanel = new GamePanel();
   // private ShootingPanel shootingPanel = new ShootingPanel(gamePanel);

    public Gui() {
        super("Angry Birds");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        buildPanel();

        setSize(1000, 800);
        setResizable(false);
        setVisible(true);
    }

    public void buildPanel(){
        JPanel content = new JPanel(new BorderLayout());

        GamePanel gamePanel = new GamePanel();
        ShootingPanel shootingPanel = new ShootingPanel(gamePanel);


        content.add(gamePanel,BorderLayout.CENTER);
        content.add(shootingPanel,BorderLayout.SOUTH);

        super.setContentPane(content);
    }
}