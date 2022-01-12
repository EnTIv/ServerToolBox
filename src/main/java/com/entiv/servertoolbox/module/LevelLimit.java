package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Module;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;

public class LevelLimit extends Module implements Listener {

    @Override
    protected void onEnable() {
        registerListener();
    }

    @EventHandler
    private void onLevelChange(PlayerLevelChangeEvent event) {
        final int newLevel = event.getNewLevel();
        final Player player = event.getPlayer();

        getConfigSection("权限列表").getValues(false).forEach((k, v) -> {
            if (v instanceof Integer) {
                String permission = "LimitLevel" + k;
                int level = (int) v;

                if (player.hasPermission(permission) && newLevel >= level) {
                    player.setLevel(level);
                }
            }
        });
    }
}
