package com.ericlam.mc.ranking;

import com.ericlam.mc.ranking.api.PlayerData;

import javax.annotation.Nonnull;
import java.util.UUID;

public class RankData implements Comparable<RankData> {

    private PlayerData data;
    private String rank;
    private double nScores;

    public RankData(PlayerData data, String rank, double nScores) {
        this.data = data;
        this.rank = rank;
        this.nScores = nScores;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setnScores(double nScores) {
        this.nScores = nScores;
    }

    public double getFinalScores() {
        return data.getFinalScores();
    }

    public int getPlays() {
        return data.getPlays();
    }

    public UUID getPlayerUniqueId() {
        return data.getPlayerUniqueId();
    }

    public String getRank() {
        return rank;
    }

    public double getnScores() {
        return nScores;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.getClass() != obj.getClass()) return false;
        RankData data = (RankData)obj;
        return data.getPlayerUniqueId().equals(this.getPlayerUniqueId());
    }

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
}
