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

    public void initialize() {
        newGameBtn.setOnAction(this::setNewGameBtn);
        loadGameBtn.setOnAction(this::setLoadGameBtn);
        rulesBtn.setOnAction(this::setRulesBtn);
    }

    private void setNewGameBtn(ActionEvent e) {
        MasterController.getInstance().setConfig1Scene();
    }

    private void setLoadGameBtn(ActionEvent e) {
        System.out.println("Not implemented yet"); //TODO
    }

    private void setRulesBtn(ActionEvent e) {
        MasterController.getInstance().setHeadquartersScene(); //TODO
    }
}
