package Model;

/**
 * Created by William on 10/27/2015.
 */
public interface Placeable {

    /**Getters**/
    String imagePath();
    String getName();
    boolean isMelee();
    boolean isHero();
    int getAtt();
    int getDef();
    int getHp();
    int getMov();
    int getVis();
    String getSkillName();


}
