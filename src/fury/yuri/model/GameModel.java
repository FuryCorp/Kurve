package fury.yuri.model;

import fury.yuri.geometry.Pixel;
import fury.yuri.listeners.IModelListener;
import fury.yuri.utility.Utility;
import javafx.geometry.BoundingBox;
import javafx.geometry.Point2D;

import java.util.*;

/**
 * Created by yuri on 29/07/16.
 */
public class GameModel {

    private List<IModelListener> modelListeners = new ArrayList<>();

    private List<Curve> curves = new ArrayList<>();
    private List<Curve> deadCurves = new ArrayList<>();
    private List<Curve> liveCurves = new ArrayList<>();

    private Set<String> pressedKeys = new LinkedHashSet<>();
    private BoundingBox boundingBox;

    public void addCurve(Curve curve) {
        curves.add(curve);
        liveCurves.add(curve);
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

    public boolean isPressed(String key) {
        return pressedKeys.contains(key);
    }

    public void update() {
        for(Curve curve : curves) {
            if(curve.isLive()) {
                curve.scanEnvironment(this);
                //TODO dodat metodu koja će provjerat intersekcije preko set-a
                killCurves(curve);
                //checkIntersectionsFor(curve);
                notifyModelListeners(curve);
            }
        }
        for(Curve dead : deadCurves) {
            liveCurves.remove(dead);
        }
    }

    private void killCurves(Curve current) {
        if(!isInside(current.getCurrentHead())) {
            current.die();
            return;
        }

        for(Curve c : curves) {
            if(current.equals(c)) {
                if(c.contains(current.getCurrentHead(), current.getRadius(), true)) {
                    current.die();
                }
            } else {
                if(c.contains(current.getCurrentHead(), current.getRadius(), false)) {
                    current.die();
                }
            }
        }
    }

    public boolean isInside(Point2D point){
        return boundingBox.contains(point);
    }

    public List<Curve> getCurves() {
        return curves;
    }

    public List<Curve> getLiveCurves() {
        return liveCurves;
    }

    private void checkIntersectionsFor(Curve curve) {
        if(!isInside(curve.getCurrentHead())) {
            curve.die();
            deadCurves.add(curve);
            return;
        }

        Point2D head = curve.getCurrentHead();

        Iterator<Curve> iterator = curves.iterator();

        while(iterator.hasNext()) {
            Curve check = iterator.next();
            if(!curve.equals(check)) {
                for(Point2D point : check.getPreviousPositions()) {
                    if(Utility.isCloseEnough(head, curve.getRadius(), point, check.getRadius())) {
                        curve.die();
                        deadCurves.add(curve);
                        break;
                    }
                }
            } else {
                double radius = curve.getRadius();
                List<Point2D> positions = new ArrayList<>(curve.getPreviousPositions());
                Collections.reverse(positions);
                int notCounted = 0;
                for(Point2D p : positions) {
                    if(Math.abs(p.getX()-head.getX()) < radius && Math.abs(p.getY()-head.getY()) < radius) {
                        notCounted++;
                    } else {
                        break;
                    }
                }
                int size = curve.getPreviousPositions().size();
                for(int i=0; i<size-notCounted; i++) {
                    Point2D point = curve.getPreviousPositions().get(i);
                    if(Utility.isCloseEnough(head, curve.getRadius(), point, check.getRadius())) {
                        curve.die();
                        deadCurves.add(curve);
                        break;
                    }
                }
            }
        }
    }

    public void setBoundingBox(BoundingBox boundingBox) {
        this.boundingBox = boundingBox;
    }

    public BoundingBox getBoundingBox() {
        return boundingBox;
    }

    public void addModelListener(IModelListener l) {
        modelListeners.add(l);
    }

    public void removeModelListener(IModelListener l) {
        modelListeners.remove(l);
    }

    private void notifyModelListeners(Curve curve) {
        for(IModelListener l : modelListeners) {
            l.curveMoved(curve);
        }
    }
}
