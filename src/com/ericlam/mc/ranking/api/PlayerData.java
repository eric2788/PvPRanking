package com.ericlam.mc.ranking.api;

import java.util.UUID;

public interface PlayerData extends Comparable<PlayerData> {

    double getFinalScores();

    int getPlays();

    UUID getPlayerUniqueId();

}
