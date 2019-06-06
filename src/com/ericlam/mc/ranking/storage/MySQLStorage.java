package com.ericlam.mc.ranking.storage;

import com.ericlam.mc.rankcal.RankDataManager;
import com.ericlam.mc.ranking.RankData;
import com.ericlam.mc.ranking.api.PlayerData;
import com.ericlam.mc.ranking.sql.SQLManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;
import java.util.UUID;

public class MySQLStorage implements DataStorage {


    public MySQLStorage(){
        try(Connection connection = SQLManager.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `PvPRanking` (`UUID` VARCHAR(40) NOT NULL PRIMARY KEY , `Score` DOUBLE NOT NULL, `nScore` DOUBLE NOT NULL, `Rank` TEXT NOT NULL )")){
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveRankingData(TreeSet<RankData> data) {
        try(Connection connection = SQLManager.getInstance().getConnection()){
            for (RankData datum : data) {
                try(PreparedStatement statement = connection.prepareStatement("INSERT INTO `PvPRanking` VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE `Score`=?, `nScore`=?, `Rank`=? ")){
                    statement.setString(1,datum.getPlayerUniqueId().toString());
                    statement.setDouble(2,datum.getFinalScores());
                    statement.setDouble(5,datum.getFinalScores());
                    statement.setDouble(3,datum.getnScores());
                    statement.setDouble(6,datum.getnScores());
                    statement.setString(4,datum.getRank());
                    statement.setString(7,datum.getRank());
                    statement.execute();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public RankData getRankData(UUID playerUniqueId) {
        PlayerData data = RankDataManager.getInstance().getDataHandler().getPlayerData(playerUniqueId);
        try(Connection connection = SQLManager.getInstance().getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM `PvPRanking` WHERE `UUID` = ?")){
            statement.setString(1,playerUniqueId.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                double nScore = resultSet.getDouble("nScore");
                String rank = resultSet.getString("Rank");
                return new RankData(data,rank,nScore);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new RankData(data, "UnRanked", 0.0);
    }

    @Override
    public boolean removeRankData(UUID playerUniqueId) {
        try (Connection connection = SQLManager.getInstance().getConnection(); PreparedStatement statement = connection.prepareStatement("DELETE FROM `PvPRanking` WHERE `UUID` = ?")) {
            statement.setString(1, playerUniqueId.toString());
            statement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public TreeSet<RankData> loadRankData() {
        TreeSet<RankData> rankData = new TreeSet<>();
        try(Connection connection = SQLManager.getInstance().getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT `UUID` FROM `PvPRanking`")){
            ResultSet set = statement.executeQuery();
            while (set.next()){
                UUID uuid = UUID.fromString(set.getString("UUID"));
                RankData data = getRankData(uuid);
                if (data != null) rankData.add(data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rankData;
    }
}
