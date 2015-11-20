package Model;

/**
 * Created by William on 10/26/2015.
 */
public enum Commander {

    /**3 types of Commander**/
    HUMAN, ROBOT, MUTANT;

    /**
     * Gets the first hero for the Commander
     * @return Hero first hero
     */
    public Hero getHero() {
        switch (this) {
            case HUMAN:
                return Hero.CHAOS;
            case ROBOT:
                return Hero.MODELX;
            case MUTANT:
                return Hero.LIZARDKING;
        }
        return null;
    }

    /**
     * Gets the first unit for the Commander
     * @return Unit first unit
     */
    public Unit getUnit() {
        switch (this) {
            case HUMAN:
                return Unit.RANGER;
            case ROBOT:
                return Unit.SENTRYDRONE;
            case MUTANT:
                return Unit.SLIMEBALL;
        }
        return null;
    }

    /**
     * Gets the commander that is weak against this
     * @return Commander weak against
     */
    public Commander getWeakCommander() {
        switch (this) {
            case HUMAN:
                return ROBOT;
            case ROBOT:
                return MUTANT;
            case MUTANT:
                return HUMAN;
        }
        return null;
    }

    /**
     * Gets the commander that is strong against this
     * @return Commander strong against
     */
    public Commander getStrongCommander() {
        switch (this) {
            case HUMAN:
                return MUTANT;
            case ROBOT:
                return HUMAN;
            case MUTANT:
                return ROBOT;
        }
        return null;
    }
}
