package Model;

/**
 * Created by William on 11/12/2015.
 */
public enum Head implements Equipment {
    RAZORTEETH(2,0,0,0,0), GOGGLES(0,2,0,0,0), TARGETINGCHIP(0,0,0,0,1);  //first headgear for Heroes

    int attack;
    int defense;
    int hp;
    int movement;
    int vision;

    Head(int a, int d, int h, int m, int v) {
        attack = a;
        defense = d;
        hp = h;
        movement = m;
        vision = v;
    }

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
