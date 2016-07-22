package Model;

import Controller.BattleController;
import Controller.MasterController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by William on 10/27/2015.
 */
public enum Hero implements Placeable {
    /**First 3 heroes**/
    LIZARDKING(12,12,6,1, "Lizard King"), MODELX(6,12,4,4, "Model X"),
    CHAOS(12,6,4,4,"Chaos");

    /**Name of hero**/
    private String name;

    /**Stats of hero**/
    private int attack, hp, movement, range;

    /**Map of imagePaths of heroes**/
    private Map<String, String> imagePath = new HashMap<>();

    /**
     * Constructor for hero
     * @param a int attack
     * @param h int hp
     * @param m int movement
     * @param r int range
     * @param n String name
     */
    Hero(int a, int h, int m, int r, String n) {
        name = n;
        attack = a;
        hp = h;
        movement = m;
        range = r;
        imagePath.put("LIZARDKING", "/View/Graphics/Fighter/lizardKing.png");
        imagePath.put("MODELX", "/View/Graphics/Fighter/modelXresting.gif");
        imagePath.put("CHAOS", "/View/Graphics/Fighter/commanderChaos.png");
    }

//    /**
//     * Bio info for each hero.
//     * TODO: Use this in game somewhere
//     * @return
//     */
//    public String getBio() {
//        if (this == LIZARDKING) {
//            return "Created by infusing human DNA with that of a T-Rex, the Lizard King is the undeniable ruler of " +
//                    "his battalion. Through fear and intimidation the Lizard King inspires obedience and fear from " +
//                    "his allies and enemies alike.";
//        } if (this == MODELX) {
//            return "The Model X represents the epitome of The Singularity's battle tech. Even when disconnected " +
//                    "its commander, the Model X is fully capable of leading an advanced robotic battalion against " +
//                    "any kind of foe";
//        } if (this == CHAOS) {
//            return "Commander Chaos climbed the ranks through his signature battle style featuring high risk ops and " +
//                    "explosives. This deviation from traditional Human fighting style makes Chaos aptly suited to " +
//                    "fight on his own as his enemies are not used to his arsenal of offensive capabilities.";
//        }
//        return "";
//    }

    /**Getters**/
    public String imagePath() {return imagePath.get(this.name());}
    public String getName() {return name;}
    public int getAtt() {return attack;}
    public int getHp() {return hp;}
    public int getMov() {return movement;}
    public int getRange() {return range;}
    public boolean isHero() {return true;}
}
