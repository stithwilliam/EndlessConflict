package Model;

import javafx.scene.layout.StackPane;

/**
 * Created by William on 10/27/2015.
 */
public class MapTile {

    /**Base of this tile**/
    private TileBase tileBase;

    /**Position of this MapTile**/
    private int xPos, yPos;

    /**Cost to move across this MapTile**/
    private int moveCost;

    /**StackPane that is holding this MapTile**/
    private StackPane stackPane;

    /**
     * Constructor for MapTile
     * @param t TileBase that models this
     * @param x int x position
     * @param y int y position
     */
    public MapTile(TileBase t, int x, int y) {
        tileBase = t;
        xPos = x;
        yPos = y;
        stackPane = null;
    }

    /**Getters**/
    public String getName() {return tileBase.getName();}
    public int getxPos() {return xPos;}
    public int getyPos() {return yPos;}
    public boolean hasFighter() {
        for (Fighter f : Main.myGame.getMap().getFighters()) {
            if (f.getxPos() == xPos && f.getyPos() == yPos) {
                return true;
            }
        }
        return false;
    }
    public Fighter getFighter() {
        for (Fighter f : Main.myGame.getMap().getFighters()) {
            if (f.getxPos() == xPos && f.getyPos() == yPos) {
                return f;
            }
        }
        return null;
    }
    public boolean isMoveable() {return tileBase.isMoveable();}
    public boolean isBlocking() {return tileBase.isBlocking();}
    public int getMoveCost() {return tileBase.getMoveCost();}
    public String imagePath() {return tileBase.imagePath();}
    public StackPane getStackPane() {return stackPane;}

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
