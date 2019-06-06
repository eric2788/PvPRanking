package com.ericlam.mc.ranking.api;

import com.ericlam.mc.rankcal.RankDataManager;
import com.ericlam.mc.ranking.RankData;
import com.ericlam.mc.ranking.main.PvPRanking;
import org.bukkit.Bukkit;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.UUID;

/**
 * use this class for api
 */
public class PvPRankingAPI {

    /**
     * @param uuid player uuid
     * @return rank name
     */
    public static String getRank(UUID uuid) {
        return RankDataManager.getInstance().getRankData(uuid).getRank();
    }

    /**
     *
     * @param uuid player UUID
     * @return player score
     */
    public static double getScores(UUID uuid) {
        return RankDataManager.getInstance().getRankData(uuid).getFinalScores();
    }

    /**
     *
     * @param uuid player UUID
     * @return player n-score
     */
    public static double getNScores(UUID uuid) {
        return RankDataManager.getInstance().getRankData(uuid).getnScores();
    }

    /**
     *
     *
     * @param uuid player UUID
     * @return played times
     *
     */
    public static int getPlays(UUID uuid) {
        return RankDataManager.getInstance().getRankData(uuid).getPlays();
    }

    /**
     * get the data handler you have implemented
     * @return DataHandler
     */
    public static DataHandler getDataHandler() {
        return RankDataManager.getInstance().getDataHandler();
    }

    /**
     *
     * @param uuid player UUID
     * @return RankData
     */
    public static RankData getRankData(UUID uuid) {
        return RankDataManager.getInstance().getRankData(uuid);
    }

    /**
     * use to update player data and rank data
     * it should be use when any player data has been edited
     * else, the rank data won't be updated
     *
     * @param uuid player UUID
     */
    public static void update(UUID uuid) {
        RankDataManager.getInstance().update(uuid);
    }

    /**
     * @param kills  kills
     * @param deaths deaths
     * @return the score using calculation format on config.yml
     */
    public static double getDefaultScore(int kills, int deaths) {
        String script = PvPRanking.getConfigManager().getCalFormat().replace("<kills>", kills + "").replace("<deaths>", deaths + "");
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            return (double) engine.eval(script);
        } catch (ScriptException e) {
            Bukkit.getLogger().warning("Script calculation failed. changing it to 0");
            return 0;
        }
    }
}
