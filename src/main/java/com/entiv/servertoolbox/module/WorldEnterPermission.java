package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Module;
import com.entiv.servertoolbox.utils.CommandUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import java.util.Objects;

public class WorldEnterPermission extends Module implements Listener {

    @Override
    protected void enable() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    protected void unload() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    private void onPlayerChangeWorld(PlayerChangedWorldEvent event) {

        Player player = event.getPlayer();
        ConfigurationSection section = Objects.requireNonNull(plugin.getConfig().getConfigurationSection("世界进入权限.世界列表"), "世界进入权限模块配置文件出错, 请检查配置文件");

        for (String name : section.getKeys(false)) {
            String permission = "enterworld." + name;

            if (!player.hasPermission(permission)) {
                CommandUtil.execute(player, section.getStringList(name));
            }
        }
    }
}
