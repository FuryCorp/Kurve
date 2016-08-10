package fury.yuri.model;

import fury.yuri.geometry.MyPoint;
import fury.yuri.utility.Utility;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by yuri on 29/07/16.
 */
public abstract class Curve {

    //*****************************************************************
    private Point2D headPosition;
    //slagat u stablo ???
    private List<Point2D> previousPositions = new ArrayList<>();
    private double angle;
    private Color color;

    private String left;
    private String right;

    private double radius = 5;
    private double speed = 1.2;
    private double rotation = 3;
    private double holeChance = 0.002;
    private double holeSize = radius*3;
    private double holeCounter = radius*3;

    private boolean transparent;
    private boolean live = true;

    private Random rand = new Random();
    //*****************************************************************

    public Curve(Point2D startPosition, String left, String right) {
        this.headPosition = startPosition;
        this.left = left;
        this.right = right;
    }

    public boolean contains(Point2D point, double radius) {
        double minDist = radius/2 + this.radius/2;

        for(Point2D p : previousPositions) {
            if(Math.abs(point.getX() - p.getX()) < minDist
                    && Math.abs(point.getY() - p.getY()) < minDist) {
                return true;
            }
        }

        return false;
    }

    public List<Point2D> intersectingPoints(Line2D line, boolean self) {

        double err = radius;
        List<Point2D> result = new ArrayList<>();

        if(self) {
            List<Point2D> positions = new ArrayList<>(previousPositions);
            Collections.reverse(positions);
            int notCounted = 0;
            for(Point2D p : positions) {
                if(Math.abs(p.getX()-headPosition.getX()) < radius && Math.abs(p.getY()-headPosition.getY()) < radius) {
                    notCounted++;
                } else {
                    break;
                }
            }
            int size = previousPositions.size();
            for(int i=0; i<size-notCounted; i++) {
                Point2D p = previousPositions.get(i);
                MyPoint point = new MyPoint(p.getX(), p.getY());
                if(line.ptSegDist(point) < err) {
                    result.add(p);
                }
            }
        } else {
            for (Point2D p : previousPositions) {
                MyPoint point = new MyPoint(p.getX(), p.getY());
                if (line.ptSegDist(point) < err) {
                    result.add(p);
                }
            }
        }

        return result;
    }

    public void die() {
        live = false;
    }

    public void move() {
        if(!live) {
            return;
        }
        checkTransparency();
        double deltaX = speed*Math.cos(Math.toRadians(angle));
        double deltaY = speed*Math.sin(Math.toRadians(angle));
        Point2D newPoint = new Point2D(headPosition.getX() + deltaX, headPosition.getY() - deltaY);
        if(!transparent) {
            previousPositions.add(headPosition);
        }
        headPosition = newPoint;
    }

    private void checkTransparency() {

        if(transparent) {
            holeCounter--;
            if(holeCounter <= 0) {
                transparent = false;
            }
        } else {
            if(rand.nextDouble() < holeChance) {
                transparent = true;
                holeCounter = holeSize;
            }
        }
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

    public boolean isTransparent() {
        return transparent;
    }
}
