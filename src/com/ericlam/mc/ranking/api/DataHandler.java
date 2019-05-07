package com.ericlam.mc.ranking.api;

import com.ericlam.mc.rankcal.RankDataManager;
import org.bukkit.OfflinePlayer;

import javax.annotation.Nonnull;
import java.util.TreeSet;
import java.util.UUID;

/**
 * 透過 繼承此 接口  掛接自定義的儲存數據的各種方法(包括獲取/儲存/刪除)
 */
public abstract class DataHandler {

    /**
     * 若果你的存儲數據 Class 有套接 PlayerData , 則從這裏返回
     *
     * @param playerUniqueId 玩家UUID
     * @return 玩家存儲數據
     */
    public abstract PlayerData getPlayerData(UUID playerUniqueId);

    /**
     *
     *  若果你的存儲數據 Class 有套接 PlayerData , 則從這裏返回。
     *
     * 注意！此方法將作為獲取所有玩家數據并計算排位積分，因此你需要在此方法返回所有離線數據。
     *
     * @return 返回所有的玩家存儲數據
     *
     */
    public abstract TreeSet<PlayerData> getAllPlayerData();

    /**
     *
     * 存儲方法，若果你不想使用本 API 進行存儲，則可漏空
     *
     * @param playerUniqueId 玩家UUID
     */
    public abstract void savePlayerData(UUID playerUniqueId);

    /**
     *
     * @param player 玩家
     * @return 多行數的文字資訊訊息
     */
    public abstract String[] showPlayerData(@Nonnull OfflinePlayer player);

    /**
     *
     * @param player 玩家
     * @return 刪除結果
     */
    public abstract boolean removePlayerData(@Nonnull OfflinePlayer player);

    /**
     * 存儲方法，若果你不想使用本 API 進行存儲，則可漏空
     */
    public abstract void saveAllPlayerData();

    /**
     * 註冊 此套接
     */
    public final void register(){
        RankDataManager.getInstance().setHandler(this);
    }
}
