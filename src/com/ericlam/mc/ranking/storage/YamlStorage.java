package com.ericlam.mc.ranking.storage;

import com.ericlam.mc.rankcal.RankDataManager;
import com.ericlam.mc.ranking.RankData;
import com.ericlam.mc.ranking.api.PlayerData;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.TreeSet;
import java.util.UUID;

public class YamlStorage implements DataStorage {

    private File folder;

    public YamlStorage() {
        this.folder = new File(plugin.getDataFolder(),"Ranking_Data");
        if (!folder.exists()) folder.mkdir();
    }

    @Override
    public void saveRankingData(TreeSet<RankData> data){
        for (RankData d : data) {
            File yml = new File(folder,d.getPlayerUniqueId().toString()+".yml");
            FileConfiguration user = new YamlConfiguration();
            user.set("score",d.getFinalScores());
            user.set("n-score",d.getnScores());
            user.set("rank",d.getRank());
            try {
                user.save(yml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public RankData getRankData(UUID playerUniqueId) {
        PlayerData data = RankDataManager.getInstance().getDataHandler().getPlayerData(playerUniqueId);
        File file = new File(folder,playerUniqueId.toString()+".yml");
        if (!file.exists()) return new RankData(data, "未定位", 0.0);
        FileConfiguration user = YamlConfiguration.loadConfiguration(file);
        double nScore = user.getDouble("n-score");
        String rank = user.getString("rank");
        return new RankData(data,rank,nScore);
    }

    @Override
    public boolean removeRankData(UUID playerUniqueId) {
        File file = new File(folder, playerUniqueId.toString() + ".yml");
        if (!file.exists()) return false;
        return file.delete();
    }

    @Override
    public TreeSet<RankData> loadRankData() {
        TreeSet<RankData> rankData = new TreeSet<>();
        File[] files = folder.listFiles();
        if (files == null) return rankData;
        for (File file : files) {
            if (!FilenameUtils.getExtension(file.getPath()).equals("yml")) continue;
            UUID uuid = UUID.fromString(FilenameUtils.getBaseName(file.getPath()));
            RankData rank = getRankData(uuid);
            if (rank == null) continue;
            rankData.add(rank);
        }
        return rankData;
    }
}
