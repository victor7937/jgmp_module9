package com.epam.victor.pool.connection;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {
    private final static int MAX_CONNECTIONS_DEFAULT = 20;
    private final static int MAX_IDLE_CONNECTIONS_DEFAULT = 5;

    private int connectionsLimit;
    private int idleConnectionsCount;

    private final ReentrantLock lock = new ReentrantLock(true);
    private Semaphore semaphore;
    private List<RentedConnection> connections;

    private static class InstanceCreator {
        private static final ConnectionPool INSTANCE = new ConnectionPool();
    }

    public static ConnectionPool getInstance() {
        return InstanceCreator.INSTANCE;
    }

    private ConnectionPool() {
        connectionsLimit = MAX_CONNECTIONS_DEFAULT;
        idleConnectionsCount = MAX_IDLE_CONNECTIONS_DEFAULT;
    }

    private RentedConnection createNewConnection() {
        return new RentedConnection(ConnectionManager.getConnection());

    }

    private RentedConnection findAvailableConnection() {
        for (RentedConnection connection : connections) {
            if (connection.isAvailable()) {
                return connection;
            }
        }
        return null;
    }

    public int getAvailableConnectionsCount() {
        return (int) connections.stream().filter(RentedConnection::isAvailable).count();
    }

    public void init(int connectionsLimit, int idleConnectionsCount) {
        this.connectionsLimit = connectionsLimit;
        this.idleConnectionsCount = idleConnectionsCount;
        createIdleConnections();
    }

    public void init () {
        createIdleConnections();
    }

    private void createIdleConnections ()  {
        semaphore = new Semaphore(connectionsLimit, true);
        connections = new CopyOnWriteArrayList<>();
        for (int i = 0; i < idleConnectionsCount; i++) {
            connections.add(createNewConnection());
        }
    }

    public Connection getConnection() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            throw new ConnectionPoolException(e);
        }
        lock.lock();
        RentedConnection connection = findAvailableConnection();
        if (connection != null) {
            connection.setAvailable(false);
        }
        lock.unlock();
        if (connection == null) {
            connection = createNewConnection();
            connection.setAvailable(false);
            connections.add(connection);
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        lock.lock();
        int available = getAvailableConnectionsCount();

        if (available < idleConnectionsCount){
            ((RentedConnection) connection).setAvailable(true);
        }
        lock.unlock();

        if (available >= idleConnectionsCount) {
            connection.close();
            connections.remove(connection);
        }
        semaphore.release();
    }

    public void destroy() {
        for (RentedConnection connection : connections) {
            connection.close();
            connections.remove(connection);
        }
    }

    public int getConnectionsLimit() {
        return connectionsLimit;
    }

    public void setConnectionsLimit(int connectionsLimit) {
        this.connectionsLimit = connectionsLimit;
    }

    public int getIdleConnectionsCount() {
        return idleConnectionsCount;
    }

    public void setIdleConnectionsCount(int idleConnectionsCount) {
        this.idleConnectionsCount = idleConnectionsCount;
    }

}