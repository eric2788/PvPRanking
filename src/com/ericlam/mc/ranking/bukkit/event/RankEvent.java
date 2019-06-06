package com.ericlam.mc.ranking.bukkit.event;

import com.ericlam.mc.ranking.RankData;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerEvent;

/**
 * rank event
 */
public abstract class RankEvent extends PlayerEvent implements Cancellable {

    private RankData newRank;
    private RankData oldRank;
    private boolean cancelled;

    /**
     * @param who     player
     * @param oldRank old rank data
     * @param newRank new rank data
     */
    public RankEvent(Player who, RankData oldRank, RankData newRank) {
        super(who);
        this.oldRank = oldRank;
        this.newRank = newRank;
        this.cancelled = false;
    }

    /**
     * @return get new rank
     */
    public RankData getNewRank() {
        return newRank;
    }

    /**
     *
     * @return get old rank
     */
    public RankData getOldRank() {
        return oldRank;
    }

    /**
     * cancel notification only
     *
     * @return cancel
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * cancel notification only
     *
     * @param b cancel
     */
    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }
}
