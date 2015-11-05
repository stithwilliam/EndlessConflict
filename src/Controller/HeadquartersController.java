package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Created by William on 11/4/2015.
 */
public class HeadquartersController {

    @FXML
    private Button mercBtn;

    @FXML
    private Button armsBtn;

    @FXML
    private Button barrBtn;

    @FXML
    private Button commBtn;

    private void setMercBtn(ActionEvent e) {

    }

    private void setArmsBtn(ActionEvent e) {

    }

    private void setBarrBtn(ActionEvent e) {

    }

    private void setCommBtn(ActionEvent e) {

    }

    public void initialize() {
        mercBtn.setOnAction(this::setMercBtn);
        armsBtn.setOnAction(this::setArmsBtn);
        barrBtn.setOnAction(this::setBarrBtn);
        commBtn.setOnAction(this::setCommBtn);
    }
}
