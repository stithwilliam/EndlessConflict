package Model;

import java.util.Random;

/**
 * Created by William on 11/4/2015.
 */
public enum MapType {

    TUTORIAL;

    private MapTile[][] board;

    public MapTile[][] getBoard() {
        board = new MapTile[0][0];
        Tile F = Tile.FOREST;
        Tile P = Tile.PLAIN;
        Tile R = Tile.ROCK;
        switch (this) {
            case TUTORIAL:
                Commander commander = Main.myGame.getCommander();
                int h = 6;
                int w = 15;
                Tile[] tiles = {P, F, R};
                int[] probs = {75, 15, 10};
                board = new MapTile[h][w];
                for (int i = 0; i < h; i++) {
                    for (int j = 0; j < w; j++) {
                        if ((i <= 4 && j <= 2) || (i == 3 && (j == 8 || j == 13))) {
                            board[i][j] = new MapTile(P, j, i);
                            Fighter f = null;
                            if (i == 2 && j == 1) {
                                f = new Fighter(commander.getHero(), j, i, false);
                            } else if (i == 1 && j == 0) {
                                f = new Fighter(commander.getUnit(), j, i, false);
                                f.setName(f.getName() + " 1");
                            } else if (i == 3 && j == 0) {
                                f = new Fighter(commander.getUnit(), j, i, false);
                                f.setName(f.getName() + " 2");
                            } else if (i == 3 && j == 8) {
                                f = new Fighter(commander.getWeakCommander().getHero(), j, i, true);
                            } else if (i == 3 && j == 13) {
                                f = new Fighter(commander.getStrongCommander().getHero(), j, i, true);
                            }
                            if (f != null) {
                                board[i][j].setFighter(f);
                            }
                        } else {
                            board[i][j] = nextTile(board, tiles, probs, j, i);
                        }
                    }
                }
        }
        return board;
    }

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
}
