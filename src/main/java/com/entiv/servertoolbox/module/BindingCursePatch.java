package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Module;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.ItemStack;

public class BindingCursePatch extends Module implements Listener {

    @Override
    protected void enable() {
        registerListener();
    }

    @Override
    protected void unload() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onEnchant(PrepareAnvilEvent event) {

        ItemStack result = event.getResult();
        Player player = event.getView().getPlayer() instanceof Player ? ((Player) event.getView().getPlayer()) : null;

        if (result == null || player == null) return;

        if (result.getType().getMaxDurability() == 0 && result.getEnchantments().containsKey(Enchantment.BINDING_CURSE)) {
            event.setResult(new ItemStack(Material.AIR));
            player.updateInventory();
        }
    }
}
