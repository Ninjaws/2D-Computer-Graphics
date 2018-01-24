import javax.swing.*;
import java.awt.*;

public class Java2DDemo extends JPanel
{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Java2D");
        frame.setSize(800, 600);
        frame.setContentPane(new Java2DDemo());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;

        setBackground(Color.WHITE);

        g2d.drawLine(0, getHeight(),0, getHeight()/2);
        g2d.drawLine(0,getHeight()/2, getWidth()/2, 0);
        g2d.drawLine(getWidth()/2, 0, getWidth(), getHeight()/2);
        g2d.drawLine(getWidth(), getHeight()/2, getWidth(), getHeight());
        g2d.drawLine(getWidth(), getHeight(), 0, getHeight());

        int gapHor = (int)(getWidth()/10);
        int gapVer = (int)(getHeight()/8);

g2d.drawLine(gapHor, getHeight(), gapHor, gapVer*6);
g2d.drawLine(gapHor, gapVer*6, gapHor*2, gapVer*6);
g2d.drawLine(gapHor*2, gapVer*6, gapHor*2, getHeight());

g2d.drawLine(gapHor*3, gapVer*7, gapHor*3, gapVer*5);
g2d.drawLine(gapHor*3, gapVer*5, gapHor*9, gapVer*5);
g2d.drawLine(gapHor*9, gapVer*5, gapHor*9, gapVer*7);
g2d.drawLine(gapHor*9, gapVer*7, gapHor*3, gapVer*7);


    }

}
