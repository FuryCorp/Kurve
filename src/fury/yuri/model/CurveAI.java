package fury.yuri.model;

import fury.yuri.geometry.MyLine;
import fury.yuri.geometry.MyPoint;
import fury.yuri.listeners.ILineTracer;
import fury.yuri.network.NeuralNetwork;
import fury.yuri.utility.Utility;
import javafx.geometry.Point2D;
import javafx.scene.input.KeyCode;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by yuri on 04/08/16.
 */
public class CurveAI extends Curve {

    private final double anglePrecision = 10;
    private final double angleRange = 360;

    private NeuralNetwork network;

    private List<ILineTracer> lineTracers = new ArrayList<>();

    private Random rand = new Random();

    public CurveAI(Point2D startPosition, NeuralNetwork network) {
        super(startPosition, KeyCode.A.toString(), KeyCode.S.toString()); // null null
        this.network = network;
    }

    @Override
    public void scanEnvironment(GameModel model) {
        //analizirat model -> izvuc iz modela ulaz za mrezu -> ulaz provuc kroz mrezu ->
        //dobit izlaz -> pomaknut kurvu na racun izlaza
        //ulaz za mrezu: 4 udaljenosti od bounding boxa, udaljenost i relativni kut do najblize i najdalje barijere,
        //               udaljenost i relativni kut do glave najblizeg neprijatelja

        if(model.isPressed(getLeft())) {
            moveLeft();
        }
        else if(model.isPressed(getRight())) {
            moveRight();
        }
        if(!model.isPressed(getRight()) && !model.isPressed(getLeft())) {
            move();
        }

        List<Line2D> lines = new ArrayList<>();
        //lines.add(lineToNearestLiveEnemyHead(model));
        if(rand.nextDouble() < 0.5) {
            for(ILineTracer lt : lineTracers) {
                lt.drawLines(lines);
            }
        }
    }

