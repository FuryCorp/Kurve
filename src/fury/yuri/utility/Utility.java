package fury.yuri.utility;

import fury.yuri.geometry.MyLine;
import fury.yuri.geometry.MyPoint;
import fury.yuri.geometry.Pixel;
import fury.yuri.model.Curve;
import fury.yuri.model.GameModel;
import javafx.geometry.Point2D;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by yuri on 08/08/16.
 */
public class Utility {

    public static boolean isCloseEnough(Point2D point1, double radius1, Point2D point2, double radius2) {
        double distFactor = 1.3;

        double minDist = (radius1/2 + radius2/2)/distFactor;

        return (Math.abs(point1.getX() - point2.getX()) < minDist
                && Math.abs(point1.getY() - point2.getY()) < minDist);
    }

    public static double distanceBetween(double x1, double y1, double x2, double y2) {

        double dx1 = x2 - x1;
        double dy1 = y2 - y1;
        return Math.sqrt(dx1 * dx1 + dy1 * dy1);
    }

    /**
     * Finds all intersecting points of line (with any of model objects)
     * starting on given curve head with given angle.
     * @param currentCurve
     * @param angle
     * @param model
     * @return
     */

    public static List<Point2D> intersectingPointsFor(Curve currentCurve, double angle, GameModel model) {
        List<Point2D> result = new ArrayList<>();
        Point2D start = currentCurve.getCurrentHead();
        Point2D end = getEndPointFor(start, angle, model);
        result.add(end);
        Line2D line = new MyLine(new MyPoint(start.getX(), start.getY()), new MyPoint(end.getX(), end.getY()));
        model.getCurves().forEach(c -> {
            if(!c.equals(currentCurve)) {
                result.addAll(c.intersectingPoints(line, false));
            } else {
                result.addAll(c.intersectingPoints(line, true));
            }
        });

        return result;
    }

    /**
     * Finds end point in field. That point is somewhere on the edge of bounding box.
     * @param start
     * @param angle
     * @param model
     * @return
     */

    public static Point2D getEndPointFor(Point2D start, double angle, GameModel model) {
        double relativeX = start.getX()-model.getBoundingBox().getMinX();
        double relativeY = start.getY()-model.getBoundingBox().getMinY();
        double relativeXOther = model.getBoundingBox().getMaxX()-start.getX();
        double relativeYOther = model.getBoundingBox().getMaxY()-start.getY();
        double len;
        if(angle>=0 && angle<90) {
            double breakAngle = Math.toDegrees(Math.atan(relativeY/relativeXOther));
            if(angle>breakAngle) {
                //upper bound
                len = relativeY/Math.cos(Math.toRadians(90-angle));
            } else {
                //right bound
                len = relativeXOther/Math.cos(Math.toRadians(angle));
            }
        } else if(angle>=90 && angle<180) {
            double breakAngle = Math.toDegrees(Math.atan(relativeX/relativeY)) + 90;
            if(angle<breakAngle) {
                //upper bound
                len = relativeY/Math.cos(Math.toRadians(angle-90));
            } else {
                //left bound
                len = relativeX/Math.cos(Math.toRadians(180-angle));
            }
        } else if(angle>=180 && angle < 270) {
            double breakAngle = Math.toDegrees(Math.atan(relativeYOther/relativeX)) + 180;
            if(angle<breakAngle) {
                //left bound
                len = relativeX/Math.cos(Math.toRadians(angle-180));
            } else {
                //down bound
                len = relativeYOther/Math.cos(Math.toRadians(270-angle));
            }
        } else {
            double breakAngle = Math.toDegrees(Math.atan(relativeXOther/relativeYOther)) + 270;
            if(angle<breakAngle) {
                //down bound
                len = relativeYOther/Math.cos(Math.toRadians(angle-270));
            } else {
                len = relativeXOther/Math.cos(Math.toRadians(360-angle));
            }
        }

        double deltaX = len*Math.cos(Math.toRadians(angle));
        double deltaY = len*Math.sin(Math.toRadians(angle));

        /*
        System.out.println("UTILITY angle = " + angle);
        System.out.println("x: " + (start.getX()+deltaX));
        System.out.println("y: " + (start.getY()-deltaY));
        */

        return new Point2D(start.getX()+deltaX, start.getY()-deltaY);
    }

    public static Set<Pixel> generatePoints(Pixel pixel, int radius) {

        Set<Pixel> result = new HashSet<>();

        for(int x = pixel.x-radius; x<pixel.x+radius; x++) {
            for(int y = pixel.y-radius; y<pixel.y+radius; y++) {
                if(Math.pow(x - pixel.x, 2) + Math.pow(y - pixel.y, 2) < Math.pow(radius, 2)) {
                    result.add(new Pixel(x, y));
                }
            }
        }
        return result;
    }

    public static Pixel pointToPixel(Point2D point) {
        return new Pixel((int) Math.round(point.getX()), (int) Math.round(point.getY()));
    }
}
