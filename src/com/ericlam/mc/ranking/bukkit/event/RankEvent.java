package com.ericlam.mc.ranking.bukkit.event;

import com.ericlam.mc.ranking.RankData;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class RankEvent extends PlayerEvent {

    private RankData newRank;
    private RankData oldRank;
    private HandlerList handlerList;

    public RankEvent(Player who, RankData oldRank, RankData newRank) {
        super(who);
        this.oldRank = oldRank;
        this.newRank = newRank;
        this.handlerList = new HandlerList();
    }

    public RankData getNewRank() {
        return newRank;
    }

    public RankData getOldRank() {
        return oldRank;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
