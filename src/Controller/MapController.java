package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Created by William on 10/26/2015.
 */
public class MapController {

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

    private Map map;
    private Game game;
    private Fighter fighter;
    private HashMap<StackPane, MapTile> paneToTile;

    public void generateMap(Map map) {
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

        //test code
        Fighter lizard = new Fighter(Hero.LIZARDKING, 0, 0, false);
        fighter = lizard;
        Fighter slimeBall1 = new Fighter(Unit.SLIMEBALL, 1, 0, false);
        slimeBall1.setName("Slime ball");
        Fighter slimeBall2 = new Fighter(Unit.SENTRYDRONE, 0, 1, false);
        slimeBall2.setName("Sentry drone");
        Fighter chaos = new Fighter(Hero.CHAOS, 6, 3, true);
        game.addToAllies(lizard);
        game.addToAllies(slimeBall1);
        game.addToAllies(slimeBall2);
        game.addToEnemies(chaos);
        game.setFighter(lizard);
        populateMap();

    }

    //overloaded populateMap that populates with just allies and enemies
    public void populateMap() {
        ArrayList<Fighter> fighters = new ArrayList<>();
        fighters.addAll(game.getAllies());
        fighters.addAll(game.getEnemies());
        populateMap(fighters);
    }

    public void populateMap(ArrayList<Fighter> fighters) {
        for (Fighter f : fighters) {
            int x = f.getxPos();
            int y = f.getyPos();
            ImageView image = new ImageView(f.imagePath());
            StackPane pane = map.getMapTile(x, y).getStackPane();
            pane.getChildren().add(image);
            map.addFighter(f);
        }
        StackPane pane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
        pane.getChildren().add(new ImageView("/View/Graphics/Tile/fighterSelect.png"));
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

    //code for Move button
    private void setMoveBtn(ActionEvent e) {
        buttonsToCancelMove();
        putInFocus(fighter.getxPos(), fighter.getyPos());
        boolean[][] valid = map.getValidMoves(fighter);
        for (int i = 0; i < valid[0].length; i++) {
            for (int j = 0; j < valid.length; j++) {
                if (valid[j][i]) {
                    ImageView move = new ImageView("/View/Graphics/Tile/moveSelect.png");
                    StackPane pane = map.getMapTile(i, j).getStackPane();
                    pane.getChildren().add(move);
                    pane.setOnMouseClicked(this::moveHere);
                }
            }
        }
    }

    //moves fighter to a valid tile
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
        //move fighter
        StackPane pane = (StackPane) e.getSource();
        MapTile tile = paneToTile.get(pane);
        int x = tile.getxPos();
        int y = tile.getyPos();
        fighter.setxPos(x);
        fighter.setyPos(y);
        pane.getChildren().add(new ImageView(fighter.imagePath()));
        pane.getChildren().add(new ImageView("/View/Graphics/Tile/fighterSelect.png"));
        //print to terminal
        putOnTerminal(fighter.getName() + " moved to " + tile.getName() + " at " + (x + 1) + ", " + (y + 1));
        buttonsToDefault();
    }

    private void buttonsToCancelMove() {
        moveBtn.setOnAction(this::cancelMove);
        atkBtn.setOnAction(this::cancelMove);
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

    //code for Attack button
    private void setAtkBtn(ActionEvent e) {
        putInFocus(fighter.getxPos(), fighter.getyPos());
        boolean[][] valid = map.getValidAttacks(fighter);
        for (int i = 0; i < valid[0].length; i++) {
            for (int j = 0; j < valid.length; j++) {
                if (valid[j][i]) {
                    ImageView atk = new ImageView("/View/Graphics/Tile/attackSelect.png");
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
            putOnTerminal(fighter.getName() + " has no valid targets");
        } else {
            buttonsToCancelAttack();
        }
    }

    private void attackHere(MouseEvent e) {
        //removing valid tile image
        boolean[][] valid = map.getValidAttacks(fighter);
        for (int i = 0; i < valid[0].length; i++) {
            for (int j = 0; j < valid.length; j++) {
                if (valid[j][i]) {
                    StackPane pane = map.getMapTile(i, j).getStackPane();
                    pane.getChildren().remove(2);
                    pane.setOnMouseClicked(null);
                }
            }
        }
        StackPane pane = (StackPane) e.getSource();
        MapTile tile = paneToTile.get(pane);
        int x = tile.getxPos();
        int y = tile.getyPos();
        putOnTerminal(fighter.getName() + " attacked " + map.getFighter(x, y).getName() + " at " + (x+1) + ", " + (y+1));
        buttonsToDefault();
    }

    private void buttonsToCancelAttack() {
        moveBtn.setOnAction(this::cancelAttack);
        atkBtn.setOnAction(this::cancelAttack);
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
                    pane.getChildren().remove(2);
                    pane.setOnMouseClicked(null);
                }
            }
        }
        putOnTerminal(fighter.getName() + "'s attack was canceled");
        buttonsToDefault();
    }

    private void setSkillBtn(ActionEvent e) {
        putInFocus(fighter.getxPos(), fighter.getyPos());
        putOnTerminal("I have no skills yet");
    }

    private void setNextBtn(ActionEvent e) {
        StackPane oldPane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
        oldPane.getChildren().remove(2);
        fighter = game.nextFighter();
        StackPane newPane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
        newPane.getChildren().add(new ImageView("/View/Graphics/Tile/fighterSelect.png"));
        putInFocus(fighter.getxPos(), fighter.getyPos());
        putOnTerminal("Next fighter is " + fighter.getName());
    }

    private void setPrevBtn(ActionEvent e) {
        StackPane oldPane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
        oldPane.getChildren().remove(2);
        fighter = game.prevFighter();
        StackPane newPane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
        newPane.getChildren().add(new ImageView("/View/Graphics/Tile/fighterSelect.png"));
        putInFocus(fighter.getxPos(), fighter.getyPos());
        putOnTerminal("Previous fighter is " + fighter.getName());
    }

    private void buttonsToDefault() {
        moveBtn.setOnAction(this::setMoveBtn);
        atkBtn.setOnAction(this::setAtkBtn);
        skillBtn.setOnAction(this::setSkillBtn);
        nextBtn.setOnAction(this::setNextBtn);
        prevBtn.setOnAction(this::setPrevBtn);
    }

    //overloaded putOnTerminal
    private void putOnTerminal(String s) {
        putOnTerminal(s, true);
    }

    //puts s on the terminal
    private void putOnTerminal(String s, boolean newLine) {
        if (s.length() > 38) {
            int cut = 38;
            while (s.charAt(cut) != ' ') {
                cut--;
            }
            String s1 = s.substring(0, cut);
            String s2 = s.substring(cut);
            putOnTerminal(s1, true);
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

    //initializes the map buttons
    public void initialize() {
        upRect.setOnMouseClicked(this::setRectClicked);
        downRect.setOnMouseClicked(this::setRectClicked);
        rightRect.setOnMouseClicked(this::setRectClicked);
        leftRect.setOnMouseClicked(this::setRectClicked);
        moveBtn.setOnAction(this::setMoveBtn);
        atkBtn.setOnAction(this::setAtkBtn);
        skillBtn.setOnAction(this::setSkillBtn);
        nextBtn.setOnAction(this::setNextBtn);
        prevBtn.setOnAction(this::setPrevBtn);
    }


}
