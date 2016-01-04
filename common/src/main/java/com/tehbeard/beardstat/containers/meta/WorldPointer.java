package com.tehbeard.beardstat.containers.meta;

import java.util.HashSet;
import java.util.Set;

/**
 * Pointer for worlds
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

    public String formatStat(String contents) {
        return String.format(statWrapper, contents, this.worldName);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.worldName != null ? this.worldName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WorldPointer other = (WorldPointer) obj;
        if ((this.worldName == null) ? (other.worldName != null) : !this.worldName.equals(other.worldName)) {
            return false;
        }
        return true;
    }
    
    private final static Set<WorldPointer> pointers = new HashSet<WorldPointer>();
    
    public static WorldPointer get(String name) {
        WorldPointer p = new WorldPointer(name);
        if(!pointers.contains(p)){
            pointers.add(p);
        }
        for(WorldPointer pp : pointers){
            if(pp.equals(p)){
                return pp;
            }
        }
        throw new IllegalStateException("Fell out of loop in get()");
    }
    
    public static WorldPointer get(int id){
        for(WorldPointer p : pointers){
            if(p.getId() == id){
                return p;
            }
        }
        throw new IllegalStateException("Fell out of loop in get()");
    }
}
