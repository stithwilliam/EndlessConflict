package Model;

import java.util.Map;
import java.util.HashMap;

/**
 * Graphic enum, used to easily get image paths for different graphics
 */
public enum Graphic {
    /**Map Selectors**/
    MOVESLCT, ATTACKSLCT, FIGHTERSLCT, HEALSLCT, REWARDCHEST, SHIELDWHEEL;

    /**map of all of the imagePaths of the Graphics**/
    private Map<String, String> imagePath;

    /**
     * Constructor for Graphic
     */
    Graphic() {
        imagePath = new HashMap<>();
        imagePath.put("MOVESLCT", "/View/Graphics/Tile/moveSelect.png");
        imagePath.put("ATTACKSLCT", "/View/Graphics/Tile/attackSelect.png");
        imagePath.put("FIGHTERSLCT", "/View/Graphics/Tile/fighterSelect.gif");
        imagePath.put("HEALSLCT", "/View/Graphics/Tile/healSelect.png");
        imagePath.put("REWARDCHEST", "/View/Graphics/Tile/rewardChest.png");
        imagePath.put("SHIELDWHEEL", "/View/Graphics/HealthWheel/healthWheelShielded.png");
    }

    /**
     * gets the imagePath of this
     * @return String imagePath
     */
    public String imagePath() {return imagePath.get(this.name());}

}
