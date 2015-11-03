package Model;

/**
 * Created by William on 10/27/2015.
 */
public class Fighter {

    String name;
    private Placeable model;
    private int xPos, yPos;
    private boolean melee, flying, enemy;
    private int attack, defense, hp, speed, movement, vision, range;

    public Fighter(Placeable p, int x, int y, boolean e) {
        model = p;
        name = p.getName();
        melee = model.isMelee();
        flying = model.isFlying();
        attack = model.getAtt();
        defense = model.getDef();
        hp = model.getHp();
        speed = model.getSpd();
        movement = model.getMov();
        vision = model.getVis();
        range = 1;
        xPos = x;
        yPos = y;
        enemy = e;
    }



    //Getters
    public String imagePath() {return model.imagePath();}
    public String getName() {return name;}
    public int getxPos() {return xPos;}
    public int getyPos() {return yPos;}
    public int getAtt() {return attack;}
    public int getDef() {return defense;}
    public int getHp() {return hp;}
    public int getSpd() {return speed;}
    public int getMov() {return movement;}
    public int getVis() {return vision;}
    public int getRange() {return range;}
    public boolean isMelee() {return melee;}
    public boolean isFlying() {return flying;}
    public boolean isEnemy() {return enemy;}
    //Setters
    public void setxPos(int xPos) {this.xPos = xPos;}
    public void setyPos(int yPos) {this.yPos = yPos;}
    public void setAtt(int attack) {this.attack = attack;}
    public void setDef(int defense) {this.defense = defense;}
    public void setHp(int hp) {this.hp = hp;}
    public void setSpd(int speed) {this.speed = speed;}
    public void setMov(int movement) {this.movement = movement;}
    public void setVis(int vision) {this.vision = vision;}
    public void setRange(int range) {this.range = range;}
    public void setMelee(boolean melee) {this.melee = melee;}
    public void setFlying(boolean flying) {this.flying = flying;}
    public void setEnemy(boolean enemy) {this.enemy = enemy;}
}

