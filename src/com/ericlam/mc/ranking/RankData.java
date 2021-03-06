package com.ericlam.mc.ranking;

import com.ericlam.mc.ranking.api.PlayerData;

import javax.annotation.Nonnull;
import java.util.UUID;

public class RankData implements Comparable<RankData>, Cloneable {

    private PlayerData data;
    private String rank;
    private double nScores;

    /**
     * @param data    player data
     * @param rank    rank
     * @param nScores n-score
     */
    public RankData(PlayerData data, String rank, double nScores) {
        this.data = data;
        this.rank = rank;
        this.nScores = nScores;
    }

    /**
     * @return scores
     */
    public double getFinalScores() {
        return data.getFinalScores();
    }

    /**
     *
     * @return played
     */
    public int getPlays() {
        return data.getPlays();
    }

    /**
     *
     * @return player UUID
     */
    public UUID getPlayerUniqueId() {
        return data.getPlayerUniqueId();
    }

    /**
     *
     * @return rank
     */
    public String getRank() {
        return rank;
    }

    /**
     *
     * @return n-scores
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
     * @param uuid player UUID
     * @return equal
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
     * @return clone RankData
     */
    @Override
    public RankData clone() {
        return new RankData(this.data, this.rank, this.nScores);
    }
}
