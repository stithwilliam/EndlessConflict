package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class EquipController {

    @FXML
    private VBox fighterBox;

    @FXML
    private Label invTypeLabel;

    @FXML
    private Label heroLabel;

    @FXML
    private Label label0;

    @FXML
    private Label label1;

    @FXML
    private Label label2;

    @FXML
    private Label label3;

    @FXML
    private Label weaponLbl;

    @FXML
    private Label helmetLbl;

    @FXML
    private Label armorLbl;

    @FXML
    private Label bootsLbl;

    @FXML
    private Label skillLbl;

    @FXML
    private Button previewBtn;

    @FXML
    private Label label4;

    @FXML
    private Label noneLabel;

    @FXML
    private Button changeBtn;

    @FXML
    private ImageView fighterImage;

    @FXML
    private Button equipBtn;

    @FXML
    private VBox inventoryBox;

    @FXML
    private Button backBtn;

    @FXML
    private GridPane gridPane;

    @FXML
    private Label nameLabel;

    private Label equipSelected;

    private Type typeSelected;

    private enum Type {
        HELMET, ARMOR, BOOTS, WEAPON, SKILL, NONE;
    }

    private void setBackBtn(ActionEvent e) {
        MasterController.getInstance().loadPrebattleScene();
    }

    private void setEquipBtn(ActionEvent e) {
        if (typeSelected != Type.NONE && equipSelected != null) {
            if (typeSelected == Type.WEAPON) {
                label0.setText(equipSelected.getText());
            } else if (typeSelected == Type.HELMET) {
                label1.setText(equipSelected.getText());
            } else if (typeSelected == Type.ARMOR) {
                label2.setText(equipSelected.getText());
            } else if (typeSelected == Type.BOOTS) {
                label3.setText(equipSelected.getText());
            } else if (typeSelected == Type.SKILL) {
                label4.setText(equipSelected.getText());
            }
        }
    }

    private void setPreviewBtn(ActionEvent e) {

    }

    private void btnEnter(MouseEvent e) {
        Button button = (Button) e.getSource();
        button.setStyle("-fx-border-color: cyan; -fx-border-width: 3; -fx-border-radius: 3;");
    }

    private void btnExit(MouseEvent e) {
        Button button = (Button) e.getSource();
        button.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-border-radius: 1;");
    }

    private void setEquipLabel(MouseEvent e) {
        if (equipSelected != null) {
            equipSelected.setTextFill(Color.BLACK);
        }
        Label label = (Label) e.getSource();
        label.setTextFill(Color.ORANGERED);
        equipSelected = label;
    }

//    private void createLabel(Equipment e) {
//        Label label = new Label(e.toString());
//        label.setFont(Font.font("Britannic Bold", 18));
//        label.setText(e.toString());
//        label.setPadding(new Insets(10, 10, 10, 10));
//        label.setOnMouseClicked(this::setEquipLabel);
//        inventoryBox.getChildren().add(label);
//
//
//    }

    private void clearInv() {
        int size = inventoryBox.getChildren().size();
        for (int i = 2; i < size; i++) {
            inventoryBox.getChildren().remove(i);
        }
        //inventoryBox.getChildren().add(noneLabel);
    }

//    @FXML
//    public void setWeapon(MouseEvent e) {
//        unselectType();
//        typeSelected = Type.WEAPON;
//        weaponLbl.setTextFill(Color.ORANGERED);
//        label0.setTextFill(Color.ORANGERED);
//        invTypeLabel.setText("Weapons");
//        clearInv();
//        ArrayList<Equipment> inv = Main.myGame.getEquipmentInv();
//        for (Equipment equipment : inv) {
//            if (equipment instanceof Weapon) {
//                createLabel(equipment);
//            }
//        }
//    }

//    @FXML
//    public void setHelmet(MouseEvent e) {
//        unselectType();
//        typeSelected = Type.HELMET;
//        helmetLbl.setTextFill(Color.ORANGERED);
//        label1.setTextFill(Color.ORANGERED);
//        invTypeLabel.setText("Helmets");
//        clearInv();
//        ArrayList<Equipment> inv = Main.myGame.getEquipmentInv();
//        for (Equipment equipment : inv) {
//            if (equipment instanceof Helmet) {
//                createLabel(equipment);
//            }
//        }
//    }

//    @FXML
//    public void setArmor(MouseEvent e) {
//        unselectType();
//        typeSelected = Type.ARMOR;
//        armorLbl.setTextFill(Color.ORANGERED);
//        label2.setTextFill(Color.ORANGERED);
//        invTypeLabel.setText("Armor");
//        clearInv();
//        ArrayList<Equipment> inv = Main.myGame.getEquipmentInv();
//        for (Equipment equipment : inv) {
//            if (equipment instanceof Armor) {
//                createLabel(equipment);
//            }
//        }
//    }

//    @FXML
//    public void setBoots(MouseEvent e) {
//        unselectType();
//        typeSelected = Type.BOOTS;
//        bootsLbl.setTextFill(Color.ORANGERED);
//        label3.setTextFill(Color.ORANGERED);
//        invTypeLabel.setText("Boots");
//        clearInv();
//        ArrayList<Equipment> inv = Main.myGame.getEquipmentInv();
//        for (Equipment equipment : inv) {
//            if (equipment instanceof Boots) {
//                createLabel(equipment);
//            }
//        }
//    }

//    @FXML
//    public void setSkill(MouseEvent e) {
//        unselectType();
//        typeSelected = Type.SKILL;
//        skillLbl.setTextFill(Color.ORANGERED);
//        label4.setTextFill(Color.ORANGERED);
//        invTypeLabel.setText("Skills");
//        clearInv();
//        ArrayList<Equipment> inv = Main.myGame.getEquipmentInv();
//        //TODO
//    }

    private void unselectType() {
        if (typeSelected == Type.WEAPON) {
            weaponLbl.setTextFill(Color.BLACK);
            label0.setTextFill(Color.BLACK);
        } else if (typeSelected == Type.HELMET) {
            helmetLbl.setTextFill(Color.BLACK);
            label1.setTextFill(Color.BLACK);
        } else if (typeSelected == Type.ARMOR) {
            armorLbl.setTextFill(Color.BLACK);
            label2.setTextFill(Color.BLACK);
        } else if (typeSelected == Type.BOOTS) {
            bootsLbl.setTextFill(Color.BLACK);
            label3.setTextFill(Color.BLACK);
        } else if (typeSelected == Type.SKILL) {
            skillLbl.setTextFill(Color.BLACK);
            label4.setTextFill(Color.BLACK);
        }
    }

    /**
     * Initialization function
     */
    public void initialize() {
        backBtn.setOnAction(this::setBackBtn);
        backBtn.setOnMouseEntered(this::btnEnter);
        backBtn.setOnMouseExited(this::btnExit);
        equipBtn.setOnAction(this::setEquipBtn);
        equipBtn.setOnMouseEntered(this::btnEnter);
        equipBtn.setOnMouseExited(this::btnExit);
        previewBtn.setOnAction(this::setPreviewBtn);
        previewBtn.setOnMouseEntered(this::btnEnter);
        previewBtn.setOnMouseExited(this::btnExit);

        noneLabel.setOnMouseClicked(this::setEquipLabel);

    }

    public void setFighter(Fighter f) {
        nameLabel.setText(f.getName());
        if (f.isCommander()) {
            heroLabel.setText("Commander");
        } else {
            heroLabel.setText("Unit");
        }
        fighterImage.setImage(new Image(f.imagePath()));
    }

}
