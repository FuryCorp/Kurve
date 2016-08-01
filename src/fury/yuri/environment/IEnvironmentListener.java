package fury.yuri.environment;

import javafx.geometry.Point2D;

/**
 * Created by yuri on 01/08/16.
 */
public interface IEnvironmentListener {

    void environmentChanged(Point2D oldHead, Point2D newHead);
}
