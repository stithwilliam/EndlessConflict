package Model;

import java.util.*;

/**
 * Created by William on 10/26/2015.
 */
public enum Unit implements Placeable {
    /**First Units**/
    SENTRYDRONE(12,12,20,3,3, false, "Sentry Drone"), SLIMEBALL(12,12,20,3,3, false, "Slime Ball"),
    RANGER(12,12,20,3,3, false, "Human Ranger");

    /**Name of this Unit**/
    String name;

    /**If this is melee**/
    boolean melee;

    /**Stats of this Unit**/
    int attack, defense, hp, speed, movement, vision;

    /**Map of all imagePaths**/
    private java.util.Map<String, String> imagePath = new HashMap<>();

    /**
     * Constructor for unit
     * @param a int attack
     * @param d int defense
     * @param h int hp
     * @param m int movement
     * @param v int vision
     * @param me boolean isMelee
     * @param n String name
     */
    Unit(int a, int d, int h, int m, int v, boolean me, String n) {
        name = n;
        attack = a;
        defense = d;
        hp = h;
        movement = m;
        vision = v;
        melee = me;
        //images of Units
        imagePath.put("SENTRYDRONE", "/View/Graphics/Placeable/sentryDroneResting.gif");
        imagePath.put("SLIMEBALL", "/View/Graphics/Placeable/slimeBallResting.gif");
        imagePath.put("RANGER", "/View/Graphics/Placeable/humanRanger.png");
    }

    /**Getters**/
    public String imagePath() { return imagePath.get(this.name());}
    public String getName() {return name;}
    public int getAtt() {return attack;}
    public int getDef() {return defense;}
    public int getHp() {return hp;}
    public int getMov() {return movement;}
    public int getVis() {return vision;}
    public boolean isMelee() {return melee;}
}
