package com.ericlam.mc.ranking.bukkit;

import com.ericlam.mc.rankcal.RankDataManager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class DataLoadRunnable extends BukkitRunnable {
    private UUID uuid;

    DataLoadRunnable(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void run() {
        RankDataManager.getInstance().getRankData(uuid);
        RankDataManager.getInstance().getDataHandler().getPlayerData(uuid);
    }
}
