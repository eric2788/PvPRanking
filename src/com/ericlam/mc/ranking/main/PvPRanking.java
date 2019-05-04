package com.ericlam.mc.ranking.main;

import com.ericlam.mc.rankcal.RankDataManager;
import com.ericlam.mc.rankcal.RefresherScheduler;
import com.ericlam.mc.rankcal.types.CalType;
import com.ericlam.mc.rankcal.types.Storage;
import com.ericlam.mc.ranking.PlaceHolderHook;
import com.ericlam.mc.ranking.bukkit.RankingListeners;
import com.ericlam.mc.ranking.bukkit.commands.RankCommandExecutor;
import com.ericlam.mc.ranking.bukkit.commands.datahandle.PvPDataCommandExecutor;
import com.ericlam.mc.ranking.config.ConfigManager;
import com.ericlam.mc.ranking.defaultdatahandle.DefaultDataHandler;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * PvPRanking 插件
 * https://github.com/eric2788/PvPRanking
 *
 * @author Eric Lam
 * @version 1.0.0
 */
public class PvPRanking extends JavaPlugin {
    private static PvPRanking plugin;
    private static ConfigManager configManager;
    private static CalType calType;
    private static Storage storage;
    private static String[] ranks;

    /**
     * @return 本插件的存儲類型 (yaml / mysql)
     */
    public static Storage getStorage() {
        return storage;
    }

    /**
     *
     * @return 本插件實例
     */
    public static Plugin getPlugin() {
        return plugin;
    }

    /**
     *
     * @return 所有段位
     */
    public static String[] getRanks() {
        return ranks;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    /**
     *
     * @return 演算方式
     */
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
        getCommand("pvpdata").setExecutor(new PvPDataCommandExecutor(this));

        if (!(boolean) getConfigData("show-info-only")) new RefresherScheduler(this);

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("找到 PlaceHolderAPI 插件！ 正在掛接...");
            new PlaceHolderHook(this).register();
        }
        ConsoleCommandSender sender = getServer().getConsoleSender();
        sender.sendMessage(ChatColor.AQUA + "=================================");
        sender.sendMessage(ChatColor.GREEN + "採用演算法： " + ChatColor.YELLOW + calType.toString());
        sender.sendMessage(ChatColor.GREEN + "儲存方式: " + ChatColor.YELLOW + storage.toString());
        sender.sendMessage(ChatColor.GREEN + "排位區段數: " + ChatColor.YELLOW + ranks.length);
        sender.sendMessage(ChatColor.AQUA + "=================================");
        getLogger().info("PvPRanking 插件已成功啟用。");
    }

    @Override
    public void onDisable() {
        if ((boolean) getConfigData("handle-data-save"))
            RankDataManager.getInstance().getDataHandler().saveAllPlayerData();
    }

    /**
     *
     * 請自行 cast 成 其他數值
     *
     * @param path config.yml 路徑
     * @return value 物件
     *
     */
    public static Object getConfigData(String path){
        return configManager.getConfig().get(path);
    }
}
