package Model;

import Controller.BattleController;

import java.util.*;



public class Map {

    /**Type of this map**/
    private MapType mapType;

    /**The board of MapTiles that backs the map**/
    private MapTile[][] board;

    /**The list of fighters on the current map**/
    private LinkedList<Fighter> fighters;

    /**The current fighter that is selected on the map**/
    private Fighter fighter;

    /**convenience vars for height and with of the map**/
    private int  width, height;

    /**List of rewards obtained on the current map**/
    private LinkedList<Placeable> rewardList;


    /**
     * Constructor for Map.
     * @param type the MapType that the new Map is based off of.
     */
    public Map(MapType type) {
        mapType = type;
        board = type.getBoard();
        height = board.length;
        width = board[0].length;
        fighters = type.getEnemies();
        rewardList = new LinkedList<>();
    }

    public void placeArmy(List<Fighter> army) {
        int i = 0;
        for (Fighter f : army) {
            if (i == 0) {
                fighter = f;
            }
            f.setxPos(mapType.getXPos(i));
            f.setyPos(mapType.getYPos(i));
            fighters.add(f);
            i++;
        }
    }



    /**
     * Utility function.
     * Returns whether the two positions, (x1, y1) and (x2, y2), have a valid path between them.
     * @param x1 int x1 position
     * @param y1 int y1 position
     * @param x2 int x2 position
     * @param y2 int y2 position
     * @return boolean if path between the positions
     */
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

    /**
     * Returns an array of costs, centered at (x, y).
     * @param x int x position
     * @param y int y position
     * @param moveCost boolean to take the moveCost of the tile in to account, or build the cost arr after the raw distance.
     * @return int[][] cost
     */
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

    /*
    Not sure if I'll need this
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
    */

    /**
     * Returns an array of the valid moves for Fighter f.
     * @param f Fighter looking at
     * @return boolean[][] valid moves
     */
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

    /**
     * Helper function for getValidMoves
     * @param tile TileBase looking at
     * @return List<MapTile> of adjacent, moveable tiles of tile
     */
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

    /**
     * Returns an array of tiles that are located within int range from position (x, y).
     * @param x int x position
     * @param y int y position
     * @param range boolean[][] tiles in range
     * @return
     */
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

    /**
     * Returns the adjacent tiles to tile
     * @param tile TileBase looking at
     * @return List<MapTile> adjacent tiles
     */
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

    /**
     * Gets the MapTiles at diagonally adjacent to tile
     * @param tile MapTile looking at
     * @return List<MapTile> diagonally adjecent to tile
     */
    public List<MapTile> getDiagAdjacent(MapTile tile) {
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

    /**
     * Returns the valid attacks of Fighter f
     * @param f Fighter looking at
     * @return boolean[][] valid attacks
     */
    //uses a BFS to find the valid attacks of f
    public boolean[][] getValidAttacks(Fighter f) {
        //finds the valid squares
        boolean[][] valid = new boolean[height][width];
        Queue<MapTile> queue = new LinkedList<>();
        MapTile startingTile = getMapTile(f.getxPos(), f.getyPos());
        HashMap<MapTile, Integer> rangeMap = new HashMap<>();
        for (MapTile x : getAdjacent(startingTile)) {
            queue.add(x);
            rangeMap.put(x, 1);
        }
        if (f.getRange() == 1) {
            for (MapTile x : getDiagAdjacent(startingTile)) {
                queue.add(x);
                rangeMap.put(x, 1);
            }
        }
        while (!queue.isEmpty()) {
            MapTile tile = queue.poll();
            valid[tile.getyPos()][tile.getxPos()] = true;
            int currentRange = rangeMap.get(tile);
            if (currentRange < f.getRange()) {
                for (MapTile x : getAdjacent(tile)) {
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

    /**
     * Gets the fighters that are attackable by f
     * @param f Fighter looking at
     * @return boolean[][] array of attackable positions
     */
    public boolean[][] getAttackable(Fighter f) {
        boolean[][] valid = getValidAttacks(f);
        boolean[][] inRange = new boolean[height][width];
        boolean allyAttacking = !f.isEnemy();
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

    /**
     * Gross code that fixes the valid attacks array for Fighter f based on an obstacle at x, y
     * @param valid boolean[][] the valid attacks array
     * @param x int x position of obstacle
     * @param y int y position of obstacle
     * @param f Fighter f that is attacking
     * @return boolean[][] the fixed valid attacks array
     */
    public boolean[][] fixObstacle(boolean[][] valid, int x, int y, Fighter f) {
        valid[y][x] = false;
        int posX = f.getxPos();
        int posY = f.getyPos();
        int height = valid.length;
        int width = valid[0].length;
        int difX = x - posX;
        int difY = y - posY;

        // directly down
        if (difX == 0 && difY > 0) {
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
        }
        // directly up
        else if (difX == 0 && difY < 0) {
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
        }
        // directly right
        else if (difY == 0 && difX > 0) {
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
        }
        //directly left
        else if (difY == 0 && difX < 0){
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
        }
        // bottom right
        else if (difX > 0 && difY > 0) {
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
        }
        //bottom left
        else if (difX < 0 && difY > 0) {
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
        }
        //top right
        else if (difX > 0 && difY < 0) {
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
        }
        //top left
        else if (difX < 0 && difY < 0) {
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

    /**
     * gets the next fighter in allies
     * @return Fighter the next Fighter
     */
    public Fighter nextFighter() {
        LinkedList<Fighter> allies = getAllies();
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

    /**
     * gets the previous fighter in allies
     * @return Fighter the previous fighter
     */
    public Fighter prevFighter() {
        LinkedList<Fighter> allies = getAllies();
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

    /**
     * gets the MapTile at location (x, y)
     * @param x int x position
     * @param y int y position
     * @return MapTile located at position
     */
    public MapTile getMapTile(int x, int y) {
        if (x >= width || y >= height || x < 0 || y < 0) {
            return null;
        } else {
            return board[y][x];
        }
    }

    /**
     * Gets the fighter located at position (x, y)
     * @param x int x position
     * @param y int y position
     * @return Fighter located at position
     */
    public Fighter getFighter(int x, int y) {
        for (Fighter f : fighters) {
            if (f.getxPos() == x && f.getyPos() == y) {
                return f;
            }
        }
        return null;
    }

    /**Getters**/
    public int getHeight() { return height;}
    public int getWidth() { return width;}
    public LinkedList<Fighter> getFighters() { return fighters;}
    public Fighter getFighter() { return fighter;}
    public LinkedList<Fighter> getAllies() {
        LinkedList<Fighter> list = new LinkedList<>();
        for (Fighter f : fighters) {
            if (!f.isEnemy()) {
                list.add(f);
            }
        }
        return list;
    }
    public LinkedList<Fighter> getEnemies() {
        LinkedList<Fighter> list = new LinkedList<>();
        for (Fighter f : fighters) {
            if (f.isEnemy()) {
                list.add(f);
            }
        }
        return list;
    }
    /**Adders**/
    public void addToRewardList(Placeable p) {rewardList.add(p);}

    /**Getters**/


    /**Setters**/
    public void setFighter(Fighter f) {fighter = f;}

    /**Removers**/
    public void removeFighter(Fighter f) {fighters.remove(f);}
}
