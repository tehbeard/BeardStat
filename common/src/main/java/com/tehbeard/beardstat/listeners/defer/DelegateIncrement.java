package com.tehbeard.beardstat.listeners.defer;

import net.dragonzone.promise.Delegate;
import net.dragonzone.promise.Promise;

import com.tehbeard.beardstat.containers.EntityStatBlob;
import com.tehbeard.beardstat.containers.meta.CategoryPointer;
import com.tehbeard.beardstat.containers.meta.DomainPointer;
import com.tehbeard.beardstat.containers.meta.StatPointer;
import com.tehbeard.beardstat.containers.meta.WorldPointer;

/**
 * Delegate incrementing a stat to a later date
 *
 * @author James
 *
 */
public class DelegateIncrement implements Delegate<Void, Promise<EntityStatBlob>> {

    private final DomainPointer domain;
    private final WorldPointer world;
    private final CategoryPointer category;
    private final StatPointer name;
    private final int increment;

    public DelegateIncrement(DomainPointer domain, WorldPointer world, CategoryPointer category, StatPointer name, int decrement) {
        super();
        this.domain = domain;
        this.world = world;
        this.category = category;
        this.name = name;
        this.increment = decrement;
    }

    @Override
    public <P extends Promise<EntityStatBlob>> Void invoke(P params) {
        params.getValue().getStat(this.domain, this.world, this.category, this.name).incrementStat(this.increment);
        return null;
    }

}
