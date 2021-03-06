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
 * Singleton master controller that is in charge of switching scenes and invoking other controllers
 */
public class MasterController {

    /**Stage being displayed**/
    private Stage stage;

    /**Different scenes in the game**/
    private Scene startScene, configScene, battleScene;

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

            root = FXMLLoader.load(getClass().getResource("/View/Config.fxml"));
            configScene = new Scene(root);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Battle.fxml"));
            root = loader.load();
            battleScene = new Scene(root);
            battleController = loader.getController();

        } catch (IOException e) {
            System.out.println("Shit's broke: " + e);
            System.out.println("\n" + Arrays.toString(e.getStackTrace()));
        }
    }

    /**Scene setters**/
    public void setStartScene() {stage.setScene(startScene);}
    public void setConfigScene() { stage.setScene(configScene);}
    public void setBattleScene() { stage.setScene(battleScene);}

    /**Scene loaders**/
    /**Loads the prebattle scene**/
    public void loadPrebattleScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/Prebattle.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Shit's broke: " + e.getCause());
        }
    }

    /**Loads the map scene**/
    public void loadMapScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/Map.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Shit's broke: " + e.getCause());
        }
    }

    public void loadRewardScene() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/View/Rewards.fxml"));
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            System.out.println("Shit's broke: " + e.getCause());
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
