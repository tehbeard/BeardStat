/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tehbeard.beardstat.containers;

import com.tehbeard.beardstat.containers.meta.CategoryPointer;
import com.tehbeard.beardstat.containers.meta.DomainPointer;
import com.tehbeard.beardstat.containers.meta.StatPointer;
import com.tehbeard.beardstat.containers.meta.WorldPointer;

/**
 *
 * @author James
 */
public abstract class AbstractStat implements IStat {

    private EntityStatBlob owner = null;
    private final DomainPointer domain;
    private final WorldPointer world;
    private final CategoryPointer category;
    private final StatPointer statistic;

    public AbstractStat(DomainPointer domain, WorldPointer world, CategoryPointer cat, StatPointer statistic) {
        this.domain = domain;
        this.world = world;
        this.statistic = statistic;
        this.category = cat;
    }

    /**
     * Get the stats name
     *
     * @return name of tstat
     */
    @Override
    public StatPointer getStatistic() {
        return this.statistic;
    }

    /**
     * @return name of category stat is in
     */
    @Override
    public CategoryPointer getCategory() {
        return this.category;
    }

    /**
     * get the blob of stats this stat belongs to.
     */
    @Override
    public EntityStatBlob getOwner() {
        return this.owner;
    }

    /**
     * Set owner of this stat
     */
    @Override
    public void setOwner(EntityStatBlob playerStatBlob) {
        this.owner = playerStatBlob;
    }

    @Override
    public DomainPointer getDomain() {
        return this.domain;
    }

    @Override
    public WorldPointer getWorld() {
        return this.world;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 73 * hash + (this.domain != null ? this.domain.hashCode() : 0);
        hash = 73 * hash + (this.world != null ? this.world.hashCode() : 0);
        hash = 73 * hash + (this.category != null ? this.category.hashCode() : 0);
        hash = 73 * hash + (this.statistic != null ? this.statistic.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof AbstractStat == false) {
            return false;
        }
        final AbstractStat other = (AbstractStat) obj;
        if (this.domain != other.domain && (this.domain == null || !this.domain.equals(other.domain))) {
            return false;
        }
        if (this.world != other.world && (this.world == null || !this.world.equals(other.world))) {
            return false;
        }
        if (this.category != other.category && (this.category == null || !this.category.equals(other.category))) {
            return false;
        }
        if (this.statistic != other.statistic && (this.statistic == null || !this.statistic.equals(other.statistic))) {
            return false;
        }
        return true;
    }
    
    

}
