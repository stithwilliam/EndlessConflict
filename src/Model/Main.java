package Model;

import Controller.MasterController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    /**Singleton of Game**/
    public static Game myGame = new Game();

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

    public static void main(String[] args) {
        launch(args);
    }
}
