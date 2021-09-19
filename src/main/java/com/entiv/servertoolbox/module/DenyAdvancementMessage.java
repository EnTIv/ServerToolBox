package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Main;
import com.entiv.servertoolbox.Module;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

public class DenyAdvancementMessage extends Module implements Listener {

    private final FileConfiguration config = Main.getInstance().getConfig();
    private final boolean enable = config.getBoolean("隐藏成就消息.启用");

    @Override
    public void enable() {
        for (World world : Bukkit.getWorlds()) {
            world.setGameRuleValue("announceAdvancements", enable ? "false" : "true");
        }

        registerListener();
    }

    @EventHandler
    private void onWorldLoad(WorldLoadEvent event) {
        World world = event.getWorld();
        world.setGameRuleValue("announceAdvancements", enable ? "false" : "true");
    }

    @Override
    protected void unload() {
        HandlerList.unregisterAll(this);
    }
}
