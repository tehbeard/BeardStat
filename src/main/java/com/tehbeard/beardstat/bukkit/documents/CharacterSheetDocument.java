/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tehbeard.beardstat.bukkit.documents;

import com.tehbeard.beardstat.containers.EntityStatBlob;
import com.tehbeard.beardstat.containers.documents.IStatDocument;
import com.tehbeard.beardstat.containers.documents.IStatDynamicDocument;
import com.tehbeard.beardstat.containers.documents.StatDocument;
import com.tehbeard.beardstat.containers.documents.docfile.DocumentFile;

/**
 *
 * @author James
 */
@StatDocument(value = "CharSheet",singleInstance = true)
public class CharacterSheetDocument implements IStatDynamicDocument {

    @Override
    public void updateDocument(EntityStatBlob blob) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    @Override
    public IStatDocument mergeDocument(DocumentFile file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
