package Model;

import javafx.scene.layout.StackPane;

import java.util.ArrayList;

/**
 * Created by William on 10/27/2015.
 */
public class MapTile {

    /**Model of Tile**/
    private Tile tile;

    /**Name of this MapTile**/
    private String name;

    /**Position of this MapTile**/
    private int xPos, yPos;

    /**Attributes of this MapTile**/
    private boolean moveable, blocking;

    /**Cost to move across this MapTile**/
    private int moveCost;

    /**StackPane that is holding this MapTile**/
    private StackPane stackPane;

    /**
     * Constructor for MapTile
     * @param t Tile that models this
     * @param x int x position
     * @param y int y position
     */
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

    /**Getters**/
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

    /**Setters**/
    public void setStackPane(StackPane s) {stackPane = s;}

    /**
     * For Testing
     * TODO: remove
     **/
    public String toString() {
        return "x: " + xPos + ", y: " + yPos + "" + "\nimagePath: " + imagePath();
    }
}
