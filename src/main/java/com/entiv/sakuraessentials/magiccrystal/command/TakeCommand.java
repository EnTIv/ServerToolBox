package com.entiv.sakuraessentials.magiccrystal.command;

import com.entiv.sakuraessentials.library.command.SimpleCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class TakeCommand extends SimpleCommand {

    protected TakeCommand(JavaPlugin plugin, SimpleCommand parent, String label) {
        super(plugin, parent, label);
        setUsage("/crystal take 玩家 数量");
        setDescription("扣除玩家指定数量的魔晶矿");
        setAdminCommand();
    }

    @Override
    public void onCommand() {

    }

}
