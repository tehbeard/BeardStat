package com.tehbeard.BeardStat.containers;


import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

import com.tehbeard.BeardStat.BeardStat;

import me.tehbeard.utils.expressions.VariableProvider;

/**
 * Represents a collection of player statistics
 * @author James
 *
 */
public class EntityStatBlob implements VariableProvider{

    private static Map<String,String> dynamics = new HashMap<String, String>();

    private static Map<String,String> dynamicsSaved = new HashMap<String, String>();

    public static void addDynamicStat(String stat,String expr){
        dynamics.put(stat,expr);
    }

    public static void addDynamicSavedStat(String stat,String expr){
        dynamicsSaved.put(stat,expr);
    }

    private void addDynamics(){
        for(Entry<String, String> entry  : dynamics.entrySet()){

            String[] parts = entry.getKey().split("\\.");

            String domain = BeardStat.DEFAULT_DOMAIN;
            String world = BeardStat.GLOBAL_WORLD;
            String cat = null;
            String stat = null;
            if(parts.length == 2){
                BeardStat.printDebugCon("Old dynamic stat found, adding to global world");
                cat = parts[0];
                stat = parts[1];
            }
            else if(parts.length == 4){
                domain = parts[0];
                world = parts[1];
                cat = parts[2];
                stat = parts[3];
            }
            addStat(new DynamicStat(domain,world,cat,stat,entry.getValue()));
        }

        //dynamics that will be saved to database
        for(Entry<String, String> entry  : dynamicsSaved.entrySet()){

            String[] parts = entry.getKey().split("\\.");

            String domain = BeardStat.DEFAULT_DOMAIN;
            String world = BeardStat.GLOBAL_WORLD;
            String cat = null;
            String stat = null;
            if(parts.length == 2){
                BeardStat.printDebugCon("Old dynamic stat found, adding to global world");
                cat = parts[0];
                stat = parts[1];
            }
            else if(parts.length == 4){
                domain = parts[0];
                world = parts[1];
                cat = parts[2];
                stat = parts[3];
            }
            addStat(new DynamicStat(domain,world,cat,stat,entry.getValue(),true));
        }
    }

    private Map<String,IStat> stats = new ConcurrentHashMap<String, IStat>();

    private int entityId;
    private String name;
    private String type;

    public String getName() {
        return name;
    }

    public int getEntityID() {
        return entityId;
    }


    /**
     * 
     * @param name Players name
     * @param ID playerID in database
     */
    public EntityStatBlob(String name,int entityId,String type){
        this.name = name;
        this.entityId=entityId;
        this.type = type;
        addDynamics();
    }

    /**
     * add stat
     * @param stat
     */
    public void addStat(IStat stat){
        stats.put(stat.getDomain() + "." + stat.getWorld() + "." + stat.getCategory() + "." + stat.getStatistic(),stat);
        stat.setOwner(this);
    }

    /**
     * Get a players stat, creates new object if not found.
     * @param statistic
     * @return
     */
    public IStat getStat(String domain,String world,String category,String statistic){
        IStat psn = stats.get(domain + "." +world + "." + category + "." + statistic);
        if(psn!= null){
            return psn;
        }
        psn = new StaticStat(domain,world,category,statistic,0);
        addStat(psn);
        return psn;
    }

    public StatVector getStats(String domain,String world,String category,String statistic){
        String pattern = starToRegex(domain);
        pattern += "\\." + starToRegex(world);
        pattern += "\\." + starToRegex(category);
        pattern += "\\." + starToRegex(statistic);

        return getStats(domain, world, category, statistic, pattern);
    }

    public StatVector getStats(String domain,String world,String category,String statistic,String regex){
        StatVector vector = new StatVector(domain, world, category, statistic);
        for( Entry<String, IStat> e : stats.entrySet()){
            if(Pattern.matches(regex, e.getKey())){
                vector.add(e.getValue());
            }
        }
        return vector;
    }

    private String starToRegex(String s){
        if(s.equals("*")){
            return "[a-zA-Z0-9_]*";
        }
        return s;
    }

    /**
     * Return all the stats!
     * @return
     */
    public Collection<IStat> getStats(){
        return  stats.values();
    }

    public boolean hasStat(String domain,String world,String category,String statistic){
        return stats.containsKey(domain + "." +world + "." + category + "." + statistic);

    }

    public int resolveVariable(String var) {
        String[] parts = var.split("\\.");
        return getStat(
                parts[0],
                parts[1],
                parts[2],
                parts[3]
                ).getValue();
    }

    public String getType() {
        return type;
    }


    public EntityStatBlob cloneForArchive(){
        EntityStatBlob blob = new EntityStatBlob(name, entityId, type);
        blob.stats.clear();
        for(IStat stat : stats.values()){
            if(stat.isArchive()){
                BeardStat.printDebugCon("Archiving stat " + stat.getDomain() + "." + stat.getWorld() + "." + stat.getCategory() + "." + stat.getStatistic() + " = " + stat.getValue());
                IStat is = stat.clone();
                if(is!=null){
                    BeardStat.printDebugCon("Stat added");
                    blob.addStat(is);
                    stat.clearArchive();
                }
            }
        }
        BeardStat.printDebugCon("End cloning");
        return blob;
    }

}