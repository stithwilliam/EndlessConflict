package Model;

import java.util.*;


/**
 * Created by William on 10/26/2015.
 */
public class Map {

    private int  width, height;
    private MapTile[][] board;
    private ArrayList<Fighter> fighters;
    private Fighter fighter;

    public Map(MapType type) {
        board = type.getBoard();
        height = board.length;
        width = board[0].length;
        fighters = type.getFighters();
        fighter = fighters.get(0);
    }

    public boolean hasPathBetween(int x1, int y1, int x2, int y2) {
        boolean foundPath = false;
        MapTile startingTile = getMapTile(x1, y1);
        HashSet<MapTile> visited = new HashSet<>();
        Queue<MapTile> queue = new LinkedList<>();
        for (MapTile x : getMoves(startingTile)) {
            queue.add(x);
        }
        while (!queue.isEmpty()) {
            MapTile tile = queue.poll();
            if (tile.getxPos() == x2 && tile.getyPos() == y2) {
                foundPath = true;
                queue = new LinkedList<>();
            } else {
                for (MapTile x : getMoves(tile)) {
                    if (!visited.contains(x)) {
                        queue.add(x);
                        visited.add(x);
                    }
                }
            }
        }
        return foundPath;
    }

    public int[][] costArr(int x, int y, boolean moveCost) {
        MapTile startingTile = getMapTile(x, y);
        HashMap<MapTile, Integer> costMap = new HashMap<>();
        Queue<MapTile> queue = new LinkedList<>();
        for (MapTile t : getMoves(startingTile)) {
            queue.add(t);
            if (moveCost) {
                costMap.put(t, t.getMoveCost());
            } else {
                costMap.put(t, 1);
            }
        }
        while (!queue.isEmpty()) {
            MapTile tile = queue.poll();
            int currentCost = costMap.get(tile);
            for (MapTile t : getMoves(tile)) {
                if (!costMap.containsKey(t)) {
                    queue.add(t);
                    if (moveCost) {
                        costMap.put(t, currentCost + t.getMoveCost());
                    } else {
                        costMap.put(t, currentCost + 1);
                    }
                } else {
                    if (moveCost) {
                        if (currentCost + t.getMoveCost() < costMap.get(t)) {
                            queue.add(t);
                            costMap.put(t, currentCost + t.getMoveCost());
                        }
                    } else {
                        if (currentCost + 1 < costMap.get(t)) {
                            queue.add(t);
                            costMap.put(t, currentCost + 1);
                        }
                    }
                }
            }
        }
        int[][] costArr = new int[height][width];
        for (int i = 0; i < costArr.length; i++) {
            for (int j = 0; j < costArr[i].length; j++) {
                costArr[i][j] = -1;
            }
        }
        for (MapTile t : costMap.keySet()) {
            int xPos = t.getxPos();
            int yPos = t.getyPos();
            costArr[yPos][xPos] = costMap.get(t);
        }
        return costArr;
    }

