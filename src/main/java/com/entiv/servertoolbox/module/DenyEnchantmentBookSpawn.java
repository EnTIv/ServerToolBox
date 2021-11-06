package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Module;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.PrepareItemEnchantEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PiglinBarterEvent;
import org.bukkit.event.entity.VillagerAcquireTradeEvent;
import org.bukkit.event.entity.VillagerReplenishTradeEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.MerchantRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class DenyEnchantmentBookSpawn extends Module implements Listener {

    @Override
    protected void onEnable() {
        registerListener();
    }

    // 村民交易
    @EventHandler
    private void onVillagerAcquireTrade(VillagerAcquireTradeEvent event) {
        MerchantRecipe recipe = event.getRecipe();
        MerchantRecipe next = new MerchantRecipe(removeAllEnchantment(recipe.getResult()), recipe.getUses(), recipe.getMaxUses(), recipe.hasExperienceReward(), recipe.getVillagerExperience(), recipe.getPriceMultiplier());
        event.setRecipe(next);
    }

    // 村民刷新交易
    @EventHandler
    private void onVillagerReplenishTrade(VillagerReplenishTradeEvent event) {
        MerchantRecipe recipe = event.getRecipe();
        MerchantRecipe next = new MerchantRecipe(removeAllEnchantment(recipe.getResult()), recipe.getUses(), recipe.getMaxUses(), recipe.hasExperienceReward(), recipe.getVillagerExperience(), recipe.getPriceMultiplier());
        event.setRecipe(next);
    }

    // 猪灵交易
    @EventHandler
    private void onPiglinBarter(PiglinBarterEvent event) {
        List<ItemStack> outcome = event.getOutcome();
        for (ItemStack itemStack : outcome) {
            removeAllEnchantment(itemStack);
        }
    }

    // 地牢箱子
    @EventHandler
    private void onPiglinBarter(LootGenerateEvent event) {
        List<ItemStack> loot = event.getLoot();
        for (ItemStack itemStack : loot) {
            removeAllEnchantment(itemStack);
        }
    }

    // 钓鱼
    @EventHandler
    private void onPlayerFish(PlayerFishEvent event) {
        Item item = event.getCaught() instanceof Item ? ((Item) event.getCaught()) : null;

        if (item != null) {
            removeAllEnchantment(item.getItemStack());
        }
    }

    // 附魔台
    @EventHandler
    private void onPrepareItemEnchantEvent(PrepareItemEnchantEvent event) {
        if (event.getItem().getType().equals(Material.BOOK)) {
            event.setCancelled(true);
        }
    }

    // 自然生成可能有装备的怪物
    @EventHandler
    private void onEntitySpawn(EntityDeathEvent event) {
        Monster monster = event.getEntity() instanceof Monster ? ((Monster) event.getEntity()) : null;
        if (monster == null) return;

        for (ItemStack drop : event.getDrops()) {
            removeAllEnchantment(drop);
        }
    }

    private ItemStack removeAllEnchantment(ItemStack itemStack) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getEnchants().keySet().forEach(itemMeta::removeEnchant);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
