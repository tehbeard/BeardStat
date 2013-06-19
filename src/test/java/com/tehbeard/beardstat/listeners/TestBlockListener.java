package com.tehbeard.beardstat.listeners;

import java.util.ArrayList;
import java.util.List;

import net.dragonzone.promise.Deferred;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.tehbeard.BeardStat.BeardStat;
import com.tehbeard.BeardStat.containers.EntityStatBlob;
import com.tehbeard.BeardStat.containers.IStat;
import com.tehbeard.BeardStat.containers.PlayerStatManager;
import com.tehbeard.BeardStat.listeners.StatBlockListener;

import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
public class TestBlockListener {

    private PlayerStatManager manager;
    private List<String> blacklist = new ArrayList<String>();
    private StatBlockListener listener;
    
    private EntityStatBlob blob;
    
    @Before
    public void setup(){
        
        //Create test blob
        blob = new EntityStatBlob("bob", 0, "player");
        //world blacklist
        blacklist.add("blacklisted");
        
        //Mock manager to return our blob
        manager = mock(PlayerStatManager.class);
        when(manager.getPlayerBlobASync(anyString())).thenReturn(new Deferred<EntityStatBlob>(blob));
        
        listener = new StatBlockListener(blacklist, manager);
        
        }
    
    @Test
    public void testPlayerPlaceBlockTrackedWorld(){
        
        Player bob = mock(Player.class);
        when(bob.getName()).thenReturn("bob");
        World mockWorld = PowerMockito.mock(World.class);
        when(mockWorld.getName()).thenReturn("blacklisted");
        
        when(bob.getWorld()).thenReturn(mockWorld);
        
        Block block = mock(Block.class);
        when(block.getType()).thenReturn(Material.STONE);
        when(block.getData()).thenReturn((byte) 0);
        
        
        BlockPlaceEvent event = new BlockPlaceEvent(block, null, null, null, bob, true);
        
       
        listener.onBlockPlace(event);
        
        
        IStat stat = blob.getStat(BeardStat.DEFAULT_DOMAIN, bob.getWorld().getName(), "stats", "totalblockcreate");
        assertTrue("total blocks placed archived",stat.isArchive());
        assertEquals("total blocks is one", 1, stat.getValue());
        
    }
}
