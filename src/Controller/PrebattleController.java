package Controller;

import Model.Fighter;
import Model.Game;
import Model.Main;
import javafx.event.ActionEvent;
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

import java.util.HashMap;
import java.util.LinkedList;

public class PrebattleController {

    @FXML
    private Label starsLabel;

    @FXML
    private Button toBattleDown;

    @FXML
    private Button toBattleUp;

    @FXML
    private Label availableNum;

    @FXML
    private Label rangeLabel;

    @FXML
    private Button okBtn;

    @FXML
    private VBox toBattleBox;

    @FXML
    private Button availableUp;

    @FXML
    private Label hpLabel;

    @FXML
    private Label movementLabel;

    @FXML
    private ImageView fighterImage;

    @FXML
    private Label attackLabel;

    @FXML
    private VBox availableBox;

    @FXML
    private Label unitLabel;

    @FXML
    private Button availableDown;

    @FXML
    private Button centerBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Label toBattleLimit;

    @FXML
    private Label nameLabel;

    /**Current fighter showing**/
    private Fighter fighter;

    private boolean firstLabel;

    /**List of fighters available and in army**/
    private LinkedList<Fighter> available, toBattle;

    /**Hashmap that maps Fighters to their labels**/
    private HashMap<Label, Fighter> labelFighterHashMap;

    private void showFighter() {
        nameLabel.setText(fighter.getName());
        fighterImage.setImage(new Image(fighter.imagePath()));
        if (fighter.isCommander()) {
            unitLabel.setText("Commander");
            starsLabel.setText("***");
        } else {
            unitLabel.setText("Unit");
            starsLabel.setText("*");
        }
        attackLabel.setText("" + fighter.getAtt());
        rangeLabel.setText("" + fighter.getRange());
        movementLabel.setText("" + fighter.getMov());
        hpLabel.setText("" + fighter.getMaxHP());

    }

    private Label createLabel(Fighter f) {
        String s = f.getName();
        Label label = new Label();
        label.setText(s);
        label.setPadding(new Insets(5, 10, 5, 10));
        label.setPrefHeight(24);
        label.setPrefWidth(200);
        label.setFont(new Font("Britannic Bold", 18));
        label.setWrapText(true);
        labelFighterHashMap.put(label, f);
        if (!firstLabel) {
            label.setTextFill(Color.YELLOW);
            firstLabel = true;
        }
        label.setOnMouseClicked(this::labelClicked);
        return label;
    }

    private void labelClicked(MouseEvent e) {
        Label label = (Label)e.getSource();
        label.setTextFill(Color.YELLOW);
        fighter = labelFighterHashMap.get(label);
        showFighter();
    }

    private void setBackBtn(ActionEvent e) {
        MasterController.getInstance().setConfigScene();
    }

    private void setOkBtn(ActionEvent e) {
        
    }

    private void setCenterBtn(ActionEvent e) {

    }

    public void initialize() {
        backBtn.setOnAction(this::setBackBtn);
        okBtn.setOnAction(this::setOkBtn);
        centerBtn.setOnAction(this::setCenterBtn);
        firstLabel = false;
        Game game = Main.myGame;
        for (Fighter f : game.getCollection()) {
            availableBox.getChildren().add(createLabel(f));
        }
        fighter = game.getCollection().get(0);
        showFighter();
    }
}

