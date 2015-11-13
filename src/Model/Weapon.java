package Model;

/**
 * Created by William on 11/12/2015.
 */
public enum Weapon implements Equipment {
    DIRTYCLAW, LASERPISTOL, LIGHTMACHINEGUN,  //hero starting weapons
    DIRTYOOZE, SHODDYLASERRIFLE,SMALLTURRET;  //unit starting weapons

    @Override
    public int getAttMod() {
        return 0;
    }
    @Override
    public int getDefMod() {
        return 0;
    }
    @Override
    public int getHpMod() {
        return 0;
    }
    @Override
    public int getSpdMod() {
        return 0;
    }
    @Override
    public int getMovMod() {
        return 0;
    }
    @Override
    public int getVisMod() {
        return 0;
    }
}
