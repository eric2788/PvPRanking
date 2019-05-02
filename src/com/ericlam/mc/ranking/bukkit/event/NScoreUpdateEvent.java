package com.ericlam.mc.ranking.bukkit.event;

import com.ericlam.mc.rankcal.RankDataManager;
import com.ericlam.mc.ranking.RankData;
import com.ericlam.mc.ranking.api.PlayerData;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class NScoreUpdateEvent extends PlayerEvent {

    private PlayerData playerData;
    private RankData rankData;

    public NScoreUpdateEvent(Player who) {
        super(who);
        playerData = RankDataManager.getInstance().getDataHandler().getPlayerData(who.getUniqueId());
        rankData = RankDataManager.getInstance().update(who.getUniqueId());
    }

    @Override
    public HandlerList getHandlers() {
        return new HandlerList();
    }

    public PlayerData getPlayerData() {
        return playerData;
    }

    public RankData getRankData() {
        return rankData;
    }
}
