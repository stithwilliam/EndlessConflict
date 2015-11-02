package Model;

import java.util.*;


/**
 * Created by William on 10/26/2015.
 */
public class Map {

    Tile p = Tile.PLAIN;
    Tile f = Tile.FOREST;
    Tile r = Tile.ROCK;
    private int  width, height;
    private MapTile[][] board;
    private ArrayList<Fighter> fighters;
    private final Tile[][] tutorialBoard = {
            {p, p, p, p, p, p, p, p, p, p, f, f},
            {p, r, r, r, r, r, r, r, r, p, f, f},
            {p, r, p, p, p, p, p, p, r, p, f, f},
            {p, r, p, p, p, p, p, p, r, p, f, f},
            {p, r, p, p, p, p, p, p, r, p, f, f},
            {p, r, r, r, r, r, r, p, r, p, f, f},
            {f, f, f, f, f, f, p, p, p, f, f, f},
            {f, f, f, f, f, f, p, p, p, f, f, f}
    };

    public Map(int w, int h) {
        width = w;
        height = h;
        board = generateBoard(tutorialBoard);
        fighters = new ArrayList<>();
    }

    public MapTile[][] generateBoard(Tile[][] tiles) {
        MapTile[][] board = new MapTile[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                board[i][j] = new MapTile(tiles[i][j], j, i);
            }
        }
        return board;
    }

    //Uses a DFS to find all of the valid moves for a fighter f
    public boolean[][] getValidMoves(Fighter f) { //TODO Make this backed by a priority queue
        boolean[][] valid = new boolean[height][width];
        Queue<MapTile> queue = new LinkedList<>();
        List<MapTile> visited = new ArrayList<>();
        MapTile startingTile = getMapTile(f.getxPos(), f.getyPos());
        visited.add(startingTile);
        HashMap<MapTile, Integer> costMap = new HashMap<>();
        for (MapTile x : getMoves(startingTile)) {
            queue.add(x);
            costMap.put(x, x.getMoveCost());
        }
        while (!queue.isEmpty()) {
            MapTile tile = queue.poll();
            if (!visited.contains(tile)) {
                visited.add(tile);
                valid[tile.getyPos()][tile.getxPos()] = true;
                int currentCost = costMap.get(tile);
                if (currentCost < f.getMov()) {
                    for (MapTile x : getMoves(tile)) {
                        queue.add(x);
                        costMap.put(x, currentCost + x.getMoveCost());
                    }
                }
            }
        }
        return valid;
    }

    public List<MapTile> getMoves(MapTile tile) {
        int x = tile.getxPos();
        int y = tile.getyPos();
        List<MapTile> list = new ArrayList<>();
        MapTile left = getMapTile(x-1, y);
        if (left != null && left.isMoveable()) {
            list.add(left);
        }
        MapTile right = getMapTile(x+1, y);
        if (right != null && right.isMoveable()) {
            list.add(right);
        }
        MapTile up = getMapTile(x, y-1);
        if (up != null && up.isMoveable()) {
            list.add(up);
        }
        MapTile down = getMapTile(x, y+1);
        if (down != null && down.isMoveable()) {
            list.add(down);
        }
        return list;
    }

    public MapTile getMapTile(int x, int y) {
        if (x >= width || y >= height || x < 0 || y < 0) {
            return null;
        } else {
            return board[y][x];
        }
    }

    //Getters
    public int getHeight() { return height;}
    public int getWidth() { return width;}

    //Adders
    public void addFighter(Fighter f) {fighters.add(f);}
}
