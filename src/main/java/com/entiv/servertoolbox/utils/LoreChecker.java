package com.entiv.servertoolbox.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LoreChecker {

    private final ItemStack itemStack;
    public final ItemMeta itemMeta;
    public final List<String> lore;

    public LoreChecker(ItemStack itemStack) {
        this.itemStack = itemStack;
        itemMeta = itemStack.getItemMeta();
        lore = itemMeta.getLore();
    }

    public Integer getIndex(String key) {
        for (int i = 0; i < lore.size(); i++) {
            if (lore.get(i).contains(key)) {
                return i;
            }
        }
        return null;
    }

    public void replaceLore(int i, String value) {
        lore.set(i, value);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    public int getInt(int index) {

        String s = lore.get(index);
        Pattern p = Pattern.compile("[0-9]+");

        String[] split = s.split("[:：]");
        Matcher matcher = p.matcher(ChatColor.stripColor(split[1]));

        if (matcher.find()) {
            return Integer.parseInt(matcher.group(0));
        }

        throw new NullPointerException("找不到整数");
    }

    public double getDouble(int i) {

        String s = lore.get(i);

        Pattern p = Pattern.compile("[0-9]*\\.?[0-9]+");

        String[] split = s.split("[:：]");
        Matcher matcher = p.matcher(ChatColor.stripColor(split[1]));

        if (matcher.find()) {
            return Double.parseDouble(matcher.group(0));
        }

        throw new NullPointerException("找不到小数");
    }

    public Player getPlayer(int i) {

        String s = lore.get(i);

        Pattern p = Pattern.compile("[a-zA-Z_]+");
        Matcher matcher = p.matcher(s);

        if (matcher.find()) {
            return Bukkit.getPlayer(matcher.group(0));
        }
        throw new NullPointerException("找不到玩家");
    }

    public int[] getStoreAndMax(int index) {

        String s = lore.get(index);
        Pattern p = Pattern.compile("[0-9]+");

        String[] split = s.split("[:：]");
        String matcher = ChatColor.stripColor(split[1]).trim();

        int[] ints = new int[2];

        ints[0] = Integer.parseInt(matcher.split("/")[0]);
        ints[1] = Integer.parseInt(matcher.split("/")[1]);

        return ints;
    }
}