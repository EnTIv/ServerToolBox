package com.entiv.sakuraessentials.island;

import com.entiv.sakuraessentials.library.command.SimpleCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class HomeCommand extends SimpleCommand {
    protected HomeCommand(JavaPlugin plugin, SimpleCommand parent, String label) {
        super(plugin, parent, label);
        setUsage("/is home");
        setDescription("回到自己的岛屿");
    }

    @Override
    public void onCommand() {

    }
}
