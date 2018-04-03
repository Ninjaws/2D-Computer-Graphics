package data;

import map.DistanceMap;

/**
 * @author Ian Vink
 */

public class Destination {
    private DistanceMap distanceMap;


    public Destination() {
        distanceMap = new DistanceMap();

    }


    public DistanceMap getDistanceMap() {
        return distanceMap;
    }

}
