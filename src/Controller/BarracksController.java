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
import javafx.scene.text.Font;

import java.util.LinkedList;

/**
 * Created by William on 11/19/2015.
 */
public class BarracksController {

    /**
     * The various components in the fxml file
     */
    @FXML
    Button backBtn;
    @FXML
    Button toggleBtn;
    @FXML
    Button nextBtn;
    @FXML
    Button prevBtn;
    @FXML
    Button addBtn;
    @FXML
    Button removeBtn;
    @FXML
    ImageView fighterImage;
    @FXML
    VBox armyBox;
    @FXML
    VBox barracksBox;
    @FXML
    Label nameLabel;
    @FXML
    Label heroLabel;
    @FXML
    Label label00;
    @FXML
    Label label01;
    @FXML
    Label label10;
    @FXML
    Label label11;
    @FXML
    Label label20;
    @FXML
    Label label21;
    @FXML
    Label label30;
    @FXML
    Label label31;
    @FXML
    Label label40;
    @FXML
    Label label41;

    /**Current fighter showing**/
    private Fighter fighter;

    /**If stats are showing (vs equipment showing)**/
    private boolean statsShowing;

    /**List of fighters in armyBox**/
    private LinkedList<Fighter> inArmyBox;

    /**List of fighters in barracksBox**/
    private LinkedList<Fighter> inBarracksBox;

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
        label11.setText("" + fighter.getAtt());
        label21.setText("" + fighter.getDef());
        label31.setText("" + fighter.getMov());
        label41.setText("" + fighter.getRange());
    }

    /**
     * Helper function called when you want to show the current fighter's equipment to the user
     */
    private void showEquipment() {
        label00.setText("Weapon");
        label10.setText("Head");
        label20.setText("Armor");
        label30.setText("Feet");
        label40.setText("Skill");
        label01.setText("" + fighter.getWeapon());
        label11.setText("" + fighter.getHead());
        label21.setText("" + fighter.getArmor());
        label31.setText("" + fighter.getFeet());
        label41.setText("" + fighter.getSkillName());

    }

    /**
     * Called when the toggle button is pressed
     * Switches between showing the stats and the equipment of the current fighter
     * @param e Button toggleBtn
     */
    @FXML
    public void setToggleBtn(ActionEvent e) {
        if (statsShowing) {
            showEquipment();
            statsShowing = false;
            toggleBtn.setText("S");
        } else {
            showStats();
            statsShowing = true;
            toggleBtn.setText("E");
        }
    }

    /**
     * Called when the Add to Army button is pressed
     * Adds the current fighter to the user's army
     * @param e Button addBtn
     */
    @FXML
    public void setAddBtn(ActionEvent e) {
        boolean inBox = inArmyBox.contains(fighter);
        if (!inBox) {
            armyBox.getChildren().add(createLabel(fighter.getName()));
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
    @FXML
    public void setRemoveBtn(ActionEvent e) {
        boolean inBox = inBarracksBox.contains(fighter);
        if (!inBox) {
            barracksBox.getChildren().add(createLabel(fighter.getName()));
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
    @FXML
    public void setPrevBtn(ActionEvent e) {
        fighter = Main.myGame.prevFighter(fighter);
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
    @FXML
    public void setNextBtn(ActionEvent e) {
        fighter = Main.myGame.nextFighter(fighter);
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
    @FXML
    public void setBackBtn(ActionEvent e) {
        MasterController.getInstance().setHeadquartersScene();
    }

    /**
     * Helper function to create a formatted label for the army and barracks boxes
     * @param s String text for the label
     * @return Label that is formatted
     */
    private Label createLabel(String s) {
        Label label = new Label();
        label.setText(s);
        label.setPadding(new Insets(5, 10, 5, 10));
        label.setPrefHeight(24);
        label.setPrefWidth(200);
        label.setFont(new Font("Britannic Bold", 18));
        label.setWrapText(true);
        return label;
    }

    /**
     * Initialize function for BarracksController
     * Populates armyBox and barracksBox, and shows the current fighter
     */
    public void initialize() {
        Game game = Main.myGame;
        fighter = game.getArmy().get(0);
        statsShowing = true;
        showStats();
        inArmyBox = new LinkedList<>();
        inBarracksBox = new LinkedList<>();
        for (int i = 0; i < game.getArmy().size(); i++) {
            Fighter f = game.getArmy().get(i);
            armyBox.getChildren().add(createLabel(f.getName()));
            inArmyBox.add(f);
        }
        for (int i = 0; i < game.getBarracks().size(); i++) {
            Fighter f = game.getBarracks().get(i);
            barracksBox.getChildren().add(createLabel(f.getName()));
            inBarracksBox.add(f);
        }
    }
}
