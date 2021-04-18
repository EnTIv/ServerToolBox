package com.entiv.sakuraessentials.island;

import com.entiv.sakuraessentials.library.command.CommandException;
import com.entiv.sakuraessentials.library.command.SimpleCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.managers.IslandsManager;
import world.bentobox.bentobox.managers.PlayersManager;


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

        BentoBox bentoBox = BentoBox.getInstance();
        IslandsManager islandsManager = bentoBox.getIslandsManager();

        Player player = getPlayer();
        World world = Bukkit.getWorld("world_island");

        if (world == null) throw new CommandException("世界不存在", CommandException.MessageType.ERROR);
        boolean hasIsland = islandsManager.hasIsland(world, player.getUniqueId());

        if (sender instanceof Player && !hasIsland) {
            player.performCommand("bskyblock");
        } else {
            sendSubCommandHelp("岛屿指令帮助");
        }
    }


}
