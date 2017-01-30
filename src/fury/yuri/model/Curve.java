package fury.yuri.model;

import fury.yuri.geometry.MyPoint;
import fury.yuri.geometry.Pixel;
import fury.yuri.utility.Utility;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.awt.geom.Line2D;
import java.util.*;

/**
 * Created by yuri on 29/07/16.
 */
public abstract class Curve {

    private Set<Pixel> pixels = new HashSet<>();
    //*****************************************************************
    private Point2D headPosition;
    private List<Point2D> previousPositions = new ArrayList<>();
    private double angle;
    private Color color;

    private String left;
    private String right;

    private int radius = 5;
    private double speed = 0.5;
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
        pixels.add(Utility.pointToPixel(startPosition));
        this.left = left;
        this.right = right;
    }

    /**
     * Check if any of pixels including central pixel and its surroundings at radius r intersects
     * with any of pixels that this Curve aready contains
     * @param point center of given head
     * @param r radius
     * @return check description
     */

    public boolean contains(Point2D point, int r, boolean self) {

        Pixel pixel = Utility.pointToPixel(point);
        Set<Pixel> generatedPixels = Utility.generatePoints(pixel, r);
        Set<Pixel> headPixels = Utility.generatePoints(Utility.pointToPixel(headPosition), radius);

        if (self) {
            this.pixels.removeAll(headPixels);
        }
        for (Pixel p : generatedPixels) {
            if(this.pixels.contains(p)) {
                return true;
            }
        }
        this.pixels.addAll(headPixels);
        return false;
    }

    /**
     * Gets points of this curve which line intersects,
     * if self is true than dots touching the head are ignored
     * @param line
     * @param self
     * @return
     */

    public List<Point2D> intersectingPoints(Line2D line, boolean self) {

        //IZMJENIT !!!!

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
        System.out.println("DIEEE");
        live = false;
    }

    public void move() {
        if(!live) {
            return;
        }
        //checkTransparency();

        double deltaX = speed*Math.cos(Math.toRadians(angle));
        double deltaY = speed*Math.sin(Math.toRadians(angle));
        Point2D newPoint = new Point2D(headPosition.getX() + deltaX, headPosition.getY() - deltaY);

        Pixel pixel = Utility.pointToPixel(newPoint);

        //System.out.println("Pixel: " + newPoint.getX() + " " + newPoint.getY());
        //System.out.println("Pixel: " + pixel.getX() + " " + pixel.getY());

        if(!transparent) {
            previousPositions.add(newPoint);
            pixels.addAll(Utility.generatePoints(pixel, radius));
        }

        headPosition = newPoint;

        //TU SE KUVA NESTO -> spremaj prave vrijednosti samo radi updateanja, prave za model su one zaokružene

        //System.out.println("pxl -->" + pixel);
        //System.out.println("new -->" + newPoint);

        //TODO sve ovo ispod

        //IDEJA: za glavu kad idemo provjeravat jel mrtva: uzmi njen radijus i generiraj sve cijele brojeve
        //koju su obuhvaćeni tim radijusom oko glave. za svaki taj broj iz setova ostalih kurvi pokušat pronać
        //te brojeve pa ako su isti onda je udarila u nešto. problem je jedino radijus druge kurve, ova metoda će
        //samo centar druge kurve uzimat u obzir. Jedna opcija je da istovremeno iz drugih kurvi odmah izvucem sve
        //piksele oko centra koje zauzimaju i spremim u set
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

    public Set<Pixel> getPixels() {
        return pixels;
    }

    @Override
    public String toString() {
        return color.toString();
    }

    public Color getColor() {
        return color;
    }

    public int getRadius() {
        return radius;
    }

    public double getSpeed() {
        return speed;
    }

    public Point2D getCurrentHead() {
        return headPosition;
    }

    public boolean isLive() {
        return live;
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
