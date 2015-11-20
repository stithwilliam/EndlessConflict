package Model;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by William on 11/10/2015.
 */
public enum Graphic {
    /**Map Selectors**/
    MOVESLCT, ATTACKSLCT, FIGHTERSLCT,

    /**Dropped Equipment**/
    WPNDROP, ARMDROP, HEADDROP, FEETDROP,

    /**Misc**/
    TRACTORBEAM;

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
        imagePath.put("TRACTORBEAM", "/View/Graphics/Tile/tractorBeam.gif");
    }

    /**
     * gets the imagePath of this
     * @return String imagePath
     */
    public String imagePath() {return imagePath.get(this.name());}

}
