package com.entiv.sakuraessentials.island;


import com.entiv.sakuraessentials.library.command.CommandException;
import com.entiv.sakuraessentials.library.command.SimpleCommand;
import com.entiv.sakuraessentials.library.message.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import world.bentobox.bentobox.BentoBox;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.database.objects.Island;
import world.bentobox.bentobox.managers.IslandsManager;

import java.util.Optional;
import java.util.UUID;

import static com.entiv.sakuraessentials.library.command.CommandException.*;

public class InfoCommand extends SimpleCommand {

    protected InfoCommand(JavaPlugin plugin, SimpleCommand parent, String label) {
        super(plugin, parent, label);
        setUsage("/is info");
        setDescription("查看岛屿信息");
    }

    @Override
    public void onCommand() {

        BentoBox bentoBox = BentoBox.getInstance();
        IslandsManager islandsManager = bentoBox.getIslandsManager();

        User user = bentoBox.getPlayersManager().getUser(sender.getName());
        Location location = user.getLocation();

        if (location == null) return;
        Player player = user.getPlayer();
        Optional<Island> islandAt = islandsManager.getIslandAt(location);

        if (!islandAt.isPresent()) {
            Message.sendWarn(player, "您当前不在岛屿上");
            return;
        }

        TextComponent.Builder textComponent = Component.text().content("━━━━━━━━━━━━━━  ")
                .color(TextColor.color(0xf2cc6d))
                .append(Component.text("岛屿信息", TextColor.color(0xbde0fe)))
                .append(Component.text("  ━━━━━━━━━━━━━━")).append(Component.newline());

        Island island = islandAt.get();

        OfflinePlayer owner = Optional.ofNullable(island.getOwner())
                .map(Bukkit::getOfflinePlayer)
                .orElseThrow(() -> new CommandException("指令执行失败, 告知服主有奖励哟", MessageType.ERROR));

        textComponent
                .append(Component.newline())
                .append(Component.text("§b ━ §7岛屿主人: " + owner.getName())).append(Component.newline())
                .append(Component.text("§b ━ §7岛屿成员: ")).append(getMemberComponent(island,owner));

        Location center = island.getCenter();
        if (center == null) return;

        textComponent
                .append(Component.newline())

                .append(Component.text("§b ━ §7岛屿大小: " + island.getMaxEverProtectionRange() * 2)).append(Component.newline())
                .append(Component.text("§b ━ §7岛屿中心坐标: " + center.getBlockX() + "," + center.getBlockZ())).append(Component.newline())
                .append(Component.text("§b ━ §7岛屿坐标范围: " +
                        island.getMinProtectedX() + "," + island.getMinProtectedZ() + " 至 " +
                        island.getMaxProtectedX() + "," + island.getMaxProtectedZ()))

                .append(Component.newline())
        ;

        player.sendMessage(textComponent);
    }

    private TextComponent getMemberComponent(Island island,OfflinePlayer owner) {

        TextComponent.Builder textComponent = Component.text().content("");

        int i = 0;

        if (island.getMembers().size() == 1) {

            textComponent.append(Component.text("无", NamedTextColor.GRAY));

        } else {

            for (UUID memberUUID : island.getMembers().keySet()) {

                String member = Bukkit.getOfflinePlayer(memberUUID).getName();
                String name = owner.getName();

                if (name == null || name.equals(member)) continue;

                if (i != 0) {
                    textComponent.append(Component.text(", ", NamedTextColor.GRAY));
                }

                if (member != null) {
                    textComponent.append(Component.text(member, NamedTextColor.GRAY));
                }

                i++;
            }
        }

        return textComponent.build();
    }
}
