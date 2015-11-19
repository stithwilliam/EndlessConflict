package Model;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by William on 11/4/2015.
 */
public enum MapType {

    TUTORIAL;

    public MapTile[][] getBoard() {
        MapTile[][] board = new MapTile[0][0];
        Tile F = Tile.FOREST;
        Tile P = Tile.PLAIN;
        Tile R = Tile.ROCK;
        Game game = Main.myGame;
        switch (this) {
            case TUTORIAL:
                int h = 6;
                int w = 15;
                Tile[] tiles = {P, F, R};
                int[] probs = {65, 15, 20};
                board = new MapTile[h][w];
                for (int i = 0; i < h; i++) {
                    for (int j = 0; j < w; j++) {
                        if (((i <= 4 && i >= 1) && j <= 2) || ((i == 3 || i == 2 || i == 4) && (j == 8 || j == 7 || j == 9 || j >= 13))) {
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
                fighters.add(new Fighter(commander.getHero(), 1, 2, false));
                fighters.add(new Fighter(commander.getUnit(), 0, 1, false));
                fighters.add(new Fighter(commander.getUnit(), 0, 3, false));
                Fighter w = new Fighter(commander.getWeakCommander().getHero(), 8, 3, true);
                w.setAtt(w.getAtt() - 3);
                w.setDef(w.getDef() - 3);
                fighters.add(w);
                Fighter s = new Fighter(commander.getStrongCommander().getHero(), 14, 3, true);
                s.setAtt(s.getAtt() - 3);
                s.setDef(s.getDef() - 3);
                fighters.add(s);
        }
        return fighters;
    }
}
