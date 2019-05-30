package com.ericlam.mc.ranking.config;

import com.ericlam.mc.ranking.main.PvPRanking;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
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
        config.addDefault("default-calculate-format", "<kills> * 50 - <death> * 20");
        ;
        try {
            config.save(cfFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
