package com.ericlam.mc.ranking.api;

import java.util.UUID;

/**
 * player data interface
 */
public interface PlayerData extends Comparable<PlayerData> {

    /**
     * @return total scores
     */
    double getFinalScores();

    /**
     *
     * @return played times
     */
    int getPlays();

    /**
     *
     * @return player UUID
     */
    UUID getPlayerUniqueId();

}
