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
        MasterController.getInstance().setMercCampScene();
    }

    private void setArmsBtn(ActionEvent e) {
        MasterController.getInstance().setArmsDealerScene();
    }

    private void setBarrBtn(ActionEvent e) {
        MasterController.getInstance().setBarracksScene();
    }

    private void setCommBtn(ActionEvent e) {
        MasterController.getInstance().setCommQuartersScene();
    }

    public void initialize() {
        mercBtn.setOnAction(this::setMercBtn);
        armsBtn.setOnAction(this::setArmsBtn);
        barrBtn.setOnAction(this::setBarrBtn);
        commBtn.setOnAction(this::setCommBtn);
    }
}
