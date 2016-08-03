package Model;

import java.util.*;
import java.util.function.Function;

import static Model.Commander.CHAOS;
import static Model.Commander.LIZARDKING;
import static Model.Commander.MODEL0;
import static Model.Unit.RANGER;
import static Model.Unit.SENTRYDRONE;
import static Model.Unit.SLIMEBALL;

/**
 * Enum for the different levels in the game
 */
public enum MapType {

    /**Types of Map**/
    LEVELONE, LEVELTWO, LEVELTHREE, LEVELFOUR, LEVELFIVE;

    /**
     * Coordinates class. Used within MapType for path calculations
     */
    private static class Coordinates {
        public int x;
        public int y;

        Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object obj) {
            Coordinates other = (Coordinates) obj;
            return this.x == other.x && this.y == other.y;
        }

        @Override
        public String toString() {
            return ("x: " + x + ". y: " + y);
        }
    }

    /**
     * Generates a board for each level.
     * @return MapTile[][] board
     */
    public MapTile[][] getBoard() {
        Game game = Main.myGame;
        MapTile[][] board = new MapTile[0][0];
        Coordinates start = new Coordinates(0, 0);
        List<Coordinates> objectives = new LinkedList<>();

        int h;
        int w;
        TileBase F = TileBase.FOREST;
        TileBase P = TileBase.PLAIN;
        TileBase R = TileBase.ROCK;
        TileBase[] tileBases;
        int[] probs;
        boolean pathBetween;
        switch (this) {
            case LEVELONE:
                System.out.println("level 1");
                h = 7;
                w = 15;
                start = new Coordinates(1, 2);
                objectives.add(new Coordinates(8, 3));
                objectives.add(new Coordinates(14, 3));
                tileBases = new TileBase[]{P, F, R};
                probs = new int[] {65, 15, 20};
                board = new MapTile[h][w];
                pathBetween = false;
                while (!pathBetween) {
                    for (int i = 0; i < h; i++) {
                        for (int j = 0; j < w; j++) {
                            if (((i <= 5 && i >= 2) && j <= 2) || ((i == 2 || i == 3 || i == 4) && (j == 8 || j == 7 || j == 9 || j >= 13))) {
                                board[i][j] = new MapTile(P, j, i);
                            } else {
                                board[i][j] = nextTile(board, tileBases, probs, j, i);
                            }
                        }
                    }
                    if (hasPathBetween(board, start, objectives.get(0)) && hasPathBetween(board, start, objectives.get(1))) {
                        pathBetween = true;
                    }
                }
                return board;
            case LEVELTWO:
                System.out.println("level 2");
                h = 12;
                w = 14;
                board = new MapTile[h][w];
                tileBases = new TileBase[]{P, F, R};
                probs = new int[] {60, 25, 15};
                start = new Coordinates(2, 6);
                objectives.add(new Coordinates(6, 2));
                objectives.add(new Coordinates(11, 9));
                pathBetween = false;
                while (!pathBetween) {
                    for (int i = 0; i < h; i++) {
                        for (int j = 0; j < w; j++) {
                            if ( ( (i <= 7 && i >= 5) && (j <= 2 && j >= 1) ) ||
                                    ( (i <= 1 && i >= 2) && (j <= 6 && j >= 5) ) ||
                                    (  (i <= 10 && i >= 9) && (j <= 12 && j >= 11) ) ) {
                                board[i][j] = new MapTile(P, j, i);
                            } else {
                                board[i][j] = nextTile(board, tileBases, probs, j, i);
                            }
                        }
                    }
                    if (hasPathBetween(board, start, objectives.get(0)) && hasPathBetween(board, start, objectives.get(1))) {
                        pathBetween = true;
                    }
                }
                return board;
            case LEVELTHREE:
                System.out.println("level 3");
                h = 12;
                w = 14;
                board = new MapTile[h][w];
                tileBases = new TileBase[]{P, F, R};
                probs = new int[] {60, 25, 15};
                start = new Coordinates(2, 6);
                objectives.add(new Coordinates(5, 9));
                objectives.add(new Coordinates(11, 2));
                pathBetween = false;
                while (!pathBetween) {
                    for (int i = 0; i < h; i++) {
                        for (int j = 0; j < w; j++) {
                            if ( ( (i <= 7 && i >= 5) && (j <= 2 && j >= 1) ) ||
                                    ( (i <= 1 && i >= 2) && (j <= 12 && j >= 11) ) ||
                                    (  (i <= 10 && i >= 9) && (j <= 6 && j >= 5) ) ) {
                                board[i][j] = new MapTile(P, j, i);
                            } else {
                                board[i][j] = nextTile(board, tileBases, probs, j, i);
                            }
                        }
                    }
                    if (hasPathBetween(board, start, objectives.get(0)) && hasPathBetween(board, start, objectives.get(1))) {
                        pathBetween = true;
                    }
                }
                return board;
            case LEVELFOUR:
                System.out.println("level 4");
                h = 12;
                w = 16;
                board = new MapTile[h][w];
                tileBases = new TileBase[]{P, F, R};
                probs = new int[] {60, 25, 15};
                start = new Coordinates(2, 6);
                objectives.add(new Coordinates(5, 9));
                objectives.add(new Coordinates(11, 2));
                objectives.add(new Coordinates(14, 6));
                pathBetween = false;
                while (!pathBetween) {
                    for (int i = 0; i < h; i++) {
                        for (int j = 0; j < w; j++) {
                            if ( ( (i <= 7 && i >= 5) && (j <= 2 && j >= 1) ) ||
                                    ( (i <= 1 && i >= 2) && (j <= 6 && j >= 5) ) ||
                                    ( (i <= 10 && i >= 9) && (j <= 12 && j >= 11) ) ||
                                    (i == 6 && j == 14) ) {
                                board[i][j] = new MapTile(P, j, i);
                            } else {
                                board[i][j] = nextTile(board, tileBases, probs, j, i);
                            }
                        }
                    }
                    if (hasPathBetween(board, start, objectives.get(0)) && hasPathBetween(board, start, objectives.get(1)) &&
                            hasPathBetween(board, start, objectives.get(2))) {
                        pathBetween = true;
                    }
                }
                return board;
            case LEVELFIVE:
                h = 16;
                w = 16;
                board = new MapTile[h][w];
                tileBases = new TileBase[]{P, F, R};
                probs = new int[] {60, 25, 15};
                start = new Coordinates(2, 8);
                objectives.add(new Coordinates(8, 13));
                objectives.add(new Coordinates(8, 2));
                objectives.add(new Coordinates(13, 2));
                pathBetween = false;
                while (!pathBetween) {
                    for (int i = 0; i < h; i++) {
                        for (int j = 0; j < w; j++) {
                            if ( ( (i <= 9 && i >= 7) && (j <= 2 && j >= 0) ) ||
                                    ( (i <= 1 && i >= 2) && (j <= 8 && j >= 7) ) ||
                                    ( (i <= 13 && i >= 14) && (j <= 8 && j >= 7) ) ||
                                    ( (i <= 8 && i <= 7) && (j <= 13 && j >= 14) ) ) {
                                board[i][j] = new MapTile(P, j, i);
                            } else {
                                board[i][j] = nextTile(board, tileBases, probs, j, i);
                            }
                        }
                    }
                    if (hasPathBetween(board, start, objectives.get(0)) && hasPathBetween(board, start, objectives.get(1)) &&
                            hasPathBetween(board, start, objectives.get(2))) {
                        pathBetween = true;
                    }
                }
                return board;
        }
        return board;
    }

    /**
     * Gets the enemies that populate each level
     * @return List<Fighter> enemies
     */
    public LinkedList<Fighter> getEnemies() {
        LinkedList<Fighter> fighters = new LinkedList<>();
        Game game = Main.myGame;
        Race race = game.getRace();
        switch (this) {
            case LEVELONE:
                Fighter w = new Fighter(race.getWeakRace().getUnit(), 8, 3, true);
                fighters.add(w);
                Fighter s = new Fighter(race.getStrongRace().getUnit(), 14, 3, true);
                fighters.add(s);
                return fighters;
            case LEVELTWO:
                Fighter f1 = new Fighter(race.getWeakRace().getUnit(), 5, 1, true);
                fighters.add(f1);
                Fighter f2 = new Fighter(race.getWeakRace().getUnit(), 6, 2, true);
                fighters.add(f2);
                Fighter f3 = new Fighter(race.getWeakRace().getUnit(), 11, 10, true);
                fighters.add(f3);
                Fighter f4 = new Fighter(race.getWeakRace().getCommander(), 12, 9, true);
                fighters.add(f4);
                return fighters;
            case LEVELTHREE:
                Fighter a1 = new Fighter(race.getStrongRace().getUnit(), 5, 10, true);
                fighters.add(a1);
                Fighter a2 = new Fighter(race.getStrongRace().getUnit(), 6, 9, true);
                fighters.add(a2);
                Fighter a3 = new Fighter(race.getStrongRace().getUnit(), 11, 1, true);
                fighters.add(a3);
                Fighter a4 = new Fighter(race.getStrongRace().getCommander(), 12, 2, true);
                fighters.add(a4);
                return fighters;
            case LEVELFOUR:
                Fighter b1 = new Fighter(race.getWeakRace().getUnit(), 5, 1, true);
                fighters.add(b1);
                Fighter b2 = new Fighter(race.getUnit(), 6, 2, true);
                fighters.add(b2);
                Fighter b3 = new Fighter(race.getStrongRace().getUnit(), 11, 10, true);
                fighters.add(b3);
                Fighter b4 = new Fighter(race.getUnit(), 12, 9, true);
                fighters.add(b4);
                Fighter b5 = new Fighter(race.getCommander(), 14, 6, true);
                fighters.add(b5);
                return fighters;
            case LEVELFIVE:
                Fighter c1 = new Fighter(race.getWeakRace().getUnit(), 7, 2, true);
                fighters.add(c1);
                Fighter c2 = new Fighter(race.getWeakRace().getCommander(), 8, 1, true);
                fighters.add(c2);
                Fighter c3 = new Fighter(race.getStrongRace().getUnit(), 7, 13, true);
                fighters.add(c3);
                Fighter c4 = new Fighter(race.getStrongRace().getCommander(), 8, 14, true);
                fighters.add(c4);
                Fighter c5 = new Fighter(race.getCommander(), 13, 8, true);
                fighters.add(c5);
                Fighter c6 = new Fighter(race.getUnit(), 14, 7, true);
                fighters.add(c6);
                Fighter c7 = new Fighter(race.getUnit(), 14, 9, true);
                fighters.add(c7);
                return fighters;
            default:
                return fighters;
        }
    }

    /**
     * gets xPos of the ally fighters for this level
     * @param i position in army
     * @return xPos
     */
    public int armyXPos(int i) {
        int[] arr;
        switch (this) {
            case LEVELONE:
                arr = new int[] {1,0,0};
                return arr[i];
            case LEVELTWO:
                arr = new int[] {2, 1, 1, 1};
                return arr[i];
            case LEVELTHREE:
                arr = new int[] {2, 1, 1, 1};
                return arr[i];
            case LEVELFOUR:
                arr = new int[] {2, 1, 1, 1};
                return arr[i];
            case LEVELFIVE:
                arr = new int[] {2, 1, 1, 1, 0};
                return arr[i];
            default:
                return -1;
        }
    }

    /**
     * gets yPos of the ally fighters for this level
     * @param i position in army
     * @return yPos
     */
    public int armyYPos(int i) {
        int[] arr;
        switch (this) {
            case LEVELONE:
                arr = new int[] {3,2,4};
                return arr[i];
            case LEVELTWO:
                arr = new int[] {6, 5, 7, 6};
                return arr[i];
            case LEVELTHREE:
                arr = new int[] {6, 5, 7, 6};
                return arr[i];
            case LEVELFOUR:
                arr = new int[] {6, 5, 7, 6};
                return arr[i];
            case LEVELFIVE:
                arr = new int[] {6, 5, 7, 6, 6};
                return arr[i];
            default:
                return -1;
        }
    }

    /**
     * Gets the limit for each level
     * @param lvl number
     * @return army limit
     */
    public static int getArmyLimit(int lvl) {
        if (lvl == 1) {
            return 3;
        } else if (lvl == 2) {
            return 4;
        } else if (lvl == 3) {
            return 4;
        } else if (lvl == 4) {
            return 4;
        } else if (lvl == 5) {
            return 5;
        } else {
            return 0;
        }
    }

    public List<Placeable> getFirstRewards() {
        List<Placeable> list;
        Race race = Main.myGame.getRace();
        switch (this) {
            case LEVELONE:
                list = new LinkedList<>();
                list.add(race.getStrongRace().getUnit());
                list.add(race.getWeakRace().getUnit());
                return list;
            case LEVELTWO:
                list = new LinkedList<>();
                list.add(race.getWeakRace().getCommander());
                return list;
            case LEVELTHREE:
                list = new LinkedList<>();
                list.add(race.getStrongRace().getCommander());
                return list;
            case LEVELFOUR:
                list = new LinkedList<>();
                list.add(race.getUnit());
                list.add(race.getUnit());
                return list;
            case LEVELFIVE:
                list = new LinkedList<>();
                list.add(race.getWeakRace().getUnit());
                list.add(race.getStrongRace().getUnit());
                return list;
            default:
                return new LinkedList<>();
        }
    }

    public double rewardChance(Fighter f) {
        if (f.getModel() instanceof Unit) {
            return rewardChance((Unit) f.getModel());
        } else {
            return rewardChance((Commander) f.getModel());
        }
    }

    public  double rewardChance(Unit unit) {
        switch (unit) {
            case RANGER:
                return .25;
            case SENTRYDRONE:
                return .25;
            case SLIMEBALL:
                return .25;
            default:
                return 0;
        }
    }

    public static double rewardChance(Commander comm) {
        switch(comm) {
            case MODEL0:
                return 0;
            case LIZARDKING:
                return 0;
            case CHAOS:
                return 0;
            default:
                return 0;
        }
    }
    /**
     * Tells whether start and goal coordinates have a moveable path between them
     * @param board to check path on
     * @param start coordinates
     * @param goal coordinates
     * @return boolean
     */
    public static boolean hasPathBetween(MapTile[][] board, Coordinates start, Coordinates goal) {
        boolean foundPath = false;
        List<Coordinates> visited = new LinkedList<>();
        Queue<Coordinates> queue = new LinkedList<>();

        for (Coordinates x : getMoves(board, start)) {
            queue.add(x);
        }
        while (!queue.isEmpty()) {
            Coordinates tile = queue.poll();
            if (tile.x == goal.x && tile.y == goal.y) {
                foundPath = true;
                queue = new LinkedList<>();
            } else {
                for (Coordinates x : getMoves(board, tile)) {
                    if (!visited.contains(x)) {
                        queue.add(x);
                        visited.add(x);
                    }
                }
            }
        }
        return foundPath;
    }

    /**
     * Gives the available moves from a start coordinate
     * @param board to look for moves on
     * @param start coordinates
     * @return List<Coordinates> of moves
     */
    public static List<Coordinates> getMoves(MapTile[][] board, Coordinates start) {
        List<Coordinates> list = new LinkedList<>();
        int maxX = board[0].length - 1;
        int maxY = board.length - 1;

        //left
        if (start.x - 1 >= 0 && start.x - 1 <= maxX && board[start.y][start.x - 1].isMoveable()) {
            list.add(new Coordinates(start.x - 1, start.y));
        }

        //right
        if (start.x + 1 >= 0 && start.x + 1 <= maxX && board[start.y][start.x + 1].isMoveable()) {
            list.add(new Coordinates(start.x + 1, start.y));
        }

        //up
        if (start.y + 1 >= 0 && start.y + 1 <= maxY && board[start.y + 1][start.x].isMoveable()) {
            list.add(new Coordinates(start.x, start.y + 1));
        }

        //down
        if (start.y - 1 >= 0 && start.y - 1 <= maxY && board[start.y - 1][start.x].isMoveable()) {
            list.add(new Coordinates(start.x, start.y - 1));
        }
        return list;
    }

    /**
     * Chooses the next tile to be added during board generation
     * @param board to add tile to
     * @param tileBases what the tiles can be
     * @param probs probability of each tile
     * @param x pos of tile
     * @param y pos of tile
     * @return MapTile chosen
     */
    private MapTile nextTile(MapTile[][] board, TileBase[] tileBases, int[] probs, int x, int y) { //TODO make touching tileBases more likely to appear
        Random r = new Random();
        int n = r.nextInt(100);
        int p = 0;
        for (int i = 0; i < probs.length; i++) {
            p += probs[i];
            if (n < p) {
                return new MapTile(tileBases[i], x, y);
            }
        }
        return new MapTile(tileBases[0], x, y);
    }



}
