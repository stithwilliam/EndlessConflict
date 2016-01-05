package Model;

/**
 * Created by William on 11/12/2015.
 */
public enum Weapon implements Equipment {
    /**Null Weapon**/
    NONE(0,0,0,0,0,0,"[None]"),

    /**Hero starting weapons**/
    DIRTYCLAW(3,0,0,0,0,1,"Dirty Claw"), LASERPISTOL(3,0,0,0,0,4,"Laser Pistol"), LIGHTMACHINEGUN(3,0,0,0,0,4,"Light Machine Gun"),

    /**Unit starting weapons**/
    DIRTYOOZE(2,0,0,0,0,3,"Dirty Ooze"), SHODDYLASERRIFLE(2,0,0,0,0,3,"Shoddy Laser Rifle"), SMALLTURRET(2,0,0,0,0,3,"Small Turret");

    /**Name of the Weapon**/
    private String name;

    /**Properties of the Weapon**/
    private int attMod;
    private int defMod;
    private int hpMod;
    private int movMod;
    private int visMod;
    private int range;

    /**
     * Constructor
     * @param a int attack modifier
     * @param d int defense modifier
     * @param h int hp modifier
     * @param m int movement modifier
     * @param v int vision modifier
     * @param r int range modifier
     * @param name String name
     */
    Weapon(int a, int d, int h, int m, int v, int r, String name) {
        attMod = a;
        defMod = d;
        hpMod = h;
        movMod = m;
        visMod = v;
        range = r;
        this.name = name;
    }

    /**Getters**/
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
