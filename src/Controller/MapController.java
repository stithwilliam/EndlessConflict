package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import Model.Map;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

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

    public void generateMap(Map map) {
        System.out.println("generating map");
        myMap = map;
        int width = map.getWidth();
        int height = map.getHeight();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                ImageView tile;
                if (i == 2) {
                    tile = new ImageView("/View/Graphics/forestTile.png");
                } else if (i == 4 && j == 4) {
                    tile = new ImageView("/View/Graphics/rockTile.png");
                } else {
                    tile = new ImageView("/View/Graphics/plainTile.png");
                }
                StackPane tileContainer = new StackPane();
                tileContainer.getChildren().add(tile);
                gridPane.add(tileContainer, j, i);

            }
        }
        gridPane.setVgap(0);
        gridPane.setHgap(0);
        gridPane.setGridLinesVisible(true);
    }

    public void setRectClicked(MouseEvent e) {
        double y = gridPane.getTranslateY();
        double x = gridPane.getTranslateX();
        if (e.getSource() == downRect) {
            System.out.println("down");
            if (y >= -myMap.getHeight() * 64 + 386) {
                gridPane.setTranslateY(y - 64);
            }
        } else if (e.getSource() == upRect) {
            System.out.println("up");
            if (y < 0) {
                gridPane.setTranslateY(y + 64);
            }
        } else if (e.getSource() == leftRect) {
            System.out.println("left");
            if (x < 0) {
                gridPane.setTranslateX(gridPane.getTranslateX() + 64);
            }
        } else if (e.getSource() == rightRect){
            System.out.println("right");
            if (x >= -myMap.getWidth() * 64 - 640) {
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
}
