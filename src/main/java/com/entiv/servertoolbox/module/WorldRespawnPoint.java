package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Main;
import com.entiv.servertoolbox.Module;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerRespawnEvent;


public class WorldRespawnPoint extends Module implements Listener {

    @Override
    protected void onEnable() {
        registerListener();
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerRespawn(PlayerRespawnEvent event) {

        World world = event.getPlayer().getWorld();

        ConfigurationSection section = config.getConfigurationSection("世界列表");
        Validate.notNull(section, "配置文件路径设置世界重生点.世界列表不存在, 请检查配置文件");

        for (String key : section.getKeys(false)) {
            if (world.getName().equals(key)) {

                String locationConfig = section.getString(key);
                if (locationConfig == null) continue;

                String[] split = locationConfig.replaceAll("\\s*", "").split(",");
                World respawnWorld;

                try {
                    respawnWorld = Bukkit.getWorld(split[5]);
                } catch (Exception ignored) {
                    respawnWorld = world;
                }

                Location location = new Location(respawnWorld, Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Float.parseFloat(split[3]), Float.parseFloat(split[4]));

                event.setRespawnLocation(location);
            }
        }
    }
}
