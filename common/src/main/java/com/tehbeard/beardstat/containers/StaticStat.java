package com.tehbeard.beardstat.containers;

import com.tehbeard.beardstat.containers.meta.CategoryPointer;
import com.tehbeard.beardstat.containers.meta.DomainPointer;
import com.tehbeard.beardstat.containers.meta.StatPointer;
import com.tehbeard.beardstat.containers.meta.WorldPointer;

/**
 * Concrete implementation of a player stat. This is the default type for stats,
 * They do not change themselves, instead relying on other code to modify them.
 * Upon modification they raise the archive flag, making eligable to be saved.
 * 
 * @author James
 * 
 */

public class StaticStat extends AbstractStat {

    private int     value;

    private boolean archive  = false;

    public StaticStat(DomainPointer domain, WorldPointer world, CategoryPointer cat, StatPointer statistic, int value) {
        super(domain, world, cat, statistic);
        this.value = value;
    }

    /**
     * Get the stats value
     * 
     * @return
     */
    @Override
    public synchronized int getValue() {
        return this.value;
    }

    /**
     * Set the stats value
     * 
     * @param value
     *            value to set stat to
     */
    @Override
    public synchronized void setValue(int value) {
        changeValue(value);
    }


    /**
     * Increment the stat by i
     * 
     * @param i
     *            amount to increment stat by new value = old value + i
     */
    @Override
    public synchronized void incrementStat(int i) {
        // if(i < 0 ){throw new
        // IllegalArgumentException("Cannot increment by negative number!");}
        changeValue(this.value + i);
    }

    /**
     * decrement the stat by i
     * 
     * @param i
     *            amount to dencrement stat by new value = old value - i
     */
    @Override
    public synchronized void decrementStat(int i) {
        // if(i < 0 ){throw new
        // IllegalArgumentException("Cannot decrement by negative number!");}
        changeValue(this.value - i);
    }


    /**
     * Clear the archive flag
     */
    @Override
    public synchronized void clearArchive() {
        this.archive = false;
    }

    /**
     * Is archive flag set? if the flag is set, the stat will be stored in the
     * database, and the flag cleared on the next save.
     */
    @Override
    public synchronized boolean isArchive() {
        return this.archive;
    }


    private synchronized void changeValue(int to) {
        this.value = to;
        this.archive = true;
    }


    @Override
    public String toString() {
        return this.getCategory() + "." + this.getStatistic() + "=" + this.value;
    }

    @Override
    public synchronized void archive() {
        this.archive = true;

    }

    @Override
    public IStat copyStat() {
        return new StaticStat(this.getDomain(), this.getWorld(), this.getCategory(), this.getStatistic(), this.value);
    }

    
}
