package com.entiv.servertoolbox;

import com.entiv.servertoolbox.module.*;
import com.entiv.servertoolbox.utils.Message;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private final List<Module> enableModules;
    private final JavaPlugin plugin;

    public ModuleManager(JavaPlugin plugin) {
        this.plugin = plugin;
        enableModules = new ArrayList<>();
    }

    public void loadModules() {
        loadModule(DropProtect.class, "物品丢弃保护");
        loadModule(PlayerCommandSend.class, "设置命令补全");
        loadModule(DenyAdvancementMessage.class, "隐藏成就消息");
        loadModule(WorldEnterPermission.class, "世界进入权限");
        loadModule(EntityTargetController.class, "实体目标控制器");
        loadModule(ExpBank.class, "经验银行");
        loadModule(PortalCommand.class, "地狱门随机传送");
        loadModule(WorldRespawnPoint.class, "设置世界重生点");
        loadModule(DenyLightningDestroyItem.class, "防止雷电破坏掉落物");
    }

    private void loadModule(Class<? extends Module> moduleClass, String moduleName) {
        try {

            Module module = moduleClass.newInstance();
            if (Main.getInstance().getConfig().getBoolean(moduleName + ".启用", false) && module.canEnable()) {
                module.load(plugin, moduleName);
                Message.sendConsole("&9" + Main.getInstance().getName() + "&6 >> &a模块&e " + moduleName + " &a已启用");

                enableModules.add(module);
            }

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void unloadAllModule() {
        enableModules.forEach(Module::unload);
        enableModules.clear();
    }
}
