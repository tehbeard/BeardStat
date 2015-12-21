package com.tehbeard.beardstat.bukkit.identifier;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tehbeard.beardstat.containers.meta.StatPointer;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

/**
 * Generates Ids based off the data from
 * http://minecraft-ids.grahamedgecombe.com
 *
 * @author James
 *
 */
public class GrahamIdentifierService implements IIdentifierGenerator {

    private static Map<String, GrahamItem> items;

    public static void readData() {
        //Load item data
        Gson gson = new Gson();
        List<GrahamItem> itemList = gson.fromJson(new InputStreamReader(GrahamIdentifierService.class.getResourceAsStream("data/items.json")), new TypeToken<List<GrahamItem>>() {
        }.getType());

        for (GrahamItem i : itemList) {
            items.put(i.type + ":" + i.meta, i);
        }
        }

    @Override
    public StatPointer keyForItemstack(ItemStack stack) {
        GrahamItem item = items.get(stack.getTypeId() + ":" + stack.getData().getData());
        return StatPointer.get(item.getKey());//TODO - Add classifier handling
    }

    @Override
    public StatPointer keyForBlock(Block block) {
        GrahamItem item = items.get(block.getTypeId() + ":" + block.getState().getData().getData());
        return StatPointer.get(item.getKey());//TODO - Add classifier handling
    }

    @Override
    public StatPointer keyForEntity(Entity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public StatPointer keyForPotionEffect(PotionEffect effect) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public class GrahamItem {
        public int type;
        public int meta;
        public String name;
        public String text_type;

        public String getKey() {
            return "minecraft:" + text_type;
        }
    }
}
