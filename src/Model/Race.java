package Model;

/**
 * Enum for the race that the user has chosen
 */
public enum Race {

    /**3 types of Races**/
    HUMAN, ROBOT, MUTANT;

    /**
     * Gets the first hero for the Race
     * @return Commander first hero
     */
    public Commander getCommander() {
        switch (this) {
            case HUMAN:
                return Commander.CHAOS;
            case ROBOT:
                return Commander.MODEL0;
            case MUTANT:
                return Commander.LIZARDKING;
        }
        return null;
    }

    /**
     * Gets the first unit for the Race
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
     * Gets the race that is weak against this
     * @return Race weak against
     */
    public Race getWeakRace() {
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
     * Gets the race that is strong against this
     * @return Race strong against
     */
    public Race getStrongRace() {
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
