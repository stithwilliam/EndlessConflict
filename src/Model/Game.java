package Model;

import Controller.MapController;
import Controller.MasterController;

import java.util.ArrayList;

/**
 * Created by William on 10/26/2015.
 */
public class Game {

    private Difficulty difficulty;
    private Mode mode;
    private Commander commander;
    private ArrayList<Fighter> allies, enemies;

    //Constructor
    public Game() {
        difficulty = null;
        mode = null;
        commander = null;
        allies = new ArrayList<>();
        enemies = new ArrayList<>();
    }

    public void startGame() {
        MasterController.getInstance().setMapScene();
        MasterController.getInstance().getMapController().generateMap(new Map(12, 8));

    }

    //Getters
    public Difficulty getDifficulty() {return difficulty;}
    public Mode getMode() {return mode;}
    public Commander getCommander() {return commander;}
    public ArrayList<Fighter> getAllies() {return allies;}
    public ArrayList<Fighter> getEnemies() {return enemies;}
    //Setters
    public void setDifficulty(Difficulty d) {difficulty = d;}
    public void setMode(Mode m) {mode = m;}
    public void setCommander(Commander c) {commander = c;}
    public void setAllies(ArrayList<Fighter> a) {allies = a;}
    public void setEnemies(ArrayList<Fighter> e) {enemies = e;}
    //Adders
    public void addToAllies(Fighter f) {allies.add(f);}
    public void addToEnemies(Fighter f) {enemies.add(f);}

}
