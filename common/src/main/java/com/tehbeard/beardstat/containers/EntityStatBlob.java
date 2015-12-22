package com.tehbeard.beardstat.containers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.tehbeard.beardstat.Refs;
import com.tehbeard.beardstat.containers.documents.IStatDocument;
import com.tehbeard.beardstat.containers.documents.docfile.DocumentFile;
import com.tehbeard.beardstat.containers.documents.docfile.DocumentFileRef;
import com.tehbeard.beardstat.containers.meta.CategoryPointer;
import com.tehbeard.beardstat.containers.meta.DomainPointer;
import com.tehbeard.beardstat.containers.meta.StatPointer;
import com.tehbeard.beardstat.containers.meta.WorldPointer;
import com.tehbeard.beardstat.dataproviders.IStatDataProvider;
import com.tehbeard.utils.expressions.VariableProvider;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a collection of statistics bound to an entity Currently only used for Players.
 *
 * @author James
 *
 */
public class EntityStatBlob implements VariableProvider {

    private Set<IStat> stats = new HashSet<IStat>();
    private int entityId;
    private String name;
    private String type;
    private UUID uuid;
    private IStatDataProvider provider;
    private Map<String, DocumentFileRef> files = new HashMap<String, DocumentFileRef>();

    /**
     * The name of the entity this EntityStatBlob is associated with.
     *
     * @return
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the internal database id of the entity, not for public use.
     *
     * @return
     */
    public int getEntityID() {
        return this.entityId;
    }

    /**
     *
     * @param name name of the entity
     * @param entityId internal database id
     * @param type
     * @param uuid
     */
    public EntityStatBlob(String name, int entityId, String type, UUID uuid, IStatDataProvider provider) {
        this.name = name;
        this.entityId = entityId;
        this.type = type;
        this.uuid = uuid;
        this.provider = provider;
    }

    /**
     * Adds a IStat object to this EntityStatBlob
     *
     * @param stat
     */
    public void addStat(IStat stat) {
        this.stats.add(stat);
        stat.setOwner(this);
    }

    /**
     * Returns a stat object from the default (BeardStat) domain, see other getStat() for details.
     *
     * @param world
     * @param category
     * @param statistic
     * @return
     */
    public IStat getStat(WorldPointer world, CategoryPointer category, StatPointer statistic) {
        return getStat(Refs.DEFAULT_DOMAIN, world, category, statistic);
    }

    /**
     * Returns a stat object for the supplied coordinates
     *
     * @param domain domain of the stats,
     * @param world world name stat is under
     * @param category category stat is under
     * @param statistic name of statistic
     * @return
     */
    public IStat getStat(DomainPointer domain, WorldPointer world, CategoryPointer category, StatPointer statistic) {
        for(IStat s : this.stats){
            if(
                s.getDomain().equals(domain) &&
                s.getWorld().equals(world) &&
                s.getCategory().equals(category) &&
                s.getStatistic().equals(statistic)
            ){
                return s;
            }
        }
        IStat psn = new StaticStat(domain, world, category, statistic, 0);
        addStat(psn);
        return psn;
    }

    /**
     * Query this blob for a {@link StatVector}, a {@link StatVector} combines multiple stats into one easy to access object {@link StatVector} supports the use of regex, with the shortcut "*"
     * to denote all possible values (substituted for ".*" in regex engine) Defaults to readonly mode, any mutators called on this {@link StatVector} will throw {@link IllegalStateException}
     * if readOnly is true
     *
     * @param domain
     * @param world
     * @param category
     * @param statistic
     * @return
     */
    public StatVector getStats(DomainPointer domain, WorldPointer world, CategoryPointer category, StatPointer statistic) {
        throw new UnsupportedOperationException();
    }

    /**
     * Return all the stats!
     *
     * @return
     */
    public Collection<IStat> getStats() {
        return this.stats;
    }

    /**
     * Checks if a stat under these coordinates has been recorded.
     *
     * @param domain
     * @param world
     * @param category
     * @param statistic
     * @return
     */
    public boolean hasStat(DomainPointer domain, WorldPointer world, CategoryPointer category, StatPointer statistic) {
        IStat s = new StaticStat(domain, world, category, statistic, 0);
        return this.stats.contains(s);
    }

    @Override
    public int resolveVariable(String var) {
        String[] parts = var.split("::");
        DomainPointer domain = Refs.DEFAULT_DOMAIN;
        WorldPointer world = Refs.GLOBAL_WORLD;
        CategoryPointer cat;
        StatPointer stat;
        if (parts.length == 4) {
            domain = DomainPointer.get(parts[0]);
            world = WorldPointer.get(parts[1]);
            cat = CategoryPointer.get(parts[2]);
            stat = StatPointer.get(parts[3]);
        }
        if (parts.length == 2) {
            cat = CategoryPointer.get(parts[0]);
            stat = StatPointer.get(parts[1]);
        } else {
            throw new IllegalStateException("Attempt to parse invalid varriable " + var);
        }
        return getStats(domain, world, cat, stat).getValue();
    }

    /**
     * Return the entity type of this EntityStatBlob
     *
     * @return
     */
    public String getType() {
        return this.type;
    }

    /**
     * Used by internal BeardStat methods, DO NOT USE.
     * @return
     */
    public StatBlobRecord cloneForArchive() {
        StatBlobRecord record = new StatBlobRecord(entityId);

        for (IStat stat : this.stats) {
            if (stat.isArchive()) {
                IStat is = stat.copyStat();
                if (is != null) {
                    record.stats.add(is);
                    stat.clearArchive();
                }
            }
        }

        for (DocumentFileRef ref : files.values()) {
            if (ref.getRef().shouldArchive()) {
                record.files.add(ref);
                ref.getRef().clearArchiveFlag();
            }

        }
        return record;
    }

    @Override
    public int[] resolveReference(String array) {
        throw new UnsupportedOperationException("Array support not yet available."); //TODO - Fix this.
    }
    
    public UUID getUUID(){

        return uuid;
    }

    /**
     * Getter method for getting documents associated with a statblob.
     * IMPORTANT: DocumentFile's are cached internally, DO NOT CACHE EXTERNALLY, DocumentFile's are valid only until a save occurs.
     * After a save there is no guarantee another server might have manipulated the document.
     * @param domain
     * @param key
     * @param docClass class of the document, used for instantiation if no document found. Pass null to not instantiate a document
     * @return a DocumentFile for a particular revision of the document or null if an error occurs / no document found and docClass is null.
     */
    public DocumentFile getDocument(DomainPointer domain, String key,Class<? extends IStatDocument> docClass) {
        String code = domain.getGameTag() + "::" + key;
        //Load if not cached.
        if (!files.containsKey(code)) {
            DocumentFile docFile = provider.pullDocument(entityId, domain, key);
            //New File?
            if(docFile == null){
                if(docClass==null){return null;}
                try {
                    IStatDocument newDoc = docClass.newInstance();
                    docFile = new DocumentFile(domain, key, newDoc);
                } catch (InstantiationException ex) {
                    Logger.getLogger(EntityStatBlob.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(EntityStatBlob.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                }
            }
            files.put(code, new DocumentFileRef(docFile));
        }
        //If invalid, fetch from db
        if(files.get(code).isInvalid()){
            files.put(code, new DocumentFileRef(provider.pullDocument(entityId, domain, key)));
        }
        DocumentFile d = files.get(code).getRef();
        d.setOwner(this);
        return d;
    }
}