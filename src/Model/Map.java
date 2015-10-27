package Model;

/**
 * Created by William on 10/26/2015.
 */
public class Map {

    MapTile p = MapTile.PLAIN;
    MapTile f = MapTile.FOREST;
    MapTile r = MapTile.ROCK;
    private int  width, height;
    private MapTile[][] board;
    private Fighter[][] fighters;
    private final MapTile[][] tutorialBoard = {
            {p, p, p, p, p, p, p, p, p, p, f, f},
            {p, r, r, r, r, r, r, r, r, p, f, f},
            {p, r, p, p, p, p, p, p, r, p, f, f},
            {p, r, p, p, p, p, p, p, r, p, f, f},
            {p, r, p, p, p, p, p, p, r, p, f, f},
            {p, r, r, r, r, r, r, r, r, p, f, f},
            {f, f, f, f, f, f, f, f, f, f, f, f},
            {f, f, f, f, f, f, f, f, f, f, f, f}
    };

    public Map(int w, int h) {
        width = w;
        height = h;
        board = tutorialBoard;
        fighters = new Fighter[h][w];
    }


    //Getters
    public int getHeight() { return height;}
    public int getWidth() { return width;}
    public MapTile getTile(int x, int y) {return board[y][x];}
    public Fighter getFighter(int x, int y) {return fighters[y][x];}
    //Setters
    public void setFighter(Fighter f, int x, int y) {fighters[y][x] = f;}
}
