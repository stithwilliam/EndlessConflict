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

    /**Stage being displayed**/
    private Stage stage;

    /**Different scenes in the game**/
    private Scene startScene, config1Scene, config2Scene, mapScene, headquartersScene,
            commQuartersScene, armsDealerScene, mercCampScene;

    /**The singleton MapController**/
    private MapController mapController;

    /**Creates the singleton of MasterController**/
    private static MasterController masterController = new MasterController();

    /**
     * Gets the singleton
     * @return MasterController this
     */
    public static MasterController getInstance() { return masterController; }

    /**
     * Called once to start the game
     * @param stage Stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Constructor for MasterController.
     * Loads and sets all scenes in the game.
     */
    public MasterController() {
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getResource("/View/Start.fxml"));
            startScene = new Scene(root);

            root = FXMLLoader.load(getClass().getResource("/View/Config1.fxml"));
            config1Scene = new Scene(root);

            root = FXMLLoader.load(getClass().getResource("/View/Config2.fxml"));
            config2Scene = new Scene(root);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Map.fxml"));
            root = loader.load();
            mapScene = new Scene(root);
            mapController = loader.getController();

            root = FXMLLoader.load(getClass().getResource("/View/Headquarters.fxml"));
            headquartersScene = new Scene(root);

            root = FXMLLoader.load(getClass().getResource("/View/ArmsDealer.fxml"));
            armsDealerScene = new Scene(root);

            root = FXMLLoader.load(getClass().getResource("/View/CommandersQuarters.fxml"));
            commQuartersScene = new Scene(root);

            root = FXMLLoader.load(getClass().getResource("/View/MercenaryCamp.fxml"));
            mercCampScene = new Scene(root);

        } catch (IOException e) {
            System.out.println("Shit's broke: " + e);
        }
    }

    /**Scene setters**/
    public void setStartScene() { stage.setScene(startScene);}
    public void setConfig1Scene() { stage.setScene(config1Scene);}
    public void setConfig2Scene() { stage.setScene(config2Scene);}
    public void setMapScene() { stage.setScene(mapScene);}
    public void setHeadquartersScene() { stage.setScene(headquartersScene);}
    public void setArmsDealerScene() { stage.setScene(armsDealerScene);}
    public void setCommQuartersScene() { stage.setScene(commQuartersScene);}
    public void setMercCampScene() { stage.setScene(mercCampScene);}

    /**Scene loaders**/
    public void loadBarracksScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/Barracks.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Shit's broke: " + e);
        }
    }

    /**Getters**/
    public MapController getMapController() { return mapController;}
}
