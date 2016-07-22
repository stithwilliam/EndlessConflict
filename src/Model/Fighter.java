package Model;

/**
 * Created by William on 10/27/2015.
 */
public class Fighter {

    /**Name of this Fighter**/
    String name;

    /**model type of the fighter**/
    private Placeable model;

    /**position**/
    private int xPos, yPos;

    /**properties of the fighter**/
    private boolean melee, enemy, hasMoved, hasAttacked;

    /**stats of the fighter**/
    private int attack, maxHP, hp, movement, range;

    public Fighter(Placeable p) {
        this(p, 0, 0, false);
    }

    /**
     * Constructor for Fighter
     * @param p Fighter model that this uses
     * @param x int x position
     * @param y int y position
     * @param e is enemy
     */
    public Fighter(Placeable p, int x, int y, boolean e) {
        model = p;
        name = p.getName();
        attack = model.getAtt();
        maxHP = model.getHp();
        hp = maxHP;
        movement = model.getMov();
        range = model.getRange();
        xPos = x;
        yPos = y;
        enemy = e;
        hasAttacked = false;
        hasMoved = false;
    }

    /**Getters**/
    public String getStats() {
        if (enemy) {
            return "Enemy " + name + ": HP - " + hp + "/" + maxHP + ". Atk - " + attack +
                    ". Mov - " + movement + ". Range - " + range;
        } else {
            return "Ally " + name + ": HP - " + hp + "/" + maxHP + ". Atk - " + attack +
                    ". Mov - " + movement + ". Range - " + range;
        }
    }
    public String toString() { return (name);}
    public String imagePath() {return model.imagePath();}
    public String getName() {return name;}
    public Placeable getModel() {return model;}
    public int getxPos() {return xPos;}
    public int getyPos() {return yPos;}
    public int getAtt() {return attack;}
    public int getHp() {return hp;}
    public int getMaxHP() {return maxHP;}
    public int getMov() {return movement;}
    public int getRange() {return range;}
    public boolean isMelee() {return melee;}
    public boolean isEnemy() {return enemy;}
    public boolean isHero() {return model.isHero();}
    public boolean hasMoved() {return hasMoved;}
    public boolean hasAttacked() {return hasAttacked;}

    /**Setters**/
    public void setxPos(int xPos) {this.xPos = xPos;}
    public void setName(String name) {this.name = name;}
    public void setyPos(int yPos) {this.yPos = yPos;}
    public void setAtt(int attack) {this.attack = attack;}
    public void setHp(int hp) {this.hp = hp;}
    public void setMov(int movement) {this.movement = movement;}
    public void setRange(int range) {this.range = range;}
    public void setEnemy(boolean enemy) {this.enemy = enemy;}
    public void setHasMoved(boolean h) {hasMoved = h;}
    public void setHasAttacked(boolean h) {hasAttacked = h;}
}

