package com.entiv.sakuraessentials;

import com.entiv.sakuraessentials.Main;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.Collection;

public class PlayerListener implements Listener {

    // 设置 pvp 世界重生点
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        FileConfiguration config = Main.getInstance().getConfig();
        if (!config.getBoolean("设置世界重生点.启用")) {
            return;
        }


        World world = event.getPlayer().getWorld();

        ConfigurationSection section = config.getConfigurationSection("设置世界重生点.世界列表");
        Validate.notNull(section, "配置文件路径 设置世界重生点.世界列表 不存在, 请检查配置文件");

        for (String key : section.getKeys(false)) {
            if (world.getName().equals(key)) {

                String locationConfig = section.getString(key);
                if (locationConfig == null) continue;

                String[] split = locationConfig.replaceAll("\\s*", "").split(",");
                Location location = new Location(Bukkit.getWorld(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), Double.parseDouble(split[3]), Float.parseFloat(split[4]),Float.parseFloat(split[5]));

                event.setRespawnLocation(location);
            }
        }

    }

    // 设置 tab 普通玩家的命令列表
    @EventHandler
    public void onPlayerCommandSendEvent(PlayerCommandSendEvent event) {

        FileConfiguration config = Main.getInstance().getConfig();
        if (!config.getBoolean("设置命令补全.启用")) {
            return;
        }

        if (!event.getPlayer().isOp()) {
            Collection<String> commands = event.getCommands();
            commands.clear();
            commands.addAll(config.getStringList("设置命令补全.补全列表"));
        }
    }

    // 设置地狱门
    @EventHandler
    public void onPlayerPortalEvent(PlayerPortalEvent event) {

        FileConfiguration config = Main.getInstance().getConfig();
        if (!config.getBoolean("地狱门随机传送.启用")) {
            return;
        }

        Player player = event.getPlayer();
        World world = player.getWorld();

        ConfigurationSection section = config.getConfigurationSection("地狱门随机传送.世界列表");
        Validate.notNull(section, "配置文件路径 地狱门随机传送.世界列表 不存在, 请检查配置文件");

        for (String key : section.getKeys(false)) {
            if (world.getName().equals(key)) {

                event.setCancelled(true);
                String command = section.getString(key);

                if (command == null) continue;

                player.performCommand(command);
            }
        }
    }

    // 反绑定诅咒南瓜头
    @EventHandler
    public void onEnchant(PrepareAnvilEvent event) {

        FileConfiguration config = Main.getInstance().getConfig();
        if (!config.getBoolean("反无限耐久绑定.启用")) {
            return;
        }

        ItemStack result = event.getResult();
        Player player = event.getView().getPlayer() instanceof Player ? ((Player) event.getView().getPlayer()) : null;

        if (result == null || player == null) return;

        if (result.getType().getMaxDurability() == 0 && result.getEnchantments().containsKey(Enchantment.BINDING_CURSE)) {
            event.setResult(new ItemStack(Material.AIR));
            player.updateInventory();
        }
    }
}
