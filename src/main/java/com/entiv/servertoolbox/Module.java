package com.entiv.servertoolbox;

import com.entiv.servertoolbox.Main;
import com.entiv.servertoolbox.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public abstract class Module {

    protected JavaPlugin plugin;
    protected ConfigurationSection config;
    protected String name;
    protected PluginCommand command;

    public void load(JavaPlugin plugin, String name) {
        this.plugin = plugin;
        this.name = name;

        config = plugin.getConfig().getConfigurationSection(name);

        enable();
    }

    protected abstract void enable();

    protected void unload() {
        unregister();
    }

    public boolean isEnable() {
        return true;
    }

    protected void registerListener() {
        Listener listener = this instanceof Listener ? ((Listener) this) : null;
        if (listener != null) Bukkit.getPluginManager().registerEvents(listener, plugin);
    }

    protected void registerCommand(String label) {
        command = Objects.requireNonNull(plugin.getCommand(label), name + "命令加载失败");
        CommandExecutor executor = this instanceof CommandExecutor ? ((CommandExecutor) this) : null;

        if (executor == null) return;

        command.setExecutor(executor);
    }

    protected void unregister() {
        Listener listener = this instanceof Listener ? ((Listener) this) : null;

        if (command != null) command.setExecutor(null);
        if (listener != null) HandlerList.unregisterAll(listener);

        Message.sendConsole("&9" + Main.getInstance().getName() + "&6 >> &c模块 &e" + name + "&c 已卸载");
    }
}
