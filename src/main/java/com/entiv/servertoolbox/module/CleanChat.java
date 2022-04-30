package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Module;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class CleanChat extends Module implements Listener, CommandExecutor {

    private static final String CLEAN_MESSAGE = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";

    @Override
    protected void onEnable() {
        registerListener();

        registerCommand("CleanChat");
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(plugin, () ->{
            for (int i = 0; i < 50; i++) {
                player.sendMessage(CLEAN_MESSAGE);
            }
        }, config.getInt("延时", 20));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        for (int i = 0; i < 50; i++) {
            sender.sendMessage(CLEAN_MESSAGE);
        }

        return false;
    }
}
