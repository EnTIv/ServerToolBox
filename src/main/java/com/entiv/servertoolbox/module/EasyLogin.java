package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Main;
import com.entiv.servertoolbox.Module;
import fr.xephi.authme.api.v3.AuthMeApi;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public class EasyLogin extends Module implements Listener {

    @Override
    protected void onEnable() {
        registerListener();
    }

    @Override
    public boolean canEnable() {
        return Bukkit.getPluginManager().getPlugin("Authme") != null;
    }

    @EventHandler
    private void onPlayerChat(AsyncChatEvent event) {
        Player player = event.getPlayer();
        AuthMeApi authMeApi = AuthMeApi.getInstance();

        if (authMeApi.isAuthenticated(player)) return;

        event.setCancelled(true);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (authMeApi.isRegistered(player.getName())) {
                    player.performCommand("l " + event.message());
                } else {
                    player.performCommand("reg " + event.message());
                }
            }
        }.runTask(Main.getInstance());
    }
}
