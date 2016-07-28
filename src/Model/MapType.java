package Model;

import java.util.*;
import java.util.function.Function;

/**
 * Created by William on 11/4/2015.
 */
public enum MapType {

    /**Types of Map**/
    LEVELONE, LEVELTWO, LEVELTHREE, LEVELFOUR, LEVELFIVE;

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
     * Generates a board based on the type of map.
     * @return MapTile[][] board
     */
    public MapTile[][] getBoard() {
        Game game = Main.myGame;
        MapTile[][] board = new MapTile[0][0];
        Coordinates start = new Coordinates(0, 0);
        List<Coordinates> objectives = new LinkedList<>();

        TileBase F = TileBase.FOREST;
        TileBase P = TileBase.PLAIN;
        TileBase R = TileBase.ROCK;
        switch (this) {
            case LEVELONE:
                System.out.println("building level 1");
                int h = 7;
                int w = 15;
                start = new Coordinates(1, 2);
                objectives.add(new Coordinates(8, 3));
                objectives.add(new Coordinates(14, 3));
                TileBase[] tileBases = {P, F, R};
                int[] probs = {65, 15, 20};
                board = new MapTile[h][w];
                boolean pathBetween = false;
                while (!pathBetween) {
                    System.out.println("generating a map");
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
                        System.out.println("has path between");
                        pathBetween = true;
                    }
                }
        }
        return board;
    }

    public static boolean hasPathBetween(MapTile[][] board, Coordinates start, Coordinates goal) {
        System.out.println("getting path");
        boolean foundPath = false;
        List<Coordinates> visited = new LinkedList<>();
        Queue<Coordinates> queue = new LinkedList<>();

        for (Coordinates x : getMoves(board, start)) {
            queue.add(x);
        }
        while (!queue.isEmpty()) {
            System.out.println("queue length: " + queue.size());
            Coordinates tile = queue.poll();
            if (tile.x == goal.x && tile.y == goal.y) {
                foundPath = true;
                queue = new LinkedList<>();
            } else {
                for (Coordinates x : getMoves(board, tile)) {
                    if (!visited.contains(x)) {
                        System.out.println("adding " + x.toString());
                        queue.add(x);
                        visited.add(x);
                    }
                }
            }
        }
        System.out.println("found path");
        return foundPath;
    }

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

    //helper function for getBoard, chooses a tile type
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
        }
        return fighters;
    }

    public int armyXPos(int i) {
        switch (this) {
            case LEVELONE:
                int[] arr = {1,0,0};
                return arr[i];
            default:
                return -1;
        }
    }

    public int armyYPos(int i) {
        switch (this) {
            case LEVELONE:
                int[] arr = {3,2,4};
                return arr[i];
            default:
                return -1;
        }
    }
}