    public int costBetween(int x1, int y1, int x2, int y2, boolean moveCost) {
        MapTile startingTile = getMapTile(x1, y1);
        HashMap<MapTile, Integer> costMap = new HashMap<>();
        Queue<MapTile> queue = new LinkedList<>();
        for (MapTile x : getMoves(startingTile)) {
            queue.add(x);
            if (moveCost) {
                costMap.put(x, x.getMoveCost());
            } else {
                costMap.put(x, 1);
            }
        }
        while (!queue.isEmpty()) {
            MapTile tile = queue.poll();
            int currentCost = costMap.get(tile);
            for (MapTile x : getMoves(tile)) {
                if (!costMap.containsKey(x)) {
                    queue.add(x);
                    if (moveCost) {
                        costMap.put(x, currentCost + x.getMoveCost());
                    } else {
                        costMap.put(x, currentCost + 1);
                    }
                } else {
                    if (moveCost) {
                        if (currentCost + x.getMoveCost() < costMap.get(x)) {
                            queue.add(x);
                            costMap.put(x, currentCost + x.getMoveCost());
                        }
                    } else {
                        if (currentCost + 1 < costMap.get(x)) {
                            queue.add(x);
                            costMap.put(x, currentCost + 1);
                        }
                    }
                }
            }
        }
        MapTile target = getMapTile(x2, y2);
        if (costMap.get(target) != null) {
            return costMap.get(target);
        } else {
            return -1;
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

    public boolean[][] getTilesWithin(int x, int y, int range) {
        boolean[][] valid = new boolean[height][width];
        Queue<MapTile> queue = new LinkedList<>();
        MapTile startingTile = getMapTile(x, y);
        HashMap<MapTile, Integer> costMap = new HashMap<>();
        for (MapTile t : getAdjacent(startingTile)) {
            queue.add(t);
            costMap.put(t, 1);
        }
        while (!queue.isEmpty()) {
            MapTile tile = queue.poll();
            valid[tile.getyPos()][tile.getxPos()] = true;
            int currentCost = costMap.get(tile);
            if (currentCost < range) {
                for (MapTile t : getAdjacent(tile)) {
                    if (!costMap.containsKey(t)) {
                        queue.add(t);
                        costMap.put(t, currentCost + 1);
                    }
                }
            }
        }
        return valid;
    }

    public List<MapTile> getAdjacent(MapTile tile) {
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
        if (f.isMelee()) {
            for (MapTile x : getDiagAttacks(startingTile)) {
                queue.add(x);
                rangeMap.put(x, 1);
            }
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

    public boolean[][] getAttackable(Fighter f, boolean allyAttacking) {
        boolean[][] valid = getValidAttacks(f);
        boolean[][] inRange = new boolean[height][width];
        for (Fighter a : fighters) {
            if (allyAttacking) {
                if (a.isEnemy()) {
                    int x = a.getxPos();
                    int y = a.getyPos();
                    if (valid[y][x]) {
                        inRange[y][x] = true;
                    }
                }
            } else {
                if (!a.isEnemy()) {
                    int x = a.getxPos();
                    int y = a.getyPos();
                    if (valid[y][x]) {
                        inRange[y][x] = true;
                    }
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

    public List<MapTile> getDiagAttacks(MapTile tile) {
        int x = tile.getxPos();
        int y = tile.getyPos();
        List<MapTile> list = new ArrayList<>();
        MapTile btmLeft = getMapTile(x - 1, y + 1);
        if (btmLeft != null) {
            list.add(btmLeft);
        }
        MapTile btmRight = getMapTile(x + 1, y + 1);
        if (btmRight != null) {
            list.add(btmRight);
        }
        MapTile topLeft = getMapTile(x - 1, y - 1);
        if (topLeft != null) {
            list.add(topLeft);
        }
        MapTile topRight = getMapTile(x + 1, y - 1);
        if (topRight != null) {
            list.add(topRight);
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

    //sets fighter to the next fighter on allies, and then returns fighter
    public Fighter nextFighter() {
        ArrayList<Fighter> allies = getAllies();
        int idx = 0;
        Fighter f = null;
        while (f != fighter) {
            f = allies.get(idx);
            idx++;
        }
        if (idx == allies.size()) {
            idx = 0;
        }
        fighter = allies.get(idx);
        return fighter;
    }

    //sets fighter to the prev fighter on allies, and then returns fighter
    public Fighter prevFighter() {
        ArrayList<Fighter> allies = getAllies();
        int idx = allies.size() - 1;
        Fighter f = null;
        while (f != fighter) {
            f = allies.get(idx);
            idx--;
        }
        if (idx == -1) {
            idx = allies.size() - 1;
        }
        fighter = allies.get(idx);
        return fighter;
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
    public Fighter getFighter() { return fighter;}
    public ArrayList<Fighter> getAllies() {
        ArrayList<Fighter> list = new ArrayList<>();
        for (Fighter f : fighters) {
            if (!f.isEnemy()) {
                list.add(f);
            }
        }
        return list;
    }
    public ArrayList<Fighter> getEnemies() {
        ArrayList<Fighter> list = new ArrayList<>();
        for (Fighter f : fighters) {
            if (f.isEnemy()) {
                list.add(f);
            }
        }
        return list;
    }

    //Set
    public void setFighter(Fighter f) {fighter = f;}

    //Removers
    public void removeFighter(Fighter f) {fighters.remove(f);}
}
