package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Main;
import com.entiv.servertoolbox.Module;
import com.entiv.servertoolbox.utils.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class KeywordFiltering extends Module implements Listener {

    @Override
    protected void onEnable() {
        registerListener();
    }

    @EventHandler
    private void onChat(AsyncPlayerChatEvent event) {

        if (!config.getBoolean("聊天检测")) {
            return;
        }

        final Player player = event.getPlayer();
        final String message = event.getMessage();

        if (player.isOp()) {
            return;
        }

        Main.debug("检测消息 \"{0}\" 是否存在违禁词: {1}", message, hasKeywords(message));

        if (hasKeywords(message)) {
            event.setCancelled(true);
            Message.send(player, config.getString("提示消息"));
        }
    }

    @EventHandler
    private void onCommand(PlayerCommandPreprocessEvent event) {
        if (!config.getBoolean("命令检测")) {
            return;
        }

        final String message = event.getMessage();
        final Player player = event.getPlayer();

        if (player.isOp()) {
            return;
        }

        Main.debug("检测消息 \"{0}\" 是否存在违禁词: {1}", message, hasKeywords(message));

        if (hasKeywords(message)) {
            event.setCancelled(true);
            Message.send(player, config.getString("提示消息"));
        }
    }

    @EventHandler
    private void onAnvilChangeName(InventoryClickEvent event) {
        if (!config.getBoolean("铁砧检测")) return;

        final Player player = event.getWhoClicked() instanceof Player ? ((Player) event.getWhoClicked()) : null;
        final Inventory inventory = event.getClickedInventory();

        if (player == null || inventory == null) return;

        if (!inventory.getType().equals(InventoryType.ANVIL)) return;

        final ItemStack item = inventory.getItem(3);

        if (player.isOp() || item == null) return;

        if (!item.getItemMeta().hasDisplayName()) {
            return;
        }

        final String displayName = item.getItemMeta().getDisplayName();

        if (hasKeywords(displayName)) {
            event.setCancelled(true);
            Message.send(player, config.getString("提示消息"));
        }
    }

    private boolean hasKeywords(String message) {
        for (String keyword : config.getStringList("违禁词列表")) {
            if (message.matches(keyword)) {
                return true;
            }
        }
        return false;
    }

    private List<String> getKeywords() {
        return config.getStringList("违禁词");
    }
}
