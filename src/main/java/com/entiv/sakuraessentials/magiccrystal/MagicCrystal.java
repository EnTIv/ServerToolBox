package com.entiv.sakuraessentials.magiccrystal;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;


public class MagicCrystal {
    private static final Material MATERIAL = Material.MUSHROOM_STEM;
    private static final BlockData BLOCK_DATA = Bukkit.createBlockData(Material.MUSHROOM_STEM, "[up=false,down=false,north=false,south=false,west=false,east=false]");

    public static void setMagicCrystal(Block block) {
        block.setType(MATERIAL, false);
        block.setBlockData(BLOCK_DATA);
    }

    public static boolean isMagicCrystal(Block block) {
        return block.getType().equals(MATERIAL) && block.getBlockData().equals(BLOCK_DATA);
    }
}
