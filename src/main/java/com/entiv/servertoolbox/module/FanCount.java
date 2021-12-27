package com.entiv.servertoolbox.module;

import com.entiv.servertoolbox.Main;
import com.entiv.servertoolbox.Module;
import com.entiv.servertoolbox.utils.Message;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FanCount extends Module {

    private static final Map<String, Integer> cache = new HashMap<>();

    @Override
    protected void onEnable() {
        new FansCountExpansion().register();
    }

    @Override
    protected void unload() {
        super.unload();
        cache.clear();
    }

    @Override
    public boolean canEnable() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            Message.sendConsole("模块 『粉丝计数器』 加载失败，未安装 PlaceholderAPI 依赖");
            return false;
        }
        return true;
    }

    private static class FansCountExpansion extends PlaceholderExpansion {

        @Override

        public @NotNull String getIdentifier() {
            return "FanCount";
        }

        @Override
        public @NotNull String getAuthor() {
            return "EnTIv";
        }

        @Override
        public @NotNull String getVersion() {
            return "1.0.0";
        }

        @Override
        public String onRequest(OfflinePlayer player, @NotNull String params) {
            if (params.startsWith("bilibili_")) {
                final String uid = params.split("_")[1];
                Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
                    try {
                        URL url = new URL("https://api.bilibili.com/x/relation/stat?vmid=" + uid);
                        InputStream is = url.openStream();
                        InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
                        BufferedReader br = new BufferedReader(isr);

                        final Pattern pattern = Pattern.compile(".*\"follower\":(\\d+)}}");
                        final Matcher matcher = pattern.matcher(br.readLine());

                        br.close();
                        isr.close();
                        is.close();

                        if (matcher.find()) {
                            cache.put(uid, Integer.valueOf(matcher.group(1)));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                return cache.getOrDefault(uid, -1).toString();
            }
            return "参数错误";
        }
    }
}

