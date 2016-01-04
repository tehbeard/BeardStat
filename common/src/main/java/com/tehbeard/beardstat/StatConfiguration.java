package com.tehbeard.beardstat;

import com.tehbeard.beardstat.cfg.InjectConfig;

/**
 * generic configuration
 * @author James
 */
public class StatConfiguration {
    
    //Enable extraneous debug messages
    @InjectConfig("general.debug")
    public boolean debugMode;
    
    //Dump more to console
    @InjectConfig("general.verbose")
    public boolean verboseMode;
    
    //Loaded config version
    @InjectConfig("stats.configversion")
    public int configVersion;
    
    //Loaded database type
    @InjectConfig("stats.database.type")
    public String dbType;
    
    //Log level
    @InjectConfig("general.loglevel")
    public String logLevel = "INFO";
    

    @Override
    public String toString() {
        return "StatConfiguration [debugMode=" + debugMode + ", verboseMode=" + verboseMode + ", configVersion="
                + configVersion + ", dbType=" + dbType + ", logLevel=" + logLevel + "]";
    }
    
    
}
