package com.tehbeard.beardstat.containers;

import com.tehbeard.beardstat.containers.meta.CategoryPointer;
import com.tehbeard.beardstat.containers.meta.DomainPointer;
import com.tehbeard.beardstat.containers.meta.StatPointer;
import com.tehbeard.beardstat.containers.meta.WorldPointer;
import com.tehbeard.utils.expressions.InFixExpression;

/**
 * Dynamic player stats generated from composites of other player stats. A
 * Dynamic player stat is only stored if expressly set to be stored. it is
 * computed at runtime using an expression bound to that stat.
 *
 * @author James
 *
 */
public class DynamicStat extends AbstractStat {

    private InFixExpression expression;
    String expr;

    private boolean archive = false;

    public DynamicStat(DomainPointer domain, WorldPointer world, CategoryPointer cat, StatPointer statistic, String expr) {
        this(domain, world, cat, statistic, expr, false);
    }

    public DynamicStat(DomainPointer domain, WorldPointer world, CategoryPointer cat, StatPointer statistic, String expr, boolean archive) {
        super(domain, world, cat, statistic);
        this.expression = new InFixExpression(expr);
        this.archive = archive;
        this.expr = expr;
    }

    @Override
    public int getValue() {
        return this.expression.getValue(this.getOwner());
    }

    @Override
    public void setValue(int value) {
    }

    @Override
    public IStat copyStat() {
        return new StaticStat(this.getDomain(), this.getWorld(), this.getCategory(), this.getStatistic(), getValue());

    }

    public IStat duplicateForPlayer(EntityStatBlob owner) {
        DynamicStat ns = new DynamicStat(this.getDomain(), this.getWorld(), this.getCategory(), this.getStatistic(), this.expr, this.archive);
        ns.setOwner(owner);
        return ns;
    }

    @Override
    public void clearArchive() {}

    @Override
    public void archive() {}

    @Override
    public boolean isArchive() {
        return archive;
    }

    @Override
    public void incrementStat(int i) {}

    @Override
    public void decrementStat(int i) {}
}
