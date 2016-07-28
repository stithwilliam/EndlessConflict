package Controller;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;

import java.util.List;

/**
 * Controller for Start.fxml
 */
public class StartController {

    /**FXML Injections**/
    @FXML
    private Button newGameBtn;
    @FXML
    private Button loadGameBtn;
    @FXML
    private Button rulesBtn;
    @FXML
    private Label titleLbl;

    /**Timer for the title animation**/
    private AnimationTimer timer;

    /**Vars for the title animation**/
    private double focusDist;
    private double focusMod;
    private boolean pos;
    private List<Stop> stops;


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
        focusDist = -.6;
        focusMod = 0.005;
        pos = true;
        RadialGradient grad = (RadialGradient) titleLbl.getTextFill();
        stops = grad.getStops();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                RadialGradient gradient;
                gradient = new RadialGradient(0, focusDist, 0.48888888888888893, 0.5142857142857142, 1, true, javafx.scene.paint.CycleMethod.NO_CYCLE, stops);
                titleLbl.setTextFill(gradient);
                if (pos) {
                    focusDist += focusMod;
                } else {
                    focusDist += -focusMod;
                }
                if (Math.abs(focusDist) >= .8) {
                    pos = !pos;
                    focusMod = 0.005;
                }
                if (focusDist > 0.2) {
                    if (pos) {
                        focusMod -= 0.0005;
                    } else {
                        focusMod += 0.0005;
                    }
                }
                if (focusDist < -0.2) {
                    if (pos) {
                        focusMod += 0.0005;
                    } else {
                        focusMod -= 0.0005;
                    }
                }
            }
        };
        timer.start();
    }

    /**
     * Called when New Game is pressed
     * @param e newGameBtn
     */
    private void setNewGameBtn(ActionEvent e) {
        MasterController.getInstance().setConfigScene();
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
        System.out.println("Not implemented yet"); //TODO
    }

    /**MOUSE HOVERING**/
    private void loadGameEnter(MouseEvent e) {
        loadGameBtn.setStyle("-fx-border-color: #ff7700; -fx-background-color: cyan; -fx-border-width: 3; -fx-border-radius: 3;");
        loadGameBtn.setFont(Font.font("Britannic Bold", 22));
    }
    private void loadGameExit(MouseEvent e) {
        loadGameBtn.setStyle("-fx-border-color: black; -fx-background-color: cyan; -fx-border-width: 1; -fx-border-radius: 1;");
        loadGameBtn.setFont(Font.font("Britannic Bold", 18));
    }
    private void newGameEnter(MouseEvent e) {
        newGameBtn.setStyle("-fx-border-color: #ff7700; -fx-background-color: cyan; -fx-border-width: 3; -fx-border-radius: 3;");
        newGameBtn.setFont(Font.font("Britannic Bold", 22));
    }
    private void newGameExit(MouseEvent e) {
        newGameBtn.setStyle("-fx-border-color: black; -fx-background-color: cyan; -fx-border-width: 1; -fx-border-radius: 1;");
        newGameBtn.setFont(Font.font("Britannic Bold", 18));
    }
    private void rulesEnter(MouseEvent e) {
        rulesBtn.setStyle("-fx-border-color: #ff7700; -fx-background-color: lightgrey; -fx-border-width: 3; -fx-border-radius: 3;");
        rulesBtn.setFont(Font.font("Britannic Bold", 18));
    }
    private void rulesExit(MouseEvent e) {
        rulesBtn.setStyle("-fx-border-color: black; -fx-background-color: lightgrey; -fx-border-width: 1; -fx-border-radius: 1;");
        rulesBtn.setFont(Font.font("Britannic Bold", 18));
    }
}
