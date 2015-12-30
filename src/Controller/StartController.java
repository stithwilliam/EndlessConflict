package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;

/**
 * Created by William on 10/25/2015.
 */
public class StartController {

    @FXML
    private Button newGameBtn;
    @FXML
    private Button loadGameBtn;
    @FXML
    private Button rulesBtn;


    /**Initializer**/
    public void initialize() {
        Font.loadFont(getClass().getResourceAsStream("/View/Graphics/Fonts/Haettenschweiler.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("/View/Graphics/Fonts/BritannicBold.ttf"), 14);
        newGameBtn.setOnAction(this::setNewGameBtn);
        newGameBtn.setOnMouseEntered(this::newGameEnter);
        newGameBtn.setOnMouseExited(this::newGameExit);
        loadGameBtn.setOnAction(this::setLoadGameBtn);
        loadGameBtn.setOnMouseEntered(this::loadGameEnter);
        loadGameBtn.setOnMouseExited(this::loadGameExit);
        rulesBtn.setOnAction(this::setRulesBtn);
        rulesBtn.setOnMouseEntered(this::rulesEnter);
        rulesBtn.setOnMouseExited(this::rulesExit);
    }

    /**
     * Called when New Game is pressed
     * @param e newGameBtn
     */
    private void setNewGameBtn(ActionEvent e) {
        MasterController.getInstance().setConfig1Scene();
    }

    /**
     * Called when Load Game is pressed
     * @param e loadGameBtn
     */
    private void setLoadGameBtn(ActionEvent e) {
        System.out.println("Not implemented yet"); //TODO
    }

    /**
     * Called when Rules button is pressed
     * @param e rulesBtn
     */
    private void setRulesBtn(ActionEvent e) {
        MasterController.getInstance().setHeadquartersScene(); //TODO
    }

    /**MOUSE HOVERING**/
    private void loadGameEnter(MouseEvent e) {
        loadGameBtn.setStyle("-fx-border-color: cyan; -fx-background-color: lightgreen; -fx-border-width: 3; -fx-border-radius: 3;");
        loadGameBtn.setFont(Font.font("Britannic Bold", 22));
    }
    private void loadGameExit(MouseEvent e) {
        loadGameBtn.setStyle("-fx-background-color: lightgreen;");
        loadGameBtn.setFont(Font.font("Britannic Bold", 18));
    }
    private void newGameEnter(MouseEvent e) {
        newGameBtn.setStyle("-fx-border-color: cyan; -fx-background-color: salmon; -fx-border-width: 3; -fx-border-radius: 3;");
        newGameBtn.setFont(Font.font("Britannic Bold", 22));
    }
    private void newGameExit(MouseEvent e) {
        newGameBtn.setStyle("-fx-background-color: salmon;");
        newGameBtn.setFont(Font.font("Britannic Bold", 18));
    }
    private void rulesEnter(MouseEvent e) {
        rulesBtn.setStyle("-fx-border-color: cyan; -fx-background-color: yellow; -fx-border-width: 3; -fx-border-radius: 3;");
        rulesBtn.setFont(Font.font("Britannic Bold", 22));
    }
    private void rulesExit(MouseEvent e) {
        rulesBtn.setStyle("-fx-background-color: yellow;");
        rulesBtn.setFont(Font.font("Britannic Bold", 18));
    }
}
