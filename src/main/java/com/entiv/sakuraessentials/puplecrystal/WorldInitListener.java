package com.entiv.sakuraessentials.puplecrystal;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldInitEvent;

public class WorldInitListener implements Listener {
    @EventHandler
    public void onWorldInit(WorldInitEvent event) {

        World world = event.getWorld();

        if (world.getName().equals("world_resource")) {
            world.getPopulators().add(new PurpleCrystalPopulator());
        }
    }
}
