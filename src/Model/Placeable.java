package Model;

/**
 * Created by William on 10/27/2015.
 */
public interface Placeable {


    //Getters
    String imagePath();
    String getName();
    boolean isFlying();
    boolean isMelee();
    int getAtt();
    int getDef();
    int getHp();
    int getSpd();
    int getMov();
    int getVis();

}
