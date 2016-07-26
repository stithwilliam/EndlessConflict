package Controller;

import Model.*;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by William on 10/26/2015.
 */
public class BattleController {

    /**FXML injections**/
    @FXML
    private GridPane gridPane;
    @FXML
    private Button rightBtn;
    @FXML
    private Button leftBtn;
    @FXML
    private Button upBtn;
    @FXML
    private Button downBtn;
    @FXML
    private Button moveBtn;
    @FXML
    private Button atkBtn;
    @FXML
    private Button pauseBtn;
    @FXML
    private Button endTurnBtn;
    @FXML
    private ImageView allySprite;
    @FXML
    private ImageView allyHealthWheel;
    @FXML
    private ImageView enemySprite;
    @FXML
    private ImageView enemyHealthWheel;
    @FXML
    private Label allyName;
    @FXML
    private Label allyAtk;
    @FXML
    private Label allyMove;
    @FXML
    private Label allyRange;
    @FXML
    private Label allyRace;
    @FXML
    private Label allyHP;
    @FXML
    private Label enemyName;
    @FXML
    private Label enemyAtk;
    @FXML
    private Label enemyMove;
    @FXML
    private Label enemyRange;
    @FXML
    private Label enemyRace;
    @FXML
    private Label enemyHP;
    @FXML
    private VBox terminal;

    //Global vars
    private Map map;
    private Game game; // for convenience, game will never change
    private HashMap<StackPane, MapTile> paneToTile;
    private MapTile tractorBeam;
    private AnimationTimer scroller;
    private double deltX, deltY;

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
        deltX = 0;
        deltY = 0;
        scroller.start();
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
    private void populateMap(LinkedList<Fighter> fighters) {
        for (Fighter f : fighters) {
            int x = f.getxPos();
            int y = f.getyPos();
            ImageView image = new ImageView(f.imagePath());
            StackPane pane = map.getMapTile(x, y).getStackPane();
            pane.getChildren().add(image);
            pane.setOnMouseClicked(this::fighterClicked);
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
        double targetX = x * -64 + 64 * 4.5;
        double targetY = y * -64 + 64 * 3.5;


        AnimationTimer scrollUntil = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double gridX = gridPane.getTranslateX();
                double gridY = gridPane.getTranslateY();
                boolean yInRange = false;
                boolean xInRange = false;
                double transX = gridX + (int)((targetX - gridX) / 10);
                double transY = gridY + (int)((targetY - gridY) / 10);
                if (transX > 0 || transX < map.getWidth() * -64 + 9 * 64 || Math.abs(gridX - targetX) < 10  ) {
                    xInRange = true;
                }
                if (transY > 0 || transY < map.getHeight() * -64 + 7 * 64 || Math.abs(gridY - targetY) < 10) {
                    yInRange = true;
                }
                if (xInRange && yInRange) this.stop();
                if (!xInRange) gridPane.setTranslateX(transX);
                if (!yInRange) gridPane.setTranslateY(transY);

            }
        };
        scrollUntil.start();

