package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Module;
import org.apache.commons.lang.Validate;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;


public class PortalCommand extends Module implements Listener {

    @Override
    protected void onEnable() {
        registerListener();
    }

    @EventHandler
    public void onPlayerPortalEvent(PlayerPortalEvent event) {

        Player player = event.getPlayer();
        World world = player.getWorld();

        ConfigurationSection section = config.getConfigurationSection("世界列表");
        Validate.notNull(section, "配置文件路径 地狱门随机传送.世界列表 不存在, 请检查配置文件");

        for (String key : section.getKeys(false)) {
            if (world.getName().equals(key)) {

                event.setCancelled(true);
                String command = section.getString(key);

                if (command == null) continue;

                player.performCommand(command);
            }
        }
    }
}
