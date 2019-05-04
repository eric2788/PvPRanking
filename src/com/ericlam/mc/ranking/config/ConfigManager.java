package com.ericlam.mc.ranking.config;

import com.ericlam.mc.ranking.main.PvPRanking;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

public class ConfigManager {
    private FileConfiguration config;
    private FileConfiguration database;
    private FileConfiguration rank;

    public ConfigManager(PvPRanking plugin) {
        File cfFile = new File(plugin.getDataFolder(),"config.yml");
        File dbFile = new File(plugin.getDataFolder(),"database.yml");
        File rankFile = new File(plugin.getDataFolder(),"rank.yml");
        for (File file : List.of(cfFile, dbFile, rankFile)) {
            if (!file.exists()) plugin.saveResource(file.getName(), true);
        }
        config = YamlConfiguration.loadConfiguration(cfFile);
        database = YamlConfiguration.loadConfiguration(dbFile);
        rank = YamlConfiguration.loadConfiguration(rankFile);
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
}
