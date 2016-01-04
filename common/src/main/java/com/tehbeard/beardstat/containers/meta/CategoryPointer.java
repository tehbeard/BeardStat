package com.tehbeard.beardstat.containers.meta;

import java.util.HashSet;
import java.util.Set;

/**
 * Pointer for categories
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

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.gameTag != null ? this.gameTag.hashCode() : 0);
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
        final CategoryPointer other = (CategoryPointer) obj;
        if ((this.gameTag == null) ? (other.gameTag != null) : !this.gameTag.equals(other.gameTag)) {
            return false;
        }
        return true;
    }
    
    private final static Set<CategoryPointer> pointers = new HashSet<CategoryPointer>();
    
    public static CategoryPointer get(String name) {
        CategoryPointer p = new CategoryPointer(name);
        if(!pointers.contains(p)){
            pointers.add(p);
        }
        for(CategoryPointer pp : pointers){
            if(pp.equals(p)){
                return pp;
            }
        }
        throw new IllegalStateException("Fell out of loop in get()");
    }
    
    public static CategoryPointer get(int id){
        for(CategoryPointer p : pointers){
            if(p.getId() == id){
                return p;
            }
        }
        throw new IllegalStateException("Fell out of loop in get()");
    }
}
