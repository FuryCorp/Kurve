package fury.yuri.listeners;

import javafx.scene.canvas.GraphicsContext;

import java.awt.geom.Line2D;
import java.util.List;

/**
 * Created by yuri on 10/08/16.
 */
public interface ILineTracer {

    void drawLines(List<Line2D> lines);
}
