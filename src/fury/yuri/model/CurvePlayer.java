package fury.yuri.model;

import javafx.geometry.Point2D;

import java.util.List;

/**
 * Created by yuri on 04/08/16.
 */
public class CurvePlayer extends Curve {

    public CurvePlayer(Point2D startPosition, String left, String right) {
        super(startPosition, left, right);
    }

    @Override
    public void scanEnvironment(GameModel model) {
        if(model.isPressed(getLeft())) {
            moveLeft();
        }
        else if(model.isPressed(getRight())) {
            moveRight();
        }
        if(!model.isPressed(getRight()) && !model.isPressed(getLeft())) {
            move();
        }
    }
}
