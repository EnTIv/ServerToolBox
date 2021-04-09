package com.entiv.sakuraessentials.library.message;

import org.bukkit.ChatColor;

import java.text.DecimalFormat;
import java.util.List;

public class Format {

    public static String toColor(String string) {
        if (string == null) return null;
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static String[] toColor(String[] strings) {

        for (int i = 0; i < strings.length; i++) {
            String string = strings[i];
            strings[i] = (toColor(string));
        }

        return strings;
    }

    public static List<String> toColor(List<String> strings) {
        for (int i = 0; i < strings.size(); i++) {
            String string = strings.get(i);
            strings.set(i, toColor(string));
        }

        return strings;
    }

    public static String formatMoney(Number number) {
        DecimalFormat format = new DecimalFormat(",###.##");
        return format.format(number);
    }

    public static String withoutColor(String string) {
        return ChatColor.stripColor(string);
    }
}
