package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Module;
import com.entiv.servertoolbox.utils.Message;
import org.apache.commons.lang.Validate;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.List;

public class WorldCommandBlacklist extends Module implements Listener {

    @Override
    protected void onEnable() {
        registerListener();
    }

    @EventHandler
    private void onPlayerCommand(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final World world = player.getWorld();

        if (player.isOp()) return;

        final ConfigurationSection section = config.getConfigurationSection("世界列表");

        Validate.notNull(section, "世界命令黑名单模块配置错误，请检查配置文件");

        for (String name : section.getKeys(false)) {

            if (!world.getName().equalsIgnoreCase(name)) {
                continue;
            }

            final List<String> blacklistCommand = section.getStringList(name);

            if (blacklistCommand.contains(event.getMessage())) {
                event.setCancelled(true);
                Message.send(player, config.getString("提示消息"));
            }

        }
    }
}