    //***********************GET SOME INFORMATION FOR AI
    //PAZIT NA DEBLJINE
    //iz linija lako mogu izvuc udaljenosti
/*
    private double leftWallDistance(GameModel model) {
        Line2D l = lineToLeftWall(model);
        return Utility.distanceBetween(l.getX1(), l.getY1(), l.getX2(), l.getY2());
    }

    private double upperWallDistance(GameModel model) {
        Line2D l = lineToUpperWall(model);
        return Utility.distanceBetween(l.getX1(), l.getY1(), l.getX2(), l.getY2());
    }

    private double rightWallDistance(GameModel model) {
        Line2D l = lineToRightWall(model);
        return Utility.distanceBetween(l.getX1(), l.getY1(), l.getX2(), l.getY2());

    }

    private double downWallDistance(GameModel model) {
        Line2D l = lineToDownWall(model);
        return Utility.distanceBetween(l.getX1(), l.getY1(), l.getX2(), l.getY2());
    }

    private Line2D lineToLeftWall(GameModel model) {
        double headX = getCurrentHead().getX();
        double headY = getCurrentHead().getY();
        double radius = getRadius();

        MyPoint start = new MyPoint(headX-radius/2, headY);
        MyPoint end = new MyPoint(model.getBoundingBox().getMinX(), headY);

        return new MyLine(start, end);
    }

    private Line2D lineToUpperWall(GameModel model) {
        double headX = getCurrentHead().getX();
        double headY = getCurrentHead().getY();
        double radius = getRadius();

        MyPoint start = new MyPoint(headX, headY-radius/2);
        MyPoint end = new MyPoint(headX, model.getBoundingBox().getMinY());

        return new MyLine(start, end);
    }

    private Line2D lineToRightWall(GameModel model) {
        double headX = getCurrentHead().getX();
        double headY = getCurrentHead().getY();
        double radius = getRadius();

        MyPoint start = new MyPoint(headX+radius/2, headY);
        MyPoint end = new MyPoint(model.getBoundingBox().getMaxX(), headY);

        return new MyLine(start, end);

    }

    private Line2D lineToDownWall(GameModel model) {
        double headX = getCurrentHead().getX();
        double headY = getCurrentHead().getY();
        double radius = getRadius();

        MyPoint start = new MyPoint(headX, headY+radius/2);
        MyPoint end = new MyPoint(headX, model.getBoundingBox().getMaxY());

        return new MyLine(start, end);
    }

    private class LineTuple {

        Line2D closest;
        Line2D farthest;

        public LineTuple(Line2D closest, Line2D farthest) {
            this.closest = closest;
            this.farthest = farthest;
        }
    }

    private List<Line2D> linesToClosestAndFarthestBarriers(double minusAngle, double plusAngle, GameModel model) {

        Point2D head = getCurrentHead();
        Point2D closest = new Point2D(10000, 10000);
        double closestDist = Utility.distanceBetween(head.getX(), head.getY(), closest.getX(), closest.getY());
        Point2D farthest = head;
        double farthestDist = 0;

        for(double angle=getAngle()+minusAngle; angle<getAngle()+plusAngle; angle+=anglePrecision) {
            double a = angle;
            if(a < 0) {
                a = 360 + a;
            } else if(a > 360) {
                a = a % 360;
            }

            Point2D closestForAngle = new Point2D(10000, 10000);
            double closestForAngleDist = Utility.distanceBetween(head.getX(), head.getY(), closestForAngle.getX(), closestForAngle.getY());

            List<Point2D> intersections = Utility.intersectingPointsFor(this, a, model);
            for(Point2D p : intersections) {
                double dist = Utility.distanceBetween(head.getX(), head.getY(), p.getX(), p.getY());
                if(dist < closestDist) {
                    closest = p;
                    closestDist = dist;
                }
                if(dist < closestForAngleDist) {
                    closestForAngle = p;
                    closestForAngleDist = dist;
                }
            }
            if(closestForAngleDist > farthestDist) {
                farthest = closestForAngle;
                farthestDist = closestForAngleDist;
            }
        }
        List<Line2D> result = new ArrayList<>();
        result.add(new MyLine(new MyPoint(head.getX(), head.getY()), new MyPoint(closest.getX(), closest.getY())));
        result.add(new MyLine(new MyPoint(head.getX(), head.getY()), new MyPoint(farthest.getX(), farthest.getY())));

        return result;
    }

    //izvuc nakon poziva lineToClosestBarrier <- ne smijem je previše puta pozivat...
    public double relativeAngleToClosestBarrier(GameModel model) {
        return 0;
    }

    public double relativeAngleToFarthestBarrier(GameModel model) {
        return 0;
    }

    private Line2D lineToNearestLiveEnemyHead(GameModel model) {

        List<Point2D> heads = new ArrayList<>();

        model.getLiveCurves().forEach(c -> {
            if(!c.equals(this)) {
                heads.add(c.getCurrentHead());
            }
        });

        // NULL POINTER EXCEPTION KASNIJE U KODU ??!?!?!?
        if(heads.isEmpty()) {
            return null;
        }

        Point2D head = getCurrentHead();
        Point2D closest = heads.get(0);
        double closestDist = Utility.distanceBetween(closest.getX(), closest.getY(), head.getX(), head.getY());

        for(Point2D p : heads) {
            double dist = Utility.distanceBetween(p.getX(), p.getY(), head.getX(), head.getY());
            if(dist < closestDist) {
                closest = p;
                closestDist = dist;
            }
        }

        return new MyLine(new MyPoint(head.getX(), head.getY()), new MyPoint(closest.getX(), closest.getY()));
    }


    public double relativeAngleToNearestEnemyHead(GameModel model) {
        return 0;
    }

    public void addLineTracer(ILineTracer lt) {
        lineTracers.add(lt);
    }

    public void removeLineTracer(ILineTracer lt) {
        lineTracers.remove(lt);
    }
   */
}
