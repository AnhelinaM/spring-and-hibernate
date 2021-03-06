package by.anhelinam.sql.pool.impl;

import by.anhelinam.sql.config.ApplicationConfig;
import by.anhelinam.sql.exception.ConnectionPoolException;
import by.anhelinam.sql.pool.ProxyCP;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public enum CustomCP implements ProxyCP {
    INSTANCE;

    private static final int AVAILABLE_CAPACITY = 4;
    private final BlockingQueue<ProxyConnection> availableConnections = new ArrayBlockingQueue<>(AVAILABLE_CAPACITY);
    private final List<ProxyConnection> unavailableConnections = new ArrayList<>();
    private static final long TIMEOUT = 10;

    {
        try {
            for (int i = 0; i < AVAILABLE_CAPACITY; ++i) {
                availableConnections.add(new ProxyConnection(DriverManager.getConnection(ApplicationConfig.getDbURL(), ApplicationConfig.getDbProperties()), this));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Connection getConnection() throws InterruptedException, ConnectionPoolException {
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

    public void closePool() throws InterruptedException, SQLException {
        while (!availableConnections.isEmpty()) {
            availableConnections.take().closeInPool();
        }
        while (!unavailableConnections.isEmpty()) {
            unavailableConnections.get(0).closeInPool();
            unavailableConnections.remove(0);
        }
    }
}
