package Model;

/**
 * Fighter class, used almost everywhere
 */
public class Fighter {

    /**Name of this Fighter**/
    String name, description;

    /**model type of the fighter**/
    private Placeable model;

    /**position**/
    private int xPos, yPos;

    /**properties of the fighter**/
    private boolean enemy, hasMoved, hasAttacked;

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
        description = p.getDescription();
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

    public String details() {
        return (name + ". xPos: " + xPos + ". yPos: " + yPos);
    }

    /**Getters**/
    public String toString() { return (name);}
    public String imagePath() {return model.imagePath();}
    public String getName() {return name;}
    public String getRace() {return model.getRace();}
    public String getDescription() {return description;}
    public Placeable getModel() {return model;}
    public int getxPos() {return xPos;}
    public int getyPos() {return yPos;}
    public int getStars() {return model.getStars();}
    public int getAtt() {return attack;}
    public int getHp() {return hp;}
    public int getMaxHP() {return maxHP;}
    public int getMov() {return movement;}
    public int getRange() {
        if (model == Commander.CHAOS) {
            return range * 2;
        } else {
            return range;
        }
    }
    public boolean isEnemy() {return enemy;}
    public boolean isCommander() {return model.isCommander();}
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

