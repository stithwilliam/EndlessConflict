package Model;

/**
 * Created by William on 11/12/2015.
 */
public enum Helmet implements Equipment {
    /**Null Headgear**/
    NONE(0,0,0,0,0,0,"[None]"),

    /**First headgear for Heroes**/
    RAZORTEETH(2,0,0,0,0,0,"Razorteeth"), GOGGLES(0,2,0,0,0,0,"Goggles"), TARGETINGCHIP(0,0,0,0,1,0,"Targeting Chip");

    /**Name of this Headgear**/
    private String name;

    /**Stats for this Headgear**/
    private int attMod;
    private int defMod;
    private int hpMod;
    private int movMod;
    private int visMod;
    private int range;

    /**
     * Constructor for Helmet
     * @param a int attack
     * @param d int defense
     * @param h int hp
     * @param m int movement
     * @param v int vision
     */
    Helmet(int a, int d, int h, int m, int v, int r, String n) {
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
        return 0;
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
