package com.ericlam.mc.ranking.bukkit.event;

import com.ericlam.mc.ranking.RankData;
import com.ericlam.mc.ranking.api.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * when n-score update
 */
public class NScoreUpdateEvent extends PlayerEvent {

    private PlayerData playerData;
    private RankData rankData;
    private static final HandlerList handlerList = new HandlerList();

    /**
     * @param who      player
     * @param data     player data
     * @param rankData rank data
     */
    public NScoreUpdateEvent(Player who, PlayerData data, RankData rankData) {
        super(who);
        this.playerData = data;
        this.rankData = rankData;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    /**
     *
     * @return player data
     */
    public PlayerData getPlayerData() {
        return playerData;
    }

    /**
     *
     * @return rank data
     */
    public RankData getRankData() {
        return rankData;
    }
}
