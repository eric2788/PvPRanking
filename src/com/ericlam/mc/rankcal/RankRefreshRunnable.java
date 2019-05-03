package com.ericlam.mc.rankcal;

import org.bukkit.scheduler.BukkitRunnable;

public class RankRefreshRunnable extends BukkitRunnable {

    @Override
    public void run() {
        RankDataManager.getInstance().updateRankData();
        RankDataManager.getInstance().saveRankData();
    }
}
