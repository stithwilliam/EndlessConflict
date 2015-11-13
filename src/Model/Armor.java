package Model;

/**
 * Created by William on 11/12/2015.
 */
public enum Armor implements Equipable{
    DAMAGEDSCALES(0,3,0,0,0,0), DAMAGEDKEVLAR(0,3,0,0,0,0), DAMAGEDFRAME(0,3,0,0,0,0),  //hero starting armors
    SQUISHYOOZE(0,2,0,0,0,0), DAMAGEDUNIFORM(0,2,0,0,0,0), DAMAGEDHULL(0,2,0,0,0,0);    //unit starting armors

    int attack;
    int defense;
    int hp;
    int speed;
    int movement;
    int vision;

    Armor(int a, int d, int h, int s, int m, int v) {
        attack = a;
        defense = d;
        hp = h;
        speed = s;
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
    public int getSpdMod() {
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
