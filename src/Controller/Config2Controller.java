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

    /**If a commander has been selected**/
    private boolean selected;

    /**The selected commander**/
    private Commander commander;

    /**Initializer**/
    public void initialize() {
        okBtn.setOnAction(this::setOkBtn);
        backBtn.setOnAction(this::setBackBtn);
        humanBox.setOnMouseClicked(this::setHumanBox);
        robotBox.setOnMouseClicked(this::setRobotBox);
        mutantBox.setOnMouseClicked(this::setMutantBox);
    }

    /**
     * Called when the human box is clicked
     * @param e humanBox
     */
    private void setHumanBox(MouseEvent e) {
        selected = true;
        humanBox.setStyle("-fx-border-color: black; -fx-background-color: darkorange; -fx-border-width: 2;");
        robotBox.setStyle("-fx-border-color: slateblue; -fx-background-color: royalblue; -fx-border-width: 2;");
        mutantBox.setStyle("-fx-border-color: lawngreen; -fx-background-color: chartreuse; -fx-border-width: 2;");
        commander = Commander.HUMAN;
    }

    /**
     * Called when the robot box is clicked
     * @param e robotBox
     */
    private void setRobotBox(MouseEvent e) {
        selected = true;
        humanBox.setStyle("-fx-border-color: sandybrown; -fx-background-color: darkorange; -fx-border-width: 2;");
        robotBox.setStyle("-fx-border-color: black; -fx-background-color: royalblue; -fx-border-width: 2;");
        mutantBox.setStyle("-fx-border-color: lawngreen; -fx-background-color: chartreuse; -fx-border-width: 2;");
        commander = Commander.ROBOT;
    }

    /**
     * Called when the mutant box is clicked
     * @param e mutantBox
     */
    private void setMutantBox(MouseEvent e) {
        selected = true;
        humanBox.setStyle("-fx-border-color: sandybrown; -fx-background-color: darkorange; -fx-border-width: 2;");
        robotBox.setStyle("-fx-border-color: slateblue; -fx-background-color: royalblue; -fx-border-width: 2;");
        mutantBox.setStyle("-fx-border-color: black; -fx-background-color: chartreuse; -fx-border-width: 2;");
        commander = Commander.MUTANT;
    }

    /**
     * Called when the OK button is clicked
     * @param e okBtn
     */
    private void setOkBtn(ActionEvent e) {
        if (selected) {
            Game game = Main.myGame;
            game.setCommander(commander);
            game.startGame();
        }
    }

    /**
     * Called when the back button is clicked
     * @param e backBtn
     */
    private void setBackBtn(ActionEvent e) {
        MasterController.getInstance().setConfig1Scene();
    }
}
