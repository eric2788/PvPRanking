package com.ericlam.mc.ranking;

import com.ericlam.mc.ranking.api.PlayerData;

import javax.annotation.Nonnull;
import java.util.UUID;

public class RankData implements Comparable<RankData>, Cloneable {

    private PlayerData data;
    private String rank;
    private double nScores;

    /**
     * @param data    玩家存儲數據
     * @param rank    段位
     * @param nScores 標準分
     */
    public RankData(PlayerData data, String rank, double nScores) {
        this.data = data;
        this.rank = rank;
        this.nScores = nScores;
    }

    /**
     * @return 積分
     */
    public double getFinalScores() {
        return data.getFinalScores();
    }

    /**
     *
     * @return 遊玩次數
     */
    public int getPlays() {
        return data.getPlays();
    }

    /**
     *
     * @return 玩家 UUID
     */
    public UUID getPlayerUniqueId() {
        return data.getPlayerUniqueId();
    }

    /**
     *
     * @return 段位
     */
    public String getRank() {
        return rank;
    }

    /**
     *
     * @return 標準分
     */
    public double getnScores() {
        return nScores;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) return false;
        RankData data = (RankData)obj;
        return data.getPlayerUniqueId().equals(this.getPlayerUniqueId());
    }

    /**
     *
     * @param uuid 玩家UUID
     * @return 是否相同
     */
    public boolean equals(UUID uuid){
        return uuid.equals(this.getPlayerUniqueId());
    }

    @Override
    public int hashCode() {
        return getPlayerUniqueId().hashCode();
    }

    @Override
    public int compareTo(@Nonnull RankData o) {
        return Double.compare(this.getFinalScores(),o.getFinalScores());
    }

    /**
     *
     * @return 創建相同數據的 RankData
     */
    @Override
    public RankData clone() {
        return new RankData(this.data, this.rank, this.nScores);
    }
}
