package Model;

import Controller.MasterController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static Game myGame;

    @Override
    public void start(Stage stage) throws Exception{
        myGame = new Game();
        MasterController controller = MasterController.getInstance();
        controller.setStage(stage);
        controller.setStartScene();
        stage.setTitle("Endless Conflict");
        stage.setResizable(false);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
