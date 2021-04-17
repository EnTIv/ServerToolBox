package com.entiv.sakuraessentials.library.command;

import com.entiv.sakuraessentials.library.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class SimpleCommand extends Command {

    public final JavaPlugin plugin;

    protected final SimpleCommand parent;

    private final Map<String, SimpleCommand> subCommands = new LinkedHashMap<>();
    private final List<String> tabComplete = new ArrayList<>();

    protected CommandSender sender;
    protected String[] args;

    private boolean isAdminCommand = false;
    private boolean isPlayerOnly = false;

    /**
     * 创建一个主命令
     *
     * @param plugin  - 插件的主类
     * @param label   - 命令名
     * @param aliases - 命令别名
     */
    protected SimpleCommand(JavaPlugin plugin, String label, String... aliases) {
        super(label, "", "", Arrays.asList(aliases));

        this.plugin = plugin;
        this.parent = null;

        CommandManager commandManager = CommandManager.getInstance(plugin);
        commandManager.registerCommand(this);
    }

    /**
     * 创建一个子命令
     *
     * @param plugin - 插件的主类
     * @param parent - 父命令
     * @param label  - 命令名
     */
    protected SimpleCommand(JavaPlugin plugin, SimpleCommand parent, String label) {
        super(label, "", "", new ArrayList<>());

        this.plugin = plugin;
        this.parent = parent;

        parent.subCommands.put(label, this);
    }

    public void setAdminCommand() {
        isAdminCommand = true;
    }

    public void setPlayerOnly() {
        isPlayerOnly = true;
    }

    @Override
    public boolean execute(@NotNull CommandSender sender, @NotNull String label, @NotNull String[] args) {

        this.sender = sender;
        this.args = args;

        SimpleCommand simpleCommand = getCommandFromArgs(args);

        simpleCommand.sender = sender;
        simpleCommand.args = args;

        if (simpleCommand.isPlayerOnly && simpleCommand.sender instanceof ConsoleCommandSender) {
            Message.sendConsole("&c该命令只能由玩家执行");
            return true;
        }

        try {
            simpleCommand.onCommand();
        } catch (CommandException e) {
            e.sendMessage(sender);
        }

        return true;
    }

    protected void sendSubCommandHelp(String title) {

        if (!hasSubCommands()) return;

        TextComponent.Builder textComponent = Component.text().content(Component.newline().content())
                .color(TextColor.color(0xf2cc6d))
                .append(Component.text("━━━━━━━━━━━━━━  "))
                .append(Component.text(title, TextColor.color(0xbde0fe)))
                .append(Component.text("  ━━━━━━━━━━━━━━")).append(Component.newline())
                .append(Component.newline());

        for (SimpleCommand v : getSubCommands().values()) {

            if (v.isAdminCommand && !sender.isOp()) {
                continue;
            }

            textComponent
                    .append(Component.text("§b ━"))
                    .append(Component.text(" " + v.getUsage(), TextColor.color(0xffd166)))
                    .append(Component.text("§7 " + v.getDescription()))
                    .append(Component.newline());

        }

        sender.sendMessage(textComponent);
    }

    public Optional<SimpleCommand> getSubCommand(String label) {
        if (subCommands.containsKey(label)) {
            return Optional.ofNullable(subCommands.get(label));
        }
        return Optional.empty();
    }

    /**
     * @return Map of sub commands for this command
     */
    public Map<String, SimpleCommand> getSubCommands() {
        return subCommands;
    }

    protected boolean hasSubCommands() {
        return !subCommands.isEmpty();
    }

    protected void setTabComplete(String[] options) {
        tabComplete.addAll(Arrays.asList(options));
    }

    public Player getPlayer() {
        return (Player) sender;
    }

    private SimpleCommand getCommandFromArgs(String[] args) {

        SimpleCommand simpleCommand = this;

        // Run through any arguments
        for (String arg : args) {
            // 没有子命令就结束循环
            if (!simpleCommand.hasSubCommands()) {
                return simpleCommand;
            }

            Optional<SimpleCommand> subCommand = simpleCommand.getSubCommand(arg);
            if (!subCommand.isPresent()) {
                return simpleCommand;
            }

            boolean isAdminCommand = subCommand.get().isAdminCommand;
            boolean isOp = sender.isOp();

            if (isAdminCommand && !isOp) {
                return simpleCommand;
            }

            // Step down one
            simpleCommand = subCommand.orElse(simpleCommand);
        }

        return simpleCommand;
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {

        this.sender = sender;
        SimpleCommand command = getCommandFromArgs(args);

        List<String> options = new ArrayList<>(command.subCommands.keySet());
        options.addAll(command.tabComplete);
        options.removeIf(s -> !s.startsWith(args[0].toLowerCase()));
        options.removeIf(s -> !s.startsWith(args[0].toLowerCase()));

        return options;
    }

    public abstract void onCommand();
}
