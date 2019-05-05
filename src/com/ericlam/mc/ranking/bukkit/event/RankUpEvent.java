package com.ericlam.mc.ranking.bukkit.event;

import com.ericlam.mc.ranking.RankData;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * 升段時觸發
 */
public class RankUpEvent extends RankEvent {

    private final HandlerList handlerList;
    public RankUpEvent(Player who, RankData oldRank, RankData newRank) {
        super(who, oldRank, newRank);
        handlerList = new HandlerList();
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
