package com.ericlam.mc.ranking.bukkit.event;

import com.ericlam.mc.ranking.RankData;
import org.bukkit.entity.Player;

/**
 * 升段時觸發
 */
public class RankUpEvent extends RankEvent {

    public RankUpEvent(Player who, RankData oldRank, RankData newRank) {
        super(who, oldRank, newRank);
    }

}
