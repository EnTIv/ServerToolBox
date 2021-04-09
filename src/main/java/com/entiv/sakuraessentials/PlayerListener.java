package com.entiv.sakuraessentials;

import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Collection;

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

}
