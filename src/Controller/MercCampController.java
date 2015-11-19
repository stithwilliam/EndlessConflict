package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Created by William on 11/19/2015.
 */
public class MercCampController {

    @FXML
    Button backBtn;

    private void setBackBtn(ActionEvent e) {
        MasterController.getInstance().setHeadquartersScene();
    }

    public void initialize() {
        backBtn.setOnAction(this::setBackBtn);
    }
}
