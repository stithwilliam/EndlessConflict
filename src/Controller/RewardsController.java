package Controller;

import Model.Fighter;
import Model.Map;
import Model.Main;
import Model.Placeable;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Alex on 7/26/2016.
 */
public class RewardsController {

    @FXML
    private ImageView image;
    @FXML
    private Label victory;
    @FXML
    private Button continueBtn;

    private List<Placeable> rewardList;

    private Placeable rewardShowing;

    private boolean buttonShowing;



    public void initialize() {
        rewardList = Main.myGame.getMap().getRewardList(); //show the user the rewards they got
        continueBtn.setOnAction(this::setContinueBtn);

            ScaleTransition scaleIn = new ScaleTransition(Duration.seconds(1), victory);
            scaleIn.setFromX(0);
            scaleIn.setFromY(0);
            scaleIn.setToX(1);
            scaleIn.setToY(1);

            FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), victory);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);

        ParallelTransition scaleFadeIn = new ParallelTransition();
        scaleFadeIn.getChildren().addAll(scaleIn, fadeIn);
        scaleFadeIn.play();

        buttonShowing = false;
        rewardShowing = null;
        image.setOnMouseClicked(this::imageClicked);
    }

    public void imageClicked(MouseEvent e) {
        if (rewardShowing == null) {
            image.setImage(new Image("/View/Graphics/Tile/rewardChestOpening.gif"));

                FadeTransition nothing = new FadeTransition(Duration.seconds(1.5));
                nothing.setFromValue(1);
                nothing.setToValue(1);
                nothing.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        image.setImage(new Image("/View/Graphics/Tile/rewardChestOpen.png"));
                    }
                });

                FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), image);
                fadeOut.setFromValue(1);
                fadeOut.setToValue(0);
                fadeOut.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (rewardList.get(0) != null) {
                            image.setImage(new Image(rewardList.get(0).imagePath()));
                        }
                    }
                });

                FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), image);
                fadeIn.setFromValue(0);
                fadeIn.setToValue(1);
                fadeIn.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        rewardShowing = true;
                    }
                });
            //animate chest opening
            //show first reward
        } else {
            int index = rewardList.indexOf(rewardShowing);
            if (index < rewardList.size() - 1) {
                //show next reward
            } else {
                //do nothing
            }
        }
    }

    private void setContinueBtn(ActionEvent e) {
        //do something
    }

  //  @FXML what to put here?
 //   void db7830(ActionEvent event)



}
