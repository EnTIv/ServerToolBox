package com.entiv.sakuraessentials;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.entiv.sakuraessentials.details.PlayerListener;
import com.entiv.sakuraessentials.island.IslandCommand;
import com.entiv.sakuraessentials.magiccrystal.MagicCrystal;
import com.entiv.sakuraessentials.magiccrystal.MagicCrystalListener;
import com.entiv.sakuraessentials.magiccrystal.command.MagicCrystalCommand;
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
        new MagicCrystalCommand(plugin, "crystal");
    }

    private void registerListener() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Bukkit.getPluginManager().registerEvents(new MagicCrystalListener(), this);
        System.out.println("啊这");
    }
}
