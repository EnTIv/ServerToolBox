package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Module;
import com.entiv.servertoolbox.utils.LoreChecker;
import com.entiv.servertoolbox.utils.Message;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ExpBank extends Module implements Listener {

    @Override
    protected void onEnable() {
        registerListener();
    }

    @EventHandler
    private void onPlayerUseItem(PlayerInteractEvent event) {
        ItemStack itemStack = event.getItem();
        Action action = event.getAction();

        if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
            return;
        }

        boolean isLeftClick = action.equals(Action.LEFT_CLICK_AIR) || action.equals(Action.LEFT_CLICK_BLOCK);
        boolean isRightClick = action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK);

        if (!isLeftClick && !isRightClick) return;

        if (itemStack.getItemMeta() == null || itemStack.getItemMeta().getLore() == null) return;

        LoreChecker loreChecker = new LoreChecker(itemStack);
        Integer i = loreChecker.getIndex(config.getString("检测关键字"));

        if (i == null) return;

        int[] storeAndMax = loreChecker.getStoreAndMax(i);
        int perStoreMax = config.getInt("每次存取经验值上限");

        Player player = event.getPlayer();
        int playerExp = SetExpFix.getTotalExperience(player);
        List<String> itemLore = itemStack.getItemMeta().getLore();

        if (isLeftClick) {

            if (storeAndMax[0] == 0) {
                Message.send(player, config.getString("无法取出提示"));
                return;
            }

            int amount = Math.min(storeAndMax[0], perStoreMax);
            SetExpFix.setTotalExperience(player, playerExp + amount);

            int has = storeAndMax[0] - amount;
            loreChecker.replaceLore(i, itemLore.get(i).replaceFirst(String.valueOf(storeAndMax[0]), String.valueOf(has)));

            Message.send(player, config.getString("取出提示", "").replace("%exp%", String.valueOf(amount)));
        } else {

            if (storeAndMax[0] == storeAndMax[1]) {
                Message.send(player, config.getString("上限提示"));
                return;
            }

            int amount = Math.min(playerExp, perStoreMax);

            if (amount + storeAndMax[0] > storeAndMax[1]) {
                amount = storeAndMax[1] - storeAndMax[0];
            }

            SetExpFix.setTotalExperience(player, playerExp - amount);
            int has = storeAndMax[0] + amount;
            loreChecker.replaceLore(i, itemLore.get(i).replaceFirst(String.valueOf(storeAndMax[0]), String.valueOf(has)));

            Message.send(player, config.getString("存入提示", "").replace("%exp%", String.valueOf(amount)));
        }
    }

    private static class SetExpFix {
        public static void setTotalExperience(final Player player, final int exp) {
            if (exp < 0) {
                throw new IllegalArgumentException("Experience is negative!");
            }
            player.setExp(0);
            player.setLevel(0);
            player.setTotalExperience(0);

            //This following code is technically redundant now, as bukkit now calulcates levels more or less correctly
            //At larger numbers however... player.getExp(3000), only seems to give 2999, putting the below calculations off.
            int amount = exp;
            while (amount > 0) {
                final int expToLevel = getExpAtLevel(player);
                amount -= expToLevel;
                if (amount >= 0) {
                    // give until next level
                    player.giveExp(expToLevel);
                } else {
                    // give the rest
                    amount += expToLevel;
                    player.giveExp(amount);
                    amount = 0;
                }
            }
        }

        private static int getExpAtLevel(final Player player) {
            return getExpAtLevel(player.getLevel());
        }

        //new Exp Math from 1.8
        public static int getExpAtLevel(final int level) {
            if (level <= 15) {
                return (2 * level) + 7;
            }
            if (level <= 30) {
                return (5 * level) - 38;
            }
            return (9 * level) - 158;

        }

        public static int getExpToLevel(final int level) {
            int currentLevel = 0;
            int exp = 0;

            while (currentLevel < level) {
                exp += getExpAtLevel(currentLevel);
                currentLevel++;
            }
            if (exp < 0) {
                exp = Integer.MAX_VALUE;
            }
            return exp;
        }

        //This method is required because the bukkit player.getTotalExperience() method, shows exp that has been 'spent'.
        //Without this people would be able to use exp and then still sell it.
        public static int getTotalExperience(final Player player) {
            int exp = (int) Math.round(getExpAtLevel(player) * player.getExp());
            int currentLevel = player.getLevel();

            while (currentLevel > 0) {
                currentLevel--;
                exp += getExpAtLevel(currentLevel);
            }
            if (exp < 0) {
                exp = Integer.MAX_VALUE;
            }
            return exp;
        }

        public static int getExpUntilNextLevel(final Player player) {
            int exp = (int) Math.round(getExpAtLevel(player) * player.getExp());
            int nextLevel = player.getLevel();
            return getExpAtLevel(nextLevel) - exp;
        }
    }
}
