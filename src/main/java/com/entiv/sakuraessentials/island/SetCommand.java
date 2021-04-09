package com.entiv.sakuraessentials.island;

import com.entiv.sakuraessentials.library.command.SimpleCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class SetCommand extends SimpleCommand {

    protected SetCommand(JavaPlugin plugin, SimpleCommand parent, String label) {
        super(plugin, parent, label);
        setUsage("/is set");
        setDescription("打开岛屿设置菜单");
    }

    @Override
    public void onCommand() {

    }

    
}
