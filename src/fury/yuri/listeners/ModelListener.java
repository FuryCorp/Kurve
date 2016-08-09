package fury.yuri.listeners;

import fury.yuri.model.Curve;
import fury.yuri.model.GameModel;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by yuri on 01/08/16.
 */
public class ModelListener implements IModelListener {

    private GraphicsContext gc;

    public ModelListener(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public void curveMoved(Curve curve) {

        if(curve.isTransparent()) {
            return;
        }

        Point2D currentHead = curve.getCurrentHead();
        Color color = curve.getColor();
        double radius = curve.getRadius();

        gc.setFill(color);
        gc.fillOval(currentHead.getX()-radius/2, currentHead.getY()-radius/2, radius, radius);
    }

    @Override
    public void modelInitialized(GameModel model) {

    }
}