        /*double gridX = gridPane.getTranslateX();
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
        deltX = 0;
        deltY = 0;*/
    }

    //MOVE

    /**
     * Called when the user mouseOvers the move button.
     * Shows the valid spots to move of the current fighter
     * @param e moveBtn
     */
    private void moveBtnEnter(MouseEvent e) {
        showMoves();
        moveBtn.setStyle("-fx-border-color: cyan; -fx-border-width: 2");
    }

    private void showMoves() {
        Fighter fighter = map.getFighter();
        if (!fighter.hasMoved()) {
            boolean[][] valid = map.getValidMoves(fighter);
            for (int i = 0; i < valid.length; i++) {
                for (int j = 0; j < valid[0].length; j++) {
                    if (valid[i][j]) {
                        //ImageView move = getMoveSelect(i, j, valid);
                        ImageView move = new ImageView("/View/Graphics/Tile/moveSelect.png");
                        StackPane pane = map.getMapTile(j, i).getStackPane();
                        if (move.getImage() != null) {
                            pane.getChildren().add(move);
                        }
                    }
                }
            }
            moveBtn.setOnMouseExited(this::noShowMoves);
        }
    }

    /*
    private ImageView getMoveSelect(int i, int j, boolean[][] valid) {
        Fighter fighter = map.getFighter();
        valid[fighter.getyPos()][fighter.getxPos()] = true;
        boolean left, right, up, down;
        Image image = null;
        up = (i > 0 && !valid[i - 1][j]);
        down = (i < valid.length - 1 && !valid[i + 1][j]);
        left = (j > 0 && !valid[i][j - 1]);
        right = (j < valid[0].length - 1 && !valid[i][j + 1]);
        if (up && down && left && right) {
            image = new Image("/View/Graphics/Tile/moveSelect1111.png");
        }

        else if (right && down && left && !up) {
            image = new Image("/View/Graphics/Tile/moveSelect1110.png");
        } else if (right && down && !left && up) {
            image = new Image("/View/Graphics/Tile/moveSelect1101.png");
        } else if (right && !down && left && up) {
            image = new Image("/View/Graphics/Tile/moveSelect1011.png");
        } else if (!right && down && left && up) {
            image = new Image("/View/Graphics/Tile/moveSelect0111.png");
        }

        else if (right && down && !left && !up) {
            image = new Image("/View/Graphics/Tile/moveSelect1100.png");
        } else if (right && !down && left && !up) {
            image = new Image("/View/Graphics/Tile/moveSelect1010.png");
        } else if (!right && down && left && !up) {
            image = new Image("/View/Graphics/Tile/moveSelect0110.png");
        } else if (right && !down && !left && up) {
            image = new Image("/View/Graphics/Tile/moveSelect1001.png");
        } else if (!right && down && !left && up) {
            image = new Image("/View/Graphics/Tile/moveSelect0101.png");
        } else if (!right && !down && left && up) {
            image = new Image("/View/Graphics/Tile/moveSelect0011.png");
        }

        else if (right && !down && !left && !up) {
            image = new Image("/View/Graphics/Tile/moveSelect1000.png");
        } else if (!right && down && !left && !up) {
            image = new Image("/View/Graphics/Tile/moveSelect0100.png");
        } else if (!right && !down && left && !up) {
            image = new Image("/View/Graphics/Tile/moveSelect0010.png");
        } else if (!right && !down && !left && up) {
            image = new Image("/View/Graphics/Tile/moveSelect0001.png");
        }

        return new ImageView(image);
    }
    */

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
            toConsole(fighter.getName() + " has already moved");
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
        pane.setOnMouseClicked(this::fighterClicked);
        //print to terminal
        toConsole(fighter.getName() + " moved to " + tile.getName() + " at " + (x + 1) + ", " + (y + 1));
        buttonsToDefault();
    }

    /**
     * Called when moveBtnEnter() is called.
     * Sets all buttons to cancel the pending moves.
     */
    private void buttonsToCancelMove() {
        moveBtn.setOnAction(this::cancelMove);
        moveBtn.setOnMouseEntered(null);
        moveBtn.setOnMouseExited(null);
        atkBtn.setOnAction(this::cancelMove);
        atkBtn.setOnMouseEntered(null);
        atkBtn.setOnMouseExited(null);
        pauseBtn.setOnAction(this::cancelMove);
        endTurnBtn.setOnAction(this::cancelMove);
    }

    /**
     * Called when the user presses another button after clicking move.
     * Cancels the pending move.
     * @param e
     */
    private void cancelMove(ActionEvent e) {
        Fighter fighter = map.getFighter();
        if ((Button) e.getSource() != moveBtn) {
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
        }
        toConsole(fighter.getName() + "'s move was canceled");
        buttonsToDefault();
        moveBtn.setOnMouseExited(this::noShowMoves);
    }

    //ATTACK

    /**
     * Called when the user mouseOvers the attack button.
     * Shows the valid spots to attack of the current fighter
     * @param e atkBtn
     */
    private void attackBtnEnter(MouseEvent e) {
        showAttacks();
        atkBtn.setStyle("-fx-border-color: cyan; -fx-border-width: 2");
        atkBtn.setBlendMode(BlendMode.HARD_LIGHT);
    }

    private void showAttacks() {
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
            atkBtn.setOnMouseExited(this::attackBtnExit);
        }
    }

    /**
     * Called when the player exits the attack button.
     * Removes the display of possible attacks.
     * @param e atkBtn
     */
    private void attackBtnExit(MouseEvent e) {
        noShowAttacks(e);
        atkBtn.setStyle("");
    }

    private void noShowAttacks(MouseEvent e) {
        System.out.println("no show");
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
            boolean hasAttack = false;
            for (int i = 0; i < valid[0].length; i++) {
                for (int j = 0; j < valid.length; j++) {
                    if (valid[j][i]) {
                        ImageView atk = new ImageView(Graphic.ATTACKSLCT.imagePath());
                        StackPane pane = map.getMapTile(i, j).getStackPane();
                        pane.getChildren().add(atk);
                        pane.setOnMouseClicked(this::attackHere);
                        hasAttack = true;
                    }
                }
            }
            if (!hasAttack) {
                atkBtn.setOnMouseExited(null);
                atkBtn.setOnAction(null);
                toConsole(fighter.getName() + " has no valid targets");
            } else {
                buttonsToCancelAttack();
            }
        } else {
            toConsole(fighter.getName() + " has already attacked");
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
                    pane.setOnMouseClicked(this::fighterClicked);
                }
            }
        }
        fighter.setHasAttacked(true);
        StackPane pane = (StackPane) e.getSource();
        MapTile tile = paneToTile.get(pane);
        Fighter defender = map.getFighter(tile.getxPos(), tile.getyPos());
        toConsole(Main.myGame.attackFighter(fighter, defender));
        buttonsToDefault();
        showEnemy(defender);
    }

    /**
     * Called when attackBtnEnter() is called.
     * Sets all buttons to cancel the pending attack.
     */
    private void buttonsToCancelAttack() {
        moveBtn.setOnAction(this::cancelAttack);
        moveBtn.setOnMouseEntered(null);
        moveBtn.setOnMouseExited(null);
        atkBtn.setOnAction(this::cancelAttack);
        atkBtn.setOnMouseEntered(null);
        atkBtn.setOnMouseExited(null);
        pauseBtn.setOnAction(this::cancelAttack);
        atkBtn.setOnAction(this::cancelAttack);
        endTurnBtn.setOnAction(this::cancelAttack);
    }

    /**
     * Called when the user presses another button after clicking attack.
     * Cancels the pending attack.
     * @param e
     */
    private void cancelAttack(ActionEvent e) {
        Fighter fighter = map.getFighter();
        //if ((Button) e.getSource() != atkBtn) {
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
        //}
        toConsole(fighter.getName() + "'s attack was canceled");
        buttonsToDefault();
        //atkBtn.setOnMouseExited(this::noShowAttacks);
    }

    /**
     * Default state for the attack button when pressed.
     * Shows the available attacks for the current fighter on the screen, unless there are none.
     * Sets the valid tiles to attackHere().
     * @param e atkBtn
     */
    private void setPauseBtn(ActionEvent e) {
        toConsole("I don't work yet. !_!");
    }


    //CONTROLS


    /**
     * Called when the end turn button is pressed.
     * Ends the player turn and begins the AI's turn.
     * @param e
     */
    private void setEndTurnBtn(ActionEvent e) {
        Fighter fighter = map.getFighter();
        toConsole("You ended your turn");
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
            toConsole("GAME OVER");
        } else {
            toConsole("Your turn begins");
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

    private void buttonsToDefault(MouseEvent e) {
        buttonsToDefault();
    }

    /**
     * Resets the buttons to their default setting.
     */
    private void buttonsToDefault() {
        moveBtn.setOnAction(this::setMoveBtn);
        moveBtn.setOnMouseEntered(this::moveBtnEnter);
        moveBtn.setOnMouseExited(null);
        atkBtn.setOnAction(this::setAtkBtn);
        atkBtn.setOnMouseEntered(this::attackBtnEnter);
        atkBtn.setOnMouseExited(null);
        pauseBtn.setOnAction(this::setPauseBtn);
        endTurnBtn.setOnAction(this::setEndTurnBtn);
    }

    /**
     * Called when a fighter on the map is clicked.
     * Shows the stats of the fighter at Maptile e.
     * @param e
     */
    private void fighterClicked(MouseEvent e) {
        StackPane pane = (StackPane) e.getSource();
        MapTile tile = paneToTile.get(pane);
        int x = tile.getxPos();
        int y = tile.getyPos();
        Fighter c = map.getFighter(x, y);
        if (!c.isEnemy()) {
            Fighter fighter = map.getFighter();
            StackPane oldPane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
            oldPane.getChildren().remove(2);
            map.setFighter(c);
            StackPane newPane = map.getMapTile(x, y).getStackPane();
            newPane.getChildren().add(new ImageView(Graphic.FIGHTERSLCT.imagePath()));
            showAlly(c);
        } else {
            showEnemy(c);
        }
    }

    public void showAlly(Fighter f) {
        allyName.setText(f.getName());
        allyAtk.setText("ATT: " + f.getAtt());
        allyMove.setText("MOVE: " + f.getMov());
        allyRange.setText("RANGE: " + f.getRange());
        allyRace.setText("RACE: " + f.getRace());
        allyHP.setText(f.getHp() + "/" + f.getMaxHP());
        allySprite.setImage(new Image(f.getModel().imagePath()));
        allyHealthWheel.setImage(healthWheel(f));
    }

    public void showEnemy(Fighter f) {
        enemyName.setText(f.getName());
        enemyAtk.setText("ATT: " + f.getAtt());
        enemyMove.setText("MOVE: " + f.getMov());
        enemyRange.setText("RANGE: " + f.getRange());
        enemyRace.setText("RACE: " + f.getRace());
        enemyHP.setText(f.getHp() + "/" + f.getMaxHP());
        enemySprite.setImage(new Image(f.getModel().imagePath()));
        enemyHealthWheel.setImage(healthWheel(f));
        toConsole("" + f.getName() + ": " + f.getDescription());

    }

    private Image healthWheel(Fighter f) {
        int n = (f.getHp() * 100) / f.getMaxHP();
        String s = "";
        if (n > 94) {
            s = "/View/Graphics/HealthWheel/healthWheel1.png";
        } else if (n > 88) {
            s = "/View/Graphics/HealthWheel/healthWheel2.png";
        } else if (n > 82) {
            s = "/View/Graphics/HealthWheel/healthWheel3.png";
        } else if (n > 75) {
            s = "/View/Graphics/HealthWheel/healthWheel4.png";
        } else if (n > 69) {
            s = "/View/Graphics/HealthWheel/healthWheel5.png";
        } else if (n > 63) {
            s = "/View/Graphics/HealthWheel/healthWheel6.png";
        } else if (n > 57) {
            s = "/View/Graphics/HealthWheel/healthWheel7.png";
        } else if (n > 50) {
            s = "/View/Graphics/HealthWheel/healthWheel8.png";
        } else if (n > 44) {
            s = "/View/Graphics/HealthWheel/healthWheel9.png";
        } else if (n > 38) {
            s = "/View/Graphics/HealthWheel/healthWheel10.png";
        } else if (n > 32) {
            s = "/View/Graphics/HealthWheel/healthWheel11.png";
        } else if (n > 25) {
            s = "/View/Graphics/HealthWheel/healthWheel12.png";
        } else if (n > 19) {
            s = "/View/Graphics/HealthWheel/healthWheel13.png";
        } else if (n > 13) {
            s = "/View/Graphics/HealthWheel/healthWheel14.png";
        } else if (n > 7) {
            s = "/View/Graphics/HealthWheel/healthWheel15.png";
        } else if (n > 0) {
            s = "/View/Graphics/HealthWheel/healthWheel16.png";
        } else {
            s = "/View/Graphics/HealthWheel/healthWheel17.png";
        }
        return new Image(s);
    }

    //MAP COMPONENTS

    /**
     * Puts the given String s on the terminal
     * @param s
     */
    private void toConsole(String s) {
        toConsole(s, true);
    }

    /**
     * Helper function for toConsole.
     * Holds the logic for when to make new lines, and which labels to remove from the terminal.
     * @param s String to put on terminal
     * @param newEntry if this string begins the entry.
     */
    private void toConsole(String s, boolean newEntry) {
        if (s.length() > 42) {
            int cut = 42;
            while (s.charAt(cut) != ' ') {
                cut--;
            }
            String s1 = s.substring(0, cut);
            String s2 = s.substring(cut);
            if (newEntry) {
                toConsole(s1, true);
            } else {
                toConsole(s1, false);
            }
            toConsole(s2, false);
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

    private void moveRight(MouseEvent e) {
        deltX = 2;
    }

    private void moveLeft(MouseEvent e) {
        deltX = -2;
    }

    private void moveUp(MouseEvent e) {
        deltY = 2;
    }

    private void moveDown(MouseEvent e) {
        deltY = -2;
    }

    private void stopScroll(MouseEvent e) {
        deltX = 0;
        deltY = 0;
    }

    /**AI**/

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
        toConsole(f.getName() + " moved to (" + (nextX - 1) + ", " + (nextY - 1) + ").");
    }

    /**
     * Called by the AI
     * Fighter attacker attacks Fighter defender
     * @param attacker
     * @param defender
     */
    public void enemyAttack(Fighter attacker, Fighter defender) {
        toConsole(game.attackFighter(attacker, defender));
        showAlly(map.getFighter());
    }

    /**
     * Initializer
     */
    public void initialize() {
        moveBtn.setOnAction(this::setMoveBtn);
        moveBtn.setOnMouseEntered(this::moveBtnEnter);
        atkBtn.setOnAction(this::setAtkBtn);
        atkBtn.setOnMouseEntered(this::attackBtnEnter);
        pauseBtn.setOnAction(this::setPauseBtn);
        endTurnBtn.setOnAction(this::setEndTurnBtn);
        rightBtn.setOnMouseEntered(this::moveRight);
        rightBtn.setOnMouseExited(this::stopScroll);
        leftBtn.setOnMouseEntered(this::moveLeft);
        leftBtn.setOnMouseExited(this::stopScroll);
        upBtn.setOnMouseEntered(this::moveUp);
        upBtn.setOnMouseExited(this::stopScroll);
        downBtn.setOnMouseEntered(this::moveDown);
        downBtn.setOnMouseExited(this::stopScroll);

        //create the scroller that handles moving around the map
        scroller = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (deltX != 0) {
                    if (deltX > 0) {
                        deltX += 0.1;
                    } else {
                        deltX -= 0.1;
                    }
                }
                if (deltY != 0) {
                    if (deltY > 0) {
                        deltY += 0.1;
                    } else {
                        deltY -= 0.1;
                    }
                }
                gridPane.setTranslateX(gridPane.getTranslateX() - (int) deltX);
                if (gridPane.getTranslateX() > 0) {
                    gridPane.setTranslateX(0);
                    deltX = 0;
                }
                if (gridPane.getTranslateX() < -map.getWidth() * 64 + 64 * 9) {
                    gridPane.setTranslateX(-map.getWidth() * 64 + 64 * 9);
                    deltX = 0;
                }
                gridPane.setTranslateY(gridPane.getTranslateY() + (int) deltY);
                if (gridPane.getTranslateY() > 0) {
                    gridPane.setTranslateY(0);
                    deltY = 0;
                }
                if (gridPane.getTranslateY() < -map.getHeight() * 64 + 64*7) {
                    gridPane.setTranslateY(-map.getHeight() * 64 + 64*7);
                    deltY = 0;
                }
            }
        };
    }
}