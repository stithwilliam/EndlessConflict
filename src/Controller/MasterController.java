package Controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by William on 10/26/2015.
 */
public class MasterController {

    private Stage stage;
    private Scene startScene, config1Scene, config2Scene, mapScene, headquartersScene;
    private FXMLLoader loader;
    private MapController mapController;

    //create a singleton
    private static MasterController masterController = new MasterController();

    //returns the singleton within the entire scope of the project
    public static MasterController getInstance() { return masterController; }

    //called once
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public MasterController() {
        Parent root;
        try {
            root = loader.load(getClass().getResource("/View/Start.fxml"));
            startScene = new Scene(root);

            root = loader.load(getClass().getResource("/View/Config1.fxml"));
            config1Scene = new Scene(root);

            root = loader.load(getClass().getResource("/View/Config2.fxml"));
            config2Scene = new Scene(root);

            loader = new FXMLLoader(getClass().getResource("/View/Map.fxml"));
            root = (Parent) loader.load();
            mapScene = new Scene(root);
            mapController = (MapController) loader.getController();

            root = loader.load(getClass().getResource("/View/Headquarters.fxml"));
            headquartersScene = new Scene(root);

        } catch (IOException e) {
            System.out.println("Shit's broke: " + e);
        }
    }

    //Scene setters
    public void setStartScene() { stage.setScene(startScene);}
    public void setConfig1Scene() { stage.setScene(config1Scene);}
    public void setConfig2Scene() { stage.setScene(config2Scene);}
    public void setMapScene() { stage.setScene(mapScene);}
    public void setHeadquartersScene() { stage.setScene(headquartersScene);}
    //Scene loaders

    //Getters
    public MapController getMapController() { return mapController;}
}
