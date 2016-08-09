package fury.yuri.model;

import fury.yuri.network.NeuralNetwork;
import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

import java.awt.geom.Line2D;

/**
 * Created by yuri on 04/08/16.
 */
public class CurveAI extends Curve {

    private NeuralNetwork network;

    public CurveAI(Point2D startPosition, NeuralNetwork network) {
        super(startPosition, null, null);
        this.network = network;
    }

    @Override
    public void scanEnvironment(GameModel model) {
        //analizirat model -> izvuc iz modela ulaz za mrezu -> ulaz provuc kroz mrezu ->
        //dobit izlaz -> pomaknut kurvu na racun izlaza
    }

    //***********************GET SOME INFORMATION FOR AI
    //PAZIT NA DEBLJINE
    //iz linija lako mogu izvuc udaljenosti

    /*

    public double leftWallDistance(GameModel model) {

    }

    public double upperWallDistance(GameModel model) {

    }

    public double rightWallDistance(GameModel model) {

    }

    public double downWallDistance(GameModel model) {

    }

    public Line2D lineToLeftWall(GameModel model) {

    }

    public Line2D lineToUpperWall(GameModel model) {

    }

    public Line2D lineToRightWall(GameModel model) {

    }

    public Line2D lineToDownWall(GameModel model) {

    }

    public Line2D lineToClosestBarrier(GameModel model) {

    }

    public double relativeAngleToClosestBarrier(GameModel model) {

    }

    public Line2D lineToFarthestBarrier(GameModel model) {

    }

    public double relativeAngleToFarthestBarrier(GameModel model) {

    }

    public Line2D lineToNearestEnemyHead(GameModel model) {

    }

    public double relativeAngleToNearestEnemyHead(GameModel model) {

    }

    */
}
