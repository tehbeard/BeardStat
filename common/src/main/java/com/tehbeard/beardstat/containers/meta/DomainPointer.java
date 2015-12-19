package com.tehbeard.beardstat.containers.meta;

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
}
