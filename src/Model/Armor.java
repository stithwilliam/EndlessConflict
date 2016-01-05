package Model;

/**
 * Created by William on 11/12/2015.
 */
public enum Armor implements Equipment {
    /**Null Armor**/
    NONE(0,0,0,0,0,0,"[None]"),

    /**Hero Starting Armors**/
    DAMAGEDSCALES(0,3,0,0,0,0,"Damaged Scales"), DAMAGEDKEVLAR(0,3,0,0,0,0,"Damaged Kevlar"), DAMAGEDFRAME(0,3,0,0,0,0,"Damaged Frame"),

    /**Unit Starting Armors**/
    SQUISHYOOZE(0,2,0,0,0,0,"Squishy Ooze"), DAMAGEDUNIFORM(0,2,0,0,0,0,"Damaged Uniform"), DAMAGEDHULL(0,2,0,0,0,0,"Damaged Hull");


    /**Armor stats**/
    private int attMod;
    private int defMod;
    private int hpMod;
    private int movMod;
    private int visMod;
    private int range;
    private String name;

    /**
     * Constructor for armor
     * @param a int attack
     * @param d int defense
     * @param h int hp
     * @param m int movement
     * @param v int vision
     */
    Armor(int a, int d, int h, int m, int v, int r, String n) {
        attMod = a;
        defMod = d;
        hpMod = h;
        movMod = m;
        visMod = v;
        range = r;
        name = n;
    }

    /**Getters**/
    @Override
    public String toString() {
        return name;
    }
    @Override
    public int getAttMod() {
        return attMod;
    }
    @Override
    public int getDefMod() {
        return defMod;
    }
    @Override
    public int getHpMod() {
        return hpMod;
    }
    @Override
    public int getMovMod() {
        return movMod;
    }
    @Override
    public int getVisMod() {
        return visMod;
    }
    @Override
    public int getRange() { return range;}
}
