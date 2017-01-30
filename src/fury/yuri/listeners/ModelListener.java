package fury.yuri.listeners;

import fury.yuri.model.Curve;
import fury.yuri.model.GameModel;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Created by yuri on 01/08/16.
 */
public class ModelListener implements IModelListener {

    private GraphicsContext gc;

    private Random rand = new Random();

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
        //gc.fillOval((int)Math.round(currentHead.getX()-radius/2), (int)Math.round(currentHead.getY()-radius/2), radius, radius);
        gc.fillOval(currentHead.getX()-radius/2, currentHead.getY()-radius/2, radius, radius);
        //gc.beginPath();
        //gc.fillOval(rand.nextDouble()*300+50,rand.nextDouble()*500+50,4,4);
        //gc.closePath();
    }

    @Override
    public void modelInitialized(GameModel model) {

    }
}
