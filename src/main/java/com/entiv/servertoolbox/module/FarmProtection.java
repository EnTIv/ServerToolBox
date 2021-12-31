package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Module;
import com.entiv.servertoolbox.utils.XMaterial;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class FarmProtection extends Module implements Listener {

    @Override
    protected void onEnable() {
        registerListener();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.PHYSICAL) {
            return;
        }

        final Block clickedBlock = event.getClickedBlock();

        if (clickedBlock != null && clickedBlock.getType() == XMaterial.FARMLAND.parseMaterial()) {
            event.setCancelled(true);
        }
    }
}
