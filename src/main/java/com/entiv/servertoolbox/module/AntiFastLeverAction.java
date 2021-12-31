package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Module;
import com.entiv.servertoolbox.utils.Message;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class AntiFastLeverAction extends Module implements Listener {

    private Cache<UUID, Integer> cache;

    @Override
    protected void onEnable() {
        registerListener();

        cache = CacheBuilder.newBuilder().expireAfterWrite(config.getInt("时间"), TimeUnit.MILLISECONDS).build();
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) throws ExecutionException {
        final Player player = event.getPlayer();
        final UUID uniqueId = player.getUniqueId();
        final Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null || !clickedBlock.getType().equals(Material.LEVER)) return;

        final Integer count = cache.get(uniqueId, () -> 0);

        if (count > config.getInt("次数")) {
            event.setCancelled(true);
            Message.send(player, config.getString("提示消息"));
        } else {
            cache.put(uniqueId, count + 1);
        }
    }
}
