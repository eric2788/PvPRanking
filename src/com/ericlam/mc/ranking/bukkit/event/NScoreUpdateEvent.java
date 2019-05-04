package com.ericlam.mc.ranking.bukkit.event;

import com.ericlam.mc.ranking.RankData;
import com.ericlam.mc.ranking.api.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class NScoreUpdateEvent extends PlayerEvent {

    private PlayerData playerData;
    private RankData rankData;
    private HandlerList handlerList;

    /**
     * @param who      玩家
     * @param data     玩家存儲數據
     * @param rankData 排位存儲數據
     */
    public NScoreUpdateEvent(Player who, PlayerData data, RankData rankData) {
        super(who);
        this.playerData = data;
        this.rankData = rankData;
        this.handlerList = new HandlerList();
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    /**
     *
     * @return 玩家存儲數據
     */
    public PlayerData getPlayerData() {
        return playerData;
    }

    /**
     *
     * @return 玩家排位數據
     */
    public RankData getRankData() {
        return rankData;
    }
}
