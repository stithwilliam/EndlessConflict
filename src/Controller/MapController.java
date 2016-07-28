package Controller;

import Model.Game;
import Model.Main;
import Model.Race;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.HashMap;

public class MapController {

    @FXML
    private Button level5;

    @FXML
    private Button level4;

    @FXML
    private Pane panelPane;

    @FXML
    private Button xBtn;

    @FXML
    private Button goBtn;

    @FXML
    private Button level1;

    @FXML
    private Button level3;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Button level2;

    @FXML
    private Label rewardLabel;

    private boolean panelShowing;

    private enum level {
        NONE, ONE, TWO, THREE, FOUR, FIVE
    }

    private level levelSelected;

    private HashMap<Button, level> buttonToLevel;
    private HashMap<level, Button> levelToButton;
    private HashMap<level, Integer> levelToInt;

    public void initialize() {
        fixLevelButtons();
        level1.setOnAction(this::levelHandler);
        level2.setOnAction(this::levelHandler);
        level3.setOnAction(this::levelHandler);
        level4.setOnAction(this::levelHandler);
        level5.setOnAction(this::levelHandler);
        level1.setOnMouseEntered(this::levelEnter);
        level2.setOnMouseEntered(this::levelEnter);
        level3.setOnMouseEntered(this::levelEnter);
        level4.setOnMouseEntered(this::levelEnter);
        level5.setOnMouseEntered(this::levelEnter);
        level1.setOnMouseExited(this::levelExit);
        level2.setOnMouseExited(this::levelExit);
        level3.setOnMouseExited(this::levelExit);
        level4.setOnMouseExited(this::levelExit);
        level5.setOnMouseExited(this::levelExit);
        goBtn.setOnAction(this::setGoBtn);
        xBtn.setOnAction(this::setXBtn);

        buttonToLevel = new HashMap<>();
        buttonToLevel.put(level1, level.ONE);
        buttonToLevel.put(level2, level.TWO);
        buttonToLevel.put(level3, level.THREE);
        buttonToLevel.put(level4, level.FOUR);
        buttonToLevel.put(level5, level.FIVE);
        levelToButton = new HashMap<>();
        levelToButton.put(level.ONE, level1);
        levelToButton.put(level.TWO, level2);
        levelToButton.put(level.THREE, level3);
        levelToButton.put(level.FOUR, level4);
        levelToButton.put(level.FIVE, level5);
        levelToInt = new HashMap<>();
        levelToInt.put(level.ONE, 1);
        levelToInt.put(level.TWO, 2);
        levelToInt.put(level.THREE, 3);
        levelToInt.put(level.FOUR, 4);
        levelToInt.put(level.FIVE, 5);

        levelSelected = level.NONE;
        panelShowing = false;
        panelPane.setOpacity(0);
        goBtn.setOpacity(0);
        xBtn.setOpacity(0);
    }

    private void setGoBtn(ActionEvent e) {
        if (levelSelected != level.NONE) {
            Main.myGame.setLevelSelected(levelToInt.get(levelSelected));
            MasterController.getInstance().loadPrebattleScene();
        }
    }

    private void setXBtn(ActionEvent e) {
            FadeTransition paneFade = new FadeTransition(Duration.seconds(.75), panelPane);
            paneFade.setFromValue(1);
            paneFade.setToValue(0);

            FadeTransition goFade = new FadeTransition(Duration.seconds(.75), goBtn);
            goFade.setFromValue(1);
            goFade.setToValue(0);

            FadeTransition xFade = new FadeTransition(Duration.seconds(.75), xBtn);
            xFade.setFromValue(1);
            xFade.setToValue(0);

        ParallelTransition panelFade = new ParallelTransition();
        panelFade.getChildren().addAll(paneFade, goFade, xFade);
        panelFade.play();
        panelShowing = false;
    }

    private void levelEnter(MouseEvent e) {
        Button source = (Button) e.getSource();
        if (buttonToLevel.get(source) != levelSelected) {
            source.setStyle("-fx-border-color: cyan; -fx-background-color: dimgrey; -fx-border-width: 3; -fx-border-radius: 3;");
        }
    }

    private void levelExit(MouseEvent e) {
        Button source = (Button) e.getSource();
        if (buttonToLevel.get(source) != levelSelected) {
            source.setStyle("-fx-background-color: dimgrey; -fx-border-color: dimgrey;-fx-border-width: 3; -fx-border-radius: 3;");
        }
    }

