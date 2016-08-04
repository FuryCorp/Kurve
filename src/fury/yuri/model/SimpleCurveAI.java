package fury.yuri.model;

import javafx.geometry.Point2D;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yuri on 04/08/16.
 */
public class SimpleCurveAI extends Curve {

    private final double STEP = 10;
    private final double SPEED = 1;

    public SimpleCurveAI(Point2D startPosition, String left, String right) {
        super(startPosition, left, right);
    }

    @Override
    public void scanEnvironment(GameModel model) {
        Point2D curr = getCurrentHead();
        double bestAngle = 0;
        double bestDistance = 0;
        for(double angle=0; angle<360; angle+=STEP) {
            int distance = 0;
            while(!intersects(model, curr)) {
                double deltaX = SPEED*Math.cos(Math.toRadians(angle));
                double deltaY = SPEED*Math.sin(Math.toRadians(angle));
                curr = new Point2D(curr.getX() + deltaX, curr.getY() - deltaY);
                distance++;
            }
            if(distance > bestDistance) {
                bestDistance = distance;
                bestAngle = angle;
            }
            curr = getCurrentHead();
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
}
