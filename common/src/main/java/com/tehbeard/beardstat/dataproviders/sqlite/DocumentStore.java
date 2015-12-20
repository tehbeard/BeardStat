/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tehbeard.beardstat.dataproviders.sqlite;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.annotations.Expose;
import com.tehbeard.beardstat.containers.meta.DomainPointer;

/**
 *
 * @author James
 */
public class DocumentStore {
    
    @Expose
    Map<String,DocEntry> documents = new HashMap<String, DocEntry>();
    
    public DocEntry getDocumentData(DomainPointer domainId,String key){
        String keyCode = domainId.getGameTag() + ":" + key;
        if(!documents.containsKey(keyCode)){
            documents.put(keyCode, new DocEntry());
        }
        return documents.get(keyCode);
    }
    
    public String[] getDocsUnderDomain(DomainPointer domain){
        List<String> s = new ArrayList<String>();
        for(String k : documents.keySet()){
            if(k.startsWith(domain.getGameTag()+":")){
                s.add(k.replaceAll(domain.getGameTag()+":", ""));
            }
        }
        return s.toArray(new String[0]);
    }
    
    public void deleteDocument(DomainPointer domain,String key){
        documents.remove(domain.getGameTag() + ":" + key);
    }
}
