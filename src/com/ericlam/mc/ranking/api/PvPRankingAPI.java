package com.ericlam.mc.ranking.api;

import com.ericlam.mc.rankcal.RankDataManager;
import com.ericlam.mc.ranking.RankData;

import java.util.UUID;

/**
 * 透過 使用 此 Class  獲得 所需數據
 */
public class PvPRankingAPI {

    /**
     * @param uuid 玩家UUID
     * @return 段位名稱
     */
    public static String getRank(UUID uuid) {
        return RankDataManager.getInstance().getRankData(uuid).getRank();
    }

    /**
     *
     * @param uuid 玩家UUID
     * @return 玩家積分
     */
    public static double getScores(UUID uuid) {
        return RankDataManager.getInstance().getRankData(uuid).getFinalScores();
    }

    /**
     *
     * @param uuid 玩家UUID
     * @return 玩家標準分
     */
    public static double getNScores(UUID uuid) {
        return RankDataManager.getInstance().getRankData(uuid).getnScores();
    }

    /**
     *
     *  此返回方式由插件師在接口套用，因此方法為自行計算
     * @param uuid 玩家UUID
     * @return 戰數/遊玩次數
     *
     */
    public static int getPlays(UUID uuid) {
        return RankDataManager.getInstance().getRankData(uuid).getPlays();
    }

    /**
     * 返回 你所掛接的 DataHandler 接口，若果你沒有掛接，則返回默認掛接
     * @return DataHandler 接口
     */
    public static DataHandler getDataHandler() {
        return RankDataManager.getInstance().getDataHandler();
    }

    /**
     *
     * @param uuid 玩家UUID
     * @return 返回 RankData 排位存儲數據
     */
    public static RankData getRankData(UUID uuid) {
        return RankDataManager.getInstance().getRankData(uuid);
    }

    /**
     * 用於更新玩家數據，
     * 在數據被修改之後必須使用此方法以更改段位數據。
     * 若玩家getPlays數據尚未通過特定條件則不會進行任何更新。
     *
     * @param uuid 玩家UUID
     */
    public static void update(UUID uuid) {
        RankDataManager.getInstance().update(uuid);
    }
}
