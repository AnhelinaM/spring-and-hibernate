package by.anhelinam.sql.pool;

import by.anhelinam.sql.config.ApplicationConfig;
import by.anhelinam.sql.exception.ConnectionPoolException;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public interface ConnectionPool {
    public ProxyConnection getConnection() throws InterruptedException, ConnectionPoolException;

    public void returnConnection(ProxyConnection proxyConnection) throws InterruptedException;

    public void closePool() throws InterruptedException, SQLException;
}
