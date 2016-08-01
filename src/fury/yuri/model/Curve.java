package fury.yuri.model;

import fury.yuri.listeners.ICurveListener;
import fury.yuri.render.IRenderer;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuri on 29/07/16.
 */
public class Curve {

    //*****************************************************************

    private List<ICurveListener> listeners = new ArrayList<>();

    private Point2D headPosition;
    private List<Point2D> previousPositions = new ArrayList<>();
    private double angle;
    private Color color;

    private String left;
    private String right;

    private final int RADIUS = 5;
    private final double SPEED = 1;
    private final int ROTATION = 2;

    private boolean live = true;

    private IRenderer renderer;

    //*****************************************************************

    public Curve(Point2D startPosition) {
        this.headPosition = startPosition;
    }

    public void die() {
        live = false;
    }

    public void move() {
        if(!live) {
            return;
        }
        double deltaX = SPEED*Math.cos(Math.toRadians(angle));
        double deltaY = SPEED*Math.sin(Math.toRadians(angle));
        Point2D newPoint = new Point2D(headPosition.getX() + deltaX, headPosition.getY() + deltaY);
        previousPositions.add(newPoint);
        headPosition = newPoint;

        notifyListeners();
    }

    public void moveLeft() {
        if(!live) {
            return;
        }
        angle -= ROTATION;
        if(angle < 0) {
            angle = 360+angle;
        }
        move();
    }

    public void moveRight() {
        if(!live) {
            return;
        }
        angle += ROTATION;
        if(angle > 360) {
            angle = angle % 360;
        }
        move();
    }

    public void addListener(ICurveListener l) {

        listeners.add(l);
    }

    public void removeListener(ICurveListener l) {

        listeners.remove(l);
    }

    public void notifyListeners() {

        for(ICurveListener l : listeners) {
            l.curveMoved(this);
        }
    }

    public Color getColor() {
        return color;
    }

    public int getRADIUS() {
        return RADIUS;
    }

    public Point2D getLastHead() {
        return previousPositions.get(previousPositions.size()-1);
    }

    public Point2D getCurrentHead() {
        return headPosition;
    }

    public String getLeft() {
        return left;
    }

    public String getRight() {
        return right;
    }

    public void setLeft(String left) {
        this.left = left;
    }

    public void setRight(String right) {
        this.right = right;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
