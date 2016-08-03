package Model;

import java.util.*;

/**
 * The different units in the game
 */
public enum Unit implements Placeable {
    /**First Units**/
    SENTRYDRONE(4,8,3,3,"Sentry Drone"), SENTRYDRONEV2(5,9,3,3,"Sentry Drone V2"), SENTRYDRONEV3(6,10,3,3,"Sentry Drove V3"),   //Robot tier 1
    SLIMEBALL(6,6,3,3,"Slime Ball"), BIGSLIMEBALL(7,7,3,3,"Big Slime Ball"), HUGESLIMEBALL(8,8,3,3,"Huge Slime Ball"),          //Mutant tier 1
    RANGER(8,4,3,3,"Human Ranger"), VETERANRANGER(9,5,3,3,"Veteran Ranger"), ELITERANGER(10,6,3,3,"Elite Ranger");              //Human tier 1

    /**Name of this Unit**/
    String name;

    /**Stats of this Unit**/
    int attack, hp, movement, range;

    /**Map of all imagePaths**/
    private java.util.Map<String, String> imagePath = new HashMap<>();

    /**
     * Constructor for unit
     * @param a int attack
     * @param h int hp
     * @param m int movement
     * @param r int range
     * @param n String name
     */
    Unit(int a, int h, int m, int r, String n) {
        name = n;
        attack = a;
        hp = h;
        movement = m;
        range = r;
        //images of Units
        imagePath.put("SENTRYDRONE", "/View/Graphics/Fighter/sentryDroneResting.gif");
        imagePath.put("SENTRYDRONEV2", "/View/Graphics/Fighter/sentryDroneV2.gif");
        imagePath.put("SENTRYDRONEV3", "/View/Graphics/Fighter/sentryDroneV3.gif");
        imagePath.put("SLIMEBALL", "/View/Graphics/Fighter/slimeBallResting.gif");
        imagePath.put("BIGSLIMEBALL", "/View/Graphics/Fighter/slimeBallResting.gif");
        imagePath.put("HUGESLIMEBALL", "/View/Graphics/Fighter/slimeBallResting.gif");
        imagePath.put("RANGER", "/View/Graphics/Fighter/humanRanger.png");
        imagePath.put("VETERANRANGER", "/View/Graphics/Fighter/humanRanger.png");
        imagePath.put("ELITERANGER", "/View/Graphics/Fighter/humanRanger.png");

    }

    /**
     * The description of this unit
     * @return String description
     */
    public String getDescription() {
        switch (this) {
            case SENTRYDRONE:
            case SENTRYDRONEV2:
            case SENTRYDRONEV3:
                return ("10% chance for this unit to negate all damage from an attack.");
            case SLIMEBALL:
            case BIGSLIMEBALL:
            case HUGESLIMEBALL:
                return ("Deals double damage when at melee range OR 10% to spawn another slime ball on death.");
            case RANGER:
            case VETERANRANGER:
            case ELITERANGER:
                return ("10% chance to deal double damage when this unit attacks.");
            default:
                return ("No description");
        }
    }

    /**
     * The race of this unit
     * @return String race
     */
    public String getRace() {
        switch (this) {
            case SENTRYDRONE:
            case SENTRYDRONEV2:
            case SENTRYDRONEV3:
                return ("Robot");
            case SLIMEBALL:
            case BIGSLIMEBALL:
            case HUGESLIMEBALL:
                return ("Mutant");
            case RANGER:
            case VETERANRANGER:
            case ELITERANGER:
                return ("Human");
            default:
                return ("None");
        }
    }

    public int getStars() {
        switch (this) {
            case SENTRYDRONE:
            case RANGER:
            case SLIMEBALL:
                return 1;
            case SENTRYDRONEV2:
            case VETERANRANGER:
            case BIGSLIMEBALL:
                return 2;
            case SENTRYDRONEV3:
            case ELITERANGER:
            case HUGESLIMEBALL:
                return 3;
            default:
                return 0;
        }
    }

    public Placeable getUpgrade() {
        switch (this) {
            case SENTRYDRONEV3:
            case ELITERANGER:
            case HUGESLIMEBALL:
                return null;
            case SLIMEBALL:
                return BIGSLIMEBALL;
            case BIGSLIMEBALL:
                return HUGESLIMEBALL;
            case RANGER:
                return VETERANRANGER;
            case VETERANRANGER:
                return ELITERANGER;
            case SENTRYDRONE:
                return SENTRYDRONEV2;
            case SENTRYDRONEV2:
                return SENTRYDRONEV3;
            default:
                return null;
        }
    }

    /**Getters**/
    public String imagePath() { return imagePath.get(this.name());}
    public String getName() {return name;}
    public int getAtt() {return attack;}
    public int getHp() {return hp;}
    public int getMov() {return movement;}
    public int getRange() {return range;}
    public boolean isCommander() {return false;}
}
