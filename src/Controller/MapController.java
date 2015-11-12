package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by William on 10/26/2015.
 */
public class MapController {

    //FXML injections
    @FXML
    private GridPane gridPane;
    @FXML
    private Rectangle upRect;
    @FXML
    private Rectangle downRect;
    @FXML
    private Rectangle rightRect;
    @FXML
    private Rectangle leftRect;
    @FXML
    private Button moveBtn;
    @FXML
    private Button atkBtn;
    @FXML
    private Button skillBtn;
    @FXML
    private Button prevBtn;
    @FXML
    private Button nextBtn;
    @FXML
    private VBox terminal;

    //Global vars
    private Map map;
    private Game game;
    private Fighter fighter;
    private HashMap<StackPane, MapTile> paneToTile;

    //GENERAL
    public void constructMap(Map map) {
        this.map = map;
        this.game = Main.myGame;
        int width = map.getWidth();
        int height = map.getHeight();
        paneToTile = new HashMap<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                MapTile tile = map.getMapTile(i, j);
                ImageView imgView = new ImageView(tile.imagePath());
                StackPane stackPane = new StackPane();
                stackPane.getChildren().add(imgView);
                tile.setStackPane(stackPane);
                gridPane.add(stackPane, i, j);
                paneToTile.put(stackPane, tile);
            }
        }
        gridPane.setVgap(0);
        gridPane.setHgap(0);
        fighter = game.getFighter();
        populateMap();
    }

    public void populateMap() {
        populateMap(map.getFighters());
    }

    public void populateMap(ArrayList<Fighter> fighters) {
        for (Fighter f : fighters) {
            int x = f.getxPos();
            int y = f.getyPos();
            ImageView image = new ImageView(f.imagePath());
            StackPane pane = map.getMapTile(x, y).getStackPane();
            pane.getChildren().add(image);
            pane.setOnMouseClicked(this::fighterStats);
        }
        StackPane pane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
        pane.getChildren().add(new ImageView("/View/Graphics/Tile/fighterSelect.gif"));
    }

    public void removeFighter(Fighter f) {
        int x = f.getxPos();
        int y = f.getyPos();
        MapTile mapTile = map.getMapTile(x, y);
        mapTile.getStackPane().getChildren().remove(1);
        mapTile.getStackPane().setOnMouseClicked(null);
    }

    private void putInFocus(int x, int y) {
        double gridX = gridPane.getTranslateX();
        double gridY = gridPane.getTranslateY();
        while ((-1*gridX/64) > x) {
            gridPane.setTranslateX(gridX + 64);
            gridX = gridPane.getTranslateX();
        }
        while (((-1*gridX/64) + 9) < x) {
            gridPane.setTranslateX(gridX - 64);
            gridX = gridPane.getTranslateX();
        }
        while ((-1*gridY/64) > y) {
            gridPane.setTranslateY(gridY + 64);
            gridY = gridPane.getTranslateY();
        }
        while (((-1*gridY/64) + 5) < y) {
            gridPane.setTranslateY(gridY - 64);
            gridY = gridPane.getTranslateY();
        }
    }

    //MOVE
    private void showMoves(MouseEvent e) {
        boolean[][] valid = map.getValidMoves(fighter);
        for (int i = 0; i < valid.length; i++) {
            for (int j = 0; j < valid[0].length; j++) {
                if (valid[i][j]) {
                    ImageView move = new ImageView(Graphic.MOVESLCT.imagePath());
                    StackPane pane = map.getMapTile(j, i).getStackPane();
                    pane.getChildren().add(move);
                }
            }
        }
        moveBtn.setOnMouseExited(this::noShowMoves);
    }

    private void noShowMoves(MouseEvent e) {
        boolean[][] valid = map.getValidMoves(fighter);
        for (int i = 0; i < valid.length; i++) {
            for (int j = 0; j < valid[0].length; j++) {
                if (valid[i][j]) {
                    StackPane pane = map.getMapTile(j, i).getStackPane();
                    int idx = pane.getChildren().size() - 1;
                    pane.getChildren().remove(idx);
                }
            }
        }
    }

    private void setMoveBtn(ActionEvent e) {
        buttonsToCancelMove();
        putInFocus(fighter.getxPos(), fighter.getyPos());
        boolean[][] valid = map.getValidMoves(fighter);
        for (int i = 0; i < valid[0].length; i++) {
            for (int j = 0; j < valid.length; j++) {
                if (valid[j][i]) {
                    StackPane pane = map.getMapTile(i, j).getStackPane();
                    pane.setOnMouseClicked(this::moveHere);
                }
            }
        }
    }

    private void moveHere(MouseEvent e) {
        //removing valid tile image
        boolean[][] valid = map.getValidMoves(fighter);
        for (int i = 0; i < valid[0].length; i++) {
            for (int j = 0; j < valid.length; j++) {
                if (valid[j][i]) {
                    StackPane pane = map.getMapTile(i, j).getStackPane();
                    pane.getChildren().remove(1);
                    pane.setOnMouseClicked(null);
                }
            }
        }
        //remove old fighter
        StackPane oldPane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
        oldPane.getChildren().remove(2);
        oldPane.getChildren().remove(1);
        oldPane.setOnMouseClicked(null);
        //move fighter
        StackPane pane = (StackPane) e.getSource();
        MapTile tile = paneToTile.get(pane);
        int x = tile.getxPos();
        int y = tile.getyPos();
        fighter.setxPos(x);
        fighter.setyPos(y);
        pane.getChildren().add(new ImageView(fighter.imagePath()));
        pane.getChildren().add(new ImageView(Graphic.FIGHTERSLCT.imagePath()));
        pane.setOnMouseClicked(this::fighterStats);
        //print to terminal
        putOnTerminal(fighter.getName() + " moved to " + tile.getName() + " at " + (x + 1) + ", " + (y + 1));
        buttonsToDefault();
    }

    private void buttonsToCancelMove() {
        moveBtn.setOnAction(this::cancelMove);
        moveBtn.setOnMouseEntered(null);
        moveBtn.setOnMouseExited(null);
        atkBtn.setOnAction(this::cancelMove);
        atkBtn.setOnMouseEntered(null);
        atkBtn.setOnMouseExited(null);
        skillBtn.setOnAction(this::cancelMove);
        nextBtn.setOnAction(this::cancelMove);
        prevBtn.setOnAction(this::cancelMove);
    }

    private void cancelMove(ActionEvent e) {
        boolean[][] valid = map.getValidMoves(fighter);
        for (int i = 0; i < valid[0].length; i++) {
            for (int j = 0; j < valid.length; j++) {
                if (valid[j][i]) {
                    StackPane pane = map.getMapTile(i, j).getStackPane();
                    pane.getChildren().remove(1);
                    pane.setOnMouseClicked(null);
                }
            }
        }
        putOnTerminal(fighter.getName() + "'s move was canceled");
        buttonsToDefault();
    }

    //ATTACK
    private void showAttacks(MouseEvent e) {
        boolean[][] valid = map.getValidAttacks(fighter);
        for (int i = 0; i < valid.length; i++) {
            for (int j = 0; j < valid[0].length; j++) {
                if (valid[i][j]) {
                    ImageView move = new ImageView(Graphic.ATTACKSLCT.imagePath());
                    StackPane pane = map.getMapTile(j, i).getStackPane();
                    pane.getChildren().add(move);
                }
            }
        }
        atkBtn.setOnMouseExited(this::noShowAttacks);
    }

    private void noShowAttacks(MouseEvent e) {
        boolean[][] valid = map.getValidAttacks(fighter);
        for (int i = 0; i < valid.length; i++) {
            for (int j = 0; j < valid[0].length; j++) {
                if (valid[i][j]) {
                    StackPane pane = map.getMapTile(j, i).getStackPane();
                    int idx = pane.getChildren().size() - 1;
                    pane.getChildren().remove(idx);
                }
            }
        }
    }

    private void setAtkBtn(ActionEvent e) {
        putInFocus(fighter.getxPos(), fighter.getyPos());
        noShowAttacks(null);
        boolean[][] valid = map.getAttackable(fighter);
        for (int i = 0; i < valid[0].length; i++) {
            for (int j = 0; j < valid.length; j++) {
                if (valid[j][i]) {
                    ImageView atk = new ImageView(Graphic.ATTACKSLCT.imagePath());
                    StackPane pane = map.getMapTile(i, j).getStackPane();
                    pane.getChildren().add(atk);
                    pane.setOnMouseClicked(this::attackHere);
                }
            }
        }
        boolean hasAttack = false;
        for (int i = 0; i < valid[0].length; i++) {
            for (int j = 0; j < valid.length; j++) {
                if (valid[j][i]) { hasAttack = true;}
            }
        }
        if (!hasAttack) {
            atkBtn.setOnMouseExited(null);
            putOnTerminal(fighter.getName() + " has no valid targets");
        } else {
            buttonsToCancelAttack();
        }
    }

    private void attackHere(MouseEvent e) {
        //removing valid tile image
        boolean[][] valid = map.getAttackable(fighter);
        for (int i = 0; i < valid[0].length; i++) {
            for (int j = 0; j < valid.length; j++) {
                if (valid[j][i]) {
                    StackPane pane = map.getMapTile(i, j).getStackPane();
                    int idx = pane.getChildren().size() - 1;
                    pane.getChildren().remove(idx);
                    pane.setOnMouseClicked(this::fighterStats);
                }
            }
        }
        StackPane pane = (StackPane) e.getSource();
        MapTile tile = paneToTile.get(pane);
        Fighter attacked = map.getFighter(tile.getxPos(), tile.getyPos());
        putOnTerminal(Main.myGame.attackFighter(attacked));
        buttonsToDefault();
    }

    private void buttonsToCancelAttack() {
        moveBtn.setOnAction(this::cancelMove);
        moveBtn.setOnMouseEntered(null);
        moveBtn.setOnMouseExited(null);
        atkBtn.setOnAction(this::cancelMove);
        atkBtn.setOnMouseEntered(null);
        atkBtn.setOnMouseExited(null);
        skillBtn.setOnAction(this::cancelAttack);
        nextBtn.setOnAction(this::cancelAttack);
        prevBtn.setOnAction(this::cancelAttack);
    }

    private void cancelAttack(ActionEvent e) {
        boolean[][] valid = map.getValidAttacks(fighter);
        for (int i = 0; i < valid[0].length; i++) {
            for (int j = 0; j < valid.length; j++) {
                if (valid[j][i]) {
                    StackPane pane = map.getMapTile(i, j).getStackPane();
                    int idx = pane.getChildren().size() - 1;
                    pane.getChildren().remove(idx);
                    pane.setOnMouseClicked(null);
                }
            }
        }
        putOnTerminal(fighter.getName() + "'s attack was canceled");
        buttonsToDefault();
    }

    private void setSkillBtn(ActionEvent e) {
        putInFocus(fighter.getxPos(), fighter.getyPos());
        if (fighter.getModel() instanceof Hero) {
            ((Hero) fighter.getModel()).showSkill();
        } else {
            putOnTerminal("I have no skills ");
        }
    }

    //SKILLS
    //LizardKing's skill
    public void showLizardJump() {
        buttonsToCancelLizardJump();
        putInFocus(fighter.getxPos(), fighter.getyPos());
        boolean[][] valid = map.getTilesWithin(fighter.getxPos(), fighter.getyPos(), fighter.getMov() + 2);
        for (int i = 0; i < valid.length; i++) {
            for (int j = 0; j < valid[i].length; j++) {
                if (valid[i][j] && map.getMapTile(j, i).isMoveable() && !map.getMapTile(j, i).hasFighter()) {
                    StackPane stackPane = map.getMapTile(j, i).getStackPane();
                    stackPane.getChildren().add(new ImageView(Graphic.MOVESLCT.imagePath()));
                    stackPane.setOnMouseClicked(this::doLizardJump);
                }
            }
        }
        putOnTerminal("Choose a location to jump to");
    }

    public void doLizardJump(MouseEvent e) {
        boolean[][] valid = map.getTilesWithin(fighter.getxPos(), fighter.getyPos(), fighter.getMov() + 2);
        for (int i = 0; i < valid.length; i++) {
            for (int j = 0; j < valid[i].length; j++) {
                if (valid[i][j] && map.getMapTile(j, i).isMoveable() && !map.getMapTile(j, i).hasFighter()) {
                    StackPane stackPane = map.getMapTile(j, i).getStackPane();
                    stackPane.getChildren().remove(1);
                    stackPane.setOnMouseClicked(null);
                }
            }
        }
        //remove old fighter
        StackPane oldPane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
        oldPane.getChildren().remove(2);
        oldPane.getChildren().remove(1);
        oldPane.setOnMouseClicked(null);
        //move fighter
        StackPane pane = (StackPane) e.getSource();
        MapTile tile = paneToTile.get(pane);
        int x = tile.getxPos();
        int y = tile.getyPos();
        fighter.setxPos(x);
        fighter.setyPos(y);
        pane.getChildren().add(new ImageView(fighter.imagePath()));
        pane.getChildren().add(new ImageView(Graphic.FIGHTERSLCT.imagePath()));
        pane.setOnMouseClicked(this::fighterStats);
        //print to terminal
        putOnTerminal(fighter.getName() + " jumped to " + tile.getName() + " at " + (x + 1) + ", " + (y + 1));
        buttonsToDefault();
    }

    public void buttonsToCancelLizardJump() {
        moveBtn.setOnAction(this::cancelLizardJump);
        moveBtn.setOnMouseEntered(null);
        moveBtn.setOnMouseExited(null);
        atkBtn.setOnAction(this::cancelLizardJump);
        atkBtn.setOnMouseEntered(null);
        atkBtn.setOnMouseExited(null);
        skillBtn.setOnAction(this::cancelLizardJump);
        nextBtn.setOnAction(this::cancelLizardJump);
        prevBtn.setOnAction(this::cancelLizardJump);
    }

    public void cancelLizardJump(ActionEvent e) {
        boolean[][] valid = map.getTilesWithin(fighter.getxPos(), fighter.getyPos(), fighter.getMov() + 2);
        for (int i = 0; i < valid.length; i++) {
            for (int j = 0; j < valid[i].length; j++) {
                if (valid[i][j] && map.getMapTile(j, i).isMoveable() && !map.getMapTile(j, i).hasFighter()) {
                    StackPane pane = map.getMapTile(j, i).getStackPane();
                    int idx = pane.getChildren().size() - 1;
                    pane.getChildren().remove(idx);
                    pane.setOnMouseClicked(null);
                }
            }
        }
        putOnTerminal(fighter.getName() + "'s jump was cancelled");
        buttonsToDefault();
    }

    //modelX's skill
    public void showTractorBeam() {
        putOnTerminal("Choose an enemy to suspend");
    }

    //Chaos's skill
    public void showGrenade() {
        buttonsToCancelGrenade();
        boolean[][] valid = map.getValidAttacks(fighter);
        for (int i = 0; i < valid.length; i++) {
            for (int j = 0; j < valid[i].length; j++) {
                if (valid[i][j]) {
                    StackPane pane = map.getMapTile(j, i).getStackPane();
                    pane.setOnMouseEntered(this::mouseOverGrenade);
                }
            }
        }
        putOnTerminal("Choose a location to throw a grenade");
    }

    public void mouseOverGrenade(MouseEvent e) {
        StackPane pane = (StackPane) e.getSource();
        pane.setOnMouseClicked(this::doGrenade);
        pane.setOnMouseExited(this::exitMouseOverGrenade);
        pane.getChildren().add(new ImageView(Graphic.ATTACKSLCT.imagePath()));
        MapTile tile = paneToTile.get(pane);
        for (MapTile t : map.getAdjacent(tile)) {
            t.getStackPane().getChildren().add(new ImageView(Graphic.ATTACKSLCT.imagePath()));
        }
        for (MapTile t : map.getDiagAttacks(tile)) {
            t.getStackPane().getChildren().add(new ImageView(Graphic.ATTACKSLCT.imagePath()));
        }
    }

    public void exitMouseOverGrenade(MouseEvent e) {
        StackPane pane = (StackPane) e.getSource();
        MapTile tile = paneToTile.get(pane);
        pane.setOnMouseClicked(null);
        int idx = pane.getChildren().size() - 1;
        pane.getChildren().remove(idx);
        for (MapTile t : map.getAdjacent(tile)) {
            StackPane p = t.getStackPane();
            int i = p.getChildren().size() - 1;
            p.getChildren().remove(i);
        }
        for (MapTile t : map.getDiagAttacks(tile)) {
            StackPane p = t.getStackPane();
            int i = p.getChildren().size() - 1;
            p.getChildren().remove(i);
        }
    }

    public void doGrenade(MouseEvent e) {
        //remove mouseOvers
        boolean[][] valid = map.getValidAttacks(fighter);
        for (int i = 0; i < valid.length; i++) {
            for (int j = 0; j < valid[i].length; j++) {
                if (valid[i][j]) {
                    StackPane pane = map.getMapTile(j, i).getStackPane();
                    pane.setOnMouseEntered(null);
                    pane.setOnMouseExited(null);
                }
            }
        }
        //grenade logic
        boolean hit = false;
        ArrayList<MapTile> tiles = new ArrayList<>();
        StackPane pane = (StackPane) e.getSource();
        MapTile tile = paneToTile.get(pane);
        tiles.addAll(map.getAttacks(tile));
        tiles.addAll(map.getDiagAttacks(tile));
        String s = fighter.getName() + " lobbed a grenade! ";
        for (MapTile t : tiles) {
            if (t.getFighter() != null && t.getFighter().isEnemy()) {
                hit = true;
                s += (game.attackFighter(t.getFighter()));
                if (t.getFighter().getHp() > 0) {
                    s += ".";
                }
                s += " ";
            }
        }
        if (!hit) {
            putOnTerminal(s + fighter.getName() + "'s grenade didn't hit anyone!");
        } else {
            putOnTerminal(s);
        }
        exitMouseOverGrenade(e);
        buttonsToDefault();
    }

    public void buttonsToCancelGrenade() {
        moveBtn.setOnAction(this::cancelGrenade);
        moveBtn.setOnMouseEntered(null);
        moveBtn.setOnMouseExited(null);
        atkBtn.setOnAction(this::cancelGrenade);
        atkBtn.setOnMouseEntered(null);
        atkBtn.setOnMouseExited(null);
        skillBtn.setOnAction(this::cancelGrenade);
        nextBtn.setOnAction(this::cancelGrenade);
        prevBtn.setOnAction(this::cancelGrenade);
    }

    public void cancelGrenade(ActionEvent e) {
        boolean[][] valid = map.getValidAttacks(fighter);
        for (int i = 0; i < valid.length; i++) {
            for (int j = 0; j < valid[i].length; j++) {
                if (valid[i][j]) {
                    StackPane pane = map.getMapTile(j, i).getStackPane();
                    pane.setOnMouseEntered(null);
                    pane.setOnMouseExited(null);
                }
            }
        }
        putOnTerminal(fighter.getName() + "'s skill was cancelled");
        buttonsToDefault();
    }

    private void setNextBtn(ActionEvent e) {
        StackPane oldPane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
        oldPane.getChildren().remove(2);
        fighter = game.nextFighter();
        StackPane newPane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
        newPane.getChildren().add(new ImageView("/View/Graphics/Tile/fighterSelect.gif"));
        putInFocus(fighter.getxPos(), fighter.getyPos());
        putOnTerminal("Next fighter is " + fighter.getName());
    }

    private void setPrevBtn(ActionEvent e) {
        StackPane oldPane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
        oldPane.getChildren().remove(2);
        fighter = game.prevFighter();
        StackPane newPane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
        newPane.getChildren().add(new ImageView(Graphic.FIGHTERSLCT.imagePath()));
        putInFocus(fighter.getxPos(), fighter.getyPos());
        putOnTerminal("Previous fighter is " + fighter.getName());
    }

    private void buttonsToDefault() {
        moveBtn.setOnAction(this::setMoveBtn);
        moveBtn.setOnMouseEntered(this::showMoves);
        moveBtn.setOnMouseExited(null);
        atkBtn.setOnAction(this::setAtkBtn);
        atkBtn.setOnMouseEntered(this::showAttacks);
        atkBtn.setOnMouseExited(null);
        skillBtn.setOnAction(this::setSkillBtn);
        nextBtn.setOnAction(this::setNextBtn);
        prevBtn.setOnAction(this::setPrevBtn);
    }

    private void fighterStats(MouseEvent e) {
        StackPane pane = (StackPane) e.getSource();
        MapTile tile = paneToTile.get(pane);
        int x = tile.getxPos();
        int y = tile.getyPos();
        Fighter f = map.getFighter(x, y);
        putOnTerminal(f.getStats());
    }

    private void putOnTerminal(String s) {
        putOnTerminal(s, true);
    }

    //puts string on terminal
    private void putOnTerminal(String s, boolean newLine) {
        if (s.length() > 38) {
            int cut = 38;
            while (s.charAt(cut) != ' ') {
                cut--;
            }
            String s1 = s.substring(0, cut);
            String s2 = s.substring(cut);
            if (newLine) {
                putOnTerminal(s1, true);
            } else {
                putOnTerminal(s1, false);
            }
            putOnTerminal(s2, false);
        } else {
            Label l;
            if (newLine) {
                l = new Label(s + " >>");
            } else {
                l = new Label(s + "      ");
            }
            l.setWrapText(true);
            l.setTextFill(Paint.valueOf("lime"));
            l.setFont(Font.font("System Regular", 14));
            terminal.getChildren().add(l);
            while (terminal.getChildren().size() > 6) {
                terminal.getChildren().remove(0);
            }
        }
    }

    //used to navigate the map
    private void setRectClicked(MouseEvent e) {
        double y = gridPane.getTranslateY();
        double x = gridPane.getTranslateX();
        if (e.getSource() == downRect) {
            if (y > -map.getHeight() * 64 + 64*6) {
                gridPane.setTranslateY(y - 64);
            }
        } else if (e.getSource() == upRect) {
            if (y < 0) {
                gridPane.setTranslateY(y + 64);
            }
        } else if (e.getSource() == leftRect) {
            if (x < 0) {
                gridPane.setTranslateX(x + 64);
            }
        } else if (e.getSource() == rightRect){
            if (x > -map.getWidth() * 64 + 64*10) {
                gridPane.setTranslateX(x - 64);
            }
        }
    }

    //initializes the map buttons
    public void initialize() {
        upRect.setOnMouseClicked(this::setRectClicked);
        downRect.setOnMouseClicked(this::setRectClicked);
        rightRect.setOnMouseClicked(this::setRectClicked);
        leftRect.setOnMouseClicked(this::setRectClicked);
        moveBtn.setOnAction(this::setMoveBtn);
        moveBtn.setOnMouseEntered(this::showMoves);
        atkBtn.setOnAction(this::setAtkBtn);
        atkBtn.setOnMouseEntered(this::showAttacks);
        skillBtn.setOnAction(this::setSkillBtn);
        nextBtn.setOnAction(this::setNextBtn);
        prevBtn.setOnAction(this::setPrevBtn);
    }


}
