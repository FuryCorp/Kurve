package fury.yuri.environment;

import fury.yuri.model.Curve;

/**
 * Created by yuri on 01/08/16.
 */
public interface IEnvironmentProvider {

    void addEnvironmentListener(IEnvironmentListener listener);

    void removeEnvironmentListener(IEnvironmentListener listener);

    void moveLeft(Curve curve);

    void moveRight(Curve curve);

    void moveFront(Curve curve);
}
