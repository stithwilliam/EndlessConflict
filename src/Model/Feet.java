package Model;

/**
 * Created by William on 11/12/2015.
 */
public enum Feet implements Equipment {
    WEBBEDTOES(0,0,0,0,1,0), HIKINGBOOTS(0,0,0,0,1,0), IMPROVEDEMITTOR(0,0,0,0,1,0);  //first footwear for heroes

    int attack;
    int defense;
    int hp;
    int speed;
    int movement;
    int vision;

    Feet(int a, int d, int h, int s, int m, int v) {
        attack = a;
        defense = d;
        hp = h;
        speed = s;
        movement = m;
        vision = v;
    }

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
    public int getSpdMod() {
        return speed;
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
