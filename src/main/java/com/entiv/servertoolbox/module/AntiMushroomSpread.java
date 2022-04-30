package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Main;
import com.entiv.servertoolbox.Module;
import com.entiv.servertoolbox.utils.XMaterial;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;

public class AntiMushroomSpread extends Module implements Listener {

    @Override
    protected void onEnable() {
        registerListener();
    }

    @EventHandler
    private void onMushroomSpread(BlockSpreadEvent event) {
        final Block block = event.getBlock();

        final boolean isEndWorld = block.getWorld().getEnvironment().equals(World.Environment.THE_END);

        if (!isEndWorld) return;

        Main.debug("检测到末地方块传播，方块为 {0}", block.getType());

        final Material blockType = block.getType();
        final Material redMushroom = XMaterial.RED_MUSHROOM.parseMaterial();
        final Material brownMushroom = XMaterial.BROWN_MUSHROOM.parseMaterial();

        if (blockType.equals(redMushroom) || blockType.equals(brownMushroom)) {
            event.setCancelled(true);
            Main.debug("蘑菇传播已阻止");
        }
    }
}
