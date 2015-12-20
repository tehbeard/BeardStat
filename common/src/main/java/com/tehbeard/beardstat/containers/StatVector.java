package com.tehbeard.beardstat.containers;

import com.tehbeard.beardstat.containers.meta.CategoryPointer;
import com.tehbeard.beardstat.containers.meta.DomainPointer;
import com.tehbeard.beardstat.containers.meta.StatPointer;
import com.tehbeard.beardstat.containers.meta.WorldPointer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Represents a collection of IStats, usually the result of a regex get
 * operation. Mutator methods related to a value affect ALL objects inside this.
 * Other mutator methods fail silently
 * 
 * @author James
 * 
 */
public class StatVector extends AbstractStat implements Iterable<IStat> {

    private List<IStat> stats    = new ArrayList<IStat>();

    private boolean     readOnly = false;

    public StatVector(DomainPointer domain, WorldPointer world, CategoryPointer cat, StatPointer statistic, boolean readOnly) {
        super(domain, world, cat, statistic);
        this.readOnly = readOnly;
    }

    public void add(IStat stat) {
        this.stats.add(stat);
    }

    @Override
    public int getValue() {
        int ss = 0;
        for (IStat s : this.stats) {
            ss += s.getValue();
        }

        return ss;
    }

    @Override
    public void setValue(int value) {
        if (this.readOnly) {
            throw new IllegalStateException("Cannot set value of read only stat vector");
        }
        for (IStat s : this.stats) {
            s.setValue(value);
        }
    }

    @Override
    public void clearArchive() {

    }

    @Override
    public void archive() {
        if (this.readOnly) {
            throw new IllegalStateException("Cannot set value of read only stat vector");
        }
        for (IStat s : this.stats) {
            s.archive();
        }

    }

    @Override
    public boolean isArchive() {
        return false;
    }

    @Override
    public void setOwner(EntityStatBlob playerStatBlob) {

    }

    @Override
    public EntityStatBlob getOwner() {
        return null;
    }


    @Override
    public void incrementStat(int i) {
        if (this.readOnly) {
            throw new IllegalStateException("Cannot set value of read only stat vector");
        }
        for (IStat s : this.stats) {
            s.incrementStat(i);
        }

    }

    @Override
    public void decrementStat(int i) {
        if (this.readOnly) {
            throw new IllegalStateException("Cannot set value of read only stat vector");
        }
        for (IStat s : this.stats) {
            s.decrementStat(i);
        }

    }

    @Override
    public IStat copyStat() {
        return null;
    }

    @Override
    public Iterator<IStat> iterator() {
        return this.stats.iterator();
    }

    public int size() {
        return this.stats.size();
    }

}
