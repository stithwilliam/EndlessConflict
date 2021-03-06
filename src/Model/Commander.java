package Model;

import java.util.HashMap;
import java.util.Map;

/**
 * The commanders in the game.
 */
public enum Commander implements Placeable {

    /**First 3 commanders**/
    LIZARDKING(12,12,6,1, "Lizard King"), MODEL0(6,12,4,4, "Model 0"),
    CHAOS(12,6,4,4,"Chaos");

    /**Name of commander**/
    private String name;

    /**Stats of commander**/
    private int attack, hp, movement, range;

    /**Map of imagePaths of commanders**/
    private Map<String, String> imagePath = new HashMap<>();

    /**
     * Constructor for commander
     * @param a int attack
     * @param h int hp
     * @param m int movement
     * @param r int range
     * @param n String name
     */
    Commander(int a, int h, int m, int r, String n) {
        name = n;
        attack = a;
        hp = h;
        movement = m;
        range = r;
        imagePath.put("LIZARDKING", "/View/Graphics/Fighter/lizardKing.png");
        imagePath.put("MODEL0", "/View/Graphics/Fighter/modelXresting.gif");
        imagePath.put("CHAOS", "/View/Graphics/Fighter/commanderChaos.png");
    }

    /**
     * The description of the commander.
     * @return String description
     */
    public String getDescription() {
        switch (this) {
            case MODEL0:
                return ("Can attack allies to give health by way of a temporary shield.");
            case LIZARDKING:
                return ("Can jump over and onto obstacles on the map.");
            case CHAOS:
                return ("Range is doubled if Chaos has not moved this turn.");
            default:
                return ("No description");
        }
    }

    /**
     * The race of the commander
     * @return String
     */
    public String getRace() {
        switch (this) {
            case MODEL0:
                return ("Robot");
            case LIZARDKING:
                return ("Mutant");
            case CHAOS:
                return ("Human");
            default:
                return ("None");
        }
    }

    public int getStars() {
        return 3;
    }

    public Placeable getUpgrade() {
        return null;
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
//        } if (this == MODEL0) {
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
    public boolean isCommander() {return true;}
}
