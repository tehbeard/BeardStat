package me.tehbeard.BeardStat.containers;

import me.tehbeard.utils.expressions.InFixExpression;

/**
 * Dynamic player stats generated from composites of other player stats.
 * A Dynamic player stat is only stored if expressly set to be stored.
 * it is computed at runtime using an expression bound to that stat.
 * @author James
 *
 */
public class DynamicPlayerStat implements PlayerStat {


	private String domain;
	private String world;
    private String cat;
    private String stat;
    private PlayerStatBlob owner;
    private InFixExpression expression;
    
    private boolean archive = false;
    
    public DynamicPlayerStat(String cat,String stat,String expr){
        this(cat,stat,expr,false);
        

    }
    
    public DynamicPlayerStat(String cat,String stat,String expr,boolean archive){
        this.cat = cat;
        this.stat = stat;
        this.expression = new InFixExpression(expr);
        this.archive = archive;
        

    }


    public int getValue() {
        return expression.getValue(owner);
    }



    public void setValue(int value) {}



    public String getStatistic() {
        return stat;
    }



    public void incrementStat(int i) {}



    public void decrementStat(int i) {}



    public String getCat() {
        return cat;
    }



    public void clearArchive() {}



    public boolean isArchive() {
        return archive;
    }



    public PlayerStatBlob getOwner() {
        return owner;
    }



    public void setOwner(PlayerStatBlob playerStatBlob) {
        owner = playerStatBlob;
    }

    public void archive() {
    }
    
    
    @Override
	public void setDomain(String domain) {
		this.domain = domain;
	}



	@Override
	public String getDomain() {
		// TODO Auto-generated method stub
		return domain;
	}



	@Override
	public void setWorld(String world) {
		this.world = world;
	}



	@Override
	public String getWorld() {
		return world;
	}
}
