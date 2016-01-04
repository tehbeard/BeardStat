package com.tehbeard.beardstat;

import com.tehbeard.beardstat.cfg.InjectConfig;

/**
 * Contains JDBC database connection information, populated by the implementing plugin
 * @author James
 */
public class DatabaseConfiguration {

    //Database type
    @InjectConfig("type")
    public String databaseType;
    
    //Hostname
    @InjectConfig("host")
    public String host;
    //Username
    @InjectConfig("username")
    public String username;
    //Password "" for blank
    @InjectConfig("password")
    public String password;
    //Database name
    @InjectConfig("database")
    public String database;
    //Table prefix (*_entity)
    @InjectConfig("prefix")
    public String tablePrefix;
    //Mysql Port
    @InjectConfig("port")
    public int port;
    //Enable auto backups for migrations
    @InjectConfig("backups")
    public boolean backups;
    //Run UUID update script
    @InjectConfig("uuidUpdate")
    @Deprecated
    public boolean runUUIDUpdate;
    
    //Database version this BeardStat implementation relies on.
    public final int latestVersion;
    
    public DatabaseConfiguration(int latestVersion){
        this.latestVersion = latestVersion;
    }

    @Override
    public String toString() {
        return "DatabaseConfiguration{" + "databaseType=" + databaseType + ", host=" + host + ", username=" + username + ", database=" + database + ", tablePrefix=" + tablePrefix + ", port=" + port + ", backups=" + backups + ", latestVersion=" + latestVersion + '}';
    }
    
    
}
