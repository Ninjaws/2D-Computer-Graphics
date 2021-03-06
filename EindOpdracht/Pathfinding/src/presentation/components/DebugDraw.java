package presentation.components;

import data.Simulator;

import java.awt.*;

/**
 * @author Ian Vink
 */

public class DebugDraw {

    private static DebugDraw instance;
    private Simulator simulator;

    private DebugDraw() {
        simulator = Simulator.getInstance();
    }

    public static DebugDraw getInstance() {
        if (instance == null) instance = new DebugDraw();
        return instance;
    }


    public void draw(Graphics2D g2d, boolean drawHeatMap, boolean drawDistanceMap, boolean drawVectorField) {


        simulator.getDestination().getDistanceMap().draw(g2d, drawHeatMap,drawDistanceMap,drawVectorField);

    }


}
