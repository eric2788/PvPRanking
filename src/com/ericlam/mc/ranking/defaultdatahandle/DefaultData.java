package com.ericlam.mc.ranking.defaultdatahandle;

import com.ericlam.mc.rankcal.RankDataManager;
import com.ericlam.mc.ranking.api.PlayerData;
import com.ericlam.mc.ranking.main.PvPRanking;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
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
        String script = PvPRanking.getConfigManager().getCalFormat().replace("<kills>", kills + "").replace("<deaths>", deaths + "");
        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");
        try {
            return (double) engine.eval(script);
        } catch (ScriptException e) {
            Bukkit.getLogger().warning("計算格式轉換出現問題，將返回 0 值");
            return 0;
        }
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
