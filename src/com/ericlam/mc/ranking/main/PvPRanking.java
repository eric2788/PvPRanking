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
 * PvPRanking
 *  (https://github.com/eric2788/PvPRanking)
 *
 * @author Eric Lam
 * @version 1.1.0
 */
public class PvPRanking extends JavaPlugin {
    private static PvPRanking plugin;
    private static ConfigManager configManager;
    private static CalType calType;
    private static Storage storage;
    private static String[] ranks;

    /**
     * @return Storage type
     */
    public static Storage getStorage() {
        return storage;
    }

    /**
     *
     * @return plugin instance
     */
    public static Plugin getPlugin() {
        return plugin;
    }

    /**
     *
     * @return all ranks
     */
    public static String[] getRanks() {
        return ranks;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

    /**
     *
     * @return calculation type
     */
    public static CalType getCalType() {
        return calType;
    }

    /**
     * @param path config.yml 's path
     * @return Object data
     */
    public static Object getConfigData(String path) {
        return configManager.getConfig().get(path);
    }

    @Override
    public void onDisable() {
        if ((boolean) getConfigData("handle-data-save"))
            RankDataManager.getInstance().getDataHandler().saveAllPlayerData();
    }

    @Override
    public void onEnable() {
        plugin = this;
        configManager = new ConfigManager(this);
        try{
            calType = CalType.valueOf(configManager.getConfig().getString("ran-cal-method").toUpperCase());
        }catch (IllegalStateException e){
            getLogger().warning("the calculation method not exist! changed it to z-score");
            calType = CalType.Z_SCORE;
        }

        try{
            storage = Storage.valueOf(configManager.getDatabase().getString("storage").toUpperCase());
        }catch (IllegalStateException e){
            getLogger().warning("saving storage not exist! changed it to yaml");
            storage = Storage.YAML;
        }

        ranks = configManager.getRank().getStringList("ranks").toArray(new String[0]);

        if (RankDataManager.getInstance().getDataHandler() == null) new DefaultDataHandler().register();
        getServer().getPluginManager().registerEvents(new RankingListeners(this),this);
        getCommand("pvprank").setExecutor(new RankCommandExecutor(this));
        getCommand("pvpdata").setExecutor(new PvPDataCommandExecutor(this));

        if (!(boolean) getConfigData("show-info-only")) new RefresherScheduler(this);

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("Hooking into PlaceHolderAPI ...");
            new PlaceHolderHook(this).register();
        }
        ConsoleCommandSender sender = getServer().getConsoleSender();
        sender.sendMessage(ChatColor.AQUA + "=================================");
        sender.sendMessage(ChatColor.GREEN + "Using calculation： " + ChatColor.YELLOW + calType.toString());
        sender.sendMessage(ChatColor.GREEN + "Saving Type: " + ChatColor.YELLOW + storage.toString());
        sender.sendMessage(ChatColor.GREEN + "Rank sizes: " + ChatColor.YELLOW + ranks.length);
        sender.sendMessage(ChatColor.AQUA + "=================================");
        getLogger().info("PvPRanking Enabled。");
    }
}
