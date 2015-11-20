package Model;

/**
 * Created by William on 11/12/2015.
 */
public enum Armor implements Equipment {
    /**Hero Starting Armors**/
    DAMAGEDSCALES(0,3,0,0,0), DAMAGEDKEVLAR(0,3,0,0,0), DAMAGEDFRAME(0,3,0,0,0),

    /**Unit Starting Armors**/
    SQUISHYOOZE(0,2,0,0,0), DAMAGEDUNIFORM(0,2,0,0,0), DAMAGEDHULL(0,2,0,0,0);

    /**Armor stats**/
    int attack;
    int defense;
    int hp;
    int movement;
    int vision;

    /**
     * Constructor for armor
     * @param a int attack
     * @param d int defense
     * @param h int hp
     * @param m int movement
     * @param v int vision
     */
    Armor(int a, int d, int h, int m, int v) {
        attack = a;
        defense = d;
        hp = h;
        movement = m;
        vision = v;
    }

    /**Getters**/
    @Override
    public int getAttMod() {
        return 0;
    }
    @Override
    public int getDefMod() {
        return 0;
    }
    @Override
    public int getHpMod() {
        return 0;
    }
    @Override
    public int getMovMod() {
        return 0;
    }
    @Override
    public int getVisMod() {
        return 0;
    }
}
