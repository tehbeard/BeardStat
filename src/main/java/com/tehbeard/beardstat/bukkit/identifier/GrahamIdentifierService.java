package com.tehbeard.beardstat.bukkit.identifier;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.potion.PotionEffect;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import org.apache.commons.lang.ClassUtils;

/**
 * Generates Ids based off the data from
 * http://minecraft-ids.grahamedgecombe.com
 *
 * @author James
 *
 */
public class GrahamIdentifierService implements IIdentifierGenerator {

    private static Map<String, GrahamItem> items;

    @Override
    public String keyForId(int id, int meta) {
        return items.get(id + ":" + meta).getKey();
    }

    @Override
    public String keyForId(int id) {
        return items.get(id + ":" + 0).getKeyNoMeta();
    }

    @Override
    public String keyForEntity(Entity entity) {
        return "minecraft:" + entity.getType().getName();
    }

    @Override
    public String keyForPotionEffect(PotionEffect effect) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getHumanName(String key) {
        for(GrahamItem item : items.values()){
            
        }
        return null;
    }

    public static void readData() {
        //Load item data
        Gson gson = new Gson();
        List<GrahamItem> itemList = gson.fromJson(new InputStreamReader(GrahamIdentifierService.class.getResourceAsStream("data/items.json")), new TypeToken<List<GrahamItem>>() {
        }.getType());

        for (GrahamItem i : itemList) {
            items.put(i.type + ":" + i.meta, i);
        }
        }

    public class GrahamItem {
        public int type;
        public int meta;
        public String name;
        public String text_type;

        public String getKey() {
            return "minecraft:" + text_type + ":" + meta;
        }

        public String getKeyNoMeta() {
            return "minecraft:" + text_type;
        }
    }
}
