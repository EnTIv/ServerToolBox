package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Module;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.FIRE_TICK;
import static org.bukkit.event.entity.EntityDamageEvent.DamageCause.LIGHTNING;

public class DenyLightningDestroyItem extends Module implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageEvent event) {

        Entity entity = event.getEntity();
        EntityDamageEvent.DamageCause cause = event.getCause();

        if (!entity.getType().equals(EntityType.DROPPED_ITEM)) return;

        if (cause.equals(LIGHTNING) || cause.equals(FIRE_TICK)) {
            event.setCancelled(true);
        }
    }

    @Override
    protected void onEnable() {
        registerListener();
    }
}
