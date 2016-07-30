package sample;

import fury.yuri.render.CanvasRenderer;
import fury.yuri.render.IRenderer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

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

        draw(gc);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void draw(GraphicsContext gc) {

        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(20);
        gc.strokeRect(0, 0, 978, 654);

        gc.setStroke(Color.RED);

        gc.setLineWidth(5);
        gc.bezierCurveTo(50, 70, 200, 200, 350, 200);
        gc.stroke();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
