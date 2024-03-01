package com.epam.victor.pool.connection;

public interface Connection {
    void close();

    boolean isClosed();

    void open();

    boolean isOpened();
}
