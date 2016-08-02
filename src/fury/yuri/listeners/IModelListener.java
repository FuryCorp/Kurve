package fury.yuri.listeners;

import fury.yuri.model.Curve;
import fury.yuri.model.GameModel;
import javafx.geometry.Point2D;

/**
 * Created by yuri on 01/08/16.
 */
public interface IModelListener {

    void curveMoved(Curve curve);

    void modelInitialized(GameModel model);
}
