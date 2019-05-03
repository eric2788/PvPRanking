package com.ericlam.mc.ranking.storage;

import com.ericlam.mc.ranking.RankData;
import com.ericlam.mc.ranking.main.PvPRanking;
import org.bukkit.plugin.Plugin;

import java.util.TreeSet;
import java.util.UUID;

public interface DataStorage {

    Plugin plugin = PvPRanking.getPlugin();

    void saveRankingData(TreeSet<RankData> data);

    RankData getRankData(UUID playerUniqueId);

    boolean removeRankData(UUID playerUniqueId);

    TreeSet<RankData> loadRankData();

}
