package com.entiv.sakuraessentials.library.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.FutureTask;

public class StatisticBlock {

    private final Plugin plugin;

    public StatisticBlock(Plugin plugin) {
        this.plugin = plugin;
    }

    public static void demo() {

//        StatisticBlock statisticBlock = new StatisticBlock(Main.getInstance());
//        CompletableFuture<Map<Material, Integer>> future = statisticBlock.callTaskFutureAsync(() -> {
//
//            World world = Bukkit.getWorld("world_resource");
//
//            Location min = new Location(world, 0, 0, 0);
//            Location max = new Location(world, 10, 10, 10);
//
//            Set<Material> blocks = new HashSet<>();
//            blocks.add(Material.DIAMOND_ORE);
//            blocks.add(Material.MUSHROOM_STEM);
//
//            return statisticBlock.statisticBlocks(world, min, max, blocks);
//        });
//
//        future.thenAccept((result) -> result.forEach((k, v) -> System.out.println(k + ": " + v)));

//        StatisticBlock statisticBlock = new StatisticBlock(Main.getInstance());
//        World world = Bukkit.getWorld("world_resource");
//
//        Location min = new Location(world, 0, 0, 0);
//        Location max = new Location(world, 10, 10, 10);
//
//        Set<Material> blocks = new HashSet<>();
//        blocks.add(Material.DIAMOND_ORE);
//        blocks.add(Material.MUSHROOM_STEM);
//
//        Map<Material, Integer> map = statisticBlock.statisticBlocks(world, min, max, blocks);
//        map.forEach((k, v) -> System.out.println(k + ": " + v));

    }

    public <T> CompletableFuture<T> callTaskFutureAsync(Callable<T> callable) {
        CompletableFuture<T> future = new CompletableFuture<>();
        FutureTask<T> futureTask = new FutureTask<>(callable);

        Runnable task = () -> {
            try {
                futureTask.run();
                future.complete(future.get());
            } catch (Throwable t) {
                future.completeExceptionally(t);
            }
        };

        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, task);
        return future;
    }

    public Map<Material, Integer> statisticBlocks(World world, Location pos1, Location pos2, Set<Material> types) {
        Vector min = new Vector(
                Math.min(pos1.getX(), pos2.getX()),
                Math.min(pos1.getY(), pos2.getY()),
                Math.min(pos1.getZ(), pos2.getZ())
        );

        Vector max = new Vector(
                Math.max(pos1.getX(), pos2.getX()),
                Math.max(pos1.getY(), pos2.getY()),
                Math.max(pos1.getZ(), pos2.getZ())
        );

        Map<Material, Integer> result = new HashMap<>();

        for (int i = min.getBlockX(); i < max.getBlockX(); i++) {
            for (int j = min.getBlockY(); j < max.getBlockY(); j++) {
                for (int k = min.getBlockZ(); k < max.getBlockZ(); k++) {

                    Block block = world.getBlockAt(i, j, k);
                    Material type = block.getType();

                    if (types.contains(type)) {
                        int count = result.getOrDefault(type, 0);
                        result.put(type, ++count);
                    }
                }
            }
        }
        return result;
    }

}
