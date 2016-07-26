package Model;

/**
 * Created by William on 10/27/2015.
 */
public interface Placeable {

    /**Getters**/
    String imagePath();
    String getName();
    String getDescription();
    boolean isCommander();
    int getAtt();
    int getHp();
    int getMov();
    int getRange();

}
