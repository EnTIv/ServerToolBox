package com.entiv.sakuraessentials;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class Main extends JavaPlugin {

    private static Main plugin;

    public static Main getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        plugin = this;
        saveDefaultConfig();
        registerListener();

        initModule();
    }

    private void initModule() {
        new DropProtect().enable(plugin);
    }

    private void registerListener() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (sender.isOp()) {
            Bukkit.getPluginManager().disablePlugin(this);
            Bukkit.getPluginManager().enablePlugin(plugin);

            sender.sendMessage("插件重载成功");
        }

        return true;
    }
}
