package fury.yuri.environment;

/**
 * Created by yuri on 01/08/16.
 */
public interface IEnvironmentProvider {

    void addEnvironmentListener(IEnvironmentListener listener);

    void removeEnvironmentListener(IEnvironmentListener listener);
}
