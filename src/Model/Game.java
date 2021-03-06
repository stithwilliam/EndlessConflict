package Model;

import Controller.BattleController;
import Controller.MasterController;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Singleton game class that stores all of the user's data, as well as providing functionality
 * for multi-screen events
 */
public class Game {

    /**Commander chosen by the user**/
    private Race race;

    /**Fighters in the user's army**/
    private LinkedList<Fighter> army;

    /**Fighters in the user's collection**/
    private LinkedList<Fighter> collection;

    /**The current map being played on**/
    private Map map;

    /**the AI object used by Game**/
    private AI comp;

    /**the BattleController in charge of the map screen**/
    private BattleController battleController;

    /**The current level selected**/
    private int levelSelected;

    /**The levels that the user has beaten. 1-5**/
    private LinkedList<Boolean> levelsComplete;

    private Fighter shieldedFighter = null;
    /**
     * Constructor for Game
     */
    public Game() {
        race = null;
        map = null;
        comp = null;
        army = new LinkedList<>();
        collection = new LinkedList<>();
        levelsComplete = new LinkedList<>();
        for (int i = 0; i < 5; i++) {
            if (i == 0) {
                levelsComplete.add(true);
            }
            else {
                levelsComplete.add(false);
            }
        }
        levelSelected = 0;
    }

    /**
     * Called when the config screen is finished.
     * Sets up the preliminary collection and loads the map screen
     */
    public void endConfig() {
        collection.add(new Fighter(race.getCommander()));
        collection.add(new Fighter(race.getUnit()));
        collection.add(new Fighter(race.getUnit()));
        MasterController.getInstance().loadMapScene();
    }

    /**
     * Called when the user makes it past the config screens
     * Creates the Tutorial map and displays it on the screen
     */
    public void startLevel() {
        MasterController.getInstance().setBattleScene();
        map = new Map(getMapType());
        map.placeArmy(army);
        for (Placeable p : getMapType().getFirstRewards()) {
            map.addToRewardList(p);
        }
        comp = new AI(this, map);
        battleController = MasterController.getInstance().getBattleController();
        battleController.constructMap(map);
        battleController.showAlly(map.getFighter());
        battleController.putInFocus(map.getFighter().getxPos(), map.getFighter().getyPos());
        battleController.showEnemy(map.getEnemies().get(0));
    }

    /**
     * Called when a player's turn ends.
     * Calls the AI to do it's turn.
     */
    public void endTurn() {
        LinkedList<Fighter> allies = map.getAllies();
        for (Fighter f : allies) {
            f.setHasAttacked(false);
            f.setHasMoved(false);
        }
        comp.doTurn();
        if (army.size() == 0) {
            MasterController.getInstance().loadMapScene(); //TODO: Go to game over screen
        }
        if (shieldedFighter != null) {
            shieldedFighter.setHp(shieldedFighter.getHp() - new Fighter(Commander.MODEL0).getAtt());
            if (shieldedFighter.getHp() <= 0) {
                shieldedFighter.setHp(1);
            }
            shieldedFighter = null;
        }
        MasterController.getInstance().getBattleController().startTurn();
    }

    /**
     * Called when one fighter attacks another fighter.
     * Damage = (att * 2 - def) + random(-5,5)
     * @param attacker Fighter attacking
     * @param defender Fighter defending
     * @return String of attack summary to put on terminal
     */
    public String attackFighter(Fighter attacker, Fighter defender) {
        int damage = attacker.getAtt();
        defender.setHp(defender.getHp() - damage);
        String s = (attacker.getName() + " dealt " + damage + " damage to " + defender.getName());
        if (defender.getHp() <= 0) {
            s += (" and killed " + defender.getName() + "!");
            killedFighter(defender);
        }
        return s;
    }

    public String shieldFighter(Fighter model0, Fighter ally) {
        int shield = model0.getAtt();
        ally.setHp(ally.getHp() + shield);
        shieldedFighter = ally;
        return (model0.getName() + " shielded " + shield + " damage for " + ally.getName() + " for this turn!");
    }

    /**
     * Called if a fighter dies.
     * @param f Fighter dying
     */
    public void killedFighter(Fighter f) {
        battleController.removeFighter(f);
        if (f.isEnemy()) {
            double rewardChance = getMapType().rewardChance(f);
            Random random = new Random();
            double d = random.nextDouble();
            System.out.println("checking... d = " + d + " and rC = " + rewardChance);
            if (d <= rewardChance) {
                System.out.println("Reward!");
                rewardChest(f.getxPos(), f.getyPos(), f.getModel());
            }
        }
        if (map.getEnemies().size() == 0) {
            //TODO: Make this flashy
            completeLevel();
            MasterController.getInstance().loadMapScene(); //TODO: switch to reward scene when functional

        }
    }

    public void completeLevel() {
        List<Placeable> list = getMapType().getFirstRewards();
        for (Placeable p : list) {
            map.addToRewardList(p);
            addToCollection(new Fighter(p)); //TODO: Move this to reward
        }
        levelsComplete.set(levelSelected, true);

    }

    /**
     * Spawns a reward chest at position (x,y).
     * @param x xPos
     * @param y yPos
     * @param model type of reward
     */
    private void rewardChest(int x, int y, Placeable model) {
        map.addToRewardList(model);
        addToCollection(new Fighter(model)); //TODO: move this to reward
        battleController.showRewardChest(x, y);
    }

    /**
     * The MapType of the current map in use
     * @return MapType
     */
    private MapType getMapType() {
        if (levelSelected == 1) {
            return MapType.LEVELONE;
        } else if (levelSelected == 2) {
            return MapType.LEVELTWO;
        } else if (levelSelected == 3) {
            return MapType.LEVELTHREE;
        } else if (levelSelected == 4) {
            return MapType.LEVELFOUR;
        } else if (levelSelected == 5) {
            return MapType.LEVELFIVE;
        }
        else {
            return null;
        }
    }

    /**Getters**/
    public Map getMap() {
        return map;
    }
    public Race getRace() {
        return race;
    }
    public int getLevelSelected() { return levelSelected;}
    public LinkedList<Fighter> getArmy() {
        return army;
    }
    public LinkedList<Fighter> getCollection() {
        return collection;
    }
    public boolean getLevelComplete(int i) {return levelsComplete.get(i);}
    public Fighter getShieldedFighter() {return shieldedFighter;}

    /**Setters**/
    public void setRace(Race c) {
        race = c;
    }
    public void setArmy(LinkedList<Fighter> a) {army = a;}
    public void setLevelSelected(int i ) { levelSelected = i;}


    /**Adders**/
    public void addToArmy(Fighter f) {
        army.add(f);
    }
    public void addToCollection(Fighter f) {
        collection.add(f);
    }

    /**Removers**/
    public void removeFromArmy(Fighter f) {
        army.remove(f);
    }
    public void removeFromCollection(Fighter f) {
        collection.remove(f);
    }
}
