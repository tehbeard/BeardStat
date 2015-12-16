package com.tehbeard.beardstat.containers;

import java.util.Collections;
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
public class StatPointer {
    
    public final int id;
    
    public final String name;
    
    public final Map<String,String> classifiers;
    
    public static Set<StatPointer> pointers = new HashSet<StatPointer>();
    
    public StatPointer(int id,String name){
        this(id,name, new HashMap<String, String>());
    }
    
    public StatPointer(int id, String name, Map<String, String> data){
        this.id = id;
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
        hash = 97 * hash + this.id;
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
        if (this.id != other.id) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.classifiers != other.classifiers && (this.classifiers == null || !this.classifiers.equals(other.classifiers))) {
            return false;
        }
        return true;
    }
    
    public String toTag(){
        StringBuilder builder = new StringBuilder();
        builder.append(name).append(";");
        
        Iterator<Map.Entry<String, String>> it = this.classifiers.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<String, String> entry = it.next();
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
            if(it.hasNext()){
                builder.append(",");
            }
        }
        
        return builder.toString();
    }
    
    public boolean contains(StatPointer pointer){
        if(!this.name.equals(pointer.name)){
            return false;
        }
        for(Entry<String,String> e : pointer.classifiers.entrySet()){
            if(
                //Fail if key doesn't exist
                !this.classifiers.containsKey(e.getKey()) ||
                // Fail if key doesn't match:
                // * Fails straight equality check
                // * Search value is null (taken as wildcard)
                // * equals(..) check fails
                !(
                    e.getValue() == null || 
                    e.getValue().equals(this.classifiers.get(e.getKey()))
                )                 
            ){
                return false;
            }
        }
        return true;
    }

    public static Set<StatPointer> filter(String name, Map<String, String> constraints){
        StatPointer qry = new StatPointer(0, name, constraints);
        Set<StatPointer> results = new HashSet<StatPointer>();
        for(StatPointer p : pointers){
            if(p.contains(qry)){
                results.add(p);
            }
        }
        return results;
    }
    
    
    public static void main(String[] args){
        
        StatPointer s = new StatPointer(1,"minecraft:wool_chest", new HashMap<String, String>(){{
            put("direction", "south");
            put("color", "red");
            put("open", "yes");
        }});
        
        System.out.println(s.toTag());
    }
}
