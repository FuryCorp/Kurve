package fury.yuri.listeners;

import fury.yuri.model.Curve;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Created by yuri on 01/08/16.
 */
public class CanvasCurveListener implements ICurveListener {

    private GraphicsContext gc;

    public CanvasCurveListener(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public void curveMoved(Curve curve) {

        Point2D lastHead = curve.getLastHead();
        Point2D currentHead = curve.getCurrentHead();
        Color color = curve.getColor();
        int radius = curve.getRADIUS();

        gc.setLineWidth(radius);
        gc.setStroke(color);
        gc.strokeLine(lastHead.getX(), lastHead.getY(), currentHead.getX(), currentHead.getY());
    }
}
