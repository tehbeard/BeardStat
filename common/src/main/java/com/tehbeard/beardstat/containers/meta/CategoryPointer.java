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
public class CategoryPointer extends AbstractPointer  {
    private final String gameTag;
    private final String statWrapper;
    
    
    public CategoryPointer(String gameTag){
        this(gameTag,"%s");
    }
    
    public CategoryPointer(String gameTag, String statwrapper){
        this.gameTag = gameTag;
        this.statWrapper = statwrapper;
        
    }

    public String getGameTag() {
        return gameTag;
    }

    public String wrapStatistic(String contents) {
        return String.format(statWrapper, contents);
    }
}
