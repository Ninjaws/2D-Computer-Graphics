import javax.swing.*;
import java.awt.*;
import java.security.Key;
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
    int pressedKey = -1;

    public enum Actions {STANDING, RUNNING, JUMPING}

    ;
    int currentAction;

    List<BufferedImage> currentAnimation;
    List<BufferedImage> standingAnimation;
    List<BufferedImage> runningAnimation;
    List<BufferedImage> jumpingAnimation;


    public AnimationPanel() {

        characterSheet = new SpriteSheet("resources\\platformer_sprites_base.png", 64,
                64, 8, 9);

        characterShape = new Rectangle2D.Double(0, 0, 64, 64);
        addKeyListener(this);


        List<BufferedImage> tiles = new ArrayList<>(Arrays.asList(characterSheet.getTiles()));
        standingAnimation = new ArrayList<>(tiles.subList(0, 4));
        runningAnimation = new ArrayList<>(tiles.subList(4, 12));
        jumpingAnimation = new ArrayList<>(tiles.subList(38,50));
        currentAnimation = new ArrayList<>();
        currentAnimation = standingAnimation;
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
        pressedKey = e.getKeyCode();
        System.out.println(pressedKey);
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
            if (pressedKey == KeyEvent.VK_D)
                setAnimation(Actions.RUNNING.ordinal());
            else if (pressedKey == KeyEvent.VK_SPACE)
                setAnimation(Actions.JUMPING.ordinal());

            nextTile();
            repaint();
        } else {
            setAnimation(Actions.STANDING.ordinal());
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
            System.out.println("Change");
            if (newAction == Actions.STANDING.ordinal())
                currentAnimation = standingAnimation;
            else if (newAction == Actions.RUNNING.ordinal())
                currentAnimation = runningAnimation;
            else if (newAction == Actions.JUMPING.ordinal())
                currentAnimation = jumpingAnimation;

            currentTile = -1;
        }
    }
}
