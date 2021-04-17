package com.entiv.sakuraessentials.magiccrystal;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.BlockPopulator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class MagicCrystalPopulator extends BlockPopulator {

    private static final int TRY_COUNT = 2;

    @Override
    public void populate(@NotNull World world, @NotNull Random random, @NotNull Chunk source) {

        // 防止种子被破解
        Random purpleCrystalRandom = new Random();

        for (int i = 0; i < TRY_COUNT; i++) {

            int x = purpleCrystalRandom.nextInt(16);
            int y = purpleCrystalRandom.nextInt(256);
            int z = purpleCrystalRandom.nextInt(16);

            Location location = source.getBlock(x, y, z).getLocation();
            generationPurpleCrystal(random, world, location);
        }
    }

    private void generationPurpleCrystal(Random random, World world, Location location) {

        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();

        int i = 0;

        while (world.getBlockAt(x, y, z).getType() == Material.STONE) {

            // 生成矿脉的概率, 由于可能会重复, 如 x+1 后 x-1, 所以实际概率会更低
            // 当概率小于 90 时, 平均矿脉数量为 4.8917
            if (random.nextInt(100) < 90) {

                Block block = world.getBlockAt(x, y, z);
                MagicCrystal.setMagicCrystal(block);

                // 方向选择器
                switch (random.nextInt(6)) {
                    case 0:
                        x++;
                        break;
                    case 1:
                        y++;
                        break;
                    case 2:
                        z++;
                        break;
                    case 3:
                        x--;
                        break;
                    case 4:
                        y--;
                        break;
                    case 5:
                        z--;
                        break;
                }

                i++;
            }
        }

        if (i != 0) {
            System.out.println("矿脉数量:" + i);
        }
    }
}
