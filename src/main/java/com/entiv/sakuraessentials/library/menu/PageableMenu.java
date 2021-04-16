package com.entiv.sakuraessentials.library.menu;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class PageableMenu extends Menu implements Iterable<Inventory> {

    public final List<Inventory> pages = new ArrayList<>();
    private int currentPage = 1;
    private int maxPage = 1;

    // 初始化所有界面
    public void initPages() {
        for (int i = 0; i < maxPage; i++) {
            int page = i + 1;
            Inventory inventory = Bukkit.createInventory(this, getSize(), getTitle().concat(" " + page + "/" + maxPage));
            pages.add(inventory);
        }

    }

    public void previousPage(Player player) {
        if (currentPage == 1) return;

        int index = currentPage - 1;
        inventory = pages.get(index - 1);

        currentPage--;
        player.openInventory(inventory);
    }

    public void nextPage(Player player) {
        if (currentPage == maxPage) return;

        int index = currentPage - 1;
        inventory = pages.get(index + 1);

        currentPage++;
        player.openInventory(inventory);
    }

    public int getMaxPage() {
        return maxPage;
    }

    // 设置可用行数
    public void setTotalRow(int row) {
        int pageSize = getSize();
        int totalSize = row * 9;
        maxPage = (totalSize == pageSize - 9 ? 0 : totalSize / pageSize) + 1;
    }

    // 通过按钮数量计算最大页数
    public void setTotalRow(List<Button> buttons) {
        setTotalRow(buttons.size());
    }

    @Override
    public void open(Player player) {
        currentPage = 1;

        inventory = pages.get(0);
        player.openInventory(inventory);
    }

    // 一次性将按钮设置到全部界面
    @Override
    public void setButton(Button button) {

        for (int i = 0; i < pages.size(); i++) {

            this.inventory = pages.get(i);

            for (int slot : button.slots) {
                buttonMap.put(slot, button);
                inventory.setItem(slot, button.itemStack);
            }

            if (button.action != null) {
                int page = i + 1;
                setDefaultButtonAction(button, page);
                setButtonAction(button);
            }
        }
    }

    @Override
    public Iterator<Inventory> iterator() {
        return pages.iterator();
    }

    // 设置按钮默认行为
    public void setDefaultButtonAction(Button button, int page) {

        if (button.action == null) return;

        switch (button.action) {

            case "NextPage":

                button.clickEvent = event -> {
                    HumanEntity whoClicked = event.getWhoClicked();
                    if (whoClicked instanceof Player) {
                        nextPage((Player) whoClicked);
                    }
                };

                if (page == maxPage) {
                    ItemStack itemStack = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
                    Button next = new Button(itemStack, button.slots);
                    next.clickEvent = button.clickEvent;
                    super.setButton(next);
                }

                break;

            case "PreviousPage":

                button.clickEvent = event -> {
                    HumanEntity whoClicked = event.getWhoClicked();
                    if (whoClicked instanceof Player) {
                        previousPage((Player) whoClicked);
                    }
                };

                if (page == 1) {
                    ItemStack itemStack = new ItemStack(Material.BLUE_STAINED_GLASS_PANE);
                    Button previous = new Button(itemStack, button.slots);
                    previous.clickEvent = button.clickEvent;
                    super.setButton(previous);
                }

                break;

            case "Close":

                button.clickEvent = event -> event.getWhoClicked().closeInventory();
                break;
        }
    }
}
