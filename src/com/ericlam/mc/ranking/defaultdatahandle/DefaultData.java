package com.ericlam.mc.ranking.defaultdatahandle;

import com.ericlam.mc.rankcal.RankDataManager;
import com.ericlam.mc.ranking.api.PlayerData;
import com.ericlam.mc.ranking.api.PvPRankingAPI;

import javax.annotation.Nonnull;
import java.util.UUID;

public class DefaultData implements PlayerData {

    private UUID uuid;
    private int kills;
    private int deaths;

    public DefaultData(UUID uuid, int kills, int deaths) {
        this.uuid = uuid;
        this.kills = kills;
        this.deaths = deaths;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void addKills(){
        ++kills;
        RankDataManager.getInstance().update(uuid);
    }

    public void addDeaths(){
        ++deaths;
        RankDataManager.getInstance().update(uuid);
    }

    @Override
    public double getFinalScores() {
        return PvPRankingAPI.getDefaultScore(kills, deaths);
    }

    @Override
    public int getPlays() {
        return kills + deaths;
    }

    @Override
    public UUID getPlayerUniqueId() {
        return uuid;
    }

    @Override
    public int compareTo(@Nonnull PlayerData o) {
        return Double.compare(this.getFinalScores(),o.getFinalScores());
    }
}
