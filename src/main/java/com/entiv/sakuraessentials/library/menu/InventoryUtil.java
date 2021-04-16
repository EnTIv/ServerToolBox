package com.entiv.sakuraessentials.library.menu;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryUtil {

    public static void denyClick(InventoryClickEvent event, Inventory targetInventory) {

        int size = targetInventory.getSize();

        if (event.getRawSlot() < size || event.isShiftClick()) {
            event.setCancelled(true);
        }
    }

    public static void denyDrag(InventoryDragEvent event, Inventory targetInventory) {

        Inventory inventory = event.getInventory();
        if (!inventory.equals(targetInventory)) {
            return;
        }

        int size = event.getInventory().getSize();
        for (int i : event.getRawSlots()) {
            if (i < size) {
                event.setCancelled(true);
            }
        }
    }

    public static int getFreeSpace(Inventory inventory, ItemStack itemStack) {

        int amount = 0;

        for (ItemStack invStack : inventory.getStorageContents()) {
            if (invStack == null) {
                amount += itemStack.getMaxStackSize();
            } else if (invStack.isSimilar(itemStack)) {
                amount += invStack.getMaxStackSize() - invStack.getAmount();
            }
        }

        return amount;
    }

    public static int hasItemAmount(Inventory inventory, ItemStack itemStack) {

        int amount = 0;

        for (ItemStack invStack : inventory.getStorageContents()) {

            if (invStack != null && invStack.isSimilar(itemStack)) {
                amount += invStack.getAmount();
            }
        }
        return amount;
    }

}
