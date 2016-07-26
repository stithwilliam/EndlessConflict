package Controller;

import Model.Fighter;
import Model.Game;
import Model.Main;
import Model.Placeable;
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

    @FXML
    private Label descriptionLabel;

    /**Current fighter showing**/
    private Fighter fighter;

    private boolean firstLabel;

    /**List of fighters available and in army**/
    private LinkedList<Fighter> available, toBattle;

    /**Hashmap that maps labels to their fighters**/
    private HashMap<Label, Fighter> labelFighterHashMap;

    /**Hashmap that maps fighters to their labels**/
    private HashMap<Fighter, Label> fighterLabelHashMap;

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
        descriptionLabel.setText(fighter.getDescription());
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
        fighterLabelHashMap.put(f, label);
        if (!firstLabel) {
            label.setTextFill(Color.YELLOW);
            firstLabel = true;
        }
        label.setOnMouseClicked(this::labelClicked);
        return label;
    }

    private void labelClicked(MouseEvent e) {
        Label prevLabel = fighterLabelHashMap.get(fighter);
        prevLabel.setTextFill(Color.BLACK);
        Label label = (Label)e.getSource();
        label.setTextFill(Color.YELLOW);
        fighter = labelFighterHashMap.get(label);
        showFighter();
        if (available.contains(fighter)) {
            centerBtn.setText("Add");
            int num = availableModelCount(fighter.getModel());
            availableNum.setText("x" + num);
        } else {
            centerBtn.setText("Remove");
            availableNum.setText("");
        }
    }

    private void setBackBtn(ActionEvent e) {
        MasterController.getInstance().setConfigScene();
    }

    private void setOkBtn(ActionEvent e) {
        if (underLimit()) {
            Main.myGame.setArmy(toBattle);
            Main.myGame.startTutorial();
        }
    }

    private void setCenterBtn(ActionEvent e) {
        if (available.contains(fighter)) {
            available.remove(fighter);
            availableBox.getChildren().remove(fighterLabelHashMap.get(fighter));
            if (availableModelCount(fighter.getModel()) > 0) {
                availableBox.getChildren().add(createLabel(availableFighterWithModel(fighter.getModel())));
            }
            toBattleBox.getChildren().add(fighterLabelHashMap.get(fighter));
            toBattle.add(fighter);
            centerBtn.setText("Remove");
            availableNum.setText("");
            fixLimitLabel();
        } else {
            toBattleBox.getChildren().remove(fighterLabelHashMap.get(fighter));
            toBattle.remove(fighter);
            if (availableModelCount(fighter.getModel()) > 0) {
                availableBox.getChildren().remove(fighterLabelHashMap.get(availableFighterWithModel(fighter.getModel())));
            }
            availableBox.getChildren().add(fighterLabelHashMap.get(fighter));
            available.add(fighter);
            centerBtn.setText("Add");
            availableNum.setText("x" + availableModelCount(fighter.getModel()));
            fixLimitLabel();
        }
    }

    private void fixLimitLabel() {
        int inArmy = toBattle.size();
        int armyLimit = Main.myGame.getArmyLimit();
        toBattleLimit.setText("" + inArmy + "/" + armyLimit);
        if (inArmy < armyLimit) {
            toBattleLimit.setTextFill(Color.BLACK);
        } else if (inArmy == armyLimit) {
            toBattleLimit.setTextFill(Color.GREEN);
        } else {
            toBattleLimit.setTextFill(Color.RED);
        }
    }

    public boolean underLimit() {
        if (toBattle.size() <= Main.myGame.getArmyLimit()) {
            return true;
        } else {
            return false;
        }
    }

    public int availableModelCount(Placeable model) {
        int toRet = 0;
        for (Fighter f : available) {
            if (f.getModel() == model) {
                toRet++;
            }
        }
        return toRet;
    }

    public Fighter availableFighterWithModel(Placeable model) {
        Fighter toRet = null;
        for (Fighter f : available) {
            if (f.getModel() == model) {
                toRet = f;
            }
        }
        return toRet;
    }

    public void initialize() {
        available = new LinkedList<>();
        toBattle = new LinkedList<>();
        labelFighterHashMap = new HashMap<>();
        fighterLabelHashMap = new HashMap<>();

        backBtn.setOnAction(this::setBackBtn);
        okBtn.setOnAction(this::setOkBtn);
        centerBtn.setOnAction(this::setCenterBtn);

        centerBtn.setText("Add");
        firstLabel = false;
        Game game = Main.myGame;

        for (Fighter f : game.getCollection()) {
            if (availableModelCount(f.getModel()) == 0) {
                availableBox.getChildren().add(createLabel(f));
            }
            available.add(f);
        }
        fighter = game.getCollection().get(0);

        availableNum.setText("x" + availableModelCount(fighter.getModel()));
        fixLimitLabel();
        showFighter();
    }
}

