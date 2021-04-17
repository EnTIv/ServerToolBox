package com.entiv.sakuraessentials.magiccrystal;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class MagicCrystal {
    //TODO 这太笨了. 整个物品方块API吧
    private static final Material MATERIAL = Material.MUSHROOM_STEM;
    private static final BlockData BLOCK_DATA = Bukkit.createBlockData(Material.MUSHROOM_STEM, "[up=false,down=false,north=false,south=false,west=false,east=false]");

    public static void setMagicCrystalBlock(Block block) {
        block.setType(MATERIAL, false);
        block.setBlockData(BLOCK_DATA);
    }

    public static boolean isMagicCrystal(Block block) {
        return block.getType().equals(MATERIAL) && block.getBlockData().equals(BLOCK_DATA);
    }

    public static ItemStack getMagicCrystalItem(int amount) {
        ItemStack itemStack = new ItemStack(Material.LEATHER_HORSE_ARMOR, amount);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setCustomModelData(1);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
