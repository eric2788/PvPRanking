package com.ericlam.mc.ranking.bukkit.event;

import com.ericlam.mc.ranking.RankData;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerEvent;

/**
 * 主體的 Rank 事件
 */
public abstract class RankEvent extends PlayerEvent implements Cancellable {

    private RankData newRank;
    private RankData oldRank;
    private boolean cancelled;

    /**
     * @param who     玩家
     * @param oldRank 舊排位存儲數據
     * @param newRank 新排位存儲數據
     */
    public RankEvent(Player who, RankData oldRank, RankData newRank) {
        super(who);
        this.oldRank = oldRank;
        this.newRank = newRank;
        this.cancelled = false;
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

    /**
     * 取消的是通知事件，而不是段位的升降。
     *
     * @return 是否取消通知事件
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * 取消的是通知事件，而不是段位的升降。
     *
     * @param b 是否取消通知事件
     */
    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
