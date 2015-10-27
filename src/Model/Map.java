package Model;

/**
 * Created by William on 10/26/2015.
 */
public class Map {

    private int  width, height;
    //private MapTile[][] board;

    public Map(int w, int h) {
        width = w;
        height = h;
    }


    //Getters
    public int getHeight() { return height;}
    public int getWidth() { return width;}
    //Setters
}
