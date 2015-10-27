package Model;

import java.util.*;

/**
 * Created by William on 10/26/2015.
 */
public enum Unit implements Placeable {
    ;

    boolean melee, flying;
    int attack, defense, hp, speed, movement, vision;
    private java.util.Map<String, String> imagePath = new HashMap<>();

    Unit(int a, int d, int h, int s, int m, int v, boolean me, boolean f) {
        attack = a;
        defense = d;
        hp = h;
        speed = s;
        movement = m;
        vision = v;
        melee = me;
        flying = f;
        imagePath.put("LIZARDKING", "/View/Graphics/lizardKing.png");
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
