package com.tehbeard.beardstat.containers.meta;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author james
 */
public class CategoryPointer {
    private final int dbId;
    private final String gameTag;
    private final String statWrapper;
    
    
    public CategoryPointer(int dbId, String gameTag){
        this(dbId, gameTag,"%s");
    }
    
    public CategoryPointer(int dbId, String gameTag, String statwrapper){
        this.dbId = dbId;
        this.gameTag = gameTag;
        this.statWrapper = statwrapper;
        
    }

    public int getDbId() {
        return dbId;
    }

    public String getGameTag() {
        return gameTag;
    }

    public String wrapStatistic(String contents) {
        return String.format(statWrapper, contents);
    }
}
