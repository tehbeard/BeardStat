package com.tehbeard.beardstat.bukkit.identifier;

import com.tehbeard.beardstat.containers.meta.StatPointer;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

/**
 * Interface for a identifier generator, which returns a string id for various given resources (blocks, items, entities).
 * This is so we can use different and/or multiple sources for generating an id (homebrew, flatfile, api, mux)
 * @author James
 *
 */
public interface IIdentifierGenerator {
    
    /**
     * Given a block/item id, returns the appropriate string id, or null if not found.
     * @param id
     * @return
     */
    public StatPointer keyForItemstack(ItemStack stack);
    
    public StatPointer keyForBlock(Block block);
    
    /**
     * Returns the key for an entity
     * @param entity
     * @return
     */
    public StatPointer keyForEntity(Entity entity);

    public StatPointer keyForPotionEffect(PotionEffect effect);

}
