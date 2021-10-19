package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Module;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.Objects;

public class EntityTargetController extends Module implements Listener {

    @Override
    protected void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    protected void unload() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    private void onEntityTarget(EntityTargetEvent event) {
        Entity entity = event.getEntity();
        Entity target = event.getTarget();

        if (target == null) return;

        ConfigurationSection section = Objects.requireNonNull(config.getConfigurationSection("控制列表"), name + "配置文件错误, 请检查配置文件");

        for (String entityName : section.getKeys(false)) {

            String targetName = section.getString(entityName);

            EntityType entityType = EntityType.valueOf(entityName);
            EntityType targetType = EntityType.valueOf(targetName);

            if (entity.getType().equals(entityType) && target.getType().equals(targetType)) {
                event.setCancelled(true);
            }
        }
    }
}
