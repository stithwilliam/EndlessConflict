package Model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by William on 11/4/2015.
 */
public enum MapType {

    /**Types of Map**/
    TUTORIAL;

    /**
     * Generates a board based on the type of map.
     * @return MapTile[][] board
     */
    public MapTile[][] getBoard() {
        MapTile[][] board = new MapTile[0][0];
        TileBase F = TileBase.FOREST;
        TileBase P = TileBase.PLAIN;
        TileBase R = TileBase.ROCK;
        Game game = Main.myGame;
        switch (this) {
            case TUTORIAL:
                int h = 7;
                int w = 15;
                TileBase[] tileBases = {P, F, R};
                int[] probs = {65, 15, 20};
                board = new MapTile[h][w];
                for (int i = 0; i < h; i++) {
                    for (int j = 0; j < w; j++) {
                        if (((i <= 5 && i >= 2) && j <= 2) || ((i == 2 || i == 3 || i == 4) && (j == 8 || j == 7 || j == 9 || j >= 13))) {
                            board[i][j] = new MapTile(P, j, i);
                        } else {
                            board[i][j] = nextTile(board, tileBases, probs, j, i);
                        }
                    }
                }
        }
        return board;
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
            case TUTORIAL:
                Fighter w = new Fighter(race.getWeakRace().getUnit(), 8, 3, true);
                fighters.add(w);
                Fighter s = new Fighter(race.getStrongRace().getUnit(), 14, 3, true);
                fighters.add(s);
        }
        return fighters;
    }

    public int getXPos(int i) {
        switch (this) {
            case TUTORIAL:
                int[] arr = {1,0,0};
                return arr[i];
            default:
                return -1;
        }
    }

    public int getYPos(int i) {
        switch (this) {
            case TUTORIAL:
                int[] arr = {3,2,4};
                return arr[i];
            default:
                return -1;
        }
    }
}
