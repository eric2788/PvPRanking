package com.ericlam.mc.ranking;

import com.ericlam.mc.ranking.api.DataHandler;
import com.ericlam.mc.ranking.api.PlayerData;

import java.util.TreeSet;
import java.util.UUID;

public class DefaultDataHandler extends DataHandler {

    @Override
    public PlayerData getPlayerData(UUID playerUniqueId) {
        return DefaultDataManager.getInstance().findData(playerUniqueId);
    }

    @Override
    public TreeSet<PlayerData> getAllPlayerData() {
        return new TreeSet<PlayerData>(DefaultDataManager.getInstance().getAllData());
    }

    @Override
    public void savePlayerData(UUID playerUniqueId) {
        DefaultDataManager.getInstance().saveData(playerUniqueId);
    }

    @Override
    public void saveAllPlayerData() {
        DefaultDataManager.getInstance().saveData();
    }
}
