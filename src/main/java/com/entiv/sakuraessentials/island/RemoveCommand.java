package com.entiv.sakuraessentials.island;

import com.entiv.sakuraessentials.library.command.SimpleCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class RemoveCommand extends SimpleCommand {

    protected RemoveCommand(JavaPlugin plugin, SimpleCommand parent, String label) {
        super(plugin, parent, label);
        setUsage("/is remove 玩家");
        setDescription("移除岛屿成员");
    }

    @Override
    public void onCommand() {

    }

    
}
