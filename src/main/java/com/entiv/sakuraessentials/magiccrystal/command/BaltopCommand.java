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

public class BaltopCommand extends SimpleCommand {

    protected BaltopCommand(JavaPlugin plugin, SimpleCommand parent, String label) {
        super(plugin, parent, label);
        setUsage("/crystal baltop");
        setDescription("查看魔晶矿排行榜");
    }

    @Override
    public void onCommand() {

    }

}
