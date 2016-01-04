package com.tehbeard.beardstat;

import com.tehbeard.beardstat.containers.meta.CategoryPointer;
import com.tehbeard.beardstat.containers.meta.DomainPointer;
import com.tehbeard.beardstat.containers.meta.StatPointer;
import com.tehbeard.beardstat.containers.meta.WorldPointer;

/**
 * Global references class for frequently used static values.
 * @author James
 */
public class Refs {

        public static final String PERM_COMMAND_PLAYED_OTHER = "stat.command.played.other";
        public static final String PERM_COMMAND_STAT_OTHER = "stat.command.stat.other";
        // Default values for domain and world
        public static final DomainPointer DEFAULT_DOMAIN = DomainPointer.get("default");
        public static final WorldPointer GLOBAL_WORLD = WorldPointer.get("__global__");

        //Track types
        public static final String TRACK_BLOCK_PLACE = "block_place";
        public static final String TRACK_BLOCK_BREAK = "block_break";

        public static final String TRACK_ITEM_CRAFT = "item_craft";
        public static final String TRACK_ITEM_DROP = "item_drop";
        public static final String TRACK_ITEM_PICKUP = "item_pickup";
        //Entity 
        public static final String TRACK_ENTITY_PREFIX = "entity_";
        public static final String TRACK_ENTITY_HEAL = "entity_heal";
        public static final String TRACK_ENTITY_TAME = "entity_tame";
        public static final String TRACK_ENTITY_POTION = "entity_potion";
        public static final String TRACK_ENTITY_BOW = "entity_bow";
        public static final String TRACK_ENTITY_INTERACT = "entity_interact";
        public static final String TRACK_ENTITY_SHEAR = "entity_shear";
        //Player
        public static final String TRACK_PLAYER_ARM = "player_arm";
        public static final String TRACK_PLAYER_FISH = "player_fish";
        public static final String TRACK_PLAYER_MOVE = "player_move";
        public static final String TRACK_PLAYER_BUCKET = "player_bucket";
        public static final String TRACK_PLAYER_USE = "player_use";
        public static final String TRACK_PLAYER_EXP = "player_exp";
        public static final String TRACK_PLAYER_CONSUME = "player_consume";
        public static final String TRACK_PLAYER_TIME = "player_time";
        
        //Categories
        public static final CategoryPointer CAT_ITEM_DROP = CategoryPointer.get("itemdrop");
        public static final CategoryPointer CAT_ITEM_PICKUP = CategoryPointer.get("itempickup");
        public static final CategoryPointer CAT_ITEM_USE = CategoryPointer.get("itemuse");
        
        public static final CategoryPointer CAT_FILL = CategoryPointer.get("fill");
        public static final CategoryPointer CAT_EMPTY = CategoryPointer.get("empty");
        
        public static final CategoryPointer CAT_STAT = CategoryPointer.get("stats");
        public static final CategoryPointer CAT_FISHING = CategoryPointer.get("fishing");
        public static final CategoryPointer CAT_INTERACT = CategoryPointer.get("interact");
        
        public static final CategoryPointer CAT_DYE = CategoryPointer.get("dye");
        
        public static final CategoryPointer CAT_DYE_WOLF = CategoryPointer.get("wolfdye");
        
        public static final CategoryPointer CAT_PLANT = CategoryPointer.get("plant");
        
        public static final CategoryPointer CAT_XP = CategoryPointer.get("exp");
        
        public static final CategoryPointer CAT_SHEAR = CategoryPointer.get("sheared");
        public static final CategoryPointer CAT_ENCHANT = CategoryPointer.get("enchant");
        
        public static final CategoryPointer CAT_CONSUME = CategoryPointer.get("consume");
    
        public static final StatPointer STAT_CHAT_LETTERS = StatPointer.get("chatletters");
        public static final StatPointer STAT_CHAT = StatPointer.get("chat");
        
        public static final StatPointer STAT_ARMSWING = StatPointer.get("armswing");
        
        public static final StatPointer STAT_LOGIN_FIRST = StatPointer.get("firstlogin");
        public static final StatPointer STAT_LOGIN_LAST = StatPointer.get("lastlogin");
        public static final StatPointer STAT_LOGIN = StatPointer.get("login");
        public static final StatPointer STAT_LOGOUT_LAST = StatPointer.get("lastlogout");
        
        public static final StatPointer STAT_FISH_CAUGHT = StatPointer.get("fishcaught");
        
        public static final StatPointer STAT_PLAYER_KICKS = StatPointer.get("kicks");
        
        public static final StatPointer STAT_MOVE = StatPointer.get("move");
        
        
    }
