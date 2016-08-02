package fury.yuri.model;

import fury.yuri.listeners.IModelListener;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;

import java.util.*;

/**
 * Created by yuri on 29/07/16.
 */
public class GameModel {

    private List<IModelListener> listeners = new ArrayList<>();

    private List<Curve> curves = new ArrayList<>();
    private Set<String> pressedKeys = new LinkedHashSet<>();
    private BoundingBox boundingBox;

    public void addCurve(Curve curve) {
        curves.add(curve);
    }

    public void addPressedKey(String key) {
        if(!pressedKeys.contains(key)) {
            pressedKeys.add(key);
        }
    }

    public void removePressedKey(String key) {
        if(pressedKeys.contains(key)) {
            pressedKeys.remove(key);
        }
    }

    public void update() {
        for(Curve curve : curves) {
            if(pressedKeys.contains(curve.getLeft())) {
                curve.moveLeft();
            }
            else if(pressedKeys.contains(curve.getRight())) {
                curve.moveRight();
            }
            if(!pressedKeys.contains(curve.getRight()) && !pressedKeys.contains(curve.getLeft())) {
                curve.move();
            }
            notifyListeners(curve);

            if(!boundingBox.contains(curve.getCurrentHead())) {
                curve.die();
            } else {
                checkIntersectionsFor(curve);
            }
        }

    }

    private void checkIntersectionsFor(Curve curve) {

        Point2D head = curve.getCurrentHead();

        for(Curve check : curves) {
            if(!curve.equals(check)) {
                for(Point2D point : check.getPreviousPositions()) {
                    if(isCloseEnough(head, curve.getRadius(), point, check.getRadius())) {
                        curve.die();
                        break;
                    }
                }
            }
        }
    }

    private boolean isCloseEnough(Point2D point1, double radius1, Point2D point2, double radius2) {

        double minDist = radius1/2 + radius2/2;

        return (Math.abs(point1.getX() - point2.getX()) < minDist
                && Math.abs(point1.getY() - point2.getY()) < minDist);
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public void addListener(IModelListener l) {

        listeners.add(l);
    }

    public void removeListener(IModelListener l) {

        listeners.remove(l);
    }

    private void notifyListeners(Curve curve) {

        for(IModelListener l : listeners) {
            l.curveMoved(curve);
        }
    }
}
