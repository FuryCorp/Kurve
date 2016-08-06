package fury.yuri.geometry;

import java.awt.geom.Rectangle2D;

/**
 * Created by yuri on 05/08/16.
 */
public class MyRectangle extends Rectangle2D {
    @Override
    public void setRect(double v, double v1, double v2, double v3) {

    }

    @Override
    public int outcode(double v, double v1) {
        return 0;
    }

    @Override
    public Rectangle2D createIntersection(Rectangle2D rectangle2D) {
        return null;
    }

    @Override
    public Rectangle2D createUnion(Rectangle2D rectangle2D) {
        return null;
    }

    @Override
    public double getX() {
        return 0;
    }

    @Override
    public double getY() {
        return 0;
    }

    @Override
    public double getWidth() {
        return 0;
    }

    @Override
    public double getHeight() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}
