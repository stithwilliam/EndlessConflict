package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
    private Button endTurnBtn;
    @FXML
    private VBox terminal;

    //Global vars
    private Map map;
    private Game game; // for convenience, game will never change
    private HashMap<StackPane, MapTile> paneToTile;
    private MapTile tractorBeam;

    //GENERAL

    /**
     * Builds the screen based on map
     * @param map
     */
    public void constructMap(Map map) {
        this.map = map;
        this.game = Main.myGame;
        int width = map.getWidth();
        int height = map.getHeight();
        paneToTile = new HashMap<>();
        tractorBeam = null;
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
        populateMap();
    }

    /**
     * Default method. Populates the screen with the fighters from map.
     */
    private void populateMap() {
        populateMap(map.getFighters());
    }

    /**
     * Overloaded method. Populates the screen with the fighters
     * @param fighters
     */
    private void populateMap(ArrayList<Fighter> fighters) {
        for (Fighter f : fighters) {
            int x = f.getxPos();
            int y = f.getyPos();
            ImageView image = new ImageView(f.imagePath());
            StackPane pane = map.getMapTile(x, y).getStackPane();
            pane.getChildren().add(image);
            pane.setOnMouseClicked(this::fighterStats);
        }
        Fighter fighter = map.getFighter();
        StackPane pane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
        pane.getChildren().add(new ImageView("/View/Graphics/Tile/fighterSelect.gif"));
    }

    /**
     * Removes fighter f from the screen
     * @param f
     */
    public void removeFighter(Fighter f) {
        map.removeFighter(f);
        int x = f.getxPos();
        int y = f.getyPos();
        MapTile mapTile = map.getMapTile(x, y);
        mapTile.getStackPane().getChildren().remove(1);
        mapTile.getStackPane().setOnMouseClicked(null);
    }

    /**
     * Puts the x and y coordinates in view of the user
     * @param x
     * @param y
     */
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

    /**
     * Called when the user mouseOvers the move button.
     * Shows the valid spots to move of the current fighter
     * @param e moveBtn
     */
    private void showMoves(MouseEvent e) {
        Fighter fighter = map.getFighter();
        if (!fighter.hasMoved()) {
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
    }

    /**
     * Called when the player exits the move button.
     * Removes the display of possible moves.
     * @param e moveBtn
     */
    private void noShowMoves(MouseEvent e) {
        Fighter fighter = map.getFighter();
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

    /**
     * Default state for the move button when pressed.
     * Shows the available moves for the current fighter on the screen.
     * Sets the valid tiles to moveHere().
     * @param e moveBtn
     */
    private void setMoveBtn(ActionEvent e) {
        Fighter fighter = map.getFighter();
        putInFocus(fighter.getxPos(), fighter.getyPos());
        if (!fighter.hasMoved()) {
            buttonsToCancelMove();
            boolean[][] valid = map.getValidMoves(fighter);
            for (int i = 0; i < valid[0].length; i++) {
                for (int j = 0; j < valid.length; j++) {
                    if (valid[j][i]) {
                        StackPane pane = map.getMapTile(i, j).getStackPane();
                        pane.setOnMouseClicked(this::moveHere);
                    }
                }
            }
        } else {
            putOnTerminal(fighter.getName() + " has already moved");
        }
    }

    /**
     * Called when the user clicks on a valid tile.
     * Move the fighter to the selected location
     * @param e MapTile
     */
    private void moveHere(MouseEvent e) {
        //removing valid tile image
        Fighter fighter = map.getFighter();
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
        MapTile oldTile =  map.getMapTile(fighter.getxPos(), fighter.getyPos());
        StackPane oldPane = oldTile.getStackPane();
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
        fighter.setHasMoved(true);
        pane.getChildren().add(new ImageView(fighter.imagePath()));
        pane.getChildren().add(new ImageView(Graphic.FIGHTERSLCT.imagePath()));
        pane.setOnMouseClicked(this::fighterStats);
        //print to terminal
        putOnTerminal(fighter.getName() + " moved to " + tile.getName() + " at " + (x + 1) + ", " + (y + 1));
        buttonsToDefault();
    }

    /**
     * Called when showMoves() is called.
     * Sets all buttons to cancel the pending moves.
     */
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

    /**
     * Called when the user presses another button after clicking move.
     * Cancels the pending move.
     * @param e
     */
    private void cancelMove(ActionEvent e) {
        Fighter fighter = map.getFighter();
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

    /**
     * Called when the user mouseOvers the attack button.
     * Shows the valid spots to attack of the current fighter
     * @param e atkBtn
     */
    private void showAttacks(MouseEvent e) {
        Fighter fighter = map.getFighter();
        if (!fighter.hasAttacked()) {
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
    }

    /**
     * Called when the player exits the attack button.
     * Removes the display of possible attacks.
     * @param e atkBtn
     */
    private void noShowAttacks(MouseEvent e) {
        Fighter fighter = map.getFighter();
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

    /**
     * Default state for the attack button when pressed.
     * Shows the available attacks for the current fighter on the screen, unless there are none.
     * Sets the valid tiles to attackHere().
     * @param e atkBtn
     */
    private void setAtkBtn(ActionEvent e) {
        Fighter fighter = map.getFighter();
        putInFocus(fighter.getxPos(), fighter.getyPos());
        if (!fighter.hasAttacked()) {
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
                    if (valid[j][i]) {
                        hasAttack = true;
                    }
                }
            }
            if (!hasAttack) {
                atkBtn.setOnMouseExited(null);
                putOnTerminal(fighter.getName() + " has no valid targets");
            } else {
                buttonsToCancelAttack();
            }
        } else {
            putOnTerminal(fighter.getName() + " has already attacked");
        }
    }

    /**
     * Called when the user clicks on a valid tile.
     * Attacks the fighter at the selected location
     * @param e MapTile
     */
    private void attackHere(MouseEvent e) {
        Fighter fighter = map.getFighter();
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
        fighter.setHasAttacked(true);
        StackPane pane = (StackPane) e.getSource();
        MapTile tile = paneToTile.get(pane);
        Fighter defender = map.getFighter(tile.getxPos(), tile.getyPos());
        putOnTerminal(Main.myGame.attackFighter(fighter, defender));
        buttonsToDefault();
    }

    /**
     * Called when showAttacks() is called.
     * Sets all buttons to cancel the pending attack.
     */
    private void buttonsToCancelAttack() {
        moveBtn.setOnAction(this::cancelAttack);
        moveBtn.setOnMouseEntered(null);
        moveBtn.setOnMouseExited(null);
        atkBtn.setOnAction(this::cancelAttack);
        atkBtn.setOnMouseEntered(null);
        atkBtn.setOnMouseExited(null);
        skillBtn.setOnAction(this::cancelAttack);
        nextBtn.setOnAction(this::cancelAttack);
        prevBtn.setOnAction(this::cancelAttack);
    }

    /**
     * Called when the user presses another button after clicking attack.
     * Cancels the pending attack.
     * @param e
     */
    private void cancelAttack(ActionEvent e) {
        Fighter fighter = map.getFighter();
        boolean[][] valid = map.getAttackable(fighter);
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

    /**
     * Default state for the attack button when pressed.
     * Shows the available attacks for the current fighter on the screen, unless there are none.
     * Sets the valid tiles to attackHere().
     * @param e atkBtn
     */
    private void setSkillBtn(ActionEvent e) {
        Fighter fighter = map.getFighter();
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
        Fighter fighter = map.getFighter();
        putInFocus(fighter.getxPos(), fighter.getyPos());
        if (!fighter.hasMoved()) {
            buttonsToCancelLizardJump();
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
        } else {
            putOnTerminal(fighter.getName() + " has used his move already");
        }
    }

    public void doLizardJump(MouseEvent e) {
        Fighter fighter = map.getFighter();
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
        MapTile oldTile = map.getMapTile(fighter.getxPos(), fighter.getyPos());
        StackPane oldPane = oldTile.getStackPane();
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
        fighter.setHasMoved(true);
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
        Fighter fighter = map.getFighter();
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
        Fighter fighter = map.getFighter();
        putInFocus(fighter.getxPos(), fighter.getyPos());
        if (!fighter.hasAttacked()) {
            boolean inRange = false;
            boolean[][] valid = map.getAttackable(fighter);
            for (int i = 0; i < valid.length; i++) {
                for (int j = 0; j < valid[i].length; j++) {
                    if (valid[i][j]) {
                        inRange = true;
                        StackPane pane = map.getMapTile(j, i).getStackPane();
                        pane.getChildren().add(new ImageView(Graphic.ATTACKSLCT.imagePath()));
                        pane.setOnMouseClicked(this::doTractorBeam);
                    }
                }
            }
            if (inRange) {
                buttonsToCancelTractorBeam();
                putOnTerminal("Choose an enemy to suspend");
            } else {
                putOnTerminal("No enemies are in range");
            }
        } else {
            putOnTerminal(fighter.getName() + " has already used his attack");
        }
    }

    public void doTractorBeam(MouseEvent e) {
        Fighter fighter = map.getFighter();
        boolean[][] valid = map.getAttackable(fighter);
        for (int i = 0; i < valid.length; i++) {
            for (int j = 0; j < valid[i].length; j++) {
                if (valid[i][j]) {
                    StackPane pane = map.getMapTile(j, i).getStackPane();
                    int idx = pane.getChildren().size() - 1;
                    pane.getChildren().remove(idx);
                    pane.setOnMouseClicked(this::fighterStats);
                }
            }
        }
        fighter.setHasAttacked(true);
        StackPane pane = (StackPane) e.getSource();
        pane.getChildren().add(new ImageView(Graphic.TRACTORBEAM.imagePath()));
        buttonsToDefault();
        MapTile tile = paneToTile.get(pane);
        Fighter x = tile.getFighter();
        x.setHasMoved(true);
        x.setHasAttacked(true);
        tractorBeam = tile;
        putOnTerminal(fighter.getName() + " suspended " + x.getName() + " in his tractor beam! ");
    }

    public void buttonsToCancelTractorBeam() {
        moveBtn.setOnAction(this::cancelTractorBeam);
        moveBtn.setOnMouseEntered(null);
        moveBtn.setOnMouseExited(null);
        atkBtn.setOnAction(this::cancelTractorBeam);
        atkBtn.setOnMouseEntered(null);
        atkBtn.setOnMouseExited(null);
        skillBtn.setOnAction(this::cancelTractorBeam);
        nextBtn.setOnAction(this::cancelTractorBeam);
        prevBtn.setOnAction(this::cancelTractorBeam);
    }

    public void cancelTractorBeam(ActionEvent e) {
        Fighter fighter = map.getFighter();
        boolean[][] valid = map.getAttackable(fighter);
        for (int i = 0; i < valid.length; i++) {
            for (int j = 0; j < valid[i].length; j++) {
                if (valid[i][j]) {
                    StackPane pane = map.getMapTile(j, i).getStackPane();
                    int idx = pane.getChildren().size() - 1;
                    pane.getChildren().remove(idx);
                    pane.setOnMouseClicked(this::fighterStats);
                }
            }
        }
    }

    //Chaos's skill
    public void showGrenade() {
        Fighter fighter = map.getFighter();
        putInFocus(fighter.getxPos(), fighter.getyPos());
        if (!fighter.hasAttacked()) {
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
        } else {
            putOnTerminal(fighter.getName() + " has already used his attack");
        }
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
        for (MapTile t : map.getDiagAdjacent(tile)) {
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
        for (MapTile t : map.getDiagAdjacent(tile)) {
            StackPane p = t.getStackPane();
            int i = p.getChildren().size() - 1;
            p.getChildren().remove(i);
        }
    }

    public void doGrenade(MouseEvent e) {
        Fighter fighter = map.getFighter();
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
        fighter.setHasAttacked(true);
        boolean hit = false;
        ArrayList<MapTile> tiles = new ArrayList<>();
        StackPane pane = (StackPane) e.getSource();
        MapTile tile = paneToTile.get(pane);
        tiles.add(tile);
        tiles.addAll(map.getAdjacent(tile));
        tiles.addAll(map.getDiagAdjacent(tile));
        String s = fighter.getName() + " lobbed a grenade! ";
        for (MapTile t : tiles) {
            if (t.getFighter() != null && t.getFighter().isEnemy()) {
                hit = true;
                s += (game.attackFighter(fighter, t.getFighter()));
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

    /**
     *
     * @param e
     */
    public void cancelGrenade(ActionEvent e) {
        Fighter fighter = map.getFighter();
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

    //CONTROLS

    /**
     * Changes the user's selection to the next fighter
     * @param e nxtBtn
     */
    private void setNextBtn(ActionEvent e) {
        Fighter fighter = map.getFighter();
        StackPane oldPane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
        oldPane.getChildren().remove(2);
        fighter = map.nextFighter();
        StackPane newPane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
        newPane.getChildren().add(new ImageView("/View/Graphics/Tile/fighterSelect.gif"));
        putInFocus(fighter.getxPos(), fighter.getyPos());
        putOnTerminal("Next fighter is " + fighter.getName() + " (" + fighter.getHp() + "/" + fighter.getMaxHP() + ")");
    }

    /**
     * Changes the user's selection to the previous fighter.
     * @param e prevBtn
     */
    private void setPrevBtn(ActionEvent e) {
        Fighter fighter = map.getFighter();
        StackPane oldPane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
        oldPane.getChildren().remove(2);
        fighter = map.prevFighter();
        StackPane newPane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
        newPane.getChildren().add(new ImageView(Graphic.FIGHTERSLCT.imagePath()));
        putInFocus(fighter.getxPos(), fighter.getyPos());
        putOnTerminal("Previous fighter is " + fighter.getName() + " (" + fighter.getHp() + "/" + fighter.getMaxHP() + ")");
    }

    /**
     * Called when the end turn button is pressed.
     * Ends the player turn and begins the AI's turn.
     * @param e
     */
    private void setEndTurnBtn(ActionEvent e) {
        Fighter fighter = map.getFighter();
        putOnTerminal("You ended your turn");
        int x = fighter.getxPos();
        int y = fighter.getyPos();
        map.getMapTile(x, y).getStackPane().getChildren().remove(2);
        game.endTurn();
    }

    /**
     * Called when the AI finishes its turn and the user begins their turn.
     * Checks if the player has lost, otherwise start the user's turn.
     */
    public void startTurn() {
        if (map.getAllies().size() == 0) {
            putOnTerminal("GAME OVER");
        } else {
            putOnTerminal("Your turn begins");
            if (tractorBeam != null) {
                tractorBeam.getStackPane().getChildren().remove(2);
                tractorBeam = null;
            }
            Fighter fighter = map.getAllies().get(0);
            map.setFighter(fighter);
            int x = fighter.getxPos();
            int y = fighter.getyPos();
            map.getMapTile(x, y).getStackPane().getChildren().add(new ImageView(Graphic.FIGHTERSLCT.imagePath()));
        }
    }

    /**
     * Resets the buttons to their default setting.
     */
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

    /**
     * Called when a fighter on the map is clicked.
     * Shows the stats of the fighter at Maptile e.
     * @param e
     */
    private void fighterStats(MouseEvent e) {
        StackPane pane = (StackPane) e.getSource();
        MapTile tile = paneToTile.get(pane);
        int x = tile.getxPos();
        int y = tile.getyPos();
        Fighter f = map.getFighter(x, y);
        putOnTerminal(f.getStats());
    }

    //MAP COMPONENTS

    /**
     * Puts the given String s on the terminal
     * @param s
     */
    private void putOnTerminal(String s) {
        putOnTerminal(s, true);
    }

    /**
     * Helper function for putOnTerminal.
     * Holds the logic for when to make new lines, and which labels to remove from the terminal.
     * @param s String to put on terminal
     * @param newEntry if this string begins the entry.
     */
    private void putOnTerminal(String s, boolean newEntry) {
        if (s.length() > 38) {
            int cut = 38;
            while (s.charAt(cut) != ' ') {
                cut--;
            }
            String s1 = s.substring(0, cut);
            String s2 = s.substring(cut);
            if (newEntry) {
                putOnTerminal(s1, true);
            } else {
                putOnTerminal(s1, false);
            }
            putOnTerminal(s2, false);
        } else {
            Label l;
            if (newEntry) {
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

    /**
     * Moves the screen down
     * @param e
     */
    private void setDownRect(MouseEvent e) {
        double y = gridPane.getTranslateY();
        if (y > -map.getHeight() * 64 + 64*6) {
            gridPane.setTranslateY(y - 64);
        }
    }

    /**
     * Moves the screen up
     * @param e
     */
    private void setUpRect(MouseEvent e) {
        double y = gridPane.getTranslateY();
        if (y < 0) {
            gridPane.setTranslateY(y + 64);
        }
    }

    /**
     * Moves the screen right
     * @param e
     */
    private void setRightRect(MouseEvent e) {
        double x = gridPane.getTranslateX();
        if (x > -map.getWidth() * 64 + 64*10) {
            gridPane.setTranslateX(x - 64);
        }
    }

    /**
     * Moves the screen left
     * @param e
     */
    private void setLeftRect(MouseEvent e) {
        double x = gridPane.getTranslateX();
        if (x < 0) {
            gridPane.setTranslateX(x + 64);
        }
    }

    //AI

    /**
     * Called by the AI
     * Fighter f moves to Maptile tile
     * @param f
     * @param tile
     */
    public void enemyMove(Fighter f, MapTile tile) {
        int curX = f.getxPos();
        int curY = f.getyPos();
        int nextX = tile.getxPos();
        int nextY = tile.getyPos();
        MapTile oldTile = map.getMapTile(curX, curY);
        oldTile.getStackPane().getChildren().remove(1);
        f.setxPos(nextX);
        f.setyPos(nextY);
        MapTile nextTile = map.getMapTile(nextX, nextY);
        nextTile.getStackPane().getChildren().add(new ImageView(f.imagePath()));
        putOnTerminal(f.getName() + " moved to (" + (nextX - 1) + ", " + (nextY - 1) + ").");
    }

    /**
     * Called by the AI
     * Fighter attacker attacks Fighter defender
     * @param attacker
     * @param defender
     */
    public void enemyAttack(Fighter attacker, Fighter defender) {
        putOnTerminal(game.attackFighter(attacker, defender));
    }

    //INITIALIZER
    public void initialize() {
        upRect.setOnMouseClicked(this::setUpRect);
        downRect.setOnMouseClicked(this::setDownRect);
        rightRect.setOnMouseClicked(this::setRightRect);
        leftRect.setOnMouseClicked(this::setLeftRect);
        moveBtn.setOnAction(this::setMoveBtn);
        moveBtn.setOnMouseEntered(this::showMoves);
        atkBtn.setOnAction(this::setAtkBtn);
        atkBtn.setOnMouseEntered(this::showAttacks);
        skillBtn.setOnAction(this::setSkillBtn);
        nextBtn.setOnAction(this::setNextBtn);
        prevBtn.setOnAction(this::setPrevBtn);
        endTurnBtn.setOnAction(this::setEndTurnBtn);
    }
}
