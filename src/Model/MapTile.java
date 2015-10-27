package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by William on 10/27/2015.
 */
public class MapTile {

    Tile tile;
    int xPos, yPos;
    private boolean hasFighter;
    private boolean moveable;
    private int moveCost;

    public MapTile(Tile t, int x, int y) {
        tile = t;
        xPos = x;
        yPos = y;
        moveable = t.isMoveable();
        moveCost = t.getMoveCost();
        hasFighter = false;
    }

    //Getters
    public int getxPos() {return xPos;}
    public int getyPos() {return yPos;}
    public boolean hasFighter() {return hasFighter;}
    public boolean isMoveable() {return moveable;}
    public int getMoveCost() {return moveCost;}
    public String imagePath() {return tile.imagePath();}

}
