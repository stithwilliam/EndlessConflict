package Model;

/**
 * Created by William on 10/26/2015.
 */
public enum Commander {

    HUMAN, ROBOT, MUTANT;

    //returns the first hero of the commander
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

    //returns the basic unit of the commander
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

    //returns the commander that is weak against this one
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

    //returns the Commander that is strong against this one
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
