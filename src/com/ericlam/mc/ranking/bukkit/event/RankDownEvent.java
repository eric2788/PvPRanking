package com.ericlam.mc.ranking.bukkit.event;

import com.ericlam.mc.ranking.RankData;
import org.bukkit.entity.Player;

/**
 * 降段時觸發
 */
public class RankDownEvent extends RankEvent {

    public RankDownEvent(Player who, RankData oldRank, RankData newRank) {
        super(who, oldRank, newRank);
    }
}
