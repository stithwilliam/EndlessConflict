package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

/**
 * Controller for Config.fxml
 */
public class ConfigController {

    /**FXML Injections**/
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
    private Race race;

    /**Initializer**/
    public void initialize() {
        Font.loadFont(getClass().getResourceAsStream("/View/Graphics/Fonts/BritannicBold.ttf"), 14);

        okBtn.setOnAction(this::setOkBtn);
        okBtn.setOnMouseEntered(this::okEnter);
        okBtn.setOnMouseExited(this::okExit);

        backBtn.setOnAction(this::setBackBtn);
        backBtn.setOnMouseEntered(this::backEnter);
        backBtn.setOnMouseExited(this::backExit);

        humanBox.setOnMouseClicked(this::setHumanBox);
        robotBox.setOnMouseClicked(this::setRobotBox);
        mutantBox.setOnMouseClicked(this::setMutantBox);

        boxesToDefault();
    }

    /**
     * Sets the boxes to their default effect.
     */
    private void boxesToDefault() {
        humanBox.setOnMouseEntered(this::humanEnter);
        humanBox.setOnMouseExited(this::humanExit);
        robotBox.setOnMouseEntered(this::robotEnter);
        robotBox.setOnMouseExited(this::robotExit);
        mutantBox.setOnMouseEntered(this::mutantEnter);
        mutantBox.setOnMouseExited(this::mutantExit);
    }

    /**
     * Called when the human box is clicked
     * @param e humanBox
     */
    private void setHumanBox(MouseEvent e) {
        selected = true;
        humanBox.setStyle("-fx-border-color: yellow; -fx-background-color: darkorange; -fx-border-width: 4; ");
        robotBox.setStyle("-fx-border-color: royalblue; -fx-background-color: royalblue; -fx-border-width: 4;");
        mutantBox.setStyle("-fx-border-color: chartreuse; -fx-background-color: chartreuse; -fx-border-width: 4; ");
        race = Race.HUMAN;

        boxesToDefault();
        humanBox.setOnMouseEntered(null);
        humanBox.setOnMouseExited(null);
    }

    /**
     * Called when the robot box is clicked
     * @param e robotBox
     */
    private void setRobotBox(MouseEvent e) {
        selected = true;
        humanBox.setStyle("-fx-border-color: darkorange; -fx-background-color: darkorange; -fx-border-width: 4;");
        robotBox.setStyle("-fx-border-color: yellow; -fx-background-color: royalblue; -fx-border-width: 4;");
        mutantBox.setStyle("-fx-border-color: chartreuse; -fx-background-color: chartreuse; -fx-border-width: 4;");
        race = Race.ROBOT;

        boxesToDefault();
        robotBox.setOnMouseEntered(null);
        robotBox.setOnMouseExited(null);
    }

    /**
     * Called when the mutant box is clicked
     * @param e mutantBox
     */
    private void setMutantBox(MouseEvent e) {
        selected = true;
        humanBox.setStyle("-fx-border-color: darkorange; -fx-background-color: darkorange; -fx-border-width: 4; ");
        robotBox.setStyle("-fx-border-color: royalblue; -fx-background-color: royalblue; -fx-border-width: 4;");
        mutantBox.setStyle("-fx-border-color: yellow; -fx-background-color: chartreuse; -fx-border-width: 4;");
        race = Race.MUTANT;

        boxesToDefault();
        mutantBox.setOnMouseEntered(null);
        mutantBox.setOnMouseExited(null);
    }

    /**
     * Called when the OK button is clicked
     * @param e okBtn
     */
    private void setOkBtn(ActionEvent e) {
        if (selected) {
            Game game = Main.myGame;
            game.setRace(race);
            game.endConfig();
        }
    }

    /**
     * Called when the back button is clicked
     * @param e backBtn
     */
    private void setBackBtn(ActionEvent e) {
        MasterController.getInstance().setStartScene();
    }


    /**Mouse hovering**/
    private void okEnter(MouseEvent e) {
        okBtn.setStyle("-fx-border-color: cyan; -fx-background-color: dimgrey; -fx-border-width: 3; -fx-border-radius: 3;");
        okBtn.setFont(Font.font("Britannic Bold", 22));
    }
    private void okExit(MouseEvent e) {
        okBtn.setStyle("-fx-background-color: dimgrey");
        okBtn.setFont(Font.font("Britannic Bold", 18));
    }
    private void backEnter(MouseEvent e) {
        backBtn.setStyle("-fx-border-color: cyan; -fx-background-color: dimgrey; -fx-border-width: 3; -fx-border-radius: 3;");
        backBtn.setFont(Font.font("Britannic Bold", 22));
    }
    private void backExit(MouseEvent e) {
        backBtn.setStyle("-fx-background-color: dimgrey");
        backBtn.setFont(Font.font("Britannic Bold", 18));
    }

    private void humanEnter(MouseEvent e) {
        humanBox.setStyle("-fx-border-color: cyan; -fx-background-color: darkorange; -fx-border-width: 4;");
    }
    private void humanExit(MouseEvent e) {
        humanBox.setStyle("-fx-border-color: darkorange; -fx-background-color: darkorange; -fx-border-width: 4;");
    }

    private void robotEnter(MouseEvent e) {
        robotBox.setStyle("-fx-border-color: cyan; -fx-background-color: royalblue; -fx-border-width: 4;");
    }
    private void robotExit(MouseEvent e) {
        robotBox.setStyle("-fx-border-color: royalblue; -fx-background-color: royalblue; -fx-border-width: 4;");
    }

    private void mutantEnter(MouseEvent e) {
        mutantBox.setStyle("-fx-border-color: cyan; -fx-background-color: chartreuse; -fx-border-width: 4;");
    }
    private void mutantExit(MouseEvent e) {
        mutantBox.setStyle("-fx-border-color: chartreuse; -fx-background-color: chartreuse; -fx-border-width: 4;");
    }
}
