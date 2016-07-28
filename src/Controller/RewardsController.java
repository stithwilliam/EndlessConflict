package Controller;

import Model.Fighter;
import Model.Map;
import Model.Main;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Alex on 7/26/2016.
 */
public class RewardsController {

    @FXML
    private Button contBtn;

    @FXML
    private ImageView chestImage;

    @FXML
    private VBox rewardsBox;

    public void initialize() {
        Main.myGame.getMap().getRewardList(); //show the user the rewards they got

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), chestImage);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        //fadeOut.play();

        ScaleTransition ....

        SequentialTransition sequence = new SequentialTransition();
        sequence.getChildren().add(fadeOut);
        sequence.getChildren().add();


    }



  //  @FXML what to put here?
 //   void db7830(ActionEvent event)



}