    private void levelHandler(ActionEvent e) {
        if (!panelShowing) {

                FadeTransition paneFade = new FadeTransition(Duration.seconds(.75), panelPane);
                paneFade.setFromValue(0);
                paneFade.setToValue(1);

                FadeTransition goFade = new FadeTransition(Duration.seconds(.75), goBtn);
                goFade.setFromValue(0);
                goFade.setToValue(1);

                FadeTransition xFade = new FadeTransition(Duration.seconds(.75), xBtn);
                xFade.setFromValue(0);
                xFade.setToValue(1);

            ParallelTransition panelFade = new ParallelTransition();
            panelFade.getChildren().addAll(paneFade, goFade, xFade);
            panelFade.play();
            panelShowing = true;
        }
        if (levelSelected != level.NONE) {
            Button prev = levelToButton.get(levelSelected);
            prev.setStyle("-fx-background-color: dimgrey; -fx-border-color: dimgrey;-fx-border-width: 3; -fx-border-radius: 3;");
        }
        Button source = (Button) e.getSource();
        source.setStyle("-fx-border-color: yellow; -fx-background-color: dimgrey; -fx-border-width: 3; -fx-border-radius: 3;");
        Game game = Main.myGame;
        Race race = game.getRace();

        if (source == level1) {
            String unit1 = race.getWeakRace().getUnit().getName();
            String unit2 = race.getStrongRace().getUnit().getName();
            descriptionLabel.setText("LEVELONE: You have a few " + unit1 + "'s and " +
            unit2 + "'s as hostages. Might as well use them for training.");
            levelSelected = level.ONE;
            if (game.getLevelComplete(1)) {
                rewardLabel.setText("REWARD: Happiness");
            } else {
                rewardLabel.setText("REWARD: 1x " + unit1 + ". 1x " + unit2 + ".");
            }
        }
        if (source == level2) {
            levelSelected = level.TWO;
            if (game.getLevelComplete(1)) {
                descriptionLabel.setText("Level 2");
                rewardLabel.setText("Reward 2");
            } else {
                descriptionLabel.setText("You have not unlocked this level yet!");
                rewardLabel.setText("");
            }
        }
        if (source == level3) {
            levelSelected = level.THREE;
            if (game.getLevelComplete(2)) {
                descriptionLabel.setText("Level 3");
                rewardLabel.setText("Reward 3");
            } else {
                descriptionLabel.setText("You have not unlocked this level yet!");
                rewardLabel.setText("");
            }
        }
        if (source == level4) {
            levelSelected = level.FOUR;
            if (game.getLevelComplete(3)) {
                descriptionLabel.setText("Level 4");
                rewardLabel.setText("Reward 4");
            } else {
                descriptionLabel.setText("You have not unlocked this level yet!");
                rewardLabel.setText("");
            }
        }
        if (source == level5) {
            levelSelected = level.FIVE;
            if (game.getLevelComplete(4)) {
                descriptionLabel.setText("Level 5");
                rewardLabel.setText("Reward 5");
            } else {
                descriptionLabel.setText("You have not unlocked this level yet!");
                rewardLabel.setText("");
            }
        }
    }

    private void fixLevelButtons() {
        Game game = Main.myGame;
        level1.setTextFill(Color.AQUA);
        level1.setStyle("-fx-background-color: dimgrey; -fx-border-color: dimgrey;-fx-border-width: 3; -fx-border-radius: 3;");
        level2.setStyle("-fx-background-color: dimgrey; -fx-border-color: dimgrey;-fx-border-width: 3; -fx-border-radius: 3;");
        level3.setStyle("-fx-background-color: dimgrey; -fx-border-color: dimgrey;-fx-border-width: 3; -fx-border-radius: 3;");
        level4.setStyle("-fx-background-color: dimgrey; -fx-border-color: dimgrey;-fx-border-width: 3; -fx-border-radius: 3;");
        level5.setStyle("-fx-background-color: dimgrey; -fx-border-color: dimgrey;-fx-border-width: 3; -fx-border-radius: 3;");
        if (game.getLevelComplete(1)) {
            level2.setTextFill(Color.AQUA);
        } else {
            level2.setTextFill(Color.TOMATO);
        }
        if (game.getLevelComplete(2)) {
            level3.setTextFill(Color.AQUA);
        } else {
            level3.setTextFill(Color.TOMATO);
        }
        if (game.getLevelComplete(3)) {
            level4.setTextFill(Color.AQUA);
        } else {
            level4.setTextFill(Color.TOMATO);
        }
        if (game.getLevelComplete(4)) {
            level5.setTextFill(Color.AQUA);
        } else {
            level5.setTextFill(Color.TOMATO);
        }
    }

}

