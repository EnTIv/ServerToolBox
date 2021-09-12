package com.entiv.sakuraessentials.module;

import org.bukkit.plugin.java.JavaPlugin;
public abstract class Module {

    protected JavaPlugin plugin;

    public void enable(JavaPlugin plugin) {
        this.plugin = plugin;

        if (isEnable()) {
            load(plugin);
        }
    }

    public abstract void load(JavaPlugin plugin);

    public abstract void unload();

    public abstract boolean isEnable();


}
