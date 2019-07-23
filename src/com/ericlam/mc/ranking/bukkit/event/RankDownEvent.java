package com.ericlam.mc.ranking.bukkit.event;

import com.ericlam.mc.ranking.RankData;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * when rank down
 */
public class RankDownEvent extends RankEvent {

    private static final HandlerList handlerList = new HandlerList();
    public RankDownEvent(Player who, RankData oldRank, RankData newRank) {
        super(who, oldRank, newRank);
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
