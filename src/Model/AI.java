package Model;

import Controller.BattleController;
import Controller.MasterController;

import java.util.ArrayList;

/**
 * Created by William on 11/13/2015.
 */
public class AI {

    /**Classes that the AI talks to**/
    Game game;
    Map map;

    /**
     * Default constructor.
     */
    public AI() {
        this(Main.myGame, Main.myGame.getMap());
    }

    /**
     * Overloaded constructor.
     * @param game Game class that AI talks to.
     * @param map Map class that AI interacts with.
     */
    public AI(Game game, Map map) {
        this.game = game;
        this.map = map;
    }

    /**
      * Handles the enemy fighters' moves. The fighters will move to the closest enemy and attack if possible.
     **/
    public void doTurn() {
        BattleController battleController = MasterController.getInstance().getBattleController();
        for (Fighter f : map.getEnemies()) { // loops through all enemies
            if (f.hasAttacked() && f.hasMoved()) {
                continue;
            }

            // move to get preferred fighter in range
            int[][] costArr = map.costArr(f.getxPos(), f.getyPos(), true);
            Fighter closestFighter = null;
            int range = f.getRange();
            boolean inRange = false;
            for (Fighter defender : map.getAllies()) {
                int xDef = defender.getxPos();
                int yDef = defender.getyPos();
                if (closestFighter == null) {
                    closestFighter = defender;
                }
                if (costArr[yDef][xDef] <= range) {
                    if (!inRange) {
                        closestFighter = defender;
                        inRange = true;
                    } else {
                        closestFighter = fighterToAttack(defender, closestFighter);
                    }
                } else if (costArr[yDef][xDef] < costArr[closestFighter.getyPos()][closestFighter.getxPos()]) {
                    closestFighter = defender;
                }
            }
            MapTile tile = tileToAttack(f, closestFighter);
            battleController.enemyMove(f, tile);

            // all(y/ies) in range, choose which to attack
            ArrayList<Fighter> alliesInRange = alliesInRange(f);
            if (alliesInRange.size() != 0) {
                Fighter toAttack = alliesInRange.get(0);
                for (Fighter a : alliesInRange) {
                    toAttack = fighterToAttack(toAttack, a);
                }
                battleController.enemyAttack(f, toAttack);
            }
            //TODO Add an animation timer so the AI doesn't move all at once
        }
        for (Fighter f : map.getEnemies()) {
            f.setHasAttacked(false);
            f.setHasMoved(false);
        }
    }

    /**
     * Helper function to determine which fighter the AI should choose to attack.
     * Looks at game's difficulty to make a decision.
     * EASY: attacks player with highest defense.
     * MEDIUM: attacks player with lowest defense.
     * HARD: attacks player based on their attack / defense combo.
     * @param a Fighter to compare with b
     * @param b Fighter to compare with a
     * @return The fighter chosen to attack.
     */
    private Fighter fighterToAttack(Fighter a, Fighter b) {
        Difficulty dif = Main.myGame.getDifficulty();
        if (dif == Difficulty.EASY) {
            if (a.getDef() > b.getDef()) {
                return a;
            } else {
                return b;
            }
        } else if (dif == Difficulty.MEDIUM) {
            if (a.getDef() < b.getDef()) {
                return a;
            } else {
                return b;
            }
        } else if (dif == Difficulty.HARD) {
            if ((a.getDef() + a.getAtt() * -2) < (b.getDef() + b.getAtt() * -2)) {
                return a;
            } else {
                return b;
            }
        }
        System.out.println("Your difficulty is weird - AI.fighterToAttack(a, b)");
        return null;
    }

    /**
     * A helper function that finds all of the allies in range of Fighter f.
     * @param f Fighter looking at
     * @return ArrayList of Fighters in range of f
     */
    private ArrayList<Fighter> alliesInRange(Fighter f) {
        boolean[][] canAttack = map.getAttackable(f);
        ArrayList<Fighter> alliesInRange = new ArrayList<>();
        for (int i = 0; i < canAttack.length; i++) {
            for (int j = 0; j < canAttack[i].length; j++) {
                if (canAttack[i][j]) {
                    alliesInRange.add(map.getMapTile(j, i).getFighter());
                }
            }
        }
        return alliesInRange;
    }

    /**
     * Finds the tile that Fighter attacker should move to so that he can attack Fighter defender.
     * TODO: Make this chosen tile smarter based on Difficulty.
     * @param attacker Fighter that the AI is controlling
     * @param defender Ally Fighter to be attacked
     * @return The MapTile to move to
     */
    private MapTile tileToAttack(Fighter attacker, Fighter defender) {
        boolean[][] possibleMoves = map.getValidMoves(attacker);
        int[][] costArr = map.costArr(defender.getxPos(), defender.getyPos(), true);
        int minCost = 99;
        int x = 99;
        int y = 99;
        //determine tile closest to defender
        for (int i = 0; i < costArr.length; i++) {
            for (int j = 0; j < costArr[i].length; j++) {
                if (possibleMoves[i][j]) {
                    if (costArr[i][j] < minCost) {
                        x = j;
                        y = i;
                        minCost = costArr[i][j];
                    }
                }
            }
        }
        return map.getMapTile(x, y);
    }

    /**
     * TODO: write a function that determines how many allies can hit f at t
     * @param f The fighter that the AI is controlling
     * @param t The tile that the fighter wants to move to
     * @return How many allies can hit f at t
     */
    public ArrayList<Fighter> alliesCanHitMe(Fighter f, MapTile t) {
        return null;
    }


}
