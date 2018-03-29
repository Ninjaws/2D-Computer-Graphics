package presentation.frames.menus;

import javax.swing.*;
import java.awt.*;

public class AboutFrame extends JFrame {


    public AboutFrame(){
        super("About");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        buildPanel();

        setSize(250, 100);
        setResizable(false);
    }

    public void buildPanel() {
        JPanel content = new JPanel(new GridLayout(3, 0));


        JLabel authorLabel = new JLabel("Made by Ian Vink");
        JLabel studentLabel = new JLabel("Studentnumber: 2129441");
        JLabel emailLabel = new JLabel("Email: i.vink@student.avans.nl");

        content.add(authorLabel);
        content.add(studentLabel);
        content.add(emailLabel);

        super.setContentPane(content);
    }

}
