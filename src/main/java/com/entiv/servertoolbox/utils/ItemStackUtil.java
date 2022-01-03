package com.entiv.servertoolbox.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemStackUtil {

    public static boolean isAir(ItemStack itemStack) {
        return itemStack == null || itemStack.getType().equals(Material.AIR);
    }
}
