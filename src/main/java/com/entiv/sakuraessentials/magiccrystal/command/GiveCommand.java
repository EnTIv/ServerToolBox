package com.entiv.sakuraessentials.magiccrystal.command;

import com.entiv.sakuraessentials.library.command.SimpleCommand;

import org.bukkit.plugin.java.JavaPlugin;


public class GiveCommand extends SimpleCommand {

    protected GiveCommand(JavaPlugin plugin, SimpleCommand parent, String label) {
        super(plugin, parent, label);
        setUsage("/crystal give 玩家 数量");
        setDescription("给予玩家指定数量的魔晶矿");
        setAdminCommand();
    }

    @Override
    public void onCommand() {

    }

}
