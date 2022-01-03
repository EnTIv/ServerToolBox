package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Module;
import com.entiv.servertoolbox.utils.CommandUtil;
import com.entiv.servertoolbox.utils.ItemStackUtil;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class WorldItemBlacklist extends Module implements Listener {

    @Override
    protected void onEnable() {
        registerListener();
    }

    @EventHandler
    private void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        final Player player = event.getPlayer();
        final World world = player.getWorld();

        final List<String> worldList = config.getStringList("世界列表");

        if (worldList.contains(world.getName()) && hasBlacklistItem(player)) {
            CommandUtil.execute(player, config.getStringList("执行指令"));
        }

    }

    private boolean hasBlacklistItem(Player player) {
        final List<Material> list = config.getStringList("物品列表.id")
                .stream()
                .map(Material::matchMaterial)
                .collect(Collectors.toList());

        for (ItemStack itemStack : player.getInventory()) {
            if (ItemStackUtil.isAir(itemStack)) {
                continue;
            }

            if (list.contains(itemStack.getType())) {
                return true;
            }
        }

        return false;
    }
}
