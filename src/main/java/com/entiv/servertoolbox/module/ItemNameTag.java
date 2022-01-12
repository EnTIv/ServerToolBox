package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Module;
import com.entiv.servertoolbox.utils.ConfigDeserialize;
import com.entiv.servertoolbox.utils.ItemStackUtil;
import com.entiv.servertoolbox.utils.Message;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;

public class ItemNameTag extends Module implements Listener, CommandExecutor {

    @Override
    protected void onEnable() {
        registerCommand("ItemNameTag");
    }

    private ItemStack getItemNameTag() {
        final ConfigurationSection section = config.getConfigurationSection("改名卡配置");
        Validate.notNull(section, name + " 改名卡配置异常，请检查配置文件");

        return ConfigDeserialize.getItemStack(section.getCurrentPath());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        // /int give player amount
        if (args.length == 3 && args[0].equalsIgnoreCase("give")) {

            final Player player = Bukkit.getPlayer(args[1]);

            if (player == null) {
                Message.send(sender, MessageFormat.format("玩家 {0} 当前不在线", args[1]));
            } else {
                final PlayerInventory inventory = player.getInventory();
                final ItemStack nameTag = getItemNameTag();

                nameTag.setAmount(Integer.parseInt(args[2]));
                inventory.addItem(nameTag);
                Message.send(sender, MessageFormat.format("玩家 {0} 得到了 {1} 个物品命名牌", args[1], args[2]));
            }

            return true;
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("name")) {

            String name = args[1];
            final Player player = sender instanceof Player ? ((Player) sender) : null;

            if (player == null) {
                Message.send(sender, "该指令只能由玩家使用");
                return true;
            }

            final PlayerInventory inventory = player.getInventory();
            final ItemStack itemStack = inventory.getItemInMainHand();

            @SuppressWarnings("all")
            final boolean hasItemNameTag = Arrays.stream(inventory.getStorageContents())
                    .filter(Objects::nonNull)
                    .anyMatch(item -> item.isSimilar(getItemNameTag()));

            if (!hasItemNameTag) {
                Message.send(sender, config.getString("提示消息.无物品命名牌"));
                return true;
            }

            if (ItemStackUtil.isAir(itemStack)) {
                Message.send(sender, config.getString("提示消息.手上无物品"));
                return true;
            }

            if (itemStack.getAmount() > 1) {
                Message.send(sender, config.getString("提示消息.物品数量过多"));
                return true;
            }

            if (!player.hasPermission("ItemNameTag.color")) {
                name = ChatColor.stripColor(name);
            } else {
                name = ChatColor.translateAlternateColorCodes('&', name);
            }

            final ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(name);
            itemStack.setItemMeta(itemMeta);
            inventory.removeItem(getItemNameTag());

            Message.send(sender, config.getString("提示消息.修改成功"));

            return true;
        }

        Message.send(sender, config.getString("提示消息.帮助消息"));

        return false;
    }

}
