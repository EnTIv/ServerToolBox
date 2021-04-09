package com.entiv.sakuraessentials.island;

import com.entiv.sakuraessentials.library.command.SimpleCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class AddCommand extends SimpleCommand {

    protected AddCommand(JavaPlugin plugin, SimpleCommand parent, String label) {
        super(plugin, parent, label);
        setUsage("/is add 玩家");
        setDescription("添加岛屿成员");
    }

    @Override
    public void onCommand() {

    }

    
}
