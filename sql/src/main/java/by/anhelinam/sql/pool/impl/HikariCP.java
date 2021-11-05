package by.anhelinam.sql.pool.impl;

import by.anhelinam.sql.config.ApplicationConfig;
import by.anhelinam.sql.exception.ConnectionPoolException;
import by.anhelinam.sql.pool.ConnectionPool;
import by.anhelinam.sql.pool.ProxyConnection;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public enum HikariCP implements ConnectionPool {
    INSTANCE;

    private static final HikariConfig config = new HikariConfig();

//    а эти штуки должны быть в интерфейсе или как?
//    у меня файлы эти только инициализацией отличаются, это странно
    private static final int AVAILABLE_CAPACITY = 4;
    private static final BlockingQueue<ProxyConnection> availableConnections = new ArrayBlockingQueue<>(AVAILABLE_CAPACITY);
    private final List<ProxyConnection> unavailableConnections = new ArrayList<>();
    private static final long TIMEOUT = 10;

    static {
        config.setJdbcUrl(ApplicationConfig.getDbURL());
        config.setUsername(ApplicationConfig.getDbProperties().getProperty("user"));
        config.setPassword(ApplicationConfig.getDbProperties().getProperty("password"));
//        config.addDataSourceProperty( "cachePrepStmts" , "true" );
//        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
//        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        HikariDataSource ds = new HikariDataSource(config);
//    }
////    тут могут быть вопросы (хотя они везде)
//    {
        try {
            for (int i = 0; i < AVAILABLE_CAPACITY; ++i) {
                availableConnections.add(new ProxyConnection(ds.getConnection()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ProxyConnection getConnection() throws InterruptedException, ConnectionPoolException {
        ProxyConnection connection = availableConnections.poll(TIMEOUT, TimeUnit.SECONDS);
        if (connection != null) {
            unavailableConnections.add(connection);
            return connection;
        } else {
            throw new ConnectionPoolException("null(((");
        }
    }

    @Override
    public void returnConnection(ProxyConnection proxyConnection) throws InterruptedException {
        if (proxyConnection != null) {
            unavailableConnections.remove(proxyConnection);
            availableConnections.put(proxyConnection);
        }
    }

    @Override
    public void closePool() throws InterruptedException, SQLException {
        while (!availableConnections.isEmpty()) {
//            исправить
            availableConnections.take().closeInPool();
        }
        while (!unavailableConnections.isEmpty()) {
//            исправить
            unavailableConnections.get(0).closeInPool();
            unavailableConnections.remove(0);
        }
    }
}
