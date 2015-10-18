package com.tehbeard.beardstat.identifier;


/**
 * Interface for a identifier generator, which returns a string id for various given resources (blocks, items, entities).
 * This is so we can use different and/or multiple sources for generating an id (homebrew, flatfile, api, mux)
 * @author James
 *
 */
public interface IIdentifierGenerator {
    
    /**
     * Given a block/item id, returns the appropriate string id, or null if not found.
     * @param id
     * @return
     */
    public String keyForId(int id,int meta);
    /**
     * Get the key for this object w/o any metadata attached
     * e.g. minecraft:wool instead of minecraft:wool:5
     * @param id
     * @return 
     */
    public String keyForId(int id);
    
    
    /**
     * Returns the key for an entity
     * @param entity
     * @return
     */
    public String keyForObject(Object object);
    
    public String getHumanName(String key);
}
