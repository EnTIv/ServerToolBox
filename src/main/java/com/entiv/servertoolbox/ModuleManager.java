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
        loadModule(DenyEnchantmentBookSpawn.class, "移除原版附魔物品生成");
        loadModule(EasyLogin.class, "简易登录");
        loadModule(FanCount.class, "粉丝计数器");
        loadModule(MiningCount.class, "挖掘计数器");
        loadModule(LeverSpeedLimit.class, "反快速拉杆");
        loadModule(AntiExplode.class, "爆炸保护");
        loadModule(FarmProtection.class, "农田防踩踏");
        loadModule(AutoTool.class, "自动化工具");
        loadModule(FarmProtection.class, "农田防踩踏");
        loadModule(WorldItemBlacklist.class, "世界物品黑名单");
        loadModule(FarmProtection.class, "反快速指令");
        loadModule(WorldItemBlacklist.class, "反聊天刷屏");
        loadModule(WorldCommandBlacklist.class, "世界指令黑名单");
        loadModule(KeywordFiltering.class, "违禁词屏蔽");
        loadModule(ItemNameTag.class, "物品改名卡");
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
