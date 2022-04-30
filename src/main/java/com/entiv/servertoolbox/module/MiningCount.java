package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Main;
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


public class MiningCount extends Module implements Listener {

    @Override
    protected void onEnable() {
        registerListener();
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final ItemStack itemStack = player.getInventory().getItemInMainHand();

        Main.debug("您当前使用的工具为 {0}",itemStack.getType());

        for (String toolName : config.getStringList("需要计数的工具")) {

            if (itemStack.getType().toString().contains("_" + toolName)) {

                Main.debug("检测到可用工具");

                final ItemMeta itemMeta = itemStack.getItemMeta();
                final List<String> lore = itemMeta.getLore();

                String key = config.getString("lore内容");
                Validate.notNull(key, "配置文件错误，请检查配置文件");

                key = ChatColor.translateAlternateColorCodes('&', key);

                if (lore == null || lore.isEmpty()) {
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
                                Main.debug("当前挖掘数量: {0}", count);
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
}
