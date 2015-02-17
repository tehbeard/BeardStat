/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tehbeard.beardstat.bukkit.documents;

import com.google.common.collect.HashBiMap;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tehbeard.beardstat.containers.EntityStatBlob;
import com.tehbeard.beardstat.containers.documents.IStatDocument;
import com.tehbeard.beardstat.containers.documents.IStatDynamicDocument;
import com.tehbeard.beardstat.containers.documents.StatDocument;
import com.tehbeard.beardstat.containers.documents.docfile.DocumentFile;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author James
 */
@StatDocument(value = "CharSheet",singleInstance = true)
public class CharacterSheetDocument implements IStatDynamicDocument {
    
    @Expose
    Map<String,String> armor = new HashMap<String, String>();
    
    Player player = null;

    @Override
    public void updateDocument(EntityStatBlob blob) {
        if(player != null) {
            ItemStack[] am = player.getInventory().getArmorContents();
            armor.put("head", am[0].getType().toString());
            armor.put("chest", am[1].getType().toString());
            armor.put("legs", am[2].getType().toString());
            armor.put("feet", am[3].getType().toString());
        }
    }

    
    @Override
    public IStatDocument mergeDocument(DocumentFile file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
