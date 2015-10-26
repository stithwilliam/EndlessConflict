package Controller;

import Model.Commander;
import Model.Game;
import Model.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * Created by William on 10/26/2015.
 */
public class Config2Controller {

    @FXML
    private Button okBtn;
    @FXML
    private Button backBtn;
    @FXML
    private HBox humanBox;
    @FXML
    private HBox robotBox;
    @FXML
    private HBox mutantBox;

    private boolean selected;
    private Commander commander;

    public void initialize() {
        okBtn.setOnAction(this::setOkBtn);
        backBtn.setOnAction(this::setBackBtn);
        humanBox.setOnMouseClicked(this::boxClicked);
        robotBox.setOnMouseClicked(this::boxClicked);
        mutantBox.setOnMouseClicked(this::boxClicked);
    }

    private void boxClicked(MouseEvent e) {
        selected = true;
        HBox source = (HBox) e.getSource();
        if (source == humanBox) {
            humanBox.setStyle("-fx-border-color: black; -fx-background-color: darkorange; -fx-border-width: 2;");
            robotBox.setStyle("-fx-border-color: slateblue; -fx-background-color: royalblue; -fx-border-width: 2;");
            mutantBox.setStyle("-fx-border-color: lawngreen; -fx-background-color: chartreuse; -fx-border-width: 2;");
            commander = Commander.HUMAN;
        } else if (source == robotBox) {
            humanBox.setStyle("-fx-border-color: sandybrown; -fx-background-color: darkorange; -fx-border-width: 2;");
            robotBox.setStyle("-fx-border-color: black; -fx-background-color: royalblue; -fx-border-width: 2;");
            mutantBox.setStyle("-fx-border-color: lawngreen; -fx-background-color: chartreuse; -fx-border-width: 2;");
            commander = Commander.ROBOT;
        } else {
            humanBox.setStyle("-fx-border-color: sandybrown; -fx-background-color: darkorange; -fx-border-width: 2;");
            robotBox.setStyle("-fx-border-color: slateblue; -fx-background-color: royalblue; -fx-border-width: 2;");
            mutantBox.setStyle("-fx-border-color: black; -fx-background-color: chartreuse; -fx-border-width: 2;");
            commander = Commander.MUTANT;
        }
    }

    private void setOkBtn(ActionEvent e) {
        if (selected) {
            Game game = Main.myGame;
            Main.myGame.setCommander(commander);
            System.out.println("DIFFICULTY: " + game.getDifficulty() + "\nMODE: " + game.getMode() + "\nCOMMANDER: " + game.getCommander());
            //TODO: change scene
        }
    }

    private void setBackBtn(ActionEvent e) {
        MasterController.getInstance().setConfig1Scene();
    }
}
