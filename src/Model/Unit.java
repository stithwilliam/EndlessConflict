package Model;

import java.util.*;

/**
 * Created by William on 10/26/2015.
 */
public enum Unit implements Placeable {
    /**First Units**/
    SENTRYDRONE(4,8,3,3,"Sentry Drone"), SLIMEBALL(6,6,4,3,"Slime Ball"),
    RANGER(8,4,3,3,"Human Ranger");

    /**Name of this Unit**/
    String name;

    /**Stats of this Unit**/
    int attack, hp, movement, range;

    /**Map of all imagePaths**/
    private java.util.Map<String, String> imagePath = new HashMap<>();

    /**
     * Constructor for unit
     * @param a int attack
     * @param h int hp
     * @param m int movement
     * @param r int range
     * @param n String name
     */
    Unit(int a, int h, int m, int r, String n) {
        name = n;
        attack = a;
        hp = h;
        movement = m;
        range = r;
        //images of Units
        imagePath.put("SENTRYDRONE", "/View/Graphics/Fighter/sentryDroneResting.gif");
        imagePath.put("SLIMEBALL", "/View/Graphics/Fighter/slimeBallResting.gif");
        imagePath.put("RANGER", "/View/Graphics/Fighter/humanRanger.png");
    }

    /**Getters**/
    public String imagePath() { return imagePath.get(this.name());}
    public String getName() {return name;}
    public int getAtt() {return attack;}
    public int getHp() {return hp;}
    public int getMov() {return movement;}
    public int getRange() {return range;}
    public boolean isHero() {return false;}
}
