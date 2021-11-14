package by.anhelinam.sql.pool.impl;

import by.anhelinam.sql.config.ApplicationConfig;
import by.anhelinam.sql.pool.ConnectionPool;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public enum HikariCP implements ConnectionPool {
    INSTANCE;

    private static final HikariConfig config = new HikariConfig();

    static {
        config.setJdbcUrl(ApplicationConfig.getDbURL());
        config.setUsername(ApplicationConfig.getDbProperties().getProperty("user"));
        config.setPassword(ApplicationConfig.getDbProperties().getProperty("password"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
    }

    @Override
    public Connection getConnection() throws SQLException {
        HikariDataSource ds = new HikariDataSource(config);
        return ds.getConnection();
    }

    public void closePool() {
    }

}
