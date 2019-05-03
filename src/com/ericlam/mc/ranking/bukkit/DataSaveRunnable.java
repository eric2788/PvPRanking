package com.ericlam.mc.ranking.bukkit;

import com.ericlam.mc.rankcal.RankDataManager;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class DataSaveRunnable extends BukkitRunnable {

    private UUID uuid;

    DataSaveRunnable(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void run() {
        RankDataManager.getInstance().getDataHandler().savePlayerData(uuid);
    }
}
