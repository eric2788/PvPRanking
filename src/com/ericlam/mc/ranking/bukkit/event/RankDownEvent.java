package com.ericlam.mc.ranking.bukkit.event;

import com.ericlam.mc.ranking.RankData;
import org.bukkit.entity.Player;

public class RankDownEvent extends RankEvent {

    public RankDownEvent(Player who, RankData oldRank, RankData newRank) {
        super(who, oldRank, newRank);
    }
}
