package com.entiv.sakuraessentials.magiccrystal.command;

import com.entiv.sakuraessentials.library.command.SimpleCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class MagicCrystalCommand extends SimpleCommand {

    public MagicCrystalCommand(JavaPlugin plugin, String label, String... aliases) {
        super(plugin, label, aliases);
        registerSubCommands();
    }

    @Override
    public void onCommand() {
        sendSubCommandHelp("魔晶矿指令");
    }

    private void registerSubCommands() {
        new FindCommand(plugin, this, "find");
        new StatisticCommand(plugin, this, "statistic");
        new GiveCommand(plugin, this, "give");
        new TakeCommand(plugin, this, "take");
        new SetCommand(plugin, this, "set");
        new BalCommand(plugin, this, "bal");
        new BaltopCommand(plugin, this, "baltop");
    }

}
