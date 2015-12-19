package com.tehbeard.beardstat.containers.meta;

/**
 *
 * @author James
 */
public abstract class AbstractPointer {
    
    private int id = -1;
    
    public int getId(){
        return id;
    }
    
    public void setId(int newId){
        if(newId < 0){
            throw new IllegalArgumentException("Id must be 0 or greater!");
        }
        if(id != -1){
            throw new IllegalStateException("Cannot set an id after it has already been set");
        }
        this.id = newId;
    }
}
