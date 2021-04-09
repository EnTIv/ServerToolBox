package com.entiv.sakuraessentials.island;

import com.entiv.sakuraessentials.library.command.SimpleCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class SetHomeCommand extends SimpleCommand {

    protected SetHomeCommand(JavaPlugin plugin, SimpleCommand parent, String label) {
        super(plugin, parent, label);
        setUsage("/is sethome");
        setDescription("设置岛屿传送点");
    }

    @Override
    public void onCommand() {

    }

}
