package com.entiv.sakuraessentials.library.menu;

import com.entiv.sakuraessentials.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class MenuListener implements Listener {

    @EventHandler
    public void inventoryClickEvent(InventoryClickEvent event) {

        HumanEntity whoClicked = event.getWhoClicked();

        if (!(whoClicked instanceof Player)) return;

        Inventory inventory = event.getInventory();

        Menu menu = getMenu(inventory);
        if (menu == null) return;

        if (menu.isLock) {
            InventoryUtil.denyClick(event, inventory);
        }

        int rawSlot = event.getRawSlot();

        Button button = menu.getButton(rawSlot);
        if (button == null) return;

        event.setCancelled(button.isLock);

        Player player = (Player) whoClicked;

        if (button.clickEvent != null) {
            button.clickEvent.accept(event);
        }

        button.runCommand(player);
    }

    @EventHandler
    public void inventoryDragEvent(InventoryDragEvent event) {

        Inventory inventory = event.getInventory();

        Menu menu = getMenu(event.getInventory());
        if (menu == null) return;

        if (menu.isLock) {
            for (int i : event.getRawSlots()) {

                if (menu.isLock && i < inventory.getSize()) {
                    event.setCancelled(true);
                }

                Button button = menu.getButton(i);

                if (button == null) return;

                if (button.isLock) {
                    event.setCancelled(true);
                    return;
                }
            }
            event.setCancelled(false);
        }
    }

    @EventHandler
    public void inventoryOpenEvent(InventoryOpenEvent event) {

        Menu menu = getMenu(event.getInventory());

        if (menu == null || menu.openEvent == null) return;
        if (menu instanceof PageableMenu) {

            PageableMenu pageableMenu = (PageableMenu) menu;
            boolean isFirstPage = pageableMenu.pages.get(0).equals(event.getInventory());

            if (!isFirstPage) return;
        }
        menu.openEvent.accept(event);
    }

    @EventHandler
    public void inventoryCloseEvent(InventoryCloseEvent event) {

        Menu menu = getMenu(event.getInventory());

        if (menu == null || menu.closeEvent == null) return;
        if (menu instanceof PageableMenu) {

            Bukkit.getScheduler().runTaskLater(Main.getInstance(), () -> {
                Inventory topInventory = event.getPlayer().getOpenInventory().getTopInventory();
                if (!topInventory.equals(menu.inventory)) {
                    menu.closeEvent.accept(event);
                }
            }, 1);

        } else {
            menu.closeEvent.accept(event);
        }
    }

    private Menu getMenu(Inventory inventory) {
        InventoryHolder holder = inventory.getHolder();

        if (!(holder instanceof Menu)) return null;

        return (Menu) holder;
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PlayerInventory inventory = player.getInventory();
            if (getMenu(inventory) == null) {
                player.closeInventory();
            }
        }
    }
}