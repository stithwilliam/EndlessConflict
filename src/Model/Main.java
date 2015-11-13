package Model;

import Controller.MasterController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static Game myGame = new Game();

    @Override
    public void start(Stage stage) throws Exception{
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
