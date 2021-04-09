package com.entiv.sakuraessentials;

import com.entiv.sakuraessentials.island.IslandCommand;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main plugin;

    public static Main getInstance() {
        return plugin;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        plugin = this;

        registerCommands();
        registerListener();
    }

    private void registerCommands() {
        new IslandCommand(plugin, "island", "is");
    }

    private void registerListener() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
    }
}
