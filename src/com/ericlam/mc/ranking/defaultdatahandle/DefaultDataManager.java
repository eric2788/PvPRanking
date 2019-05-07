package com.ericlam.mc.ranking.defaultdatahandle;

import com.ericlam.mc.ranking.main.PvPRanking;
import com.ericlam.mc.ranking.sql.SQLManager;
import org.apache.commons.io.FilenameUtils;
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

/**
 * me fucking lazy, so i dont fucking use interface to split data storage method here
 * 別吐糟我為啥不用 Interface 分開 yaml 和 mysql 存儲, 因為我懶哈哈哈ww
 */
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

    DefaultData findData(UUID uuid) {
        return datas.stream().filter(d->d.getPlayerUniqueId().equals(uuid)).findAny().orElseGet(()->{
            DefaultData data = getData(uuid);
            datas.add(data);
            return data;
        });
    }

    public boolean setData(DefaultData data) {
        datas.removeIf(d -> d.getPlayerUniqueId().equals(data.getPlayerUniqueId()));
        return datas.add(data);
    }

    public TreeSet<DefaultData> getDatas() {
        return datas;
    }


    boolean removeData(UUID uuid) {
        switch (PvPRanking.getStorage()) {
            case MYSQL:
                return removeSQLData(uuid);
            case YAML:
            default:
                return removeYamlData(uuid);
        }
    }

    void saveData() {
        switch (PvPRanking.getStorage()){
            case MYSQL:
                saveSQLData();
                break;
            case YAML:
            default:
                saveYamlData();
        }
    }

    void saveData(UUID uuid) {
        switch (PvPRanking.getStorage()) {
            case MYSQL:
                saveSQLData(uuid);
                break;
            case YAML:
            default:
                saveYamlData(uuid);
        }
    }

    private DefaultData getData(UUID uuid) {
        switch (PvPRanking.getStorage()){
            case MYSQL:
                return getSQLData(uuid);
            case YAML:
            default:
                return getYamlData(uuid);
        }
    }

    TreeSet<DefaultData> getAllData() {
        switch (PvPRanking.getStorage()) {
            case MYSQL:
                return getSQLData();
            case YAML:
            default:
                return getYamlData();
        }
    }


    private boolean removeYamlData(UUID uuid) {
        File folder = new File(plugin.getDataFolder(), "Default_Data");
        File user = new File(folder, uuid.toString() + ".yml");
        if (!user.exists()) return false;
        return user.delete();
    }

    private boolean removeSQLData(UUID uuid) {
        try (Connection connection = SQLManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `DefaultData` (`UUID` VARCHAR(40) NOT NULL , `Kills` INT NOT NULL , `Deaths` INT NOT NULL )");
             PreparedStatement delete = connection.prepareStatement("DELETE FROM `DefaultData` WHERE `UUID` = ?")) {
            statement.execute();
            delete.setString(1, uuid.toString());
            delete.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void saveSQLData(){
        try(Connection connection = SQLManager.getInstance().getConnection();
            PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `DefaultData` (`UUID` VARCHAR(40) NOT NULL , `Kills` INT NOT NULL , `Deaths` INT NOT NULL )")){
            statement.execute();
            for (DefaultData data : datas) {
                stmt(connection, data);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveSQLData(UUID uuid) {
        try (Connection connection = SQLManager.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS `DefaultData` (`UUID` VARCHAR(40) NOT NULL , `Kills` INT NOT NULL , `Deaths` INT NOT NULL )")) {
            statement.execute();
            DefaultData data = findData(uuid);
            stmt(connection, data);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void stmt(Connection connection, DefaultData data) throws SQLException {
        try (PreparedStatement save = connection.prepareStatement("INSERT INTO `DefaultData` VALUES(?,?,?) ON DUPLICATE KEY UPDATE `Kills` = ? , `Deaths` = ?")) {
            save.setString(1, data.getPlayerUniqueId().toString());
            save.setInt(2, data.getKills());
            save.setInt(3, data.getDeaths());
            save.setInt(4, data.getKills());
            save.setInt(5, data.getDeaths());
            save.execute();
        }
    }

    private void saveYamlData(){
        File folder = new File(plugin.getDataFolder(),"Default_Data");
        if (!folder.exists()) folder.mkdir();
        for (DefaultData data : datas) {
            ymlSave(folder, data);
        }
    }

    private void saveYamlData(UUID uuid) {
        File folder = new File(plugin.getDataFolder(), "Default_Data");
        if (!folder.exists()) folder.mkdir();
        DefaultData data = findData(uuid);
        ymlSave(folder, data);
    }

    private void ymlSave(File folder, DefaultData data) {
        File yml = new File(folder, data.getPlayerUniqueId().toString() + ".yml");
        FileConfiguration user = new YamlConfiguration();
        user.set("kills", data.getKills());
        user.set("deaths", data.getDeaths());
        try {
            user.save(yml);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private DefaultData getSQLData(UUID uuid){
        try(Connection connection = SQLManager.getInstance().getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM `DefaultData` WHERE `UUID`=?")){
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

    private TreeSet<DefaultData> getSQLData() {
        TreeSet<DefaultData> data = new TreeSet<>();
        try (Connection connection = SQLManager.getInstance().getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM `DefaultData`")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UUID playerUniqueId = UUID.fromString(resultSet.getString("UUID"));
                int kills = resultSet.getInt("Kills");
                int deaths = resultSet.getInt("Deaths");
                data.add(new DefaultData(playerUniqueId, kills, deaths));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
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

    private TreeSet<DefaultData> getYamlData() {
        TreeSet<DefaultData> data = new TreeSet<>();
        File folder = new File(plugin.getDataFolder(), "Default_Data");
        if (!folder.exists()) folder.mkdir();
        File[] files = folder.listFiles();
        if (files == null) return data;
        for (File file : files) {
            if (!FilenameUtils.getExtension(file.getPath()).equals("yml")) continue;
            UUID uuid = UUID.fromString(FilenameUtils.getBaseName(file.getPath()));
            FileConfiguration user = YamlConfiguration.loadConfiguration(file);
            int kills = user.getInt("kills");
            int deaths = user.getInt("deaths");
            data.add(new DefaultData(uuid, kills, deaths));
        }
        return data;
    }


}
