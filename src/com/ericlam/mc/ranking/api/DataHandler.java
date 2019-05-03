package com.ericlam.mc.ranking.api;

import com.ericlam.mc.rankcal.RankDataManager;

import java.util.TreeSet;
import java.util.UUID;

public abstract class DataHandler {

    public abstract PlayerData getPlayerData(UUID playerUniqueId);

    public abstract TreeSet<PlayerData> getAllPlayerData();

    public abstract void savePlayerData(UUID playerUniqueId);

    public abstract void saveAllPlayerData();

    public final void register(){
        RankDataManager.getInstance().registerHandler(this);
    }
}
