package fury.yuri.model;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yuri on 29/07/16.
 */
public abstract class Curve {

    //*****************************************************************
    private Point2D headPosition;
    private List<Point2D> previousPositions = new ArrayList<>();
    private double angle;
    private Color color;

    private String left;
    private String right;

    private double radius = 5;
    private double speed = 1;
    private double rotation = 2;
    //private double holeChance = 0.2;
    //private double holeSize = 10;

    private boolean live = true;
    //*****************************************************************

    public Curve(Point2D startPosition, String left, String right) {
        this.headPosition = startPosition;
        this.left = left;
        this.right = right;
    }

    public void die() {
        live = false;
    }

    public void move() {
        if(!live) {
            return;
        }
        double deltaX = speed*Math.cos(Math.toRadians(angle));
        double deltaY = speed*Math.sin(Math.toRadians(angle));
        Point2D newPoint = new Point2D(headPosition.getX() + deltaX, headPosition.getY() - deltaY);
        previousPositions.add(headPosition);
        headPosition = newPoint;
    }

    public void moveRight() {
        if(!live) {
            return;
        }
        angle -= rotation;
        if(angle < 0) {
            angle = 360+angle;
        }
        move();
    }

    public void moveLeft() {
        if(!live) {
            return;
        }
        angle += rotation;
        if(angle > 360) {
            angle = angle % 360;
        }
        move();
    }

    public abstract void scanEnvironment(GameModel model);

    @Override
    public String toString() {
        return color.toString();
    }

    public Color getColor() {
        return color;
    }

    public double getRadius() {
        return radius;
    }

    public double getSpeed() {
        return speed;
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

    public void setColor(Color color) {
        this.color = color;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public List<Point2D> getPreviousPositions() {
        return previousPositions;
    }

    public double getAngle() {
        return angle;
    }
}
