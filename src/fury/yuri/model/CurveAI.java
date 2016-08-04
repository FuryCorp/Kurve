package fury.yuri.model;

import fury.yuri.network.NeuralNetwork;
import javafx.geometry.Point2D;

/**
 * Created by yuri on 04/08/16.
 */
public class CurveAI extends Curve {

    private NeuralNetwork network;

    public CurveAI(Point2D startPosition, String left, String right, NeuralNetwork network) {
        super(startPosition, left, right);
        this.network = network;
    }

    @Override
    public void scanEnvironment(GameModel model) {
        //analizirat model -> izvuc iz modela ulaz za mrezu -> ulaz provuc kroz mrezu ->
        //dobit izlaz -> pomaknut kurvu na racun izlaza
    }
}
