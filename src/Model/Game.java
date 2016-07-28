package Model;

import Controller.BattleController;
import Controller.MasterController;

import java.util.ArrayList;
import java.util.LinkedList;
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
        Random r = new Random();
        int damage = attacker.getAtt();
        defender.setHp(defender.getHp() - damage);
        String s = (attacker.getName() + " dealt " + damage + " damage to " + defender.getName());
        if (defender.getHp() <= 0) {
            s += (" and killed " + defender.getName() + "!");
            killedFighter(defender);
        } else {
            s += (". (" + defender.getHp() + "/" + defender.getMaxHP() + ") health remaining");
        }
        return s;
    }

    /**
     * Called if a fighter dies.
     * @param f Fighter dying
     */
    public void killedFighter(Fighter f) {
        battleController.removeFighter(f);
        if (f.isEnemy()) {
            rewardChest(f.getxPos(), f.getyPos(), f.getModel());
        }
        if (map.getEnemies().size() == 0) {
            MasterController.getInstance().loadMapScene();

        }
    }

    /**
     * Spawns a reward chest at position (x,y).
     * @param x xPos
     * @param y yPos
     * @param model type of reward
     */
    private void rewardChest(int x, int y, Placeable model) {
        map.addToRewardList(model);
        battleController.showRewardChest(x, y);
    }

    /**
     * gets the next fighter in army after f
     * @param fighter The current Fighter
     * @return Fighter the next Fighter
     */
    public Fighter nextFighter(Fighter fighter) {
        ArrayList<Fighter> fighters = new ArrayList<>();
        fighters.addAll(army);
        fighters.addAll(collection);
        int idx = 0;
        Fighter f = null;
        while (f != fighter) {
            f = fighters.get(idx);
            idx++;
        }
        if (idx == fighters.size()) {
            idx = 0;
        }
        fighter = fighters.get(idx);
        return fighter;
    }

    /**
     * gets the previous fighter in army before fighter
     * @param fighter The current Fighter
     * @return Fighter the previous fighter
     */
    public Fighter prevFighter(Fighter fighter) {
        ArrayList<Fighter> fighters = new ArrayList<>();
        fighters.addAll(army);
        fighters.addAll(collection);
        int idx = fighters.size() - 1;
        Fighter f = null;
        while (f != fighter) {
            f = fighters.get(idx);
            idx--;
        }
        if (idx == -1) {
            idx = fighters.size() - 1;
        }
        fighter = fighters.get(idx);
        return fighter;
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

    /**Setters**/
    public void setRace(Race c) {
        race = c;
    }
    public void setArmy(LinkedList<Fighter> a) {army = a;}
    public void setLevelSelected(int i ) { levelSelected = i;}
    public void completeLevel(int i) {levelsComplete.set(i, true);}

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
