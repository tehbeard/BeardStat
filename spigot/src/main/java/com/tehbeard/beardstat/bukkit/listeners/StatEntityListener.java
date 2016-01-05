package com.tehbeard.beardstat.bukkit.listeners;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ComplexEntityPart;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.ThrownPotion;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent.RegainReason;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.projectiles.BlockProjectileSource;
import org.bukkit.projectiles.ProjectileSource;

import com.tehbeard.beardstat.bukkit.BukkitPlugin;
import com.tehbeard.beardstat.Refs;
import com.tehbeard.beardstat.manager.EntityStatManager;
import com.tehbeard.beardstat.bukkit.utils.StatUtils;
import com.tehbeard.beardstat.containers.meta.CategoryPointer;
import com.tehbeard.beardstat.containers.meta.StatPointer;

public class StatEntityListener extends StatListener {

    public StatEntityListener( EntityStatManager playerStatManager, BukkitPlugin plugin) {
        super(playerStatManager, plugin);
    }

    private final CategoryPointer[] DAMAGELBLS = { CategoryPointer.get("damagedealt"), CategoryPointer.get("damagetaken") };
    private final CategoryPointer[] KDLBLS     = { CategoryPointer.get("kills"), CategoryPointer.get("deaths") };

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamage(EntityDamageEvent event) {

        if (event.isCancelled()) {
            return;
        }

        processEntityDamage(event, this.DAMAGELBLS, false);

    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDeath(EntityDeathEvent event) {

        

        EntityDamageEvent lastCause = event.getEntity().getLastDamageCause();
        if (lastCause != null) {
            processEntityDamage(lastCause, this.KDLBLS, true);
        }

    }

    /**
     * ATTACKER NAME, ATTACKED NAME
     * 
     * @param event
     * @param category
     */
    private void processEntityDamage(EntityDamageEvent event, CategoryPointer[] category, boolean forceOne) {
        // Initialise base stats
        Entity attacked = event.getEntity();
        DamageCause cause = event.getCause();
        int amount = forceOne ? 1 : (int) Math.floor(event.getDamage());
        Entity attacker = null;
        Projectile projectile = null;

        boolean dispenserFired = false;
        // grab the attacker if one exists.
        if (event instanceof EntityDamageByEntityEvent) {
            attacker = ((EntityDamageByEntityEvent) event).getDamager();
        }
        // Projectile -> projectile + attacker
        if (attacker instanceof Projectile) {
            projectile = (Projectile) attacker;
            ProjectileSource projectileSource = projectile.getShooter();
            if(projectileSource instanceof Entity){
                attacker = (Entity)projectileSource;
            }
            if(projectileSource instanceof BlockProjectileSource){
                dispenserFired = true;
            }
        }

        // dragon fixer
        if (attacker instanceof ComplexEntityPart) {
            attacker = ((ComplexEntityPart) attacker).getParent();
        }
        if (attacked instanceof ComplexEntityPart) {
            attacked = ((ComplexEntityPart) attacked).getParent();
        }

        // get the player
        Player player = attacked instanceof Player ? (Player) attacked : attacker instanceof Player ? (Player) attacker
                : null;
        Entity other = attacked instanceof Player ? attacker : attacker instanceof Player ? attacked : null;
        int idx = attacker instanceof Player ? 0 : 1;

        if (player == null) {
            return;
        }// kill if no player involved

        if (event.isCancelled() || !shouldTrackPlayer(player, Refs.TRACK_ENTITY_PREFIX + category[idx])) {
            return;
        }

        // Damage cause if not from 
        if (cause != DamageCause.PROJECTILE) {
            StatUtils.instance.modifyStatPlayer(player, category[idx], StatPointer.get(cause.toString().toLowerCase().replace("_", "")), amount);
        }
        // Entity damage
        if ((other != null) && !(other instanceof Player)) {
            StatUtils.instance.modifyStatEntity(player, category[idx], other, amount);
        }
        // Projectile damage
        if (projectile != null) {
            StatUtils.instance.modifyStatEntity(player, category[idx], projectile, amount);
        }
        // Dispenser
        if(dispenserFired){
            StatUtils.instance.modifyStatPlayer(player, category[idx], StatPointer.get("minecraft:dispenser"), amount);
        }

        //PvP
        if ((attacker instanceof Player) && (attacked instanceof Player)) {
            StatUtils.instance.modifyStatPlayer((Player)attacker, category[0], StatPointer.get("pvp"), 1);
            StatUtils.instance.modifyStatPlayer((Player)attacked, category[1], StatPointer.get("pvp"), 1);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityRegainHealth(EntityRegainHealthEvent event) {

        if ((event.isCancelled() == false) && (event.getEntity() instanceof Player)) {

            int amount = (int) Math.floor(event.getAmount());
            RegainReason reason = event.getRegainReason();
            Player player = (Player) event.getEntity();

            if (!shouldTrackPlayer(player, Refs.TRACK_ENTITY_HEAL)) {
                return;
            }

            StatUtils.instance.modifyStatPlayer(player, Refs.CAT_STAT, StatPointer.get("damagehealed"), amount);
            if (reason != RegainReason.CUSTOM) {
                StatUtils.instance.modifyStatPlayer(player, Refs.CAT_STAT, StatPointer.get("heal" + reason.toString().replace("_", "").toLowerCase()), amount);
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityTame(EntityTameEvent event) {
        if ((event.isCancelled() == false) && (event.getOwner() instanceof Player)) {
            if (event.isCancelled() || !shouldTrackPlayer((Player) event.getOwner(),Refs.TRACK_ENTITY_TAME)) {
                return;
            }

            StatUtils.instance.modifyStatEntity((Player)event.getOwner(), CategoryPointer.get("tame"), event.getEntity(), 1);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPotionSplash(PotionSplashEvent event) {

        if (event.isCancelled()) {
            return;
        }

        ThrownPotion potion = event.getPotion();

        for (Entity e : event.getAffectedEntities()) {
            if (e instanceof Player) {
                Player p = (Player) e;

                if (!shouldTrackPlayer(p,Refs.TRACK_ENTITY_POTION)) {
                    continue;
                }

                StatUtils.instance.modifyStatPlayer(p, Refs.CAT_POTIONS, StatPointer.get("splashhit"), 1);
                // added per potion details
                for (PotionEffect potionEffect : potion.getEffects()) {
                    StatUtils.instance.modifyStatPotion(p, Refs.CAT_POTIONS,potionEffect, 1);
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBowShoot(EntityShootBowEvent event) {

        if ((event.isCancelled())) {
            return;
        }

        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();

            if (!shouldTrackPlayer(player, Refs.TRACK_ENTITY_BOW)) {
                return;
            }

            StatUtils.instance.modifyStatPlayer(player, Refs.CAT_BOW, StatPointer.get("shots"), 1);

            if (event.getBow().containsEnchantment(Enchantment.ARROW_FIRE)) {
                StatUtils.instance.modifyStatPlayer(player, Refs.CAT_BOW, StatPointer.get("fireshots"), 1);
            }

            if (event.getBow().containsEnchantment(Enchantment.ARROW_INFINITE)) {
                StatUtils.instance.modifyStatPlayer(player, Refs.CAT_BOW, StatPointer.get("infiniteshots"), 1);
            }

        }

    }
}
