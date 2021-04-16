package com.entiv.sakuraessentials.details;

import com.entiv.sakuraessentials.Main;
import com.entiv.sakuraessentials.library.util.StatisticBlock;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class PlayerListener implements Listener {

    // 设置 pvp 世界重生点
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        World world = event.getPlayer().getWorld();

        if (world.getName().equals("world_pvp")) {
            Location location = new Location(world, -0.5, 100, 0.5, -90, 0);
            event.setRespawnLocation(location);
        }
    }

    // 虚空秒杀
    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        EntityDamageEvent.DamageCause cause = event.getCause();
        if (cause.equals(EntityDamageEvent.DamageCause.VOID)) {
            event.setDamage(200);
        }
    }

    // 设置 tab 普通玩家的命令列表
    @EventHandler
    public void onPlayerCommandSendEvent(PlayerCommandSendEvent event) {

        if (!event.getPlayer().isOp()) {
            Collection<String> commands = event.getCommands();
            commands.clear();

            commands.add("tpa");
            commands.add("tpahere");

            commands.add("home");
            commands.add("spawn");

            commands.add("kit");

            commands.add("t");
            commands.add("tell");
            commands.add("m");
            commands.add("msg");

            commands.add("r");
            commands.add("reply");

            commands.add("is");
            commands.add("island");


        }
    }


    @EventHandler
    public void test(EntityShootBowEvent event) {

        StatisticBlock statisticBlock = new StatisticBlock(Main.getInstance());

        World world = Bukkit.getWorld("world_resource");

        Location min = new Location(world, 0, 0, 0);
        Location max = new Location(world, 100, 130, 100);

        Set<Material> blocks = new HashSet<>();
        blocks.add(Material.DIAMOND_ORE);
        blocks.add(Material.MUSHROOM_STEM);

//        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
//            Map<Material, Integer> result = statisticBlock.statisticBlocks(world, min, max, blocks);
//            result.forEach((k, v) -> System.out.println(k + ": " + v));
//        });

        CompletableFuture<Map<Material, Integer>> future = statisticBlock.callTaskFutureAsync(() -> statisticBlock.statisticBlocks(world, min, max, blocks));

//        future.thenAccept((result) -> result.forEach((k, v) -> System.out.println(k + ": " + v)));
        future.whenComplete((k, v) -> System.out.println("结果2: " + k + ": " + v));
    }

    @EventHandler
    public void onAdvancementDone(PlayerAdvancementDoneEvent event) {
        Player player = event.getPlayer();
        Advancement advancement = event.getAdvancement();

        NamespacedKey key = advancement.getKey();
        //TODO 根据 key 给予紫水晶


    }
}
