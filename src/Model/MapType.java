package Model;

import java.util.ArrayList;
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
        Tile F = Tile.FOREST;
        Tile P = Tile.PLAIN;
        Tile R = Tile.ROCK;
        Game game = Main.myGame;
        switch (this) {
            case TUTORIAL:
                int h = 7;
                int w = 15;
                Tile[] tiles = {P, F, R};
                int[] probs = {65, 15, 20};
                board = new MapTile[h][w];
                for (int i = 0; i < h; i++) {
                    for (int j = 0; j < w; j++) {
                        if (((i <= 5 && i >= 2) && j <= 2) || ((i == 2 || i == 3 || i == 4) && (j == 8 || j == 7 || j == 9 || j >= 13))) {
                            board[i][j] = new MapTile(P, j, i);
                        } else {
                            board[i][j] = nextTile(board, tiles, probs, j, i);
                        }
                    }
                }
        }
        return board;
    }

    //helper function for getBoard, chooses a tile type
    private MapTile nextTile(MapTile[][] board, Tile[] tiles, int[] probs, int x, int y) { //TODO make touching tiles more likely to appear
        Random r = new Random();
        int n = r.nextInt(100);
        int p = 0;
        for (int i = 0; i < probs.length; i++) {
            p += probs[i];
            if (n < p) {
                return new MapTile(tiles[i], x, y);
            }
        }
        return new MapTile(tiles[0], x, y);
    }

    public ArrayList<Fighter> getFighters() {
        ArrayList<Fighter> fighters = new ArrayList<>();
        Game game = Main.myGame;
        Commander commander = game.getCommander();
        switch (this) {
            case TUTORIAL:
                Fighter hero = new Fighter(commander.getHero(), 1, 3, false);
                fighters.add(hero);
                Fighter unit1 = new Fighter (commander.getUnit(), 0, 2, false);
                unit1.setName(unit1.getName() + " 1");
                fighters.add(unit1);
                Fighter unit2 = new Fighter(commander.getUnit(), 0, 4, false);
                unit2.setName(unit2.getName() + " 2");
                fighters.add(unit2);
                Fighter w = new Fighter(commander.getWeakCommander().getUnit(), 8, 3, true);
                fighters.add(w);
                Fighter s = new Fighter(commander.getStrongCommander().getUnit(), 14, 3, true);
                fighters.add(s);
        }
        return fighters;
    }
}
