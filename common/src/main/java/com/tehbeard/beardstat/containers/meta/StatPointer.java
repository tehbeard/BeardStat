package com.tehbeard.beardstat.containers.meta;

import com.tehbeard.beardstat.LanguagePack;
import com.tehbeard.beardstat.commands.formatters.StatFormatter;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author James
 */
public class StatPointer extends AbstractPointer {


    private final String name;

    private final Map<String, String> classifiers;

    private final Formatting format = Formatting.none;

    private final String outputStr = "%s";
    
    private static Set<StatPointer> pointers = new HashSet<StatPointer>();

    private static final Map<Formatting, StatFormatter> formatters = new EnumMap<Formatting, StatFormatter>(Formatting.class);

    static {
        formatters.put(Formatting.time, new StatFormatter() {

            @Override
            public String format(int value) {
                long seconds = value;
                int days = (int) Math.ceil(seconds / 86400);
                int hours = (int) Math.ceil((seconds - (86400 * days)) / 3600);
                int minutes = (int) Math.ceil((seconds - ((86400 * days) + (3600 * hours))) / 60);

                return LanguagePack.getMsg("format.time", days, hours, minutes);
            }
        });
        formatters.put(Formatting.timestamp, new StatFormatter() {

            @Override
            public String format(int value) {
                return (new Date(value * 1000L)).toString();
            }

        });
        formatters.put(Formatting.none, new StatFormatter() {

            @Override
            public String format(int value) {

                return "" + value;
            }
        });
    }

    public enum Formatting {
        none, time, timestamp
    }

    private StatPointer(String name) {
        this(name, new HashMap<String, String>());
    }

    private StatPointer(String name, Map<String, String> data) {
        this.name = name;
        Map<String, String> m = new TreeMap<String, String>();
        m.putAll(data);
        this.classifiers = Collections.unmodifiableMap(m);
    }

    @Override
    public String toString() {
        return "StatPointer{" + "name=" + name + ", classifiers=" + classifiers + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 97 * hash + (this.classifiers != null ? this.classifiers.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StatPointer other = (StatPointer) obj;

        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.classifiers != other.classifiers && (this.classifiers == null || !this.classifiers.equals(other.classifiers))) {
            return false;
        }
        return true;
    }

    public String toTag() {
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(";");

        Iterator<Map.Entry<String, String>> it = this.classifiers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
            if (it.hasNext()) {
                builder.append(",");
            }
        }

        return builder.toString();
    }

    public String formatStat(int value) {
        return String.format(this.outputStr, formatters.get(this.format).format(value));
        // Wrap output of formatter with outputStr, to allow for things like x
        // metres
    }

    public boolean contains(StatPointer pointer) {
        if (!this.name.equals(pointer.name)) {
            return false;
        }
        for (Entry<String, String> e : pointer.classifiers.entrySet()) {
            if ( 
                    !this.classifiers.containsKey(e.getKey())
                    || !(e.getValue() == null
                    || e.getValue().equals(this.classifiers.get(e.getKey())))
                ) {
                return false;
            }
        }
        return true;
    }

    public static Set<StatPointer> filter(String name, Map<String, String> constraints) {
        StatPointer qry = new StatPointer(name, constraints);
        Set<StatPointer> results = new HashSet<StatPointer>();
        for (StatPointer p : pointers) {
            if (p.contains(qry)) {
                results.add(p);
            }
        }
        return results;
    }
    public static StatPointer get(String name) {
        StatPointer p = new StatPointer(name);
        if(!pointers.contains(p)){
            pointers.add(p);
        }
        for(StatPointer pp : pointers){
            if(pp.equals(p)){
                return pp;
            }
        }
        throw new IllegalStateException("Fell out of loop in get()");
    }
    public static StatPointer get(String name, Map<String,String> constraints) {
        StatPointer p = new StatPointer(name, constraints);
        if(!pointers.contains(p)){
            pointers.add(p);
        }
        for(StatPointer pp : pointers){
            if(pp.equals(p)){
                return pp;
            }
        }
        throw new IllegalStateException("Fell out of loop in get()");
    }

    public static void main(String[] args) {
        StatPointer s = StatPointer.get("minecraft:wool_chest", new HashMap<String, String>() {
            {
                put("direction", "south");
                put("color", "red");
                put("open", "yes");
            }
        });
        
        StatPointer.get("minecraft:wool_chest", new HashMap<String, String>() {
            {
                put("direction", "south");
                put("color", "red");
                put("open", "yes");
            }
        });
        
               

        System.out.println(s.toTag());
        System.out.println(pointers);
        System.out.println(pointers.size());
    }
}
