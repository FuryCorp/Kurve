package fury.yuri.model;

import javafx.geometry.BoundingBox;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuri on 29/07/16.
 */
public class GameModel {

    private List<Curve> curves = new ArrayList<>();
    private List<String> pressedKeys = new ArrayList<>();
    private BoundingBox boundingBox;

    public void addPressedKey(String key) {
        if(!pressedKeys.contains(key)) {
            pressedKeys.add(key);
        }
    }

    public void addCurve(Curve curve) {
        curves.add(curve);
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
            if(pressedKeys.contains(curve.getRight())) {
                curve.moveRight();
            }
            if(!pressedKeys.contains(curve.getRight()) && !pressedKeys.contains(curve.getLeft())) {
                curve.move();
            }
        }

        checkIntersections();
    }

    private void checkIntersections() {
        //provjerit jel glava ijedne kurve dodiruje tijelo ijedne kurve (samo prednji dio glave ???)
        //curve.die()
    }
}
