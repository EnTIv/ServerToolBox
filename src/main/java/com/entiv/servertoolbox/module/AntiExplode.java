package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Module;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

public class AntiExplode extends Module implements Listener {

    @Override
    protected void onEnable() {
        registerListener();
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        if (event.isCancelled()) {
            return;
        }
        event.blockList().clear();

    }
}
