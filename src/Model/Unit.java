package Model;

import java.util.*;

/**
 * Created by William on 10/26/2015.
 */
public enum Unit implements Placeable {
    SENTRYDRONE(10, 10, 20, 10, 3, 6, false, true, "Sentry Drone"), SLIMEBALL(10, 10, 20, 10, 3, 3, true, false, "Slime Ball"),
    RANGER(10, 10, 20, 10, 3, 3, false, false, "Human Ranger");

    String name;
    boolean melee, flying;
    int attack, defense, hp, speed, movement, vision;
    private java.util.Map<String, String> imagePath = new HashMap<>();

    Unit(int a, int d, int h, int s, int m, int v, boolean me, boolean f, String n) {
        name = n;
        attack = a;
        defense = d;
        hp = h;
        speed = s;
        movement = m;
        vision = v;
        melee = me;
        flying = f;
        //images of Units
        imagePath.put("SENTRYDRONE", "/View/Graphics/Placeable/sentryDrone.png");
        imagePath.put("SLIMEBALL", "/View/Graphics/Placeable/slimeBall.png");
        imagePath.put("RANGER", "/View/Graphics/Placeable/humanRanger.png");
    }

    //Getters
    public String imagePath() { return imagePath.get(this.name());}
    public String getName() {return name;}
    public int getAtt() {return attack;}
    public int getDef() {return defense;}
    public int getHp() {return hp;}
    public int getSpd() {return speed;}
    public int getMov() {return movement;}
    public int getVis() {return vision;}
    public boolean isMelee() {return melee;}
    public boolean isFlying() {return flying;}
}
