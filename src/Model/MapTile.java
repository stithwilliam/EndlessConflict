package Model;

import javafx.scene.layout.StackPane;

import java.util.ArrayList;

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

    public MapTile(Tile t, int x, int y) {
        tile = t;
        name = t.getName();
        xPos = x;
        yPos = y;
        moveable = t.isMoveable();
        moveCost = t.getMoveCost();
        blocking = t.isBlocking();
        stackPane = null;
    }

    //Getters
    public String getName() {return name;}
    public int getxPos() {return xPos;}
    public int getyPos() {return yPos;}
    public boolean hasFighter() {
        for (Fighter f : Main.myGame.getFighters()) {
            if (f.getxPos() == xPos && f.getyPos() == yPos) {
                return true;
            }
        }
        return false;
    }
    public Fighter getFighter() {
        for (Fighter f : Main.myGame.getFighters()) {
            if (f.getxPos() == xPos && f.getyPos() == yPos) {
                return f;
            }
        }
        return null;
    }
    public boolean isMoveable() {return moveable;}
    public boolean isBlocking() {return blocking;}
    public int getMoveCost() {return moveCost;}
    public String imagePath() {return tile.imagePath();}
    public StackPane getStackPane() {return stackPane;}
    public Tile getTile() {return tile;}

    //Setters
    public void setStackPane(StackPane s) {stackPane = s;}

    //test code
    public String toString() {
        return "x: " + xPos + ", y: " + yPos + "" + "\nimagePath: " + imagePath();
    }
}
