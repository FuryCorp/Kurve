package fury.yuri.geometry;

import java.awt.geom.Point2D;

/**
 * Created by yuri on 05/08/16.
 */
public class MyPoint extends Point2D {

    private double x;
    private double y;

    public MyPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    @Override
    public void setLocation(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
