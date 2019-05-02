package com.ericlam.mc.ranking;

import com.ericlam.mc.ranking.main.PvPRanking;
import com.ericlam.mc.ranking.sql.SQLManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.TreeSet;
import java.util.UUID;

public class DefaultDataManager {
    private TreeSet<DefaultData> datas = new TreeSet<>();
    private static DefaultDataManager defaultDataManager;
    private Plugin plugin;

    public static DefaultDataManager getInstance() {
        if (defaultDataManager == null) defaultDataManager = new DefaultDataManager();
        return defaultDataManager;
    }

    private DefaultDataManager() {
        this.plugin = PvPRanking.getPlugin();
        for (OfflinePlayer player : Bukkit.getOfflinePlayers()) {
            DefaultData data = getData(player.getUniqueId());
            if (data == null) continue;
            this.datas.add(data);
        }
    }

    public void addData(DefaultData data){
        datas.add(data);
    }

    public DefaultData findData(UUID uuid){
        return datas.stream().filter(d->d.getPlayerUniqueId().equals(uuid)).findAny().orElseGet(()->{
            DefaultData data = getData(uuid);
            datas.add(data);
            return data;
        });
    }

    public TreeSet<DefaultData> getDatas() {
       return datas;
    }

    public void saveData() {
        switch (PvPRanking.getStorage()){
            case MYSQL:
                saveSQLData();
                break;
            case YAML:
                default:
                saveYamlData();
        }
    }

    public DefaultData getData(UUID uuid){
        switch (PvPRanking.getStorage()){
            case MYSQL:
                return getSQLData(uuid);
            case YAML:
                default:
                 return getYamlData(uuid);
        }
    }

    private void saveSQLData(){
        try(Connection connection = SQLManager.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `DefaultData` (`UUID` VARCHAR(40) NOT NULL , `Kills` INT NOT NULL , `Deaths` INT NOT NULL )")){
            statement.execute();
            for (DefaultData data : datas) {
                try(PreparedStatement save = connection.prepareStatement("INSERT INTO `DefaultData` VALUES(?,?,?) ON DUPLICATE KEY UPDATE `Kills` = ? , `Deaths` = ?")){
                    save.setString(1,data.getPlayerUniqueId().toString());
                    save.setInt(2,data.getKills());
                    save.setInt(3,data.getDeaths());
                    save.setInt(4,data.getKills());
                    save.setInt(5,data.getDeaths());
                    save.execute();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveYamlData(){
        File folder = new File(plugin.getDataFolder(),"Default_Data");
        if (!folder.exists()) folder.mkdir();
        for (DefaultData data : datas) {
            File yml = new File(folder,data.getPlayerUniqueId().toString()+".yml");
            FileConfiguration user = new YamlConfiguration();
            user.set("kills",data.getKills());
            user.get("deaths",data.getDeaths());
            try {
                user.save(yml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private DefaultData getSQLData(UUID uuid){
        try(Connection connection = SQLManager.getInstance().getConnection();PreparedStatement statement = connection.prepareStatement("SELECT * FROM `DefaultData` WHERE `UUID`=?")){
            statement.setString(1,uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                UUID playerUniqueId = UUID.fromString(resultSet.getString("UUID"));
                int kills = resultSet.getInt("Kills");
                int deaths = resultSet.getInt("Deaths");
                return new DefaultData(playerUniqueId,kills,deaths);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new DefaultData(uuid,0,0);
    }

    private DefaultData getYamlData(UUID uuid){
        File folder = new File(plugin.getDataFolder(),"Default_Data");
        if (!folder.exists()) folder.mkdir();
        File file = new File(folder,uuid.toString()+".yml");
        if (!file.exists()) return new DefaultData(uuid,0,0);
        FileConfiguration user = YamlConfiguration.loadConfiguration(file);
        int kills = user.getInt("kills");
        int deaths = user.getInt("deaths");
        return new DefaultData(uuid,kills,deaths);
    }


}
