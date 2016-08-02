package fury.yuri.engine;

import fury.yuri.model.GameModel;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

/**
 * Created by yuri on 29/07/16.
 */
public class GameEngine {

    private GameModel gameModel;
    private AnimationTimer loop;
    private boolean running;

    public GameEngine(GameModel gameModel) {
        this.gameModel = gameModel;
        loop = new AnimationTimer() {
            //~60fps
            @Override
            public void handle(long l) {
                gameModel.update();
            }
        };
    }

    public void start() {
        loop.start();
        running = true;
    }

    public void stop() {
        loop.stop();
        running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public EventHandler<KeyEvent> getOnKeyPressedEventHandler() {
        return e -> {
            gameModel.addPressedKey(e.getCode().toString());
        };
    }

    public EventHandler<KeyEvent> getOnKeyReleasedEventHandler() {
        return e -> {
            gameModel.removePressedKey(e.getCode().toString());
        };
    }
}
