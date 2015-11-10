package Model;

import java.util.Map;
import java.util.HashMap;

/**
 * Created by William on 11/10/2015.
 */
public enum Graphic {
    MOVESLCT, ATTACKSLCT, FIGHTERSLCT;

    private Map<String, String> imagePath;

    Graphic() {
        imagePath = new HashMap<>();
        imagePath.put("MOVESLCT", "/View/Graphics/Tile/moveSelect.png");
        imagePath.put("ATTACKSLCT", "/View/Graphics/Tile/attackSelect.png");
        imagePath.put("FIGHTERSLCT", "/View/Graphics/Tile/fighterSelect.gif");
    }

    public String imagePath() {return imagePath.get(this.name());}

}
