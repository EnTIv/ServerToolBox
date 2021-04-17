package com.entiv.sakuraessentials.magiccrystal.command;

import com.entiv.sakuraessentials.library.command.SimpleCommand;
import com.entiv.sakuraessentials.library.message.Message;
import com.entiv.sakuraessentials.magiccrystal.MagicCrystal;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class BalCommand extends SimpleCommand {

    protected BalCommand(JavaPlugin plugin, SimpleCommand parent, String label) {
        super(plugin, parent, label);
        setUsage("/crystal bal 玩家");
        setDescription("查看某玩家的魔晶矿数量");
    }

    @Override
    public void onCommand() {
        Message.sendConsole("寻找魔晶矿中, 这可能需要一些时间...");

        Player player = getPlayer();
        Location location = player.getLocation();

        World world = location.getWorld();

        Location min = new Location(world, location.getBlockX() - 30, 0, location.getBlockZ() - 30);
        Location max = new Location(world, location.getBlockX() + 30, 130, location.getBlockZ() + 30);

        if (findBlock(world, min, max)) {
            Message.sendTip(player, "魔晶矿已找到, 您已经前往了对应地点");
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 1, 6000));
        } else {
            Message.sendWarn(player, "附近未找到魔晶矿...");
        }
    }

    private boolean findBlock(World world, Location pos1, Location pos2) {

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


        for (int i = min.getBlockX(); i < max.getBlockX(); i++) {
            for (int j = min.getBlockY(); j < max.getBlockY(); j++) {
                for (int k = min.getBlockZ(); k < max.getBlockZ(); k++) {

                    Block block = world.getBlockAt(i, j, k);
                    if (MagicCrystal.isMagicCrystal(block)) {
                        Player player = getPlayer();
                        player.teleport(block.getLocation());
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
