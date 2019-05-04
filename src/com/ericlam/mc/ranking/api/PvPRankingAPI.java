package com.ericlam.mc.ranking.api;

import com.ericlam.mc.rankcal.RankDataManager;
import com.ericlam.mc.ranking.RankData;

import java.util.UUID;

public class PvPRankingAPI {

    public static String getRank(UUID uuid) {
        return RankDataManager.getInstance().getRankData(uuid).getRank();
    }

    public static double getScores(UUID uuid) {
        return RankDataManager.getInstance().getRankData(uuid).getFinalScores();
    }

    public static double getNScores(UUID uuid) {
        return RankDataManager.getInstance().getRankData(uuid).getnScores();
    }

    public static int getPlays(UUID uuid) {
        return RankDataManager.getInstance().getRankData(uuid).getPlays();
    }

    public static DataHandler getDataHandler() {
        return RankDataManager.getInstance().getDataHandler();
    }

    public static RankData getRankData(UUID uuid) {
        return RankDataManager.getInstance().getRankData(uuid);
    }
}
