package com.entiv.servertoolbox;


import com.entiv.servertoolbox.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class Main extends JavaPlugin {

    private static Main plugin;
    private final ModuleManager moduleManager = new ModuleManager(this);
    private boolean debug = false;

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

        if (!label.equalsIgnoreCase("ServerToolBox")) return false;

        if (!sender.isOp()) return false;

        if (args.length == 0) {
            Message.send(sender,
                    "",
                    "&6━━━━━━━━━━━━━━&d  服务器工具箱指令帮助  &6━━━━━━━━━━━━━━",
                    "",
                    "&b ━ &d/ServerToolBox reload &7重载配置文件",
                    "&b ━ &d/ServerToolBox debug &7切换 debug 模式"
            );
            return true;
        }

        if (args.length == 1) {

            if (args[0].equalsIgnoreCase("reload")) {
                reloadConfig();

                moduleManager.unloadAllModule();
                moduleManager.loadModules();

                Message.send(sender, "&9" + Main.getInstance().getName() + "&6 >> &a插件重载成功");

            } else if (args[0].equalsIgnoreCase("debug")) {
                debug = !debug;
                final String enable = debug ? "启用" : "关闭";

                Message.send(sender, "&9" + Main.getInstance().getName() + "&6 >>&e debug &a模式已" + enable);
            }
        }

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1) {
            return Arrays.asList("reload", "debug");
        }
        return super.onTabComplete(sender, command, alias, args);
    }

    public static Main getInstance() {
        return plugin;
    }

    public static void debug(String message, Object... objects) {
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
