package Controller;

import Model.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

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
    private FlowPane terminal;

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
        Fighter theKing = new Fighter(Hero.LIZARDKING, 7, 4, false);
        Fighter chaos = new Fighter(Hero.CHAOS, 2, 2, true);
        StackPane starter = map.getMapTile(7, 4).getStackPane();
        starter.getChildren().add(new ImageView(Hero.LIZARDKING.imagePath()));
        StackPane pane = map.getMapTile(2, 2).getStackPane();
        pane.getChildren().add(new ImageView(Hero.CHAOS.imagePath()));
        map.addFighter(chaos);
        map.addFighter(theKing);
        fighter = theKing;
        //test code \\
    }

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

    private void setMoveBtn(ActionEvent e) {
        boolean[][] valid = map.getValidMoves(fighter);
        for (int i = 0; i < valid[0].length; i++) {
            for (int j = 0; j < valid.length; j++) {
                if (valid[j][i]) {
                    ImageView good = new ImageView("/View/Graphics/moveSelect.png");
                    StackPane pane = map.getMapTile(i, j).getStackPane();
                    pane.getChildren().add(good);
                    pane.setOnMouseClicked(this::moveHere);
                }
            }
        }
    }

    private void moveHere(MouseEvent e) {
        System.out.println("MOVE HERE");
        //REMOVE MOVE STUFF
        //removing valid tile image
        boolean[][] valid = map.getValidMoves(fighter);
        for (int i = 0; i < valid[0].length; i++) {
            for (int j = 0; j < valid.length; j++) {
                if (valid[j][i]) {
                    StackPane pane = map.getMapTile(i, j).getStackPane();
                    pane.getChildren().remove(1);
                }
            }
        }
        //remove old fighter
        StackPane oldPane = map.getMapTile(fighter.getxPos(), fighter.getyPos()).getStackPane();
        oldPane.getChildren().remove(1);
        //MOVE FIGHTER
        StackPane pane = (StackPane) e.getSource();
        MapTile tile = paneToTile.get(pane);
        int x = tile.getxPos();
        int y = tile.getyPos();
        fighter.setxPos(x);
        fighter.setyPos(y);
        pane.getChildren().add(new ImageView(fighter.imagePath()));
    }

    private void setAtkBtn(ActionEvent e) {
        System.out.println("attack!");
    }

    public void initialize() {
        upRect.setOnMouseClicked(this::setRectClicked);
        downRect.setOnMouseClicked(this::setRectClicked);
        rightRect.setOnMouseClicked(this::setRectClicked);
        leftRect.setOnMouseClicked(this::setRectClicked);
        moveBtn.setOnAction(this::setMoveBtn);
        atkBtn.setOnAction(this::setAtkBtn);
        Label toConsole = new Label();
        toConsole.setText("I am a console hear me roar! ");
        terminal.getChildren().add(consoleLabel(toConsole));
    }

    private Label consoleLabel(Label l) {
        l.setTextFill(Paint.valueOf("lime"));
        l.setFont(Font.font("System Regular", 16));
        System.out.println("label: " + l.getText());
        return l;
    }
}
