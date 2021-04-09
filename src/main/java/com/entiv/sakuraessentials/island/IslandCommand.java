package com.entiv.sakuraessentials.island;

import com.entiv.sakuraessentials.library.command.SimpleCommand;
import org.bukkit.plugin.java.JavaPlugin;


public class IslandCommand extends SimpleCommand {

    public IslandCommand(JavaPlugin plugin, String label, String... aliases) {
        super(plugin, label, aliases);
        registerSubCommands();
    }

    private void registerSubCommands() {
        new InfoCommand(plugin, this, "info");
        new SetCommand(plugin, this, "set");
        new AddCommand(plugin, this, "add");
        new RemoveCommand(plugin, this, "remove");
        new HomeCommand(plugin, this, "home");
        new SetHomeCommand(plugin, this, "sethome");
    }

    @Override
    public void onCommand() {
        sendSubCommandHelp("岛屿指令帮助");
    }

}
