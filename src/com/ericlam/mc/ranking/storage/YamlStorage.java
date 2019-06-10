package com.ericlam.mc.ranking.storage;

import com.ericlam.mc.rankcal.RankDataManager;
import com.ericlam.mc.ranking.RankData;
import com.ericlam.mc.ranking.api.PlayerData;
import com.ericlam.mc.ranking.main.PvPRanking;
import org.apache.commons.io.FilenameUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.TreeSet;
import java.util.UUID;

public class YamlStorage implements DataStorage {

    private File folder;
    private String unranked;

    public YamlStorage() {
        this.folder = new File(plugin.getDataFolder(), "Ranking_Data"); //定位 Ranking_Data 資料夾
        if (!folder.exists()) folder.mkdir(); //若不存在, 則創建一個 (mkdir 為創建資料夾, createFile 為 創建文件)
        this.unranked = ChatColor.translateAlternateColorCodes('&', (String) PvPRanking.getConfigData("unranked-tag"));
    }

    @Override
    public void saveRankingData(TreeSet<RankData> data){
        for (RankData d : data) {
            File yml = new File(folder,d.getPlayerUniqueId().toString()+".yml");
            FileConfiguration user = new YamlConfiguration();
            user.set("score", d.getFinalScores()); //為文件設置路徑及數值
            user.set("n-score",d.getnScores());
            user.set("rank",d.getRank());
            try {
                user.save(yml); //保存文件，若不存在則創建一個
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public RankData getRankData(UUID playerUniqueId) {
        PlayerData data = RankDataManager.getInstance().getDataHandler().getPlayerData(playerUniqueId);
        File file = new File(folder, playerUniqueId.toString() + ".yml");//定位文件位置
        if (!file.exists()) return new RankData(data, unranked, 0.0);
        FileConfiguration user = YamlConfiguration.loadConfiguration(file);//把定位文件加載為 yaml config
        double nScore = user.getDouble("n-score"); //透過路徑獲取數據
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
            if (!FilenameUtils.getExtension(file.getPath()).equals("yml")) continue; //獲取文件格式
            UUID uuid = UUID.fromString(FilenameUtils.getBaseName(file.getPath()));
            RankData rank = getRankData(uuid);
            if (rank == null) continue;
            rankData.add(rank);
        }
        return rankData;
    }
}
