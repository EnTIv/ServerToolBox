package com.entiv.servertoolbox.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {

    public static void send(CommandSender sender, String message) {

        if (message == null) return;
        sender.sendMessage(toColor(message));
    }

    public static void send(CommandSender sender, String... messages) {
        if (messages == null) return;
        sender.sendMessage(toColor(messages));
    }


    public static void sendConfigMessage(JavaPlugin plugin, CommandSender sender, String path) {
        String message = plugin.getConfig().getString(path);
        Message.send(sender, message);
    }

    public static void broadcastConfigMessage(JavaPlugin plugin, String path) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Message.sendConfigMessage(plugin, player, path);
        }
    }

    public static void broadcastMessage(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            Message.send(player, message);
        }
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

    public static String toColor(String string) {
        if (string == null) return "";

        // Then replace RGB color
        Pattern pattern = Pattern.compile("#[A-F|\\d]{6}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);

        while (matcher.find()) {
            String RGBColor = matcher.group();
            net.md_5.bungee.api.ChatColor chatColor = net.md_5.bungee.api.ChatColor.of(RGBColor);
            string = string.replace(RGBColor, "" + chatColor);
        }

        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String[] toColor(String[] strings) {

        String[] copy = new String[strings.length];

        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            copy[i] = toColor(string);
        }

        return copy;
    }

    public static String formatMoney(Number number) {
        DecimalFormat format = new DecimalFormat(",###.##");
        return format.format(number);
    }

    public static String withoutColor(String string) {
        return ChatColor.stripColor(string);
    }

    public static List<String> toColor(List<String> lore) {
        List<String> copy = new ArrayList<>();

        for (String s : lore) {
            copy.add(toColor(s));
        }

        return copy;
    }

    public static String replaceVariables(String string, String... variables) {

        for (int i = 0; i < variables.length; i += 2) {
            string = string.replace(variables[i], variables[i + 1]);
        }

        return string;
    }
}
