package com.tehbeard.beardstat.dataproviders;

import java.util.UUID;

/**
 * Results of a search initiated by a ProviderQuer
 * @author James
 */
public class ProviderQueryResult {

    public final int dbid;
    public final String name;
    public final String type;
    public final UUID uuid;

    public ProviderQueryResult(int dbid, String name, String type, UUID uuid) {
        this.dbid = dbid;
        this.name = name;
        this.type = type;
        this.uuid = uuid;

    }

    public ProviderQuery asProviderQuery() {
        return new ProviderQuery(name, type, uuid, false);
    }
}
