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
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by William on 11/19/2015.
 */
public class BarracksController {

    @FXML
    private Button prevBtn;

    @FXML
    private Button toggleBtn;

    @FXML
    private Button changeBtn;

    @FXML
    private VBox barracksBox;

    @FXML
    private Label label10;

    @FXML
    private Label label21;

    @FXML
    private Label label20;

    @FXML
    private Label label31;

    @FXML
    private Label heroLabel;

    @FXML
    private Label label30;

    @FXML
    private Label label41;

    @FXML
    private Label label40;

    @FXML
    private Label label01;

    @FXML
    private Button addBtn;

    @FXML
    private Label label00;

    @FXML
    private Label label11;

    @FXML
    private VBox armyBox;

    @FXML
    private ImageView fighterImage;

    @FXML
    private Button removeBtn;

    @FXML
    private Button backBtn;

    @FXML
    private Button nextBtn;

    @FXML
    private Label nameLabel;

    /**Current fighter showing**/
    private Fighter fighter;

    /**If stats are showing (vs equipment showing)**/
    private boolean statsShowing;

    /**List of fighters in armyBox**/
    private LinkedList<Fighter> inArmyBox;

    /**List of fighters in barracksBox**/
    private LinkedList<Fighter> inBarracksBox;

    /**Hashmap that maps Fighters to their labels**/
    private HashMap<Fighter, Label> fighterLabelHashMap;

    private boolean firstLabel;

    /**
     * Helper function that shows the image, name, and hero designation of the current fighter
     */
    private void showFighter() {
        fighterImage.setImage(new Image(fighter.imagePath()));
        nameLabel.setText(fighter.getName());
        if (fighter.isHero()) {
            heroLabel.setText("Hero");
        } else {
            heroLabel.setText("Unit");
        }
    }

    /**
     * Helper function called when you want to show the current fighter's stats to the user
     */
    private void showStats() {
        label00.setText("HP");
        label10.setText("Attack");
        label20.setText("Defense");
        label30.setText("Movement");
        label40.setText("Range");
        label01.setText("" + fighter.getMaxHP());
        label01.setFont(Font.font("Britannic Bold", 24));
        label11.setText("" + fighter.getAtt());
        label11.setFont(Font.font("Britannic Bold", 24));
        label21.setText("" + fighter.getDef());
        label21.setFont(Font.font("Britannic Bold", 24));
        label31.setText("" + fighter.getMov());
        label31.setFont(Font.font("Britannic Bold", 24));
        label41.setText("" + fighter.getRange());
        label41.setFont(Font.font("Britannic Bold", 24));
    }

    /**
     * Helper function called when you want to show the current fighter's equipment to the user
     */
    private void showEquipment() {
        label00.setText("Weapon");
        label10.setText("Helmet");
        label20.setText("Armor");
        label30.setText("Boots");
        label40.setText("Skill");
        label01.setText("" + fighter.getWeapon());
        label01.setFont(Font.font("Britannic Bold", 18));
        label11.setText("" + fighter.getHead());
        label11.setFont(Font.font("Britannic Bold", 18));
        label21.setText("" + fighter.getArmor());
        label21.setFont(Font.font("Britannic Bold", 18));
        label31.setText("" + fighter.getFeet());
        label31.setFont(Font.font("Britannic Bold", 18));
        label41.setText("" + fighter.getSkillName());
        label41.setFont(Font.font("Britannic Bold", 18));
    }

    /**
     * Called when the toggle button is pressed
     * Switches between showing the stats and the equipment of the current fighter
     * @param e Button toggleBtn
     */
    private void setToggleBtn(ActionEvent e) {
        if (statsShowing) {
            showEquipment();
            changeBtn.setOpacity(1);
            changeBtn.setOnAction(this::setChangeBtn);
            statsShowing = false;
            toggleBtn.setText("S");
        } else {
            showStats();
            changeBtn.setOpacity(0);
            changeBtn.setOnAction(null);
            statsShowing = true;
            toggleBtn.setText("E");
        }
    }

    /**
     * Called when the Add to Army button is pressed
     * Adds the current fighter to the user's army
     * @param e Button addBtn
     */
    private void setAddBtn(ActionEvent e) {
        boolean inBox = inArmyBox.contains(fighter);
        if (!inBox) {
            Label l = createLabel(fighter);
            l.setTextFill(Color.YELLOW);
            armyBox.getChildren().add(l);
            inArmyBox.add(fighter);
            for (int i = 0; i < inBarracksBox.size(); i++) {
                if (inBarracksBox.get(i) == fighter) {
                    inBarracksBox.remove(i);
                    barracksBox.getChildren().remove(i + 1);
                }
            }
            Main.myGame.addToArmy(fighter);
            Main.myGame.removeFromBarracks(fighter);
        } else {
            System.out.println("Already in army box");
        }
    }

