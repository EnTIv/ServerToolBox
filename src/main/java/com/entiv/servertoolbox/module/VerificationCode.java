package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Module;
import com.entiv.servertoolbox.utils.CommandUtil;
import com.entiv.servertoolbox.utils.Message;
import com.entiv.servertoolbox.utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class VerificationCode extends Module implements Listener {

    private final Random random = new Random();
    private final List<UUID> verificationPlayers = new ArrayList<>();
    private final Map<UUID, Long> activeTime = new HashMap<>();

    private BukkitRunnable runnable;

    @Override
    protected void onEnable() {
        registerListener();
        setRunnable(config.getInt("触发概率"), config.getInt("检测间隔") * 20);
    }

    @Override
    protected void unload() {
        super.unload();
        runnable.cancel();
    }

    private void setRunnable(int chance, int period) {
        runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (chance > random.nextDouble() * 100) {
                    Bukkit.getOnlinePlayers()
                            .stream()
                            .filter(player -> !player.isOp())
                            .forEach(player -> {
                                Long activeTime = VerificationCode.this.activeTime.getOrDefault(player.getUniqueId(), 99999999999L);

                                int checkTime = config.getInt("检测间隔") * 20;

                                if (System.currentTimeMillis() - activeTime < checkTime * 1000L) {
                                    return;
                                }

                                Inventory inventory = new VerificationInventory().getInventory();
                                player.openInventory(inventory);
                                verificationPlayers.add(player.getUniqueId());

                                int time = config.getInt("验证超时时长");
                                List<String> commands = config.getStringList("验证失败指令");

                                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                    if (verificationPlayers.contains(player.getUniqueId())) {
                                        CommandUtil.execute(player, commands);
                                        verificationPlayers.remove(player.getUniqueId());
                                    }
                                }, time * 20L);
                            });

                }

            }
        };
        runnable.runTaskTimer(plugin, period, period);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Inventory inventory = event.getInventory();

        VerificationInventory verificationInventory = inventory.getHolder() instanceof VerificationInventory ? ((VerificationInventory) inventory.getHolder()) : null;
        Player player = event.getWhoClicked() instanceof Player ? ((Player) event.getWhoClicked()) : null;

        if (verificationInventory == null || player == null) return;

        event.setCancelled(true);

        int slot = event.getSlot();

        if (verificationInventory.verificationSlot == slot) {
            player.closeInventory();
            Message.send(player, config.getString("验证通过消息"));
            verificationPlayers.remove(player.getUniqueId());
        }
    }

    @EventHandler
    public void onPlayerI(PlayerInteractEvent event) {
        activeTime.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
    }

    private class VerificationInventory implements InventoryHolder {

        private final Inventory inventory;
        private final int verificationSlot;


        public VerificationInventory() {
            inventory = Bukkit.createInventory(this, 54, config.getName());
            verificationSlot = random.nextInt(54);

            setupInventory();
        }

        @Override
        public Inventory getInventory() {
            return inventory;
        }

        private void setupInventory() {
            inventory.setItem(verificationSlot, verificationButton());

            for (int i = 0; i < inventory.getSize(); i++) {
                ItemStack itemStack = inventory.getItem(i);
                if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
                    inventory.setItem(i, otherButton());
                }
            }
        }

        private ItemStack verificationButton() {
            ItemStack itemStack = XMaterial.LIME_STAINED_GLASS_PANE.parseItem();
            if (itemStack == null) itemStack = new ItemStack(Material.STONE);

            String displayName = config.getString("验证通过按钮");

            ItemMeta itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(Message.toColor(displayName));
            itemStack.setItemMeta(itemMeta);

            return itemStack;
        }

        private ItemStack otherButton() {
            ItemStack itemStack = XMaterial.RED_STAINED_GLASS_PANE.parseItem();
            return itemStack == null ? new ItemStack(Material.STONE): itemStack;
        }
    }
}
