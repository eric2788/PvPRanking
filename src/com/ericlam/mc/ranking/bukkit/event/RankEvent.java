package com.ericlam.mc.ranking.bukkit.event;

import com.ericlam.mc.ranking.RankData;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * 主體的 Rank 事件
 */
public class RankEvent extends PlayerEvent {

    private RankData newRank;
    private RankData oldRank;
    private HandlerList handlerList;

    /**
     * @param who     玩家
     * @param oldRank 舊排位存儲數據
     * @param newRank 新排位存儲數據
     */
    public RankEvent(Player who, RankData oldRank, RankData newRank) {
        super(who);
        this.oldRank = oldRank;
        this.newRank = newRank;
        this.handlerList = new HandlerList();
    }

    /**
     * @return 該玩家新的排位存儲數據
     */
    public RankData getNewRank() {
        return newRank;
    }

    /**
     *
     * @return 該玩家舊的排位存儲數據
     */
    public RankData getOldRank() {
        return oldRank;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
