package Model;

/**
 * Created by William on 10/27/2015.
 */
public class Fighter {

    String name;
    private Placeable model;
    private int xPos, yPos;
    private boolean melee, enemy;
    private int attack, defense, maxHP, hp, speed, movement, vision, range;
    private Equipable weapon, armor, head, feet;

    public Fighter(Placeable p, int x, int y, boolean e) {
        model = p;
        name = p.getName();
        melee = model.isMelee();
        attack = model.getAtt();
        defense = model.getDef();
        maxHP = model.getHp();
        hp = maxHP;
        speed = model.getSpd();
        movement = model.getMov();
        vision = model.getVis();
        xPos = x;
        yPos = y;
        enemy = e;
        if (melee) {
            range = 1;
        } else {
            range = vision;
        }
    }

    private void changeStats(Equipable e, boolean equipping) {
        int mod;
        if (equipping) {
            mod = 1;
        } else {
            mod = -1;
        }
        attack += e.getAttMod() * mod;
        defense += e.getDefMod() * mod;
        maxHP += e.getHpMod() * mod;
        hp += e.getHpMod() * mod;
        speed += e.getSpdMod() * mod;
        movement += e.getMovMod() * mod;
        vision += e.getVisMod() * mod;
    }

    //Getters
    public String getStats() {
        if (enemy) {
            return "Enemy " + name + ": HP - " + hp + "/" + maxHP + ". Atk - " + attack + ". Def - " + defense +
                    ". Spd - " + speed + ". Mov - " + movement;
        } else {
            return "Ally " + name + ": HP - " + hp + "/" + maxHP + ". Atk - " + attack + ". Def - " + defense +
                    ". Spd - " + speed + ". Mov - " + movement;
        }
    }
    public String imagePath() {return model.imagePath();}
    public String getName() {return name;}
    public Placeable getModel() {return model;}
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
    public boolean isEnemy() {return enemy;}
    public Weapon getWeapon() {return (Weapon) weapon;}
    public Armor getArmor() {return (Armor) armor;}
    public Head getHead() {return (Head) head;}
    public Feet getFeet() {return (Feet) feet;}
    //Setters
    public void setxPos(int xPos) {this.xPos = xPos;}
    public void setName(String name) {this.name = name;}
    public void setyPos(int yPos) {this.yPos = yPos;}
    public void setAtt(int attack) {this.attack = attack;}
    public void setDef(int defense) {this.defense = defense;}
    public void setHp(int hp) {this.hp = hp;}
    public void setSpd(int speed) {this.speed = speed;}
    public void setMov(int movement) {this.movement = movement;}
    public void setVis(int vision) {this.vision = vision;}
    public void setRange(int range) {this.range = range;}
    public void setMelee(boolean melee) {this.melee = melee;}
    public void setEnemy(boolean enemy) {this.enemy = enemy;}
    public void setWeapon(Weapon weapon) {
        if (this.weapon != null) {
            changeStats(this.weapon, false);
        }
        this.weapon = weapon;
        changeStats(weapon, true);
    }
    public void setArmor(Armor armor) {
        if (this.armor != null) {
            changeStats(this.armor, false);
        }
        this.armor = armor;
        changeStats(armor, true);
    }
    public void setHead(Head head) {
        if (this.head != null) {
            changeStats(this.head, false);
        }
        this.head = head;
        changeStats(head, true);
    }
    public void setFeet(Feet feet) {
        if (this.feet != null) {
            changeStats(this.feet, false);
        }
        this.feet = feet;
        changeStats(feet, true);
    }
}

