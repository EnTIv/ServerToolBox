package com.entiv.sakuraessentials.library.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

import static com.entiv.sakuraessentials.library.message.Format.toColor;

public class Message {

    public static void sendTip(CommandSender player, String message, String... variables) {

        Component textComponent = Component.text("ꑠ ")
                .append(Component.text(message, TextColor.color(0xccffce)));

        if (variables.length > 1) {
            textComponent = replaceVariables(textComponent, variables);
        }

        player.sendMessage(textComponent);
    }

    public static void sendWarn(CommandSender player, String message, String... variables) {

        Component textComponent = Component.text("ꑟ ")
                .append(Component.text(message, TextColor.color(0xf2cc6d)));

        if (variables.length > 1) {
            textComponent = replaceVariables(textComponent, variables);
        }

        player.sendMessage(textComponent);
    }

    public static void sendError(CommandSender player, String message, String... variables) {

        Component textComponent = Component.text("ꑜ ")
                .append(Component.text(message, NamedTextColor.RED));

        if (variables.length > 1) {
            textComponent = replaceVariables(textComponent, variables);
        }

        player.sendMessage(textComponent);
    }

    private static Component replaceVariables(Component component, String... variables) {

        for (int i = 0; i < variables.length; i += 2) {

            TextReplacementConfig replacement = TextReplacementConfig.builder()
                    .matchLiteral(variables[i])
                    .replacement(Component.text(variables[i + 1], TextColor.color(0xbde0fe)))
                    .build();

            component = component.replaceText(replacement);
        }

        return component;
    }

    public static void sendConsole(String message) {
        if (message == null || message.isEmpty()) return;

        ConsoleCommandSender consoleSender = Bukkit.getConsoleSender();
        consoleSender.sendMessage(toColor(message));
    }

    public static void sendConsole(String[] message) {
        if (message == null || message.length == 0) return;

        ConsoleCommandSender consoleSender = Bukkit.getConsoleSender();
        consoleSender.sendMessage(toColor(message));
    }

    public static void sendConsole(List<String> message) {
        if (message == null || message.size() == 0) return;

        for (String string : message) {
            sendConsole(string);
        }
    }

}
