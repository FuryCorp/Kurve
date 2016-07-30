package fury.yuri.render;

import fury.yuri.model.Curve;
import javafx.scene.canvas.GraphicsContext;

/**
 * Created by yuri on 30/07/16.
 */
public class CanvasRenderer implements IRenderer {

    private GraphicsContext gc;

    public CanvasRenderer(GraphicsContext gc) {
        this.gc = gc;
    }

    @Override
    public void render(Curve curve) {

    }
}
