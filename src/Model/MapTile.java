package Model;

import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by William on 10/27/2015.
 */
public class MapTile {

    private Tile tile;
    private String name;
    private int xPos, yPos;
    private boolean moveable, blocking;
    private int moveCost;
    private StackPane stackPane;
    private Fighter fighter;

    public MapTile(Tile t, int x, int y) {
        name = t.getName();
        xPos = x;
        yPos = y;
        moveable = t.isMoveable();
        moveCost = t.getMoveCost();
        blocking = t.isBlocking();
        stackPane = null;
        fighter = null;
    }

    //Getters
    public String getName() {return name;}
    public int getxPos() {return xPos;}
    public int getyPos() {return yPos;}
    public boolean hasFighter() {return (fighter != null);}
    public boolean isMoveable() {return moveable;}
    public boolean isBlocking() {return blocking;}
    public int getMoveCost() {return moveCost;}
    public String imagePath() {return tile.imagePath();}
    public StackPane getStackPane() {return stackPane;}

    //Setters
    public void setStackPane(StackPane s) {stackPane = s;}
    public void setFighter(Fighter f) {fighter = f;}

}
