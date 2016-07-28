package Model;

import Controller.MasterController;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Run this to start game
 */
public class Main extends Application {

    /**Singleton of Game**/
    public static Game myGame = new Game();

    /**
     * Starts the game, loads title screen.
     */
    @Override
    public void start(Stage stage) throws Exception{
        MasterController controller = MasterController.getInstance();
        controller.setStage(stage);
        stage.setTitle("Endless Conflict");
        stage.setResizable(false);
        controller.setConfigScene();
        stage.show();
        controller.setStartScene();
    }

    /**This is main**/
    public static void main(String[] args) {
        launch(args);
    }
}
