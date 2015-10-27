package Model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by William on 10/27/2015.
 */
public enum Hero implements Placeable {
    LIZARDKING(30, 30, 100, 20, 4, 0, true, false), MODELX(20, 40, 100, 20, 4, 0, false, false),
    CHAOS(40, 20, 100, 20, 4, 0, false, true);

    private int attack, defense, hp, speed, movement, vision;
    private boolean melee, flying;
    private Map<String, String> imagePath = new HashMap<>();

    //Constructor
    Hero(int a, int d, int h, int s, int m, int v, boolean me, boolean f) {
        attack = a;
        defense = d;
        hp = h;
        speed = s;
        movement = m;
        vision = v;
        melee = me;
        flying = f;
        imagePath.put("LIZARDKING", "/View/Graphics/lizardKing.png");
        imagePath.put("MODELX", "/View/Graphics/modelX.png");
        imagePath.put("CHAOS", "/View/Graphics/commanderChaos.png");
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
    public String imagePath() { return imagePath.get(this.name());}
    public int getAtt() {return attack;}
    public int getDef() {return defense;}
    public int getHp() {return hp;}
    public int getSpd() {return speed;}
    public int getMov() {return movement;}
    public int getVis() {return vision;}
    public boolean isMelee() {return melee;}
    public boolean isFlying() {return flying;}
}
