package com.tehbeard.beardstat.containers;

import com.tehbeard.beardstat.containers.meta.CategoryPointer;
import com.tehbeard.beardstat.containers.meta.DomainPointer;
import com.tehbeard.beardstat.containers.meta.StatPointer;
import com.tehbeard.beardstat.containers.meta.WorldPointer;

/**
 * Represents a timeable stat, adds a few handy features for computing time spent.
 * @author James
 *
 */
public class TimeStat extends AbstractStat {

    /**
     * @param owner
     * @param domain
     * @param cat
     * @param world
     * @param statistic
     */
    public TimeStat(DomainPointer domain, WorldPointer world, CategoryPointer cat, StatPointer statistic, EntityStatBlob owner) {
        super(domain, world, cat, statistic);
    }

    private int value = 0;

    @Override
    public boolean isArchive() {
        return true;
    }

    @Override
    public void incrementStat(int i) {
        value += i;
    }

    @Override
    public int getValue() {
        return value + getTime();
    }

    @Override
    public void decrementStat(int i) {
        value -= i;
    }

    @Override
    public void clearArchive() {
    }

    @Override
    public void archive() {
    }

    @Override
    public IStat copyStat() {
        return new StaticStat(getDomain(), getWorld(), getCategory(), getStatistic(), getValue());
    }
    
    
    private long timerStarted = 0;
    
    
    /**
     * Start a timer running
     */
    public void startTimer(){
        timerStarted = System.currentTimeMillis();
    }
    
    /**
     * Get current time elapsed. This is added to the current value.
     * @return
     */
    public int getTime(){
        return (timerStarted > 0) ? (int) ((System.currentTimeMillis() - timerStarted) / 1000L) : 0;
    }
    
    /**
     * Stop the timer and reset it's value.
     */
    public void resetTimer(){
        timerStarted = 0;
    }
    
    /**
     * Add the timer value to static value and reset. 
     */
    public void markAndResetTimer(){
        value += getTime();
        resetTimer();
    }

    @Override
    public void setValue(int value) { this.value = value; }
}
