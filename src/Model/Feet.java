package Model;

/**
 * Created by William on 11/12/2015.
 */
public enum Feet implements Equipment {
    /**First footwear for heroes**/
    WEBBEDTOES(0,0,0,1,0), HIKINGBOOTS(0,0,0,1,0), IMPROVEDEMITTOR(0,0,0,1,0);

    /**Stats for this Feet**/
    int attack;
    int defense;
    int hp;
    int movement;
    int vision;

    /**
     * Constructor for Feet
     * @param a int attack
     * @param d int defense
     * @param h int hp
     * @param m int movement
     * @param v int vision
     */
    Feet(int a, int d, int h, int m, int v) {
        attack = a;
        defense = d;
        hp = h;
        movement = m;
        vision = v;
    }

    /**Getters**/
    @Override
    public int getAttMod() {
        return attack;
    }
    @Override
    public int getDefMod() {
        return defense;
    }
    @Override
    public int getHpMod() {
        return hp;
    }
    @Override
    public int getMovMod() {
        return movement;
    }
    @Override
    public int getVisMod() {
        return vision;
    }
}
