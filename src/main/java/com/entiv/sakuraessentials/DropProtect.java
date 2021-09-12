package com.entiv.sakuraessentials;

import com.entiv.sakuraessentials.module.Module;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class DropProtect extends Module implements Listener, CommandExecutor {

    private List<UUID> allowPlayers;
    private PluginCommand command;

    @Override
    public void load(JavaPlugin plugin) {
        allowPlayers = new ArrayList<>();
        command = Objects.requireNonNull(plugin.getCommand("drop"), "丢弃保护命令加载失败");

        Bukkit.getPluginManager().registerEvents(this, plugin);
        command.setExecutor(this);
    }

    @Override
    public void unload() {
        HandlerList.unregisterAll(this);
        command.unregister(Bukkit.getCommandMap());
    }

    @Override
    public boolean isEnable() {
        return plugin.getConfig().getBoolean("物品丢弃保护.启用");
    }

    @EventHandler
    private void onPlayerDropItem(PlayerDropItemEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();

        if (!allowPlayers.contains(uuid)) {
            event.setCancelled(true);
        }

    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = sender instanceof Player ? ((Player) sender) : null;
        if (player == null) return true;

        allowPlayers.add(player.getUniqueId());
        int allowTick = plugin.getConfig().getInt("物品丢弃保护.允许丢弃时长");

        Bukkit.getScheduler().runTaskLater(plugin, () -> allowPlayers.remove(player.getUniqueId()), allowTick);
        return true;
    }

}
