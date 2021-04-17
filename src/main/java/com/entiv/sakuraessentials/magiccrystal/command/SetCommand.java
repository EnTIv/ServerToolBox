package com.entiv.sakuraessentials.magiccrystal.command;

import com.entiv.sakuraessentials.library.command.SimpleCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class SetCommand extends SimpleCommand {

    protected SetCommand(JavaPlugin plugin, SimpleCommand parent, String label) {
        super(plugin, parent, label);
        setUsage("/crystal set 玩家 数量");
        setDescription("设置玩家的魔晶矿数量");
        setAdminCommand();
    }

    @Override
    public void onCommand() {

    }

}
