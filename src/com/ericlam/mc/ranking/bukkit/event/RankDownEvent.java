package com.ericlam.mc.ranking.bukkit.event;

import com.ericlam.mc.ranking.RankData;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

/**
 * 降段時觸發
 */
public class RankDownEvent extends RankEvent {

    private final HandlerList handlerList;
    public RankDownEvent(Player who, RankData oldRank, RankData newRank) {
        super(who, oldRank, newRank);
        this.handlerList = new HandlerList();
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
