import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class AnimationPanel extends JPanel implements KeyListener, ActionListener {

    SpriteSheet characterSheet;
    Rectangle2D characterShape;
    int currentTile = 0;
    boolean keyPressed = false;

    public enum Actions {STANDING, RUNNING, JUMPING};
    int currentAction;

    List<BufferedImage> currentAnimation;
    List<BufferedImage> standingAnimation;
    List<BufferedImage> runningAnimation;


    public AnimationPanel() {

        characterSheet = new SpriteSheet("resources\\platformer_sprites_base.png", 64,
                64, 8, 9);

        characterShape = new Rectangle2D.Double(0, 0, 64, 64);
        addKeyListener(this);


        List<BufferedImage> tiles = new ArrayList<>(Arrays.asList(characterSheet.getTiles()));
        standingAnimation = new ArrayList<>(tiles.subList(0, 4));
        runningAnimation = new ArrayList<>(tiles.subList(5, 12));
        currentAnimation = new ArrayList<>();
        currentAnimation.addAll(standingAnimation);
        currentAction = Actions.STANDING.ordinal();

        int fps = 10;
        Timer timer = new Timer(1000 / fps, this);
        timer.start();

    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.translate(getWidth() / 2, getHeight() / 2);
        //      g2d.scale(1,-1);


        g2d.setPaint(new TexturePaint(currentAnimation.get(currentTile), characterShape));


        g2d.fill(characterShape);
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keyPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keyPressed = false;
    }


    public void addNotify() {
        super.addNotify();
        requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (keyPressed) {
        //    setAnimation(Actions.RUNNING.ordinal());
      //      nextTile();
            repaint();
        } else {
              setAnimation(Actions.STANDING.ordinal());
            System.out.println(currentAnimation.size());
               nextTile();
            repaint();
        }
    }

    public void nextTile() {
        currentTile++;
        currentTile = currentTile % currentAnimation.size();
    }


    public void setAnimation(int newAction) {
        if (newAction != currentAction) {
            currentAction = newAction;
            currentAnimation.clear();
            System.out.println("Not same");
            if (newAction == Actions.STANDING.ordinal())
                currentAnimation.addAll(standingAnimation);
            else if (newAction == Actions.RUNNING.ordinal())
                currentAnimation.addAll(runningAnimation);

            currentTile = -1;
        }
    }
}
