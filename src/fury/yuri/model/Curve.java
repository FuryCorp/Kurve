package fury.yuri.model;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuri on 29/07/16.
 */
public class Curve {

    private Point2D headPosition;
    private List<Point2D> previousPositions = new ArrayList<>();
    private double angle;
    private int score;

    private String left;
    private String right;

    private static final int RADIUS = 10;
    private static final int SPEED = 2;
    private static final int ROTATION = 1;

    private boolean live = true;

    private IRenderer renderer;

    public void display() {
        renderer.render();
    }

    public void die() {
        live = false;
    }

    public void move() {
        if(!live) {
            return;
        }
    }

    public void moveRight() {
        if(!live) {
            return;
        }
        //...
        move();
    }

    public void moveLeft() {
        if(!live) {
            return;
        }
        //...
        move();
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
}
