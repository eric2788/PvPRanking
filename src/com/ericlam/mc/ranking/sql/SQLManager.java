package com.ericlam.mc.ranking.sql;

import com.ericlam.mc.ranking.main.PvPRanking;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class SQLManager {
    private static DataSource source;
    private static SQLManager manager;


    private SQLManager() {
        FileConfiguration DByml = PvPRanking.getConfigManager().getDatabase();
        HikariConfig config = new HikariConfig();
        String host = DByml.getString("mysql.host");
        String port = DByml.getString("mysql.port");
        String database = DByml.getString("mysql.database");
        String username = DByml.getString("mysql.username");
        String password = DByml.getString("mysql.password");
        int minsize = DByml.getInt("mysql.pool.min-size");
        int maxsize = DByml.getInt("mysql.pool.max-size");
        boolean SSL = DByml.getBoolean("mysql.use-ssl");
        String jdbc = "jdbc:mysql://" + host + ":" + port + "/" + database + "?" + "useSSL=" + SSL;
        config.setJdbcUrl(jdbc);
        config.setPoolName("PvPRanking");
        config.setMaximumPoolSize(maxsize);
        config.setMinimumIdle(minsize);
        config.setUsername(username);
        config.setPassword(password);
        /*config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        config.setAutoCommit(false);*/
        config.addDataSourceProperty("cachePrepStmts", true);
        config.addDataSourceProperty("useServerPrepStmts", true);
        config.addDataSourceProperty("prepStmtCacheSize", 250);
        config.addDataSourceProperty("prepStmtCacheSqlLimit", 2048);
        //config.addDataSourceProperty("useUnicode",true);
        config.addDataSourceProperty("characterEncoding","utf8");

        source = new HikariDataSource(config);
    }

    public static SQLManager getInstance() {
        if (manager == null) manager = new SQLManager();
        return manager;
    }

    public DataSource getDataSource() {
        return source;
    }

    public Connection getConnection() throws SQLException {
        return source.getConnection();
    }
}
