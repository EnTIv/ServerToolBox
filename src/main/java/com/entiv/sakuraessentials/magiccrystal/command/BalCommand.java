package com.entiv.sakuraessentials.magiccrystal.command;

import com.entiv.sakuraessentials.library.command.SimpleCommand;
import com.entiv.sakuraessentials.library.message.Message;
import com.entiv.sakuraessentials.magiccrystal.MagicCrystal;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class BalCommand extends SimpleCommand {

    protected BalCommand(JavaPlugin plugin, SimpleCommand parent, String label) {
        super(plugin, parent, label);
        setUsage("/crystal bal 玩家");
        setDescription("查看某玩家的魔晶矿数量");
    }

    @Override
    public void onCommand() {

    }

}
