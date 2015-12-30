package Controller;

import Model.Difficulty;
import Model.Game;
import Model.Main;
import Model.Mode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.scene.Node;

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
        Font.loadFont(getClass().getResourceAsStream("/View/Graphics/Fonts/BritannicBold.ttf"), 14);

        okBtn.setOnAction(this::setOkBtn);
        okBtn.setOnMouseEntered(this::okEnter);
        okBtn.setOnMouseExited(this::okExit);

        backBtn.setOnAction(this::setBackBtn);
        backBtn.setOnMouseEntered(this::backEnter);
        backBtn.setOnMouseExited(this::backExit);

        difficultyToggle = new ToggleGroup();
        modeToggle = new ToggleGroup();

        easyRadio.setToggleGroup(difficultyToggle);
        easyRadio.setOnMouseEntered(this::easyEnter);
        easyRadio.setOnMouseExited(this::easyExit);

        mediumRadio.setToggleGroup(difficultyToggle);
        mediumRadio.setOnMouseEntered(this::mediumEnter);
        mediumRadio.setOnMouseExited(this::mediumEnter);

        hardRadio.setToggleGroup(difficultyToggle);
        hardRadio.setOnMouseEntered(this::hardEnter);
        hardRadio.setOnMouseExited(this::hardExit);

        softcoreRadio.setToggleGroup(modeToggle);
        softcoreRadio.setOnMouseEntered(this::softcoreEnter);
        softcoreRadio.setOnMouseExited(this::softcoreExit);

        hardcoreRadio.setToggleGroup(modeToggle);
        hardcoreRadio.setOnMouseEntered(this::hardcoreEnter);
        hardcoreRadio.setOnMouseExited(this::hardcoreExit);
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

    /**Mouse Hovering**/

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

    private void easyEnter(MouseEvent e) {
        easyRadio.setFont(Font.font("Britannic Bold", 22));
    }
    private void easyExit(MouseEvent e) {
        easyRadio.setFont(Font.font("Britannic Bold", 18));
    }
    private void mediumEnter(MouseEvent e) {
        mediumRadio.setFont(Font.font("Britannic Bold", 22));
    }
    private void mediumExit(MouseEvent e) {
        mediumRadio.setFont(Font.font("Britannic Bold", 18));
    }
    private void hardEnter(MouseEvent e) {
        hardRadio.setFont(Font.font("Britannic Bold", 22));
    }
    private void hardExit(MouseEvent e) {
        hardRadio.setFont(Font.font("Britannic Bold", 18));
    }

    private void softcoreEnter(MouseEvent e) {
        softcoreRadio.setFont(Font.font("Britannic Bold", 22));
    }
    private void softcoreExit(MouseEvent e) {
        softcoreRadio.setFont(Font.font("Britannic Bold", 18));
    }
    private void hardcoreEnter(MouseEvent e) {
        hardcoreRadio.setFont(Font.font("Britannic Bold", 22));
    }
    private void hardcoreExit(MouseEvent e) {
        hardcoreRadio.setFont(Font.font("Britannic Bold", 18));
    }
}
