package com.tehbeard.beardstat.containers;

import com.tehbeard.beardstat.containers.meta.CategoryPointer;
import com.tehbeard.beardstat.containers.meta.DomainPointer;
import com.tehbeard.beardstat.containers.meta.StatPointer;
import com.tehbeard.beardstat.containers.meta.WorldPointer;

/**
 * Represents a stat
 * 
 * @author James
 * 
 */

public interface IStat {

    /**
     * Get the stats value
     * 
     * @return
     */
    public int getValue();

    /**
     * Set the stats value
     * 
     * @param value
     */
    public void setValue(int value);

    /**
     * Get the stats name
     * 
     * @return
     */
    public StatPointer getStatistic();

    public CategoryPointer getCategory();

    public void clearArchive();

    public void archive();

    public boolean isArchive();

    public void setOwner(EntityStatBlob playerStatBlob);

    public EntityStatBlob getOwner();

    public DomainPointer getDomain();

    public WorldPointer getWorld();

    /**
     * Increment the stat by i
     * 
     * @param i
     */
    public void incrementStat(int i);

    /**
     * decrement the stat by i
     * 
     * @param i
     */
    public void decrementStat(int i);

    /**
     * Clone self
     * 
     * @return
     */
    public IStat copyStat();
}
