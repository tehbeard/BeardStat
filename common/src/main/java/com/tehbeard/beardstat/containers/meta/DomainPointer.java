package com.tehbeard.beardstat.containers.meta;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author james
 */
public class DomainPointer extends AbstractPointer {
    
    private final String gameTag;

    public DomainPointer(String gameTag) {
        this.gameTag = gameTag;
    }

    public String getGameTag() {
        return gameTag;
    }
    
    private final static Set<DomainPointer> pointers = new HashSet<DomainPointer>();
    
    public static DomainPointer get(String name) {
        DomainPointer p = new DomainPointer(name);
        if(!pointers.contains(p)){
            pointers.add(p);
        }
        for(DomainPointer pp : pointers){
            if(pp.equals(p)){
                return pp;
            }
        }
        throw new IllegalStateException("Fell out of loop in get()");
    }
    public static DomainPointer get(int id){
        for(DomainPointer p : pointers){
            if(p.getId() == id){
                return p;
            }
        }
        throw new IllegalStateException("Fell out of loop in get()");
    }
}
