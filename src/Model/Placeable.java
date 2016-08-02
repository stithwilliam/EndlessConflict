package Model;

/**
 * The interface that all fighters implement
 */
public interface Placeable {

    /**Getters**/
    String imagePath();
    String getName();
    String getRace();
    String getDescription();
    boolean isCommander();
    int getAtt();
    int getHp();
    int getMov();
    int getRange();
    int getStars();

}
