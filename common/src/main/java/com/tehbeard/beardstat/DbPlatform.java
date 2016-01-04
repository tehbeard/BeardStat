package com.tehbeard.beardstat;

import com.tehbeard.beardstat.containers.EntityStatBlob;
import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Implementation agnostic provider for certain functions BeardStat common lib needs
 * @author James
 */
public interface DbPlatform {

    /**
     * @return logger for this implementation
     */
    public Logger getLogger();

    /**
     * @return Path to config folder
     */
    public File getDataFolder();
    /**
     * Handle a mysql error
     * @param sqlException
     * @param string 
     */
    public void mysqlError(SQLException sqlException, String string);

    /**
     * @param string resource path name
     * @return resource from the JAR
     */
    public InputStream getResource(String string);

    /**
     * Trigger a config save
     */
    public void saveConfig();
    /**
     * Check if a config value exists
     * @param key
     * @return 
     */
    public boolean configValueIsSet(String key);
    /**
     * Set a config value
     * @param key
     * @param val 
     */
    public void configValueSet(String key, Object val);
    /**
     * Trigger a load event for an EntityStatBlob
     * @param esb 
     */
    public void loadEvent(EntityStatBlob esb);

    /**
     * Check if player is online
     * @param player
     * @return 
     */
    public boolean isPlayerOnline(String player);

    /**
     * Get world name for a player
     * @param entityName
     * @return 
     */
    public String getWorldForPlayer(String entityName);
    
    /**
     * Generic error handler
     * @param e 
     */
    public void handleError(Exception e);
    
}
