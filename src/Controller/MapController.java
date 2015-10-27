package Controller;

import Model.Fighter;
import Model.Hero;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import Model.Map;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by William on 10/26/2015.
 */
public class MapController {

    @FXML
    private ScrollPane scrollPane;
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

    private Map myMap;
    private HashMap<String, StackPane> stackPanes = new HashMap<>();

    public void generateMap(Map map) {
        myMap = map;
        int width = map.getWidth();
        int height = map.getHeight();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                ImageView tile = new ImageView(map.getMapTile(i, j).imagePath());
                StackPane tileContainer = new StackPane();
                tileContainer.getChildren().add(tile);
                gridPane.add(tileContainer, i, j);
                stackPanes.put("" + i + "," + j, tileContainer);
            }
        }
        gridPane.setVgap(0);
        gridPane.setHgap(0);

        //Test code
        StackPane center = getStackPane(7, 4);
        Fighter theKing = new Fighter(Hero.LIZARDKING, 7, 4);
        map.addFighter(theKing);
        boolean[][] valid = map.getValidMoves(theKing);
        for (int i = 0; i < valid[0].length; i++) {
            for (int j = 0; j < valid.length; j++) {
                if (valid[j][i]) {
                    System.out.println("GOOD at " + i + "," + j);
                    ImageView good = new ImageView(Hero.MODELX.imagePath());
                    StackPane pane = getStackPane(i, j);
                    pane.getChildren().add(good);
                } else {
                    System.out.println("BAD at " + i + "," + j);
                }
            }
        }
        center.getChildren().add(new ImageView(Hero.LIZARDKING.imagePath()));
    }

    public void setRectClicked(MouseEvent e) {
        double y = gridPane.getTranslateY();
        double x = gridPane.getTranslateX();
        if (e.getSource() == downRect) {
            if (y >= -myMap.getHeight() * 64 + 386) {
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
            if (x > -myMap.getWidth() * 64 + 640) {
                gridPane.setTranslateX(gridPane.getTranslateX() - 64);
            }
        }
    }

    public void initialize() {
        upRect.setOnMouseClicked(this::setRectClicked);
        downRect.setOnMouseClicked(this::setRectClicked);
        rightRect.setOnMouseClicked(this::setRectClicked);
        leftRect.setOnMouseClicked(this::setRectClicked);
    }

    public StackPane getStackPane(int i, int j) {
        return stackPanes.get("" + i + "," + j);
    }
}
