package Model;

import java.util.Random;

/**
 * Created by William on 11/4/2015.
 */
public enum MapType {

    TUTORIAL;

    private Tile[][] tileArr;

    public Tile[][] getTileArr() {
        tileArr = new Tile[0][0];
        Tile F = Tile.FOREST;
        Tile P = Tile.PLAIN;
        Tile R = Tile.ROCK;
        switch (this) {
            case TUTORIAL:
                int h = 6;
                int w = 15;
                Tile[] tiles = {P, F, R};
                int[] probs = {75, 15, 10};
                tileArr = new Tile[h][w];
                for (int i = 0; i < h; i++) {
                    for (int j = 0; j < w; j++) {
                        tileArr[i][j] = nextTile(tileArr, tiles, probs, i, j);
                    }
                }
        }
        return tileArr;
    }

    private Tile nextTile(Tile[][] tileArr, Tile[] tiles, int[] probs, int x, int y) { //TODO make touching tiles more likely to appear
        Random r = new Random();
        int n = r.nextInt(100);
        int p = 0;
        for (int i = 0; i < probs.length; i++) {
            p += probs[i];
            if (n < p) {
                return tiles[i];
            }
        }
        return tiles[0];
    }
}
