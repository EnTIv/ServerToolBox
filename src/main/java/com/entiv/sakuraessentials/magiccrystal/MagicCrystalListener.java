package com.entiv.sakuraessentials.magiccrystal;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.WorldInitEvent;

public class MagicCrystalListener implements Listener {

    @EventHandler
    public void onWorldInit(WorldInitEvent event) {

        World world = event.getWorld();

        if (world.getName().equals("world_resource")) {
            world.getPopulators().add(new MagicCrystalPopulator());
        }

    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (MagicCrystal.isMagicCrystal(block)) {
            event.setDropItems(false);
            Location location = block.getLocation();
            World world = location.getWorld();
            world.dropItem(location, MagicCrystal.getMagicCrystalItem(1));
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (block.getType().equals(Material.MUSHROOM_STEM)) {
            event.setCancelled(true);
        }
    }
}
