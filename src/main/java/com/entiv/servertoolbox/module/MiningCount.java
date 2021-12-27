package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Module;
import com.entiv.servertoolbox.utils.StringUtil;
import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class MiningCount extends Module implements Listener {

    @Override
    protected void onEnable() {
        registerListener();
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final ItemStack itemStack = player.getInventory().getItemInMainHand();

        if (itemStack.getType().toString().contains("PICKAXE")) {

            final ItemMeta itemMeta = itemStack.getItemMeta();
            final List<String> lore = itemMeta.getLore();

            String key = config.getString("lore内容");
            Validate.notNull(key, "配置文件错误，请检查配置文件");

            key = ChatColor.translateAlternateColorCodes('&', key);

            if (lore == null) {
                final List<String> addLore = new ArrayList<>();
                addLore.add(key.replace("%count%", "1"));
                itemMeta.setLore(addLore);
            } else {
                for (int i = 0; i < lore.size(); i++) {
                    final String s = lore.get(i);

                    key = ChatColor.stripColor(key.replace("%count%", ""));

                    if (s.contains(key)) {
                        int finalI = i;

                        StringUtil.findInt(s).stream().findFirst().ifPresent(count -> {
                            lore.set(finalI, s.replace(String.valueOf(count), String.valueOf(count + 1)));
                            itemMeta.setLore(lore);
                        });

                    }
                }
            }

            itemStack.setItemMeta(itemMeta);
        }
    }
}
