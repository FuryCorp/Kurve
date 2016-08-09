package fury.yuri.model;

import javafx.geometry.Point2D;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yuri on 06/08/16.
 */
public class PointsCollector {

    private Map<Point2D, Integer> points = new HashMap<>();

    public boolean contains(Point2D point, int radius) {
        return false;
    }
}
