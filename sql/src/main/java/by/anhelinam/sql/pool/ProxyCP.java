package by.anhelinam.sql.pool;

import by.anhelinam.sql.pool.impl.ProxyConnection;

public interface ProxyCP extends ConnectionPool {
    void returnConnection(ProxyConnection proxyConnection) throws InterruptedException;
}
