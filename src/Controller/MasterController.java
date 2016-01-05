package Controller;

import Model.Fighter;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

/**
 * Created by William on 10/26/2015.
 */
public class MasterController {

    /**Stage being displayed**/
    private Stage stage;

    /**Different scenes in the game**/
    private Scene startScene, config1Scene, config2Scene, battleScene, headquartersScene,
            commQuartersScene, armsDealerScene, mercCampScene;

    /**The singleton BattleController**/
    private BattleController battleController;

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

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Battle.fxml"));
            root = loader.load();
            battleScene = new Scene(root);
            battleController = loader.getController();

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
            System.out.println("\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    /**Scene setters**/
    public void setStartScene() {stage.setScene(startScene);}
    public void setConfig1Scene() { stage.setScene(config1Scene);}
    public void setConfig2Scene() { stage.setScene(config2Scene);}
    public void setBattleScene() { stage.setScene(battleScene);}
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

    public void loadBattleScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/Battle.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Shit's broke: " + e);

        }
    }

    public void loadEquipScene(Fighter f) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/EquipmentChanger.fxml"));
            Parent root = loader.load();
            EquipController controller = loader.getController();
            stage.setScene(new Scene(root));
            controller.setFighter(f);
        } catch (IOException e) {
            System.out.println("Shit's broke: " + e);
        }
    }

    /**Getters**/
    public BattleController getBattleController() { return battleController;}

    /**
     * Shows a popup with text and an OK button that closes the popup
     * @param text String of text to be shown
     */
    public void textPopup(String text) {

        //Create VBox and Popup
        Popup popup = new Popup();
        VBox vBox = new VBox();
        vBox.setPrefWidth(300);
        vBox.setPrefHeight(200);
        vBox.setStyle("-fx-background-color: SKYBLUE; -fx-border-width: 1; -fx-border-color: BLACK;");
        vBox.setAlignment(Pos.CENTER);

        //Create and add the text label
        Label label = new Label(text);
        label.setFont(Font.font("Britannic Bold", 18));
        label.setWrapText(true);
        label.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().add(label);

        //Create and add the OK button
        Button button = new Button();
        button.setText("OK");
        button.setFont(Font.font("Britannic Bold", 18));
        button.setOnAction(event -> popup.hide());
        button.setPadding(new Insets(20, 20, 20, 20));
        vBox.getChildren().add(button);

        //Show popup
        popup.getContent().add(vBox);
        popup.show(stage);
    }
}
