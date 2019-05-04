package com.ericlam.mc.ranking.api;

import com.ericlam.mc.ranking.main.PvPRanking;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PlaceHolderHook extends PlaceholderExpansion {
    private PvPRanking plugin;

    public PlaceHolderHook(PvPRanking plugin) {
        this.plugin = plugin;
    }

    @Override
    public String onPlaceholderRequest(Player p, String params) {
        if (p == null) return null;
        switch (params) {
            case "rank":
                return PvPRankingAPI.getRank(p.getUniqueId());
            case "score":
                return PvPRankingAPI.getScores(p.getUniqueId()) + "";
            case "n_score":
                return PvPRankingAPI.getNScores(p.getUniqueId()) + "";
            case "plays":
                return PvPRankingAPI.getPlays(p.getUniqueId()) + "";
            default:
                break;
        }
        return null;
    }

    @Override
    public String getIdentifier() {
        return plugin.getName().toLowerCase();
    }

    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }
}
