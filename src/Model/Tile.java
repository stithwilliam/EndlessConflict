package Model;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by William on 10/26/2015.
 */
public enum Tile {
    PLAIN(true, 1), FOREST(true, 2), ROCK(false, 2);

    private Map<String, String> imagePath;
    private boolean moveable;
    private int moveCost;

    Tile(boolean b, int i) {
        moveable = b;
        moveCost = i;
        imagePath = new HashMap<>();
        imagePath.put("PLAIN", "/View/Graphics/plainTile.png");
        imagePath.put("FOREST", "/View/Graphics/forestTile.png");
        imagePath.put("ROCK", "/View/Graphics/rockTile.png");
    }

    public String imagePath() { return imagePath.get(this.name());}
    public boolean isMoveable() {return moveable;}
    public int getMoveCost() {return moveCost;}
}
