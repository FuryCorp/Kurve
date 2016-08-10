package fury.yuri.listeners;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yuri on 10/08/16.
 */
public class LineTracer implements ILineTracer {

    private GraphicsContext gc;
    private List<Line2D> currentLines = new ArrayList<>();

    public LineTracer(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public void drawLines(List<Line2D> lines) {
        clearOldLines();
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        for(Line2D line : lines) {
            gc.strokeLine(line.getX1(), line.getY1(), line.getX2(), line.getY2());
            //gc.setStroke(Color.YELLOW);
        }
    }

    private void clearOldLines() {
        for(Line2D l : currentLines) {
            Line line = new Line(l.getX1(), l.getY1(), l.getX2(), l.getY2());

        }
    }
}
