package com.tehbeard.beardstat.bukkit.utils;


import net.dragonzone.promise.Promise;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.tehbeard.beardstat.Refs;
import com.tehbeard.beardstat.bukkit.BukkitPlugin;
import com.tehbeard.beardstat.bukkit.identifier.IIdentifierGenerator;
import com.tehbeard.beardstat.containers.EntityStatBlob;
import com.tehbeard.beardstat.containers.meta.CategoryPointer;
import com.tehbeard.beardstat.containers.meta.StatPointer;
import com.tehbeard.beardstat.containers.meta.WorldPointer;
import com.tehbeard.beardstat.listeners.defer.DelegateDecrement;
import com.tehbeard.beardstat.listeners.defer.DelegateIncrement;
import com.tehbeard.beardstat.listeners.defer.DelegateSet;
import com.tehbeard.beardstat.manager.EntityStatManager;

/**
 * Provides helper methods for recording stats
 * @author James
 *
 * Methods that take a {@link Player} object use the this.domain domain, and world provided by the player.
 * modifyXXX methods adjust stats relatively. if you pass in +3, the stat is incremented by 3.
 * setXXX methods adjust stats absolutely. If you pass 50, the stat is now 50. 
 */
public class StatUtils {

    private static EntityStatManager manager = null;
    private static IIdentifierGenerator generator = null;

    public static void setManager(BukkitPlugin plugin){
        StatUtils.generator = plugin.getStatGenerator();
        StatUtils.manager = plugin.getStatManager();
    }
    
    public static final StatUtils instance = new StatUtils();
    
    /**
     * Increment/decrement a stat based on a {@link PotionEffect}
     * @param player
     * @param category
     * @param effect
     * @param amount 
     */
    public void modifyStatPotion(Player player, CategoryPointer category, PotionEffect effect, int amount){
        modifyStatPlayer(player, category, generator.keyForPotionEffect(effect), amount);
    }
    
    /**
     * Increment/decrement a stat based on a {@link Entity}
     * @param player
     * @param category
     * @param entity
     * @param amount
     */
    public void modifyStatEntity(Player player, CategoryPointer category, Entity entity, int amount){
        modifyStatPlayer(player, category, generator.keyForEntity(entity), amount);
    }
    
    /**
     * Sets a players stat to the provided value
     * @param player
     * @param category
     * @param statistic
     * @param amount
     */
    public void setPlayerStat(Player player,CategoryPointer category, StatPointer statistic, int amount){
        modifyStatPlayer(player,  
                category, 
                statistic,
                amount);
    }
    
    /**
     * Increment/decrement a stat
     * @param player
     * @param category
     * @param statistic
     * @param amount
     */
    public void modifyStatPlayer(Player player, CategoryPointer category, StatPointer statistic, int amount){
        modifyStat(
                player, 
                WorldPointer.get(player.getWorld().getName()), 
                category, 
                statistic,
                amount);
    }

    /**
     * Increment/decrement a stat based on a {@link ItemStack}
     * @param player
     * @param category
     * @param item
     * @param amount
     */
    public void modifyStatItem(Player player, CategoryPointer category, ItemStack item, int amount){
        modifyStatPlayer(player, category, generator.keyForItemstack(item), amount);
        
    }

    /**
     * Increment/decrement a stat based on a {@link Block}
     * @param player
     * @param domain
     * @param world
     * @param category
     * @param block
     * @param amount
     */
    
    public void modifyStatBlock(Player player, CategoryPointer category, Block block, int amount){
        modifyStatPlayer(player, category, generator.keyForBlock(block), amount);
    }

    /**
     * Increment/decrement a stat
     * @param uuid
     * @param domain
     * @param world
     * @param category
     * @param baseId
     * @param metaId
     * @param amount
     */
    public void modifyStat(Player player, WorldPointer world, CategoryPointer category, StatPointer statistic, int amount){
        boolean inc = (amount > 0);
        int am = Math.abs(amount);

        if(inc){
            increment(player, world, category, statistic, am);
        }
        else
        {
            decrement(player, world, category, statistic, am);
        }
    }

    /**
     * Increments a stat
     * @param player
     * @param domain
     * @param world
     * @param category
     * @param statistic
     * @param amount
     */
    public void increment(Player player, WorldPointer world, CategoryPointer category, StatPointer statistic, int amount){
        Promise<EntityStatBlob> blob = manager.getPlayer(player.getName(), player.getUniqueId());
        blob.onResolve(new DelegateIncrement(Refs.DEFAULT_DOMAIN,world,category,statistic,amount));
    }

    /**
     * Decrements a stat
     * @param player
     * @param world
     * @param category
     * @param statistic
     * @param amount
     */
    public void decrement(Player player, WorldPointer world, CategoryPointer category, StatPointer statistic, int amount){
        Promise<EntityStatBlob> blob = manager.getPlayer(player.getName(), player.getUniqueId());
        blob.onResolve(new DelegateDecrement(Refs.DEFAULT_DOMAIN,world,category,statistic,amount));
    }
    
    /**
     * Sets a stat
     * @param player
     * @param world
     * @param category
     * @param statistic
     * @param amount
     */
    public void set(Player player, WorldPointer world, CategoryPointer category, StatPointer statistic, int amount){
        Promise<EntityStatBlob> blob = manager.getPlayer(player.getName(), player.getUniqueId());
        blob.onResolve(new DelegateSet(Refs.DEFAULT_DOMAIN,world,category,statistic,amount));
    }
    
    
}
