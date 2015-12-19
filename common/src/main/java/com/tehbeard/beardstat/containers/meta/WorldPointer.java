package com.tehbeard.beardstat.containers.meta;

/**
 *
 * @author james
 */
public class WorldPointer extends AbstractPointer {
    private final String worldName;
    private final String statWrapper;
    
    
    public WorldPointer(String worldName){
        this(worldName,"%s in world %s");
    }
    
    public WorldPointer(String worldName, String statwrapper){
        this.worldName = worldName;
        this.statWrapper = statwrapper;
    }

    public String getGameTag() {
        return worldName;
    }

    public String wrapStatistic(String contents) {
        return String.format(statWrapper, contents, this.worldName);
    }
}
