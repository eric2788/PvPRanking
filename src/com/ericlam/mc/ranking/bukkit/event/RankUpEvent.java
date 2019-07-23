package com.ericlam.mc.ranking.bukkit.event;

import com.ericlam.mc.ranking.RankData;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * when rank up
 */
public class RankUpEvent extends RankEvent {

    private static final HandlerList handlerList = new HandlerList();

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public RankUpEvent(Player who, RankData oldRank, RankData newRank) {
        super(who, oldRank, newRank);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
