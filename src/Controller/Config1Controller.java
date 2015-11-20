package Controller;

import Model.Difficulty;
import Model.Game;
import Model.Main;
import Model.Mode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

/**
 * Created by William on 10/26/2015.
 */
public class Config1Controller {

    @FXML
    private Button backBtn;
    @FXML
    private Button okBtn;
    @FXML
    private RadioButton easyRadio;
    @FXML
    private RadioButton mediumRadio;
    @FXML
    private RadioButton hardRadio;
    @FXML
    private RadioButton softcoreRadio;
    @FXML
    private RadioButton hardcoreRadio;

    /**Groups for the toggles on screen**/
    private ToggleGroup difficultyToggle, modeToggle;

    /**Initializer**/
    public void initialize() {
        okBtn.setOnAction(this::setOkBtn);
        backBtn.setOnAction(this::setBackBtn);
        difficultyToggle = new ToggleGroup();
        modeToggle = new ToggleGroup();
        easyRadio.setToggleGroup(difficultyToggle);
        mediumRadio.setToggleGroup(difficultyToggle);
        hardRadio.setToggleGroup(difficultyToggle);
        softcoreRadio.setToggleGroup(modeToggle);
        hardcoreRadio.setToggleGroup(modeToggle);
    }

    /**
     * Called when OK button is pressed
     * @param e okBtn
     */
    private void setOkBtn(ActionEvent e) {
        Game game = Main.myGame;
        boolean toggles[] = {false, false};
        for (Toggle t : difficultyToggle.getToggles()) {
            if (t.isSelected()) {
                toggles[0] = true;
                if (easyRadio.isSelected()) {
                    game.setDifficulty(Difficulty.EASY);
                } else if (mediumRadio.isSelected()) {
                    game.setDifficulty(Difficulty.MEDIUM);
                } else {
                    game.setDifficulty(Difficulty.HARD);
                }
            }
        } for (Toggle t: modeToggle.getToggles()) {
            if (t.isSelected()) {
                toggles[1] = true;
                if (softcoreRadio.isSelected()) {
                    game.setMode(Mode.SOFTCORE);
                } else {
                    game.setMode(Mode.HARDCORE);
                }
            }
        } if (toggles[0] && toggles[1]) {
            MasterController.getInstance().setConfig2Scene();
        } else {
            System.out.println("You dumb fuck");
            //TODO make this better
        }
    }

    /**
     * Called when back button is pressed
     * @param e backBtn
     */
    private void setBackBtn(ActionEvent e) {
        MasterController.getInstance().setStartScene();
    }
}
