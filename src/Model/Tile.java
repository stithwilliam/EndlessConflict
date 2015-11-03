package Model;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by William on 10/26/2015.
 */
public enum Tile {
    PLAIN(true, 1, "Plains"), FOREST(true, 2, "Forest"), ROCK(false, 2, "Rock");

    private String name;
    private Map<String, String> imagePath;
    private boolean moveable;
    private int moveCost;

    Tile(boolean b, int i, String n) {
        name = n;
        moveable = b;
        moveCost = i;
        imagePath = new HashMap<>();
        imagePath.put("PLAIN", "/View/Graphics/Tile/plainTile.png");
        imagePath.put("FOREST", "/View/Graphics/Tile/forestTile.png");
        imagePath.put("ROCK", "/View/Graphics/Tile/rockTile.png");
    }

    public String imagePath() {return imagePath.get(this.name());}
    public String getName() {return name;}
    public boolean isMoveable() {return moveable;}
    public int getMoveCost() {return moveCost;}
}
