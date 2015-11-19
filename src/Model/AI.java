package Model;

import Controller.MapController;
import Controller.MasterController;

import java.util.ArrayList;

/**
 * Created by William on 11/13/2015.
 */
public class AI {

    Game game;
    Map map;

    public AI() {
        this(Main.myGame, Main.myGame.getMap());
    }

    public AI(Game game, Map map) {
        this.game = game;
        this.map = map;
    }

    public void doTurn() {
        MapController mapController = MasterController.getInstance().getMapController();
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
            mapController.enemyMove(f, tile);

            // all(y/ies) in range, choose which to attack
            ArrayList<Fighter> alliesInRange = alliesInRange(f);
            if (alliesInRange.size() != 0) {
                Fighter toAttack = alliesInRange.get(0);
                for (Fighter a : alliesInRange) {
                    toAttack = fighterToAttack(toAttack, a);
                }
                mapController.enemyAttack(f, toAttack);
            }
            //TODO Add an animation timer so the AI doesn't move all at once
        }
        for (Fighter f : map.getEnemies()) {
            f.setHasAttacked(false);
            f.setHasMoved(false);
        }
    }

    public Fighter fighterToAttack(Fighter a, Fighter b) {
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

    public ArrayList<Fighter> alliesInRange(Fighter f) {
        boolean[][] canAttack = map.getAttackable(f, false);
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

    public MapTile tileToAttack(Fighter attacker, Fighter defender) {
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

    public ArrayList<Fighter> alliesCanHitMe(Fighter f) {
        return null;
    }

    //Getters

    //Setters
    public void setMap(Map m) {map = m;}
}
