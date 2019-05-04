package com.ericlam.mc.ranking.main;

import com.ericlam.mc.rankcal.RankDataManager;
import com.ericlam.mc.rankcal.RefresherScheduler;
import com.ericlam.mc.rankcal.types.CalType;
import com.ericlam.mc.rankcal.types.Storage;
import com.ericlam.mc.ranking.DefaultDataHandler;
import com.ericlam.mc.ranking.api.PlaceHolderHook;
import com.ericlam.mc.ranking.bukkit.RankingListeners;
import com.ericlam.mc.ranking.bukkit.commands.RankCommandExecutor;
import com.ericlam.mc.ranking.config.ConfigManager;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class PvPRanking extends JavaPlugin {
    private static PvPRanking plugin;
    private static ConfigManager configManager;
    private static CalType calType;
    private static Storage storage;
    private static String[] ranks;

    public static Storage getStorage() {
        return storage;
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public static String[] getRanks() {
        return ranks;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    public static CalType getCalType() {
        return calType;
    }

    @Override
    public void onEnable() {
        plugin = this;
        configManager = new ConfigManager(this);
        try{
            calType = CalType.valueOf(configManager.getConfig().getString("ran-cal-method").toUpperCase());
        }catch (IllegalStateException e){
            getLogger().warning("你所填寫的 演算 方法 并不存在！已自動改成 z_score 演算法。");
            calType = CalType.Z_SCORE;
        }

        try{
            storage = Storage.valueOf(configManager.getDatabase().getString("storage").toUpperCase());
        }catch (IllegalStateException e){
            getLogger().warning("你所填寫的 存儲 方法 并不存在！已自動改成 yaml 儲存。");
            storage = Storage.YAML;
        }

        ranks = configManager.getRank().getStringList("ranks").toArray(String[]::new);

        if (RankDataManager.getInstance().getDataHandler() == null) new DefaultDataHandler().register();
        getServer().getPluginManager().registerEvents(new RankingListeners(this),this);
        getCommand("pvprank").setExecutor(new RankCommandExecutor(this));

        if ((boolean) getConfigData("show-info-only")) new RefresherScheduler(this);

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("找到 PlaceHolderAPI 插件！ 正在掛接...");
            new PlaceHolderHook(this).register();
        }
    }

    @Override
    public void onDisable() {
        if ((boolean) getConfigData("handle-data-save"))
            RankDataManager.getInstance().getDataHandler().saveAllPlayerData();
    }

    public static Object getConfigData(String path){
        return configManager.getConfig().get(path);
    }
}
