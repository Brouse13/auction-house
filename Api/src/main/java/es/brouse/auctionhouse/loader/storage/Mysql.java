package es.brouse.auctionhouse.loader.storage;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import es.brouse.auctionhouse.loader.config.YamlConfig;
import es.brouse.auctionhouse.loader.utils.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public class Mysql {
    private static final HikariConfig config = new HikariConfig();
    private static HikariDataSource dataSource;

    /**
     * Singleton that will handle the {@link Mysql}
     * @param settings started settings class
     */
    public static void init(YamlConfig settings) {
        if (dataSource != null)
            throw new IllegalStateException("Mysql has already been enabled");

        if (!settings.isMysqlEnabled()) {
            Logger.log("Mysql can't be enabled, check config");
            return;
        }

        config.setJdbcUrl("com.mysql.cj.jdbc.Driver");
        config.setUsername(settings.getUsername());
        config.setPassword(settings.getPassword());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(5000);
        dataSource = new HikariDataSource(config);
    }

    /**
     * Get the established connection
     * ThreadPool connections - 10
     * ThreadPool timeout     - 5000ms
     * Cache limit            - 2048 / 250
     * @return the sql connection
     * @throws SQLException if any error was established
     */
    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
