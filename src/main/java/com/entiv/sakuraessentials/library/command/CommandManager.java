package com.entiv.sakuraessentials.library.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandMap;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;

import java.util.HashMap;
import java.util.Map;

public class CommandManager {

    private static CommandManager commandManager;

    private final Map<String, Command> commands = new HashMap<>();
    private final Plugin plugin;
    private final CommandMap commandMap;

    public CommandManager(Plugin plugin) {
        this.plugin = plugin;
        commandMap = plugin.getServer().getCommandMap();

        safelyRegistryListenerHandler();
    }

    public static CommandManager getInstance(Plugin plugin) {
        if (commandManager == null) {
            commandManager = new CommandManager(plugin);
        }

        return commandManager;
    }

    public void registerCommand(SimpleCommand command) {
        CommandMap commandMap = plugin.getServer().getCommandMap();
        commandMap.register(plugin.getName(), command);

        commands.put(command.getLabel(), command);
    }

    public void unregisterCommands() {
        Map<String, Command> knownCommands = commandMap.getKnownCommands();

        knownCommands.values().removeIf(commands.values()::contains);

        commands.values().forEach(c -> c.unregister(commandMap));

        commands.clear();
    }

    private static void safelyRegistryListenerHandler() {

        Plugin fakePlugin = new FakePlugin("SimpleCommandFakePlugin");

        RegisteredListener listener = new RegisteredListener(new Listener() {}, (owner, evt) -> {

            commandManager.unregisterCommands();
            PluginDisableEvent.getHandlerList().unregister(owner);

        }, EventPriority.MONITOR, fakePlugin, false);

        PluginDisableEvent.getHandlerList().register(listener);
    }
}
