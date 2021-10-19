package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Module;
import com.entiv.servertoolbox.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DropProtect extends Module implements Listener, CommandExecutor {

    private List<UUID> allowPlayers;

    @Override
    public void onEnable() {
        allowPlayers = new ArrayList<>();

        registerListener();
        registerCommand("drop");
    }

    @Override
    protected void unload() {
        HandlerList.unregisterAll(this);
        command.setExecutor(null);
        allowPlayers = null;
    }

    @EventHandler
    private void onPlayerDropItem(PlayerDropItemEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        if (!allowPlayers.contains(uuid)) {
            event.setCancelled(true);
            Message.send(event.getPlayer(), config.getString("禁止丢弃提示"));
        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = sender instanceof Player ? ((Player) sender) : null;
        if (player == null) return true;

        allowPlayers.add(player.getUniqueId());
        int allowTick = plugin.getConfig().getInt("物品丢弃保护.允许丢弃时长");
        Bukkit.getScheduler().runTaskLater(plugin, () -> allowPlayers.remove(player.getUniqueId()), allowTick);

        Message.send(sender, config.getString("允许丢弃提示"));

        return true;
    }
}
