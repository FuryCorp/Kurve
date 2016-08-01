package sample;

import fury.yuri.engine.GameEngine;
import fury.yuri.listeners.CanvasCurveListener;
import fury.yuri.listeners.ICurveListener;
import fury.yuri.model.Curve;
import fury.yuri.model.GameModel;
import fury.yuri.render.CanvasRenderer;
import fury.yuri.render.IRenderer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Start.fxml"));
        primaryStage.setTitle("CurveFever");

        Scene scene = new Scene(root);

        Canvas canvas = (Canvas) scene.lookup("#canvas");
        root.setStyle("-fx-background-color: black");

        GraphicsContext gc = canvas.getGraphicsContext2D();

        draw(gc, scene);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void draw(GraphicsContext gc, Scene scene) {

        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(20);
        gc.strokeRect(0, 0, 978, 654);

        /*gc.setStroke(Color.RED);

        gc.setLineWidth(5);
        gc.bezierCurveTo(50, 70, 200, 200, 350, 70);
        gc.stroke();*/

        //parabola(gc);
        //test(gc);

        //curveTest(gc);
        movementTest(gc, scene);
    }

    private void movementTest(GraphicsContext gc, Scene scene) {

        GameModel model = new GameModel();
        Curve redCurve = new Curve(new Point2D(100, 100));
        redCurve.setColor(Color.RED);
        redCurve.setAngle(0);
        redCurve.setLeft(KeyCode.LEFT.toString());
        redCurve.setRight(KeyCode.RIGHT.toString());
        model.addCurve(redCurve);

        GameEngine engine = new GameEngine(model);

        ICurveListener listener = new CanvasCurveListener(gc);
        redCurve.addListener(listener);

        scene.addEventHandler(KeyEvent.KEY_PRESSED, engine.getOnKeyPressedEventHandler());
        scene.addEventHandler(KeyEvent.KEY_RELEASED, engine.getOnKeyReleasedEventHandler());

        engine.start();
    }

    private void parabola(GraphicsContext gc) {
        gc.setLineWidth(5);
        gc.setStroke(Color.RED);

        List<Point2D> parabolaPoints = new ArrayList<>();
        for(int x=0; x<200; x++) {
            int y = (int) (0.01*x*x);
            parabolaPoints.add(new Point2D(x,y));
        }

        gc.moveTo(0, 0);
        for(Point2D p : parabolaPoints) {
            gc.lineTo(p.getX(), p.getY());
        }
        gc.stroke();

        gc.moveTo(0, 0);
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(2);
        gc.lineTo(100, 100);
        gc.stroke();
    }

    private void test(GraphicsContext gc) {

        gc.setLineWidth(5);
        gc.setStroke(Color.BLUE);
        gc.strokeLine(10, 10, 100, 100);

        gc.setLineWidth(2);
        gc.setStroke(Color.BEIGE);
        gc.strokeLine(100, 100, 200, 300);
    }

    private void curveTest(GraphicsContext gc) {

        List<Point2D> points = new ArrayList<>();
        points.addAll(generateFor(0, 100, new Point2D(10, 10)));


        for(int i=1; i<50; i++) {
            points.addAll(generateFor(i, 10, points.get(points.size()-1)));
        }

        for(int i=50; i>0; i--) {
            points.addAll(generateFor(i, 10, points.get(points.size()-1)));
        }

        for(Point2D p : points) {
            System.out.println(p);
        }

        gc.setLineWidth(5);
        gc.setStroke(Color.RED);
        gc.moveTo(10, 10);
        for(Point2D p : points) {
            gc.lineTo(p.getX(), p.getY());
        }
        gc.stroke();

    }

    private List<Point2D> generateFor(int angle, int n, Point2D start) {

        List<Point2D> points = new ArrayList<>();
        points.add(start);
        for(int i=0; i<n; i++) {
            double deltaX = Math.cos(Math.toRadians(angle));
            double deltaY = Math.sin(Math.toRadians(angle));
            Point2D newPoint = new Point2D(start.getX() + deltaX, start.getY() + deltaY);
            points.add(newPoint);
            start = newPoint;
        }

        return points;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
