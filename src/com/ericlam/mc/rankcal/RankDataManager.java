package com.ericlam.mc.rankcal;

import com.ericlam.mc.rankcal.types.CalType;
import com.ericlam.mc.rankcal.utils.Normalization;
import com.ericlam.mc.ranking.RankData;
import com.ericlam.mc.ranking.api.DataHandler;
import com.ericlam.mc.ranking.api.PlayerData;
import com.ericlam.mc.ranking.bukkit.event.NScoreUpdateEvent;
import com.ericlam.mc.ranking.bukkit.event.RankDownEvent;
import com.ericlam.mc.ranking.bukkit.event.RankUpEvent;
import com.ericlam.mc.ranking.main.PvPRanking;
import com.ericlam.mc.ranking.storage.DataStorage;
import com.ericlam.mc.ranking.storage.MySQLStorage;
import com.ericlam.mc.ranking.storage.YamlStorage;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.*;

public class RankDataManager {
    private static RankDataManager rankDataManager;
    private DataStorage storage;
    private DataHandler dataHandler;
    private TreeSet<RankData> rankData = new TreeSet<>();
    private Normalization normal;

    /**
     * @return 此 Class 實例
     */
    public static RankDataManager getInstance() {
        if (rankDataManager == null) rankDataManager = new RankDataManager();
        return rankDataManager;
    }

    private RankDataManager() {
        switch (PvPRanking.getStorage()){
            case YAML:
                storage = new YamlStorage();
                rankData.addAll(storage.loadRankData());
                break;
            case MYSQL:
                default:
                storage = new MySQLStorage();
        }
        // Dangerous to load too many in mysql
        //rankData.addAll(storage.loadRankData());
    }

    public void registerHandler(DataHandler handler){
        Validate.notNull(handler.getPlayerData(Bukkit.getOfflinePlayers()[0].getUniqueId()), "getPlayerData 方法不能返回 null");
        this.dataHandler = handler;
    }

    /**
     *
     * @param type 演算法類型
     * @param score 標準分
     * @return 段位
     */
    public static String getRank(CalType type, double score) {
        String[] ranks = PvPRanking.getRanks();
        switch (type) {
            case MIN_MAX:
                return ranks[(int) Math.round(score)];
            case Z_SCORE:
                int minScore = (int) -Math.floor((int) (ranks.length / 2));
                int scoreIndex = ((int) score) - minScore;
                if (scoreIndex < 0) {
                    return ranks[0];
                } else if (scoreIndex > ranks.length) {
                    return ranks[ranks.length + 1];
                }
                return ranks[scoreIndex];
            default:
                return null;
        }
    }

    public RankData getRankData(UUID uuid){
        return rankData.stream().filter(l->l.equals(uuid)).findAny().orElseGet(()->{
            RankData data = storage.getRankData(uuid);
            rankData.add(data);
            return data;
        });
    }

    /**
     *
     * @param uuid 玩家UUID
     * @return 玩家是否在快取內
     */
    public boolean rankContain(UUID uuid) {
        return rankData.stream().anyMatch(l -> l.getPlayerUniqueId().equals(uuid));
    }

    public void saveRankData(){
        storage.saveRankingData(rankData);
    }

    public DataHandler getDataHandler() {
        return dataHandler;
    }

    public TreeSet<RankData> getRankData() {
        return rankData;
    }

    /**
     * 用於篡改排位存儲數據
     * @param uuid 玩家UUID
     * @param data 新的排位存儲數據
     */
    public void setRankData(UUID uuid, RankData data) {
        rankData.removeIf(l -> l.equals(uuid));
        rankData.add(data);
    }

    /**
     * 用於更新玩家數據，
     * 在數據被修改之後必須使用此方法以更改段位數據。
     * 若玩家getPlays數據尚未通過特定條件則不會進行任何更新。
     *
     * @param uuid 玩家UUID
     */
    public void update(UUID uuid) {
        PluginManager manager = PvPRanking.getPlugin().getServer().getPluginManager();
        PlayerData data = dataHandler.getPlayerData(uuid);
        if (data.getPlays() < (int) PvPRanking.getConfigData("require-plays")) return;
        RankData oldRank = getRankData(uuid).clone();
        RankData newRank;
        switch (PvPRanking.getCalType()){
            case MIN_MAX:
                int min = 0;
                int max = PvPRanking.getRanks().length-1;
                newRank = normal.minMaxNormalizeSingle(min,max,data);
                break;
            case Z_SCORE:
            default:
                newRank = normal.zScoreNormalizeSingle(data);
                break;
        }
        setRankData(uuid,newRank);
        if (oldRank.getnScores() != newRank.getnScores())
            manager.callEvent(new NScoreUpdateEvent(Bukkit.getPlayer(uuid), data, newRank));
        if (!oldRank.getRank().equals(newRank.getRank())) {
            Player player = Bukkit.getPlayer(uuid);
            if (player == null || !player.isOnline()) return;
            manager.callEvent(newRank.getnScores() > oldRank.getnScores() ? new RankUpEvent(player, oldRank, newRank) : new RankDownEvent(player, oldRank, newRank));
        }
    }

    /**
     * @param playerUniqueId 玩家UUID
     * @return 刪除結果
     */
    public boolean removeRankData(UUID playerUniqueId) {
        return storage.removeRankData(playerUniqueId) && rankData.removeIf(e -> e.equals(playerUniqueId));
    }

    void updateRankData() {
        RankData[] rankData;
        List<PlayerData> datas = new ArrayList<>(dataHandler.getAllPlayerData());
        normal = new Normalization(datas);
        switch (PvPRanking.getCalType()){
            case MIN_MAX:
                int min = 0;
                int max = PvPRanking.getRanks().length-1;
                rankData = normal.minMaxNormalize(min,max);
                break;
            case Z_SCORE:
                default:
                rankData = normal.zScoreNormalize();
        }
        this.rankData.clear();
        this.rankData.addAll(Arrays.asList(rankData));
    }


}
