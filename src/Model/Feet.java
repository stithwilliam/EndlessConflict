package Model;

/**
 * Created by William on 11/12/2015.
 */
public enum Feet implements Equipment {
    /**Null Feet**/
    NONE(0,0,0,0,0,0,"None"),

    /**First footwear for heroes**/
    WEBBEDTOES(0,0,0,1,0,0,"Webbed Toes"), HIKINGBOOTS(0,0,0,1,0,0,"Hiking Boots"), BASICEMITTOR(0,0,0,1,0,0,"Basic Emittor");

    /**Stats for this Feet**/
    private int attMod;
    private int defMod;
    private int hpMod;
    private int movMod;
    private int visMod;
    private int range;
    private String name;

    /**
     * Constructor for Feet
     * @param a int attack
     * @param d int defense
     * @param h int hp
     * @param m int movement
     * @param v int vision
     */
    Feet(int a, int d, int h, int m, int v, int r, String n) {
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
    public int getRange() {
        return range;
    }
}
