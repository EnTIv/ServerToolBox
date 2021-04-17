package com.entiv.sakuraessentials.magiccrystal.command;

import com.entiv.sakuraessentials.Main;
import com.entiv.sakuraessentials.library.command.SimpleCommand;
import com.entiv.sakuraessentials.library.message.Message;
import com.entiv.sakuraessentials.library.util.StatisticBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StatisticCommand extends SimpleCommand {

    protected StatisticCommand(JavaPlugin plugin, SimpleCommand parent, String label) {
        super(plugin, parent, label);
        setUsage("/crystal statistic");
        setDescription("统计魔晶矿数量");
        setAdminCommand();
    }

    @Override
    public void onCommand() {

        Message.send(sender, "§6统计数据中, 这可能需要一些时间...");
        StatisticBlock statisticBlock = new StatisticBlock(Main.getInstance());

        World world = Bukkit.getWorld("world_resource");

        Location min = new Location(world, 0, 0, 0);
        Location max = new Location(world, 100, 130, 100);

        Set<Material> blocks = new HashSet<>();
        blocks.add(Material.DIAMOND_ORE);
        blocks.add(Material.MUSHROOM_STEM);

        Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
            Map<Material, Integer> result = statisticBlock.statisticBlocks(world, min, max, blocks);

            if (result.isEmpty()) {
                Message.send(sender, "魔晶矿统计完毕, 未找到魔晶矿");
            } else {
                Message.send(sender, "§6魔晶矿统计完毕, 列出统计结果:");
                result.forEach((k, v) -> Message.send(sender, "§a" + k + ": §b" + v));
            }
        });
    }
}
