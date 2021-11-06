package com.entiv.servertoolbox;


import com.entiv.servertoolbox.utils.Message;
import com.sun.javafx.binding.StringFormatter;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.MessageFormat;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    private static Main plugin;
    private final ModuleManager moduleManager = new ModuleManager(this);

    private boolean debug = true;

    @Override
    public void onEnable() {

        plugin = this;

        String[] message = {
                "§e" + getName() + "§a 插件§e v" + getDescription().getVersion() + " §a已启用",
                "§a插件制作作者:§e EnTIv §aQQ群:§e 600731934"
        };
        Bukkit.getConsoleSender().sendMessage(message);

        saveDefaultConfig();
        moduleManager.loadModules();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!label.equalsIgnoreCase("ServerToolBox")) return true;

        if (sender.isOp()) {

            reloadConfig();

            moduleManager.unloadAllModule();
            moduleManager.loadModules();

            Message.sendConsole("&9" + Main.getInstance().getName() + "&6 >> &a插件重载成功");

        }
        return true;
    }

    public static Main getInstance() {
        return plugin;
    }

    public static void debug(String message,Object... objects) {
        if (getInstance().isDebug()) {
            Bukkit.getLogger().log(Level.INFO, MessageFormat.format(message, objects));
        }
    }

    public boolean isDebug() {
        return debug;
    }

    public boolean setDebug(boolean b) {
        return debug = b;
    }
}
