package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Main;
import com.entiv.servertoolbox.Module;
import com.entiv.servertoolbox.utils.ItemStackUtil;
import com.entiv.servertoolbox.utils.Message;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;

public class AutoTool extends Module implements Listener, CommandExecutor {

    private final Set<UUID> disablePlayers = new HashSet<>();
    private static final String PERMISSION = "ServerToolBox.AutoTool";

    @Override
    protected void onEnable() {
        registerListener();
        registerCommand("AutoTool");
    }

    @EventHandler
    private void onPlayerClickBlock(PlayerInteractEvent event) {
        final Player player = event.getPlayer();

        if (disablePlayers.contains(player.getUniqueId())) {
            return;
        }

        if (!player.hasPermission(PERMISSION)) {
            return;
        }

        if (!event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }

        final Block block = event.getClickedBlock();

        if (block == null) {
            return;
        }

        List<Tool> tools = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            final ItemStack item = player.getInventory().getItem(i);

            if (ItemStackUtil.isAir(item)) continue;

            if (isPreferredTool(item, block)) {
                tools.add(new Tool(i, item));
            }
        }

        tools.stream()
                .max(Comparator.comparingInt(Tool::getToolValue))
                .ifPresent(tool -> {
                    if (tool.value == 0) return;
                    player.getInventory().setHeldItemSlot(tool.slot);
                });
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        final UUID uuid = player.getUniqueId();

        disablePlayers.remove(uuid);
    }

    private boolean isPreferredTool(ItemStack tool, Block block) {
        final ConfigurationSection section = config.getConfigurationSection("可挖掘方块");

        Validate.notNull(section, "可挖掘方块配置错误，请检查配置文件");

        List<String> canBreakBlocks;
        final String toolName = tool.getType().toString();

        Main.debug("您使用的工具名为: {0}", toolName);
        Main.debug("您破坏的方块为: {0}", block.getType());

        if (toolName.contains("SWORD")) {
            canBreakBlocks = section.getStringList("剑");
        } else if (toolName.contains("SHOVEL") || toolName.contains("SPADE")) {
            canBreakBlocks = section.getStringList("铲子");
        } else if (toolName.contains("PICKAXE")) {
            canBreakBlocks = section.getStringList("镐子");
        } else if (toolName.contains("AXE")) {
            canBreakBlocks = section.getStringList("斧头");
        } else {
            return false;
        }

        return canBreakBlocks.stream()
                .map(Material::matchMaterial)
                .collect(Collectors.toList())
                .contains(block.getType());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        final Player player = sender instanceof Player ? ((Player) sender) : null;

        if (player == null || !player.hasPermission(PERMISSION)) return false;

        final UUID uuid = player.getUniqueId();

        final String message = config.getString("提示消息", "");

        if (disablePlayers.contains(uuid)) {
            disablePlayers.remove(uuid);
            Message.send(player, message.replace("%enable%", "开启"));
        } else {
            disablePlayers.add(uuid);
            Message.send(player, message.replace("%enable%", "关闭"));
        }

        return true;
    }

    private static class Tool {

        private final ItemStack itemStack;
        private final int value;
        private final int slot;

        public Tool(int slot, ItemStack itemStack) {
            this.itemStack = itemStack;
            this.slot = slot;

            Main.debug("检测到工具 {0} 槽位 {1}", itemStack, slot);

            value = getToolValue();
        }

        public int getToolValue() {
            final String name = itemStack.getType().toString();

            if (name.contains("WOODEN_")) {
                return 1;
            } else if (name.contains("STONE_")) {
                return 2;
            } else if (name.contains("GOLDEN_")) {
                return 3;
            } else if (name.contains("IRON_")) {
                return 4;
            } else if (name.contains("DIAMOND_")) {
                return 5;
            } else if (name.contains("NETHERITE_")) {
                return 6;
            } else {
                return 0;
            }
        }
    }
}
