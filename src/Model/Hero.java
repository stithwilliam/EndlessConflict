package Model;

import Controller.MapController;
import Controller.MasterController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by William on 10/27/2015.
 */
public enum Hero implements Placeable {
    LIZARDKING(15,15,50,4,4, true, "Lizard King"), MODELX(12,18,40,4,4, false, "Model X"),
    CHAOS(18,12,40,4,4, false, "Commander Chaos");

    private String name;
    private int attack, defense, hp, movement, vision, level;
    private boolean melee;
    private Map<String, String> imagePath = new HashMap<>();

    //Constructor
    Hero(int a, int d, int h, int m, int v, boolean me, String n) {
        name = n;
        attack = a;
        defense = d;
        hp = h;
        movement = m;
        vision = v;
        level = 1;
        melee = me;
        imagePath.put("LIZARDKING", "/View/Graphics/Placeable/lizardKing.png");
        imagePath.put("MODELX", "/View/Graphics/Placeable/modelXresting.gif");
        imagePath.put("CHAOS", "/View/Graphics/Placeable/commanderChaos.png");
    }

    public void showSkill() {
        MapController mapCtr = MasterController.getInstance().getMapController();
        switch (this) {
            case LIZARDKING:
                mapCtr.showLizardJump();
                break;
            case MODELX:
                mapCtr.showTractorBeam();
                break;
            case CHAOS:
                mapCtr.showGrenade();
                break;
        }
    }

    public void doSkill() {

    }

    //Bio info for each hero.
    public String getBio() {
        if (this == LIZARDKING) {
            return "Created by infusing human DNA with that of a T-Rex, the Lizard King is the undeniable ruler of " +
                    "his battalion. Through fear and intimidation the Lizard King inspires obedience and fear from " +
                    "his allies and enemies alike.";
        } if (this == MODELX) {
            return "The Model X represents the epitome of The Singularity's battle tech. Even when disconnected " +
                    "its commander, the Model X is fully capable of leading an advanced robotic battalion against " +
                    "any kind of foe";
        } if (this == CHAOS) {
            return "Commander Chaos climbed the ranks through his signature battle style featuring high risk ops and " +
                    "explosives. This deviation from traditional Human fighting style makes Chaos aptly suited to " +
                    "fight on his own as his enemies are not used to his arsenal of offensive capabilities.";
        }
        return "";
    }

    //Getters
    public String imagePath() {return imagePath.get(this.name());}
    public String getName() {return name;}
    public int getAtt() {return attack;}
    public int getDef() {return defense;}
    public int getHp() {return hp;}
    public int getMov() {return movement;}
    public int getVis() {return vision;}
    public int getLevel() {return level;}
    public boolean isMelee() {return melee;}
}
