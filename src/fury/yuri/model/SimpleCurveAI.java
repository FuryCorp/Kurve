package fury.yuri.model;

import fury.yuri.geometry.MyLine;
import fury.yuri.geometry.MyPoint;
import fury.yuri.utility.Utility;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.awt.geom.Line2D;
import java.util.*;

/**
 * Created by yuri on 04/08/16.
 */
public class SimpleCurveAI extends Curve {

    private final double STEP = 10;
    private GraphicsContext gc;
    Random rand = new Random();

    public SimpleCurveAI(Point2D startPosition, GraphicsContext gc) {
        super(startPosition, null, null);
        this.gc = gc;
    }

    @Override
    public void scanEnvironment(GameModel model) {
        Point2D curr = getCurrentHead();
        double bestAngle = 0;
        double bestDistance = 0;
        for(double angle=0; angle<360; angle+=STEP) {

            Point2D end = Utility.getEndPointFor(curr, angle, model);
            //getEndPointFor(curr, angle, model);
            Line2D line = new MyLine(new MyPoint(curr.getX(), curr.getY()), new MyPoint(end.getX(), end.getY()));

            double distance = minIntersectionDistance(model, line);
            if(distance > bestDistance) {
                bestDistance = distance;
                bestAngle = angle;
            }
        }

        double currAngle = getAngle();
        double rightAngle = 0;
        double leftAngle = 0;
        if(bestAngle > currAngle) {
            leftAngle = bestAngle-currAngle;
            rightAngle = 360-bestAngle+currAngle;
        } else if(bestAngle < currAngle) {
            rightAngle = currAngle-bestAngle;
            leftAngle = 360-currAngle+bestAngle;
        }
        if(rightAngle > leftAngle) {
            moveLeft();
        } else if(rightAngle < leftAngle){
            moveRight();
        } else {
            move();
        }
    }

    private Point2D getEndPointFor(Point2D start, double angle, GameModel model) {
        while(model.isInside(start)) {
            double deltaX = Math.cos(Math.toRadians(angle));
            double deltaY = Math.sin(Math.toRadians(angle));
            start = new Point2D(start.getX() + deltaX, start.getY() - deltaY);
        }

        /*
        System.out.println("REGULAR angle = " + angle);
        System.out.println("x: " + (start.getX()));
        System.out.println("y: " + (start.getY()));
        */

        return start;
    }

    private double distanceBetween(Point2D p1, Point2D p2) {

        double dx1 = p2.getX() - p1.getX();
        double dy1 = p2.getY() - p1.getY();
        return Math.sqrt(dx1 * dx1 + dy1 * dy1);
    }

    private double minIntersectionDistance(GameModel model, Line2D line) {

        double minDist = distanceBetween(new Point2D(line.getX1(), line.getY1()), new Point2D(line.getX2(), line.getY2()));

        Iterator<Curve> iterator = model.getCurves().iterator();

        while(iterator.hasNext()) {
            Curve check = iterator.next();
            double err = check.getRadius();

            if(!this.equals(check)) {
                for(Point2D p : check.getPreviousPositions()) {
                    double errDist = line.ptSegDist(new MyPoint(p.getX(), p.getY()));
                    double dist = distanceBetween(new Point2D(line.getX1(), line.getY1()), p);
                    if(errDist < err && minDist > dist) {
                        minDist = dist;
                    }
                }
            } else {
                double radius = getRadius();
                List<Point2D> positions = new ArrayList<>(getPreviousPositions());
                Collections.reverse(positions);
                int notCounted = 0;
                for(Point2D p : positions) {
                    if(Math.abs(p.getX()-getCurrentHead().getX()) < radius && Math.abs(p.getY()-getCurrentHead().getY()) < radius) {
                        notCounted++;
                    } else {
                        break;
                    }
                }
                int size = getPreviousPositions().size();
                for(int i=0; i<size-notCounted; i++) {
                    Point2D p = getPreviousPositions().get(i);
                    double errDist = line.ptSegDist(new MyPoint(p.getX(), p.getY()));
                    double dist = distanceBetween(new Point2D(line.getX1(), line.getY1()), p);
                    if(errDist < err && minDist > dist) {
                        minDist = dist;
                    }
                }
            }
        }
        return minDist;
    }

    /*
    private boolean intersects(GameModel model, Point2D point) {

        if(!model.isInside(point)) {
            return true;
        }

        Iterator<Curve> iterator = model.getCurves().iterator();

        while(iterator.hasNext()) {
            Curve check = iterator.next();
            if(!this.equals(check)) {
                for(Point2D p : check.getPreviousPositions()) {
                    if(model.isCloseEnough(point, getRadius(), p, check.getRadius())) {
                        return true;
                    }
                }
            } else {
                double radius = getRadius();
                List<Point2D> positions = new ArrayList<>(getPreviousPositions());
                Collections.reverse(positions);
                int notCounted = 0;
                for(Point2D p : positions) {
                    if(Math.abs(p.getX()-point.getX()) < radius && Math.abs(p.getY()-point.getY()) < radius) {
                        notCounted++;
                    } else {
                        break;
                    }
                }
                int size = getPreviousPositions().size();
                for(int i=0; i<size-notCounted; i++) {
                    Point2D p = getPreviousPositions().get(i);
                    if(model.isCloseEnough(point, getRadius(), p, check.getRadius())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    */
}
