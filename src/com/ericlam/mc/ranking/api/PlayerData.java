package com.ericlam.mc.ranking.api;

import java.util.UUID;

/**
 * 此接口用於套接自定義的儲存數據
 */
public interface PlayerData extends Comparable<PlayerData> {

    /**
     * @return 總積分, 由玩家自行計算
     */
    double getFinalScores();

    /**
     *
     * @return 遊玩次數/勝數, 由玩家自行計算
     */
    int getPlays();

    /**
     *
     * @return 玩家 UUID
     */
    UUID getPlayerUniqueId();

}
