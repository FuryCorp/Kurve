package fury.yuri.geometry;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by yuri on 05/08/16.
 */
public class MyLine extends Line2D {

    private Point2D start;
    private Point2D end;

    public MyLine(Point2D start, Point2D end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public double getX1() {
        return start.getX();
    }

    @Override
    public double getY1() {
        return start.getY();
    }

    @Override
    public Point2D getP1() {
        return start;
    }

    @Override
    public double getX2() {
        return end.getX();
    }

    @Override
    public double getY2() {
        return end.getY();
    }

    @Override
    public Point2D getP2() {
        return end;
    }

    @Override
    public void setLine(double x1, double y1, double x2, double y2) {
        start = new MyPoint(x1, y1);
        end = new MyPoint(x2, y2);
    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }
}
