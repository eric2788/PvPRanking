package com.ericlam.mc.rankcal;

import com.ericlam.mc.ranking.main.PvPRanking;
import org.bukkit.Bukkit;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class RefresherScheduler {
    private LocalDateTime firstcheck;

    public RefresherScheduler(PvPRanking plugin) {
        Object obj = PvPRanking.getConfigData("update-time");
        LocalTime resetTime = obj == null ? LocalTime.MIDNIGHT : LocalTime.of(Integer.parseInt(((String) obj).split(":")[0]), Integer.parseInt(((String) obj).split(":")[1]));
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, () -> {
            LocalDateTime now = LocalDateTime.now();
            if (firstcheck != null) {

                long first = Timestamp.valueOf(firstcheck).getTime();
                long second = Timestamp.valueOf(now).getTime();
                long reset = Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), resetTime)).getTime();

                if (reset >= first && reset <= second) {
                    new RankRefreshRunnable().runTaskAsynchronously(plugin);
                }
            }
            firstcheck = now;
        }, 0L, 5 * 60 * 20L);
    }

}
