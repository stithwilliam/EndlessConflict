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
    private int attack, defense, maxHP, hp, movement, vision, range;

    /**equipment of the fighter**/
    private Equipment weapon, armor, head, feet;

    public Fighter(Placeable p) {
        this(p, 0, 0, false);
    }

    /**
     * Constructor for Fighter
     * @param p Placeable model that this uses
     * @param x int x position
     * @param y int y position
     * @param e is enemy
     */
    public Fighter(Placeable p, int x, int y, boolean e) {
        model = p;
        name = p.getName();
        melee = model.isMelee();
        attack = model.getAtt();
        defense = model.getDef();
        maxHP = model.getHp();
        hp = maxHP;
        movement = model.getMov();
        vision = model.getVis();
        xPos = x;
        yPos = y;
        enemy = e;
        hasAttacked = false;
        hasMoved = false;
        if (melee) {
            range = 1;
        } else {
            range = vision;
        }
        weapon = Weapon.NONE;
        head = Helmet.NONE;
        armor = Armor.NONE;
        feet = Boots.NONE;
    }

    /**
     * Changes the equipment on the player.
     * @param e Equipment being equipped
     * @param equipping if equipping or removing
     */
    public void changeEquipment(Equipment e, boolean equipping) {
        int mod;
        if (equipping) {
            mod = 1;
            equip(e, equipping);
        } else {
            mod = -1;
        }
        attack += e.getAttMod() * mod;
        defense += e.getDefMod() * mod;
        maxHP += e.getHpMod() * mod;
        hp += e.getHpMod() * mod;
        movement += e.getMovMod() * mod;
        vision += e.getVisMod() * mod;
    }

    /**
     * Helper method for changeEquipment
     * Equip/remove e to this
     * @param e Equipment to equip or remove
     * @param equipping if equipping or removing
     */
    private void equip(Equipment e, boolean equipping) {
        Equipment x = e;
        if (!equipping) {
            x = null;
        }
        if (e instanceof Helmet) {
            head = x;
        } else if (e instanceof Armor) {
            armor = x;
        } else if (e instanceof Boots) {
            feet = x;
        } else if (e instanceof Weapon) {
            weapon = x;
        }
    }

    /**Getters**/
    public String getStats() {
        if (enemy) {
            return "Enemy " + name + ": HP - " + hp + "/" + maxHP + ". Atk - " + attack + ". Def - " + defense +
                    ". Mov - " + movement + ". Range - " + range;
        } else {
            return "Ally " + name + ": HP - " + hp + "/" + maxHP + ". Atk - " + attack + ". Def - " + defense +
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
    public int getDef() {return defense;}
    public int getHp() {return hp;}
    public int getMaxHP() {return maxHP;}
    public int getMov() {return movement;}
    public int getVis() {return vision;}
    public int getRange() {return range;}
    public boolean isMelee() {return melee;}
    public boolean isEnemy() {return enemy;}
    public boolean isHero() {return model.isHero();}
    public boolean hasMoved() {return hasMoved;}
    public boolean hasAttacked() {return hasAttacked;}
    public Weapon getWeapon() {return (Weapon) weapon;}
    public Armor getArmor() {return (Armor) armor;}
    public Helmet getHead() {return (Helmet) head;}
    public Boots getFeet() {return (Boots) feet;}
    public String getSkillName() {return model.getSkillName();}
    /**Setters**/
    public void setxPos(int xPos) {this.xPos = xPos;}
    public void setName(String name) {this.name = name;}
    public void setyPos(int yPos) {this.yPos = yPos;}
    public void setAtt(int attack) {this.attack = attack;}
    public void setDef(int defense) {this.defense = defense;}
    public void setHp(int hp) {this.hp = hp;}
    public void setMov(int movement) {this.movement = movement;}
    public void setVis(int vision) {this.vision = vision;}
    public void setRange(int range) {this.range = range;}
    public void setEnemy(boolean enemy) {this.enemy = enemy;}
    public void setHasMoved(boolean h) {hasMoved = h;}
    public void setHasAttacked(boolean h) {hasAttacked = h;}
    public void setWeapon(Weapon weapon) {
        if (this.weapon != null) {
            changeEquipment(this.weapon, false);
        }
        this.weapon = weapon;
        changeEquipment(weapon, true);
    }
    public void setArmor(Armor armor) {
        if (this.armor != null) {
            changeEquipment(this.armor, false);
        }
        this.armor = armor;
        changeEquipment(armor, true);
    }
    public void setHead(Helmet helmet) {
        if (this.head != null) {
            changeEquipment(this.head, false);
        }
        this.head = helmet;
        changeEquipment(helmet, true);
    }
    public void setFeet(Boots boots) {
        if (this.feet != null) {
            changeEquipment(this.feet, false);
        }
        this.feet = boots;
        changeEquipment(boots, true);
    }
}