    /**
     * Called when the Put in Barracks buttons is pressed
     * Puts the current fighter in to the user's barracks
     * @param e Button removeBtn
     */
    private void setRemoveBtn(ActionEvent e) {
        boolean inBox = inBarracksBox.contains(fighter);
        if (!inBox) {
            Label l = createLabel(fighter);
            l.setTextFill(Color.YELLOW);
            barracksBox.getChildren().add(l);
            inBarracksBox.add(fighter);
            for (int i = 0; i < inArmyBox.size(); i++) {
                if (inArmyBox.get(i) == fighter) {
                    inArmyBox.remove(i);
                    armyBox.getChildren().remove(i + 1);
                }
            }
            Main.myGame.removeFromArmy(fighter);
            Main.myGame.addToBarracks(fighter);
        } else {
            System.out.println("Already in barracks box");
        }
    }

    /**
     * Called when the previous button is pressed
     * Shows the user the previous fighter in the army
     * @param e Button prevBtn
     */
    private void setPrevBtn(ActionEvent e) {
        Label label = fighterLabelHashMap.get(fighter);
        label.setTextFill(Color.BLACK);
        fighter = Main.myGame.prevFighter(fighter);
        Label label2 = fighterLabelHashMap.get(fighter);
        label2.setTextFill(Color.YELLOW);
        showFighter();
        if (statsShowing) {
            showStats();
        } else {
            showEquipment();
        }
    }

    /**
     * Called when the next button is pressed.
     * Shows the next fighter in the user's army
     * @param e Button nextBtn
     */
    private void setNextBtn(ActionEvent e) {
        Label label = fighterLabelHashMap.get(fighter);
        label.setTextFill(Color.BLACK);
        fighter = Main.myGame.nextFighter(fighter);
        Label label2 = fighterLabelHashMap.get(fighter);
        label2.setTextFill(Color.YELLOW);
        showFighter();
        if (statsShowing) {
            showStats();
        } else {
            showEquipment();
        }
    }

    /**
     * Called when the back button is pressed
     * Returns the user to the headquarters screen
     * @param e Button backBtn
     */
    private void setBackBtn(ActionEvent e) {
        MasterController.getInstance().setHeadquartersScene();
    }

    /**
     * Helper function to create a formatted label of the fighter's name for the army and barracks boxes
     * @param f Fighter to make the label of
     * @return Label that is formatted
     */
    private Label createLabel(Fighter f) {
        String s = f.getName();
        Label label = new Label();
        label.setText(s);
        label.setPadding(new Insets(5, 10, 5, 10));
        label.setPrefHeight(24);
        label.setPrefWidth(200);
        label.setFont(new Font("Britannic Bold", 18));
        label.setWrapText(true);
        fighterLabelHashMap.put(f, label);
        if (!firstLabel) {
            label.setTextFill(Color.YELLOW);
            firstLabel = true;
        }
        return label;
    }

    private void setChangeBtn(ActionEvent e) {
        MasterController.getInstance().loadEquipScene(fighter);
    }

    /**
     * Initialize function for BarracksController
     * Populates armyBox and barracksBox, and shows the current fighter
     */
    public void initialize() {
        System.out.println("Initialize !_!");

        nextBtn.setOnAction(this::setNextBtn);
        prevBtn.setOnAction(this::setPrevBtn);
        backBtn.setOnAction(this::setBackBtn);
        addBtn.setOnAction(this::setAddBtn);
        removeBtn.setOnAction(this::setRemoveBtn);
        toggleBtn.setOnAction(this::setToggleBtn);

        fighterLabelHashMap = new HashMap<>();
        firstLabel = false;
        Game game = Main.myGame;
        fighter = game.getArmy().get(0);
        statsShowing = true;
        showFighter();
        showStats();
        inArmyBox = new LinkedList<>();
        inBarracksBox = new LinkedList<>();
        for (int i = 0; i < game.getArmy().size(); i++) {
            Fighter f = game.getArmy().get(i);
            Label l = createLabel(f);
            armyBox.getChildren().add(l);
            inArmyBox.add(f);
        }
        for (int i = 0; i < game.getBarracks().size(); i++) {
            Fighter f = game.getBarracks().get(i);
            barracksBox.getChildren().add(createLabel(f));
            inBarracksBox.add(f);
        }
    }

    public Fighter getFighter() {return fighter;}
}
