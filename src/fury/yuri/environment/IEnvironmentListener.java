package fury.yuri.environment;

import javafx.geometry.Point2D;

/**
 * Created by yuri on 01/08/16.
 */
public interface IEnvironmentListener {

    void environmentChanged(IEnvironmentProvider provider, EnvironmentVariables variables);
}
