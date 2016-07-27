package Controller;

import Model.Game;
import Model.Main;
import Model.Race;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class MapController {

    @FXML
    private Button level5;

    @FXML
    private Button level4;

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
    }

    private void setGoBtn(ActionEvent e) {
        MasterController.getInstance().loadPrebattleScene();
    }

    private void levelEnter(MouseEvent e) {
        Button source = (Button) e.getSource();
        source.setStyle("-fx-border-color: cyan; -fx-background-color: dimgrey; -fx-border-width: 3; -fx-border-radius: 3;");
    }

    private void levelExit(MouseEvent e) {
        Button source = (Button) e.getSource();
        source.setStyle("-fx-background-color: dimgrey;");
    }

    private void levelHandler(ActionEvent e) {
        Button source = (Button) e.getSource();
        source.setStyle("-fx-border-color: yellow; -fx-background-color: dimgrey; -fx-border-width: 3; -fx-border-radius: 3;");
        Game game = Main.myGame;
        Race race = game.getRace();

        if (source == level1) {
            String unit1 = race.getWeakRace().getUnit().getName();
            String unit2 = race.getStrongRace().getUnit().getName();
            descriptionLabel.setText("TUTORIAL: You have a few " + unit1 + "'s and " +
            unit2 + "'s as hostages. Might as well use them for training.");
            if (game.getLevelComplete(1)) {
                rewardLabel.setText("REWARD: Happiness");
            } else {
                rewardLabel.setText("REWARD: 1x " + unit1 + ". 1x " + unit2 + ".");
            }
        }
        if (source == level2) {
            if (game.getLevelComplete(1)) {
                descriptionLabel.setText("Level 2");
                rewardLabel.setText("Reward 2");
            } else {
                descriptionLabel.setText("You have not unlocked this level yet!");
                rewardLabel.setText("");
            }
        }
        if (source == level3) {
            if (game.getLevelComplete(2)) {
                descriptionLabel.setText("Level 3");
                rewardLabel.setText("Reward 3");
            } else {
                descriptionLabel.setText("You have not unlocked this level yet!");
                rewardLabel.setText("");
            }
        }
        if (source == level4) {
            if (game.getLevelComplete(3)) {
                descriptionLabel.setText("Level 4");
                rewardLabel.setText("Reward 4");
            } else {
                descriptionLabel.setText("You have not unlocked this level yet!");
                rewardLabel.setText("");
            }
        }
        if (source == level5) {
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

