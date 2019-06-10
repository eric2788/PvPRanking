package com.ericlam.mc.ranking.config;

import com.ericlam.mc.ranking.main.PvPRanking;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class ConfigManager {
    private FileConfiguration config;
    private FileConfiguration database;
    private FileConfiguration rank;

    public ConfigManager(PvPRanking plugin) {
        File cfFile = new File(plugin.getDataFolder(),"config.yml");
        File dbFile = new File(plugin.getDataFolder(),"database.yml");
        File rankFile = new File(plugin.getDataFolder(),"rank.yml");
        for (File file : Arrays.asList(cfFile, dbFile, rankFile)) {
            if (!file.exists()) plugin.saveResource(file.getName(), true);
        }
        config = YamlConfiguration.loadConfiguration(cfFile);
        database = YamlConfiguration.loadConfiguration(dbFile);
        rank = YamlConfiguration.loadConfiguration(rankFile);
        addDefault(config, "default-calculate-format", "<kills> * 50 - <deaths> * 20");
        addDefault(config, "unranked-tag", "UnRanked");
        addDefault(config, "rankup-subtitle", "&k&a||&e RANK UP &k&a||");
        addDefault(config, "rank-updated-msg", "&bRank data has been updated。");
        try {
            config.save(cfFile);
            config = YamlConfiguration.loadConfiguration(cfFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addDefault(FileConfiguration config, String path, Object value) {
        if (config.contains(path)) return;
        config.set(path, value);
    }

    public FileConfiguration getDatabase() {
        return database;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getRank() {
        return rank;
    }

    public String getCalFormat() {
        return config.getString("default-calculate-format");
    }
}
