package Model;

import java.util.*;


/**
 * Created by William on 10/26/2015.
 */
public class Map {

    private int  width, height;
    private MapTile[][] board;
    private ArrayList<Fighter> fighters;

    public Map(MapType type) {
        board = type.getBoard();
        height = board.length;
        width = board[0].length;
        fighters = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Fighter f = board[i][j].getFighter();
                if (f != null) {
                    fighters.add(f);
                }
            }
        }
    }

    //Uses a BFS to find all of the valid moves for a fighter f
    public boolean[][] getValidMoves(Fighter f) {
        boolean[][] valid = new boolean[height][width];

        MapTile startingTile = getMapTile(f.getxPos(), f.getyPos());
        HashMap<MapTile, Integer> costMap = new HashMap<>();
        PriorityQueue<MapTile> queue = new PriorityQueue<>(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                MapTile a = (MapTile)o1;
                MapTile b = (MapTile)o2;
                if (costMap.get(a) != null && costMap.get(b) != null) {
                    return (costMap.get(a) - costMap.get(b));
                } else {
                    return 0;
                }
            }
        });
        for (MapTile x : getMoves(startingTile)) {
            queue.add(x);
            costMap.put(x, x.getMoveCost());
        }
        while (!queue.isEmpty()) {
            MapTile tile = queue.poll();
            valid[tile.getyPos()][tile.getxPos()] = true;
            int currentCost = costMap.get(tile);
            if (currentCost < f.getMov()) {
                for (MapTile x : getMoves(tile)) {
                    if (!valid[x.getyPos()][x.getxPos()]) {
                        queue.add(x);
                        int nextCost = currentCost + x.getMoveCost();
                        if (costMap.containsKey(x)) {
                            costMap.put(x, Math.min(nextCost, costMap.get(x)));
                        } else {
                            costMap.put(x, nextCost);
                        }
                    }
                }
            }
        }
        for (Fighter a : fighters) {
            int x = a.getxPos();
            int y = a.getyPos();
            valid[y][x] = false;
        }
        return valid;
    }

    //helper function for getValidMoves
    //gets the adjacent, moveable tiles of tile.
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

    //uses a BFS to find the valid attacks of f
    public boolean[][] getValidAttacks(Fighter f) { //TODO implement terrain blocking
        //finds the valid squares
        boolean[][] valid = new boolean[height][width];
        Queue<MapTile> queue = new LinkedList<>();
        MapTile startingTile = getMapTile(f.getxPos(), f.getyPos());
        HashMap<MapTile, Integer> rangeMap = new HashMap<>();
        for (MapTile x : getAttacks(startingTile)) {
            queue.add(x);
            rangeMap.put(x, 1);
        }
        while (!queue.isEmpty()) {
            MapTile tile = queue.poll();
            valid[tile.getyPos()][tile.getxPos()] = true;
            int currentRange = rangeMap.get(tile);
            if (currentRange < f.getRange()) {
                for (MapTile x : getAttacks(tile)) {
                    queue.add(x);
                    rangeMap.put(x, currentRange + 1);
                }
            }
        }
        //removes positions behind rocks
        for (int i = 0; i < valid.length; i++) {
            for (int j = 0; j < valid[0].length; j++) {
                if (valid[i][j]) {
                    if (getMapTile(j, i).isBlocking()) {
                        valid = fixObstacle(valid, j, i, f);
                    }
                }
            }
        }
        return valid;
    }

    public boolean[][] getAttackable(Fighter f) {
        boolean[][] valid = getValidAttacks(f);
        boolean[][] inRange = new boolean[height][width];
        for (Fighter a : fighters) {
            if (a.isEnemy()) {
                int x = a.getxPos();
                int y = a.getyPos();
                if (valid[y][x]) {
                    inRange[y][x] = true;
                }
            }
        }
        return inRange;
    }

    public List<MapTile> getAttacks(MapTile tile) {
        int x = tile.getxPos();
        int y = tile.getyPos();
        List<MapTile> list = new ArrayList<>();
        MapTile left = getMapTile(x - 1, y);
        if (left != null) {
            list.add(left);
        }
        MapTile right = getMapTile(x + 1, y);
        if (right != null) {
            list.add(right);
        }
        MapTile up = getMapTile(x, y-1);
        if (up != null) {
            list.add(up);
        }
        MapTile down = getMapTile(x, y+1);
        if (down != null) {
            list.add(down);
        }
        return list;
    }

    public boolean[][] fixObstacle(boolean[][] valid, int x, int y, Fighter f) {
        valid[y][x] = false;
        int posX = f.getxPos();
        int posY = f.getyPos();
        int height = valid.length;
        int width = valid[0].length;
        int difX = x - posX;
        int difY = y - posY;
        if (difX == 0 && difY > 0) { // directly down
            double count = 0;
            for (int i = y; i < height; i++) {
                for (int j = (int) count; j >= 0; j--) {
                    if ((x + j) < width) {
                        valid[i][x + j] = false;
                    }
                    if ((x - j) >= 0) {
                        valid[i][x - j] = false;
                    }
                }
                count += 0.5;
            }
        } else if (difX == 0 && difY < 0) { // directly up
            double count = 0;
            for (int i = y; i >= 0; i--) {
                for (int j = (int) count; j >= 0; j--) {
                    if ((x + j) < width) {
                        valid[i][x + j] = false;
                    }
                    if ((x - j) >= 0) {
                        valid[i][x - j] = false;
                    }
                }
                count += 0.5;
            }
        } else if (difY == 0 && difX > 0) { // directly right
            double count = 0;
            for (int i = x; i < width; i++) {
                for (int j = (int) count; j >= 0; j--) {
                    if ((y + j) < height) {
                        valid[y + j][i] = false;
                    }
                    if ((y - j) >= 0) {
                        valid[y - j][i] = false;
                    }
                }
                count += 0.5;
            }
        } else if (difY == 0 && difX < 0){ //directly left
            double count = 0;
            for (int i = x; i >= 0; i--) {
                for (int j = (int) count; j>= 0; j--) {
                    if ((y + j) < height) {
                        valid[y + j][i] = false;
                    }
                    if ((y - j) >= 0) {
                        valid[y - j][i] = false;
                    }
                }
                count += 0.5;
            }
        } else if (difX > 0 && difY > 0) { // bottom right
            System.out.println("BTM RIGHT: difX = " + difX + ", difY = " + difY);
            double count = 0.5;
            for (int i = 0; i < Math.max(width, height); i++) {
                for (int j = (int) count; j >= 0; j--) {
                    if ((x + i - j) < width && (y + i) < height) {
                        valid[y + i][x + i - j] = false;
                    }
                    if ((x + i) < width && (y + i - j) < height) {
                        valid[y + i - j][x + i] = false;
                    }
                }
                count += 0.5;
            }
        } else if (difX < 0 && difY > 0) { //bottom left
            System.out.println("BTM LEFT: difX = " + difX + ", difY = " + difY);
            double count = 0.5;
            for (int i = 0; i < Math.max(width, height); i++) {
                for (int j = (int) count; j >= 0; j--) {
                    if ((x - i + j) >= 0 && (y + i) < height) {
                        valid[y + i][x - i + j] = false;
                    }
                    if ((x - i) >= 0 && (y + i - j) < height) {
                        valid[y + i - j][x - i] = false;
                    }
                }
                count += 0.5;
            }
        } else if (difX > 0 && difY < 0) { //top right
            System.out.println("TOP RIGHT: difX = " + difX + ", difY = " + difY);
            double count = 0.5;
            for (int i = 0; i < Math.max(width, height); i++) {
                for (int j = (int) count; j >= 0; j--) {
                    if ((x + i - j) < width && (y - i) >= 0) {
                        valid[y - i][x + i - j] = false;
                    }
                    if ((x + i) < width && (y - i + j) >= 0) {
                        valid[y - i + j][x + i] = false;
                    }
                }
                count += 0.5;
            }
        } else if (difX < 0 && difY < 0) { //top left
            System.out.println("TOP LEFT: difX = " + difX + ", difY = " + difY);
            double count = 0.5;
            for (int i = 0; i < Math.max(width, height); i++) {
                for (int j = (int) count; j >= 0; j--) {
                    if ((x - i + j) >= 0 && (y - i) >= 0) {
                        valid[y - i][x - i + j] = false;
                    }
                    if ((x - i) >= 0 && (y - i + j) >= 0) {
                        valid[y - i + j][x - i] = false;
                    }
                }
                count += 0.5;
            }
        }
        return valid;
    }

    public MapTile getMapTile(int x, int y) {
        if (x >= width || y >= height || x < 0 || y < 0) {
            return null;
        } else {
            return board[y][x];
        }
    }

    public Fighter getFighter(int x, int y) {
        for (Fighter f : fighters) {
            if (f.getxPos() == x && f.getyPos() == y) {
                return f;
            }
        }
        return null;
    }

    //Getters
    public int getHeight() { return height;}
    public int getWidth() { return width;}
    public ArrayList<Fighter> getFighters() { return fighters;}

    //Adders
    public void addFighter(Fighter f) {fighters.add(f);}

    //Removers
    public void removeFighter(Fighter f) {fighters.remove(f);}
}
