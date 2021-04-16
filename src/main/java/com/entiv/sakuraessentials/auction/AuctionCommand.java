package com.entiv.sakuraessentials.auction;

import com.entiv.sakuraessentials.library.command.SimpleCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class AuctionCommand extends SimpleCommand {

    protected AuctionCommand(JavaPlugin plugin, String label, String... aliases) {
        super(plugin, label, aliases);
    }

    @Override
    public void onCommand() {

    }

    private void registerSubCommands() {

    }
}
