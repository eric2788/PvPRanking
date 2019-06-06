package com.ericlam.mc.ranking.api;

import com.ericlam.mc.rankcal.RankDataManager;
import org.bukkit.OfflinePlayer;

import javax.annotation.Nonnull;
import java.util.TreeSet;
import java.util.UUID;

/**
 * data handler api
 */
public abstract class DataHandler {

    /**
     * get the player data
     *
     * @param playerUniqueId player UUID
     * @return player data
     */
    public abstract PlayerData getPlayerData(UUID playerUniqueId);

    /**
     *
     * get all player data
     *
     * this is used to calculate all the rank data , so it is required to get the data including offline.
     *
     * @return all player data
     *
     */
    public abstract TreeSet<PlayerData> getAllPlayerData();

    /**
     *
     * save method , can be optional to be empty
     *
     * @param playerUniqueId player UUID
     */
    public abstract void savePlayerData(UUID playerUniqueId);

    /**
     *
     * @param player player
     * @return String array to send messages
     */
    public abstract String[] showPlayerData(@Nonnull OfflinePlayer player);

    /**
     *
     * @param player player
     * @return deleting result
     */
    public abstract boolean removePlayerData(@Nonnull OfflinePlayer player);

    /**
     * save all player data method, can be optional to be empty
     */
    public abstract void saveAllPlayerData();

    /**
     * register this API
     */
    public final void register(){
        RankDataManager.getInstance().setHandler(this);
    }
}
