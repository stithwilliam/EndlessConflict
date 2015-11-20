package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
        newGameBtn.setOnAction(this::setNewGameBtn);
        loadGameBtn.setOnAction(this::setLoadGameBtn);
        rulesBtn.setOnAction(this::setRulesBtn);
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
}
