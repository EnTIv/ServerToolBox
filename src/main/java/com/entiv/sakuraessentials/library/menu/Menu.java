package com.entiv.sakuraessentials.library.menu;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class Menu implements InventoryHolder {

    public boolean isLock = true;

    public Consumer<InventoryOpenEvent> openEvent;
    public Consumer<InventoryCloseEvent> closeEvent;

    public final Map<Integer, Button> buttonMap = new HashMap<>();

    private int size = 3 * 9;
    private String title = "Menu";

    public Inventory inventory = Bukkit.createInventory(this, size, title);

    public void open(Player player) {
        player.openInventory(inventory);
    }

    public void setButton(Collection<Button> buttons) {
        for (Button button : buttons) {
            setButton(button);
        }
    }

    public void setButton(Button button) {

        for (int slot : button.slots) {
            buttonMap.put(slot, button);
            inventory.setItem(slot, button.itemStack);
        }

        if (button.action != null) {
            setDefaultButtonAction(button);
            setButtonAction(button);
        }
    }

    public void setDefaultButtonAction(Button button) {
        if (button.action.equals("Close")) {
            button.clickEvent = event -> event.getWhoClicked().closeInventory();
        }
    }

    public boolean isOpen(Player player) {
        Inventory topInventory = player.getOpenInventory().getTopInventory();
        return topInventory.equals(inventory);
    }

    public void removeButton(int slot) {
        buttonMap.remove(slot);
    }

    public boolean isButton(int slot) {
        return buttonMap.get(slot) != null;
    }

    public abstract void setButtonAction(Button button);

    public Button getButton(int slot) {
        return buttonMap.get(slot);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = ChatColor.translateAlternateColorCodes('&', title);
        inventory = Bukkit.createInventory(this, size, this.title);
    }

    public int getSize() {
        return size;
    }

    public int getRow() {
        return size / 9;
    }

    public void setRow(int row) {
        size = row * 9;
        inventory = Bukkit.createInventory(this, size, title);
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

}

