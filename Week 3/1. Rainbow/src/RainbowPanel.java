import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class RainbowPanel extends JPanel {


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        String sentence = "Rainbow";

        g2d.translate(getWidth() / 2, getHeight() / 2);

        double totalAngle = 180.0;
        double currentAngle = -90;
        int amountOfLetters = sentence.length();
        double deltaRotation = totalAngle / (amountOfLetters - 1);
        float colorValue = (float) deltaRotation / (float) totalAngle;

        int index = 0;
        for (char letter : sentence.toCharArray()) {
            g2d.setColor(Color.getHSBColor(colorValue * index, 1, 1));
            Font font = new Font("Sans Serif", Font.PLAIN, 50);
            Shape shape = font.createGlyphVector(g2d.getFontRenderContext(), "" + letter).getOutline();

            AffineTransform affineTransform = new AffineTransform();
            affineTransform.rotate(Math.toRadians(currentAngle));
            affineTransform.translate(0, -font.getSize() * 2);
            affineTransform.translate(-shape.getBounds2D().getWidth() / 2, shape.getBounds2D().getHeight() / 2);

            g2d.fill(affineTransform.createTransformedShape(shape));
            currentAngle += deltaRotation;
            index++;
        }
    }
}
