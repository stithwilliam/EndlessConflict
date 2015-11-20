package Model;

/**
 * Created by William on 11/12/2015.
 */
public enum Head implements Equipment {
    /**First headgear for Heroes**/
    RAZORTEETH(2,0,0,0,0), GOGGLES(0,2,0,0,0), TARGETINGCHIP(0,0,0,0,1);

    /**Stats for this Head**/
    int attack;
    int defense;
    int hp;
    int movement;
    int vision;

    /**
     * Constructor for Head
     * @param a int attack
     * @param d int defense
     * @param h int hp
     * @param m int movement
     * @param v int vision
     */
    Head(int a, int d, int h, int m, int v) {
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
