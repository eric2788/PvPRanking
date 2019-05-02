package com.ericlam.mc.ranking.bukkit.event;

import com.ericlam.mc.ranking.RankData;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class RankUpEvent extends PlayerEvent {

    private RankData rankData;
    private String newRank;
    private String oldRank;

    public RankUpEvent(Player who,String oldRank,RankData data) {
        super(who);
        this.oldRank = oldRank;
        this.rankData = data;
        this.newRank = data.getRank();
    }

    public String getNewRank() {
        return newRank;
    }

    public String getOldRank() {
        return oldRank;
    }

    @Override
    public HandlerList getHandlers() {
        return null;
    }
}
