package by.anhelinam.sql.pool;

import by.anhelinam.sql.exception.ConnectionPoolException;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionPool {
    Connection getConnection() throws InterruptedException, ConnectionPoolException, SQLException;

    void closePool() throws InterruptedException, SQLException;
}
