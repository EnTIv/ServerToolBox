package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Main;
import com.entiv.servertoolbox.Module;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandSendEvent;

import java.util.Collection;

public class PlayerCommandSend extends Module implements Listener {

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    protected void unload() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onPlayerCommandSendEvent(PlayerCommandSendEvent event) {

        FileConfiguration config = Main.getInstance().getConfig();

        if (!event.getPlayer().isOp()) {
            Collection<String> commands = event.getCommands();
            commands.clear();
            commands.addAll(config.getStringList("设置命令补全.补全列表"));
        }
    }
}
