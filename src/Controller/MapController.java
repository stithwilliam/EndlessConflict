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
    private Fighter fighter;
    private HashMap<StackPane, MapTile> paneToTile;

    public void generateMap(Map map) {
        this.map = map;
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
        Fighter lizard = new Fighter(Hero.LIZARDKING, 2, 2, false);
        fighter = lizard;
        Fighter chaos = new Fighter(Hero.CHAOS, 7, 2, true);
        Main.myGame.addToAllies(lizard);
        Main.myGame.addToEnemies(chaos);
        populateMap();

    }

    //overloaded populateMap that populates with just allies and enemies
    public void populateMap() {
        ArrayList<Fighter> fighters = new ArrayList<>();
        fighters.addAll(Main.myGame.getAllies());
        fighters.addAll(Main.myGame.getEnemies());
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
    }

    //used to navigate the map
    private void setRectClicked(MouseEvent e) {
        double y = gridPane.getTranslateY();
        double x = gridPane.getTranslateX();
        if (e.getSource() == downRect) {
            if (y >= -map.getHeight() * 64 + 386) {
                gridPane.setTranslateY(y - 64);
            }
        } else if (e.getSource() == upRect) {
            if (y < 0) {
                gridPane.setTranslateY(y + 64);
            }
        } else if (e.getSource() == leftRect) {
            if (x < 0) {
                gridPane.setTranslateX(gridPane.getTranslateX() + 64);
            }
        } else if (e.getSource() == rightRect){
            if (x > -map.getWidth() * 64 + 640) {
                gridPane.setTranslateX(gridPane.getTranslateX() - 64);
            }
        }
    }

    //code for Move button
    private void setMoveBtn(ActionEvent e) {
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
        oldPane.getChildren().remove(1);
        //move fighter
        StackPane pane = (StackPane) e.getSource();
        MapTile tile = paneToTile.get(pane);
        int x = tile.getxPos();
        int y = tile.getyPos();
        fighter.setxPos(x);
        fighter.setyPos(y);
        pane.getChildren().add(new ImageView(fighter.imagePath()));
        //print to terminal
        putOnTerminal(fighter.getName() + " moved to " + tile.getName() + " at " + (x+1) + ", " + (y+1));
    }

    //code for Attack button
    private void setAtkBtn(ActionEvent e) {
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
        putOnTerminal(fighter.getName() + " attacking " + map.getFighter(x, y).getName() + " at " + (x+1) + ", " + (y+1));
    }

    private void setSkillBtn(ActionEvent e) {
        putOnTerminal("I have no skills yet");
    }

    //overloaded putOnTerminal
    private void putOnTerminal(String s) {
        putOnTerminal(s, true);
    }

    //puts s on the terminal
    private void putOnTerminal(String s, boolean newLine) {
        if (s.length() > 38) {
            String s1 = s.substring(0, 38);
            String s2 = s.substring(38);
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
    }


}
