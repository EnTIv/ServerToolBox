package com.entiv.servertoolbox.utils;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemBuilder {

    private final ItemStack itemStack;
    private ItemMeta itemMeta;

    public ItemBuilder(Material material, int amount, short data) {
        if (material == null) material = Material.STONE;

        itemStack = new ItemStack(material, amount, data);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material, int amount) {
        if (material == null) material = Material.STONE;

        itemStack = new ItemStack(material, amount);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = new ItemStack(itemStack);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this(material, 1);
        itemMeta = itemStack.getItemMeta();
    }

    public ItemBuilder name(String displayName) {

        if (displayName == null) return this;

        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder lore(String... lore) {

        List<String> collect = Arrays.stream(lore)
                .map(string -> ChatColor.translateAlternateColorCodes('&', string))
                .collect(Collectors.toList());

        itemMeta.setLore(collect);
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder lore(List<String> lore) {

        List<String> collect = lore.stream()
                .map(string -> ChatColor.translateAlternateColorCodes('&', string))
                .collect(Collectors.toList());

        itemMeta.setLore(collect);
        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemBuilder addFlag(ItemFlag itemFlag) {
        itemMeta.addItemFlags(itemFlag);
        itemStack.setItemMeta(itemMeta);
        return this;
    }


    public ItemBuilder setUnbreakable() {

        itemMeta.setUnbreakable(true);

        itemStack.setItemMeta(itemMeta);

        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}