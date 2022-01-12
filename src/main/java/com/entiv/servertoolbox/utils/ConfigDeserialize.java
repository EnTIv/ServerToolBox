package com.entiv.servertoolbox.utils;

import com.entiv.servertoolbox.Main;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ConfigDeserialize {

    private ConfigDeserialize() {

    }

    public static ItemStack getItemStack(String path) {
        ConfigurationSection config = Main.getInstance().getConfig().getConfigurationSection(path);
        if (config == null) throw new NullPointerException("配置文件错误, 请检查 " + path + " 路径是否存在");

        String type = config.getString("type","STONE");
        Material material;

        short data = 0;

        if (type.contains(":")) {
            String[] split = type.split(":");
            data = Short.parseShort(split[1]);
            material = Material.matchMaterial(split[0]);
        } else {
            material = Material.matchMaterial(type);
        }

        String name = config.getString("name");
        int amount = config.getInt("amount", 1);

        List<String> lore = config.getStringList("lore");
        List<String> flag = config.getStringList("flag");
        List<String> enchantment = config.getStringList("enchantment");

        ItemBuilder itemBuilder = new ItemBuilder(material, amount).name(name).lore(lore);

        for (String string : flag) {
            ItemFlag itemFlag = ItemFlag.valueOf(string);
            itemBuilder.addFlag(itemFlag);
        }

        for (String string : enchantment) {

            String[] split = string.split(":");
            Enchantment enchant = Enchantment.getByName(split[0]);

            int level = Integer.parseInt(split[1]);
            itemBuilder.addEnchantment(enchant, level);
        }

        boolean isUnbreakable = config.getBoolean("unbreakable");
        if (isUnbreakable) itemBuilder.setUnbreakable();

        return new ItemBuilder(material, amount, data).name(name).lore(lore).build();
    }
}
